/*
 * @(#)GenericRepository.java Aug 3, 2012 12:08:43 PM
 * 
 * Copyright (c) 2012 Duy Do
 */
package com.duydo.repository;

import java.io.Serializable;

import com.duydo.repository.spec.Specification;

/**
 * @author Duy Do
 * @version $Id$
 */
public interface GenericRepository<T> {
	/**
	 * Find by a given identifier (primary key).
	 * 
	 * @param clazz the entity class
	 * @param id the identifier (primary key)
	 * @return the found entity instance or null if not found
	 * @throws IllegalArgumentException if <code>clazz</code> is does not denote
	 *         an entity or <code>id</code> is null
	 */
	T find(Serializable id);

	/**
	 * Find by a given specification.
	 * 
	 * @param clazz the entity class
	 * @param spec the specification
	 * @return the <code>SpecificationResult</code> instance
	 * @throws IllegalArgumentException if <code>clazz</code> is does not denote
	 *         an entity
	 */
	SpecificationResult<T> find(Specification<T> spec);

	/**
	 * Find all instances of T entity.
	 * 
	 * @param clazz the entity class
	 * @return the <code>SpecificationResult</code> instance
	 */
	SpecificationResult<T> find();

	/**
	 * Finds entities by example.
	 * 
	 * @return the <code>SpecificationResult</code> instance
	 */
	SpecificationResult<T> find(T example);

	/**
	 * Count all entities of T entity.
	 * 
	 * @param clazz the entity class
	 * @return number of entities
	 */
	long count();

	/**
	 * Count entities by a given specification.
	 * 
	 * @param clazz the entity class
	 * @param spec the specification
	 */
	long count(Specification<T> spec);

	/*
	 * EXECUTE OPERATIONS
	 */
	/**
	 * Save a new entity.
	 * 
	 * @throws RepositoryException if entity can not be saved
	 */
	void save(T t) throws RepositoryException;

	/**
	 * Update an existed entity.
	 * 
	 * @return instance of T
	 * @throws RepositoryException if entity can not be updated
	 */
	T update(T t) throws RepositoryException;

	/**
	 * Delete an entity.
	 * 
	 * @throws RepositoryException if entity can not be deleted.
	 */
	void remove(T t) throws RepositoryException;

	/**
	 * Return the underlying provider object for the <code>Repository</code>, if
	 * available. The result of this method is implementation specific.
	 * 
	 * @return underlying provider object for Repository
	 */
	Object getDelegate();
}
