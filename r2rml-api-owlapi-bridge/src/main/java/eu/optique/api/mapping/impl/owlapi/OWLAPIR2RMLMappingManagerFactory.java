package eu.optique.api.mapping.impl.owlapi;

import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.R2RMLMappingManagerFactory;
import eu.optique.api.mapping.impl.owlapi.OWLAPIR2RMLMappingManagerFactory;
import eu.optique.api.mapping.impl.R2RMLMappingManagerImpl;

public class OWLAPIR2RMLMappingManagerFactory implements R2RMLMappingManagerFactory {

    /**
     * @return A R2RMLMappingManager configured with OWLAPI.
     */
    @Override
    public R2RMLMappingManager getR2RMLMappingManager() {
        LibConfiguration jc = new OWLAPIConfiguration();
        R2RMLMappingManager mm = new R2RMLMappingManagerImpl(jc);
        return mm;
    }

}
