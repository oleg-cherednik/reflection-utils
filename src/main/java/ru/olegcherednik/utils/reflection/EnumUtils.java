package ru.olegcherednik.utils.reflection;

import ru.olegcherednik.utils.reflection.exceptions.NoSuchFieldException;
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
     * @throws NullPointerException in case of any of required parameters is {@literal null}
     * @throws RuntimeException     in case if any other problem; checked exception is wrapped with runtime exception as well
     */
    public static <T extends Enum<?>> void addConstant(Class<T> cls, String constantName) {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireConstantNameNonNull(constantName);

        addConstant(cls, constantName, (InvokeUtils.Consumer<T>)InvokeUtils.Consumer.NULL);
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
     * @throws RuntimeException         in case if any other problem; checked exception is wrapped with runtime exception as well
     * @throws IllegalArgumentException in case of enums contains constant with given name
     */
    @SuppressWarnings("UseOfSunClasses")
    public static <T extends Enum<?>> void addConstant(Class<T> cls, String constantName, InvokeUtils.Consumer<T> setExtraFieldTask) {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireConstantNameNonNull(constantName);
        ValidationUtils.requireSetExtraFieldTaskNonNull(setExtraFieldTask);
        ValidationUtils.requireConstantNotExist(cls, constantName);

        try {
            Constructor<?> constructor = Unsafe.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Unsafe unsafe = (Unsafe)constructor.newInstance();
            T enumValue = (T)unsafe.allocateInstance(cls);

            setFieldValue(enumValue, "name", constantName);
            setFieldValue(enumValue, "ordinal", cls.getEnumConstants().length);

            setExtraFieldTask.accept(enumValue);

            FieldUtils.setStaticFieldValue(cls, "$VALUES", field -> {
                T[] oldValues = (T[])field.get(null);
                T[] newValues = (T[])Array.newInstance(cls, oldValues.length + 1);
                System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
                newValues[oldValues.length] = enumValue;
                field.set(null, newValues);
            });

            setField(cls, "enumConstants", null);
            setField(cls, "enumConstantDirectory", null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* package */ static <T extends Enum<?>> void setFieldValue(T obj, String fieldName, Object value) {
        ValidationUtils.requireObjNonNull(obj);
        ValidationUtils.requireFieldNameNonNull(fieldName);

        try {
            InvokeUtils.invokeConsumer(Enum.class.getDeclaredField(fieldName), field -> field.set(obj, value));
        } catch (java.lang.NoSuchFieldException e) {
            throw new NoSuchFieldException(Enum.class, fieldName);
        }
    }

    /* package */ static <T extends Enum<?>> void setField(Class<T> cls, String fieldName, Object value) {
        ValidationUtils.requireClsNonNull(cls);
        ValidationUtils.requireFieldNameNonNull(fieldName);

        try {
            InvokeUtils.invokeConsumer(Class.class.getDeclaredField(fieldName), field -> field.set(cls, value));
        } catch (java.lang.NoSuchFieldException e) {
            throw new NoSuchFieldException(cls, fieldName);
        }
    }

    private EnumUtils() { }

}
