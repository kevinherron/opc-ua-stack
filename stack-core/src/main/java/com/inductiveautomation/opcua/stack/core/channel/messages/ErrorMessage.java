package com.inductiveautomation.opcua.stack.core.channel.messages;

import com.google.common.base.MoreObjects;
import com.inductiveautomation.opcua.stack.core.serialization.binary.BinaryDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.binary.BinaryEncoder;
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
        return MoreObjects.toStringHelper(this)
                .add("error", String.format("0x%08X", error))
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
        new BinaryEncoder().setBuffer(buffer).encodeString(null, s);
    }

    private static String decodeString(ByteBuf buffer) {
        return new BinaryDecoder().setBuffer(buffer).decodeString(null);
    }

}
