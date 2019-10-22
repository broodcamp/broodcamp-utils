/**
 * (C) Copyright 2019 Edward P. Legaspi (https://github.com/czetsuya).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.broodcamp.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * Collection of utility methods for managing collections.
 * 
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public class CollectionUtils {

    private CollectionUtils() {

    }

    /**
     * Computes the cartersian product of a list. For example we have the following
     * list:
     * 
     * <pre>
     * [
     *  [A, B], [1, 2], [x, y]
     * ]
     * </pre>
     * 
     * Then the resulting cartesian product should be:
     * 
     * <pre>
     * [
     *  [A, 1, x], [A, 1, y], [A, 2, x], [A, 2, y],
     *  [B, 1, x], [B, 1, y], [B, 2, x], [B, 2, y]
     * ]
     * </pre>
     * 
     * @param elements list of list of objects
     * @return cartesian product of the list of list
     */
    public static List<List<Object>> cartersianProduct(Collection<Collection<?>> elements) {

        List<ImmutableList<?>> immutableElements = makeListofImmutable(elements);

        return Lists.cartesianProduct(immutableElements);
    }

    /**
     * Converts to {@linkplain LinkedList} of {@linkplain ImmutableList} object.
     * 
     * @param listOfValues list of values to be converted
     * @return the converted values
     */
    public static List<ImmutableList<?>> makeListofImmutable(Collection<Collection<?>> listOfValues) {

        List<ImmutableList<?>> converted = new LinkedList<>();
        listOfValues.forEach(array -> {
            converted.add(ImmutableList.copyOf(array));
        });

        return converted;
    }
}
