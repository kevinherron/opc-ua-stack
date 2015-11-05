/*
 * Copyright 2015 Kevin Herron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.opcua.stack.core.serialization;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaSerializationException;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelegateRegistry {

    private static final Map<Class<?>, EncoderDelegate<?>> encodersByClass = Maps.newConcurrentMap();

    private static final Map<NodeId, EncoderDelegate<?>> encodersById = Maps.newConcurrentMap();

    private static final Map<Class<?>, DecoderDelegate<?>> decodersByClass = Maps.newConcurrentMap();

    private static final Map<NodeId, DecoderDelegate<?>> decodersById = Maps.newConcurrentMap();

    public static <T> void registerEncoder(EncoderDelegate<T> delegate, Class<T> clazz, NodeId... ids) {
        encodersByClass.put(clazz, delegate);

        if (ids != null) {
            Arrays.stream(ids).forEach(id -> encodersById.put(id, delegate));
        }
    }

    public static <T> void registerDecoder(DecoderDelegate<T> delegate, Class<T> clazz, NodeId... ids) {
        decodersByClass.put(clazz, delegate);

        if (ids != null) {
            Arrays.stream(ids).forEach(id -> decodersById.put(id, delegate));
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> EncoderDelegate<T> getEncoder(Object t) throws UaSerializationException {
        try {
            return (EncoderDelegate<T>) encodersByClass.get(t.getClass());
        } catch (NullPointerException e) {
            throw new UaSerializationException(StatusCodes.Bad_EncodingError,
                    "no encoder registered for class=" + t);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> EncoderDelegate<T> getEncoder(Class<?> clazz) throws UaSerializationException {
        try {
            return (EncoderDelegate<T>) encodersByClass.get(clazz);
        } catch (NullPointerException e) {
            throw new UaSerializationException(StatusCodes.Bad_EncodingError,
                    "no encoder registered for class=" + clazz);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> EncoderDelegate<T> getEncoder(NodeId encodingId) throws UaSerializationException {
        try {
            return (EncoderDelegate<T>) encodersById.get(encodingId);
        } catch (NullPointerException e) {
            throw new UaSerializationException(StatusCodes.Bad_EncodingError,
                    "no encoder registered for encodingId=" + encodingId);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> DecoderDelegate<T> getDecoder(T t) throws UaSerializationException {
        try {
            return (DecoderDelegate<T>) decodersByClass.get(t.getClass());
        } catch (NullPointerException e) {
            throw new UaSerializationException(StatusCodes.Bad_DecodingError,
                    "no decoder registered for class=" + t);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> DecoderDelegate<T> getDecoder(Class<T> clazz) throws UaSerializationException {
        try {
            return (DecoderDelegate<T>) decodersByClass.get(clazz);
        } catch (NullPointerException e) {
            throw new UaSerializationException(StatusCodes.Bad_DecodingError,
                    "no decoder registered for class=" + clazz);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> DecoderDelegate<T> getDecoder(NodeId encodingId) {
        DecoderDelegate<T> decoder = (DecoderDelegate<T>) decodersById.get(encodingId);

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

        ClassLoader classLoader = Stack.getCustomClassLoader()
                .orElse(DelegateRegistry.class.getClassLoader());

        try {
            loadGeneratedClasses(classLoader);
        } catch (Exception e1) {
            // Temporarily set the thread context ClassLoader to our
            // ClassLoader and try loading the classes one more time.
            
            Thread thread = Thread.currentThread();
            ClassLoader contextClassLoader = thread.getContextClassLoader();

            thread.setContextClassLoader(classLoader);

            try {
                loadGeneratedClasses(classLoader);
            } catch (Exception e2) {
                logger.error("Error loading generated classes.", e2);
            } finally {
                thread.setContextClassLoader(contextClassLoader);
            }
        }
    }

    private static void loadGeneratedClasses(ClassLoader classLoader) throws IOException, ClassNotFoundException {
        ClassPath classPath = ClassPath.from(classLoader);

        ImmutableSet<ClassInfo> structures =
                classPath.getTopLevelClasses("com.digitalpetri.opcua.stack.core.types.structured");

        ImmutableSet<ClassInfo> enumerations =
                classPath.getTopLevelClasses("com.digitalpetri.opcua.stack.core.types.enumerated");

        for (ClassInfo classInfo : Sets.union(structures, enumerations)) {
            Class<?> clazz = classInfo.load();
            Class.forName(clazz.getName(), true, classLoader);
        }
    }

}
