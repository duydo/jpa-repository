/*
 * Copyright (C) 2011 Duy Do. All rights reserved.
 */
package com.duydo.jpa.repository;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Duy Do
 * @version $Id$
 */
@SuppressWarnings("serial")
@Entity
public class User implements Serializable {
	@Id
	@GeneratedValue
	private Long id;
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

	public long getId() {
		return id;
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
