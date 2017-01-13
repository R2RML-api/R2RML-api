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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eu.optique.r2rml.api.model.LogicalTable;
import eu.optique.r2rml.api.model.PredicateObjectMap;
import eu.optique.r2rml.api.model.R2RMLVocabulary;
import eu.optique.r2rml.api.model.SubjectMap;
import eu.optique.r2rml.api.model.TriplesMap;
import eu.optique.r2rml.api.model.TermMap;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;

/**
 * An implementation of a TriplesMap.
 * 
 * @author Marius Strandhaug
 * @author Martin G. Skjæveland
 */
public class TriplesMapImpl extends MappingComponentImpl implements TriplesMap {

	LogicalTable logTable;
	SubjectMap subMap;
	ArrayList<PredicateObjectMap> pomList;

	BlankNodeOrIRI res;

	public TriplesMapImpl(RDF rdf, LogicalTable lt, SubjectMap sm) {

		super(rdf);

		pomList = new ArrayList<>();
		setLogicalTable(lt);
		setSubjectMap(sm);

        setNode(getRDF().createBlankNode());
	}

	public TriplesMapImpl(RDF rdf, LogicalTable lt, SubjectMap sm, String resourceIdentifier) {

        super(rdf);

		pomList = new ArrayList<>();
		setLogicalTable(lt);
		setSubjectMap(sm);

        setNode(getRDF().createIRI(resourceIdentifier));
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
	public void addPredicateObjectMaps(Collection<PredicateObjectMap> poms) {
		pomList.addAll(poms);
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
	public void setNode(BlankNodeOrIRI node) {
		if (node == null) {
			throw new NullPointerException("A TriplesMap must have a resource.");
		}

		res = (BlankNodeOrIRI) node;
	}

    @Override
    public BlankNodeOrIRI getNode() {
        return res;
    }

	@Override
	public Set<Triple> serialize() {
		Set<Triple> stmtSet = new HashSet<>();

        stmtSet.add(getRDF().createTriple(res, getRDF().createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), getRDF().createIRI(R2RMLVocabulary.TYPE_TRIPLES_MAP)));

        stmtSet.add(getRDF().createTriple(res, getRDF().createIRI(R2RMLVocabulary.PROP_LOGICAL_TABLE), getLogicalTable().getNode()));
		stmtSet.addAll(getLogicalTable().serialize());

		if (getSubjectMap().getTermMapType() == TermMap.TermMapType.CONSTANT_VALUED) {
			// Use constant shortcut property.
            stmtSet.add(getRDF().createTriple(res, getRDF().createIRI(R2RMLVocabulary.PROP_SUBJECT), getRDF().createIRI(getSubjectMap().getConstant())));
		} else {
            stmtSet.add(getRDF().createTriple(res, getRDF().createIRI(R2RMLVocabulary.PROP_SUBJECT_MAP), getSubjectMap().getNode()));
			stmtSet.addAll(getSubjectMap().serialize());
		}

		for (PredicateObjectMap pom : pomList) {
            stmtSet.add(getRDF().createTriple(res, getRDF().createIRI(R2RMLVocabulary.PROP_PREDICATE_OBJECT_MAP), pom.getNode()));
			stmtSet.addAll(pom.serialize());
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
				+ ", pomList=" + pomList + ", node=" + res + "]";
	}

}
