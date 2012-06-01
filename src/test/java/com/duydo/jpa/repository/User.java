/*
 * Copyright (C) 2011 Duy Do. All rights reserved.
 */
package com.duydo.jpa.repository;

import javax.persistence.Entity;

import com.duydo.jpa.domain.EntityObject;

/**
 * @author Duy Do
 * @version $Id$
 */
@SuppressWarnings("serial")
@Entity
public class User extends EntityObject<Long> {
	private String name;
	private Integer age;

	public User() {}

	public User(final String name, final Integer age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(final Integer age) {
		this.age = age;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "@[name = " + name + ", age = "
				+ age + "]";
	}
}
