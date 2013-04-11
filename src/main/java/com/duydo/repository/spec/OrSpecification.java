/*
 * @(#)OrSepecification.java May 7, 2011 9:20:26 AM
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
package com.duydo.repository.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.duydo.repository.AbstractSpecification;
import com.duydo.repository.Specification;



/**
 * @author Duy Do
 * @version $Id$
 */
@SuppressWarnings("serial")
public class OrSpecification<T> extends AbstractSpecification<T> {
	private final Specification<T> specification1;
	private final Specification<T> specification2;

	public OrSpecification(final Specification<T> specification1,
			final Specification<T> specification2) {
		this.specification1 = specification1;
		this.specification2 = specification2;
	}

	@Override
	public Predicate toPredicate(final CriteriaBuilder cb,
			final CriteriaQuery<?> cq, final Root<T> root) {
		return cb.or(specification1.toPredicate(cb, cq, root),
				specification2.toPredicate(cb, cq, root));
	}

}
