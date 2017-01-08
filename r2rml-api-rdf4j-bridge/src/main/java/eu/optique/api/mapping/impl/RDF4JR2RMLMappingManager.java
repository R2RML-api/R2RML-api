package eu.optique.api.mapping.impl;

import eu.optique.api.mapping.TriplesMap;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.apache.commons.rdf.rdf4j.RDF4JGraph;
import org.eclipse.rdf4j.model.Model;

import java.util.Collection;

public class RDF4JR2RMLMappingManager extends R2RMLMappingManagerImpl {

    public RDF4JR2RMLMappingManager(RDF lc) {
        super(lc);
    }

    public Collection<TriplesMap> importMappings(Model model) throws InvalidR2RMLMappingException {
        return importMappings(((RDF4J) getRDF()).asGraph(model));
    }

    @Override
    public RDF4JGraph exportMappings(Collection<TriplesMap> maps) {
        return (RDF4JGraph) super.exportMappings(maps);
    }
}
