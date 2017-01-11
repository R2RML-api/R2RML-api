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

import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Triple;

import java.util.Set;

/**
 * A common abstract of all the classes defined in the <a href="https://www.w3.org/TR/r2rml/#dfn-r2rml-vocabulary">R2RML specification</a>:
 *
 * <ul>
 * <li><code>rr:TriplesMap</code> is the class of
 * <a href="https://www.w3.org/TR/r2rml/#dfn-triples-map">triples maps</a>.</li>
 * <li><code>rr:LogicalTable</code> is the class of
 * <a href="https://www.w3.org/TR/r2rml/#dfn-logical-table">logical tables</a>. It has two subclasses:
 * <ul>
 * <li><code>rr:R2RMLView</code> is the class of
 * <a href="https://www.w3.org/TR/r2rml/#dfn-r2rml-view">R2RML views</a>.</li>
 * <li><code>rr:BaseTableOrView</code> is the class of
 * <a href="https://www.w3.org/TR/r2rml/#dfn-sql-base-table-or-view">SQL base tables or views</a>.</li>
 * </ul>
 * </li>
 * <li><code>rr:TermMap</code> is the class of
 * <a href="https://www.w3.org/TR/r2rml/#dfn-term-map">term maps</a>. It has four subclasses:
 * <ul>
 * <li><code>rr:SubjectMap</code> is the class of
 * <a href="https://www.w3.org/TR/r2rml/#dfn-subject-map">subject maps</a>.</li>
 * <li><code>rr:PredicateMap</code> is the class of
 * <a href="https://www.w3.org/TR/r2rml/#dfn-predicate-map">predicate maps</a>.</li>
 * <li><code>rr:ObjectMap</code> is the class of
 * <a href="https://www.w3.org/TR/r2rml/#dfn-object-map">object maps</a>.</li>
 * <li><code>rr:GraphMap</code> is the class of
 * <a href="https://www.w3.org/TR/r2rml/#dfn-graph-map">graph maps</a>.</li>
 * </ul>
 * </li>
 * <li><code>rr:PredicateObjectMap</code> is the class of
 * <a href="https://www.w3.org/TR/r2rml/#dfn-predicate-object-map">predicate-object maps</a>.</li>
 * <li><code>rr:RefObjectMap</code> is the class of
 * <a href="https://www.w3.org/TR/r2rml/#dfn-referencing-object-map">referencing object maps</a>.</li>
 * <li><code>rr:Join</code> is the class of
 * <a href="https://www.w3.org/TR/r2rml/#dfn-join-condition">join conditions</a>.</li>
 * </ul>
 *
 * The members of these classes are collectively called mapping components.
 *
 * @author Marius Strandhaug
 * @author xiao
 */
public interface MappingComponent {

    /**
     * Sets the resource used to serialize an R2RML component. The node parameter
     * must be an instance of the library's resource class.
     *
     * @param node The resource to set.
     */
    public void setNode(BlankNodeOrIRI node);

    /**
     * Get the resource used to serialize an R2RML component.
     *
     * @return The resource set by setNode(Object res).
     */
    //public Object getNode(Class resourceClass);
    public BlankNodeOrIRI getNode();

    /**
     * Serializes the R2RML component to a list of RDF triples.
     *
     * @return A set of triples.
     */
    public Set<Triple> serialize();
}
