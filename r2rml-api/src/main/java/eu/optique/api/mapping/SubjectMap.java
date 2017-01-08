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

import org.apache.commons.rdf.api.IRI;

import java.util.List;

/**
 * R2RML Subject Map
 * 
 * @author Marius Strandhaug
 */
public interface SubjectMap extends TermMap {

	/**
	 * Adds a class to the SubjectMap. The class URI will be added to the end of
	 * the class list. A SubjectMap can have zero or more classes. The classURI
	 * parameter must be an instance of the library's resource class.
	 * 
	 * @param classURI
	 *            The class URI that will be added.
	 */
	public void addClass(IRI classURI);

	/**
	 * Adds a GraphMap to this SubjectMap. The GraphMap will be added to the end
	 * of the GraphMap list. A SubjectMap can have zero or more GraphMaps.
	 * 
	 * @param gm
	 *            The GraphMap that will be added.
	 */
	public void addGraphMap(GraphMap gm);

	/**
	 * Adds a list of GraphMaps to this SubjectMap. A SubjectMap can have zero
	 * or more GraphMaps. The GraphMaps will be added to the end of the GraphMap
	 * list.
	 * 
	 * @param gms
	 *            The list of GraphMaps that will be added.
	 */
	public void addGraphMap(List<GraphMap> gms);

	/**
	 * Sets the term type of this SubjectMap if it is a column-valued or
	 * template-valued TermMap. The default term type is rr:iri. The typeURI
	 * must be an instance of the library's resource class.<br>
	 * The possible values for the term type are
	 * (http://www.w3.org/TR/r2rml/#dfn-term-type>):<br>
	 * - rr:IRI<br>
	 * - rr:BlankNode
	 * 
	 * @param typeURI
	 *            The term type that will be set.
	 * @throws IllegalStateException
	 *             If the SubjectMap is not column-valued or template-valued.
	 * @throws IllegalArgumentException
	 *             If typeIRI is not a valid term type for a SubjectMap.
	 */
	public void setTermType(IRI typeURI);

	/**
	 * Get the class URI located at the given index.
	 *
	 * @param index
	 *            The index of the class URI.
	 * @return The class URI located at the given index.
	 * @throws IndexOutOfBoundsException
	 *             If the given index is out of range.
	 */
	public IRI getClass(int index);

	/**
	 * Get the GraphMap located at the given index.
	 * 
	 * @param index
	 *            The index of the GraphMap.
	 * @return The GraphMap located at the given index.
	 * @throws IndexOutOfBoundsException
	 *             If the given index is out of range.
	 */
	public GraphMap getGraphMap(int index);

	/**
	 * Returns an unmodifiable view of the list of classes that have been added
	 * to this SubjectMap.
	 *
	 * @return An unmodifiable list of classes.
	 */
	public List<IRI> getClasses();

	/**
	 * Returns an unmodifiable view of the list of GraphMaps that have been
	 * added to this SubjectMap.
	 * 
	 * @return An unmodifiable list of GraphMaps.
	 */
	public List<GraphMap> getGraphMaps();

	/**
	 * Remove the class given by the parameter, from the SubjectMap. The
	 * subsequent class URIs in the list will be shifted left. The classURI
	 * parameter must be an instance of the library's resource class.
	 *
     * @param classURI
     *            The class that will be removed.
     */
	public void removeClass(IRI classURI);

	/**
	 * Remove the GraphMap given by the parameter, from the SubjectMap. The
	 * subsequent GraphMaps in the list will be shifted left.
	 * 
	 * @param gm
	 *            The GraphMap to be removed.
	 */
	public void removeGraphMap(GraphMap gm);

}
