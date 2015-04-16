package com.digitalpetri.opcua.stack.core.serialization;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.digitalpetri.opcua.stack.core.UaSerializationException;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.reflect.ClassPath;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelegateRegistry {

    private static final Map<Class<? extends UaSerializable>, EncoderDelegate<? extends UaSerializable>>
            encodersByClass = Maps.newConcurrentMap();

    private static final Map<NodeId, EncoderDelegate<? extends UaSerializable>>
            encodersById = Maps.newConcurrentMap();

    private static final Map<Class<? extends UaSerializable>, DecoderDelegate<? extends UaSerializable>>
            decodersByClass = Maps.newConcurrentMap();

    private static final Map<NodeId, DecoderDelegate<? extends UaSerializable>>
            decodersById = Maps.newConcurrentMap();

    public static <T extends UaSerializable> void registerEncoder(EncoderDelegate<T> delegate, Class<T> clazz, NodeId... ids) {
        encodersByClass.put(clazz, delegate);

        if (ids != null) {
            Arrays.stream(ids).forEach(id -> encodersById.put(id, delegate));
        }
    }

    public static <T extends UaSerializable> void registerDecoder(DecoderDelegate<T> delegate, Class<T> clazz, NodeId... ids) {
        decodersByClass.put(clazz, delegate);

        if (ids != null) {
            Arrays.stream(ids).forEach(id -> decodersById.put(id, delegate));
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends UaSerializable> EncoderDelegate<T> getEncoder(T t) throws UaSerializationException {
        try {
            return (EncoderDelegate<T>) encodersByClass.get(t.getClass());
        } catch (NullPointerException e) {
            throw new UaSerializationException(StatusCodes.Bad_EncodingError,
                    "no encoder registered for class=" + t);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends UaSerializable> EncoderDelegate<T> getEncoder(Class<T> clazz) throws UaSerializationException {
        try {
            return (EncoderDelegate<T>) encodersByClass.get(clazz);
        } catch (NullPointerException e) {
            throw new UaSerializationException(StatusCodes.Bad_EncodingError,
                    "no encoder registered for class=" + clazz);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends UaSerializable> EncoderDelegate<T> getEncoder(NodeId encodingId) throws UaSerializationException {
        try {
            return (EncoderDelegate<T>) encodersById.get(encodingId);
        } catch (NullPointerException e) {
            throw new UaSerializationException(StatusCodes.Bad_EncodingError,
                    "no encoder registered for encodingId=" + encodingId);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends UaSerializable> DecoderDelegate<T> getDecoder(T t) throws UaSerializationException {
        try {
            return (DecoderDelegate<T>) decodersByClass.get(t.getClass());
        } catch (NullPointerException e) {
            throw new UaSerializationException(StatusCodes.Bad_DecodingError,
                    "no decoder registered for class=" + t);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends UaSerializable> DecoderDelegate<T> getDecoder(Class<T> clazz) throws UaSerializationException {
        try {
            return (DecoderDelegate<T>) decodersByClass.get(clazz);
        } catch (NullPointerException e) {
            throw new UaSerializationException(StatusCodes.Bad_DecodingError,
                    "no decoder registered for class=" + clazz);
        }
    }

    public static DecoderDelegate<? extends UaSerializable> getDecoder(NodeId encodingId) {
        DecoderDelegate<? extends UaSerializable> decoder = decodersById.get(encodingId);

        if (decoder == null) {
            throw new UaSerializationException(StatusCodes.Bad_DecodingError,
                    "no decoder registered for encodingId=" + encodingId);
        }

        return decoder;
    }

    static {
        /*
         * Reflect-o-magically find all generated structured and enumerated types and force their static initialization
         * blocks to run, registering their encode/decode methods with the delegate registry.
         */
        Logger logger = LoggerFactory.getLogger(DelegateRegistry.class);

        try {
            ClassLoader classLoader = DelegateRegistry.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);

            ImmutableSet<ClassPath.ClassInfo> structures =
                    classPath.getTopLevelClasses("com.digitalpetri.opcua.stack.core.types.structured");

            ImmutableSet<ClassPath.ClassInfo> enumerations =
                    classPath.getTopLevelClasses("com.digitalpetri.opcua.stack.core.types.enumerated");

            Sets.union(structures, enumerations).forEach(classInfo -> {
                Class<?> clazz = classInfo.load();

                try {
                    Class.forName(clazz.getName(), true, classLoader);
                } catch (Exception e) {
                    logger.error("Error loading class: {}", clazz, e);
                }
            });
        } catch (IOException e) {
            logger.error("Error loading class path.", e);
        }
    }

}
