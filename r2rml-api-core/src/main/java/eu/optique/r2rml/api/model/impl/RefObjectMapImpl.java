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
package eu.optique.r2rml.api.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import eu.optique.r2rml.api.model.Join;
import eu.optique.r2rml.api.model.LogicalTable;
import eu.optique.r2rml.api.model.RefObjectMap;
import eu.optique.r2rml.api.model.TriplesMap;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;

/**
 * An implementation of a RefObjectMap.
 * 
 * @author Marius Strandhaug
 * @author Martin G. Skjæveland
 */
public class RefObjectMapImpl extends MappingComponentImpl implements RefObjectMap {

	TriplesMap parent;

	LogicalTable parentLogicalTable;
	LogicalTable childLogicalTable;

	ArrayList<Join> joinList;


	public RefObjectMapImpl(RDF c, TriplesMap parentMap) {

		super(c);

		joinList = new ArrayList<Join>();

		setParentMap(parentMap);
        setNode(getRDF().createBlankNode());
	}

	@Override
	public void setParentMap(TriplesMap tm) {
		parent = tm;
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
	public TriplesMap getParentMap() {
		return parent;
	}

	@Override
	public void removeJoinCondition(Join j) {
		joinList.remove(j);
	}

	@Override
	public Set<Triple> serialize() {
		Set<Triple> stmtSet = new HashSet<Triple>();

        stmtSet.add(getRDF().createTriple(node, getRDF().createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), getRDF().createIRI(R2RMLVocabulary.TYPE_REF_OBJECT_MAP)));
        stmtSet.add(getRDF().createTriple(node, getRDF().createIRI(R2RMLVocabulary.PROP_PARENT_TRIPLES_MAP), parent.getNode()));

		for (Join j : joinList) {
            stmtSet.add(getRDF().createTriple(node, getRDF().createIRI(R2RMLVocabulary.PROP_JOIN_CONDITION), j.getNode()));
			stmtSet.addAll(j.serialize());
		}

		return stmtSet;
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
		result = prime * result + ((node == null) ? 0 : node.hashCode());
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

		if (node == null) {
			if (other.node != null) {
				return false;
			}
		} else if (!node.equals(other.node)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "RefObjectMapImpl [parent=" + parent + ", parentLogicalTable="
				+ parentLogicalTable + ", childLogicalTable="
				+ childLogicalTable + ", joinList=" + joinList + ", node=" + node
				+ "]";
	}

}
