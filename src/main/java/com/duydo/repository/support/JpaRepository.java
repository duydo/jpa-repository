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
package com.duydo.repository.support;

import com.duydo.repository.Repository;
import com.duydo.repository.RepositoryException;
import com.duydo.repository.SpecificationResult;
import com.duydo.repository.spec.Specification;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Map;

/**
 * Implementation of {@link com.duydo.repository.Repository} interface using JPA 2.
 *
 * @author Duy Do
 * @version $Id$
 */
public class JpaRepository implements Repository {

    private EntityManager em;

    public JpaRepository() {
    }

    public JpaRepository(final EntityManager em) {
        setEntityManager(em);
    }

    /**
     * {@inheritDoc}
     */
    public <T> T find(final Class<T> type, final Serializable id) {
        return getEntityManager().find(type, id);
    }

    /**
     * {@inheritDoc}
     */
    public <T> SpecificationResult<T> find(final Class<T> clazz, final Specification<T> spec) {
        return new DefaultSpecificationResult<T>(clazz, spec, getEntityManager());
    }

    @SuppressWarnings("unchecked")
    public <T> SpecificationResult<T> find(final T example) {
        final Map<String, Object> propertyValues = ReflectionUtils.notNullPropertyValues(example);
        Specification<T> spec = null;
        for (Map.Entry<String, Object> entry : propertyValues.entrySet()) {
            Specification<T> tmp = SpecificationBuilder.forProperty(entry.getKey()).equal(entry.getValue()).build();
            if (spec == null) {
                spec = tmp;
            } else {
                spec = spec.and(tmp);
            }
        }

        return find((Class<T>) example.getClass(), spec);
    }

    /**
     * {@inheritDoc}
     */
    public <T> long count(final Class<T> clazz) {
        return count(clazz, null);
    }

    /**
     * {@inheritDoc}
     */
    public <T> long count(final Class<T> type, final Specification<T> specification) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<T> root = cq.from(type);
        cq.select(cb.count(root));

        if (specification != null) {
            cq.where(specification.toPredicate(cb, cq, root));
        }
        try {
            return getEntityManager().createQuery(cq).getSingleResult().longValue();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    public <T> void save(final T t) {
        try {
            getEntityManager().persist(t);
        } catch (Exception e) {
            throw newRepositoryException(e);
        }
    }

    public <T> void saveAndFlush(T t) {
        EntityTransaction tx = null;
        try {
            tx = getEntityManager().getTransaction();
            tx.begin();
            getEntityManager().persist(t);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            throw newRepositoryException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public <T> T update(final T t) {
        try {
            return getEntityManager().merge(t);
        } catch (final Exception e) {
            throw newRepositoryException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public <T> T updateAndFlush(final T t) {
        EntityTransaction tx = null;
        try {
            tx = getEntityManager().getTransaction();
            tx.begin();
            final T updated = getEntityManager().merge(t);
            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            throw newRepositoryException(e);
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
            throw newRepositoryException(e);
        }
    }

    public <T> void removeAndFlush(T t) {
        EntityTransaction tx = null;
        try {
            tx = getEntityManager().getTransaction();
            tx.begin();
            getEntityManager().refresh(t);
            getEntityManager().remove(t);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            throw newRepositoryException(e);
        }
    }

    /**
     * Returns {@link EntityManager}.
     */
    public Object getDelegate() {
        return getEntityManager();
    }

    /**
     * @return the entityManager
     */
    protected EntityManager getEntityManager() {
        if (em == null) {
            throw new NullPointerException("entityManager must be set before executing any operations");
        }
        return em;
    }

    /**
     * @param entityManager the entityManager to set
     */
    public void setEntityManager(final EntityManager entityManager) {
        this.em = PreconditionUtils.checkNotNull(entityManager);
    }

    protected RepositoryException newRepositoryException(final Exception e) {
        // TODO:FIXME: better translate exception
        return new RepositoryException(e.getMessage(), e.getCause());
    }

    public <T> SpecificationResult<T> find(Class<T> clazz) {
        return find(clazz, null);
    }
}
