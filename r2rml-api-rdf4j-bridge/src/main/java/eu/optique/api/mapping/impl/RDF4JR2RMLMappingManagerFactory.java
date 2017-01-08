package eu.optique.api.mapping.impl;

import com.google.inject.Guice;
import com.google.inject.Injector;
import eu.optique.api.mapping.R2RMLMappingManagerFactory;
import org.apache.commons.rdf.api.RDF;

public class RDF4JR2RMLMappingManagerFactory implements R2RMLMappingManagerFactory {

    /**
     * @return A R2RMLMappingManager configured with RDF4J.
     */
    @Override
    public RDF4JR2RMLMappingManager getR2RMLMappingManager() {
        Injector injector = Guice.createInjector(new RDF4JModule());
        RDF rdf = injector.getInstance(RDF.class);
        return new RDF4JR2RMLMappingManager(rdf);
    }

}
