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
public class TypeUtilsTest {

    public void shouldRetrieveFieldTypeWhenGetTypeForField() throws NoSuchFieldException {
        Data data = Data.create();
        Field field = data.getClass().getDeclaredField("age");

        Class<?> actual = TypeUtils.getType(field);
        assertThat(actual).isSameAs(int.class);
    }

    public void shouldRetrieveMethodReturnTypeWhenGetTypeForMethod() throws Exception {
        Data data = Data.create();
        Method method = data.getClass().getDeclaredMethod("getCity");

        Class<?> actual = TypeUtils.getType(method);
        assertThat(actual).isSameAs(String.class);
    }

    public void shouldThrowIllegalArgumentExceptionWhenGetTypeForNotFieldOrMethod() throws Exception {
        Data data = Data.create();
        Constructor<?> constructor = data.getClass().getDeclaredConstructor();

        assertThatThrownBy(() -> TypeUtils.getType(constructor))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unknown 'accessibleObject' class: class java.lang.reflect.Constructor");
    }

}
