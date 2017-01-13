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

import java.util.Collection;

import eu.optique.r2rml.api.model.impl.InvalidR2RMLMappingException;
import org.apache.commons.rdf.api.Graph;

/**
 * Interface representing an R2RML mapping collection, i.e. a set of triples
 * maps.
 * 
 * @author michael.schmidt
 */
public interface R2RMLMappingCollection {
	/**
	 * Sets the RDF graph of this MappingCollection. Generates the TriplesMaps
	 * from the graph object. The graph parameter must be an instance of the
	 * library's graph class.
	 * 
	 * @param graph
	 *            The graph to be used for mapping generation.
	 * @throws InvalidR2RMLMappingException
	 *             if the mapping is invalid
	 * @throws NullPointerException
	 *             if the graph is null.
	 */
	public void load(Graph graph) throws InvalidR2RMLMappingException;

	/**
	 * Returns all triples maps in the R2RML mapping generated from the graph
	 * given at construction.
	 */
	public Collection<TriplesMap> getTriplesMaps();

	/**
	 * Add a triples map to the R2RML mapping.
	 */
	public void addTriplesMap(TriplesMap mapping);

	/**
	 * Add a collection of triples maps to the R2RML mapping.
	 */
	public void addTriplesMaps(Collection<TriplesMap> mappings);

}
