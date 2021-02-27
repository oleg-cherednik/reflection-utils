package ru.olegcherednik.utils.reflection;

import org.testng.annotations.Test;
import ru.olegcherednik.utils.reflection.data.Data;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Oleg Cherednik
 * @since 27.02.2021
 */
@Test
public class FieldUtilsTest {

    public void shouldRetrieveFieldValueWhenGetFieldValueByField() throws Exception {
        Data data = Data.create();
        Field field = data.getClass().getDeclaredField("name");
        String actual = FieldUtils.getFieldValue(data, field);
        assertThat(actual).isEqualTo("oleg.cherednik");
    }

    public void shouldRetrieveFieldValueWhenGetFieldValueByFieldName() throws Exception {
        Data data = Data.create();
        String actual = FieldUtils.getFieldValue(data, "name");
        assertThat(actual).isEqualTo("oleg.cherednik");
    }

    public void shouldRetrieveFieldValueWhenGetFieldValueFromBaseClassByFieldName() throws Exception {
        Data data = Data.create();
        String actual = FieldUtils.getFieldValue(data, "baseName");
        assertThat(actual).isEqualTo("pizza");
    }

    public void shouldThrowNoSuchElementExceptionWhenGetNotExistedFieldByFieldName() throws Exception {
        assertThatThrownBy(() -> FieldUtils.getFieldValue(Data.create(), "unknownName"))
                .isExactlyInstanceOf(NoSuchElementException.class);
    }

    public void shouldRetrieveFieldTypeWhenGetType() throws NoSuchFieldException {
        Data data = Data.create();
        Field field = data.getClass().getDeclaredField("name");
        assertThat(FieldUtils.getType(field)).isSameAs(String.class);
    }

    public void shouldRetrieveFieldTypeWhenGetTypeForExistedField() throws NoSuchFieldException {
        Data data = Data.create();
        Field field = data.getClass().getDeclaredField("name");
        assertThat(FieldUtils.getType(field, Number.class)).isSameAs(String.class);
    }

    public void shouldRetrieveDefaultTypeWhenGetTypeForNotExistedFieldWithDefaultValue() throws NoSuchFieldException {
        assertThat(FieldUtils.getType(null, Number.class)).isSameAs(Number.class);
    }

}
