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

import eu.optique.api.mapping.PredicateMap;
import eu.optique.api.mapping.Template;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;

/**
 * An implementation of a PredicateMap.
 * 
 * @author Marius Strandhaug
 */
public class PredicateMapImpl extends TermMapImpl implements PredicateMap {

	public PredicateMapImpl(RDF rdf, TermMapType termMapType,
                            Template template) {
		super(rdf, termMapType, template);
	}

	public PredicateMapImpl(RDF rdf, TermMapType termMapType,
                            String columnOrConst) {
		super(rdf, termMapType, columnOrConst);
	}

	@Override
	public Set<Triple> serialize() {
		Set<Triple> stmtSet = new HashSet<>();

		stmtSet.addAll(super.serialize());

        stmtSet.add(getRDF().createTriple(getNode(),
                getRDF().createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
                getRDF().createIRI(R2RMLVocabulary.TYPE_PREDICATE_MAP)));

		return stmtSet;
	}

	@Override
	public String toString() {
		return "PredicateMapImpl [type=" + type + ", termtype=" + termtype
				+ ", template=" + template + ", constVal=" + constVal
				+ ", columnName=" + columnName + ", inverseExp=" + inverseExp
				+ ", node=" + getNode() + "]";
	}

}
