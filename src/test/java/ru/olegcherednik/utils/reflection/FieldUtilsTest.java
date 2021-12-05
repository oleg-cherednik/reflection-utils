/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package ru.olegcherednik.utils.reflection;

import org.testng.annotations.Test;
import ru.olegcherednik.utils.reflection.data.Data;
import ru.olegcherednik.utils.reflection.data.TypeData;
import ru.olegcherednik.utils.reflection.exceptions.NoSuchFieldException;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Oleg Cherednik
 * @since 27.02.2021
 */
@Test
public class FieldUtilsTest {

    public void shouldRetrieveFieldValueWhenGetFieldValueByField() throws java.lang.NoSuchFieldException {
        Data data = Data.create();
        Field field = data.getClass().getDeclaredField("name");
        String actual = FieldUtils.getFieldValue(data, field);
        assertThat(actual).isEqualTo("oleg.cherednik");
    }

    public void shouldRetrieveFieldValueWhenGetFieldValueByFieldName() {
        Data data = Data.create();
        String actual = FieldUtils.getFieldValue(data, "name");
        assertThat(actual).isEqualTo("oleg.cherednik");
    }

    public void shouldRetrieveFieldValueWhenGetFieldValueFromBaseClassByFieldName() {
        Data data = Data.create();
        String actual = FieldUtils.getFieldValue(data, "baseName");
        assertThat(actual).isEqualTo("pizza");
    }

    public void shouldThrowNoSuchElementExceptionWhenGetNotExistedFieldByFieldName() {
        assertThatThrownBy(() -> FieldUtils.getFieldValue(Data.create(), "unknownName"))
                .isExactlyInstanceOf(NoSuchFieldException.class);
    }

    public void shouldRetrieveFieldTypeWhenGetType() throws java.lang.NoSuchFieldException {
        Data data = Data.create();
        Field field = data.getClass().getDeclaredField("name");
        assertThat(FieldUtils.getType(field)).isSameAs(String.class);
    }

    public void shouldRetrieveFieldTypeWhenGetTypeForExistedField() throws java.lang.NoSuchFieldException {
        Data data = Data.create();
        Field field = data.getClass().getDeclaredField("name");
        assertThat(FieldUtils.getType(field, Number.class)).isSameAs(String.class);
    }

    public void shouldRetrieveDefaultTypeWhenGetTypeForNotExistedFieldWithDefaultValue() {
        assertThat(FieldUtils.getType(null, Number.class)).isSameAs(Number.class);
    }

    public void shouldRetrieveStaticFieldValueWhenGetStaticFieldValueByField() throws java.lang.NoSuchFieldException {
        Field field = Data.class.getDeclaredField("AUTO");
        String actual = FieldUtils.getStaticFieldValue(field);
        assertThat(actual).isEqualTo("ferrari");
    }

    public void shouldRetrieveStaticFieldValueWhenGetStaticFieldValueByFieldName() {
        String actual = FieldUtils.getStaticFieldValue(Data.class, "AUTO");
        assertThat(actual).isEqualTo("ferrari");
    }

    public void shouldRetrieveStaticFieldValueWhenGetStaticFieldValueFromBaseClassByFieldName() {
        Data data = Data.create();
        String actual = FieldUtils.getFieldValue(data, "MONITOR");
        assertThat(actual).isEqualTo("dell");
    }

    public void shouldSetFieldValueWhenSetFieldValueByField() throws java.lang.NoSuchFieldException {
        Data data = Data.create();
        assertThat(data.getName()).isNotEqualTo("moskow");

        Field field = data.getClass().getDeclaredField("name");
        FieldUtils.setFieldValue(data, field, "moskow");
        assertThat(data.getName()).isEqualTo("moskow");
    }

    public void shouldSetFieldValueWhenSetFieldValueByFieldName() {
        Data data = Data.create();
        assertThat(data.getName()).isNotEqualTo("moskow");

        FieldUtils.setFieldValue(data, "name", "moskow");
        assertThat(data.getName()).isEqualTo("moskow");
    }

    public void shouldSetFieldValueWhenSetFieldValueByFieldWithConsumer() throws java.lang.NoSuchFieldException {
        Data data = Data.create();
        assertThat(data.getName()).isNotEqualTo("moskow");

        Field field = data.getClass().getDeclaredField("name");
        FieldUtils.setFieldValue(field, f -> f.set(data, "moskow"));
        assertThat(data.getName()).isEqualTo("moskow");
    }

    public void shouldSetFieldValueWhenSetFieldValueByFieldNameWithConsumer() {
        Data data = Data.create();
        assertThat(data.getName()).isNotEqualTo("moskow");

        FieldUtils.setFieldValue(data, "name", field -> field.set(data, "moskow"));
        assertThat(data.getName()).isEqualTo("moskow");
    }

    public void shouldSetStaticFieldValueWhenSetFieldValueByField() throws java.lang.NoSuchFieldException {
        Field field = Data.class.getDeclaredField("AUTO");
        assertThat((String)FieldUtils.getStaticFieldValue(field)).isEqualTo("ferrari");

        FieldUtils.setStaticFieldValue(field, "audi");
        assertThat((String)FieldUtils.getStaticFieldValue(field)).isEqualTo("audi");

        FieldUtils.setStaticFieldValue(field, "ferrari");
        assertThat((String)FieldUtils.getStaticFieldValue(field)).isEqualTo("ferrari");
    }

