/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.usp.larc.Benchmark.ApacheCommons;

import br.usp.larc.Modifier.LogFile;

/**
 * <p>
 * Operations on arrays, primitive arrays (like {@code int[]}) and primitive wrapper arrays (like {@code Integer[]}).
 * </p>
 * <p>
 * This class tries to handle {@code null} input gracefully. An exception will not be thrown for a {@code null} array
 * input. However, an Object array that contains a {@code null} element may throw an exception. Each method documents
 * its behavior.
 * </p>
 * <p>
 * Package private, might move to an internal package if this needs to be public.
 * </p>
 * <p>
 * #ThreadSafe#
 * </p>
 *
 * @since 4.2 (Copied from Apache Commons Lang.)
 */
public class ArrayUtilsInstrumented {

    /**
     * Don't allow instances.
     */
    private ArrayUtilsInstrumented() {
    }

    /**
     * <p>
     * Checks if the object is in the given array.
     * </p>
     *
     * <p>
     * The method returns {@code false} if a {@code null} array is passed in.
     * </p>
     *
     * @param array
     *            the array to search through
     * @param objectToFind
     *            the object to find
     * @return {@code true} if the array contains the object
     */
    public static boolean contains(final Object[] array, final Object objectToFind) {
        return indexOf(array, objectToFind) != CollectionUtils.INDEX_NOT_FOUND;
    }

    /**
     * <p>
     * Finds the index of the given object in the array.
     * </p>
     *
     * <p>
     * This method returns {@link CollectionUtils#INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * @param array
     *            the array to search through for the object, may be {@code null}
     * @param objectToFind
     *            the object to find, may be {@code null}
     * @return the index of the object within the array, {@link CollectionUtils#INDEX_NOT_FOUND} ({@code -1}) if not found or
     *         {@code null} array input
     */
    public static <T> int indexOf(final T[] array, final Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }

    /**
     * <p>
     * Finds the index of the given object in the array starting at the given index.
     * </p>
     *
     * <p>
     * This method returns {@link CollectionUtils#INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     * </p>
     *
     * <p>
     * A negative startIndex is treated as zero. A startIndex larger than the array length will return
     * {@link CollectionUtils#INDEX_NOT_FOUND} ({@code -1}).
     * </p>
     *
     * @param array
     *            the array to search through for the object, may be {@code null}
     * @param objectToFind
     *            the object to find, may be {@code null}
     * @param startIndex
     *            the index to start searching at
     * @return the index of the object within the array starting at the index, {@link CollectionUtils#INDEX_NOT_FOUND} ({@code -1}) if
     *         not found or {@code null} array input
     */
    public static int indexOf(final Object[] array, final Object objectToFind, int startIndex) {
        LogFile.write("ArrayUtils#indexOf", "ifStmt", "array == null", array == null, "[array]", String.valueOf(array));
        if (array == null) {
            return CollectionUtils.INDEX_NOT_FOUND;
        }
        LogFile.write("ArrayUtils#indexOf", "ifStmt", "startIndex < 0", startIndex < 0, "[startIndex]", String.valueOf(startIndex));
        if (startIndex < 0) {
            startIndex = 0;
        }
        LogFile.write("ArrayUtils#indexOf", "ifStmt", "objectToFind == null", objectToFind == null, "[objectToFind]", String.valueOf(objectToFind));
        if (objectToFind == null) {
            for (int i = startIndex; i < array.length; i++) {
                LogFile.write("ArrayUtils#indexOf", "ifStmt", "array[i] == null", array[i] == null);
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i < array.length; i++) {
                LogFile.write("ArrayUtils#indexOf", "ifStmt", "objectToFind.equals(array[i])", objectToFind.equals(array[i]), "[objectToFind]", String.valueOf(objectToFind));
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return CollectionUtils.INDEX_NOT_FOUND;
    }
}
