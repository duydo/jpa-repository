/*
 * @(#)JpaRepository.java Jun 8, 2011 9:59:18 AM
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

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.duydo.jpa.specification.Specification;

/**
 * Implementation of {@link Repository} interface using JPA 2.
 * 
 * @author Duy Do
 * @version $Id$
 */
public class JpaRepository implements Repository {

	@PersistenceContext
	private EntityManager entityManager;

	public JpaRepository(final EntityManager entityManager) {
		setEntityManager(entityManager);
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> T findById(final Class<T> type, final Serializable id) {
		return getEntityManager().find(type, id);
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> List<T> findAll(final Class<T> type) {
		return findBySpecification(type, null).asList();
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> SpecificationResult<T> findBySpecification(final Class<T> type,
			final Specification<T> specification) {
		return new SpecificationResultImpl<T>(type, specification,
				getEntityManager());
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> long countAll(final Class<T> type) {
		return countBySpecification(type, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> long countBySpecification(final Class<T> type,
			final Specification<T> specification) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		final Root<T> root = cq.from(type);
		cq.select(cb.count(root));
		if (specification != null) {
			cq.where(specification.toPredicate(cb, cq, root));
		}
		try {
			return getEntityManager().createQuery(cq).getSingleResult()
					.longValue();
		} catch (final Exception e) {
			return 0;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> void save(final T t) throws RepositoryException {
		try {
			getEntityManager().persist(t);
		} catch (final Exception e) {
			throw repositoryException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> T update(final T t) {
		try {
			return getEntityManager().merge(t);
		} catch (final Exception e) {
			throw repositoryException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> void remove(final T t) {
		try {
			getEntityManager().refresh(t);
			getEntityManager().remove(t);
		} catch (final Exception e) {
			throw repositoryException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getDelegate() {
		return getEntityManager();
	}

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * @param entityManager the entityManager to set
	 * @throws NullPointerException if entityManager parameter is null
	 */
	public void setEntityManager(final EntityManager entityManager) {
		if (entityManager == null) {
			throw new NullPointerException(
					"entityManager must be set before executing any operations");
		}
		this.entityManager = entityManager;
	}

	protected RepositoryException repositoryException(final Exception e) {
		// TODO:FIXME: better translate exception
		return new RepositoryException(e.getMessage(), e.getCause());
	}
}
