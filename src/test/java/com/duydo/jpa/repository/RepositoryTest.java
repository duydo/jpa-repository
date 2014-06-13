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
import org.junit.BeforeClass;
import org.junit.Test;

import com.duydo.repository.support.JpaRepository;
import com.duydo.repository.Repository;
import com.duydo.repository.spec.Specification;
import com.duydo.repository.support.SpecificationBuilder;

/**
 * @author Duy Do
 * @version $Id$
 */
public class RepositoryTest {
	private static Repository repository;
	private static EntityManager em;
	private static EntityTransaction tx;

	@BeforeClass
	public static void before() {
		em = Persistence.createEntityManagerFactory("test").createEntityManager();
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

	private static void  createUser(final String name, final int age) {
		User u = new User(name, age);
		repository.save(u);
	}

	@Test
	public void findUserById() {
		User user = repository.find(User.class, 1L);
		Assert.assertNotNull(user);
		Assert.assertTrue(1L == user.getId());
		Assert.assertEquals("Sam", user.getName());
	}

	@Test
	public void countAllShouldReturn6() {
		Assert.assertEquals(6, repository.count(User.class));
	}

	@Test
	public void countUsersHasAge28ShouldReturn2() {
		// final Specification<User> age28 = Specifications.equal("age", 28);

		Specification<User> age28 = SpecificationBuilder.forProperty("age").equal(28).build();
		Assert.assertEquals(2, repository.count(User.class, age28));
	}

	@Test
	public void findUsingLikeShouldReturn2() {
		final Specification<User> hasT = SpecificationBuilder.forProperty("name").like("T%").build();
		List<User> users = repository.find(User.class, hasT).list();
		Assert.assertEquals(2, users.size());
	}

	public void findAndPaging() {
		final Specification<User> hasT = SpecificationBuilder.forProperty("name").like("T%").build();
		List<User> users = repository.find(User.class, hasT).size(1).list();
		Assert.assertEquals(1, users.size());
	}

	@Test
	public void findWithOp() {
		final Specification<User> hasT = SpecificationBuilder.forProperty("age").greaterThan(28).or().lessThanOrEqualTo(1).build();
		List<User> users = repository.find(User.class, hasT).list();
		Assert.assertEquals(3, users.size());
	}

	@Test
	public void findByExample() {
		User user = new User();
		user.setName("Duy");

		List<User> users = repository.find(user).list();
		for (User u : users) {
			System.out.println(u);
		}
	}

	@Test
	public void createUser() {
		User user = new User("Duy Do", 28);
		tx.begin();
		repository.save(user);
		tx.commit();
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
        repository.removeAndFlush(user);
	}

	@Test
	public void deleteUserShouldReturnNull() {
		User user = repository.find(User.class, 1L);
		repository.remove(user);

		User old = repository.find(User.class, 1L);
		Assert.assertNull(old);
	}
}
