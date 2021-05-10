package ru.olegcherednik.utils.reflection;

import org.testng.annotations.Test;
import ru.olegcherednik.utils.reflection.data.Data;
import ru.olegcherednik.utils.reflection.exceptions.NoSuchMethodException;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Oleg Cherednik
 * @since 01.03.2021
 */
@Test
public class MethodUtilsTest {

    public void shouldRetrieveReturnValueWhenInvokeMethodWithNoArguments() throws java.lang.NoSuchMethodException {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("getCity");
        String actual = MethodUtils.invokeMethod(data, method);
        assertThat(actual).isEqualTo("Saint-Petersburg");
    }

    public void shouldRetrieveReturnValueWhenInvokeMethodByNameWithNoArguments() {
        Data data = Data.create();
        String actual = MethodUtils.invokeMethod(data, "getCity");
        assertThat(actual).isEqualTo("Saint-Petersburg");
    }

    public void shouldRetrieveReturnValueWhenInvokeMethodWithOneArgument() throws java.lang.NoSuchMethodException {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("addPrefixFoo", String.class);
        String actual = MethodUtils.invokeMethod(data, method, "xxx");
        assertThat(actual).isEqualTo("foo_xxx");
    }

    public void shouldRetrieveReturnValueWhenInvokeMethodByNameWithOneArgument() {
        Data data = Data.create();
        String actual = MethodUtils.invokeMethod(data, "addPrefixFoo", String.class, "xxx");
        assertThat(actual).isEqualTo("foo_xxx");
    }

    public void shouldRetrieveReturnValueWhenInvokeMethodWithTwoArguments() throws java.lang.NoSuchMethodException {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("sum", int.class, int.class);
        int actual = MethodUtils.invokeMethod(data, method, 2, 3);
        assertThat(actual).isEqualTo(5);
    }

    public void shouldRetrieveReturnValueWhenInvokeMethodByNameWithTwoArguments() {
        Data data = Data.create();
        int actual = MethodUtils.invokeMethod(data, "sum", int.class, 5, int.class, 4);
        assertThat(actual).isEqualTo(9);
    }

    public void shouldRetrieveReturnValueWhenInvokeMethodWithThreeArguments() throws java.lang.NoSuchMethodException {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("sub", int.class, int.class, int.class);
        int actual = MethodUtils.invokeMethod(data, method, 8, 3, 2);
        assertThat(actual).isEqualTo(3);
    }

    public void shouldRetrieveReturnValueWhenInvokeMethodByNameWithThreeArguments() {
        Data data = Data.create();
        int actual = MethodUtils.invokeMethod(data, "sub", int.class, 9, int.class, 4, int.class, 2);
        assertThat(actual).isEqualTo(3);
    }

    public void shouldRetrieveReturnValueWhenInvokeMethodWithManyArguments() throws java.lang.NoSuchMethodException {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("sub", int.class, int.class, int.class);
        int actual = MethodUtils.invokeMethod(data, method, 8, 3, 2);
        assertThat(actual).isEqualTo(3);
    }

    public void shouldRetrieveReturnValueWhenInvokeMethodByNameWithManyArguments() {
        Data data = Data.create();
        int actual = MethodUtils.invokeMethod(data, "sub",
                new Class<?>[] { int.class, int.class, int.class },
                new Object[] { 8, 3, 2 });
        assertThat(actual).isEqualTo(3);
    }

