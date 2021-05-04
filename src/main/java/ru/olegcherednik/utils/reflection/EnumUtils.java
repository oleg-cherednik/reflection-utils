package ru.olegcherednik.utils.reflection;

import sun.misc.Unsafe;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;

/**
 * Utils for working with enums using reflection.
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
     * @throws Exception in case if any problem; check type for details
     */
    public static <T extends Enum<?>> void addConstant(Class<T> cls, String constantName) throws Exception {
        Objects.requireNonNull(cls, "'cls' should not be null");
        Objects.requireNonNull(constantName, "'constantName' should not be null");
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
     * @throws Exception in case if any problem; check type for details
     */
    @SuppressWarnings("UseOfSunClasses")
    public static <T extends Enum<?>> void addConstant(Class<T> cls, String constantName, InvokeUtils.Consumer<T> setExtraFieldTask)
            throws Exception {
        Objects.requireNonNull(cls, "'cls' should not be null");
        Objects.requireNonNull(constantName, "'constantName' should not be null");
        Objects.requireNonNull(setExtraFieldTask, "'setExtraFieldTask' should not be null");

        requireConstantNotExist(cls, constantName);

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
    }

    private static <T extends Enum<?>> void setFieldValue(T obj, String name, Object value) throws Exception {
        InvokeUtils.invokeConsumer(Enum.class.getDeclaredField(name), field -> field.set(obj, value));
    }

    private static <T extends Enum<?>> void setField(Class<T> cls, String name, Object value) throws Exception {
        InvokeUtils.invokeConsumer(Class.class.getDeclaredField(name), field -> field.set(cls, value));
    }

    /**
     * Checks that given enum {@code cls} does not contain constant (case-sensitive) with given {@code constantName}.
     *
     * @param cls          not {@literal null} enum class object
     * @param constantName not {@literal null} enum's constant name
     * @param <T>          enum class type
     * @throws IllegalArgumentException in case of enums contains constaint with given name
     */
    private static <T extends Enum<?>> void requireConstantNotExist(Class<T> cls, String constantName) throws IllegalArgumentException {
        Objects.requireNonNull(cls, "'cls' should not be null");
        Objects.requireNonNull(constantName, "'constantName' should not be null");

        if (Arrays.stream(cls.getEnumConstants())
                  .anyMatch(value -> value.name().equals(constantName)))
            throw new IllegalArgumentException("Enum '" + cls + "' already has constant with name '" + constantName + '\'');
    }

    private EnumUtils() { }

}
