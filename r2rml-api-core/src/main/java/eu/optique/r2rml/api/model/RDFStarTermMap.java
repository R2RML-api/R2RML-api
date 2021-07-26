package eu.optique.r2rml.api.model;


/**
 * RDF-star Term Map, essentially a wrapper around three other TermMaps
 *
 * @author Lukas Sundqvist
 */
public interface RDFStarTermMap extends TermMap {

    ObjectMap getSubject();

    PredicateMap getPredicate();

    ObjectMap getObject();
}
