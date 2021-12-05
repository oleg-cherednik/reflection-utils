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

import ru.olegcherednik.utils.reflection.exceptions.ClassNotFoundException;
import ru.olegcherednik.utils.reflection.exceptions.ReflectionUtilsException;

/**
 * @author Oleg Cherednik
 * @since 05.12.2021
 */
final class ClassUtils {

    /**
     * Retrieve {@link Class} instance with given {@code className}.
     *
     * @param className not {@literal null} class name
     * @param <T>       type of the class that contains the constructor
     * @return not {@literal null} class
     * @throws NullPointerException     in case of any of required parameters is {@literal null}
     * @throws ReflectionUtilsException in case of any checked exception is thrown
     * @throws ClassNotFoundException   in case of class was not found
     * @throws RuntimeException         in case if any other problem
     */
    public static <T> Class<T> getClass(String className) {
        ValidationUtils.requireClassNameNonNull(className);

        try {
            return (Class<T>)Class.forName(className);
        } catch (java.lang.ClassNotFoundException e) {
            throw new ClassNotFoundException(className);
        }
    }

    private ClassUtils() {}

}
