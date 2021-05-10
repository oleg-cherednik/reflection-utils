package ru.olegcherednik.utils.reflection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Utils for working with {@link AccessibleObject} and {@link Member} invocation.
 *
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
public final class InvokeUtils {

    public static <T extends AccessibleObject & Member> void invokeConsumer(T accessibleObject, Consumer<T> task) {
        invokeFunction(accessibleObject, (Function<T, Void>)func -> {
            task.accept(accessibleObject);
            return null;
        });
    }

    public static <T extends AccessibleObject & Member, R> R invokeFunction(T accessibleObject, Function<T, R> task) {
        boolean accessible = accessibleObject.isAccessible();

        try {
            accessibleObject.setAccessible(true);

            if (accessibleObject instanceof Field)
                return invokeWithModifiers(accessibleObject, task);
            return task.apply(accessibleObject);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    /**
     * Invoke given {@code accessibleObject} ({@link Field} or {@link Method}) for the given {@code obj}. In case of {@code obj} is {@literal null},
     * then static accessible object will be invoked.
     *
     * @param obj              object instance
     * @param accessibleObject not {@literal null} accessible object ({@link Field} or {@link Method})
     * @param <T>              type of the field or type of the method's return value
     * @return value of the field or return value of the method
     * @throws Exception in case if any problem; check exception type for details
     */
    public static <T> T invoke(Object obj, AccessibleObject accessibleObject) {
        if (accessibleObject instanceof Field) {
            Field field = (Field)accessibleObject;
            return obj == null ? FieldUtils.getStaticFieldValue(field) : FieldUtils.getFieldValue(obj, field);
        }

        if (accessibleObject instanceof Method) {
            Method method = (Method)accessibleObject;
            return obj == null ? MethodUtils.invokeStaticMethod(method) : MethodUtils.invokeMethod(obj, method);
        }

        throw new IllegalArgumentException("Unknown 'accessibleObject' class: " + accessibleObject.getClass());
    }

    /**
     * Invoke given static {@code accessibleObject} ({@link Field} or {@link Method}).
     *
     * @param accessibleObject not {@literal null} accessible object ({@link Field} or {@link Method})
     * @param <T>              type of the field or type of the method's return value
     * @return value of the field or return value of the method
     * @throws Exception in case if any problem; check exception type for details
     */
    public static <T> T invoke(AccessibleObject accessibleObject) {
        return invoke(null, accessibleObject);
    }

    public interface Function<T, R> {

        R apply(T t) throws Exception;

    }

    public interface Consumer<T> {

        Consumer<?> NULL = (Consumer<Object>)obj -> { };

        void accept(T t) throws Exception;

    }

    private InvokeUtils() { }

}
