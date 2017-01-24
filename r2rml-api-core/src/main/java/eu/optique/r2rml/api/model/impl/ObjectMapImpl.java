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

import eu.optique.r2rml.api.model.ObjectMap;
import eu.optique.r2rml.api.model.R2RMLVocabulary;
import eu.optique.r2rml.api.model.Template;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An implementation of an ObjectMap.
 *
 * @author Marius Strandhaug
 */
public class ObjectMapImpl extends TermMapImpl implements ObjectMap {

    private static List<String> validTermTypeIRIs = Arrays.asList(R2RMLVocabulary.TERM_IRI,
            R2RMLVocabulary.TERM_BLANK_NODE, R2RMLVocabulary.TERM_LITERAL);

    /**
     * dataType IRI for literals
     */
    private IRI dataType;

    /**
     * language tag when datatype rdf:langString
     */
    private String langTag;


    public ObjectMapImpl(RDF c, TermMapType termMapType, Template template) {
		super(c, termMapType, template);
	}

	public ObjectMapImpl(RDF c, TermMapType termMapType, String columnOrConst) {
		super(c, termMapType, columnOrConst);
	}

	@Override
	public void setTermType(IRI termTypeIRI) {

        final String typeIRIString = termTypeIRI.getIRIString();

        if (!validTermTypeIRIs.contains(typeIRIString)){
            throw new IllegalArgumentException(
                String.format("<%s> is not a valid term termMapType IRI for ObjectMap.", typeIRIString));
        }

        switch (termMapType) {
            case TEMPLATE_VALUED:
            case COLUMN_VALUED:
                this.termTypeIRI = termTypeIRI;
                // Remove language tag and data termMapType if the new term termMapType isn't
                // literal.
                if (!typeIRIString.equals(R2RMLVocabulary.TERM_LITERAL)) {
                    removeLanguageTag();
                    removeDatatype();
                }
                break;
            case CONSTANT_VALUED:
                 /*
                  * IGNORE: See R2RML Recommendation Section 7.4:
                  *
                  * NOTE. Constant-valued term maps are not considered as having a term termMapType, and
                  * specifying rr:termType on these term maps has no effect.
                  */
                break;
        }

    }

	@Override
	public void setDefaultTermType() {
		/*
		 * An object map's default term termMapType is Literal if it's column valued,
		 * has a language tag, or if it's data typed.
		 */
		if (termMapType == TermMapType.COLUMN_VALUED || langTag != null
				|| dataType != null) {
            termTypeIRI = getRDF().createIRI(R2RMLVocabulary.TERM_LITERAL);
		} else {
            termTypeIRI = getRDF().createIRI(R2RMLVocabulary.TERM_IRI);
		}
	}

	@Override
	public void setLanguageTag(String lang) {
        if (termTypeIRI.equals(getRDF().createIRI(R2RMLVocabulary.TERM_LITERAL))) {
			removeDatatype();
			langTag = lang;
		} else {
			throw new IllegalStateException("The term termMapType is " + termTypeIRI
					+ ". Should be " + R2RMLVocabulary.TERM_LITERAL + ".");
		}
	}

	@Override
	public void setDatatype(IRI datatypeURI) {
        if (termTypeIRI.equals(getRDF().createIRI(R2RMLVocabulary.TERM_LITERAL))) {
			dataType = datatypeURI;
			removeLanguageTag();
		} else {
			throw new IllegalStateException("The term termMapType is " + termTypeIRI
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

        stmtSet.add(getRDF().createTriple(getNode(),
                getRDF().createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
                getRDF().createIRI(R2RMLVocabulary.TYPE_OBJECT_MAP)));

		if (dataType != null) {
            stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI(R2RMLVocabulary.PROP_DATATYPE), dataType));
		} else if (langTag != null) {

            stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI(R2RMLVocabulary.PROP_LANGUAGE),
                    getRDF().createLiteral(langTag)));
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
				+ ", termMapType=" + termMapType + ", termTypeIRI=" + termTypeIRI + ", template="
				+ template + ", constVal=" + constVal + ", columnName="
				+ columnName + ", inverseExp=" + inverseExp + ", node=" + getNode()
				+ "]";
	}

}
