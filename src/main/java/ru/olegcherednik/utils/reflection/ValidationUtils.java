package ru.olegcherednik.utils.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

    public static InvokeUtils.Consumer<Field> requireSetValueTaskNonNull(InvokeUtils.Consumer<Field> setValueTask) {
        return Objects.requireNonNull(setValueTask, "'setValueTask' should not be null");
    }

    public static String requireConstantNameNonNull(String constantName) {
        return Objects.requireNonNull(constantName, "'constantName' should not be null");
    }

    public static <T extends Enum<?>> InvokeUtils.Consumer<T> requireSetExtraFieldTaskNonNull(InvokeUtils.Consumer<T> setExtraFieldTask) {
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

    private ValidationUtils() {}

}
