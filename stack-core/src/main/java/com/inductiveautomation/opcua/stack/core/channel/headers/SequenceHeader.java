package com.inductiveautomation.opcua.stack.core.channel.headers;

import com.inductiveautomation.opcua.stack.core.util.annotations.UInt32;
import com.google.common.base.Objects;
import io.netty.buffer.ByteBuf;

/**
 * The sequence header ensures that the first encrypted block of every Message sent over a channel will start with
 * different data.
 * <p>
 * SequenceNumbers may not be reused for any TokenId. The SecurityToken lifetime should be short enough to ensure that
 * this never happens; however, if it does the receiver should treat it as a transport error and force a reconnect.
 */
public class SequenceHeader {

    @UInt32
    private final long sequenceNumber;

    @UInt32
    private final long requestId;

    /**
     * @param sequenceNumber a monotonically increasing sequence number assigned by the sender to each MessageChunk
     *                       sent over the SecureChannel.
     * @param requestId      an identifier assigned by the Client to OPC UA request Message. All MessageChunks for the
     *                       request and the associated response use the same identifier.
     */
    public SequenceHeader(long sequenceNumber, long requestId) {
        this.sequenceNumber = sequenceNumber;
        this.requestId = requestId;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public long getRequestId() {
        return requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SequenceHeader that = (SequenceHeader) o;

        return requestId == that.requestId && sequenceNumber == that.sequenceNumber;
    }

    @Override
    public int hashCode() {
        int result = (int) (sequenceNumber ^ (sequenceNumber >>> 32));
        result = 31 * result + (int) (requestId ^ (requestId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("sequenceNumber", sequenceNumber)
                .add("requestId", requestId)
                .toString();
    }

    public static ByteBuf encode(SequenceHeader header, ByteBuf buffer) {
        buffer.writeInt((int) header.getSequenceNumber());
        buffer.writeInt((int) header.getRequestId());

        return buffer;
    }

    public static SequenceHeader decode(ByteBuf buffer) {
        return new SequenceHeader(
                buffer.readUnsignedInt(), /*    SequenceNumber  */
                buffer.readUnsignedInt()  /*    RequestId       */
        );
    }

}
