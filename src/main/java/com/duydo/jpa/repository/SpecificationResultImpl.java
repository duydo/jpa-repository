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
package com.duydo.jpa.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import com.duydo.jpa.specification.Specification;

/**
 * @author Duy Do
 * @version $Id$
 */
public class SpecificationResultImpl<T> implements SpecificationResult<T> {
	private final EntityManager em;
	private final Specification<T> specification;

	private final CriteriaBuilder cb;
	private final CriteriaQuery<T> cq;
	private final Root<T> root;

	private int first;
	private int max;
	private final List<Order> orders = new ArrayList<Order>();

	public SpecificationResultImpl(Class<T> type,
			Specification<T> specification, EntityManager em) {
		this.specification = specification;
		this.em = em;
		this.cb = em.getCriteriaBuilder();
		this.cq = cb.createQuery(type);
		this.root = cq.from(type);
	}

	/**
	 * {@inheritDoc}
	 */
	public SpecificationResult<T> skip(int count) {
		this.first = count;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public SpecificationResult<T> get(int count) {
		this.max = count;
		return this;
	}

	public SpecificationResult<T> sortAscending(String expression) {
		orders.add(cb.asc(root.get(expression)));
		return this;
	}

	public SpecificationResult<T> sortDescending(String property) {
		orders.add(cb.desc(root.get(property)));
		return this;
	}

	public T asSingle() {
		// better than query.getSingleResult()???
		final List<T> result = asList();
		return result.isEmpty() ? null : result.iterator().next();
	}

	public List<T> asList() {
		try {
			return buildQuery().getResultList();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	private TypedQuery<T> buildQuery() {

		if (specification != null) {
			cq.where(specification.toPredicate(cb, cq, root));
		}

		if (!orders.isEmpty()) {
			cq.orderBy(orders);
		}

		final TypedQuery<T> query = em.createQuery(cq);

		if (first >= 0) {
			query.setFirstResult(first);
		}

		if (max > 0) {
			query.setMaxResults(max);
		}

		return query;
	}

}
