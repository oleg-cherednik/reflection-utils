/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package ru.olegcherednik.utils.reflection;

import ru.olegcherednik.utils.reflection.exceptions.ClassNotFoundException;
import ru.olegcherednik.utils.reflection.exceptions.NoSuchConstructorException;
import ru.olegcherednik.utils.reflection.exceptions.ReflectionUtilsException;

import java.lang.reflect.Constructor;

/**
 * Utils for working with {@link Constructor} using reflection.
 *
 * @author Oleg Cherednik
 * @since 27.02.2021
 */
public final class ConstructorUtils {

    /**
     * Invoke a constructor with no arguments for the given {@code cls}.
     *
     * @param cls not {@literal null} class object
     * @param <T> type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws NullPointerException       in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException   in case of any checked exception is thrown
     * @throws RuntimeException           in case if any other problem
     * @throws NoSuchConstructorException in case of constructor was not found
     */
    public static <T> T invokeConstructor(Class<T> cls) {
        ValidationUtils.requireClsNonNull(cls);

        return invokeConstructor(getConstructor(cls));
    }

    /**
     * Invoke a constructor with exactly one argument ({@code type}/{@code value}) for the given {@code cls}.<br>
     *
     * @param cls   not {@literal null} class object
     * @param type  not {@literal null} type of the argument
     * @param value value of the argument
     * @param <T>   type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws NullPointerException       in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException   in case of any checked exception is thrown
     * @throws RuntimeException           in case if any other problem
     * @throws NoSuchConstructorException in case of constructor was not found
     */
    public static <T> T invokeConstructor(Class<T> cls, Class<?> type, Object value) {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireTypeNonNull(type);

        return invokeConstructor(getConstructor(cls, type), value);
    }

    /**
     * Invoke a constructor with exactly two arguments ({@code type1}/{@code value1} for the first argument and {@code type2}/{@code value2} for the
     * second argument) for the given {@code cls}.
     *
     * @param cls    not {@literal null} class object
     * @param type1  not {@literal null} type of the 1st argument
     * @param value1 value of the 1st argument
     * @param type2  not {@literal null} type of the 2nd argument
     * @param value2 value of the 2nd argument
     * @param <T>    type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws NullPointerException       in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException   in case of any checked exception is thrown
     * @throws RuntimeException           in case if any other problem
     * @throws NoSuchConstructorException in case of constructor was not found
     */
    public static <T> T invokeConstructor(Class<T> cls,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2) {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireTypeNonNull(type1, type2);

        return invokeConstructor(getConstructor(cls, type1, type2), value1, value2);
    }

    /**
     * Invoke a constructor with exactly three arguments ({@code type1}/{@code value1} for the first argument, {@code type2}/{@code value2} for the
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
     * @throws NullPointerException       in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException   in case of any checked exception is thrown
     * @throws RuntimeException           in case if any other problem
     * @throws NoSuchConstructorException in case of constructor was not found
     */
    public static <T> T invokeConstructor(Class<T> cls,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2,
            Class<?> type3, Object value3) {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireTypeNonNull(type1, type2, type3);

        return invokeConstructor(getConstructor(cls, type1, type2, type3), value1, value2, value3);
    }

    /**
     * Invoke a constructor with arguments of given {@code types}/{@code values} for the given {@code cls}. The array's length {@code types} and
     * {@code value} should be equal.
     *
     * @param cls    not {@literal null} class object
     * @param types  types of the arguments (each item should not be {@literal null})
     * @param values values of the arguments (array length should match with {@code types})
     * @param <T>    type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws NullPointerException       in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException   in case of any checked exception is thrown
     * @throws RuntimeException           in case if any other problem
     * @throws NoSuchConstructorException in case of constructor was not found
     */
    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static <T> T invokeConstructor(Class<T> cls, Class<?>[] types, Object[] values) {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireLengthMatch(types, values);
        ValidationUtils.requireTypeNonNull(types);

        return invokeConstructor(getConstructor(cls, types), values);
    }

    /**
     * Invoke a constructor with no arguments for the class with given {@code className}.
     *
     * @param className not {@literal null} class name
     * @param <T>       type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws NullPointerException       in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException   in case of any checked exception is thrown
     * @throws RuntimeException           in case if any other problem
     * @throws NoSuchConstructorException in case of constructor was not found
     * @throws ClassNotFoundException     in case of class was not found
     */
    public static <T> T invokeConstructor(String className) {
        ValidationUtils.requireClassNameNonNull(className);

        return invokeConstructor(getClass(className));
    }

