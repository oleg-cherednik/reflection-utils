package ru.olegcherednik.utils.reflection;

import java.lang.reflect.Constructor;

/**
 * @author Oleg Cherednik
 * @since 27.02.2021
 */
public final class ConstructorUtils {

    public static <T> T invokeConstructor(Class<T> cls) throws Exception {
        return invokeConstructor(cls.getDeclaredConstructor());
    }

    public static <T> T invokeConstructor(Class<T> cls, Class<?> type, Object value) throws Exception {
        return invokeConstructor(cls.getDeclaredConstructor(type), value);
    }

    public static <T> T invokeConstructor(Class<T> cls,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2) throws Exception {
        return invokeConstructor(cls.getDeclaredConstructor(type1, type2), value1, value2);
    }

    public static <T> T invokeConstructor(Class<T> cls,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2,
            Class<?> type3, Object value3) throws Exception {
        return invokeConstructor(cls.getDeclaredConstructor(type1, type2, type3), value1, value2, value3);
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static <T> T invokeConstructor(Class<T> cls, Class<?>[] types, Object[] values) throws Exception {
        return invokeConstructor(cls.getDeclaredConstructor(types), values);
    }

    public static <T> T invokeConstructor(String className) throws Exception {
        return invokeConstructor((Class<T>)Class.forName(className));
    }

    public static <T> T invokeConstructor(String className, Class<?> type, Object value) throws Exception {
        return invokeConstructor((Class<T>)Class.forName(className), type, value);
    }

    public static <T> T invokeConstructor(String className,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2) throws Exception {
        return invokeConstructor((Class<T>)Class.forName(className), type1, value1, type2, value2);
    }

    public static <T> T invokeConstructor(String className,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2,
            Class<?> type3, Object value3) throws Exception {
        return invokeConstructor((Class<T>)Class.forName(className), type1, value1, type2, value2, type3, value3);
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static <T> T invokeConstructor(String className, Class<?>[] types, Object[] values) throws Exception {
        return invokeConstructor((Class<T>)Class.forName(className), types, values);
    }

    private static <T> T invokeConstructor(Constructor<T> constructor, Object... values) throws Exception {
        return InvokeUtils.invokeFunction(constructor, c -> c.newInstance(values));
    }

    private ConstructorUtils() {}

}
