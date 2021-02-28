package ru.olegcherednik.utils.reflection;

import org.testng.annotations.Test;
import ru.olegcherednik.utils.reflection.data.Data;
import ru.olegcherednik.utils.reflection.data.TypeData;

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

    public void shouldSetStaticFieldValueWhenSetFieldValueByFieldConsumer() throws Exception {
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("ferrari");

        Field field = Data.class.getDeclaredField("AUTO");
        FieldUtils.setStaticFieldValue(field, f -> f.set(Data.class, "audi"));
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("audi");

        FieldUtils.setStaticFieldValue(field, f -> f.set(Data.class, "ferrari"));
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("ferrari");
    }

    public void shouldSetStaticFieldValueWhenSetFieldValueByFieldNameConsumer() throws Exception {
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("ferrari");

        FieldUtils.setStaticFieldValue(Data.class, "AUTO", field -> field.set(Data.class, "audi"));
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("audi");

        FieldUtils.setStaticFieldValue(Data.class, "AUTO", field -> field.set(Data.class, "ferrari"));
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("ferrari");
    }

    public void shouldSetIntFieldValueWhenSetFieldValue() throws Exception {
        TypeData data = new TypeData();
        assertThat(data.getValInt()).isEqualTo(0);
        assertThat(data.getValInteger()).isNull();

        FieldUtils.setFieldValue(data, "valInt", 66);
        assertThat(data.getValInt()).isEqualTo(66);

        FieldUtils.setFieldValue(data, "valInteger", 88);
        assertThat(data.getValInteger()).isEqualTo(88);
        assertThat(data.getValInteger()).isNotNull();

        FieldUtils.setFieldValue(data, "valInteger", (Object)null);
        assertThat(data.getValInteger()).isNull();
    }

    public void shouldSetBooleanFieldValueWhenSetFieldValue() throws Exception {
        TypeData data = new TypeData();
        assertThat(data.isValBoolean()).isFalse();
        assertThat(data.getValBooleanWrapper()).isNull();

        FieldUtils.setFieldValue(data, "valBoolean", true);
        assertThat(data.isValBoolean()).isTrue();

        FieldUtils.setFieldValue(data, "valBooleanWrapper", false);
        assertThat(data.getValBooleanWrapper()).isFalse();
        assertThat(data.getValBooleanWrapper()).isNotNull();

        FieldUtils.setFieldValue(data, "valBooleanWrapper", (Object)null);
        assertThat(data.getValBooleanWrapper()).isNull();
    }

    public void shouldSetByteFieldValueWhenSetFieldValue() throws Exception {
        TypeData data = new TypeData();
        assertThat(data.getValByte()).isEqualTo((byte)0);
        assertThat(data.getValByteWrapper()).isNull();

        FieldUtils.setFieldValue(data, "valByte", (byte)11);
        assertThat(data.getValByte()).isEqualTo((byte)11);

        FieldUtils.setFieldValue(data, "valByteWrapper", (byte)22);
        assertThat(data.getValByteWrapper()).isEqualTo((byte)22);
        assertThat(data.getValByteWrapper()).isNotNull();

        FieldUtils.setFieldValue(data, "valByteWrapper", (Object)null);
        assertThat(data.getValByteWrapper()).isNull();
    }

    public void shouldSetCharFieldValueWhenSetFieldValue() throws Exception {
        TypeData data = new TypeData();
        assertThat(data.getValChar()).isEqualTo('\0');
        assertThat(data.getValCharacter()).isNull();

        FieldUtils.setFieldValue(data, "valChar", 'o');
        assertThat(data.getValChar()).isEqualTo('o');

        FieldUtils.setFieldValue(data, "valCharacter", 'z');
        assertThat(data.getValCharacter()).isEqualTo('z');
        assertThat(data.getValCharacter()).isNotNull();

        FieldUtils.setFieldValue(data, "valCharacter", (Object)null);
        assertThat(data.getValCharacter()).isNull();
    }

    public void shouldSetDoubleFieldValueWhenSetFieldValue() throws Exception {
        TypeData data = new TypeData();
        assertThat(data.getValDouble()).isEqualTo(0.0);
        assertThat(data.getValDoubleWrapper()).isNull();

        FieldUtils.setFieldValue(data, "valDouble", 11.22);
        assertThat(data.getValDouble()).isEqualTo(11.22);

        FieldUtils.setFieldValue(data, "valDoubleWrapper", 33.44);
        assertThat(data.getValDoubleWrapper()).isEqualTo(33.44);
        assertThat(data.getValDoubleWrapper()).isNotNull();

        FieldUtils.setFieldValue(data, "valDoubleWrapper", (Object)null);
        assertThat(data.getValDoubleWrapper()).isNull();
    }

    public void shouldSetLongFieldValueWhenSetFieldValue() throws Exception {
        TypeData data = new TypeData();
        assertThat(data.getValLong()).isEqualTo(0L);
        assertThat(data.getValLongWrapper()).isNull();

        FieldUtils.setFieldValue(data, "valLong", 88L);
        assertThat(data.getValLong()).isEqualTo(88L);

        FieldUtils.setFieldValue(data, "valLong", 77);
        assertThat(data.getValLong()).isEqualTo(77L);

        FieldUtils.setFieldValue(data, "valLongWrapper", 99L);
        assertThat(data.getValLongWrapper()).isEqualTo(99L);
        assertThat(data.getValLongWrapper()).isNotNull();

        FieldUtils.setFieldValue(data, "valLongWrapper", (Object)null);
        assertThat(data.getValLongWrapper()).isNull();
    }

    public void shouldSetShortFieldValueWhenSetFieldValue() throws Exception {
        TypeData data = new TypeData();
        assertThat(data.getValShort()).isEqualTo((short)0);
        assertThat(data.getValShortWrapper()).isNull();

        FieldUtils.setFieldValue(data, "valShort", (short)11);
        assertThat(data.getValShort()).isEqualTo((short)11);

        FieldUtils.setFieldValue(data, "valShort", 22);
        assertThat(data.getValShort()).isEqualTo((short)22);

        FieldUtils.setFieldValue(data, "valShortWrapper", (short)33);
        assertThat(data.getValShortWrapper()).isEqualTo((short)33);
        assertThat(data.getValShortWrapper()).isNotNull();

        FieldUtils.setFieldValue(data, "valShortWrapper", (Object)null);
        assertThat(data.getValShortWrapper()).isNull();
    }

    public void shouldSetFloatFieldValueWhenSetFieldValue() throws Exception {
        TypeData data = new TypeData();
        assertThat(data.getValFloat()).isEqualTo((float)0);
        assertThat(data.getValFloatWrapper()).isNull();

        FieldUtils.setFieldValue(data, "valFloat", (float)11);
        assertThat(data.getValFloat()).isEqualTo((float)11);

        FieldUtils.setFieldValue(data, "valFloat", 22);
        assertThat(data.getValFloat()).isEqualTo((float)22);

        FieldUtils.setFieldValue(data, "valFloatWrapper", (float)33);
        assertThat(data.getValFloatWrapper()).isEqualTo((float)33);
        assertThat(data.getValFloatWrapper()).isNotNull();

        FieldUtils.setFieldValue(data, "valFloatWrapper", (Object)null);
        assertThat(data.getValFloatWrapper()).isNull();
    }

}
