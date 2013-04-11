/*
 * @(#)Reflections.java Jul 30, 2012 2:51:24 PM
 * 
 * Copyright (c) 2012 Duy Do
 */
package com.duydo.repository;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Duy Do
 * @version $Id$
 */
public class Reflections {
	public static Map<String, Object> propertyValues(Object obj) {
		if (obj == null) {
			return new HashMap<String, Object>();
		}

		final Map<String, Object> map = new HashMap<String, Object>();

		Class<?> clazz = obj.getClass();
		while (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				try {
					field.setAccessible(true);
					map.put(field.getName(), field.get(obj));
				} catch (Exception e) {
					// ignored
				}
			}
			clazz = clazz.getSuperclass();
		}
		return map;
	}

	public static Map<String, Object> notNullPropertyValues(Object obj) {
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
					if (value != null) {
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
}
