package ru.olegcherednik.utils.reflection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Oleg Cherednik
 * @since 09.05.2021
 */
final class ValidationUtils {

    public static Object requireObjNonNull(Object obj) {
        return Objects.requireNonNull(obj, "'obj' should not be null");
    }

    public static Field requireFieldNonNull(Field field) {
        return Objects.requireNonNull(field, "'field' should not be null");
    }

    public static String requireFieldNameNonNull(String fieldName) {
        return Objects.requireNonNull(fieldName, "'fieldName' should not be null");
    }

    public static Class<?> requireClsNonNull(Class<?> cls) {
        return Objects.requireNonNull(cls, "'cls' should not be null");
    }

    public static <T> T requireTaskNonNull(T task) {
        return Objects.requireNonNull(task, "'task' should not be null");
    }

    public static String requireConstantNameNonNull(String constantName) {
        return Objects.requireNonNull(constantName, "'constantName' should not be null");
    }

    public static <T extends Enum<?>> Consumer<T> requireSetExtraFieldTaskNonNull(Consumer<T> setExtraFieldTask) {
        return Objects.requireNonNull(setExtraFieldTask, "'setExtraFieldTask' should not be null");
    }

    public static String requireMethodNameNonNull(String methodName) {
        return Objects.requireNonNull(methodName, "'methodName' should not be null");
    }

    public static void requireTypeNonNull(Class<?>... types) {
        if (types == null || types.length == 0)
            return;
        if (types.length == 1)
            Objects.requireNonNull(types[0], "'type' should not be null");
        else {
            for (int i = 0; i < types.length; i++)
                Objects.requireNonNull(types[i], "'type" + (i + 1) + "' should not be null");
        }
    }

    public static Method requireMethodNonNull(Method method) {
        return Objects.requireNonNull(method, "'method' should not be null");
    }

    /**
     * Checks that given arrays {@code types} and {@code values} have the same length
     *
     * @param types  types of the parameters
     * @param values values of the parameters
     * @throws IllegalArgumentException in case of given arrays have not the same length
     */
    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static void requireLengthMatch(Class<?>[] types, Object[] values) {
        int typesLength = types == null ? 0 : types.length;
        int valuesLength = values == null ? 0 : values.length;

        if (typesLength != valuesLength)
            throw new IllegalArgumentException("Length of 'types' and 'values' should be equal");
    }

    public static String requireClassNameNonNull(String className) {
        return Objects.requireNonNull(className, "'className' should not be null");
    }

    public static <T> Constructor<T> requireConstructorNonNull(Constructor<T> constructor) {
        return Objects.requireNonNull(constructor, "'constructor' should not be null");
    }

    /**
     * Checks that given enum {@code cls} does not contain constant (case-sensitive) with given {@code constantName}.
     *
     * @param cls          not {@literal null} enum class object
     * @param constantName not {@literal null} enum's constant name
     * @param <T>          enum class type
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws RuntimeException         in case if any other problem; checked exception is wrapped with runtime exception as well
     * @throws IllegalArgumentException in case of enums contains constant with given name
     */
    public static <T extends Enum<?>> void requireConstantNotExist(Class<T> cls, String constantName) {
        requireClsNonNull(cls);
        requireConstantNameNonNull(constantName);

        if (Arrays.stream(cls.getEnumConstants()).anyMatch(value -> value.name().equals(constantName)))
            throw new IllegalArgumentException(String.format("Enum '%s' already has a constant with name '%s'", cls, constantName));
    }

    public static <T extends AccessibleObject & Member> T requireAccessibleObjectNonNull(T accessibleObject) {
        return Objects.requireNonNull(accessibleObject, "'accessibleObject' should not be null");
    }

    private ValidationUtils() {}

}
