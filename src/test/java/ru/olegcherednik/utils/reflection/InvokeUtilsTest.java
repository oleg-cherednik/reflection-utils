package ru.olegcherednik.utils.reflection;

import org.testng.annotations.Test;
import ru.olegcherednik.utils.reflection.data.Data;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Oleg Cherednik
 * @since 27.02.2021
 */
@Test
public class InvokeUtilsTest {

    public void shouldRetrieveFieldValueWhenInvokeFunctionOnField() throws Exception {
        Data data = Data.create();
        Field field = data.getClass().getDeclaredField("name");
        String name = InvokeUtils.invokeFunction(field, f -> (String)f.get(data));
        assertThat(name).isEqualTo("oleg.cherednik");
    }

    public void shouldRetrieveMethodReturnValueWhenInvokeFunctionOnMethod() throws Exception {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("getCity");
        String city = InvokeUtils.invokeFunction(method, m -> (String)m.invoke(data));
        assertThat(city).isEqualTo("Saint-Petersburg");
    }

    public void shouldThrowExceptionWhenWhenInvokeFunctionWithException() throws Exception {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("getCity");

        assertThatThrownBy(() -> InvokeUtils.invokeFunction(method, m -> {
            throw new NoSuchMethodException("xxx");
        })).isExactlyInstanceOf(NoSuchMethodException.class).hasMessage("xxx");
    }

    public void shouldRestoreFieldModifiersWhenInvokeFunctionOnField() throws Exception {
        Data data = Data.create();
        Field field = data.getClass().getDeclaredField("name");
        boolean expectedAccessible = field.isAccessible();
        int expectedModifiers = field.getModifiers();

        InvokeUtils.invokeFunction(field, f -> (String)f.get(data));
        assertThat(field.isAccessible()).isEqualTo(expectedAccessible);
        assertThat(field.getModifiers()).isEqualTo(expectedModifiers);
    }

    public void shouldRestoreFieldModifiersWhenInvokeFunctionOnMethod() throws Exception {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("getCity");
        boolean expectedAccessible = method.isAccessible();
        int expectedModifiers = method.getModifiers();

        InvokeUtils.invokeFunction(method, m -> (String)m.invoke(data));
        assertThat(method.isAccessible()).isEqualTo(expectedAccessible);
        assertThat(method.getModifiers()).isEqualTo(expectedModifiers);
    }

    public void shouldRetrieveFieldValueWhenInvokeOnField() throws Exception {
        Data data = Data.create();
        Field field = data.getClass().getDeclaredField("name");

        String actual = InvokeUtils.invoke(data, field);
        assertThat(actual).isEqualTo("oleg.cherednik");
    }

    public void shouldRetrieveMethodReturnValueWhenInvokeOnMethod() throws Exception {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("getCity");

        String actual = InvokeUtils.invoke(data, method);
        assertThat(actual).isEqualTo("Saint-Petersburg");
    }

    public void shouldThrowIllegalArgumentExceptionWhenInvokeNotFieldOrMethod() throws Exception {
        Data data = Data.create();
        Constructor<?> constructor = data.getClass().getDeclaredConstructor();

        assertThatThrownBy(() -> InvokeUtils.invoke(data, constructor))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unknown 'accessibleObject' class: class java.lang.reflect.Constructor");
    }

}
