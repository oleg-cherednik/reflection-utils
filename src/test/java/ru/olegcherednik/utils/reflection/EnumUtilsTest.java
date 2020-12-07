package ru.olegcherednik.utils.reflection;

import java.util.Arrays;

/**
 * @author Oleg Cherednik
 * @since 06.12.2020
 */
//@Test
public class EnumUtilsTest {

    public static void main(String... args) throws Throwable {
        Arrays.stream(Car.values())
              .map(Enum::name)
              .forEach(System.out::println);

        System.out.println();

        EnumUtils.addValue(Car.class, "AUDI");
        EnumUtils.addValue(Car.class, "VOLKSWAGEN");

        Car[] a = Car.class.getEnumConstants();
        Car[] b = Car.values();
        Car c = Car.valueOf("AUDI");


        Arrays.stream(Car.values())
              .map(Enum::name)
              .forEach(System.out::println);
    }

    enum Car {
        BMW,
        MERCEDES
    }


}
