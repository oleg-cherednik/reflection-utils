package ru.olegcherednik.utils.reflection;

import org.testng.annotations.Test;
import ru.olegcherednik.utils.reflection.data.Data;
import ru.olegcherednik.utils.reflection.exceptions.ReflectionUtilsException;

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
public class AccessibleObjectUtilsTest {

    public void shouldRetrieveFieldValueWhenInvokeFunctionOnField() throws NoSuchFieldException {
        Data data = Data.create();
        Field field = data.getClass().getDeclaredField("name");
        String name = AccessibleObjectUtils.invokeFunction(field, f -> (String)f.get(data));
        assertThat(name).isEqualTo("oleg.cherednik");
    }

    public void shouldRetrieveMethodReturnValueWhenInvokeFunctionOnMethod() throws NoSuchMethodException {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("getCity");
        String city = AccessibleObjectUtils.invokeFunction(method, m -> (String)m.invoke(data));
        assertThat(city).isEqualTo("Saint-Petersburg");
    }

    public void shouldThrowExceptionWhenWhenInvokeFunctionWithException() throws NoSuchMethodException {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("getCity");

        assertThatThrownBy(() -> AccessibleObjectUtils.invokeFunction(method, m -> {
            throw new NoSuchMethodException("xxx");
        }))
                .isExactlyInstanceOf(ReflectionUtilsException.class)
                .hasCauseInstanceOf(NoSuchMethodException.class)
                .hasMessageContaining("xxx");
    }

    public void shouldRestoreFieldModifiersWhenInvokeFunctionOnField() throws NoSuchFieldException {
        Data data = Data.create();
        Field field = data.getClass().getDeclaredField("name");
        boolean expectedAccessible = field.isAccessible();
        int expectedModifiers = field.getModifiers();

        AccessibleObjectUtils.invokeFunction(field, f -> (String)f.get(data));
        assertThat(field.isAccessible()).isEqualTo(expectedAccessible);
        assertThat(field.getModifiers()).isEqualTo(expectedModifiers);
    }

    public void shouldRestoreFieldModifiersWhenInvokeFunctionOnMethod() throws NoSuchMethodException {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("getCity");
        boolean expectedAccessible = method.isAccessible();
        int expectedModifiers = method.getModifiers();

        AccessibleObjectUtils.invokeFunction(method, m -> (String)m.invoke(data));
        assertThat(method.isAccessible()).isEqualTo(expectedAccessible);
        assertThat(method.getModifiers()).isEqualTo(expectedModifiers);
    }

    public void shouldRetrieveFieldValueWhenInvokeOnField() throws NoSuchFieldException {
        Data data = Data.create();
        Field field = data.getClass().getDeclaredField("name");

        String actual = AccessibleObjectUtils.invoke(data, field);
        assertThat(actual).isEqualTo("oleg.cherednik");
    }

    public void shouldRetrieveMethodReturnValueWhenInvokeOnMethod() throws NoSuchMethodException {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("getCity");

        String actual = AccessibleObjectUtils.invoke(data, method);
        assertThat(actual).isEqualTo("Saint-Petersburg");
    }

    public void shouldThrowIllegalArgumentExceptionWhenInvokeNotFieldOrMethod() throws NoSuchMethodException {
        Data data = Data.create();
        Constructor<?> constructor = data.getClass().getDeclaredConstructor();

        assertThatThrownBy(() -> AccessibleObjectUtils.invoke(data, constructor))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unknown 'accessibleObject' class: class java.lang.reflect.Constructor");
    }

    public void shouldNotWrapRuntimeExceptionWhenRuntimeExceptionIsThrown() throws NoSuchMethodException {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("getCity");

        assertThatThrownBy(() -> AccessibleObjectUtils.invokeFunction(method, (Function<Method, String>)m -> {
            throw new IllegalArgumentException("xxx");
        }))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("xxx");
    }

    public void shouldRetrieveFieldValueWhenInvokeOnStaticField() throws NoSuchFieldException {
        Data data = Data.create();
        Field field = data.getClass().getDeclaredField("AUTO");

        String actual = AccessibleObjectUtils.invoke(null, field);
        assertThat(actual).isEqualTo("ferrari");
    }

    public void shouldRetrieveMethodReturnValueWhenInvokeOnStaticMethod() throws NoSuchMethodException {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("getMarker");

        String actual = AccessibleObjectUtils.invoke(null, method);
        assertThat(actual).isEqualTo("green");
    }

    public void shouldRetrieveFieldValueWhenInvokeOnStaticFieldSpecialMethod() throws NoSuchFieldException {
        Data data = Data.create();
        Field field = data.getClass().getDeclaredField("AUTO");

        String actual = AccessibleObjectUtils.invoke(field);
        assertThat(actual).isEqualTo("ferrari");
    }

    public void shouldRetrieveMethodReturnValueWhenInvokeOnStaticMethodSpecialMethod() throws NoSuchMethodException {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("getMarker");

        String actual = AccessibleObjectUtils.invoke(method);
        assertThat(actual).isEqualTo("green");
    }

}
