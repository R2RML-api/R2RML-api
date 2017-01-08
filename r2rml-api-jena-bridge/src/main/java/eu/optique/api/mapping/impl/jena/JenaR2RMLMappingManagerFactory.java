package eu.optique.api.mapping.impl.jena;

import com.google.inject.Guice;
import com.google.inject.Injector;
import eu.optique.api.mapping.R2RMLMappingManagerFactory;
import eu.optique.api.mapping.impl.R2RMLMappingManagerImpl;
import org.apache.commons.rdf.api.RDF;

public class JenaR2RMLMappingManagerFactory implements R2RMLMappingManagerFactory {

    /**
     * @return A R2RMLMappingManager configured with Jena.
     */
    @Override
    public JenaR2RMLMappingManager getR2RMLMappingManager() {
        Injector injector = Guice.createInjector(new JenaModule());
        RDF rdf = injector.getInstance(RDF.class);
        return new JenaR2RMLMappingManager(rdf);
    }
}
