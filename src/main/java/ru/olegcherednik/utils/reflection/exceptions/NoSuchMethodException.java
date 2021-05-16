package ru.olegcherednik.utils.reflection.exceptions;

import java.util.Arrays;

/**
 * @author Oleg Cherednik
 * @since 10.05.2021
 */
public final class NoSuchMethodException extends ReflectionUtilsException {

    private static final long serialVersionUID = -5313115007938010744L;

    public NoSuchMethodException(Class<?> cls, String methodName, Class<?>... types) {
        super(String.format("Method '%s' with arguments '%s' was not found in class '%s' and it's parents",
                methodName, Arrays.toString(types), cls));
    }

}
