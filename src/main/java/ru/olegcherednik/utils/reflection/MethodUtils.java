package ru.olegcherednik.utils.reflection;

import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
public final class MethodUtils {

    public static <T> T invokeMethod(Object obj, String methodName) throws Exception {
        return invokeMethod(obj, methodName, (Class<?>[])null, null);
    }

    public static <T> T invokeMethod(Object obj, String methodName, Class<?> type, Object value) throws Exception {
        return invokeMethod(obj, methodName, new Class<?>[] { type }, new Object[] { value });
    }

    public static <T> T invokeMethod(Object obj, String methodName,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2) throws Exception {
        return invokeMethod(obj, methodName, new Class<?>[] { type1, type2 }, new Object[] { value1, value2 });
    }

    public static <T> T invokeMethod(Object obj, String methodName,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2,
            Class<?> type3, Object value3) throws Exception {
        return invokeMethod(obj, methodName, new Class<?>[] { type1, type2, type3 }, new Object[] { value1, value2, value3 });
    }

    public static <T> T invokeMethod(Object obj, Method method, Object... values) throws Exception {
        return Invoke.invokeFunction(method, m -> (T)m.invoke(obj, values));
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static <T> T invokeMethod(Object obj, String methodName, Class<?>[] types, Object[] values) throws Exception {
        return invokeMethod(obj, getMethod(obj.getClass(), methodName, types), values);
    }

    // --------------


    public static <T> T invokeStaticMethod(Class<?> cls, String methodName) throws Exception {
        return invokeStaticMethod(cls, methodName, (Class<?>[])null, null);
    }

    public static <T> T invokeStaticMethod(Class<?> cls, String methodName, Class<?> type, Object value) throws Exception {
        return invokeStaticMethod(cls, methodName, new Class<?>[] { type }, new Object[] { value });
    }

    public static <T> T invokeStaticMethod(Class<?> cls, String methodName,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2) throws Exception {
        return invokeStaticMethod(cls, methodName, new Class<?>[] { type1, type2 }, new Object[] { value1, value2 });
    }

    public static <T> T invokeStaticMethod(Class<?> cls, String methodName,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2,
            Class<?> type3, Object value3) throws Exception {
        return invokeStaticMethod(cls, methodName, new Class<?>[] { type1, type2, type3 }, new Object[] { value1, value2, value3 });
    }

    public static <T> T invokeStaticMethod(Method method, Object... values) throws Exception {
        return Invoke.invokeFunction(method, m -> (T)m.invoke(null, values));
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static <T> T invokeStaticMethod(Class<?> cls, String methodName, Class<?>[] types, Object[] values) throws Exception {
        return invokeStaticMethod(getMethod(cls, methodName, types), values);
    }

    private static Method getMethod(Class<?> cls, String methodName, Class<?>... types) throws NoSuchMethodException {
        Method method = null;

        while (method == null && cls != null) {
            try {
                method = cls.getDeclaredMethod(methodName, types);
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
