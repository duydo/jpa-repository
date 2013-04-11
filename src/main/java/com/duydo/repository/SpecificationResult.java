/*
 * @(#)SpecificationResult.java Jun 8, 2011 5:57:36 PM
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
package com.duydo.repository;

import java.util.List;

/**
 * TODO: document
 * 
 * @author Duy Do
 * @version $Id$
 * @param <T>
 */
public interface SpecificationResult<T> {

	/**
	 * Set the number of results to retrieve.
	 */
	SpecificationResult<T> size(int size);

	/**
	 * Set the from position to start retrieve.
	 */
	SpecificationResult<T> from(int from);

	/**
	 * Sort results by ascending.
	 */
	SpecificationResult<T> ascending(String propertyName);

	/**
	 * Sort results by descending.
	 */
	SpecificationResult<T> descending(String propertyName);

	/**
	 * Set a query property or hint.
	 */
	SpecificationResult<T> hint(String name, Object value);

	/**
	 * Return single result.
	 * 
	 * @return the found instance of T or null if not found
	 */
	T single();

	/**
	 * @return list of the results or empty if not result found.
	 */
	List<T> list();
}