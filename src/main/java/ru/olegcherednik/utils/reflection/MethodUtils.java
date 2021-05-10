package ru.olegcherednik.utils.reflection;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Utils for working with {@link Method} using reflection.
 *
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
public final class MethodUtils {

    /**
     * Invoke not static method with the given {@code methodName} and no arguments for the given {@code obj}.<br>
     * Method with this {@code methodName} could be as in the given class itself as in any it's parents. The first found method is taken.
     *
     * @param obj        not {@literal null} object instance
     * @param methodName not {@literal null} method name
     * @param <T>        type of the method's return value
     * @return return value of the method
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeMethod(Object obj, String methodName) throws Exception {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireMethodNameNonNull(methodName);

        return invokeMethod(obj, methodName, (Class<?>[])null, null);
    }

    /**
     * Invoke not static method with the given {@code methodName} and exactly one argument ({@code type}/{@code value}) for the given
     * {@code obj}.<br>
     * Method with this {@code methodName} could be as in the given class itself as in any it's parents. The first found method is taken.
     *
     * @param obj        not {@literal null} object instance
     * @param methodName not {@literal null} method name
     * @param type       not {@literal null} type of the argument
     * @param value      value of the argument
     * @param <T>        type of the method's return value
     * @return return value of the method
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeMethod(Object obj, String methodName, Class<?> type, Object value) throws Exception {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireMethodNameNonNull(methodName);
        ValidationUtils.requireTypeNonNull(type);

        return invokeMethod(obj, methodName, new Class<?>[] { type }, new Object[] { value });
    }

    /**
     * Invoke not static method with the given {@code methodName} and exactly two arguments ({@code type1}/{@code value1} for the first argument and
     * {@code type2}/{@code value2} for the second argument) for the given {@code obj}.<br>
     * Method with this {@code methodName} could be as in the given class itself as in any it's parents. The first found method is taken.
     *
     * @param obj        not {@literal null} object instance
     * @param methodName not {@literal null} method name
     * @param type1      not {@literal null} type of the 1st argument
     * @param value1     value of the 1st argument
     * @param type2      not {@literal null} type of the 2nd argument
     * @param value2     value of the 2nd argument
     * @param <T>        type of the method's return value
     * @return return value of the method
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeMethod(Object obj, String methodName,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2) throws Exception {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireMethodNameNonNull(methodName);
        ValidationUtils.requireTypeNonNull(type1, type2);

        return invokeMethod(obj, methodName, new Class<?>[] { type1, type2 }, new Object[] { value1, value2 });
    }

    /**
     * Invoke not static method with the given {@code methodName} and exactly three arguments ({@code type1}/{@code value1} for the first argument,
     * {@code type2}/{@code value2} for the second argument and {@code type3}/{@code value3} for the third argument) for the given {@code obj}.<br>
     * Method with this {@code methodName} could be as in the given class itself as in any it's parents. The first found method is taken.
     *
     * @param obj        not {@literal null} object instance
     * @param methodName not {@literal null} method name
     * @param type1      not {@literal null} type of the 1st argument
     * @param value1     value of the 1st argument
     * @param type2      not {@literal null} type of the 2nd argument
     * @param value2     value of the 2nd argument
     * @param type3      not {@literal null} type of the 3rd argument
     * @param value3     value of the 3rd argument
     * @param <T>        type of the method's return value
     * @return return value of the method
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeMethod(Object obj, String methodName,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2,
            Class<?> type3, Object value3) throws Exception {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireMethodNameNonNull(methodName);
        ValidationUtils.requireTypeNonNull(type1, type2, type3);

        return invokeMethod(obj, methodName, new Class<?>[] { type1, type2, type3 }, new Object[] { value1, value2, value3 });
    }

    /**
     * Invoke not static {@code method} with arguments' {@code values} for the given {@code obj}.
     *
     * @param obj    not {@literal null} object instance
     * @param method not {@literal null} method
     * @param values values of the arguments
     * @param <T>    type of the method's return value
     * @return return value of the method
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeMethod(Object obj, Method method, Object... values) throws Exception {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireMethodNonNull(method);

        return InvokeUtils.invokeFunction(method, m -> (T)m.invoke(obj, values));
    }

    /**
     * Invoke not static method with the given {@code methodName} and arguments of given {@code types}/{@code values} for the given {@code
     * obj}. The array's length {@code types} and {@code value} should be equal.<br>
     * Method with this {@code methodName} could be as in the given class itself as in any it's parents. The first found method is taken.
     *
     * @param obj        not {@literal null} object instance
     * @param methodName not {@literal null} method name
     * @param types      types of the arguments (each item should not be {@literal null})
     * @param values     values of the arguments (array length should match with {@code types})
     * @param <T>        type of the method's return value
     * @return return value of the method
     * @throws Exception in case if any problem; check type for details
     */
    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static <T> T invokeMethod(Object obj, String methodName, Class<?>[] types, Object[] values) throws Exception {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireMethodNameNonNull(methodName);
        ValidationUtils.requireLengthMatch(types, values);
        ValidationUtils.requireValuesNotNull(types);

        return invokeMethod(obj, getMethod(obj.getClass(), methodName, types), values);
    }

