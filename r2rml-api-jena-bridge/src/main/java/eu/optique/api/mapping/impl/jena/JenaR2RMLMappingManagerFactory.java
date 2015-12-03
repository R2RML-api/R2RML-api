package eu.optique.api.mapping.impl.jena;

import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.R2RMLMappingManagerFactory;
import eu.optique.api.mapping.impl.R2RMLMappingManagerImpl;

public class JenaR2RMLMappingManagerFactory implements R2RMLMappingManagerFactory {

    /**
     * @return A R2RMLMappingManager configured with Jena.
     */
    @Override
    public R2RMLMappingManager getR2RMLMappingManager() {
        LibConfiguration jc = new JenaConfiguration();
        R2RMLMappingManager mm = new R2RMLMappingManagerImpl(jc);
        return mm;
    }

}
