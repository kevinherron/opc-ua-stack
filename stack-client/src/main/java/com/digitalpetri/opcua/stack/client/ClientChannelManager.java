package com.digitalpetri.opcua.stack.client;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.client.channel.UaTcpClientAcknowledgeHandler;
import com.digitalpetri.opcua.stack.core.Stack;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientChannelManager extends AbstractChannelManager {

    private final UaTcpClient client;

    public ClientChannelManager(UaTcpClient client) {
        this.client = client;
    }

    @Override
    protected CompletableFuture<Channel> connect(CompletableFuture<Channel> future) {
        CompletableFuture<Channel> handshake = new CompletableFuture<>();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(Stack.EVENT_LOOP)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new UaTcpClientAcknowledgeHandler(client, handshake));
                    }
                });

        URI uri = URI.create(client.getEndpointUrl());

        bootstrap.connect(uri.getHost(), uri.getPort()).addListener(f -> {
            if (!f.isSuccess()) {
                handshake.completeExceptionally(f.cause());
            }
        });

        handshake.whenComplete((ch, ex) -> {
            if (ch != null) {
                future.complete(ch);
            } else {
                future.completeExceptionally(ex);
            }
        });

        return future;
    }

    public CompletableFuture<Void> disconnect() {
        CompletableFuture<Void> disconnect = new CompletableFuture<>();

        if (isConnected()) {
            getChannel().whenComplete((ch, ex) -> {
                if (ch != null) {
                    ChannelFuture closeFuture = ch.close();
                    closeFuture.addListener(cf -> disconnect.complete(null));
                } else {
                    disconnect.completeExceptionally(ex);
                }
            });
        } else {
            disconnect.complete(null);
        }

        return disconnect;
    }

}
