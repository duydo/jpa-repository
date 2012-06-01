/*
 * @(#)RepositoryTest.java Jun 8, 2011 11:21:36 AM
 * 
 * Copyright 2011 Duy Do. All rights reserved.
 */
package com.duydo.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.duydo.jpa.specification.Specification;
import com.duydo.jpa.specification.Specifications;

/**
 * @author Duy Do
 * @version $Id$
 */
public class RepositoryTest {
	private Repository repository;
	private EntityManager em;
	private EntityTransaction tx;

	@Before
	public void before() {
		em = Persistence.createEntityManagerFactory("test")
				.createEntityManager();
		repository = new JpaRepository(em);
		tx = em.getTransaction();
		tx.begin();
		createUser("Sam", 52);
		createUser("Lien", 48);
		createUser("Duy", 28);
		createUser("Thuy", 28);
		createUser("Trang", 21);
		createUser("An", 1);
		tx.commit();
	}

	private void createUser(final String name, final int age) {
		User u = new User(name, age);
		repository.save(u);
	}

	@Test
	public void findUserById() {
		User user = repository.findById(User.class, 1L);
		Assert.assertNotNull(user);
		Assert.assertTrue(1L == user.getId());
		Assert.assertEquals("Sam", user.getName());
	}

	@Test
	public void countAllShouldReturn6() {
		Assert.assertEquals(6, repository.countAll(User.class));
	}

	@Test
	public void countUsersHasAge28ShouldReturn2() {
		final Specification<User> age28 = Specifications.equal("age", 28);
		Assert.assertEquals(2,
				repository.countBySpecification(User.class, age28));
	}

	@Test
	public void findUsingLikeShouldReturn2() {
		final Specification<User> hasT = Specifications.like("name", "T%");
		List<User> users = repository.findBySpecification(User.class, hasT)
				.asList();
		Assert.assertEquals(2, users.size());
	}

	public void findAndPaging() {
		final Specification<User> hasT = Specifications.like("name", "T%");
		List<User> users = repository.findBySpecification(User.class, hasT).get(1)
				.asList();
		Assert.assertEquals(1, users.size());
	}

	@Test
	public void createUser() {
		User user = new User("Duy Do", 28);
		tx.begin();
		repository.save(user);
		tx.commit();
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
	}

	@Test
	public void deleteUserShouldReturnNull() {
		User user = repository.findById(User.class, 1L);
		repository.remove(user);

		User old = repository.findById(User.class, 1L);
		Assert.assertNull(old);
	}
}
