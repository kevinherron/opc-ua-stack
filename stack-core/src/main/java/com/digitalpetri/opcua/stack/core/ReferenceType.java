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

package com.digitalpetri.opcua.stack.core;

import java.util.Optional;

import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;

public interface ReferenceType {

    /**
     * @return the {@link NodeId} that identifies this {@link ReferenceType}.
     */
    NodeId getNodeId();

    /**
     * @return the {@link QualifiedName} that identifies this {@link ReferenceType}.
     */
    QualifiedName getBrowseName();

    /**
     * @return the inverse name of the reference, i.e. the meaning of the reference as seen from the target node.
     */
    Optional<String> getInverseName();

    /**
     * @return {@code true} if the reference is the same as seen from the source node and the target node.
     */
    boolean isSymmetric();

    /**
     * @return {@code true} if the reference is an abstract reference type, i.e. no references of this type exist, only
     * references of its subtypes.
     */
    boolean isAbstract();

    /**
     * @return the super type of this reference type. All references except the root reference type have a super type.
     */
    Optional<ReferenceType> getSuperType();

}
