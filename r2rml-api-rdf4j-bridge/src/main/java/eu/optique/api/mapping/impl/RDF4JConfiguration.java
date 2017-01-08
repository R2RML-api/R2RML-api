package eu.optique.api.mapping.impl;

import eu.optique.api.mapping.LibConfiguration;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.rdf4j.RDF4J;

/**
 * The library configuration for the RDF4J API.
 *
 * @author Marius Strandhaug
 * @author xiao
 */
public class RDF4JConfiguration implements LibConfiguration {

    private static RDF vf = new RDF4J();

    @Override
    public RDF getRDF() {
        return vf;
    }

}
