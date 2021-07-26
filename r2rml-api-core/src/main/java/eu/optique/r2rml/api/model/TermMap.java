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
package eu.optique.r2rml.api.model;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;

import java.util.List;

/**
 * R2RML Term Map
 *
 * @author Marius Strandhaug
 */
@W3C_R2RML_Recommendation(R2RMLVocabulary.TYPE_TERM_MAP)
public interface TermMap extends MappingComponent {

    /**
     * The term map must be set to one of these values when created.
     */
    public enum TermMapType {
        CONSTANT_VALUED, TEMPLATE_VALUED, COLUMN_VALUED, RDF_STAR_VALUED
    }



    /**
     * Sets the term type of this TermMap if it is column-valued or
     * template-valued.
     *
     * @param typeIRI
     *            The term type that will be set.
     *
     * @throws IllegalArgumentException
     *             If typeIRI is not a valid term type for an ObjectMap.
     */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_TERM_TYPE)
    void setTermType(IRI typeIRI);


    List<IRI> getValidTermTypes();


    /**
     * Get the TermMapType of this TermMap. A TermMap's TermMapType will never
     * change.
     *
     * @return The TermMapType of this TermMap.
     */
    public TermMapType getTermMapType();

    /**
     * Set the template-value of this TermMap if it's a template-valued TermMap.
     *
     * @param template The template value that will be set.
     * @throws IllegalStateException If the TermMap is not template-valued.
     */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_TEMPLATE)
    public void setTemplate(Template template);

    /**
     * Set the constant-value of this TermMap if it's a constant-valued TermMap.
     *
     * @param constVal The constant value that will be set.
     * @throws IllegalStateException If the TermMap is not constant-valued.
     */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_CONSTANT)
    public void setConstant(RDFTerm constVal);

    /**
     * Set the column-value of this TermMap if it's a constant-valued TermMap.
     * The columnName must be a column name in the TriplesMap's LogicalTable.
     *
     * @param columnName The column value that will be set.
     * @throws IllegalStateException If the TermMap is not column-valued.
     */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_COLUMN)
    public void setColumn(String columnName);

    /**
     * Sets the inverse expression of this TermMap. An inverse expression can
     * only be set if the TermMap is either column-valued or template-valued.
     * The inverse expression template must satisfy the conditions given at
     * http://www.w3.org/TR/r2rml/#dfn-inverse-expression. A TermMap can have
     * zero or one inverse expressions.
     *
     * @param invExp The inverse expression.
     * @throws IllegalStateException If the TermMap is not template-valued or column-valued.
     */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_INVERSE_EXPRESSION)
    public void setInverseExpression(InverseExpression invExp);

    /**
     * Set the term type of this TermMap to the default term type. The default
     * term type is given at http://www.w3.org/TR/r2rml/#dfn-term-type.
     *
     * If the term map does not have a rr:termType property, then its term type is:
     * <ul>
     *     <li>r:Literal, if it is an object map and at least one of the following conditions is true:
     *  <ul>
     * <li>It is a column-based term map.</li>
     * <li>It has a rr:language property (and thus a specified language tag).</li>
     * <li>It has a rr:datatype property (and thus a specified datatype).</li>
     * </ul>
     * </li>
     * <li> rr:IRI, otherwise.</li>
     </ul>r
     */
    public void setDefaultTermType();

    /**
     * Returns the term type of this TermMap. The term type can be rr:IRI,
     * rr:BlankNode or rr:Literal.
     *
     * @return The term type of this TermMap.
     */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_TERM_TYPE)
    public IRI getTermType();

    /**
     * Get the template value of this TermMap. It will return null if this is
     * not a template-valued TermMap.
     *
     * @return The template value of this TermMap.
     */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_TEMPLATE)
    public Template getTemplate();

    /**
     * Get the template value of this TermMap as a String.
     *
     * @return The string value of the template of this TermMap.
     */
    public String getTemplateString();

    /**
     * Get the constant value of this TermMap. It will return null if this is
     * not a constant-valued TermMap.
     *
     * @return The constant value of this TermMap.
     */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_CONSTANT)
    public RDFTerm getConstant();

    /**
     * Get the column value of this TermMap. It will return null if this is not
     * a column-valued TermMap.
     *
     * @return The column value of this TermMap.
     */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_COLUMN)
    public String getColumn();

    /**
     * Get the inverse expression of this TermMap if there is one.
     *
     * @return The inverse expression of this TermMap.
     */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_INVERSE_EXPRESSION)
    public InverseExpression getInverseExpression();

    /**
     * Get the inverse expression of this TermMap as a String.
     *
     * @return The inverse expression String of this TermMap.
     */
    public String getInverseExpressionString();

    /**
     * Remove this TermMap's inverse expression if there is one.
     */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_INVERSE_EXPRESSION)
    public void removeInverseExpression();

}