    public void shouldThrowIllegalArgumentExceptionWhenTypeLengthNotMatchValuesLength() {
        assertThatThrownBy(() -> MethodUtils.invokeMethod(Data.create(), "sub",
                new Class<?>[] { int.class, int.class, int.class },
                new Object[] { 8, 3 })).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    public void shouldRetrieveReturnValueWhenInvokeMethodByNameFromBaseClass() {
        Data data = Data.create();
        String actual = MethodUtils.invokeMethod(data, "getSeason");
        assertThat(actual).isEqualTo("winter");
    }

    public void shouldRetrieveReturnValueWhenInvokeStaticMethodWithNoArguments() throws java.lang.NoSuchMethodException {
        Method method = Data.class.getDeclaredMethod("getMarker");
        String actual = MethodUtils.invokeStaticMethod(method);
        assertThat(actual).isEqualTo("green");
    }

    public void shouldRetrieveReturnValueWhenInvokeStaticMethodByNameWithNoArguments() {
        String actual = MethodUtils.invokeStaticMethod(Data.class, "getMarker");
        assertThat(actual).isEqualTo("green");
    }

    public void shouldRetrieveReturnValueWhenInvokeStaticMethodWithOneArgument() throws java.lang.NoSuchMethodException {
        Method method = Data.class.getDeclaredMethod("addPostfixBar", String.class);
        String actual = MethodUtils.invokeStaticMethod(method, "yyy");
        assertThat(actual).isEqualTo("yyy_bar");
    }

    public void shouldRetrieveReturnValueWhenInvokeStaticMethodByNameWithOneArgument() {
        String actual = MethodUtils.invokeStaticMethod(Data.class, "addPostfixBar", String.class, "yyy");
        assertThat(actual).isEqualTo("yyy_bar");
    }

    public void shouldRetrieveReturnValueWhenInvokeStaticMethodWithTwoArguments() throws java.lang.NoSuchMethodException {
        Method method = Data.class.getDeclaredMethod("mul", long.class, long.class);
        long actual = MethodUtils.invokeStaticMethod(method, 2, 3);
        assertThat(actual).isEqualTo(6L);
    }

    public void shouldRetrieveReturnValueWhenInvokeStaticMethodByNameWithTwoArguments() {
        long actual = MethodUtils.invokeStaticMethod(Data.class, "mul", long.class, 4, long.class, 2);
        assertThat(actual).isEqualTo(8L);
    }

    public void shouldRetrieveReturnValueWhenInvokeStaticMethodWithThreeArguments() throws java.lang.NoSuchMethodException {
        Method method = Data.class.getDeclaredMethod("concat", int.class, int.class, int.class);
        String actual = MethodUtils.invokeStaticMethod(method, 1, 2, 3);
        assertThat(actual).isEqualTo("123");
    }

    public void shouldRetrieveReturnValueWhenInvokeStaticMethodByNameWithThreeArguments() {
        String actual = MethodUtils.invokeStaticMethod(Data.class, "concat", int.class, 4, int.class, 2, int.class, 1);
        assertThat(actual).isEqualTo("421");
    }

    public void shouldRetrieveReturnValueWhenInvokeStaticMethodWithManyArguments() throws java.lang.NoSuchMethodException {
        Method method = Data.class.getDeclaredMethod("concat", int.class, int.class, int.class);
        String actual = MethodUtils.invokeStaticMethod(method, new Object[] { 8, 3, 2 });
        assertThat(actual).isEqualTo("832");
    }

    public void shouldRetrieveReturnValueWhenInvokeStaticMethodByNameWithManyArguments() {
        String actual = MethodUtils.invokeStaticMethod(Data.class, "concat",
                new Class<?>[] { int.class, int.class, int.class },
                new Object[] { 6, 3, 1 });
        assertThat(actual).isEqualTo("631");
    }

    public void shouldRetrieveReturnValueWhenInvokeStaticMethodByNameFromBaseClass() {
        int actual = MethodUtils.invokeStaticMethod(Data.class, "getVoltage");
        assertThat(actual).isEqualTo(220);
    }

    public void shouldThrowNoSuchMethodExceptionWhenInvokeNotExistedMethodByFieldName() {
        Data data = Data.create();
        assertThatThrownBy(() -> MethodUtils.invokeMethod(data, "unknownName"))
                .isExactlyInstanceOf(NoSuchMethodException.class);
    }

    public void shouldThrowNoSuchMethodExceptionWhenInvokeNotExistedStaticMethodByFieldName() {
        assertThatThrownBy(() -> MethodUtils.invokeStaticMethod(Data.class, "unknownName"))
                .isExactlyInstanceOf(NoSuchMethodException.class);
    }

    public void shouldRetrieveMethodReturnTypeWhenGetReturnType() throws java.lang.NoSuchMethodException {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("getCity");
        assertThat(MethodUtils.getReturnType(method)).isSameAs(String.class);
    }

    public void shouldRetrieveMethodReturnTypeWhenGetReturnTypeForExistedMethod() throws java.lang.NoSuchMethodException {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("getCity");
        assertThat(MethodUtils.getReturnType(method, Number.class)).isSameAs(String.class);
    }

    public void shouldRetrieveDefaultTypeWhenGetReturnTypeForNotExistedMethodWithDefaultValue() {
        assertThat(MethodUtils.getReturnType(null, Number.class)).isSameAs(Number.class);
    }

}
