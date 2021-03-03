package ru.olegcherednik.utils.reflection;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
public final class FieldUtils {

    public static <T> T getFieldValue(Object obj, Field field) throws Exception {
        return InvokeUtils.invokeFunction(field, f -> (T)field.get(obj));
    }

    public static <T> T getFieldValue(Object obj, String fieldName) throws Exception {
        return getFieldValue(obj, getField(obj.getClass(), fieldName));
    }

    public static <T> T getStaticFieldValue(Field field) throws Exception {
        return InvokeUtils.invokeFunction(field, f -> (T)f.get(field.getDeclaringClass()));
    }

    public static <T> T getStaticFieldValue(Class<?> cls, String fieldName) throws Exception {
        return getStaticFieldValue(getField(cls, fieldName));
    }

    public static void setFieldValue(Object obj, Field field, Object value) throws Exception {
        InvokeUtils.invokeConsumer(field, f -> setFieldValueImpl(f, obj, value));
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        setFieldValue(obj, getField(obj.getClass(), fieldName), value);
    }

    public static void setFieldValue(Object obj, Field field, InvokeUtils.Consumer<Field> setValueTask) throws Exception {
        InvokeUtils.invokeConsumer(field, setValueTask);
    }

    public static void setFieldValue(Object obj, String fieldName, InvokeUtils.Consumer<Field> setValueTask) throws Exception {
        setFieldValue(obj, getField(obj.getClass(), fieldName), setValueTask);
    }

    public static void setStaticFieldValue(Field field, Object value) throws Exception {
        InvokeUtils.invokeConsumer(field, f -> setFieldValueImpl(f, null, value));
    }

    public static void setStaticFieldValue(Class<?> cls, String fieldName, Object value) throws Exception {
        setStaticFieldValue(getField(cls, fieldName), value);
    }

    public static void setStaticFieldValue(Field field, InvokeUtils.Consumer<Field> setValueTask) throws Exception {
        InvokeUtils.invokeConsumer(field, setValueTask);
    }

    public static void setStaticFieldValue(Class<?> cls, String fieldName, InvokeUtils.Consumer<Field> setValueTask) throws Exception {
        setStaticFieldValue(getField(cls, fieldName), setValueTask);
    }

    private static Field getField(Class<?> cls, String fieldName) throws NoSuchFieldException {
        Field field = null;
        Class<?> clazz = cls;

        while (field == null && clazz != null) {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch(NoSuchFieldException ignored) {
                clazz = clazz.getSuperclass();
            }
        }

        return Optional.ofNullable(field).orElseThrow(NoSuchElementException::new);
    }

    private static void setFieldValueImpl(Field field, Object obj, Object value) throws IllegalAccessException {
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

    public static Class<?> getType(Field field) {
        return field.getType();
    }

    public static Class<?> getType(Field field, Class<?> def) {
        return field == null ? def : field.getType();
    }

    private FieldUtils() { }

}
