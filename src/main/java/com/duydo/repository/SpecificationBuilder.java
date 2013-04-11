/*
 * @(#)SpecificationBuilder.java Jul 24, 2012 11:37:16 AM
 * 
 * Copyright (c) 2012 Duy Do
 */
package com.duydo.repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Duy Do
 * @version $Id$
 */
public class SpecificationBuilder {

	public static enum Operator {
		AND,
		OR;
	}

	private String property;
	private Path<?> path;
	private Operator operator = Operator.AND;

	private Set<Specification<?>> set = new HashSet<Specification<?>>();

	public static SpecificationBuilder forProperty(String name) {
		return new SpecificationBuilder(name);
	}

	public SpecificationBuilder operator(Operator operator) {
		this.operator = operator;
		return this;
	}

	@SuppressWarnings("serial")
	public <T> SpecificationBuilder isNull() {
		set.add(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb,
					CriteriaQuery<?> cq, Root<T> root) {
				return path(root).isNull();
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T> SpecificationBuilder isNotNull() {
		set.add(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb,
					CriteriaQuery<?> cq, Root<T> root) {
				return path(root).isNotNull();
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T> SpecificationBuilder in(final Collection<?> values) {
		set.add(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb,
					CriteriaQuery<?> cq, Root<T> root) {
				return path(root).in(values);
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T> SpecificationBuilder like(final String pattern) {
		set.add(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb,
					CriteriaQuery<?> cq, Root<T> root) {
				return cb.like(path(root).as(String.class), pattern);
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T> SpecificationBuilder equal(final Object value) {
		set.add(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb,
					CriteriaQuery<?> cq, Root<T> root) {
				return cb.equal(path(root), value);
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T, V extends Comparable<V>> SpecificationBuilder lt(final V value) {
		set.add(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb,
					CriteriaQuery<?> cq, Root<T> root) {
				final Expression<V> exp = path(root);
				return cb.lessThan(exp, value);
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T, V extends Comparable<? super V>> SpecificationBuilder lte(
			final V value) {
		set.add(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb,
					CriteriaQuery<?> cq, Root<T> root) {
				final Expression<V> exp = path(root);
				return cb.lessThanOrEqualTo(exp, value);
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T, V extends Comparable<V>> SpecificationBuilder gt(final V value) {
		set.add(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb,
					CriteriaQuery<?> cq, Root<T> root) {
				final Expression<V> exp = path(root);
				return cb.greaterThan(exp, value);
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T, V extends Comparable<? super V>> SpecificationBuilder gte(
			final V value) {
		set.add(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb,
					CriteriaQuery<?> cq, Root<T> root) {
				final Expression<V> exp = path(root);
				return cb.greaterThanOrEqualTo(exp, value);
			}
		});
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> Specification<T> build() {
		Iterator<Specification<?>> iter = set.iterator();
		Specification<T> spec = null;
		while (iter.hasNext()) {

			if (spec == null) {
				spec = (Specification<T>) iter.next();
				continue;
			}

			if (operator == Operator.AND) {
				spec = spec.and((Specification<T>) iter.next());
			} else {
				spec = spec.or((Specification<T>) iter.next());
			}
		}
		return spec;
	}

	@SuppressWarnings("unchecked")
	private final <T, V> Path<V> path(Root<T> root) {
		if (path == null) {
			if (property.indexOf('.') == -1) {
				path = root.get(property);
			} else {
				String[] properties = property.split(".");
				path = root.get(properties[0]);
				for (int i = 1; i < properties.length; i++) {
					path = path.get(properties[i]);
				}
			}
		}
		return (Path<V>) path;
	}

	private SpecificationBuilder(String propertyName) {
		this.property = propertyName;
	}
}
