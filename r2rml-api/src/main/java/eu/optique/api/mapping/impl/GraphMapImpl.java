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

import eu.optique.api.mapping.GraphMap;
import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.Template;
import org.apache.commons.rdf.api.Triple;

/**
 * An implementation of a GraphMap.
 * 
 * @author Marius Strandhaug
 */
public class GraphMapImpl extends TermMapImpl implements GraphMap {

	public GraphMapImpl(LibConfiguration lc, TermMapType termMapType,
			Template template) {
		super(lc, termMapType, template);
	}

	public GraphMapImpl(LibConfiguration lc, TermMapType termMapType,
			String columnOrConst) {
		super(lc, termMapType, columnOrConst);
	}

	@Override
	public Set<Triple> serialize() {
		Set<Triple> stmtSet = new HashSet<>();

		stmtSet.addAll(super.serialize());

        stmtSet.add(lc.getRDF().createTriple(res, lc.getRDF().createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), lc.getRDF().createIRI(R2RMLVocabulary.TYPE_GRAPH_MAP)));

		return stmtSet;
	}

	@Override
	public String toString() {
		return "GraphMapImpl [type=" + type + ", termtype=" + termtype
				+ ", template=" + template + ", constVal=" + constVal
				+ ", columnName=" + columnName + ", inverseExp=" + inverseExp
				+ ", res=" + res + "]";
	}

}
