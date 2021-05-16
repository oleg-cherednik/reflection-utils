package ru.olegcherednik.utils.reflection.exceptions;

/**
 * @author Oleg Cherednik
 * @since 11.05.2021
 */
public class ReflectionUtilsException extends RuntimeException {

    private static final long serialVersionUID = 8833940399790258150L;

    public ReflectionUtilsException(String message) {
        super(message);
    }

    public ReflectionUtilsException(Throwable cause) {
        super(cause);
    }

}
