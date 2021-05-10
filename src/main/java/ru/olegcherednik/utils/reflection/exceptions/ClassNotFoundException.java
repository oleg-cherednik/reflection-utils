package ru.olegcherednik.utils.reflection.exceptions;

/**
 * @author Oleg Cherednik
 * @since 10.05.2021
 */
public final class ClassNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 9014565566139950106L;

    public ClassNotFoundException(String className) {
        super(String.format("Class '%s' was not found", className));
    }

}
