/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Oleg Cherednik
 * @since 10.05.2021
 */
@Test
public class ValidationUtilsTest {

    @SuppressWarnings("ConstantConditions")
    public void shouldThrowNullPointerExceptionWhenNullArgument() {
        assertThatThrownBy(() -> ValidationUtils.requireObjNonNull(null))
                .isExactlyInstanceOf(NullPointerException.class).hasMessage("'obj' should not be null");
        assertThatThrownBy(() -> ValidationUtils.requireFieldNonNull(null))
                .isExactlyInstanceOf(NullPointerException.class).hasMessage("'field' should not be null");
        assertThatThrownBy(() -> ValidationUtils.requireFieldNameNonNull(null))
                .isExactlyInstanceOf(NullPointerException.class).hasMessage("'fieldName' should not be null");
        assertThatThrownBy(() -> ValidationUtils.requireClsNonNull(null))
                .isExactlyInstanceOf(NullPointerException.class).hasMessage("'cls' should not be null");
        assertThatThrownBy(() -> ValidationUtils.requireTaskNonNull(null))
                .isExactlyInstanceOf(NullPointerException.class).hasMessage("'task' should not be null");
        assertThatThrownBy(() -> ValidationUtils.requireConstantNameNonNull(null))
                .isExactlyInstanceOf(NullPointerException.class).hasMessage("'constantName' should not be null");
        assertThatThrownBy(() -> ValidationUtils.requireSetExtraFieldTaskNonNull(null))
                .isExactlyInstanceOf(NullPointerException.class).hasMessage("'setExtraFieldTask' should not be null");
        assertThatThrownBy(() -> ValidationUtils.requireMethodNameNonNull(null))
                .isExactlyInstanceOf(NullPointerException.class).hasMessage("'methodName' should not be null");
        assertThatThrownBy(() -> ValidationUtils.requireMethodNonNull(null))
                .isExactlyInstanceOf(NullPointerException.class).hasMessage("'method' should not be null");
        assertThatThrownBy(() -> ValidationUtils.requireClassNameNonNull(null))
                .isExactlyInstanceOf(NullPointerException.class).hasMessage("'className' should not be null");
        assertThatThrownBy(() -> ValidationUtils.requireConstructorNonNull(null))
                .isExactlyInstanceOf(NullPointerException.class).hasMessage("'constructor' should not be null");
        assertThatThrownBy(() -> ValidationUtils.requireAccessibleObjectNonNull(null))
                .isExactlyInstanceOf(NullPointerException.class).hasMessage("'accessibleObject' should not be null");
    }

    public void shouldThrowNullPointerExceptionWhenSomeTypesDoNotExist() {
        assertThatThrownBy(() -> ValidationUtils.requireTypeNonNull(new Class[] { null }))
                .isExactlyInstanceOf(NullPointerException.class).hasMessage("'type' should not be null");
        assertThatThrownBy(() -> ValidationUtils.requireTypeNonNull(null, String.class))
                .isExactlyInstanceOf(NullPointerException.class).hasMessage("'type1' should not be null");
        assertThatThrownBy(() -> ValidationUtils.requireTypeNonNull(String.class, String.class, null))
                .isExactlyInstanceOf(NullPointerException.class).hasMessage("'type3' should not be null");
    }

    @SuppressWarnings({ "ConfusingArgumentToVarargsMethod", "ZeroLengthArrayAllocation", "RedundantArrayCreation" })
    public void shouldNotThrowExceptionWhenNoTypes() {
        assertThatCode(ValidationUtils::requireTypeNonNull).doesNotThrowAnyException();
        assertThatCode(() -> ValidationUtils.requireTypeNonNull(null)).doesNotThrowAnyException();
        assertThatCode(() -> ValidationUtils.requireTypeNonNull(new Class[0])).doesNotThrowAnyException();
    }

    @SuppressWarnings("ZeroLengthArrayAllocation")
    public void shouldThrowIllegalArgumentExceptionWhenLengthNotEquals() {
        assertThatThrownBy(() -> ValidationUtils.requireLengthMatch(new Class[] { String.class }, new Object[] { "anna", 12 }))
                .isExactlyInstanceOf(IllegalArgumentException.class).hasMessage("Length of 'types' and 'values' should be equal");
        assertThatThrownBy(() -> ValidationUtils.requireLengthMatch(new Class[] { String.class, int.class }, new Object[] { "anna" }))
                .isExactlyInstanceOf(IllegalArgumentException.class).hasMessage("Length of 'types' and 'values' should be equal");
        assertThatThrownBy(() -> ValidationUtils.requireLengthMatch(new Class[] { String.class }, null))
                .isExactlyInstanceOf(IllegalArgumentException.class).hasMessage("Length of 'types' and 'values' should be equal");
        assertThatThrownBy(() -> ValidationUtils.requireLengthMatch(null, new Object[] { "anna" }))
                .isExactlyInstanceOf(IllegalArgumentException.class).hasMessage("Length of 'types' and 'values' should be equal");
        assertThatThrownBy(() -> ValidationUtils.requireLengthMatch(new Class[] { String.class }, new Object[0]))
                .isExactlyInstanceOf(IllegalArgumentException.class).hasMessage("Length of 'types' and 'values' should be equal");
        assertThatThrownBy(() -> ValidationUtils.requireLengthMatch(new Class[0], new Object[] { "anna" }))
                .isExactlyInstanceOf(IllegalArgumentException.class).hasMessage("Length of 'types' and 'values' should be equal");
    }

    @SuppressWarnings({ "ZeroLengthArrayAllocation", "LambdaBodyCanBeCodeBlock" })
    public void shouldNotThrowExceptionWhenLengthEqual() {
        assertThatCode(() -> ValidationUtils.requireLengthMatch(null, null)).doesNotThrowAnyException();
        assertThatCode(() -> ValidationUtils.requireLengthMatch(new Class[0], null)).doesNotThrowAnyException();
        assertThatCode(() -> ValidationUtils.requireLengthMatch(null, new Object[0])).doesNotThrowAnyException();
        assertThatCode(() -> ValidationUtils.requireLengthMatch(new Class[0], new Object[0])).doesNotThrowAnyException();
        assertThatCode(() -> ValidationUtils.requireLengthMatch(new Class[] { String.class }, new Object[] { "anna" })).doesNotThrowAnyException();
    }

}
