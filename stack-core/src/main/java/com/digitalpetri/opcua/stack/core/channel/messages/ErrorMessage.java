package com.digitalpetri.opcua.stack.core.channel.messages;

import com.digitalpetri.opcua.stack.core.serialization.binary.BinaryDecoder;
import com.digitalpetri.opcua.stack.core.serialization.binary.BinaryEncoder;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.google.common.base.MoreObjects;
import io.netty.buffer.ByteBuf;

public class ErrorMessage {

    private final StatusCode error;
    private final String reason;

    public ErrorMessage(long error, String reason) {
        this.error = new StatusCode(error);
        this.reason = reason;
    }

    public StatusCode getError() {
        return error;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {

        return MoreObjects.toStringHelper(this)
                .add("error", error)
                .add("reason", reason)
                .toString();
    }

    public static void encode(ErrorMessage message, ByteBuf buffer) {
        buffer.writeInt((int) message.getError().getValue());
        encodeString(message.getReason(), buffer);
    }

    public static ErrorMessage decode(ByteBuf buffer) {
        return new ErrorMessage(
                buffer.readUnsignedInt(),
                decodeString(buffer));
    }

    private static void encodeString(String s, ByteBuf buffer) {
        new BinaryEncoder().setBuffer(buffer).encodeString(null, s);
    }

    private static String decodeString(ByteBuf buffer) {
        return new BinaryDecoder().setBuffer(buffer).decodeString(null);
    }

}
