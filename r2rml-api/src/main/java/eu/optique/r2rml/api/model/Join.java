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

import eu.optique.r2rml.api.model.impl.R2RMLVocabulary;

/**
 * R2RML Join Condition
 * 
 * @author Marius Strandhaug
 */
@W3C_R2RML_Recommendation(iri = R2RMLVocabulary.TYPE_JOIN)
public interface Join extends R2RMLClass {

	/**
	 * Set the child to a column that exists in the logical table of the triples
	 * map that contains the referencing object map. A Join must have exactly
	 * one child.
	 * 
	 * @param columnName
	 *            The name of the column.
	 * @throws NullPointerException
	 *             If columnName is null.
	 */
	@W3C_R2RML_Recommendation(iri = R2RMLVocabulary.PROP_CHILD)
	public void setChild(String columnName);

	/**
	 * Set the parent to a column that exists in the logical table of the
	 * RefObjectMap's parent triples map. A Join must have exactly one parent.
	 * 
	 * @param columnName
	 *            The name of the column.
	 * @throws NullPointerException
	 *             If columnName is null.
	 */
    @W3C_R2RML_Recommendation(iri = R2RMLVocabulary.PROP_PARENT)
	public void setParent(String columnName);

	/**
	 * Get the child for this Join.
	 * 
	 * @return The child associated with this Join.
	 */
    @W3C_R2RML_Recommendation(iri = R2RMLVocabulary.PROP_CHILD)
	public String getChild();

	/**
	 * Get the parent for this Join.
	 * 
	 * @return The parent associated with this Join.
	 */
    @W3C_R2RML_Recommendation(iri = R2RMLVocabulary.PROP_PARENT)
	public String getParent();

}
