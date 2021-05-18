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
import ru.olegcherednik.utils.reflection.exceptions.ClassNotFoundException;
import ru.olegcherednik.utils.reflection.exceptions.NoSuchConstructorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    public void shouldThrowNoSuchConstructorExceptionWhenConstructorNotFound() {
        assertThatThrownBy(() -> ConstructorUtils.invokeConstructor(Data.class, int.class, 12, long.class, 15L))
                .isExactlyInstanceOf(NoSuchConstructorException.class)
                .hasMessageContaining("Constructor with arguments '[int, long]' was not found in class ");
    }

    public void shouldThrowClassNotFoundExceptionWhenInvokeConstructorForNotExistedClass() {
        assertThatThrownBy(() -> ConstructorUtils.invokeConstructor("aaa.bbb.Data"))
                .isExactlyInstanceOf(ClassNotFoundException.class)
                .hasMessage("Class 'aaa.bbb.Data' was not found");
    }

}


