/*
 * @(#)JpaGenericRepository.java Aug 3, 2012 12:11:28 PM
 * 
 * Copyright (c) 2012 Duy Do
 */
package com.duydo.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

/**
 * @author Duy Do
 * @version $Id$
 */
public class JpaGenericRepository<T> implements GenericRepository<T> {

	private Repository repository;
	private Class<T> clazz;

	public JpaGenericRepository() {}

	public JpaGenericRepository(EntityManager em, Class<T> clazz) {
		setEntityManager(em);
		setClass(clazz);
	}

	public void setClass(Class<T> clazz) {
		this.clazz = clazz;
	}

	public void setEntityManager(EntityManager em) {
		this.repository = new JpaRepository(em);
	}

	public T find(Serializable id) {
		return repository.find(clazz, id);
	}

	public SpecificationResult<T> find(Specification<T> spec) {
		return repository.find(clazz, spec);
	}

	public SpecificationResult<T> find() {
		return repository.find(clazz);
	}

	public SpecificationResult<T> find(T example) {
		return repository.find(example);
	}

	public long count() {
		return repository.count(clazz);
	}

	public long count(Specification<T> spec) {
		return repository.count(clazz, spec);
	}

	public void save(T t) throws RepositoryException {
		repository.save(t);
	}

	public T update(T t) throws RepositoryException {
		return repository.update(t);
	}

	public void remove(T t) throws RepositoryException {
		repository.remove(t);
	}

	public Object getDelegate() {
		return repository.getDelegate();
	}

}
