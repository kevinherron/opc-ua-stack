package com.digitalpetri.opcua.stack.core.serialization;

public interface DecoderDelegate<T extends UaSerializable> {
    T decode(UaDecoder decoder);
}
