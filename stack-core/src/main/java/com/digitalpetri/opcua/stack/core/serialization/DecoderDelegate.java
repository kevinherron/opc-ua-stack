package com.digitalpetri.opcua.stack.core.serialization;

public interface DecoderDelegate<T> {
    T decode(UaDecoder decoder);
}
