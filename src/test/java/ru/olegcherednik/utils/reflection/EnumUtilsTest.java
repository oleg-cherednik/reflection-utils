package ru.olegcherednik.utils.reflection;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
@Test
public class EnumUtilsTest {

    public void shouldAddConstantsToEnumWhenAddConstant() throws Throwable {
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

    enum CarBrand {
        BMW,
        MERCEDES
    }


}
