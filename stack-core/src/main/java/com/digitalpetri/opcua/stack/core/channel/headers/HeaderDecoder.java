package com.digitalpetri.opcua.stack.core.channel.headers;

import com.digitalpetri.opcua.stack.core.channel.messages.TcpMessageEncoder;
import com.google.common.primitives.Ints;
import io.netty.buffer.ByteBuf;

public interface HeaderDecoder {

    public static long PROTOCOL_VERSION = 0L;

	public static final int HeaderLength = 8;
	public static final int HeaderLengthIndex = 4;

	/**
	 * Get the message length from a {@link ByteBuf} containing a {@link TcpMessageEncoder}. The reader index will not be
	 * advanced.
	 *
	 * @param buffer {@link ByteBuf} to extract from.
	 * @return The message length, which includes the size of the header.
	 */
	default int getMessageLength(ByteBuf buffer) {
        return Ints.checkedCast(buffer.getUnsignedInt(buffer.readerIndex() + HeaderLengthIndex));
	}

}
