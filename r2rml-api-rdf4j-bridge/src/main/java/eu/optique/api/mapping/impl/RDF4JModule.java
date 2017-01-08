package eu.optique.api.mapping.impl;

import com.google.inject.AbstractModule;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.rdf4j.RDF4J;

public class RDF4JModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(RDF.class).to(RDF4J.class);
    }
}
