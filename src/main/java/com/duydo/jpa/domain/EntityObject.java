/*
 * @(#)AbstractEntityObject.java May 12, 2011 11:31:11 AM
 * 
 * Copyright (c) 2011 Duy Do
 * 
 * Permission is hereby granted, free of charge, to
 * any person obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following
 * conditions:
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
package com.duydo.jpa.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;

/**
 * @author Duy Do
 * @version $Id$
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class EntityObject<Id> implements Identifiable<Id> {

	@javax.persistence.Id
	@GeneratedValue
	private Id id;

	public Id getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Id id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return (getId() == null ? 0 : getId().hashCode());
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (obj.getClass() != getClass()) {
			return false;
		}
		final Identifiable<Id> other = (Identifiable<Id>) obj;
		return getId() != null && getId().equals(other.getId());
	}
}
