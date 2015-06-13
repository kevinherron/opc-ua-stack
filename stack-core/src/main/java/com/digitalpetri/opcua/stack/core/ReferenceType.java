/*
 * digitalpetri OPC-UA SDK
 *
 * Copyright (C) 2015 Kevin Herron
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
