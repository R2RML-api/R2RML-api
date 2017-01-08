package eu.optique.api.mapping.impl;

import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.TriplesMap;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.apache.commons.rdf.rdf4j.RDF4JGraph;
import org.eclipse.rdf4j.model.Model;

import java.util.Collection;

public class RDF4JR2RMLMappingManager extends R2RMLMappingManagerImpl{

    public RDF4JR2RMLMappingManager(LibConfiguration lc) {
        super(lc);
    }

    public Collection<TriplesMap> importMappings(Model model) throws InvalidR2RMLMappingException {
        RDF4J factory = new RDF4J();
        return importMappings(factory.asGraph(model));
    }

    public RDF4JGraph exportMappings(Collection<TriplesMap> maps) {
        //RDF4J factory = new RDF4J();
        return (RDF4JGraph)super.exportMappings(maps);
    }
}
