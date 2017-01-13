package eu.optique.r2rml.api.binding.jena;

import eu.optique.r2rml.api.model.TriplesMap;
import eu.optique.r2rml.api.model.impl.InvalidR2RMLMappingException;
import eu.optique.r2rml.api.model.impl.R2RMLMappingManagerImpl;
import org.apache.commons.rdf.jena.JenaGraph;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.rdf.model.Model;

import java.util.Collection;

public class JenaR2RMLMappingManager extends R2RMLMappingManagerImpl {

    private static JenaR2RMLMappingManager INSTANCE = new JenaR2RMLMappingManager(new JenaRDF());

    private JenaR2RMLMappingManager(JenaRDF rdf) {
        super(rdf);
    }

    public Collection<TriplesMap> importMappings(Model model) throws InvalidR2RMLMappingException {
        return importMappings(((JenaRDF) getRDF()).asGraph(model));
    }

    @Override
    public JenaGraph exportMappings(Collection<TriplesMap> maps) {
        return (JenaGraph) super.exportMappings(maps);
    }

    public static JenaR2RMLMappingManager getInstance(){
        return INSTANCE;
    }
}
