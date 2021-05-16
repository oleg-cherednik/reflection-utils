package ru.olegcherednik.utils.reflection.exceptions;

import java.util.Arrays;

/**
 * @author Oleg Cherednik
 * @since 10.05.2021
 */
public final class NoSuchConstructorException extends ReflectionUtilsException {

    private static final long serialVersionUID = 5593468798001649472L;

    public NoSuchConstructorException(Class<?> cls, Class<?>... types) {
        super(String.format("Constructor with arguments '%s' was not found in class '%s'", Arrays.toString(types), cls));
    }

}
