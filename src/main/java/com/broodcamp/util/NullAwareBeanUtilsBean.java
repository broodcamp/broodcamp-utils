/**
 * An Open Source Inventory and Sales Management System
 * Copyright (C) 2019 Edward P. Legaspi (https://github.com/czetsuya)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.broodcamp.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;

/**
 * Custom {@link BeanUtilsBean} that does not copy null values.
 * 
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public class NullAwareBeanUtilsBean extends BeanUtilsBean {

    /**
     * Copy source property to destination. Null value will not be copy. To nullify
     * a field set it to an empty space.
     */
    @Override
    public void copyProperty(Object dest, String name, Object value) throws IllegalAccessException, InvocationTargetException {

        if (value == null) {
            return;
        }

        if (value == "") {
            value = null;
        }

        super.copyProperty(dest, name, value);
    }
}
