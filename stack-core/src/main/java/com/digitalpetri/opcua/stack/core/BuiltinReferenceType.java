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
import javax.annotation.Nullable;

import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;

public enum BuiltinReferenceType implements ReferenceType {

    REFERENCES(Identifiers.References, "References", null, true, true, null),
    HIERARCHICAL_REFERENCES(Identifiers.HierarchicalReferences, "HierarchicalReferences", null, false, true, REFERENCES),
    NON_HIERARCHICAL_REFERENCES(Identifiers.NonHierarchicalReferences, "NonHierarchicalReferences", null, true, true, REFERENCES),
    HAS_CHILD(Identifiers.HasChild, "HasChild", null, false, true, HIERARCHICAL_REFERENCES),
    AGGREGATES(Identifiers.Aggregates, "Aggregates", null, false, true, HAS_CHILD),
    ORGANIZES(Identifiers.Organizes, "Organizes", "OrganizedBy", false, false, HIERARCHICAL_REFERENCES),
    HAS_COMPONENT(Identifiers.HasComponent, "HasComponent", "ComponentOf", false, false, AGGREGATES),
    HAS_ORDERED_COMPONENT(Identifiers.HasOrderedComponent, "HasOrderedComponent", "OrderedComponentOf", false, false, HAS_COMPONENT),
    HAS_PROPERTY(Identifiers.HasProperty, "HasProperty", "PropertyOf", false, false, AGGREGATES),
    HAS_SUBTYPE(Identifiers.HasSubtype, "HasSubtype", "SubtypeOf", false, false, HAS_CHILD),
    HAS_MODELLING_RULE(Identifiers.HasModellingRule, "HasModellingRule", "ModellingRuleOf", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_TYPE_DEFINITION(Identifiers.HasTypeDefinition, "HasTypeDefinition", "TypeDefinitionOf", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_ENCODING(Identifiers.HasEncoding, "HasEncoding", "EncodingOf", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_DESCRIPTION(Identifiers.HasDescription, "HasDescription", "DescriptionOf", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_EVENT_SOURCE(Identifiers.HasEventSource, "HasEventSource", "EventSourceOf", false, false, HIERARCHICAL_REFERENCES),
    HAS_NOTIFIER(Identifiers.HasNotifier, "HasNotifier", "NotifierOf", false, false, HAS_EVENT_SOURCE),
    GENERATES_EVENT(Identifiers.GeneratesEvent, "GeneratesEvent", "GeneratedBy", false, false, NON_HIERARCHICAL_REFERENCES),
    ALWAYS_GENERATES_EVENT(Identifiers.AlwaysGeneratesEvent, "AlwaysGeneratesEvent", "AlwaysGeneratedBy", false, false, GENERATES_EVENT),
    FROM_STATE(Identifiers.FromState, "FromState", "ToTransition", false, false, NON_HIERARCHICAL_REFERENCES),
    TO_STATE(Identifiers.ToState, "ToState", "FromTransition", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_CAUSE(Identifiers.HasCause, "HasCause", "MayBeCausedBy", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_EFFECT(Identifiers.HasEffect, "HasEffect", "MayBeEffectedBy", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_SUB_STATE_MACHINE(Identifiers.HasSubStateMachine, "HasSubStateMachine", "SubStateMachineOf", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_TRUE_SUB_STATE(Identifiers.HasTrueSubState, "HasTrueSubState", "IsTrueSubStateOf", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_FALSE_SUB_STATE(Identifiers.HasFalseSubState, "HasFalseSubState", "IsFalseSubStateOf", false, false, NON_HIERARCHICAL_REFERENCES);


    private final NodeId nodeId;
    private final QualifiedName browseName;
    private final String inverseName;
    private final boolean symmetric;
    private final boolean isAbstract;
    private final BuiltinReferenceType superType;

    BuiltinReferenceType(NodeId nodeId,
                         String browseName,
                         @Nullable String inverseName,
                         boolean symmetric,
                         boolean isAbstract,
                         @Nullable BuiltinReferenceType superType) {

        this.nodeId = nodeId;
        this.browseName = new QualifiedName(0, browseName);
        this.inverseName = inverseName;
        this.symmetric = symmetric;
        this.isAbstract = isAbstract;
        this.superType = superType;
    }

    @Override
    public NodeId getNodeId() {
        return nodeId;
    }

    @Override
    public QualifiedName getBrowseName() {
        return browseName;
    }

    @Override
    public Optional<String> getInverseName() {
        return Optional.ofNullable(inverseName);
    }

    @Override
    public boolean isSymmetric() {
        return symmetric;
    }

    @Override
    public boolean isAbstract() {
        return isAbstract;
    }

    @Override
    public Optional<ReferenceType> getSuperType() {
        return Optional.ofNullable(superType);
    }

}
