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

    public void shouldRetrieveStaticFieldValueWhenGetStaticFieldValueByField() throws Exception {
        Field field = Data.class.getDeclaredField("AUTO");
        String actual = FieldUtils.getStaticFieldValue(field);
        assertThat(actual).isEqualTo("ferrari");
    }

    public void shouldRetrieveStaticFieldValueWhenGetStaticFieldValueByFieldName() throws Exception {
        String actual = FieldUtils.getStaticFieldValue(Data.class, "AUTO");
        assertThat(actual).isEqualTo("ferrari");
    }

    public void shouldRetrieveStaticFieldValueWhenGetStaticFieldValueFromBaseClassByFieldName() throws Exception {
        Data data = Data.create();
        String actual = FieldUtils.getFieldValue(data, "MONITOR");
        assertThat(actual).isEqualTo("dell");
    }

    public void shouldSetFieldValueWhenSetFieldValueByField() throws Exception {
        Data data = Data.create();
        assertThat(data.getName()).isNotEqualTo("moskow");

        Field field = data.getClass().getDeclaredField("name");
        FieldUtils.setFieldValue(data, field, "moskow");
        assertThat(data.getName()).isEqualTo("moskow");
    }

    public void shouldSetFieldValueWhenSetFieldValueByFieldName() throws Exception {
        Data data = Data.create();
        assertThat(data.getName()).isNotEqualTo("moskow");

        FieldUtils.setFieldValue(data, "name", "moskow");
        assertThat(data.getName()).isEqualTo("moskow");
    }

    public void shouldSetFieldValueWhenSetFieldValueByFieldWithConsumer() throws Exception {
        Data data = Data.create();
        assertThat(data.getName()).isNotEqualTo("moskow");

        Field field = data.getClass().getDeclaredField("name");
        FieldUtils.setFieldValue(data, field, f -> f.set(data, "moskow"));
        assertThat(data.getName()).isEqualTo("moskow");
    }

    public void shouldSetFieldValueWhenSetFieldValueByFieldNameWithConsumer() throws Exception {
        Data data = Data.create();
        assertThat(data.getName()).isNotEqualTo("moskow");

        FieldUtils.setFieldValue(data, "name", field -> field.set(data, "moskow"));
        assertThat(data.getName()).isEqualTo("moskow");
    }

    public void shouldSetStaticFieldValueWhenSetFieldValueByField() throws Exception {
        Field field = Data.class.getDeclaredField("AUTO");

        assertThat((String)FieldUtils.getStaticFieldValue(field)).isEqualTo("ferrari");

        FieldUtils.setStaticFieldValue(field, "audi");
        assertThat((String)FieldUtils.getStaticFieldValue(field)).isEqualTo("audi");

        FieldUtils.setStaticFieldValue(field, "ferrari");
        assertThat((String)FieldUtils.getStaticFieldValue(field)).isEqualTo("ferrari");
    }

    public void shouldSetStaticFieldValueWhenSetFieldValueByFieldName() throws Exception {
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("ferrari");

        FieldUtils.setStaticFieldValue(Data.class, "AUTO", "audi");
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("audi");

        FieldUtils.setStaticFieldValue(Data.class, "AUTO", "ferrari");
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("ferrari");
    }

    public void shouldSetStaticFieldValueWhenSetFieldValueByFieldNameInBaseCalss() throws Exception {
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "MONITOR")).isEqualTo("dell");

        FieldUtils.setStaticFieldValue(Data.class, "MONITOR", "acer");
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "MONITOR")).isEqualTo("acer");

        FieldUtils.setStaticFieldValue(Data.class, "MONITOR", "dell");
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "MONITOR")).isEqualTo("dell");
    }

}
