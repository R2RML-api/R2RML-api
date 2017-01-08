package eu.optique.api.mapping.impl.jena;

import eu.optique.api.mapping.TriplesMap;
import eu.optique.api.mapping.impl.InvalidR2RMLMappingException;
import eu.optique.api.mapping.impl.R2RMLMappingManagerImpl;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.jena.JenaGraph;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.rdf.model.Model;

import java.util.Collection;

public class JenaR2RMLMappingManager  extends R2RMLMappingManagerImpl {

    JenaR2RMLMappingManager(RDF rdf) {
        super(rdf);
    }

    public Collection<TriplesMap> importMappings(Model model) throws InvalidR2RMLMappingException {
        return importMappings(((JenaRDF) getRDF()).asGraph(model));
    }

    @Override
    public JenaGraph exportMappings(Collection<TriplesMap> maps) {
        return (JenaGraph) super.exportMappings(maps);
    }
}
