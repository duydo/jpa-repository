/*
 * @(#)Repository.java Jun 8, 2011 9:34:25 AM
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

import java.io.Serializable;

import com.duydo.repository.spec.Specification;

/**
 * @author Duy Do
 * @version $Id$
 */
public interface Repository {
	/**
	 * Find by a given identifier (primary key).
	 * 
	 * @param clazz the entity class
	 * @param id the identifier (primary key)
	 * @return the found entity instance or null if not found
	 * @throws IllegalArgumentException if <code>clazz</code> is does not denote
	 *         an entity or <code>id</code> is null
	 */
	<T> T find(Class<T> clazz, Serializable id);

	/**
	 * Find by a given specification.
	 * 
	 * @param clazz the entity class
	 * @param spec the specification
	 * @return the <code>SpecificationResult</code> instance
	 * @throws IllegalArgumentException if <code>clazz</code> is does not denote
	 *         an entity
	 */
	<T> SpecificationResult<T> find(Class<T> clazz, Specification<T> spec);

	/**
	 * Find all instances of T entity.
	 * 
	 * @param clazz the entity class
	 * @return the <code>SpecificationResult</code> instance
	 */
	<T> SpecificationResult<T> find(Class<T> clazz);

	/**
	 * Finds entities by example.
	 * 
	 * @return the <code>SpecificationResult</code> instance
	 */
	<T> SpecificationResult<T> find(T example);

	/**
	 * Count all entities of T entity.
	 * 
	 * @param clazz the entity class
	 * @return number of entities
	 */
	<T> long count(Class<T> clazz);

	/**
	 * Count entities by a given specification.
	 * 
	 * @param clazz the entity class
	 * @param spec the specification
	 */
	<T> long count(Class<T> clazz, Specification<T> spec);

	/*
	 * EXECUTE OPERATIONS
	 */
	/**
	 * Save a new entity.
	 * 
	 * @throws RepositoryException if entity can not be saved
	 */
	<T> void save(T t);

	/**
	 * Save and flush entity into database.
	 * 
	 * @throws RepositoryException if entity can not be saved
	 */
	<T> void saveAndFlush(T t);

	/**
	 * Update an existed entity.
	 * 
	 * @return instance of T
	 * @throws RepositoryException if entity can not be updated
	 */
	<T> T update(T t);

	/**
	 * Update an existed entity then flush it into database.
	 * 
	 * @return instance of T
	 * @throws RepositoryException if entity can not be updated
	 */
	<T> T updateAndFlush(T t);

	/**
	 * Delete an entity.
	 * 
	 * @throws RepositoryException if entity can not be deleted.
	 */
	<T> void removeAndFlush(T t);

	/**
	 * Delete an entity.
	 * 
	 * @throws RepositoryException if entity can not be deleted.
	 */
	<T> void remove(T t);

	/**
	 * Return the underlying provider object for the <code>Repository</code>, if
	 * available. The result of this method is implementation specific.
	 * 
	 * @return underlying provider object for Repository
	 */
	Object getDelegate();
}
