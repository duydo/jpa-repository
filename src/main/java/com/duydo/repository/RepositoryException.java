/*
 * @(#)RepositoryException.java May 6, 2011 3:32:47 PM
 * 
 * Copyright 2011 Duy Do. All rights reserved.
 */
package com.duydo.repository;

/**
 * @author Duy Do (duydo)
 * @version $Id$
 */
@SuppressWarnings("serial")
public class RepositoryException extends RuntimeException {

	public RepositoryException() {
		super();
	}

	/**
	 * @param message
	 */
	public RepositoryException(final String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public RepositoryException(final Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RepositoryException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
