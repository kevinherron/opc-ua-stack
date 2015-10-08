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

package com.digitalpetri.opcua.stack.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManifestUtil {

    private static final Logger logger = LoggerFactory.getLogger(ManifestUtil.class);
    private static final Map<String, String> attributes = load();

    public static Optional<String> read(String name) {
        return Optional.ofNullable(attributes.get(name));
    }

    public static boolean exists(String name) {
        return attributes.containsKey(name);
    }

    private static Map<String, String> load() {
        Map<String, String> attributes = Maps.newConcurrentMap();

        for (URI uri : uris()) {
            try {
                attributes.putAll(load(uri.toURL()));
            } catch (Throwable t) {
                logger.error("load(): '{}' failed", uri, t);
            }
        }

        return attributes;
    }

    private static Set<URI> uris() {
        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources("META-INF/MANIFEST.MF");

            Set<URI> uris = new HashSet<>();
            while (resources.hasMoreElements()) {
                uris.add(resources.nextElement().toURI());
            }

            return uris;
        } catch (Throwable t) {
            logger.error("uris() failed", t);
            return Collections.emptySet();
        }
    }

    private static Map<String, String> load(URL url) throws IOException {
        return load(url.openStream());
    }

    private static Map<String, String> load(InputStream stream) {
        Map<String, String> props = Maps.newConcurrentMap();

        try {
            Manifest manifest = new Manifest(stream);
            Attributes attributes = manifest.getMainAttributes();

            for (Object key : attributes.keySet()) {
                String value = attributes.getValue((Attributes.Name) key);
                props.put(key.toString(), value);
            }
        } catch (Throwable t) {
            logger.error("#load(): failed", t);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                // ignored
            }
        }

        return props;
    }

}
