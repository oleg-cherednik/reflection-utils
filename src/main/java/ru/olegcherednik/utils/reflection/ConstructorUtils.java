package ru.olegcherednik.utils.reflection;

/**
 * @author Oleg Cherednik
 * @since 27.02.2021
 */
public final class ConstructorUtils {

    public static <T> T newInstance(Class<T> cls) throws Exception {
        return Invoke.invokeFunction(cls.getDeclaredConstructor(), constructor -> constructor.newInstance());
    }

    public static <T> T newInstance(Class<T> cls, Class<?> type, Object value) throws Exception {
        return Invoke.invokeFunction(cls.getDeclaredConstructor(type), constructor -> constructor.newInstance(value));
    }

    public static <T> T newInstance(Class<T> cls,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2) throws Exception {
        return Invoke.invokeFunction(cls.getDeclaredConstructor(type1, type2), constructor -> constructor.newInstance(value1, value2));
    }

    public static <T> T newInstance(Class<T> cls,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2,
            Class<?> type3, Object value3) throws Exception {
        return Invoke.invokeFunction(cls.getDeclaredConstructor(type1, type2, type3), constructor -> constructor.newInstance(value1, value2, value3));
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static <T> T newInstance(Class<T> cls, Class<?>[] types, Object[] values) throws Exception {
        return Invoke.invokeFunction(cls.getDeclaredConstructor(types), constructor -> constructor.newInstance(values));
    }

    public static <T> T newInstance(String className) throws Exception {
        return newInstance((Class<T>)Class.forName(className));
    }

    public static <T> T newInstance(String className, Class<?> type, Object value) throws Exception {
        return newInstance((Class<T>)Class.forName(className), type, value);
    }

    public static <T> T newInstance(String className,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2) throws Exception {
        return newInstance((Class<T>)Class.forName(className), type1, value1, type2, value2);
    }

    public static <T> T newInstance(String className,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2,
            Class<?> type3, Object value3) throws Exception {
        return newInstance((Class<T>)Class.forName(className), type1, value1, type2, value2, type3, value3);
    }

    public static <T> T newInstance(String className, Class<?>[] types, Object[] values) throws Exception {
        return newInstance((Class<T>)Class.forName(className), types, values);
    }

    private ConstructorUtils() {}

}
