package com.digitalpetri.opcua.stack.core.channels.messages;

import com.digitalpetri.opcua.stack.core.serialization.binary.BinaryDecoder;
import com.digitalpetri.opcua.stack.core.serialization.binary.BinaryEncoder;
import com.google.common.base.Objects;
import io.netty.buffer.ByteBuf;

public class ErrorMessage {

    private final long error;
    private final String reason;

    public ErrorMessage(long error, String reason) {
        this.error = error;
        this.reason = reason;
    }

    public long getError() {
        return error;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("error", error)
                .add("reason", reason)
                .toString();
    }

    public static void encode(ErrorMessage message, ByteBuf buffer) {
        buffer.writeInt((int) message.getError());
        encodeString(message.getReason(), buffer);
    }

    public static ErrorMessage decode(ByteBuf buffer) {
        return new ErrorMessage(
                buffer.readUnsignedInt(),   /* Error  */
                decodeString(buffer)        /* Reason */
        );
    }

    private static void encodeString(String s, ByteBuf buffer) {
        new BinaryEncoder(buffer).encodeString(null, s);
    }

    private static String decodeString(ByteBuf buffer) {
        return new BinaryDecoder(buffer).decodeString(null);
    }

}
