package com.digitalpetri.opcua.stack.core.channels;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.UaRuntimeException;
import com.digitalpetri.opcua.stack.core.channels.messages.ErrorMessage;
import com.digitalpetri.opcua.stack.core.channels.messages.TcpMessageEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ExceptionHandler {

    public static void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String message = cause.toString();
        long statusCode = StatusCodes.Bad_UnexpectedError;

        Throwable innerCause = cause.getCause();

        if (innerCause instanceof UaException) {
            UaException ex = (UaException) innerCause;
            message = ex.getMessage();
            statusCode = ex.getStatusCode().getValue();
        } else if (innerCause instanceof UaRuntimeException) {
            UaRuntimeException ex = (UaRuntimeException) innerCause;
            message = ex.getMessage();
            statusCode = ex.getStatusCode();
        }

        ErrorMessage error = new ErrorMessage(statusCode, message);
        ByteBuf messageBuffer = TcpMessageEncoder.encode(error);

        ctx.writeAndFlush(messageBuffer).addListener(future -> ctx.close());
    }

}
