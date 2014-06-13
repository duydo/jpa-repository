/*
 * @(#)Reflections.java Jul 30, 2012 2:51:24 PM
 * 
 * Copyright (c) 2012 Duy Do
 */
package com.duydo.repository.support;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Reflection utilities.
 *
 * @author Duy Do
 * @version $Id$
 */
public final class ReflectionUtils {
    private ReflectionUtils() {
    }

    private static Map<String, Object> propertyValues(Object obj, boolean nullValue) {
        if (obj == null) {
            return new HashMap<String, Object>();
        }

        final Map<String, Object> map = new HashMap<String, Object>();

        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    if (nullValue) {
                        map.put(field.getName(), value);
                    } else if (value != null) {
                        map.put(field.getName(), value);
                    }
                } catch (Exception e) {
                    // ignored
                }
            }
            clazz = clazz.getSuperclass();
        }
        return map;
    }

    public static Map<String, Object> propertyValues(Object obj) {
        return propertyValues(obj, true);
    }

    public static Map<String, Object> notNullPropertyValues(Object obj) {
        return propertyValues(obj, false);
    }
}
