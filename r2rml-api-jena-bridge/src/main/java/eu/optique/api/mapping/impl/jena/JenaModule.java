package eu.optique.api.mapping.impl.jena;

import com.google.inject.AbstractModule;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.jena.JenaRDF;

public class JenaModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(RDF.class).to(JenaRDF.class);
    }
}
