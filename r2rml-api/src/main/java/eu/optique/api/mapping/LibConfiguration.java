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

	/**
	 * Creates a resource with the given URI. Returns an object of the class
	 * returned by the method getResourceClass().
	 *
	 * @param iri
	 *            - The URI of the created resource.
	 * @return A resource.
	 */
    default IRI createResource(String iri) {
        return getRDF().createIRI(iri);
    }

	/**
	 * Creates a blank node resource. Returns an object of the class returned by
	 * the method getResourceClass().
	 *
	 * @return A resource.
	 */
    default BlankNode createBlankNode(){
        return getRDF().createBlankNode();
    }

	/**
	 * Creates a triple from the given subject, predicate and object. The
	 * subject, predicate and object must all be of the class returned by the
	 * method getResourceClass().
	 *
	 * @param subject
	 *            - The subject of the triple.
	 * @param predicate
	 *            - The predicate of the triple.
	 * @param object
	 *            - The object of the triple.
	 * @return A triple with the given subject, predicate and object.
	 */
    default Triple createTriple(BlankNodeOrIRI subject, IRI predicate, BlankNodeOrIRI object){
        return getRDF().createTriple( subject, predicate, object);
    }

	/**
	 * Creates a literal triple from the given subject, predicate and literal
	 * object. The subject and predicate must both be of the class returned by
	 * the method getResourceClass().
	 *
	 * @param subject
	 *            - The subject of the triple.
	 * @param predicate
	 *            - The predicate of the triple.
	 * @param litObject
	 *            - The object of the triple.
	 * @return A triple with the given subject, predicate and literal object.
	 */
    default Triple createLiteralTriple(BlankNodeOrIRI subject, IRI predicate,
                                      String litObject) {

        return getRDF().createTriple(subject, predicate,
                getRDF().createLiteral(litObject));

    }

	/**
	 * Create a graph containing triples generated from the given collection of
	 * TriplesMaps. The returned graph will be the class returned by the method
	 * getGraphClass().
	 *
	 * @param maps
	 *            - The TriplesMaps that will be serialized.
	 * @return A graph with the given TriplesMaps.
	 */
	default Graph createGraph(Collection<TriplesMap> maps){
        Graph m = getRDF().createGraph();

        for (TriplesMap tm : maps) {
            tm.serialize().forEach(m::add);
        }

        return m;

    }

	/**
	 * Returns the URI of the rdf:type predicate. The URI will be of the class
	 * returned by the method getResourceClass().
	 *
	 * @return Resource with the URI of rdf:type.
	 */
	default IRI getRDFType(){
        return getRDF().createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
    }

	/**
	 * Gets the subject of all triples in the graph with the given predicate and
	 * object. Null-values are considered as wildcards. The objects in the
	 * returned collection are of the class returned by the method
	 * getResourceClass().
	 *
	 * @param graph
	 *            - The graph to get triples from.
	 * @param pred
	 *            - The predicate of the triple.
	 * @param obj
	 *            - The object of the triple.
	 * @return - A collection of resources.
	 */
	default public Collection<BlankNodeOrIRI> getSubjects(Graph graph, IRI pred, RDFTerm obj) {
        return graph.stream(null, pred, obj)
                .map(Triple::getSubject)
                .collect(toSet());
    }

	/**
	 * Gets the object of all triples in the graph with the given subject and
	 * predicate. Null-values are considered as wildcards. The objects in the
	 * returned collection are of the class returned by the method
	 * getResourceClass().
	 *
	 * @param graph
	 *            - The graph to get triples from.
	 * @param subject
	 *            - The subject of the triple.
     * @param pred
     *            - The predicate of the triple.
	 * @return - A collection of resources.
	 */
    default Collection<RDFTerm> getObjects(Graph graph, BlankNodeOrIRI subject, IRI pred){
        return graph.stream(subject, pred, null)
                .map(Triple::getObject)
                .collect(toSet());
    }


    /**
     *
     * Gets the lexical form (no quotation or escape) of a node
     * <ul>
     * <li>
     *     For IRIs, return the UNQUOTED String representation.
     * </li>
     * <li>
     *     For Literals, return the UNESCAPED string representation of the value.
     * </li>
     * </ul>
     *
     * NOTE: Avoid using toString() methods in general, since they are not reliable.
     *
     * @param node an IRI, Literal, or BNode
     * @return string
     */
    default String getLexicalForm(RDFTerm node) {
        if (node instanceof IRI) {
            return ((IRI)node).getIRIString();
        } else if (node instanceof BlankNode) {
            return ((BlankNode)node).uniqueReference();
        } else if (node instanceof Literal) {
            return ((Literal)node).getLexicalForm();
        } else {
            throw new IllegalArgumentException("unknown term: " + node);
        }
    }
}
