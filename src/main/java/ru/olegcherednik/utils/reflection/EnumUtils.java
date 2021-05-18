/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package ru.olegcherednik.utils.reflection;

import ru.olegcherednik.utils.reflection.exceptions.ReflectionUtilsException;
import sun.misc.Unsafe;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

/**
 * Utils for working with {@link Enum} using reflection.
 *
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
public final class EnumUtils {

    /**
     * Add new constant with given {@code constantName} to the given enum {@code cls}.
     *
     * @param cls          not {@literal null} enum class object
     * @param constantName not {@literal null} enum's constant name
     * @param <T>          enum class type
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     */
    public static <T extends Enum<?>> void addConstant(Class<T> cls, String constantName) {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireConstantNameNonNull(constantName);

        addConstant(cls, constantName, (Consumer<T>)Consumer.NULL);
    }

    /**
     * Add new constant with given {@code constantName} to the given enum {@code cls} and call given {@code setExtraFieldTask} consumer to set
     * additional properties for the new created enum's constant before it will be added to the enum.
     *
     * @param cls               not {@literal null} enum class object
     * @param constantName      not {@literal null} enum's constant name
     * @param setExtraFieldTask not {@literal null} consumer is called for the new constant
     * @param <T>               enum class type
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     * @throws IllegalArgumentException in case of enums contains constant with given name
     */
    @SuppressWarnings("UseOfSunClasses")
    public static <T extends Enum<?>> void addConstant(Class<T> cls, String constantName, Consumer<T> setExtraFieldTask) {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireConstantNameNonNull(constantName);
        ValidationUtils.requireSetExtraFieldTaskNonNull(setExtraFieldTask);
        ValidationUtils.requireConstantNotExist(cls, constantName);

        try {
            Constructor<?> constructor = Unsafe.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Unsafe unsafe = (Unsafe)constructor.newInstance();
            T enumValue = (T)unsafe.allocateInstance(cls);

            setEnumField("name", enumValue, constantName);
            setEnumField("ordinal", enumValue, cls.getEnumConstants().length);

            setExtraFieldTask.accept(enumValue);

            FieldUtils.setStaticFieldValue(cls, "$VALUES", field -> {
                T[] oldValues = (T[])field.get(null);
                T[] newValues = (T[])Array.newInstance(cls, oldValues.length + 1);
                System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
                newValues[oldValues.length] = enumValue;
                field.set(null, newValues);
            });

            setClassField("enumConstants", cls);
            setClassField("enumConstantDirectory", cls);
        } catch (Exception e) {
            throw new ReflectionUtilsException(e);
        }
    }

    private static <T extends Enum<?>> void setEnumField(String fieldName, T obj, Object value) {
        FieldUtils.setStaticFieldValue(Enum.class, fieldName, field -> field.set(obj, value));
    }

    private static <T extends Enum<?>> void setClassField(String fieldName, Class<T> cls) {
        FieldUtils.setStaticFieldValue(Class.class, fieldName, field -> field.set(cls, null));
    }

    private EnumUtils() { }

}
