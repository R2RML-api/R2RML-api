package eu.optique.api.mapping;

import org.apache.commons.rdf.api.BlankNode;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Literal;
import org.apache.commons.rdf.api.RDFTerm;
import org.apache.commons.rdf.api.Triple;

import java.util.Collection;

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
	 * Creates a resource with the given URI. Returns an object of the class
	 * returned by the method getResourceClass().
	 *
	 * @param URI
	 *            - The URI of the created resource.
	 * @return A resource.
	 */
	public IRI createResource(String URI);

	/**
	 * Creates a blank node resource. Returns an object of the class returned by
	 * the method getResourceClass().
	 *
	 * @return A resource.
	 */
	public BlankNode createBNode();

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
	public Triple createTriple(BlankNodeOrIRI subject, IRI predicate, BlankNodeOrIRI object);

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
	public Triple createLiteralTriple(BlankNodeOrIRI subject, IRI predicate,
			String litObject);

	/**
	 * Create a graph containing triples generated from the given collection of
	 * TriplesMaps. The returned graph will be the class returned by the method
	 * getGraphClass().
	 *
	 * @param maps
	 *            - The TriplesMaps that will be serialized.
	 * @return A graph with the given TriplesMaps.
	 */
	public Graph createGraph(Collection<TriplesMap> maps);

	/**
	 * Returns the URI of the rdf:type predicate. The URI will be of the class
	 * returned by the method getResourceClass().
	 *
	 * @return Resource with the URI of rdf:type.
	 */
	public IRI getRDFType();

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
	public Collection<BlankNodeOrIRI> getSubjects(Graph graph, IRI pred, RDFTerm obj);

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
	public Collection<RDFTerm> getObjects(Graph graph, BlankNodeOrIRI subject, IRI pred);

	/**
	 * Returns the resource class. A resource can either be named, or it can be
	 * a blank node.
	 *
	 * @return The resource class.
	 */
	default public Class<?> getResourceClass() {
		return BlankNodeOrIRI.class;
	}

	/**
	 * Returns the triple class. A triple consists of a subject, predicate and
	 * an object.
	 *
	 * @return The triple class.
	 */
	default public Class<?> getTripleClass() {
		return Triple.class;
	}

	/**
	 * Returns the graph class. A graph consists of a set of triples.
	 *
	 * @return The graph class.
	 */
	default public Class<?> getGraphClass() {
		return Graph.class;
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
    String getLexicalForm(RDFTerm node);
}