    public void shouldSetStaticFieldValueWhenSetFieldValueByFieldName() {
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("ferrari");

        FieldUtils.setStaticFieldValue(Data.class, "AUTO", "audi");
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("audi");

        FieldUtils.setStaticFieldValue(Data.class, "AUTO", "ferrari");
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("ferrari");
    }

    public void shouldSetStaticFieldValueWhenSetFieldValueByFieldNameInBaseClass() {
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "MONITOR")).isEqualTo("dell");

        FieldUtils.setStaticFieldValue(Data.class, "MONITOR", "acer");
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "MONITOR")).isEqualTo("acer");

        FieldUtils.setStaticFieldValue(Data.class, "MONITOR", "dell");
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "MONITOR")).isEqualTo("dell");
    }

    public void shouldSetStaticFieldValueWhenSetFieldValueByFieldConsumer() throws java.lang.NoSuchFieldException {
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("ferrari");

        Field field = Data.class.getDeclaredField("AUTO");
        FieldUtils.setStaticFieldValue(field, f -> f.set(null, "audi"));
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("audi");

        FieldUtils.setStaticFieldValue(field, f -> f.set(null, "ferrari"));
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("ferrari");
    }

    public void shouldSetStaticFieldValueWhenSetFieldValueByFieldNameConsumer() {
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("ferrari");

        FieldUtils.setStaticFieldValue(Data.class, "AUTO", field -> field.set(null, "audi"));
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("audi");

        FieldUtils.setStaticFieldValue(Data.class, "AUTO", field -> field.set(null, "ferrari"));
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "AUTO")).isEqualTo("ferrari");
    }

    public void shouldSetStaticFieldValueWhenSetFieldValueByFieldConsumerInBaseClass() throws java.lang.NoSuchFieldException {
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "MONITOR")).isEqualTo("dell");

        FieldUtils.setStaticFieldValue(Data.class, "MONITOR", f -> f.set(null, "acer"));
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "MONITOR")).isEqualTo("acer");

        FieldUtils.setStaticFieldValue(Data.class, "MONITOR", f -> f.set(null, "dell"));
        assertThat((String)FieldUtils.getStaticFieldValue(Data.class, "MONITOR")).isEqualTo("dell");
    }

    public void shouldSetIntFieldValueWhenSetFieldValue() {
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

    public void shouldSetBooleanFieldValueWhenSetFieldValue() {
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

    public void shouldSetByteFieldValueWhenSetFieldValue() {
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

    public void shouldSetCharFieldValueWhenSetFieldValue() {
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

    public void shouldSetDoubleFieldValueWhenSetFieldValue() {
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

    public void shouldSetLongFieldValueWhenSetFieldValue() {
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

    public void shouldSetShortFieldValueWhenSetFieldValue() {
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

    public void shouldSetFloatFieldValueWhenSetFieldValue() {
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

    public void shouldThrowNoSuchElementExceptionWhenGetNotExistedStaticFieldByFieldName() {
        assertThatThrownBy(() -> FieldUtils.getStaticFieldValue(Data.class, "unknownName"))
                .isExactlyInstanceOf(NoSuchFieldException.class);
    }

    public void shouldRetrieveTrueWhenGivenClassContainField() {
        assertThat(FieldUtils.isFieldExist(Data.class, "name")).isTrue();
        assertThat(FieldUtils.isFieldExist(Data.class, "AUTO")).isTrue();
        assertThat(FieldUtils.isFieldExist(Data.class.getName(), "name")).isTrue();
        assertThat(FieldUtils.isFieldExist(Data.class.getName(), "AUTO")).isTrue();

        assertThat(FieldUtils.isFieldExist(Data.class, "baseName")).isFalse();
        assertThat(FieldUtils.isFieldExist(Data.class, "MONITOR")).isFalse();
        assertThat(FieldUtils.isFieldExist(Data.class.getName(), "baseName")).isFalse();
        assertThat(FieldUtils.isFieldExist(Data.class.getName(), "MONITOR")).isFalse();
    }

    public void shouldRetrieveTrueWhenClassOrParentsContainField() {
        assertThat(FieldUtils.isFieldExistIncludeParents(Data.class, "name")).isTrue();
        assertThat(FieldUtils.isFieldExistIncludeParents(Data.class, "AUTO")).isTrue();
        assertThat(FieldUtils.isFieldExistIncludeParents(Data.class.getName(), "name")).isTrue();
        assertThat(FieldUtils.isFieldExistIncludeParents(Data.class.getName(), "AUTO")).isTrue();

        assertThat(FieldUtils.isFieldExistIncludeParents(Data.class, "baseName")).isTrue();
        assertThat(FieldUtils.isFieldExistIncludeParents(Data.class, "MONITOR")).isTrue();
        assertThat(FieldUtils.isFieldExistIncludeParents(Data.class.getName(), "baseName")).isTrue();
        assertThat(FieldUtils.isFieldExistIncludeParents(Data.class.getName(), "MONITOR")).isTrue();
    }

    public void shouldRetrieveFalseWhenClassOrParentsNotContainField() {
        assertThat(FieldUtils.isFieldExistIncludeParents(Data.class, "unknown")).isFalse();
    }


}
