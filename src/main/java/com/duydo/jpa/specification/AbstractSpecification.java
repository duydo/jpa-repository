/*
 * @(#)AbstractSpecification.java May 7, 2011 9:18:40 AM
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
public abstract class AbstractSpecification<T> implements Specification<T> {

	/**
	 * {@inheritDoc}
	 */
	public abstract Predicate toPredicate(final CriteriaBuilder cb,
			final CriteriaQuery<?> cq, final Root<T> root);

	/**
	 * {@inheritDoc}
	 * 
	 */
	public Specification<T> or(final Specification<T> specification) {
		return new OrSpecification<T>(this, specification);
	}

	/**
	 * {@inheritDoc}
	 */
	public Specification<T> and(final Specification<T> specification) {
		return new AndSpecification<T>(this, specification);
	}

	/**
	 * {@inheritDoc}
	 */
	public Specification<T> not() {
		return new NotSpecification<T>(this);
	}

}
