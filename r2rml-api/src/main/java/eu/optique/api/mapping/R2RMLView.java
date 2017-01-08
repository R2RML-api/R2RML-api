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
package eu.optique.api.mapping;

import eu.optique.api.mapping.impl.R2RMLVocabulary;
import org.apache.commons.rdf.api.IRI;

import java.util.List;

/**
 * A logical tables' R2RML View.
 * 
 * @author Marius Strandhaug
 */
@W3C_R2RML_Recommendation(iri = R2RMLVocabulary.TYPE_R2RML_VIEW)
public interface R2RMLView extends LogicalTable {

	/**
	 * Sets the SQL Query of this R2RMLView. A R2RMLView must have exactly one
	 * SQL query.
	 * 
	 * @param sqlQuery
	 *            The SQL query that will be set.
	 * @throws NullPointerException
	 *             If sqlQuery is null.
	 */
    @W3C_R2RML_Recommendation(iri = R2RMLVocabulary.PROP_SQL_QUERY)
    public void setSqlQuery(String sqlQuery);

	/**
	 * Adds a SQL version identifier to this R2RMLView. The SQL version URI will
	 * be added to the end of the SQL version list. A R2RMLView can have any
	 * number of SQL version identifiers. The version parameter must be an
	 * instance of the library's resource class.
	 * 
	 * @param version
	 *            The SQL version that will be set.
	 */
    @W3C_R2RML_Recommendation(iri = R2RMLVocabulary.PROP_SQL_VERSION)
    public void addSQLVersion(IRI version);

	/**
	 * Get the SQL version URI located at the given index.
	 *
	 * @param index
	 *            The index of the SQL version URI.
	 * @return The SQL version URI located at the given index.
	 * @throws IndexOutOfBoundsException
	 *             If the given index is out of range.
	 */
    @W3C_R2RML_Recommendation(iri = R2RMLVocabulary.PROP_SQL_VERSION)
    public IRI getSQLVersion(int index);

	/**
	 * Returns an unmodifiable view of the list of SQL versions that have been
	 * added to this R2RMLView.
	 *
	 * @return An unmodifiable list of SQL versions.
	 */
    @W3C_R2RML_Recommendation(iri = R2RMLVocabulary.PROP_SQL_VERSION)
	public List<IRI> getSQLVersions();

	/**
	 * Remove the SQL version identifier given by the parameter, from the
	 * R2RMLTable. The subsequent SQL version URIs in the list will be shifted
	 * left.
	 * 
	 * @param version
	 *            The SQL version identifier that will be removed.
	 */
    @W3C_R2RML_Recommendation(iri = R2RMLVocabulary.PROP_SQL_VERSION)
    public void removeSQLVersion(IRI version);
}
