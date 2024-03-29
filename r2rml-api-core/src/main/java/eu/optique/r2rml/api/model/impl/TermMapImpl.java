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

import eu.optique.r2rml.api.model.InverseExpression;
import eu.optique.r2rml.api.model.R2RMLVocabulary;
import eu.optique.r2rml.api.model.Template;
import eu.optique.r2rml.api.model.TermMap;
import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * An implementation of a TermMap. A TermMap must either be a SubjectMap, a
 * PredicateMap, an ObjectMap or a GraphMap, and is therefore abstract.
 *
 * @author Marius Strandhaug
 */
public abstract class TermMapImpl extends MappingComponentImpl implements TermMap {

    TermMapType termMapType;

    IRI termTypeIRI;

    // Only one of these will be set.
    Template template;

    RDFTerm constVal;

    String columnName;

    InverseExpression inverseExp;

    TermMapImpl(RDF rdf, Template template) {
        super(rdf);
        this.template = requireNonNull(template);
        this.termMapType = TermMapType.TEMPLATE_VALUED;
        setDefaultTermType();
        setNode(getRDF().createBlankNode());
    }

    TermMapImpl(RDF rdf, String columnName) {
        super(rdf);
        this.columnName = requireNonNull(columnName);
        this.termMapType = TermMapType.COLUMN_VALUED;
        setDefaultTermType();
        setNode(getRDF().createBlankNode());
    }

    TermMapImpl(RDF rdf, RDFTerm constant) {
        super(rdf);
        this.constVal = requireNonNull(constant);
        if(constant instanceof BlankNode){
            throw new IllegalArgumentException("BlankNode cannot be used as constant");
        } else if (constant instanceof IRI){
            this.termTypeIRI = getRDF().createIRI(R2RMLVocabulary.TERM_IRI);
        } else {// if (constant instanceof Literal)
            this.termTypeIRI = getRDF().createIRI(R2RMLVocabulary.TERM_LITERAL);
        }

        this.termMapType = TermMapType.CONSTANT_VALUED;
        setNode(getRDF().createBlankNode());
    }

    // Is only used by unofficial RDF-star extension
    TermMapImpl(RDF rdf) {
        super(rdf);
        this.termMapType = TermMapType.RDF_STAR_VALUED;
        this.termTypeIRI = null;
        setNode(getRDF().createBlankNode());
    }


    @Override
    public TermMapType getTermMapType() {
        return termMapType;
    }

    @Override
    public void setTermType(IRI typeIRI) {
        if (!getValidTermTypes().contains(typeIRI)) {
            throw new IllegalArgumentException(
                    String.format("invalid term termMapType IRI: %s .", typeIRI));
        }

        switch (termMapType) {
            case TEMPLATE_VALUED:
            case COLUMN_VALUED:
            case RDF_STAR_VALUED:
                this.termTypeIRI = typeIRI;
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
    public void setTemplate(Template template) {
        this.termMapType = TermMapType.TEMPLATE_VALUED;
        this.template = requireNonNull(template);
    }

    @Override
    public void setConstant(RDFTerm constVal) {
        // termTypeIRI will be ignored
        this.termMapType = TermMapType.CONSTANT_VALUED;
        this.constVal = requireNonNull(constVal);
    }

    @Override
    public void setColumn(String columnName) {
        this.termMapType = TermMapType.COLUMN_VALUED;
        this.columnName = requireNonNull(columnName);
    }

    @Override
    public void setInverseExpression(InverseExpression invExp) {
        if (getTermMapType() == TermMapType.COLUMN_VALUED
                || getTermMapType() == TermMapType.TEMPLATE_VALUED) {
            inverseExp = invExp;
        } else {
            throw new IllegalStateException("Wrong TermMapType");
        }
    }

    @Override
    public void setDefaultTermType() {
        termTypeIRI = getRDF().createIRI(R2RMLVocabulary.TERM_IRI);
    }

    @Override
    public IRI getTermType() {
        return termTypeIRI;
    }

    @Override
    public Template getTemplate() {
        return template;
    }

    @Override
    public String getTemplateString() {
        return template.toString();
    }

    @Override
    public RDFTerm getConstant() {
        return constVal;
    }

    @Override
    public String getColumn() {
        return columnName;
    }

    @Override
    public InverseExpression getInverseExpression() {
        return inverseExp;
    }

    @Override
    public String getInverseExpressionString() {
        return inverseExp.toString();
    }

    @Override
    public void removeInverseExpression() {
        inverseExp = null;
    }


    @Override
    public Set<Triple> serialize() {
        Set<Triple> stmtSet = new HashSet<Triple>();

        stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), getRDF().createIRI(R2RMLVocabulary.TYPE_TERM_MAP)));

        if (termMapType == TermMapType.COLUMN_VALUED) {
            stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI(R2RMLVocabulary.PROP_COLUMN),
                    getRDF().createLiteral(getColumn())));
        } else if (termMapType == TermMapType.CONSTANT_VALUED) {
            stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI(R2RMLVocabulary.PROP_CONSTANT),
                    getConstant()));
        } else if (termMapType == TermMapType.TEMPLATE_VALUED) {
            stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI(R2RMLVocabulary.PROP_TEMPLATE),
                    getRDF().createLiteral(getTemplateString())));
        }

        // Will always have the term termMapType explicitly listed.
        stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI(R2RMLVocabulary.PROP_TERM_TYPE), termTypeIRI));

        if (getInverseExpression() != null) {

            stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI(R2RMLVocabulary.PROP_INVERSE_EXPRESSION),
                    getRDF().createLiteral(getInverseExpressionString())));
        }

        return stmtSet;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((columnName == null) ? 0 : columnName.hashCode());
        result = prime * result
                + ((constVal == null) ? 0 : constVal.hashCode());
        result = prime * result + ((getNode() == null) ? 0 : getNode().hashCode());
        result = prime * result
                + ((termTypeIRI == null) ? 0 : termTypeIRI.hashCode());
        result = prime * result + ((termMapType == null) ? 0 : termMapType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof TermMapImpl))
            return false;

        TermMapImpl other = (TermMapImpl) obj;
        if (columnName == null) {
            if (other.columnName != null) {
                return false;
            }
        } else if (!columnName.equals(other.columnName)) {
            return false;
        }

        if (constVal == null) {
            if (other.constVal != null) {
                return false;
            }
        } else if (!constVal.equals(other.constVal)) {
            return false;
        }

        if (getNode() == null) {
            if (other.getNode() != null) {
                return false;
            }
        } else if (!getNode().equals(other.getNode())) {
            return false;
        }

        if (termTypeIRI == null) {
            if (other.termTypeIRI != null) {
                return false;
            }
        } else if (!termTypeIRI.equals(other.termTypeIRI)) {
            return false;
        }

        if (termMapType != other.termMapType) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "TermMapImpl [termMapType=" + termMapType + ", termTypeIRI=" + termTypeIRI
                + ", template=" + template + ", constVal=" + constVal
                + ", columnName=" + columnName + ", inverseExp=" + inverseExp
                + ", node=" + getNode() + "]";
    }

}
