package com.digitalpetri.opcua.stack.core.serialization.binary;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaRuntimeException;
import com.digitalpetri.opcua.stack.core.channel.ChannelConfig;
import com.digitalpetri.opcua.stack.core.serialization.DecoderDelegate;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaSerializable;
import com.digitalpetri.opcua.stack.core.serialization.UaSerializationException;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.XmlElement;
import com.digitalpetri.opcua.stack.core.util.ArrayUtil;
import com.digitalpetri.opcua.stack.core.util.TypeUtil;
import io.netty.buffer.ByteBuf;

public class BinaryDecoder implements UaDecoder {

    private volatile ByteBuf buffer;

    private final int maxArrayLength;
    private final int maxStringLength;

    public BinaryDecoder() {
        this(ChannelConfig.DEFAULT_MAX_ARRAY_LENGTH, ChannelConfig.DEFAULT_MAX_STRING_LENGTH);
    }

    public BinaryDecoder(int maxArrayLength, int maxStringLength) {
        this.maxArrayLength = maxArrayLength;
        this.maxStringLength = maxStringLength;
    }

    public BinaryDecoder setBuffer(ByteBuf buffer) {
        this.buffer = buffer;
        return this;
    }

    @Override
    public Boolean decodeBoolean(String field) {
        return buffer.readBoolean();
    }

    @Override
    public Byte decodeSByte(String field) {
        return buffer.readByte();
    }

    @Override
    public Short decodeInt16(String field) {
        return buffer.readShort();
    }

    @Override
    public Integer decodeInt32(String field) {
        return buffer.readInt();
    }

    @Override
    public Long decodeInt64(String field) {
        return buffer.readLong();
    }

    @Override
    public Short decodeByte(String field) {
        return buffer.readUnsignedByte();
    }

    @Override
    public Integer decodeUInt16(String field) {
        return buffer.readUnsignedShort();
    }

    @Override
    public Long decodeUInt32(String field) {
        return buffer.readUnsignedInt();
    }

    @Override
    public Long decodeUInt64(String field) {
        return buffer.readLong();
    }

    @Override
    public Float decodeFloat(String field) {
        return buffer.readFloat();
    }

    @Override
    public Double decodeDouble(String field) {
        return buffer.readDouble();
    }

    @Override
    public String decodeString(String field) {
        int length = decodeInt32(null);

        if (length == -1) {
            return null;
        } else {
            if (length > maxStringLength) {
                throw new UaSerializationException(StatusCodes.Bad_EncodingLimitsExceeded,
                        "max string length exceeded");
            }

            String s = buffer.toString(buffer.readerIndex(), length, Charset.forName("UTF-8"));
            buffer.skipBytes(length);
            return s;
        }
    }

    @Override
    public DateTime decodeDateTime(String field) {
        return new DateTime(buffer.readLong());
    }

    @Override
    public UUID decodeGuid(String field) {
        long part1 = buffer.readUnsignedInt();
        long part2 = buffer.readUnsignedShort();
        long part3 = buffer.readUnsignedShort();
        long part4 = buffer.order(ByteOrder.BIG_ENDIAN).readLong();

        long msb = (part1 << 32) | (part2 << 16) | part3;

        return new UUID(msb, part4);
    }

    @Override
    public ByteString decodeByteString(String field) {
        int length = decodeInt32(null);

        if (length == -1) {
            return ByteString.NullValue;
        } else {
            byte[] bs = new byte[length];
            buffer.readBytes(bs);
            return new ByteString(bs);
        }
    }

    @Override
    public XmlElement decodeXmlElement(String field) {
        ByteString byteString = decodeByteString(null);
        byte[] bs = byteString.bytes();

        if (bs == null) {
            return new XmlElement(null);
        } else {
            try {
                String fragment = new String(bs, "UTF-8");
                return new XmlElement(fragment);
            } catch (UnsupportedEncodingException e) {
                throw new UaRuntimeException(StatusCodes.Bad_DecodingError, e);
            }
        }
    }

    @Override
    public NodeId decodeNodeId(String field) {
        int format = buffer.readByte() & 0x0F;

        if (format == 0x00) {
            /* Two-byte format */
            return new NodeId(0, buffer.readUnsignedByte());
        } else if (format == 0x01) {
            /* Four-byte format */
            return new NodeId(buffer.readUnsignedByte(), buffer.readUnsignedShort());
        } else if (format == 0x02) {
            /* Numeric format */
            return new NodeId(buffer.readUnsignedShort(), buffer.readUnsignedInt());
        } else if (format == 0x03) {
            /* String format */
            return new NodeId(buffer.readUnsignedShort(), decodeString(null));
        } else if (format == 0x04) {
            /* Guid format */
            return new NodeId(buffer.readUnsignedShort(), decodeGuid(null));
        } else if (format == 0x05) {
            /* Opaque format */
            return new NodeId(buffer.readUnsignedShort(), decodeByteString(null));
        } else {
            throw new UaRuntimeException(StatusCodes.Bad_EncodingError, "invalid NodeId format: " + format);
        }
    }

