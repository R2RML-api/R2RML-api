package eu.optique.r2rml.api.binding.rdf4j;

import eu.optique.r2rml.api.model.R2RMLMappingManagerFactory;
import org.apache.commons.rdf.rdf4j.RDF4J;

public class RDF4JR2RMLMappingManagerFactory implements R2RMLMappingManagerFactory {

    /**
     * @return A R2RMLMappingManager configured with RDF4J.
     */
    @Override
    public RDF4JR2RMLMappingManager getR2RMLMappingManager() {
        return new RDF4JR2RMLMappingManager(new RDF4J());
    }

}
