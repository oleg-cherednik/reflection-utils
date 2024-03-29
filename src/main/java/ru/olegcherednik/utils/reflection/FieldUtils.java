/*
 * Copyright 2021 Oleg Cherednik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.olegcherednik.utils.reflection;

import ru.olegcherednik.utils.reflection.exceptions.NoSuchFieldException;
import ru.olegcherednik.utils.reflection.exceptions.ReflectionUtilsException;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Utils for working with {@link Field} using reflection.
 *
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
public final class FieldUtils {

    /**
     * Get value of the given non-static {@code field} for the given {@code obj}.
     *
     * @param obj   not {@literal null} object instance
     * @param field not {@literal null} field
     * @param <T>   type of the field
     * @return value of the field
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     */
    public static <T> T getFieldValue(Object obj, Field field) {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireFieldNonNull(field);

        return AccessibleObjectUtils.invokeFunction(field, f -> (T)field.get(obj));
    }

    /**
     * Get value of the field with given non-static {@code fieldName} for the given {@code obj}.<br>
     * Field with this {@code fieldName} could be as in the given class itself as in any it's parents. The first found field is taken.
     *
     * @param obj       not {@literal null} object instance
     * @param fieldName not {@literal null} field name
     * @param <T>       type of the field
     * @return value of the field
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     * @throws NoSuchFieldException     in case of filed with {@code fieldName} was not found
     */
    public static <T> T getFieldValue(Object obj, String fieldName) {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireFieldNameNonNull(fieldName);

        return getFieldValue(obj, getField(obj.getClass(), fieldName));
    }

    /**
     * Get value of the given static {@literal field}. {@link Field#getDeclaringClass()} is used to get field's class.
     *
     * @param field not {@literal null} field
     * @param <T>   type of the field
     * @return value of the static field
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     */
    public static <T> T getStaticFieldValue(Field field) {
        ValidationUtils.requireFieldNonNull(field);

        return AccessibleObjectUtils.invokeFunction(field, f -> (T)f.get(field.getDeclaringClass()));
    }

    /**
     * Get value of the static field with given {@code fieldName} for the given {@code cls}.<br>
     * Field with this {@code fieldName} could be as in the given class itself as in any it's parents. The first found field is taken.
     *
     * @param cls       not {@literal null} class object
     * @param fieldName not {@literal null} field name
     * @param <T>       type of the field
     * @return value of the static field
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     * @throws NoSuchFieldException     in case of filed with {@code fieldName} was not found
     */
    public static <T> T getStaticFieldValue(Class<?> cls, String fieldName) {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireFieldNameNonNull(fieldName);

        return getStaticFieldValue(getField(cls, fieldName));
    }

    /**
     * Set given {@code value} to the non-static {@code field} for the given {@code obj}.
     *
     * @param obj   not {@literal null} object instance
     * @param field not {@literal null} field
     * @param value new value of the field
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     */
    public static void setFieldValue(Object obj, Field field, Object value) {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireFieldNonNull(field);

        AccessibleObjectUtils.invokeConsumer(field, f -> setFieldValueImpl(f, obj, value));
    }

    /**
     * Set given {@code value} of the non-static field with given {@code fieldName} for the given {@code obj}.<br>
     * Field with this {@code fieldName} could be as in the given class itself as in any it's parents. The first found field is taken.
     *
     * @param obj       not {@literal null} object instance
     * @param fieldName not {@literal null} field name
     * @param value     new value of the field
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     * @throws NoSuchFieldException     in case of filed with {@code fieldName} was not found
     */
    public static void setFieldValue(Object obj, String fieldName, Object value) {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireFieldNameNonNull(fieldName);

        setFieldValue(obj, getField(obj.getClass(), fieldName), value);
    }

    /**
     * Call given {@link Consumer} for the not static field {@code field}.
     *
     * @param field not {@literal null} field
     * @param task  not {@literal null} consumer is called for the field
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     */
    public static void setFieldValue(Field field, Consumer<Field> task) {
        ValidationUtils.requireFieldNonNull(field);
        ValidationUtils.requireTaskNonNull(task);

        AccessibleObjectUtils.invokeConsumer(field, task);
    }

    /**
     * Call given {@link Consumer} for the not static field with given {@code fieldName} for the given {@code obj}.<br>
     * Field with this {@code fieldName} could be as in the given class itself as in any it's parents. The first found field is taken.
     *
     * @param obj       not {@literal null} object instance
     * @param fieldName not {@literal null} field name
     * @param task      not {@literal null} consumer is called for the field
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     * @throws NoSuchFieldException     in case of filed with {@code fieldName} was not found
     */
    public static void setFieldValue(Object obj, String fieldName, Consumer<Field> task) {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireFieldNameNonNull(fieldName);
        ValidationUtils.requireTaskNonNull(task);

        setFieldValue(getField(obj.getClass(), fieldName), task);
    }

    /**
     * Set given {@code value} to the given static {@code field}.
     *
     * @param field not {@literal null} field
     * @param value new value of the field
     * @throws NullPointerException   in case of any of required parameters is {@literal null}
     * @throws RuntimeException       in case if any other problem; checked exception is wrapped with runtime exception as well
     */
    public static void setStaticFieldValue(Field field, Object value) {
        ValidationUtils.requireFieldNonNull(field);

        AccessibleObjectUtils.invokeConsumer(field, f -> setFieldValueImpl(f, null, value));
    }

    /**
     * Set given {@code value} to the static field with given {@code fieldName} for the given {@code cls}.<br>
     * Field with this {@code fieldName} could be as in the given class itself as in any it's parents. The first found field is taken.
     *
     * @param cls       not {@literal null} class object
     * @param fieldName not {@literal null} field name
     * @param value     new value of the field
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     * @throws NoSuchFieldException     in case of filed with {@code fieldName} was not found
     */
    public static void setStaticFieldValue(Class<?> cls, String fieldName, Object value) {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireFieldNameNonNull(fieldName);

        setStaticFieldValue(getField(cls, fieldName), value);
    }

    /**
     * Call given {@link Consumer} for the static field {@code field}.
     *
     * @param field not {@literal null} field
     * @param task  not {@literal null} consumer is called for the field
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     */
    public static void setStaticFieldValue(Field field, Consumer<Field> task) {
        ValidationUtils.requireFieldNonNull(field);
        ValidationUtils.requireTaskNonNull(task);

        AccessibleObjectUtils.invokeConsumer(field, task);
    }

    /**
     * Call given {@link Consumer} for the static field with given {@code fieldName} for the given {@code cls}.<br>
     * Field with this {@code fieldName} could be as in the given class itself as in any it's parents. The first found field is taken.
     *
     * @param cls          not {@literal null} class object
     * @param fieldName    not {@literal null} field name
     * @param setValueTask not {@literal null} consumer is called for the field
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     * @throws NoSuchFieldException     in case of filed with {@code fieldName} was not found
     */
    public static void setStaticFieldValue(Class<?> cls, String fieldName, Consumer<Field> setValueTask) {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireFieldNameNonNull(fieldName);
        ValidationUtils.requireTaskNonNull(setValueTask);

        setStaticFieldValue(getField(cls, fieldName), setValueTask);
    }

    /**
     * Retrieve filed with given {@code fieldName} for the given {@code cls}. If field was not found in the given class, then parent class will be
     * used to find the field and etc.
     *
     * @param cls       not {@literal null} class object
     * @param fieldName not {@literal null} field name
     * @return not {@literal null} filed
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     * @throws NoSuchFieldException     in case of filed was not found
     */
    private static Field getField(Class<?> cls, String fieldName) {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireFieldNameNonNull(fieldName);

        Field field = null;
        Class<?> clazz = cls;

        while (field == null && clazz != null) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (java.lang.NoSuchFieldException ignored) {
                clazz = clazz.getSuperclass();
            }
        }

        return Optional.ofNullable(field).orElseThrow(() -> new NoSuchFieldException(cls, fieldName));
    }

    /**
     * Set given {@code value} for the {@code field}. If {@code obj} is {@literal null}, then {@code field} is static; otherwise it is not static and
     * {@code obj} instance is used to set the field's value.
     *
     * @param field not {@literal null} field
     * @param obj   {@literal null} for static field or not {@literal null} for not static field
     * @param value new value of the field
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws RuntimeException         in case if any other problem
     * @throws IllegalAccessException   in case of value cannot be set to the field
     */
    private static void setFieldValueImpl(Field field, Object obj, Object value) throws IllegalAccessException {
        ValidationUtils.requireFieldNonNull(field);

        if (field.getType().isAssignableFrom(int.class))
            field.setInt(obj, ((Number)value).intValue());
        else if (field.getType().isAssignableFrom(boolean.class))
            field.setBoolean(obj, (boolean)value);
        else if (field.getType().isAssignableFrom(byte.class))
            field.setByte(obj, ((Number)value).byteValue());
        else if (field.getType().isAssignableFrom(char.class))
            field.setChar(obj, (char)value);
        else if (field.getType().isAssignableFrom(double.class))
            field.setDouble(obj, ((Number)value).doubleValue());
        else if (field.getType().isAssignableFrom(long.class))
            field.setLong(obj, ((Number)value).longValue());
        else if (field.getType().isAssignableFrom(short.class))
            field.setShort(obj, ((Number)value).shortValue());
        else if (field.getType().isAssignableFrom(float.class))
            field.setFloat(obj, ((Number)value).floatValue());
        else
            field.set(obj, value);
    }

    /**
     * Retrieve value's {@link Class} of the given {@code field}.
     *
     * @param field not {@literal null} field
     * @return not {@literal null} {@link Class} object
     * @throws NullPointerException in case of any of required parameters is {@literal null}
     */
    public static Class<?> getType(Field field) {
        ValidationUtils.requireFieldNonNull(field);

        return field.getType();
    }

    /**
     * Retrieve value's {@link Class} of the given {@code field} if the field is not {@literal null} or {@code def} otherwise.
     *
     * @param field field
     * @param def   default {@link Class}
     * @return {@link Class} object
     */
    public static Class<?> getType(Field field, Class<?> def) {
        return field == null ? def : field.getType();
    }

    private FieldUtils() { }

}