    @Override
    public ExpandedNodeId decodeExpandedNodeId(String field) {
        int flags = buffer.getByte(buffer.readerIndex());

        NodeId nodeId = decodeNodeId(null);

        String namespaceUri = null;
        long serverIndex = 0;

        if ((flags & 0x80) == 0x80) {
            namespaceUri = decodeString(null);
        }

        if ((flags & 0x40) == 0x40) {
            serverIndex = decodeUInt32(null);
        }

        return new ExpandedNodeId(nodeId, namespaceUri, serverIndex);
    }

    @Override
    public StatusCode decodeStatusCode(String field) {
        return new StatusCode(decodeUInt32(null));
    }

    @Override
    public QualifiedName decodeQualifiedName(String field) {
        int namespaceIndex = decodeUInt16(null);
        String name = decodeString(null);

        return new QualifiedName(namespaceIndex, name);
    }

    @Override
    public LocalizedText decodeLocalizedText(String field) {
        int mask = buffer.readByte();

        String locale = null;
        String text = null;

        if ((mask & 1) == 1) {
            locale = decodeString(null);
        }

        if ((mask & 2) == 2) {
            text = decodeString(null);
        }

        return new LocalizedText(locale, text);
    }

    @Override
    public ExtensionObject decodeExtensionObject(String field) {
        NodeId dataTypeEncodingId = decodeNodeId(null);
        int encoding = buffer.readByte();

        if (encoding == 0) {
            return new ExtensionObject((UaSerializable) null, dataTypeEncodingId);
        } else if (encoding == 1) {
            DecoderDelegate<?> delegate = DelegateRegistry.getDecoder(dataTypeEncodingId);

            if (delegate != null) {
                buffer.skipBytes(4); // Length; not needed since we know what's coming.

                UaSerializable serializable = delegate.decode(this);

                return new ExtensionObject(serializable, dataTypeEncodingId);
            } else {
                ByteString byteString = decodeByteString(null);

                return new ExtensionObject(byteString, dataTypeEncodingId);
            }
        } else if (encoding == 2) {
            XmlElement xmlElement = decodeXmlElement(null);

            return new ExtensionObject(xmlElement, dataTypeEncodingId);
        } else {
            throw new UaRuntimeException(StatusCodes.Bad_DecodingError, "unknown ExtensionObject encoding: " + encoding);
        }
    }

    @Override
    public DataValue decodeDataValue(String field) {
        int mask = buffer.readByte() & 0x0F;

        Variant value = ((mask & 0x01) == 0x01) ? decodeVariant(null) : null;
        StatusCode status = ((mask & 0x02) == 0x02) ? decodeStatusCode(null) : StatusCode.Good;
        DateTime sourceTime = ((mask & 0x04) == 0x04) ? decodeDateTime(null) : DateTime.MinValue;
        DateTime serverTime = ((mask & 0x08) == 0x08) ? decodeDateTime(null) : DateTime.MinValue;

        return new DataValue(value, status, sourceTime, serverTime);
    }

    @Override
    public Variant decodeVariant(String field) {
        int encodingMask = buffer.readByte();

        if (encodingMask == 0) {
            return new Variant(null);
        } else {
            int typeId = encodingMask & 0x3F;
            boolean dimensionsEncoded = (encodingMask & 0x40) == 0x40;
            boolean arrayEncoded = (encodingMask & 0x80) == 0x80;

            if (arrayEncoded) {
                int length = decodeInt32(null);
                Class<?> backingClass = TypeUtil.getBackingClass(typeId);
                Object flatArray = Array.newInstance(backingClass, length);

                for (int i = 0; i < length; i++) {
                    Object element = decodeBuiltinType(typeId);
                    Array.set(flatArray, i, element);
                }

                int[] dimensions = dimensionsEncoded ? decodeDimensions() : new int[]{length};
                Object array = dimensions.length > 1 ? ArrayUtil.unflatten(flatArray, dimensions) : flatArray;

                if (TypeUtil.isOverloadedType(typeId)) {
                    return new Variant(array, TypeUtil.getOverloadedType(typeId));
                } else {
                    return new Variant(array);
                }
            } else {
                Object value = decodeBuiltinType(typeId);

                if (TypeUtil.isOverloadedType(typeId)) {
                    return new Variant(value, TypeUtil.getOverloadedType(typeId));
                } else {
                    return new Variant(value);
                }
            }
        }
    }

