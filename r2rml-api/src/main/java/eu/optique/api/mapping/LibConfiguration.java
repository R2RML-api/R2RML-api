package eu.optique.api.mapping;

import java.util.Collection;

/**
 * The library configuration that is used by the API to handle RDF. This
 * interface can be implemented in order to extend the support for other
 * libraries.
 * 
 * @author Marius Strandhaug
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
	public Object createResource(String URI);

	/**
	 * Creates a blank node resource. Returns an object of the class returned by
	 * the method getResourceClass().
	 * 
	 * @return A resource.
	 */
	public Object createBNode();

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
	public Object createTriple(Object subject, Object predicate, Object object);

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
	public Object createLiteralTriple(Object subject, Object predicate,
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
	public Object createGraph(Collection<TriplesMap> maps);

	/**
	 * Returns the URI of the rdf:type predicate. The URI will be of the class
	 * returned by the method getResourceClass().
	 * 
	 * @return Resource with the URI of rdf:type.
	 */
	public Object getRDFType();

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
	public Collection<Object> getSubjects(Object graph, Object pred, Object obj);

	/**
	 * Gets the object of all triples in the graph with the given subject and
	 * predicate. Null-values are considered as wildcards. The objects in the
	 * returned collection are of the class returned by the method
	 * getResourceClass().
	 * 
	 * @param graph
	 *            - The graph to get triples from.
	 * @param subj
	 *            - The subject of the triple.
	 * @param pred
	 *            - The predicate of the triple.
	 * @return - A collection of resources.
	 */
	public Collection<Object> getObjects(Object graph, Object subj, Object pred);

	/**
	 * Returns the resource class. A resource can either be named, or it can be
	 * a blank node.
	 * 
	 * @return The resource class.
	 */
	public Class<?> getResourceClass();

	/**
	 * Returns the triple class. A triple consists of a subject, predicate and
	 * an object.
	 * 
	 * @return The triple class.
	 */
	public Class<?> getTripleClass();

	/**
	 * Returns the graph class. A graph consists of a set of triples.
	 * 
	 * @return The graph class.
	 */
	public Class<?> getGraphClass();

    /**
     * the unquoted String representation of the IRI
     *
     * @param iri an IRI object
     * @return string
     */
    String toUnquotedString(Object iri);
}
