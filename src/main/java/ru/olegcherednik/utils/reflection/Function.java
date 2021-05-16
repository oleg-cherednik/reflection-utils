package ru.olegcherednik.utils.reflection;

/**
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 * @author Oleg Cherednik
 * @since 11.05.2021
 */
public interface Function<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t) throws Exception;

}
