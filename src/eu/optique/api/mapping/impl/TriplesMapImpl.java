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
import java.util.List;
import java.util.Set;

import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.LogicalTable;
import eu.optique.api.mapping.PredicateObjectMap;
import eu.optique.api.mapping.SubjectMap;
import eu.optique.api.mapping.TriplesMap;
import eu.optique.api.mapping.TermMap.TermMapType;

/**
 * An implementation of a TriplesMap.
 * 
 * @author Marius Strandhaug
 */
public class TriplesMapImpl implements TriplesMap {

	LogicalTable logTable;
	SubjectMap subMap;
	ArrayList<PredicateObjectMap> pomList;

	Object res;
	final LibConfiguration lc;

	public TriplesMapImpl(LibConfiguration c, LogicalTable lt, SubjectMap sm) {

		if (c == null) {
			throw new NullPointerException("LibConfiguration was null.");
		}

		lc = c;

		pomList = new ArrayList<PredicateObjectMap>();
		setLogicalTable(lt);
		setSubjectMap(sm);

		setResource(lc.createBNode());
	}

	@Override
	public void setLogicalTable(LogicalTable lt) {
		if (lt == null) {
			throw new NullPointerException(
					"A TriplesMap must have a LogicalTable.");
		} else {
			logTable = lt;
		}
	}

	@Override
	public void setSubjectMap(SubjectMap sm) {
		if (sm == null) {
			throw new NullPointerException(
					"A TriplesMap must have a SubjectMap.");
		} else {
			subMap = sm;
		}
	}

	@Override
	public void addPredicateObjectMap(PredicateObjectMap pom) {
		pomList.add(pom);
	}

	@Override
	public LogicalTable getLogicalTable() {
		return logTable;
	}

	@Override
	public SubjectMap getSubjectMap() {
		return subMap;
	}

	@Override
	public PredicateObjectMap getPredicateObjectMap(int index) {
		return pomList.get(index);
	}

	@Override
	public List<PredicateObjectMap> getPredicateObjectMaps() {
		return Collections.unmodifiableList(pomList);
	}

	@Override
	public void removePredicateObjectMap(PredicateObjectMap pom) {
		pomList.remove(pom);
	}

	@Override
	public void setResource(Object r) {
		if (r != null && !lc.getResourceClass().isInstance(r)) {
			throw new IllegalArgumentException("Parameter r is of type "
					+ r.getClass() + ". Should be an instance of "
					+ lc.getResourceClass() + ".");
		} else if (r == null) {
			throw new NullPointerException("A TriplesMap must have a resource.");
		}

		res = r;
	}

	@Override
	public <R> R getResource(Class<R> resourceClass) {
		return resourceClass.cast(res);
	}

	@Override
	public <T> Set<T> serialize(Class<T> tripleClass) {
		Set<T> stmtSet = new HashSet<T>();

		stmtSet.add(tripleClass.cast(lc.createTriple(res, lc.getRDFType(),
				lc.createResource(R2RMLVocabulary.TYPE_TRIPLES_MAP))));

		stmtSet.add(tripleClass.cast(lc.createTriple(res,
				lc.createResource(R2RMLVocabulary.PROP_LOGICAL_TABLE),
				getLogicalTable().getResource(lc.getResourceClass()))));
		stmtSet.addAll(getLogicalTable().serialize(tripleClass));

		if (getSubjectMap().getTermMapType() == TermMapType.CONSTANT_VALUED) {
			// Use constant shortcut property.
			stmtSet.add(tripleClass.cast(lc.createTriple(res,
					lc.createResource(R2RMLVocabulary.PROP_SUBJECT),
					lc.createResource(getSubjectMap().getConstant()))));
		} else {
			stmtSet.add(tripleClass.cast(lc.createTriple(res,
					lc.createResource(R2RMLVocabulary.PROP_SUBJECT_MAP),
					getSubjectMap().getResource(lc.getResourceClass()))));
			stmtSet.addAll(getSubjectMap().serialize(tripleClass));
		}

		for (PredicateObjectMap pom : pomList) {
			stmtSet.add(tripleClass.cast(lc.createTriple(res, 
					lc.createResource(R2RMLVocabulary.PROP_PREDICATE_OBJECT_MAP),
					pom.getResource(lc.getResourceClass()))));
			stmtSet.addAll(pom.serialize(tripleClass));
		}

		return stmtSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((logTable == null) ? 0 : logTable.hashCode());
		result = prime * result + ((pomList == null) ? 0 : pomList.hashCode());
		result = prime * result + ((res == null) ? 0 : res.hashCode());
		result = prime * result + ((subMap == null) ? 0 : subMap.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof TriplesMapImpl))
			return false;

		TriplesMapImpl other = (TriplesMapImpl) obj;
		if (logTable == null) {
			if (other.logTable != null) {
				return false;
			}
		} else if (!logTable.equals(other.logTable)) {
			return false;
		}

		if (pomList == null) {
			if (other.pomList != null) {
				return false;
			}
		} else if (!pomList.equals(other.pomList)) {
			return false;
		}

		if (res == null) {
			if (other.res != null) {
				return false;
			}
		} else if (!res.equals(other.res)) {
			return false;
		}

		if (subMap == null) {
			if (other.subMap != null) {
				return false;
			}
		} else if (!subMap.equals(other.subMap)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "TriplesMapImpl [logTable=" + logTable + ", subMap=" + subMap
				+ ", pomList=" + pomList + ", res=" + res + "]";
	}

}
