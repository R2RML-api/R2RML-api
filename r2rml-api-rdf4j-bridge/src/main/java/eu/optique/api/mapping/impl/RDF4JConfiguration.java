package eu.optique.api.mapping.impl;

import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.TriplesMap;
import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.rdf4j.RDF4J;

import java.util.Collection;

import static java.util.stream.Collectors.toSet;

/**
 * The library configuration for the OpenRDF Sesame API.
 * 
 * Uses org.eclipse.rdf4j.model.Resource as the resource class,
 * org.eclipse.rdf4j.model.Statement as the triple class and org.eclipse.rdf4j.model.Model
 * as the graph class.
 * 
 * @author Marius Strandhaug
 * @author xiao
 */
public class RDF4JConfiguration implements LibConfiguration {

	//private ValueFactory vf = SimpleValueFactory.getInstance();

    private RDF vf = new RDF4J();

    @Override
    public RDF getRDF() {
        return vf;
    }

}
