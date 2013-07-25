/*
 * @(#)SpecificationBuilder.java Jul 24, 2012 11:37:16 AM
 * 
 * Copyright (c) 2012 Duy Do
 */
package com.duydo.repository.spec;

import java.util.Collection;

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
	private Specification<?> spec;

	public static SpecificationBuilder forProperty(String name) {
		return new SpecificationBuilder(name);
	}

	public SpecificationBuilder withOperator(Operator operator) {
		this.operator = operator;
		return this;
	}

	public SpecificationBuilder and() {
		return withOperator(Operator.AND);
	}

	public SpecificationBuilder or() {
		return withOperator(Operator.OR);
	}

	@SuppressWarnings("serial")
	public <T> SpecificationBuilder isNull() {
		build(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<T> root) {
				return path(root).isNull();
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T> SpecificationBuilder isNotNull() {
		build(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<T> root) {
				return path(root).isNotNull();
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T> SpecificationBuilder in(final Collection<?> values) {
		build(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<T> root) {
				return path(root).in(values);
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T> SpecificationBuilder like(final String pattern) {
		build(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<T> root) {
				return cb.like(path(root).as(String.class), pattern);
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T> SpecificationBuilder equal(final Object value) {
		build(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<T> root) {
				return cb.equal(path(root), value);
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T, V extends Comparable<V>> SpecificationBuilder lessThan(final V value) {
		build(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<T> root) {
				final Expression<V> exp = path(root);
				return cb.lessThan(exp, value);
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T, V extends Comparable<? super V>> SpecificationBuilder lessThanOrEqualTo(final V value) {
		build(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<T> root) {
				final Expression<V> exp = path(root);
				return cb.lessThanOrEqualTo(exp, value);
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T, V extends Comparable<V>> SpecificationBuilder greaterThan(final V value) {
		build(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<T> root) {
				final Expression<V> exp = path(root);
				return cb.greaterThan(exp, value);
			}
		});
		return this;
	}

	@SuppressWarnings("serial")
	public <T, V extends Comparable<? super V>> SpecificationBuilder greaterThanOrEqualTo(final V value) {
		build(new AbstractSpecification<T>() {
			@Override
			public Predicate toPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq, Root<T> root) {
				final Expression<V> exp = path(root);
				return cb.greaterThanOrEqualTo(exp, value);
			}
		});
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> Specification<T> build() {
		return (Specification<T>) this.spec;
	}

	@SuppressWarnings("unchecked")
	private <T> void build(Specification<T> spec) {
		if (this.spec == null) {
			this.spec = spec;
		} else {
			if (Operator.OR == operator) {
				this.spec = ((Specification<T>) this.spec).or(spec);
			} else {
				this.spec = ((Specification<T>) this.spec).and(spec);
			}
		}
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
