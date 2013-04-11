/*
 * @(#)SpringJpaRepository.java Aug 3, 2012 1:02:08 PM
 * 
 * Copyright (c) 2012 Duy Do
 */
package com.duydo.repository.spring;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.duydo.repository.JpaRepository;

/**
 * TODO: document
 * 
 * @author Duy Do
 * @version $Id$
 */
@Repository
public class SpringJpaRepository extends JpaRepository {

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		super.setEntityManager(em);
	}

}
