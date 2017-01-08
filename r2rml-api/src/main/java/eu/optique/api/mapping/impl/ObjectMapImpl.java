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

import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.ObjectMap;
import eu.optique.api.mapping.Template;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Triple;

/**
 * An implementation of an ObjectMap.
 * 
 * @author Marius Strandhaug
 */
public class ObjectMapImpl extends TermMapImpl implements ObjectMap {

	String langTag;
	IRI dataType;

	public ObjectMapImpl(LibConfiguration c, TermMapType termMapType,
			Template template) {
		super(c, termMapType, template);
	}

	public ObjectMapImpl(LibConfiguration c, TermMapType termMapType,
			String columnOrConst) {
		super(c, termMapType, columnOrConst);
	}

	@Override
	public void setTermType(IRI typeURI) {
		// Check if the typeURI is one of the possible term type values for an
		// ObjectMap.
        if (typeURI.equals(lc.getRDF().createIRI(R2RMLVocabulary.TERM_IRI))
				|| typeURI.equals(lc.getRDF().createIRI(R2RMLVocabulary.TERM_BLANK_NODE))
				|| typeURI.equals(lc.getRDF().createIRI(R2RMLVocabulary.TERM_LITERAL))) {

			if (type == TermMapType.COLUMN_VALUED
					|| type == TermMapType.TEMPLATE_VALUED) {
				termtype = typeURI;

				// Remove language tag and data type if the new term type isn't
				// literal.
                if (!typeURI.equals(lc.getRDF().createIRI(R2RMLVocabulary.TERM_LITERAL))) {
					removeLanguageTag();
					removeDatatype();
				}
			} else {
				throw new IllegalStateException(
						"The term type can only be set for column "
								+ "and template valued ObjectMaps.");
			}

		} else {
			throw new IllegalArgumentException("The typeIRI is not a valid "
					+ "term type IRI for an ObjectMap.");
		}
	}

	@Override
	public void setDefaultTermType() {
		/*
		 * An object map's default term type is Literal if it's column valued,
		 * has a language tag, or if it's data typed.
		 */
		if (type == TermMapType.COLUMN_VALUED || langTag != null
				|| dataType != null) {
            termtype = lc.getRDF().createIRI(R2RMLVocabulary.TERM_LITERAL);
		} else {
            termtype = lc.getRDF().createIRI(R2RMLVocabulary.TERM_IRI);
		}
	}

	@Override
	public void setLanguageTag(String lang) {
        if (termtype.equals(lc.getRDF().createIRI(R2RMLVocabulary.TERM_LITERAL))) {
			removeDatatype();
			langTag = lang;
		} else {
			throw new IllegalStateException("The term type is " + termtype
					+ ". Should be " + R2RMLVocabulary.TERM_LITERAL + ".");
		}
	}

	@Override
	public void setDatatype(IRI datatypeURI) {
        if (termtype.equals(lc.getRDF().createIRI(R2RMLVocabulary.TERM_LITERAL))) {
			dataType = datatypeURI;
			removeLanguageTag();
		} else {
			throw new IllegalStateException("The term type is " + termtype
					+ ". Should be " + R2RMLVocabulary.TERM_LITERAL + ".");
		}
	}

	@Override
	public String getLanguageTag() {
		return langTag;
	}

	@Override
	public IRI getDatatype() {
		return dataType;
	}

	@Override
	public void removeDatatype() {
		dataType = null;
	}

	@Override
	public void removeLanguageTag() {
		langTag = null;
	}

	@Override
	public Set<Triple> serialize() {
		Set<Triple> stmtSet = new HashSet<Triple>();

		stmtSet.addAll(super.serialize());

        stmtSet.add(lc.getRDF().createTriple(res, lc.getRDF().createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), lc.getRDF().createIRI(R2RMLVocabulary.TYPE_OBJECT_MAP)));

		if (dataType != null) {
            stmtSet.add(lc.getRDF().createTriple(res, lc.getRDF().createIRI(R2RMLVocabulary.PROP_DATATYPE), dataType));
		} else if (langTag != null) {

            stmtSet.add(lc.getRDF().createTriple(res, lc.getRDF().createIRI(R2RMLVocabulary.PROP_LANGUAGE),
                    lc.getRDF().createLiteral(langTag)));
		}

		return stmtSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result + ((langTag == null) ? 0 : langTag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!super.equals(obj))
			return false;

		if (!(obj instanceof ObjectMapImpl))
			return false;

		ObjectMapImpl other = (ObjectMapImpl) obj;
		if (dataType == null) {
			if (other.dataType != null) {
				return false;
			}
		} else if (!dataType.equals(other.dataType)) {
			return false;
		}

		if (langTag == null) {
			if (other.langTag != null) {
				return false;
			}
		} else if (!langTag.equals(other.langTag)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "ObjectMapImpl [langTag=" + langTag + ", dataType=" + dataType
				+ ", type=" + type + ", termtype=" + termtype + ", template="
				+ template + ", constVal=" + constVal + ", columnName="
				+ columnName + ", inverseExp=" + inverseExp + ", res=" + res
				+ "]";
	}

}
