/*
 * Copyright 2015 Kevin Herron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.opcua.stack;

import java.util.List;

import com.digitalpetri.opcua.stack.core.channel.ChannelConfig;
import com.digitalpetri.opcua.stack.core.channel.ChannelParameters;
import com.digitalpetri.opcua.stack.core.channel.ChunkDecoder;
import com.digitalpetri.opcua.stack.core.channel.ChunkEncoder;
import com.digitalpetri.opcua.stack.core.channel.SecureChannel;
import com.digitalpetri.opcua.stack.core.channel.messages.MessageType;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.util.BufferUtil;
import com.digitalpetri.opcua.stack.core.util.CryptoRestrictions;
import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ChunkSerializationTest extends SecureChannelFixture {

    static {
        CryptoRestrictions.remove();

//        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.PARANOID);
    }

    Logger logger = LoggerFactory.getLogger(getClass());

    ChannelParameters parameters = new ChannelParameters(
            ChannelConfig.DEFAULT_MAX_MESSAGE_SIZE,
            ChannelConfig.DEFAULT_MAX_CHUNK_SIZE,
            ChannelConfig.DEFAULT_MAX_CHUNK_SIZE,
            ChannelConfig.DEFAULT_MAX_CHUNK_COUNT,
            ChannelConfig.DEFAULT_MAX_MESSAGE_SIZE,
            ChannelConfig.DEFAULT_MAX_CHUNK_SIZE,
            ChannelConfig.DEFAULT_MAX_CHUNK_SIZE,
            ChannelConfig.DEFAULT_MAX_CHUNK_COUNT
    );

    @DataProvider
    public Object[][] getAsymmetricSecurityParameters() {
        return new Object[][]{
                {SecurityPolicy.None, MessageSecurityMode.None, 128},
                {SecurityPolicy.Basic128Rsa15, MessageSecurityMode.Sign, 128},
                {SecurityPolicy.Basic128Rsa15, MessageSecurityMode.SignAndEncrypt, 128},
                {SecurityPolicy.Basic256, MessageSecurityMode.Sign, 128},
                {SecurityPolicy.Basic256, MessageSecurityMode.SignAndEncrypt, 128},
                {SecurityPolicy.Basic256Sha256, MessageSecurityMode.Sign, 128},
                {SecurityPolicy.Basic256Sha256, MessageSecurityMode.SignAndEncrypt, 128},

                {SecurityPolicy.None, MessageSecurityMode.None, ChannelConfig.DEFAULT_MAX_CHUNK_SIZE},
                {SecurityPolicy.Basic128Rsa15, MessageSecurityMode.Sign, ChannelConfig.DEFAULT_MAX_CHUNK_SIZE},
                {SecurityPolicy.Basic128Rsa15, MessageSecurityMode.SignAndEncrypt, ChannelConfig.DEFAULT_MAX_CHUNK_SIZE},
                {SecurityPolicy.Basic256, MessageSecurityMode.Sign, ChannelConfig.DEFAULT_MAX_CHUNK_SIZE},
                {SecurityPolicy.Basic256, MessageSecurityMode.SignAndEncrypt, ChannelConfig.DEFAULT_MAX_CHUNK_SIZE},
                {SecurityPolicy.Basic256Sha256, MessageSecurityMode.Sign, ChannelConfig.DEFAULT_MAX_CHUNK_SIZE},
                {SecurityPolicy.Basic256Sha256, MessageSecurityMode.SignAndEncrypt, ChannelConfig.DEFAULT_MAX_CHUNK_SIZE},
        };
    }

    @Test(dataProvider = "getAsymmetricSecurityParameters")
    public void testAsymmetricMessage(SecurityPolicy securityPolicy,
                                      MessageSecurityMode messageSecurity,
                                      int messageSize) throws Exception {

        logger.info("Asymmetric chunk serialization, securityPolicy={}, messageSecurityMode={}, messageSize={}",
                securityPolicy, messageSecurity, messageSize);

        ChunkEncoder encoder = new ChunkEncoder(parameters);
        ChunkDecoder decoder = new ChunkDecoder(parameters);

        SecureChannel[] channels = generateChannels(securityPolicy, messageSecurity);
        SecureChannel clientChannel = channels[0];
        SecureChannel serverChannel = channels[1];

        byte[] messageBytes = new byte[messageSize];
        for (int i = 0; i < messageBytes.length; i++) {
            messageBytes[i] = (byte) i;
        }

        ByteBuf messageBuffer = BufferUtil.buffer().writeBytes(messageBytes);

        List<ByteBuf> chunkBuffers = encoder.encodeAsymmetricRequest(
                clientChannel,
                MessageType.OpenSecureChannel,
                messageBuffer
        );

        ByteBuf decodedBuffer = decoder.decodeAsymmetric(
                serverChannel,
                chunkBuffers
        );

        ReferenceCountUtil.releaseLater(messageBuffer);
        ReferenceCountUtil.releaseLater(decodedBuffer);

        messageBuffer.readerIndex(0);
        assertEquals(decodedBuffer, messageBuffer);
    }

    @DataProvider
    public Object[][] getSymmetricSecurityParameters() {
        return new Object[][]{
                {SecurityPolicy.None, MessageSecurityMode.None, 128},
                {SecurityPolicy.Basic128Rsa15, MessageSecurityMode.Sign, 128},
                {SecurityPolicy.Basic128Rsa15, MessageSecurityMode.SignAndEncrypt, 128},
                {SecurityPolicy.Basic256, MessageSecurityMode.Sign, 128},
                {SecurityPolicy.Basic256, MessageSecurityMode.SignAndEncrypt, 128},
                {SecurityPolicy.Basic256Sha256, MessageSecurityMode.Sign, 128},
                {SecurityPolicy.Basic256Sha256, MessageSecurityMode.SignAndEncrypt, 128},

                {SecurityPolicy.None, MessageSecurityMode.None, ChannelConfig.DEFAULT_MAX_CHUNK_SIZE},
                {SecurityPolicy.Basic128Rsa15, MessageSecurityMode.Sign, ChannelConfig.DEFAULT_MAX_CHUNK_SIZE},
                {SecurityPolicy.Basic128Rsa15, MessageSecurityMode.SignAndEncrypt, ChannelConfig.DEFAULT_MAX_CHUNK_SIZE},
                {SecurityPolicy.Basic256, MessageSecurityMode.Sign, ChannelConfig.DEFAULT_MAX_CHUNK_SIZE},
                {SecurityPolicy.Basic256, MessageSecurityMode.SignAndEncrypt, ChannelConfig.DEFAULT_MAX_CHUNK_SIZE},
                {SecurityPolicy.Basic256Sha256, MessageSecurityMode.Sign, ChannelConfig.DEFAULT_MAX_CHUNK_SIZE},
                {SecurityPolicy.Basic256Sha256, MessageSecurityMode.SignAndEncrypt, ChannelConfig.DEFAULT_MAX_CHUNK_SIZE},

                {SecurityPolicy.None, MessageSecurityMode.None, ChannelConfig.DEFAULT_MAX_MESSAGE_SIZE},
                {SecurityPolicy.Basic128Rsa15, MessageSecurityMode.Sign, ChannelConfig.DEFAULT_MAX_MESSAGE_SIZE},
                {SecurityPolicy.Basic128Rsa15, MessageSecurityMode.SignAndEncrypt, ChannelConfig.DEFAULT_MAX_MESSAGE_SIZE},
                {SecurityPolicy.Basic256, MessageSecurityMode.Sign, ChannelConfig.DEFAULT_MAX_MESSAGE_SIZE},
                {SecurityPolicy.Basic256, MessageSecurityMode.SignAndEncrypt, ChannelConfig.DEFAULT_MAX_MESSAGE_SIZE},
                {SecurityPolicy.Basic256Sha256, MessageSecurityMode.Sign, ChannelConfig.DEFAULT_MAX_MESSAGE_SIZE},
                {SecurityPolicy.Basic256Sha256, MessageSecurityMode.SignAndEncrypt, ChannelConfig.DEFAULT_MAX_MESSAGE_SIZE},
        };
    }

    @Test(dataProvider = "getSymmetricSecurityParameters")
    public void testSymmetricMessage(SecurityPolicy securityPolicy,
                                     MessageSecurityMode messageSecurity,
                                     int messageSize) throws Exception {

        logger.info("Symmetric chunk serialization, securityPolicy={}, messageSecurityMode={}, messageSize={}",
                securityPolicy, messageSecurity, messageSize);

        ChunkEncoder encoder = new ChunkEncoder(parameters);
        ChunkDecoder decoder = new ChunkDecoder(parameters);

        SecureChannel[] channels = generateChannels(securityPolicy, messageSecurity);
        SecureChannel clientChannel = channels[0];
        SecureChannel serverChannel = channels[1];

        byte[] messageBytes = new byte[messageSize];
        for (int i = 0; i < messageBytes.length; i++) {
            messageBytes[i] = (byte) i;
        }

        ByteBuf messageBuffer = BufferUtil.buffer().writeBytes(messageBytes);

        List<ByteBuf> chunkBuffers = encoder.encodeSymmetricRequest(
                clientChannel,
                MessageType.SecureMessage,
                messageBuffer
        );

        ByteBuf decodedBuffer = decoder.decodeSymmetric(
                serverChannel,
                chunkBuffers
        );

        ReferenceCountUtil.releaseLater(messageBuffer);
        ReferenceCountUtil.releaseLater(decodedBuffer);

        messageBuffer.readerIndex(0);
        assertEquals(decodedBuffer, messageBuffer);
    }

}
