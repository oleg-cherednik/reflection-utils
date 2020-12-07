package ru.olegcherednik.utils.reflection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
public class TypeUtils {

    public static Class<?> getType(AccessibleObject obj) {
        if (obj instanceof Field)
            return FieldUtils.getType((Field)obj);
        if (obj instanceof Method)
            return MethodUtils.getReturnType((Method)obj);
        throw new IllegalArgumentException("Unknown 'obj' class: " + obj.getClass());
    }

    public static Class<?> getType(AccessibleObject obj, Class<?> def) {
        if (obj instanceof Field)
            return FieldUtils.getType((Field)obj, def);
        if (obj instanceof Method)
            return MethodUtils.getReturnType((Method)obj, def);
        return def;
    }

    public static String getName(Member obj) {
        return obj.getName();
    }

    public static boolean isString(Class<?> type) {
        return type.isAssignableFrom(String.class);
    }

    public static boolean isBoolean(Class<?> type) {
        return type.isAssignableFrom(boolean.class) || isBooleanWrapper(type);
    }

    public static boolean isBooleanWrapper(Class<?> type) {
        return type.isAssignableFrom(Boolean.class);
    }

    public static boolean isInteger(Class<?> type) {
        return type.isAssignableFrom(int.class) || isIntegerWrapper(type);
    }

    public static boolean isIntegerWrapper(Class<?> type) {
        return type.isAssignableFrom(Integer.class);
    }

    public static boolean isByte(Class<?> type) {
        return type.isAssignableFrom(byte.class) || isByteWrapper(type);
    }

    public static boolean isByteWrapper(Class<?> type) {
        return type.isAssignableFrom(Byte.class);
    }

    public static boolean isShort(Class<?> type) {
        return type.isAssignableFrom(short.class) || isShortWrapper(type);
    }

    public static boolean isShortWrapper(Class<?> type) {
        return type.isAssignableFrom(Short.class);
    }

    public static boolean isLong(Class<?> type) {
        return type.isAssignableFrom(long.class) || isLongWrapper(type);
    }

    public static boolean isLongWrapper(Class<?> type) {
        return type.isAssignableFrom(Long.class);
    }

    public static boolean isChar(Class<?> type) {
        return type.isAssignableFrom(char.class) || isCharWrapper(type);
    }

    public static boolean isCharWrapper(Class<?> type) {
        return type.isAssignableFrom(Character.class);
    }

    public static boolean isFloat(Class<?> type) {
        return type.isAssignableFrom(float.class) || isFloatWrapper(type);
    }

    public static boolean isFloatWrapper(Class<?> type) {
        return type.isAssignableFrom(Float.class);
    }

    public static boolean isDouble(Class<?> type) {
        return type.isAssignableFrom(double.class) || isDoubleWrapper(type);
    }

    public static boolean isDoubleWrapper(Class<?> type) {
        return type.isAssignableFrom(Double.class);
    }

    public static boolean isNumeric(Class<?> type) {
        return isByte(type) || isShort(type) || isInteger(type) || isLong(type) || isFloat(type) || isDouble(type);
    }

    public static boolean isNumberWrapper(Class<?> type) {
        return isByteWrapper(type) || isShortWrapper(type) || isIntegerWrapper(type)
                || isLongWrapper(type) || isFloatWrapper(type) || isDoubleWrapper(type);
    }

    public static Class<?>[] getBooleanClasses() {
        return new Class<?>[] { boolean.class, Boolean.class };
    }

    public static Class<?>[] getByteClasses() {
        return new Class<?>[] { byte.class, Byte.class };
    }

    public static Class<?>[] getShortClasses() {
        return new Class<?>[] { short.class, Short.class };
    }

    public static Class<?>[] getIntegerClasses() {
        return new Class<?>[] { int.class, Integer.class };
    }

    public static Class<?>[] getLongClasses() {
        return new Class<?>[] { long.class, Long.class };
    }

    public static Class<?>[] getFloatClasses() {
        return new Class<?>[] { float.class, Float.class };
    }

    public static Class<?>[] getDoubleClasses() {
        return new Class<?>[] { double.class, Double.class };
    }

    public static Class<?>[] getNumericClasses() {
        return new Class<?>[] { byte.class, Byte.class, short.class, Short.class, int.class, Integer.class, long.class,
                Long.class, float.class, Float.class, double.class, Double.class };
    }

    public static Number getNumberValue(Class<?> type, Number number) {
        if (isByte(type))
            return number.byteValue();
        if (isShort(type))
            return number.shortValue();
        if (isInteger(type))
            return number.intValue();
        if (isLong(type))
            return number.longValue();
        if (isFloat(type))
            return number.floatValue();
        if (isDouble(type))
            return number.doubleValue();
        throw new IllegalArgumentException("Cannot recognize 'type': " + type);
    }

    private TypeUtils() {
    }

}
