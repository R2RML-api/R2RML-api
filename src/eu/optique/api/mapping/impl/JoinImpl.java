/*******************************************************************************
 * Copyright 2013, the Optique Consortium
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This first version of the R2RML API was developed jointly at the University of Oslo, 
 * the University of Bolzano, La Sapienza University of Rome, and fluid Operations AG, 
 * as part of the Optique project, www.optique-project.eu
 ******************************************************************************/
package eu.optique.api.mapping.impl;

import java.util.HashSet;
import java.util.Set;

import eu.optique.api.mapping.Join;
import eu.optique.api.mapping.LibConfiguration;

/**
 * An implementation of Join.
 * 
 * @author Marius Strandhaug
 */
public class JoinImpl implements Join {

	String child;
	String parent;

	Object res;
	final LibConfiguration lc;

	public JoinImpl(LibConfiguration c, String childCol, String parentCol) {

		if (c == null) {
			throw new NullPointerException("LibConfiguration was null.");
		}

		lc = c;

		setChild(childCol);
		setParent(parentCol);

		setResource(lc.createBNode());
	}

	@Override
	public void setChild(String columnName) {
		if (columnName != null) {
			child = columnName;
		} else {
			throw new NullPointerException("A Join must have a child column.");
		}
	}

	@Override
	public void setParent(String columnName) {
		if (columnName != null) {
			parent = columnName;
		} else {
			throw new NullPointerException("A Join must have a parent column.");
		}
	}

	@Override
	public String getChild() {
		return child;
	}

	@Override
	public String getParent() {
		return parent;
	}

	@Override
	public <T> Set<T> serialize(Class<T> tripleClass) {
		Set<T> stmtSet = new HashSet<T>();

		stmtSet.add(tripleClass.cast(lc.createTriple(res, lc.getRDFType(),
				lc.createResource(R2RMLVocabulary.TYPE_JOIN))));

		stmtSet.add(tripleClass.cast(lc.createLiteralTriple(res,
				lc.createResource(R2RMLVocabulary.PROP_CHILD), child)));
		stmtSet.add(tripleClass.cast(lc.createLiteralTriple(res,
				lc.createResource(R2RMLVocabulary.PROP_PARENT), parent)));

		return stmtSet;
	}

	@Override
	public void setResource(Object r) {
		if (r != null && !lc.getResourceClass().isInstance(r)) {
			throw new IllegalArgumentException("Parameter r is of type "
					+ r.getClass() + ". Should be an instance of "
					+ lc.getResourceClass() + ".");
		} else if (r == null) {
			throw new NullPointerException("A Join must have a resource.");
		}

		res = r;
	}

	@Override
	public <R> R getResource(Class<R> resourceClass) {
		return resourceClass.cast(res);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((child == null) ? 0 : child.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((res == null) ? 0 : res.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof JoinImpl))
			return false;

		JoinImpl other = (JoinImpl) obj;
		if (child == null) {
			if (other.child != null) {
				return false;
			}
		} else if (!child.equals(other.child)) {
			return false;
		}

		if (parent == null) {
			if (other.parent != null) {
				return false;
			}
		} else if (!parent.equals(other.parent)) {
			return false;
		}

		if (res == null) {
			if (other.res != null) {
				return false;
			}
		} else if (!res.equals(other.res)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "JoinImpl [child=" + child + ", parent=" + parent + ", res="
				+ res + "]";
	}

}
