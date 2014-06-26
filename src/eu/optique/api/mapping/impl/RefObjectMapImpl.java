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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import eu.optique.api.mapping.Join;
import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.LogicalTable;
import eu.optique.api.mapping.RefObjectMap;

/**
 * An implementation of a RefObjectMap.
 * 
 * @author Marius Strandhaug
 */
public class RefObjectMapImpl implements RefObjectMap {

	Object parent;

	LogicalTable parentLogicalTable;
	LogicalTable childLogicalTable;

	ArrayList<Join> joinList;

	Object res;
	final LibConfiguration lc;

	public RefObjectMapImpl(LibConfiguration c, Object parentMap) {

		if (c == null) {
			throw new NullPointerException("LibConfiguration was null.");
		}

		lc = c;

		joinList = new ArrayList<Join>();

		setParentMap(parentMap);
		setResource(lc.createBNode());
	}

	@Override
	public void setParentMap(Object tm) {
		if (tm != null && !lc.getResourceClass().isInstance(tm)) {
			throw new IllegalArgumentException("Parameter tm is of type "
					+ tm.getClass() + ". Should be an instance of "
					+ lc.getResourceClass() + ".");
		}

		if (tm != null) {
			parent = tm;
		} else {
			throw new NullPointerException(
					"A RefObjectMap must have a parent triples map resource.");
		}
	}

	@Override
	public void setParentLogicalTable(LogicalTable lt) {
		parentLogicalTable = lt;
	}

	@Override
	public void setChildLogicalTable(LogicalTable lt) {
		childLogicalTable = lt;
	}

	@Override
	public String getChildQuery() {
		if (childLogicalTable == null) {
			throw new NullPointerException(
					"The child logical table is not set.");
		} else {
			return childLogicalTable.getSQLQuery();
		}
	}

	@Override
	public String getParentQuery() {
		if (parentLogicalTable == null) {
			throw new NullPointerException(
					"The parent logical table is not set.");
		} else {
			return parentLogicalTable.getSQLQuery();
		}
	}

	@Override
	public String getJointQuery() {
		if (joinList.isEmpty()) {
			return "SELECT * FROM (" + getChildQuery() + ") AS tmp";
		} else {
			Iterator<Join> it = joinList.iterator();

			String sql = "SELECT * FROM (" + getChildQuery() + ") AS child, ("
					+ getParentQuery() + ") AS parent WHERE ";

			Join j = it.next();
			sql += "child." + j.getChild() + "=parent." + j.getParent();

			while (it.hasNext()) {
				j = it.next();
				sql += " AND child." + j.getChild() + "=parent."
						+ j.getParent();
			}

			return sql;
		}
	}

	@Override
	public void addJoinCondition(Join j) {
		joinList.add(j);
	}

	@Override
	public Join getJoinCondition(int index) {
		return joinList.get(index);
	}

	@Override
	public List<Join> getJoinConditions() {
		return Collections.unmodifiableList(joinList);
	}

	@Override
	public <R> R getParentMap(Class<R> resourceClass) {
		return resourceClass.cast(parent);
	}

	@Override
	public void removeJoinCondition(Join j) {
		joinList.remove(j);
	}

	@Override
	public <T> Set<T> serialize(Class<T> tripleClass) {
		Set<T> stmtSet = new HashSet<T>();

		stmtSet.add(tripleClass.cast(lc.createTriple(res, lc.getRDFType(),
				lc.createResource(R2RMLVocabulary.TYPE_REF_OBJECT_MAP))));
		stmtSet.add(tripleClass.cast(lc.createTriple(res,
				lc.createResource(R2RMLVocabulary.PROP_PARENT_TRIPLES_MAP),
				parent)));

		for (Join j : joinList) {
			stmtSet.add(tripleClass.cast(lc.createTriple(res,
					lc.createResource(R2RMLVocabulary.PROP_JOIN_CONDITION),
					j.getResource(lc.getResourceClass()))));
			stmtSet.addAll(j.serialize(tripleClass));
		}

		return stmtSet;
	}

	@Override
	public void setResource(Object r) {
		if (r != null && !lc.getResourceClass().isInstance(r)) {
			throw new IllegalArgumentException("Parameter r is of type "
					+ r.getClass() + ". Should be an instance of "
					+ lc.getResourceClass() + ".");
		} else if (r == null) {
			throw new NullPointerException(
					"A RefObjectMap must have a resource.");
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
		result = prime
				* result
				+ ((childLogicalTable == null) ? 0 : childLogicalTable
						.hashCode());
		result = prime * result
				+ ((joinList == null) ? 0 : joinList.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime
				* result
				+ ((parentLogicalTable == null) ? 0 : parentLogicalTable
						.hashCode());
		result = prime * result + ((res == null) ? 0 : res.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof RefObjectMapImpl))
			return false;

		RefObjectMapImpl other = (RefObjectMapImpl) obj;
		if (childLogicalTable == null) {
			if (other.childLogicalTable != null) {
				return false;
			}
		} else if (!childLogicalTable.equals(other.childLogicalTable)) {
			return false;
		}

		if (joinList == null) {
			if (other.joinList != null) {
				return false;
			}
		} else if (!joinList.equals(other.joinList)) {
			return false;
		}

		if (parent == null) {
			if (other.parent != null) {
				return false;
			}
		} else if (!parent.equals(other.parent)) {
			return false;
		}

		if (parentLogicalTable == null) {
			if (other.parentLogicalTable != null) {
				return false;
			}
		} else if (!parentLogicalTable.equals(other.parentLogicalTable)) {
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
		return "RefObjectMapImpl [parent=" + parent + ", parentLogicalTable="
				+ parentLogicalTable + ", childLogicalTable="
				+ childLogicalTable + ", joinList=" + joinList + ", res=" + res
				+ "]";
	}

}
