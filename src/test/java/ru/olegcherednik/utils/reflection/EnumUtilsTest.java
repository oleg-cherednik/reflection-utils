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
import ru.olegcherednik.utils.reflection.exceptions.ReflectionUtilsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
@Test
public class EnumUtilsTest {

    public void shouldAddConstantsToEnumWhenAddConstant() {
        assertThat(CarBrand.class.getEnumConstants()).extracting(Enum::name).containsExactly(CarBrand.BMW.name(), CarBrand.MERCEDES.name());
        assertThat(CarBrand.values()).extracting(Enum::name).containsExactly(CarBrand.BMW.name(), CarBrand.MERCEDES.name());

        for (CarBrand brand : CarBrand.values())
            assertThat(CarBrand.valueOf(brand.name())).isNotNull();

        final String audi = "AUDI";
        final String volkswagen = "VOLKSWAGEN";

        EnumUtils.addConstant(CarBrand.class, audi);
        EnumUtils.addConstant(CarBrand.class, volkswagen);

        assertThat(CarBrand.class.getEnumConstants()).extracting(Enum::name)
                                                     .containsExactly(CarBrand.BMW.name(), CarBrand.MERCEDES.name(), audi, volkswagen);
        assertThat(CarBrand.values()).extracting(Enum::name)
                                     .containsExactly(CarBrand.BMW.name(), CarBrand.MERCEDES.name(), audi, volkswagen);

        for (CarBrand brand : CarBrand.values())
            assertThatCode(() -> CarBrand.valueOf(brand.name())).doesNotThrowAnyException();
    }

    public void shouldThrowIllegalArgumentExceptionWhenConstantWithGivenNameExists() {
        assertThatThrownBy(() -> EnumUtils.addConstant(CarBrand.class, CarBrand.BMW.name()))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("Enum '%s' already has a constant with name '%s'", CarBrand.class, CarBrand.BMW.name()));
    }

    public void shouldThrowReflectionUtilsExceptionWhenExceptionExists() {
        assertThatThrownBy(() -> EnumUtils.addConstant(CarBrand.class, "PORSCHE", carBrand -> {
            throw new IllegalArgumentException("xxx");
        }))
                .isExactlyInstanceOf(ReflectionUtilsException.class)
                .hasCauseInstanceOf(IllegalArgumentException.class)
                .hasMessage("java.lang.IllegalArgumentException: xxx");

    }

    enum CarBrand {
        BMW,
        MERCEDES
    }

}
