package com.digitalpetri.opcua.stack.server.tcp;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.google.common.collect.Maps;
import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.server.handlers.UaTcpServerHelloHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketServer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<String, UaTcpStackServer> servers = Maps.newConcurrentMap();

    private volatile Channel channel;

    private final ServerBootstrap bootstrap = new ServerBootstrap();

    private final InetSocketAddress address;

    private SocketServer(InetSocketAddress address) {
        this.address = address;

        bootstrap.group(Stack.sharedEventLoop())
                .handler(new LoggingHandler(SocketServer.class))
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new UaTcpServerHelloHandler(SocketServer.this));
                    }
                });
    }

    public synchronized void bind() throws ExecutionException, InterruptedException {
        if (channel != null) return; // Already bound

        CompletableFuture<Void> bindFuture = new CompletableFuture<>();

        bootstrap.bind(address).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    channel = future.channel();
                    bindFuture.complete(null);
                } else {
                    bindFuture.completeExceptionally(future.cause());
                }
            }
        });

        bindFuture.get();
    }

    public void addServer(UaTcpStackServer server) {
        server.getEndpointUrls().forEach(url -> {
            if (!servers.containsKey(url)) {
                servers.put(url, server);
                logger.debug("Added server at {}", url);
            }
        });
        server.getDiscoveryUrls().forEach(url -> {
            if (!servers.containsKey(url)) {
                servers.put(url, server);
                logger.debug("Added server at {}", url);
            }
        });
    }

    public void removeServer(UaTcpStackServer server) {
        server.getEndpointUrls().forEach(url -> {
            if (servers.remove(url) != null) {
                logger.debug("Removed server at {}", url);
            }
        });
        server.getDiscoveryUrls().forEach(url -> {
            if (servers.remove(url) != null) {
                logger.debug("Removed server at {}", url);
            }
        });
    }

    public UaTcpStackServer getServer(String endpointUrl) {
        return servers.get(endpointUrl);
    }

    public SocketAddress getLocalAddress() {
        return channel != null ? channel.localAddress() : null;
    }

    public void shutdown() {
        if (channel != null) channel.close();
    }

    public static synchronized SocketServer boundTo(String address) throws Exception {
        return boundTo(address, Stack.DEFAULT_PORT);
    }

    public static synchronized SocketServer boundTo(String address, int port) throws Exception {
        return boundTo(InetAddress.getByName(address), port);
    }

    public static synchronized SocketServer boundTo(InetAddress address) throws Exception {
        return boundTo(address, Stack.DEFAULT_PORT);
    }

    public static synchronized SocketServer boundTo(InetAddress address, int port) throws Exception {
        return boundTo(new InetSocketAddress(address, port));
    }

    public static synchronized SocketServer boundTo(InetSocketAddress address) throws Exception {
        if (socketServers.containsKey(address)) {
            return socketServers.get(address);
        } else {
            SocketServer server = new SocketServer(address);
            server.bind();

            socketServers.put(address, server);

            return server;
        }
    }

    public static synchronized void shutdownAll() {
        socketServers.values().forEach(SocketServer::shutdown);
        socketServers.clear();
    }

    private static final Map<InetSocketAddress, SocketServer> socketServers = Maps.newConcurrentMap();

}
