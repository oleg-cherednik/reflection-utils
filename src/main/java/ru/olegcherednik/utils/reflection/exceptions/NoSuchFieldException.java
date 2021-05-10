package ru.olegcherednik.utils.reflection.exceptions;

/**
 * @author Oleg Cherednik
 * @since 10.05.2021
 */
public final class NoSuchFieldException extends RuntimeException {

    private static final long serialVersionUID = -2044079362036348059L;

    public NoSuchFieldException(Class<?> cls, String fieldName) {
        super(String.format("Field '%s' was not found in class '%s' and it's parents", fieldName, cls));
    }

}
