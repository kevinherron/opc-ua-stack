package com.inductiveautomation.opcua.stack.core.types.builtin;

import javax.annotation.Nullable;

import com.inductiveautomation.opcua.stack.core.util.annotations.UInt16Primitive;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * This Built-in DataType contains a qualified name. It is, for example, used as BrowseName.
 */
public class QualifiedName {

    public static final QualifiedName NullValue = new QualifiedName(0, null);

    @UInt16Primitive
    private final int namespaceIndex;
    private final String name;

    /**
     * The name part of the QualifiedName is restricted to 512 characters.
     *
     * @param namespaceIndex index that identifies the namespace that defines the name. This index is the index of that
     *                       namespace in the local Server’s NamespaceArray.
     * @param name           the text portion of the QualifiedName.
     */
    public QualifiedName(@UInt16Primitive int namespaceIndex, @Nullable String name) {
        Preconditions.checkArgument(namespaceIndex >= 0 && namespaceIndex <= 0xFFFF, "namespaceIndex");
        Preconditions.checkArgument(name == null || name.length() <= 512, "name");

        this.namespaceIndex = namespaceIndex;
        this.name = name;
    }

    @UInt16Primitive
    public int getNamespaceIndex() {
        return namespaceIndex;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QualifiedName that = (QualifiedName) o;

        return namespaceIndex == that.namespaceIndex &&
                !(name != null ? !name.equals(that.name) : that.name != null);
    }

    @Override
    public int hashCode() {
        int result = namespaceIndex;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("namespaceIndex", namespaceIndex)
                .toString();
    }

    public static QualifiedName parse(String s) {
        String[] ss = s.split(":");

        int namespaceIndex = 0;
        String name = s;

        if (ss.length > 1) {
            try {
                namespaceIndex = Short.parseShort(ss[0]);
            } catch (NumberFormatException ignored) {
                namespaceIndex = 0;
            }
            name = ss[1];
        }

        return new QualifiedName(namespaceIndex, name);
    }

}
