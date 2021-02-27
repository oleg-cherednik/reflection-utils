package ru.olegcherednik.utils.reflection.data;

/**
 * @author Oleg Cherednik
 * @since 27.02.2021
 */
public class Data extends BaseData {

    private static final String AUTO = "ferrari";

    private final String name;
    private final int age;
    private final boolean marker;

    public static Data create() {
        return new Data();
    }

    public static Data create(String name) {
        return new Data(name);
    }

    public static Data create(String name, int age) {
        return new Data(name, age);
    }

    public static Data create(String name, int age, boolean marker) {
        return new Data(name, age, marker);
    }

    private Data() {
        this("oleg.cherednik");
    }

    private Data(String name) {
        this(name, 666);
    }

    private Data(String name, int age) {
        this(name, age, false);
    }

    private Data(String name, int age, boolean marker) {
        this.name = name;
        this.age = age;
        this.marker = marker;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isMarker() {
        return marker;
    }

    private String getCity() {
        return "Saint-Petersburg";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Data))
            return false;
        Data data = (Data)obj;
        return age == data.age &&
                marker == data.marker &&
                name.equals(data.name);
    }

}
