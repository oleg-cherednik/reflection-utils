package ru.olegcherednik.utils.reflection.data;

/**
 * @author Oleg Cherednik
 * @since 27.02.2021
 */
@SuppressWarnings("unused")
abstract class BaseData {

    private static final String MONITOR = "dell";

    @SuppressWarnings("FieldMayBeStatic")
    private final String baseName = "pizza";

    private static int getVoltage() {
        return 220;
    }

    @SuppressWarnings("MethodMayBeStatic")
    private String getSeason() {
        return "winter";
    }

}
