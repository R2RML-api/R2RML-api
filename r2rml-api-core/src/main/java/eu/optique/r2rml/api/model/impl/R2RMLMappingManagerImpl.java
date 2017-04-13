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

import java.util.Collection;

import eu.optique.r2rml.api.MappingFactory;
import eu.optique.r2rml.api.model.R2RMLMappingCollection;
import eu.optique.r2rml.api.R2RMLMappingManager;
import eu.optique.r2rml.api.model.TriplesMap;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDF;

import static java.util.Objects.requireNonNull;

/**
 * Implementation of the {@link R2RMLMappingManager} interface.
 *
 * @author michael.schmidt
 */
public class R2RMLMappingManagerImpl implements R2RMLMappingManager {

    private final RDF rdf;

    private MappingFactory mf;

    public R2RMLMappingManagerImpl(RDF rdf) {
        this.rdf = rdf;
        mf = new MappingFactoryImpl(rdf);
    }

    protected RDF getRDF() {
        return rdf;
    }

    @Override
    public MappingFactory getMappingFactory() {
        return mf;
    }

    @Override
    public Collection<TriplesMap> importMappings(Graph graph)
            throws IllegalArgumentException, InvalidR2RMLMappingException {

        // try once to extract the mapping from the graph and do some basic
        // validations
        // e.g. whether are any triplesmap at all, whether those have a
        // mandatory logical table as well as a subjectmap
        R2RMLMappingCollection mc = extractR2RMLMapping(graph);

        if (mc == null || mc.getTriplesMaps().isEmpty())
            throw new IllegalArgumentException("Does not contain any (valid) TriplesMaps");
        for (TriplesMap map : mc.getTriplesMaps()) {
            requireNonNull(map.getLogicalTable(),  () -> "No logical table for TriplesMap " + map.getNode().toString());
            if (map.getLogicalTable().getNode() == null && map.getLogicalTable().getSQLQuery() == null)
                throw new IllegalArgumentException("No logical table for TriplesMap " + map.getNode().toString());
            requireNonNull(map.getSubjectMap(), () -> map.getNode().toString() + " does not have any SubjectMap");
        }

        return mc.getTriplesMaps();
    }

    public Graph exportMappings(Collection<TriplesMap> maps) {

        requireNonNull(maps, "The mapping collection is null.");

        if (maps.isEmpty())
            throw new IllegalArgumentException("The mapping collection is empty");

        Graph m = rdf.createGraph();

        for (TriplesMap tm : maps) {
            tm.serialize().forEach(m::add);
        }

        return m;
    }

    /**
     * Extracts the R2RML mapping from an RDF graph.
     *
     * @param graph the RDF graph defining the R2RML mapping
     * @return the list of triples map objects defined in the graph
     * @throws InvalidR2RMLMappingException if invalid mapping found
     */
    protected R2RMLMappingCollection extractR2RMLMapping(Graph graph)
            throws InvalidR2RMLMappingException {
        return new R2RMLMappingCollectionImpl(this, rdf, graph);
    }
}
