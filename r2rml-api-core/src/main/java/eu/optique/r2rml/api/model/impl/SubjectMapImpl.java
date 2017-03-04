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

import eu.optique.r2rml.api.model.GraphMap;
import eu.optique.r2rml.api.model.R2RMLVocabulary;
import eu.optique.r2rml.api.model.SubjectMap;
import eu.optique.r2rml.api.model.Template;
import eu.optique.r2rml.api.model.TermMap;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An implementation of a SubjectMap.
 * 
 * @author Marius Strandhaug
 */
public class SubjectMapImpl extends TermMapImpl implements SubjectMap {

    private List<IRI> validTermTypes = Arrays.asList(
            getRDF().createIRI(R2RMLVocabulary.TERM_IRI),
            getRDF().createIRI(R2RMLVocabulary.TERM_BLANK_NODE));


    private ArrayList<IRI> classList;

	private ArrayList<GraphMap> graphList;

	SubjectMapImpl(RDF c, Template template) {
		super(c, template);

		classList = new ArrayList<>();
		graphList = new ArrayList<>();
	}

	SubjectMapImpl(RDF c, String columnName) {
		super(c, columnName);

		classList = new ArrayList<>();
		graphList = new ArrayList<>();
	}

    SubjectMapImpl(RDF c, IRI constant) {
        super(c, constant);

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
	public IRI getClass(int index) {
		return classList.get(index);
	}

	@Override
	public GraphMap getGraphMap(int index) {
		return graphList.get(index);
	}

	@Override
	public List<IRI> getClasses() {
		return Collections.unmodifiableList(classList);
	}

	@Override
	public List<GraphMap> getGraphMaps() {
		return Collections.unmodifiableList(graphList);
	}

	@Override
	public void removeClass(IRI classURI) {
		classList.remove(classURI);
	}

	@Override
	public void removeGraphMap(GraphMap gm) {
        graphList.remove(gm);
	}

	@Override
	public  Set<Triple> serialize() {
		Set<Triple> stmtSet = new HashSet<Triple>();

		stmtSet.addAll(super.serialize());

        stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), getRDF().createIRI(R2RMLVocabulary.TYPE_SUBJECT_MAP)));

		for (IRI cl : classList) {
            stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI(R2RMLVocabulary.PROP_CLASS), cl));
		}
		
		for(GraphMap g : graphList){
			if(g.getTermMapType() == TermMap.TermMapType.CONSTANT_VALUED){
				// Use constant shortcut property.
                stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI(R2RMLVocabulary.PROP_GRAPH), g.getConstant()));
			}else{
                stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI(R2RMLVocabulary.PROP_GRAPH_MAP), g.getNode()));
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
				+ graphList + ", termMapType=" + termMapType + ", termTypeIRI=" + termTypeIRI
				+ ", template=" + template + ", constVal=" + constVal
				+ ", columnName=" + columnName + ", inverseExp=" + inverseExp
				+ ", node=" + getNode() + "]";
	}

    @Override
    public List<IRI> getValidTermTypes() {
        return validTermTypes;
    }
}
