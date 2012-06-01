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
package com.duydo.jpa.repository;

import java.io.Serializable;
import java.util.List;

import com.duydo.jpa.specification.Specification;

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
	<T> T findById(Class<T> clazz, Serializable id);

	/**
	 * Find by a given specification.
	 * 
	 * @param clazz the entity class
	 * @param specification the specification
	 * @return the <code>SpecificationResult</code> instance
	 * @throws IllegalArgumentException if <code>clazz</code> is does not denote
	 *         an entity
	 */
	<T> SpecificationResult<T> findBySpecification(Class<T> clazz,
			Specification<T> specification);

	/**
	 * Find all instances of T entity.
	 * 
	 * @param clazz the entity class
	 * @return the list of T instances
	 */
	<T> List<T> findAll(Class<T> clazz);

	/**
	 * Count all entities of T entity.
	 * 
	 * @param clazz the entity class
	 * @return number of entities
	 */
	<T> long countAll(Class<T> clazz);

	/**
	 * Count entities by a given specification.
	 * 
	 * @param clazz the entity class
	 * @param specification the specification
	 */
	<T> long countBySpecification(Class<T> clazz, Specification<T> specification);

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
	 * Update an existed entity.
	 * 
	 * @return instance of T
	 * @throws RepositoryException if entity can not be updated
	 */
	<T> T update(T t);

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
	public Object getDelegate();
}
