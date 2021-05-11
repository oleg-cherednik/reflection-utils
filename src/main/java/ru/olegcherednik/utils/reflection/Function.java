package ru.olegcherednik.utils.reflection;

/**
 * @author Oleg Cherednik
 * @since 11.05.2021
 */
public interface Function<T, R> {

    R apply(T t) throws Exception;

}
