package ru.olegcherednik.utils.reflection;

/**
 * @param <T> the type of the input to the operation
 * @author Oleg Cherednik
 * @since 11.05.2021
 */
public interface Consumer<T> {

    Consumer<?> NULL = (Consumer<Object>)t -> { };

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t) throws Exception;

}
