package com.broodcamp.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
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
