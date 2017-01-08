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

import eu.optique.api.mapping.GraphMap;
import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.SubjectMap;
import eu.optique.api.mapping.Template;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Triple;

/**
 * An implementation of a SubjectMap.
 * 
 * @author Marius Strandhaug
 */
public class SubjectMapImpl extends TermMapImpl implements SubjectMap {

	ArrayList<IRI> classList;
	ArrayList<GraphMap> graphList;

	public SubjectMapImpl(LibConfiguration c, TermMapType termMapType,
			Template template) {
		super(c, termMapType, template);

		classList = new ArrayList<>();
		graphList = new ArrayList<>();
	}

	public SubjectMapImpl(LibConfiguration c, TermMapType termMapType,
			String columnOrConst) {
		super(c, termMapType, columnOrConst);

		classList = new ArrayList<>();
		graphList = new ArrayList<>();
	}

	@Override
	public void addClass(IRI classURI) {
		classList.add(classURI);
	}

	@Override
	public void addGraphMap(GraphMap gm) {
		graphList.add(gm);
	}

	@Override
	public void addGraphMap(List<GraphMap> gms) {
		graphList.addAll(gms);
	}

	@Override
	public void setTermType(IRI typeURI) {
		// Check if the typeIRI is one of the possible term type values for a
		// SubjectMap.
		if (typeURI.equals(lc.createResource(R2RMLVocabulary.TERM_IRI))
				|| typeURI.equals(lc
						.createResource(R2RMLVocabulary.TERM_BLANK_NODE))) {

			if (type == TermMapType.COLUMN_VALUED
					|| type == TermMapType.TEMPLATE_VALUED) {
				termtype = typeURI;
			} else {
				throw new IllegalStateException(
						"The term type can only be set for column "
								+ "and template valued SubjectMaps.");
			}
		} else {
			throw new IllegalArgumentException("The typeIRI is not a valid "
					+ "term type IRI for a SubjectMap.");
		}
	}

	@Override
	public IRI getClass(int index) {
		return classList.get(index);
	}

	@Override
	public GraphMap getGraphMap(int index) {
		return graphList.get(index);
	}

	@Override
	public List<IRI> getClasses() {
//		List<R> l = new ArrayList<R>();
//		for (Object o : classList) {
//			l.add(resourceClass.cast(o));
//		}
		return Collections.unmodifiableList(classList);
	}

	@Override
	public List<GraphMap> getGraphMaps() {
		return Collections.unmodifiableList(graphList);
	}

	@Override
	public void removeClass(Object classURI) {
		classList.remove(classURI);
	}

	@Override
	public void removeGraphMap(GraphMap gm) {
		classList.remove(gm);
	}

	@Override
	public  Set<Triple> serialize() {
		Set<Triple> stmtSet = new HashSet<Triple>();

		stmtSet.addAll(super.serialize());

		stmtSet.add(lc.createTriple(res, lc.getRDFType(),
				lc.createResource(R2RMLVocabulary.TYPE_SUBJECT_MAP)));

		for (IRI cl : classList) {
			stmtSet.add(lc.createTriple(res,
					lc.createResource(R2RMLVocabulary.PROP_CLASS), cl));
		}
		
		for(GraphMap g : graphList){
			if(g.getTermMapType() == TermMapType.CONSTANT_VALUED){
				// Use constant shortcut property.
				stmtSet.add(lc.createTriple(res,
						lc.createResource(R2RMLVocabulary.PROP_GRAPH), 
						lc.createResource(g.getConstant())));
			}else{
				stmtSet.add(lc.createTriple(res,
						lc.createResource(R2RMLVocabulary.PROP_GRAPH_MAP), 
						g.getResource()));
				stmtSet.addAll(g.serialize());
			}
		}

		return stmtSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((classList == null) ? 0 : classList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!super.equals(obj))
			return false;

		if (!(obj instanceof SubjectMapImpl))
			return false;

		SubjectMapImpl other = (SubjectMapImpl) obj;
		if (classList == null) {
			if (other.classList != null) {
				return false;
			}
		} else if (!classList.equals(other.classList)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "SubjectMapImpl [classList=" + classList + ", graphList="
				+ graphList + ", type=" + type + ", termtype=" + termtype
				+ ", template=" + template + ", constVal=" + constVal
				+ ", columnName=" + columnName + ", inverseExp=" + inverseExp
				+ ", res=" + res + "]";
	}

}
