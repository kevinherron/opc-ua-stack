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

import java.lang.reflect.Array;
import java.util.List;

public class ConversionUtil {

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(List<T> list, Class<T> c) {
        Object array = Array.newInstance(c, list.size());
        for (int i = 0; i < list.size(); i++) {
            Array.set(array, i, list.get(i));
        }
        return (T[]) array;
    }

    public static <T> T[] a(List<T> list, Class<T> c) {
        return toArray(list, c);
    }

}
