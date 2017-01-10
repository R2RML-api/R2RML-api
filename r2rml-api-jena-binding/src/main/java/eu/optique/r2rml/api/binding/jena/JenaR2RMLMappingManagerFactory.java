package eu.optique.r2rml.api.binding.jena;

import eu.optique.r2rml.api.model.R2RMLMappingManagerFactory;
import org.apache.commons.rdf.jena.JenaRDF;

public class JenaR2RMLMappingManagerFactory implements R2RMLMappingManagerFactory {

    /**
     * @return A R2RMLMappingManager configured with Jena.
     */
    @Override
    public JenaR2RMLMappingManager getR2RMLMappingManager() {
        return new JenaR2RMLMappingManager(new JenaRDF());
    }
}
