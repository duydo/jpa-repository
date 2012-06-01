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
package com.duydo.jpa.repository;

import java.util.List;

public interface SpecificationResult<T> {

	SpecificationResult<T> get(int count);

	SpecificationResult<T> skip(int count);

	/**
	 * Sort result by ascending.
	 */
	SpecificationResult<T> sortAscending(String expression);

	/**
	 * Sort result by descending.
	 */
	SpecificationResult<T> sortDescending(String expression);

	/**
	 * Return single entity.
	 * 
	 * @return the found instance of T or null if not found
	 */
	T asSingle();

	/**
	 * @return instances of T list or empty list
	 */
	List<T> asList();
}