package ru.olegcherednik.utils.reflection;

/**
 * @author Oleg Cherednik
 * @since 11.05.2021
 */
public interface Consumer<T> {

    Consumer<?> NULL = (Consumer<Object>)obj -> { };

    void accept(T t) throws Exception;

}
