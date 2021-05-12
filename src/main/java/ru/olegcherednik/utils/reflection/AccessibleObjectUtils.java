package ru.olegcherednik.utils.reflection;

import ru.olegcherednik.utils.reflection.exceptions.ReflectionUtilsException;

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
public final class AccessibleObjectUtils {

    /**
     * Invoke given consumer {@code task} on the given {@code accessibleObject}.
     *
     * @param accessibleObject not {@literal null} accessible object
     * @param task             not {@literal null} task
     * @param <T>              accessible object instance type
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     */
    public static <T extends AccessibleObject & Member> void invokeConsumer(T accessibleObject, Consumer<T> task) {
        ValidationUtils.requireObjNonNull(accessibleObject);
        ValidationUtils.requireTaskNonNull(task);

        invokeFunction(accessibleObject, (Function<T, Void>)func -> {
            task.accept(accessibleObject);
            return null;
        });
    }

    /**
     * Invoke given function {@code task} on the given {@code accessibleObject}.
     *
     * @param accessibleObject not {@literal null} accessible object
     * @param task             not {@literal null} task
     * @param <T>              accessible object type
     * @param <R>              type of the field or type of the method's return value
     * @return value of the field or return value of the method
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     */
    public static <T extends AccessibleObject & Member, R> R invokeFunction(T accessibleObject, Function<T, R> task) {
        ValidationUtils.requireAccessibleObjectNonNull(accessibleObject);
        ValidationUtils.requireTaskNonNull(task);

        boolean accessible = accessibleObject.isAccessible();

        try {
            accessibleObject.setAccessible(true);

            if (accessibleObject instanceof Field)
                return invokeWithModifiers(accessibleObject, task);
            return task.apply(accessibleObject);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new ReflectionUtilsException(e);
        } finally {
            accessibleObject.setAccessible(accessible);
        }
    }

    /**
     * Invoke given function {@code task} on the given {@code accessibleObject} with update modification. E.g. final field should be changed to not
     * final before set the value. After invocation, all modifications are restored to the initial state.
     *
     * @param accessibleObject not {@literal null} accessible object
     * @param task             not {@literal null} task
     * @param <T>              accessible object type
     * @param <R>              type of the field or type of the method's return value
     * @return value of the field or return value of the method
     */
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
     * @param obj              object instance ({@literal null} in case of static accessible object)
     * @param accessibleObject not {@literal null} accessible object ({@link Field} or {@link Method})
     * @param <T>              type of the field or type of the method's return value
     * @return value of the field or return value of the method
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws RuntimeException         in case if any other problem; checked exception is wrapped with runtime exception as well
     * @throws IllegalArgumentException in case of given {@code accessibleObject} neither {@code Field} not {@code Method}.
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
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws RuntimeException         in case if any other problem; checked exception is wrapped with runtime exception as well
     * @throws IllegalArgumentException in case of given {@code accessibleObject} neither {@code Field} not {@code Method}.
     */
    public static <T> T invoke(AccessibleObject accessibleObject) {
        return invoke(null, accessibleObject);
    }

    private AccessibleObjectUtils() { }

}
