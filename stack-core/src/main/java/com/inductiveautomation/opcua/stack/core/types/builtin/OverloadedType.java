package com.inductiveautomation.opcua.stack.core.types.builtin;

/**
 * Overloaded types are types that exist in UA but don't map 1:1 to their own Java class, instead sharing the same
 * backing class as another UA type.
 * <p>
 * When it's necessary to distinguish which UA type is desired for a shared backing class or value, e.g. in the case of
 * {@link Variant}s, this type can be included to indicate the desired UA type.
 */
public enum OverloadedType {

    UByte(Byte.class),
    UInt16(Integer.class),
    UInt32(Long.class),
    UInt64(Long.class);

    private final Class<?> backingClass;

    OverloadedType(Class<?> backingClass) {
        this.backingClass = backingClass;
    }

    public Class<?> getBackingClass() {
        return backingClass;
    }

}
