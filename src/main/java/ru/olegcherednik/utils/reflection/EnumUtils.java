package ru.olegcherednik.utils.reflection;

import sun.misc.Unsafe;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

/**
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
public class EnumUtils {

    public static <T extends Enum<?>> void addValue(Class<T> cls, String name) throws Throwable {
        addValue(cls, name, (Invoke.Consumer<T>)Invoke.NULL_CONSUMER);
    }

    @SuppressWarnings("UseOfSunClasses")
    public static <T extends Enum<?>> void addValue(Class<T> cls, String name, Invoke.Consumer<T> setExtraFieldValueTask) throws Throwable {
        Constructor<?> constructor = Unsafe.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Unsafe unsafe = (Unsafe)constructor.newInstance();
        T enumValue = (T)unsafe.allocateInstance(cls);

        setFieldValue(enumValue, "name", name);
        setFieldValue(enumValue, "ordinal", cls.getEnumConstants().length);

        setExtraFieldValueTask.accept(enumValue);

        FieldUtils.setStaticFieldValue(cls, "$VALUES", field -> {
            T[] oldValues = (T[])field.get(null);
            T[] newValues = (T[])Array.newInstance(cls, oldValues.length + 1);
            System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
            newValues[oldValues.length] = enumValue;
            field.set(null, newValues);
        });

//        FieldUtils.setStaticFieldValue(Class.class, "enumConstants", (Object)null);
        int a = 0;
        a++;
//        FieldUtils.setStaticFieldValue(cls, "enumConstants", field -> {
//            T[] oldValues = (T[])field.get(null);
//            T[] newValues = (T[])Array.newInstance(cls, oldValues.length + 1);
//            System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
//            newValues[oldValues.length] = enumValue;
//            field.set(null, newValues);
//        });

        setField(cls, null);

//        Field field = Class.class.getDeclaredField("enumConstants");
//        field.setAccessible(true);
//        field.set(cls, null);


//        Invoke.invokeConsumer(cls.getClass().getDeclaredField("enumConstants"), field -> {
//            field.set(cls.getClass(), null);
//        });

    }

    public static <T extends Enum<?>> void setFieldValue(T obj, String name, Object value) throws Exception {
        Invoke.invokeConsumer(Enum.class.getDeclaredField(name), field -> field.set(obj, value));
    }

    private static <T extends Enum<?>> void setField(Class<T> cls, Object value) throws Exception {
        Invoke.invokeConsumer(Class.class.getDeclaredField("enumConstants"), field -> field.set(cls, value));
    }

    private EnumUtils() {
    }

}
