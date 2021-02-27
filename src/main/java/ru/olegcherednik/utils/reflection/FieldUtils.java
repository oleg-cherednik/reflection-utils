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
        return Invoke.invokeFunction(field, f -> (T)field.get(obj));
    }

    public static <T> T getFieldValue(Object obj, String fieldName) throws Exception {
        return Invoke.invokeFunction(getField(obj.getClass(), fieldName), field -> (T)field.get(obj));
    }

    public static <T> T getStaticFieldValue(Field field) throws Exception {
        return Invoke.invokeFunction(field, f -> (T)f.get(field.getDeclaringClass()));
    }

    public static <T> T getStaticFieldValue(Class<?> cls, String fieldName) throws Exception {
        return Invoke.invokeFunction(getField(cls, fieldName), field -> (T)field.get(cls));
    }

    public static void setFieldValue(Object obj, Field field, Object value) throws Exception {
        Invoke.invokeConsumer(field, f -> setFieldValueImpl(f, obj, value));
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Invoke.invokeConsumer(getField(obj.getClass(), fieldName), field -> setFieldValueImpl(field, obj, value));
    }

    public static void setFieldValue(Object obj, Field field, Invoke.Consumer<Field> setValueTask) throws Exception {
        Invoke.invokeConsumer(field, setValueTask);
    }

    public static void setFieldValue(Object obj, String fieldName, Invoke.Consumer<Field> setValueTask) throws Exception {
        Invoke.invokeConsumer(getField(obj.getClass(), fieldName), setValueTask);
    }

    public static void setStaticFieldValue(Field field, Object value) throws Exception {
        Invoke.invokeConsumer(field, f -> setFieldValueImpl(f, null, value));
    }

    public static void setStaticFieldValue(Class<?> cls, String fieldName, Object value) throws Exception {
        setStaticFieldValue(cls, fieldName, field -> setFieldValueImpl(field, null, value));
    }

    public static void setStaticFieldValue(Field field, Invoke.Consumer<Field> setValueTask) throws Exception {
        Invoke.invokeConsumer(field, setValueTask);
    }

    public static void setStaticFieldValue(Class<?> cls, String fieldName, Invoke.Consumer<Field> setValueTask) throws Exception {
        Invoke.invokeConsumer(getField(cls, fieldName), setValueTask);
    }

    private static Field getField(Class<?> cls, String fieldName) throws NoSuchFieldException {
        Field field = null;

        while (field == null && cls != null) {
            try {
                field = cls.getDeclaredField(fieldName);
            } catch(NoSuchFieldException ignored) {
                cls = cls.getSuperclass();
            }
        }

        return Optional.ofNullable(field).orElseThrow(NoSuchElementException::new);
    }

    public static void setFieldValueImpl(Field field, Object obj, Object value) throws IllegalAccessException {
        if (TypeUtils.isInteger(field.getType()))
            field.setInt(obj, (Integer)value);
        else if (TypeUtils.isBoolean(field.getType()))
            field.setBoolean(obj, (Boolean)value);
        else if (TypeUtils.isByte(field.getType()))
            field.setByte(obj, (Byte)value);
        else if (TypeUtils.isChar(field.getType()))
            field.setChar(obj, (Character)value);
        else if (TypeUtils.isDouble(field.getType()))
            field.setDouble(obj, (Double)value);
        else if (TypeUtils.isLong(field.getType()))
            field.setLong(obj, (Long)value);
        else if (TypeUtils.isShort(field.getType()))
            field.setShort(obj, (Short)value);
        else if (TypeUtils.isFloat(field.getType()))
            field.setFloat(obj, (Float)value);
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
