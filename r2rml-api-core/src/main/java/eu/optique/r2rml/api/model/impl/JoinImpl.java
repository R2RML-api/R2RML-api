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

import java.util.HashSet;
import java.util.Set;

import eu.optique.r2rml.api.model.Join;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;

/**
 * An implementation of Join.
 * 
 * @author Marius Strandhaug
 */
public class JoinImpl extends R2RMLClassImpl implements Join {

	private String child;
	private String parent;

	public JoinImpl(RDF rdf, String childCol, String parentCol) {

		super(rdf);
        setChild(childCol);
		setParent(parentCol);
        setNode(getRDF().createBlankNode());
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
	public Set<Triple> serialize() {
		Set<Triple> stmtSet = new HashSet<Triple>();

        stmtSet.add(getRDF().createTriple(node, getRDF().createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), getRDF().createIRI(R2RMLVocabulary.TYPE_JOIN)));

        stmtSet.add(getRDF().createTriple(node, getRDF().createIRI(R2RMLVocabulary.PROP_CHILD),
                getRDF().createLiteral(child)));

        stmtSet.add(getRDF().createTriple(node, getRDF().createIRI(R2RMLVocabulary.PROP_PARENT),
                getRDF().createLiteral(parent)));

		return stmtSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((child == null) ? 0 : child.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((node == null) ? 0 : node.hashCode());
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
		return "JoinImpl [child=" + child + ", parent=" + parent + ", node="
				+ node + "]";
	}

}
