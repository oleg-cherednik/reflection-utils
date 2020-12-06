package ru.olegcherednik.utils.reflection;

import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
public class MethodUtils {

    public static <T> T invokeMethod(Object obj, Method method) throws Exception {
        return Invoke.invokeFunction(method, m -> (T)m.invoke(obj));
    }

    public static <T> T invokeMethod(Object obj, String name) throws Exception {
        return invokeMethod(obj, name, null);
    }

    public static <T> T invokeMethod(Object obj, String name, Class<?> type, Object value) throws Exception {
        return invokeMethod(obj, name, new Class<?>[] { type }, value);
    }

    public static <T> T invokeMethod(Object obj, String name,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2) throws Exception {
        return invokeMethod(obj, name, new Class<?>[] { type1, type2 }, value1, value2);
    }

    public static <T> T invokeMethod(Object obj, String name,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2,
            Class<?> type3, Object value3) throws Exception {
        return invokeMethod(obj, name, new Class<?>[] { type1, type2, type3 }, value1, value2, value3);
    }

    public static <T> T invokeStaticMethod(Class<?> cls, String name) throws Exception {
        return invokeStaticMethod(cls, name, null);
    }

    public static <T> T invokeStaticMethod(Class<?> cls, String name, Class<?> type, Object value) throws Exception {
        return invokeStaticMethod(cls, name, new Class<?>[] { type }, value);
    }

    public static <T> T invokeStaticMethod(Class<?> cls, String name,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2) throws Exception {
        return invokeStaticMethod(cls, name, new Class<?>[] { type1, type2 }, value1, value2);
    }

    public static <T> T invokeStaticMethod(Class<?> cls, String name,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2,
            Class<?> type3, Object value3) throws Exception {
        return invokeStaticMethod(cls, name, new Class<?>[] { type1, type2, type3 }, value1, value2, value3);
    }

    public static <T> T invokeMethod(Object obj, String name, Class<?>[] types, Object... values) throws Exception {
        return Invoke.invokeFunction(getMethod(obj.getClass(), name, types), method -> (T)method.invoke(obj, values));
    }

    public static <T> T invokeStaticMethod(Class<?> cls, String name, Class<?>[] types, Object... values) throws Exception {
        return Invoke.invokeFunction(getMethod(cls, name, types), method -> (T)method.invoke(null, values));
    }

    private static Method getMethod(Class<?> cls, String name, Class<?>... types) throws NoSuchMethodException {
        Method method = null;

        while (method == null && cls != null) {
            try {
                method = cls.getDeclaredMethod(name, types);
            } catch(NoSuchMethodException ignored) {
                cls = cls.getSuperclass();
            }
        }

        return Optional.ofNullable(method).orElseThrow(NoSuchElementException::new);
    }

    public static Class<?> getReturnType(Method method) {
        return method.getReturnType();
    }

    public static Class<?> getReturnType(Method method, Class<?> def) {
        return method == null ? def : method.getReturnType();
    }

    private MethodUtils() {
    }

}
