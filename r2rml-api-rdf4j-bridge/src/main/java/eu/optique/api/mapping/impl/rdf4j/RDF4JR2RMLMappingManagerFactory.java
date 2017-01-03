package eu.optique.api.mapping.impl.rdf4j;

import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.R2RMLMappingManagerFactory;
import eu.optique.api.mapping.impl.R2RMLMappingManagerImpl;

public class RDF4JR2RMLMappingManagerFactory implements R2RMLMappingManagerFactory {

    /**
     * @return A R2RMLMappingManager configured with Sesame.
     */
    @Override
    public R2RMLMappingManager getR2RMLMappingManager() {
        LibConfiguration jc = new RDF4JConfiguration();
        R2RMLMappingManager mm = new R2RMLMappingManagerImpl(jc);
        return mm;
    }

}
