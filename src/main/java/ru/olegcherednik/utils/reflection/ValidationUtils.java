package ru.olegcherednik.utils.reflection;

import java.lang.reflect.Field;
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

    private ValidationUtils() {}

}
