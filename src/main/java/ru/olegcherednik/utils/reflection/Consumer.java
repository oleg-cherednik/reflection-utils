/*
 * Copyright 2021 Oleg Cherednik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
     * @throws Exception in case of any problem
     */
    void accept(T t) throws Exception;

}
