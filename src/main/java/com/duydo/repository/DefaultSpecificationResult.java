/*
 * @(#)SpecificationResultImpl.java Jun 30, 2011 1:46:35 PM
 * 
 * Copyright (c) 2011 Duy Do
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
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
package com.duydo.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import com.duydo.repository.spec.Specification;

/**
 * @author Duy Do
 * @version $Id$
 */
class DefaultSpecificationResult<T> implements SpecificationResult<T> {
	private final EntityManager em;
	private final CriteriaBuilder cb;
	private final CriteriaQuery<T> cq;
	private final Root<T> root;
	private final Specification<T> spec;
	private int from;
	private int size;

	private final List<Order> orders = new ArrayList<Order>();
	private final Map<String, Object> hints = new HashMap<String, Object>();

	public DefaultSpecificationResult(Class<T> clazz, Specification<T> spec, EntityManager em) {
		this.spec = spec;
		this.em = em;
		this.cb = em.getCriteriaBuilder();
		this.cq = cb.createQuery(clazz);
		this.root = cq.from(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	public SpecificationResult<T> from(int from) {
		this.from = from;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public SpecificationResult<T> size(int size) {
		this.size = size;
		return this;
	}

	public SpecificationResult<T> ascending(String propertyName) {
		orders.add(cb.asc(root.get(propertyName)));
		return this;
	}

	public SpecificationResult<T> descending(String propertyName) {
		orders.add(cb.desc(root.get(propertyName)));
		return this;
	}

	public SpecificationResult<T> hint(String name, Object value) {
		hints.put(name, value);
		return this;
	}

	public T single() {
		// better than query.getSingleResult()???

		size(1);
		final List<T> result = list();
		if (!result.isEmpty()) {
			return result.get(0);
		}

		return null;
	}

	public List<T> list() {
		try {
			return buildQuery().getResultList();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	private TypedQuery<T> buildQuery() {

		if (spec != null) {
			cq.where(spec.toPredicate(cb, cq, root));
		}

		if (!orders.isEmpty()) {
			cq.orderBy(orders);
		}

		final TypedQuery<T> query = em.createQuery(cq);

		if (from >= 0) {
			query.setFirstResult(from);
		}

		if (size > 0) {
			query.setMaxResults(size);
		}
		
		for(Map.Entry<String, Object> hint : hints.entrySet()) {
			query.setHint(hint.getKey(), hint.getValue());
		}

		return query;
	}

}
