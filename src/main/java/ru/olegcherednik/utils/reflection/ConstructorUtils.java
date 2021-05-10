package ru.olegcherednik.utils.reflection;

import java.lang.reflect.Constructor;

/**
 * Utils for working with {@link Constructor} using reflection.
 *
 * @author Oleg Cherednik
 * @since 27.02.2021
 */
public final class ConstructorUtils {

    /**
     * Invoke constructor with no arguments for the given {@code cls}.
     *
     * @param cls not {@literal null} class object
     * @param <T> type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeConstructor(Class<T> cls) throws Exception {
        ValidationUtils.requireClsNonNull(cls);

        return invokeConstructor(cls.getDeclaredConstructor());
    }

    /**
     * Invoke constructor with exactly one argument ({@code type}/{@code value}) for the given {@code cls}.<br>
     *
     * @param cls   not {@literal null} class object
     * @param type  not {@literal null} type of the argument
     * @param value value of the argument
     * @param <T>   type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeConstructor(Class<T> cls, Class<?> type, Object value) throws Exception {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireTypeNonNull(type);

        return invokeConstructor(cls.getDeclaredConstructor(type), value);
    }

    /**
     * Invoke constructor with exactly two arguments ({@code type1}/{@code value1} for the first argument and {@code type2}/{@code value2} for the
     * second argument) for the given {@code cls}.
     *
     * @param cls    not {@literal null} class object
     * @param type1  not {@literal null} type of the 1st argument
     * @param value1 value of the 1st argument
     * @param type2  not {@literal null} type of the 2nd argument
     * @param value2 value of the 2nd argument
     * @param <T>    type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeConstructor(Class<T> cls,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2) throws Exception {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireTypeNonNull(type1, type2);

        return invokeConstructor(cls.getDeclaredConstructor(type1, type2), value1, value2);
    }

    /**
     * Invoke constructor with exactly three arguments ({@code type1}/{@code value1} for the first argument, {@code type2}/{@code value2} for the
     * second argument and {@code type3}/{@code value3} for the third argument) for the given {@code cls}.
     *
     * @param cls    not {@literal null} class object
     * @param type1  not {@literal null} type of the 1st argument
     * @param value1 value of the 1st argument
     * @param type2  not {@literal null} type of the 2nd argument
     * @param value2 value of the 2nd argument
     * @param type3  not {@literal null} type of the 3rd argument
     * @param value3 value of the 3rd argument
     * @param <T>    type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeConstructor(Class<T> cls,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2,
            Class<?> type3, Object value3) throws Exception {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireTypeNonNull(type1, type2, type3);

        return invokeConstructor(cls.getDeclaredConstructor(type1, type2, type3), value1, value2, value3);
    }

    /**
     * Invoke constructor with arguments of given {@code types}/{@code values} for the given {@code cls}. The array's length {@code types} and
     * {@code value} should be equal.
     *
     * @param cls    not {@literal null} class object
     * @param types  types of the arguments (each item should not be {@literal null})
     * @param values values of the arguments (array length should match with {@code types})
     * @param <T>    type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws Exception in case if any problem; check type for details
     */
    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static <T> T invokeConstructor(Class<T> cls, Class<?>[] types, Object[] values) throws Exception {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireLengthMatch(types, values);
        ValidationUtils.requireValuesNotNull(types);

        return invokeConstructor(cls.getDeclaredConstructor(types), values);
    }

    /**
     * Invoke constructor with no arguments for the class with given {@code className}.
     *
     * @param className not {@literal null} class name
     * @param <T>       type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeConstructor(String className) throws Exception {
        ValidationUtils.requireClassNameNonNull(className);

        return invokeConstructor((Class<T>)Class.forName(className));
    }

    /**
     * Invoke constructor with exactly one argument ({@code type}/{@code value}) for the class with given {@code className}.
     *
     * @param className not {@literal null} class name
     * @param type      not {@literal null} type of the argument
     * @param value     value of the argument
     * @param <T>       type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeConstructor(String className, Class<?> type, Object value) throws Exception {
        ValidationUtils.requireClassNameNonNull(className);
        ValidationUtils.requireTypeNonNull(type);

        return invokeConstructor((Class<T>)Class.forName(className), type, value);
    }

    /**
     * Invoke constructor with exactly two arguments ({@code type1}/{@code value1} for the first argument and {@code type2}/{@code value2} for the
     * second argument) for the class with given {@code className}.
     *
     * @param className not {@literal null} class name
     * @param type1     not {@literal null} type of the 1st argument
     * @param value1    value of the 1st argument
     * @param type2     not {@literal null} type of the 2nd argument
     * @param value2    value of the 2nd argument
     * @param <T>       type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeConstructor(String className,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2) throws Exception {
        ValidationUtils.requireClassNameNonNull(className);
        ValidationUtils.requireTypeNonNull(type1, type2);

        return invokeConstructor((Class<T>)Class.forName(className), type1, value1, type2, value2);
    }

    /**
     * Invoke constructor with exactly three arguments ({@code type1}/{@code value1} for the first argument, {@code type2}/{@code value2} for the
     * second argument and {@code type3}/{@code value3} for the third argument) for the class with given {@code className}.
     *
     * @param className not {@literal null} class name
     * @param type1     not {@literal null} type of the 1st argument
     * @param value1    value of the 1st argument
     * @param type2     not {@literal null} type of the 2nd argument
     * @param value2    value of the 2nd argument
     * @param type3     not {@literal null} type of the 3rd argument
     * @param value3    value of the 3rd argument
     * @param <T>       type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeConstructor(String className,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2,
            Class<?> type3, Object value3) throws Exception {
        ValidationUtils.requireClassNameNonNull(className);
        ValidationUtils.requireTypeNonNull(type1, type2, type3);

        return invokeConstructor((Class<T>)Class.forName(className), type1, value1, type2, value2, type3, value3);
    }

    /**
     * Invoke constructor with arguments of given {@code types}/{@code values} for the class with given {@code className}. The array's length
     * {@code types} and {@code value} should be equal.
     *
     * @param className not {@literal null} class name
     * @param types     types of the arguments (each item should not be {@literal null})
     * @param values    values of the arguments (array length should match with {@code types})
     * @param <T>       type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws Exception in case if any problem; check type for details
     */
    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static <T> T invokeConstructor(String className, Class<?>[] types, Object[] values) throws Exception {
        ValidationUtils.requireClassNameNonNull(className);
        ValidationUtils.requireLengthMatch(types, values);
        ValidationUtils.requireValuesNotNull(types);

        return invokeConstructor((Class<T>)Class.forName(className), types, values);
    }

    /**
     * Invoke constructor {@code constructor} with arguments' {@code values}.
     *
     * @param constructor not {@literal null} constructor
     * @param values      values of the arguments (array length should match with constructor's arguments amount)
     * @param <T>         type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T invokeConstructor(Constructor<T> constructor, Object... values) throws Exception {
        ValidationUtils.requireConstructorNonNull(constructor);
        ValidationUtils.requireLengthMatch(constructor.getParameterTypes(), values);

        return InvokeUtils.invokeFunction(constructor, c -> c.newInstance(values));
    }

    private ConstructorUtils() {}

}
