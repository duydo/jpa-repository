/*
 * @(#)PropertySpecification.java Jun 8, 2011 5:57:36 PM
 * 
 * Copyright (c) 2011 Duy Do
 * 
 * Permission is hereby granted, free of charge, to
 * any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.duydo.jpa.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Duy Do
 * @version $Id$
 */
@SuppressWarnings("serial")
public class Specifications {
	public static <T> Specification<T> equal(final String propertyName, final Object propertyValue) {
		return new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(final CriteriaBuilder cb, final CriteriaQuery<?> cq, final Root<T> root) {
				return cb.equal(root.get(propertyName), propertyValue);
			}
		};
	}

	public static <T> Specification<T> like(final String propertyName, final String pattern) {
		return new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(final CriteriaBuilder cb, final CriteriaQuery<?> cq, final Root<T> root) {
				return cb.like(root.get(propertyName).as(String.class), pattern);
			}
		};
	}
	
	public static <T> Specification<T> isNull(final String propertyName) {
		return new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(final CriteriaBuilder cb, final CriteriaQuery<?> cq, final Root<T> root) {
				return cb.isNull(root.get(propertyName));
			}
		};
	}

	public static <T, E extends Comparable<? super E>> Specification<T> lessThan(final String propertyName,
			final E propertyValue, final Class<E> propertyClass) {
		return new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(final CriteriaBuilder cb, final CriteriaQuery<?> cq, final Root<T> root) {
				return cb.lessThan(root.get(propertyName).as(propertyClass), propertyValue);
			}
		};
	}

	public static <T, E extends Comparable<? super E>> Specification<T> lessThanOrEqualTo(final String propertyName,
			final E propertyValue, final Class<E> propertyClass) {
		return new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(final CriteriaBuilder cb, final CriteriaQuery<?> cq, final Root<T> root) {
				return cb.lessThanOrEqualTo(root.get(propertyName).as(propertyClass), propertyValue);
			}
		};
	}

	public static <T, E extends Comparable<? super E>> Specification<T> greaterThan(final String propertyName,
			final E propertyValue, final Class<E> propertyClass) {
		return new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(final CriteriaBuilder cb, final CriteriaQuery<?> cq, final Root<T> root) {
				return cb.greaterThan(root.get(propertyName).as(propertyClass), propertyValue);
			}
		};
	}

	public static <T, E extends Comparable<? super E>> Specification<T> greaterThanOrEqual(final String propertyName,
			final E propertyValue, final Class<E> propertyClass) {
		return new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(final CriteriaBuilder cb, final CriteriaQuery<?> cq, final Root<T> root) {
				return cb.greaterThan(root.get(propertyName).as(propertyClass), propertyValue);
			}
		};
	}
}
