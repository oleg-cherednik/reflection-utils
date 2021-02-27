package ru.olegcherednik.utils.reflection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
public final class Invoke {

    public static <T extends AccessibleObject & Member> void invokeConsumer(T accessibleObject, Consumer<T> task) throws Exception {
        invokeFunction(accessibleObject, (Function<T, Void>)func -> {
            task.accept(accessibleObject);
            return null;
        });
    }

    public static <T extends AccessibleObject & Member, R> R invokeFunction(T accessibleObject, Function<T, R> task) throws Exception {
        boolean accessible = accessibleObject.isAccessible();

        try {
            accessibleObject.setAccessible(true);

            if (accessibleObject instanceof Field)
                return invokeWithModifiers(accessibleObject, task);
            return task.apply(accessibleObject);
        } finally {
            accessibleObject.setAccessible(accessible);
        }
    }

    private static <T extends AccessibleObject & Member, R> R invokeWithModifiers(T accessibleObject, Function<T, R> task) throws Exception {
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        boolean accessible = modifiersField.isAccessible();
        int modifiers = accessibleObject.getModifiers();

        try {
            modifiersField.setAccessible(true);
            modifiersField.setInt(accessibleObject, modifiers & ~Modifier.FINAL);
            return task.apply(accessibleObject);
        } finally {
            modifiersField.setInt(accessibleObject, modifiers);
            modifiersField.setAccessible(accessible);
        }
    }

    public static <R> R invoke(Object obj, AccessibleObject accessibleObject) throws Exception {
        if (accessibleObject instanceof Field)
            return FieldUtils.getFieldValue(obj, (Field)accessibleObject);
        if (accessibleObject instanceof Method)
            return MethodUtils.invokeMethod(obj, (Method)accessibleObject);
        throw new IllegalArgumentException("Unknown 'accessibleObject' class: " + accessibleObject.getClass());
    }

    public interface Function<T, R> {

        R apply(T t) throws Exception;

    }

    public interface Consumer<T> {

        void accept(T t) throws Exception;

    }

    public static final Consumer<?> NULL_CONSUMER = (Consumer<Object>)obj -> { };

    private Invoke() { }

}
