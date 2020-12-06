package ru.olegcherednik.utils.reflection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
@SuppressWarnings("deprecation")
public class Invoke {

    public static <T> T invokeConstructor(Class<T> cls) throws Exception {
        return invokeFunction(cls.getDeclaredConstructor(), constructor -> constructor.newInstance(cls));
    }

    public static <T extends AccessibleObject, R> R invokeFunction(T accessibleObject, Function<T, R> task) throws Exception {
        boolean accessible = accessibleObject.isAccessible();

        try {
            accessibleObject.setAccessible(true);
            return task.apply(accessibleObject);
        } finally {
            accessibleObject.setAccessible(accessible);
        }
    }

    public static <T extends AccessibleObject> void invokeConsumer(T accessibleObject, Consumer<T> task) throws Exception {
        invokeFunction(accessibleObject, (Function<T, Void>)t -> {
            task.accept(accessibleObject);
            return null;
        });
    }

    public static <R> R invoke(Object obj, AccessibleObject accessibleObject) throws Exception {
        if (obj instanceof Field)
            return FieldUtils.getFieldValue(obj, (Field)accessibleObject);
        if (obj instanceof Method)
            return MethodUtils.invokeMethod(obj, (Method)accessibleObject);
        throw new IllegalArgumentException("Unknown 'obj' class: " + obj.getClass());
    }

    public static Class<?> getType(AccessibleObject obj) {
        if (obj instanceof Field)
            return FieldUtils.getType((Field)obj);
        if (obj instanceof Method)
            return MethodUtils.getReturnType((Method)obj);
        throw new IllegalArgumentException("Unknown 'obj' class: " + obj.getClass());
    }

    public interface Function<T, R> {

        R apply(T t) throws Exception;
    }

    public interface Consumer<T> {

        void accept(T t) throws Exception;
    }

    private Invoke() {
    }

}
