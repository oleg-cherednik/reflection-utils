package ru.olegcherednik.utils.reflection;

import org.testng.annotations.Test;
import ru.olegcherednik.utils.reflection.data.Data;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oleg Cherednik
 * @since 27.02.2021
 */
@Test
public class ConstructorUtilsTest {

    public void shouldInvokeDefaultConstructorWhenInvokeConstructorWithNoArguments() {
        Data expected = Data.create();
        Data actual = ConstructorUtils.invokeConstructor(Data.class);
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldInvokeOneArgumentConstructorWhenInvokeConstructorWithOneArgument() {
        Data expected = Data.create("anna");
        Data actual = ConstructorUtils.invokeConstructor(Data.class, String.class, "anna");
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldInvokeTwoArgumentsConstructorWhenInvokeConstructorWithTwoArguments() {
        Data expected = Data.create("peter", 71);
        Data actual = ConstructorUtils.invokeConstructor(Data.class, String.class, "peter", int.class, 71);
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldInvokeThreeArgumentsConstructorWhenInvokeConstructorWithThreeArguments() {
        Data expected = Data.create("marvin", 91, true);
        Data actual = ConstructorUtils.invokeConstructor(Data.class, String.class, "marvin", int.class, 91, boolean.class, true);
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldInvokeGivenArgumentsConstructorWhenInvokeConstructorWithMultipleArguments() {
        Data expected = Data.create("marvin", 91, true);
        Data actual = ConstructorUtils.invokeConstructor(Data.class,
                new Class<?>[] { String.class, int.class, boolean.class },
                new Object[] { "marvin", 91, true });
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldInvokeDefaultConstructorWhenInvokeConstructorStringWithNoArguments() {
        Data expected = Data.create();
        Data actual = ConstructorUtils.invokeConstructor(Data.class.getCanonicalName());
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldInvokeOneArgumentConstructorWhenInvokeConstructorStringWithOneArgument() {
        Data expected = Data.create("anna");
        Data actual = ConstructorUtils.invokeConstructor(Data.class.getCanonicalName(), String.class, "anna");
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldInvokeTwoArgumentsConstructorWhenInvokeConstructorStringWithTwoArguments() {
        Data expected = Data.create("peter", 71);
        Data actual = ConstructorUtils.invokeConstructor(Data.class.getCanonicalName(), String.class, "peter", int.class, 71);
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldInvokeThreeArgumentsConstructorWhenInvokeConstructorStringWithThreeArguments() {
        Data expected = Data.create("marvin", 91, true);
        Data actual = ConstructorUtils.invokeConstructor(Data.class.getCanonicalName(), String.class, "marvin", int.class, 91, boolean.class, true);
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldInvokeGivenArgumentsConstructorWhenInvokeConstructorStringWithMultipleArguments() {
        Data expected = Data.create("marvin", 91, true);
        Data actual = ConstructorUtils.invokeConstructor(Data.class.getCanonicalName(),
                new Class<?>[] { String.class, int.class, boolean.class },
                new Object[] { "marvin", 91, true });
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldInvokeConstructorWithNoArgumentsWhenInvokeConstructorWithNoArguments() throws NoSuchMethodException {
        Data expected = Data.create();
        Data actual = ConstructorUtils.invokeConstructor(Data.class.getDeclaredConstructor());
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldInvokeConstructorWithOneArgumentWhenInvokeConstructorWithOneArguments() throws NoSuchMethodException {
        Data expected = Data.create("anna");
        Data actual = ConstructorUtils.invokeConstructor(Data.class.getDeclaredConstructor(String.class), "anna");
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldInvokeConstructorWithTwoArgumentsWhenInvokeConstructorWithTwoArguments() throws NoSuchMethodException {
        Data expected = Data.create("peter", 71);
        Data actual = ConstructorUtils.invokeConstructor(Data.class.getDeclaredConstructor(String.class, int.class), "peter", 71);
        assertThat(actual).isEqualTo(expected);
    }

    public void shouldInvokeConstructorWithThreeArgumentsWhenInvokeConstructorWithThreeArguments() throws NoSuchMethodException {
        Data expected = Data.create("marvin", 91, true);
        Data actual = ConstructorUtils.invokeConstructor(Data.class.getDeclaredConstructor(String.class, int.class, boolean.class),
                "marvin", 91, true);
        assertThat(actual).isEqualTo(expected);
    }

}