    @Override
    public DiagnosticInfo decodeDiagnosticInfo(String field) {
        int mask = buffer.readByte();

        if (mask == 0) {
            return null;
        } else {
            int symbolicId = ((mask & 0x01) == 0x01) ? decodeInt32(null) : -1;
            int namespaceUri = ((mask & 0x02) == 0x02) ? decodeInt32(null) : -1;
            int localizedText = ((mask & 0x04) == 0x04) ? decodeInt32(null) : -1;
            int locale = ((mask & 0x08) == 0x08) ? decodeInt32(null) : -1;
            String additionalInfo = ((mask & 0x10) == 0x10) ? decodeString(null) : null;
            StatusCode innerStatusCode = ((mask & 0x20) == 0x20) ? decodeStatusCode(null) : null;
            DiagnosticInfo innerDiagnosticInfo = ((mask & 0x40) == 0x40) ? decodeDiagnosticInfo(null) : null;

            return new DiagnosticInfo(namespaceUri, symbolicId, locale, localizedText, additionalInfo, innerStatusCode, innerDiagnosticInfo);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends UaStructure> T decodeMessage(String field) {
        NodeId encodingId = decodeNodeId(null);

        DecoderDelegate<?> delegate = DelegateRegistry.getDecoder(encodingId);

        return (T) delegate.decode(this);
    }

    @Override
    public <T extends UaSerializable> T decodeSerializable(String field, Class<T> clazz) {
        DecoderDelegate<T> delegate = DelegateRegistry.getDecoder(clazz);

        return delegate.decode(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] decodeArray(String field, Function<String, T> decoder, Class<T> clazz) {
        int length = decodeInt32(null);

        if (length == -1) {
            return null;
        } else {
            if (length > maxArrayLength) {
                throw new UaSerializationException(StatusCodes.Bad_EncodingLimitsExceeded,
                        "max array length exceeded");
            }

            Object array = Array.newInstance(clazz, length);

            for (int i = 0; i < length; i++) {
                Array.set(array, i, decoder.apply(null));
            }

            return (T[]) array;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] decodeArray(String field, BiFunction<String, Class<T>, T> decoder, Class<T> clazz) {
        int length = decodeInt32(null);

        if (length == -1) {
            return null;
        } else {
            if (length > maxArrayLength) {
                throw new UaSerializationException(StatusCodes.Bad_EncodingLimitsExceeded,
                        "max array length exceeded");
            }

            T[] array = (T[]) Array.newInstance(clazz, length);
            for (int i = 0; i < length; i++) {
                array[i] = decoder.apply(null, clazz);
            }
            return array;
        }
    }

    private int[] decodeDimensions() {
        int length = decodeInt32(null);

        if (length == -1) {
            return new int[0];
        } else {
            int[] is = new int[length];
            for (int i = 0; i < length; i++) {
                is[i] = decodeInt32(null);
            }
            return is;
        }
    }

    private Object decodeBuiltinType(int typeId) {
		switch (typeId) {
			case 1: return decodeBoolean(null);
			case 2: return decodeSByte(null);
			case 3: return decodeByte(null);
			case 4: return decodeInt16(null);
			case 5: return decodeUInt16(null);
			case 6: return decodeInt32(null);
			case 7: return decodeUInt32(null);
			case 8: return decodeInt64(null);
			case 9: return decodeUInt64(null);
			case 10: return decodeFloat(null);
			case 11: return decodeDouble(null);
			case 12: return decodeString(null);
			case 13: return decodeDateTime(null);
			case 14: return decodeGuid(null);
			case 15: return decodeByteString(null);
			case 16: return decodeXmlElement(null);
			case 17: return decodeNodeId(null);
			case 18: return decodeExpandedNodeId(null);
			case 19: return decodeStatusCode(null);
			case 20: return decodeQualifiedName(null);
			case 21: return decodeLocalizedText(null);
			case 22: return decodeExtensionObject(null);
			case 23: return decodeDataValue(null);
			case 24: return decodeVariant(null);
			case 25: return decodeDiagnosticInfo(null);
			default: throw new UaRuntimeException(StatusCodes.Bad_DecodingError, "unknown builtin type: " + typeId);
		}
    }

}