    /**
     * Invoke a constructor with exactly one argument ({@code type}/{@code value}) for the class with given {@code className}.
     *
     * @param className not {@literal null} class name
     * @param type      not {@literal null} type of the argument
     * @param value     value of the argument
     * @param <T>       type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws NullPointerException       in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException   in case of any checked exception is thrown
     * @throws RuntimeException           in case if any other problem
     * @throws NoSuchConstructorException in case of constructor was not found
     * @throws ClassNotFoundException     in case of class was not found
     */
    public static <T> T invokeConstructor(String className, Class<?> type, Object value) {
        ValidationUtils.requireClassNameNonNull(className);
        ValidationUtils.requireTypeNonNull(type);

        return invokeConstructor(getClass(className), type, value);
    }

    /**
     * Invoke a constructor with exactly two arguments ({@code type1}/{@code value1} for the first argument and {@code type2}/{@code value2} for the
     * second argument) for the class with given {@code className}.
     *
     * @param className not {@literal null} class name
     * @param type1     not {@literal null} type of the 1st argument
     * @param value1    value of the 1st argument
     * @param type2     not {@literal null} type of the 2nd argument
     * @param value2    value of the 2nd argument
     * @param <T>       type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     * @throws ClassNotFoundException   in case of class was not found
     */
    public static <T> T invokeConstructor(String className,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2) {
        ValidationUtils.requireClassNameNonNull(className);
        ValidationUtils.requireTypeNonNull(type1, type2);

        return invokeConstructor(getClass(className), type1, value1, type2, value2);
    }

    /**
     * Invoke a constructor with exactly three arguments ({@code type1}/{@code value1} for the first argument, {@code type2}/{@code value2} for the
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
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     * @throws ClassNotFoundException   in case of class was not found
     */
    public static <T> T invokeConstructor(String className,
            Class<?> type1, Object value1,
            Class<?> type2, Object value2,
            Class<?> type3, Object value3) {
        ValidationUtils.requireClassNameNonNull(className);
        ValidationUtils.requireTypeNonNull(type1, type2, type3);

        return invokeConstructor(getClass(className), type1, value1, type2, value2, type3, value3);
    }

    /**
     * Invoke a constructor with arguments of given {@code types}/{@code values} for the class with given {@code className}. The array's length
     * {@code types} and {@code value} should be equal.
     *
     * @param className not {@literal null} class name
     * @param types     types of the arguments (each item should not be {@literal null})
     * @param values    values of the arguments (array length should match with {@code types})
     * @param <T>       type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     * @throws ClassNotFoundException   in case of class was not found
     */
    @SuppressWarnings("MethodCanBeVariableArityMethod")
    public static <T> T invokeConstructor(String className, Class<?>[] types, Object[] values) {
        ValidationUtils.requireClassNameNonNull(className);
        ValidationUtils.requireLengthMatch(types, values);
        ValidationUtils.requireTypeNonNull(types);

        return invokeConstructor(getClass(className), types, values);
    }

    /**
     * Invoke a constructor {@code constructor} with arguments' {@code values}.
     *
     * @param constructor not {@literal null} constructor
     * @param values      values of the arguments (array length should match with constructor's arguments amount)
     * @param <T>         type of the class that contains the constructor
     * @return not {@literal null} instance of created type
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     */
    public static <T> T invokeConstructor(Constructor<T> constructor, Object... values) {
        ValidationUtils.requireConstructorNonNull(constructor);
        ValidationUtils.requireLengthMatch(constructor.getParameterTypes(), values);

        return AccessibleObjectUtils.invokeFunction(constructor, c -> c.newInstance(values));
    }

    /**
     * Retrieve constructor with with arguments of type {@code types}.
     *
     * @param cls   not {@literal null} class object
     * @param types types of the arguments (each item should not be {@literal null})
     * @param <T>   type of the class that contains the constructor
     * @return not {@literal null} method
     * @throws NullPointerException       in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException   in case of any checked exception is thrown
     * @throws RuntimeException           in case if any other problem
     * @throws NoSuchConstructorException in case of constructor was not found
     */
    private static <T> Constructor<T> getConstructor(Class<T> cls, Class<?>... types) {
        ValidationUtils.requireClsNonNull(cls);

        try {
            return cls.getDeclaredConstructor(types);
        } catch (NoSuchMethodException e) {
            throw new NoSuchConstructorException(cls, types);
        }
    }

    /**
     * Retrieve {@link Class} instance with given {@code className}.
     *
     * @param className not {@literal null} class name
     * @param <T>       type of the class that contains the constructor
     * @return not {@literal null} class
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     * @throws ClassNotFoundException   in case of class was not found
     */
    private static <T> Class<T> getClass(String className) {
        ValidationUtils.requireClassNameNonNull(className);

        try {
            return (Class<T>)Class.forName(className);
        } catch (java.lang.ClassNotFoundException e) {
            throw new ClassNotFoundException(className);
        }
    }

    private ConstructorUtils() {}

}
