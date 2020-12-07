package ru.olegcherednik.utils.reflection;

import sun.misc.Unsafe;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

/**
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
public class EnumUtils {

    public static <T extends Enum<?>> void addConstant(Class<T> cls, String name) throws Throwable {
        addConstant(cls, name, (Invoke.Consumer<T>)Invoke.NULL_CONSUMER);
    }

    @SuppressWarnings("UseOfSunClasses")
    public static <T extends Enum<?>> void addConstant(Class<T> cls, String name, Invoke.Consumer<T> setExtraFieldTask) throws Throwable {
        Constructor<?> constructor = Unsafe.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Unsafe unsafe = (Unsafe)constructor.newInstance();
        T enumValue = (T)unsafe.allocateInstance(cls);

        setFieldValue(enumValue, "name", name);
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

    public static <T extends Enum<?>> void setFieldValue(T obj, String name, Object value) throws Exception {
        Invoke.invokeConsumer(Enum.class.getDeclaredField(name), field -> field.set(obj, value));
    }

    private static <T extends Enum<?>> void setField(Class<T> cls, String name, Object value) throws Exception {
        Invoke.invokeConsumer(Class.class.getDeclaredField(name), field -> field.set(cls, value));
    }

    private EnumUtils() {
    }

}
