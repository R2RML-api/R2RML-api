package eu.optique.api.mapping;

import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import java.util.Collection;

import static java.util.stream.Collectors.toSet;

/**
 * The library configuration that is used by the API to handle RDF. This
 * interface can be implemented in order to extend the support for other
 * libraries.
 *
 * @author Marius Strandhaug
 * @author xiao
 */
public interface LibConfiguration {

    /**
     * Gets a commons-rdf RDF factory
     *
     * @return RDF Factory
     */
    public RDF getRDF();
}
