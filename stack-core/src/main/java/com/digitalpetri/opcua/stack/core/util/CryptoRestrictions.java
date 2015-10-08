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

import java.lang.reflect.Field;
import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Map;

import com.digitalpetri.opcua.stack.core.Stack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoRestrictions {

    private static volatile boolean removed = false;

    static {
        removed = removeCryptographyRestrictions();
    }

    /**
     * Attempt to remove cryptography restrictions.
     *
     * @return {@code true} if this is an Oracle JRE/JDK and restrictions were removed.
     */
    public static synchronized boolean remove() {
        return removed;
    }

    /**
     * Do the following, but with reflection to bypass access checks:
     * <p>
     * JceSecurity.isRestricted = false;
     * JceSecurity.defaultPolicy.perms.clear();
     * JceSecurity.defaultPolicy.add(CryptoAllPermission.INSTANCE);
     */
    private static boolean removeCryptographyRestrictions() {
        Logger logger = LoggerFactory.getLogger(Stack.class);

        if (isRestrictedCryptography()) {
            try {
                final Class<?> jceSecurity = Class.forName("javax.crypto.JceSecurity");
                final Class<?> cryptoPermissions = Class.forName("javax.crypto.CryptoPermissions");
                final Class<?> cryptoAllPermission = Class.forName("javax.crypto.CryptoAllPermission");

                final Field isRestrictedField = jceSecurity.getDeclaredField("isRestricted");
                isRestrictedField.setAccessible(true);
                isRestrictedField.set(null, false);

                final Field defaultPolicyField = jceSecurity.getDeclaredField("defaultPolicy");
                defaultPolicyField.setAccessible(true);
                final PermissionCollection defaultPolicy = (PermissionCollection) defaultPolicyField.get(null);

                final Field perms = cryptoPermissions.getDeclaredField("perms");
                perms.setAccessible(true);
                ((Map<?, ?>) perms.get(defaultPolicy)).clear();

                final Field instance = cryptoAllPermission.getDeclaredField("INSTANCE");
                instance.setAccessible(true);
                defaultPolicy.add((Permission) instance.get(null));

                logger.info("Successfully removed cryptography restrictions.");
                return true;
            } catch (final Exception e) {
                logger.warn("Failed to remove cryptography restrictions.", e);
                return false;
            }
        }

        return true;
    }

    private static boolean isRestrictedCryptography() {
        // This simply matches the Oracle JRE, but not OpenJDK.
        return "Java(TM) SE Runtime Environment".equals(System.getProperty("java.runtime.name"));
    }

}
