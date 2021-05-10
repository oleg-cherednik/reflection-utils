package ru.olegcherednik.utils.reflection;

import java.lang.reflect.Field;

/**
 * Utils for working with {@link Field} using reflection.
 *
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
public final class FieldUtils {

    /**
     * Get value of the given non static {@code field} for the given {@code obj}.
     *
     * @param obj   not {@literal null} object instance
     * @param field not {@literal null} field
     * @param <T>   type of the field
     * @return value of the field
     * @throws Exception in case if any problem; check type for details
     */
    public static <T> T getFieldValue(Object obj, Field field) throws Exception {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireFieldNonNull(field);

        return InvokeUtils.invokeFunction(field, f -> (T)field.get(obj));
    }

    /**
     * Get value of the field with given non static {@code fieldName} for the given {@code obj}.<br>
     * Field with this {@code fieldName} could be as in the given class itself as in any it's parents. The first found field is taken.
     *
     * @param obj       not {@literal null} object instance
     * @param fieldName not {@literal null} field name
     * @param <T>       type of the field
     * @return value of the field
     * @throws Exception in case if any problem; check exception type for details
     */
    public static <T> T getFieldValue(Object obj, String fieldName) throws Exception {
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
     * @throws Exception in case if any problem; check exception type for details
     */
    public static <T> T getStaticFieldValue(Field field) throws Exception {
        ValidationUtils.requireFieldNonNull(field);

        return InvokeUtils.invokeFunction(field, f -> (T)f.get(field.getDeclaringClass()));
    }

    /**
     * Get value of the static field with given {@code fieldName} for the given {@code cls}.<br>
     * Field with this {@code fieldName} could be as in the given class itself as in any it's parents. The first found field is taken.
     *
     * @param cls       not {@literal null} class object
     * @param fieldName not {@literal null} field name
     * @param <T>       type of the field
     * @return value of the static field
     * @throws Exception in case if any problem; check exception type for details
     */
    public static <T> T getStaticFieldValue(Class<?> cls, String fieldName) throws Exception {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireFieldNameNonNull(fieldName);

        return getStaticFieldValue(getField(cls, fieldName));
    }

    /**
     * Set given {@code value} of the non static {@code field} for the given {@code obj}.
     *
     * @param obj   not {@literal null} object instance
     * @param field not {@literal null} field
     * @param value new value of the field
     * @throws Exception in case if any problem; check exception type for details
     */
    public static void setFieldValue(Object obj, Field field, Object value) throws Exception {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireFieldNonNull(field);

        InvokeUtils.invokeConsumer(field, f -> setFieldValueImpl(f, obj, value));
    }

    /**
     * Set given {@code value} of the non static field with given {@code fieldName} for the given {@code obj}.<br>
     * Field with this {@code fieldName} could be as in the given class itself as in any it's parents. The first found field is taken.
     *
     * @param obj       not {@literal null} object instance
     * @param fieldName not {@literal null} field name
     * @param value     new value of the field
     * @throws Exception in case if any problem; check exception type for details
     */
    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireFieldNameNonNull(fieldName);

        setFieldValue(obj, getField(obj.getClass(), fieldName), value);
    }

    /**
     * Call given {@link InvokeUtils.Consumer} for the not static field {@code field} for the give {@code obj}.
     *
     * @param obj          not {@literal null} object instance
     * @param field        not {@literal null} field
     * @param setValueTask not {@literal null} consumer is called for the field
     * @throws Exception in case if any problem; check exception type for details
     */
    public static void setFieldValue(Object obj, Field field, InvokeUtils.Consumer<Field> setValueTask) throws Exception {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireFieldNonNull(field);
        ValidationUtils.requireSetValueTaskNonNull(setValueTask);

        InvokeUtils.invokeConsumer(field, setValueTask);
    }

    /**
     * Call given {@link InvokeUtils.Consumer} for the not static field with given {@code fieldName} for the given {@code obj}.<br>
     * Field with this {@code fieldName} could be as in the given class itself as in any it's parents. The first found field is taken.
     *
     * @param obj          not {@literal null} object instance
     * @param fieldName    not {@literal null} field name
     * @param setValueTask not {@literal null} consumer is called for the field
     * @throws Exception in case if any problem; check exception type for details
     */
    public static void setFieldValue(Object obj, String fieldName, InvokeUtils.Consumer<Field> setValueTask) throws Exception {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireFieldNameNonNull(fieldName);
        ValidationUtils.requireSetValueTaskNonNull(setValueTask);

        setFieldValue(obj, getField(obj.getClass(), fieldName), setValueTask);
    }

    /**
     * Set given {@code value} for the given static {@code field}.
     *
     * @param field not {@literal null} field
     * @param value new value of the field
     * @throws Exception in case if any problem; check exception type for details
     */
    public static void setStaticFieldValue(Field field, Object value) throws Exception {
        ValidationUtils.requireFieldNonNull(field);

        InvokeUtils.invokeConsumer(field, f -> setFieldValueImpl(f, null, value));
    }

    /**
     * Set given {@code value} for the static field with given {@code fieldName} for the given {@code cls}.<br>
     * Field with this {@code fieldName} could be as in the given class itself as in any it's parents. The first found field is taken.
     *
     * @param cls       not {@literal null} class object
     * @param fieldName not {@literal null} field name
     * @param value     new value of the field
     * @throws Exception in case if any problem; check exception type for details
     */
    public static void setStaticFieldValue(Class<?> cls, String fieldName, Object value) throws Exception {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireFieldNameNonNull(fieldName);

        setStaticFieldValue(getField(cls, fieldName), value);
    }

    /**
     * Call given {@link InvokeUtils.Consumer} for the static field {@code field}.
     *
     * @param field        not {@literal null} field
     * @param setValueTask not {@literal null} consumer is called for the field
     * @throws Exception in case if any problem; check exception type for details
     */
    public static void setStaticFieldValue(Field field, InvokeUtils.Consumer<Field> setValueTask) throws Exception {
        ValidationUtils.requireFieldNonNull(field);
        ValidationUtils.requireSetValueTaskNonNull(setValueTask);

        InvokeUtils.invokeConsumer(field, setValueTask);
    }

    /**
     * Call given {@link InvokeUtils.Consumer} for the static field with given {@code fieldName} for the given {@code cls}.<br>
     * Field with this {@code fieldName} could be as in the given class itself as in any it's parents. The first found field is taken.
     *
     * @param cls          not {@literal null} class object
     * @param fieldName    not {@literal null} field name
     * @param setValueTask not {@literal null} consumer is called for the field
     * @throws Exception in case if any problem; check exception type for details
     */
    public static void setStaticFieldValue(Class<?> cls, String fieldName, InvokeUtils.Consumer<Field> setValueTask) throws Exception {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireFieldNameNonNull(fieldName);
        ValidationUtils.requireSetValueTaskNonNull(setValueTask);

        setStaticFieldValue(getField(cls, fieldName), setValueTask);
    }

    /**
     * Retrieve filed with given {@code fieldName} for the given {@code cls}. If field was not found in the given class, then parent class will be
     * used to find the field and etc.
     *
     * @param cls       not {@literal null} class object
     * @param fieldName not {@literal null} field name
     * @return not {@literal null} filed
     * @throws NoSuchFieldException in case of filed was not found
     */
    private static Field getField(Class<?> cls, String fieldName) throws NoSuchFieldException {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireFieldNameNonNull(fieldName);

        Field field = null;
        Class<?> clazz = cls;

        while (field == null && clazz != null) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch(NoSuchFieldException ignored) {
                clazz = clazz.getSuperclass();
            }
        }

        if (field == null)
            throw new NoSuchFieldException(String.format("Field '%s' was not found in class '%s' and it's parents", fieldName, cls));

        return field;
    }

    /**
     * Set given {@code value} for the {@code field}. If {@code obj} is {@literal null}, then {@code field} is static; otherwise it is not static and
     * {@code obj} instance is used to set the field's value.
     *
     * @param field not {@literal null} field
     * @param obj   {@literal null} for static field or not {@literal null} for not static field
     * @param value new value of the field
     * @throws IllegalAccessException in case of value cannot be set to the field
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