    /**
     * Invoke static method with the given {@code methodName} for the given {@code cls}<br>
     * Method with this {@code methodName} could be as in the given class itself as in any it's parents. The first found method is taken.
     *
     * @param cls        not {@literal null} class object
     * @param methodName not {@literal null} method name
     * @param <T>        type of the method's return value
     * @return return value of the method
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeStaticMethod(Class<?> cls, String methodName) throws Exception {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireMethodNameNonNull(methodName);

        return invokeStaticMethod(cls, methodName, (Class<?>[])null, null);
    }

    /**
     * Invoke static method with the given {@code methodName} and exactly one argument ({@code type}/{@code value}) for the given {@code cls}.<br>
     * Method with this {@code methodName} could be as in the given class itself as in any it's parents. The first found method is taken.
     *
     * @param cls        not {@literal null} class object
     * @param methodName not {@literal null} method name
     * @param type       not {@literal null} type of the argument
     * @param value      value of the argument
     * @param <T>        type of the method's return value
     * @return return value of the method
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeStaticMethod(Class<?> cls, String methodName, Class<?> type, Object value) throws Exception {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireMethodNameNonNull(methodName);
        ValidationUtils.requireTypeNonNull(type);

        return invokeStaticMethod(cls, methodName, new Class<?>[] { type }, new Object[] { value });
    }

    /**
     * Invoke static method with the given {@code methodName} and exactly two arguments ({@code type1}/{@code value1} for the first argument and
     * {@code type2}/{@code value2} for the second argument) for the given {@code cls}.<br>
     * Method with this {@code methodName} could be as in the given class itself as in any it's parents. The first found method is taken.
     *
     * @param cls        not {@literal null} class object
     * @param methodName not {@literal null} method name
     * @param type1      not {@literal null} type of the 1st argument
     * @param value1     value of the 1st argument
     * @param type2      not {@literal null} type of the 2nd argument
     * @param value2     value of the 2nd argument
     * @param <T>        type of the method's return value
     * @return return value of the method
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeStaticMethod(Class<?> cls, String methodName,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2) throws Exception {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireMethodNameNonNull(methodName);
        ValidationUtils.requireTypeNonNull(type1, type2);

        return invokeStaticMethod(cls, methodName, new Class<?>[] { type1, type2 }, new Object[] { value1, value2 });
    }

    /**
     * Invoke static method with the given {@code methodName} and exactly three arguments ({@code type1}/{@code value1} for the first argument,
     * {@code type2}/{@code value2} for the second argument and {@code type3}/{@code value3} for the third argument) for the given {@code cls}.<br>
     * Method with this {@code methodName} could be as in the given class itself as in any it's parents. The first found method is taken.
     *
     * @param cls        not {@literal null} class object
     * @param methodName not {@literal null} method name
     * @param type1      not {@literal null} type of the 1st argument
     * @param value1     value of the 1st argument
     * @param type2      not {@literal null} type of the 2nd argument
     * @param value2     value of the 2nd argument
     * @param type3      not {@literal null} type of the 3rd argument
     * @param value3     value of the 3rd argument
     * @param <T>        type of the method's return value
     * @return return value of the method
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeStaticMethod(Class<?> cls, String methodName,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2,
            Class<?> type3, Object value3) throws Exception {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireMethodNameNonNull(methodName);
        ValidationUtils.requireTypeNonNull(type1, type2, type3);

        return invokeStaticMethod(cls, methodName, new Class<?>[] { type1, type2, type3 }, new Object[] { value1, value2, value3 });
    }

    /**
     * Invoke static {@code method} with arguments' {@code values}.
     *
     * @param method not {@literal null} method
     * @param values values of the arguments
     * @param <T>    type of the method's return value
     * @return return value of the method
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeStaticMethod(Method method, Object... values) throws Exception {
        ValidationUtils.requireMethodNonNull(method);

        return InvokeUtils.invokeFunction(method, m -> (T)m.invoke(null, values));
    }

    /**
     * Invoke static method with the given {@code methodName} and arguments with given {@code types}/{@code values}<br>
     * Method with this {@code methodName} could be as in the given class itself as in any it's parents. The first found method is taken.
     *
     * @param cls        not {@literal null} class object
     * @param methodName not {@literal null} method name
     * @param types      types of the arguments
     * @param values     values of the arguments
     * @param <T>        type of the method's return value
     * @return return value of the method
     * @throws Exception in case if any problem; check type for details
     */
    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static <T> T invokeStaticMethod(Class<?> cls, String methodName, Class<?>[] types, Object[] values) throws Exception {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireMethodNameNonNull(methodName);

        return invokeStaticMethod(getMethod(cls, methodName, types), values);
    }

    /**
     * Retrieve method with given {@code methodName} for the given {@code cls}. If method was not found in the given class, then parent class will be
     * used to find the method and etc.
     *
     * @param cls        not {@literal null} class object
     * @param methodName not {@literal null} method name
     * @return not {@literal null} method
     * @throws NoSuchFieldException in case of filed was not found
     */
    private static Method getMethod(Class<?> cls, String methodName, Class<?>... types) throws NoSuchMethodException {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireMethodNameNonNull(methodName);

        Method method = null;
        Class<?> clazz = cls;

        while (method == null && clazz != null) {
            try {
                method = clazz.getDeclaredMethod(methodName, types);
            } catch(NoSuchMethodException ignored) {
                clazz = clazz.getSuperclass();
            }
        }

        if (method == null)
            throw new NoSuchElementException(String.format("Method '%s' with arguments '%s' was not found in class '%s' and it's parents",
                    methodName, Arrays.toString(types), cls));

        return method;
    }

    /**
     * Retrieve return value's {@link Class} of the given {@code method}.
     *
     * @param method not {@literal null} method
     * @return not {@literal null} {@link Class} object
     */
    public static Class<?> getReturnType(Method method) {
        ValidationUtils.requireMethodNonNull(method);

        return method.getReturnType();
    }

    /**
     * Retrieve return value's {@link Class} of the given {@code method} if the method is not {@literal null} or {@code def} otherwise.
     *
     * @param method method
     * @param def    default {@link Class}
     * @return {@link Class} object
     */
    public static Class<?> getReturnType(Method method, Class<?> def) {
        return method == null ? def : method.getReturnType();
    }

    private MethodUtils() {
    }

}
