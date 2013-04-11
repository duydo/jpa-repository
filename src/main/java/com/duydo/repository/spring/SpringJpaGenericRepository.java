/*
 * @(#)SpringJpaGenericRepository.java Aug 3, 2012 1:28:11 PM
 * 
 * Copyright (c) 2012 Duy Do
 */
package com.duydo.repository.spring;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.duydo.repository.JpaGenericRepository;

/**
 * TODO: document
 * 
 * @author Duy Do
 * @version $Id$
 */
public class SpringJpaGenericRepository<T> extends JpaGenericRepository<T> {

	public SpringJpaGenericRepository(Class<T> clazz) {
		setClass(clazz);
	}

	@PersistenceContext
	public void injectEntityManager(EntityManager em) {
		setEntityManager(em);
	}
}
