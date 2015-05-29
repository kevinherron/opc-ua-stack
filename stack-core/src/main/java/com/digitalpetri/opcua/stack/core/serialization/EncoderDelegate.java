package com.digitalpetri.opcua.stack.core.serialization;

public interface EncoderDelegate<T> {
    void encode(T encodable, UaEncoder encoder);
}
