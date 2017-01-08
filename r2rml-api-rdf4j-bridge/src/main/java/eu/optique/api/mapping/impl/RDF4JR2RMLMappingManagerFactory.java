package eu.optique.api.mapping.impl;

import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.R2RMLMappingManagerFactory;

public class RDF4JR2RMLMappingManagerFactory implements R2RMLMappingManagerFactory {

    /**
     * @return A R2RMLMappingManager configured with Sesame.
     */
    @Override
    public RDF4JR2RMLMappingManager getR2RMLMappingManager() {
        LibConfiguration jc = new RDF4JConfiguration();
        RDF4JR2RMLMappingManager mm = new RDF4JR2RMLMappingManager(jc);
        return mm;
    }

}
