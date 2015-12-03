package eu.optique.api.mapping.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFLiteral;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFResourceBlankNode;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.io.RDFResourceIRI;
import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.TriplesMap;

/**
 * The library configuration for the OWL API.
 * 
 * Uses org.coode.owlapi.rdf.model.RDFResource as the resource class,
 * org.coode.owlapi.rdf.model.RDFTriple as the triple class and
 * java.util.Set<org.coode.owlapi.rdf.model.RDFTriple> as the graph class.
 * 
 * @author Marius Strandhaug
 */
public class OWLAPIConfiguration implements LibConfiguration {

	OWLOntologyManager manager;
	OWLDataFactory factory;

	private Class<RDFResource> res = RDFResource.class;
	private Class<RDFTriple> trpl = RDFTriple.class;
	@SuppressWarnings("unchecked")
	private Class<Set<RDFTriple>> graph = (Class<Set<RDFTriple>>) (Class<?>) Set.class;

	public OWLAPIConfiguration() {
		manager = OWLManager.createOWLOntologyManager();
		factory = manager.getOWLDataFactory();
	}

	@Override
	public RDFResource createResource(String URI) {
		return new RDFResourceIRI(IRI.create(URI));
	}

	@Override
	public RDFResource createBNode() {

		String s = factory.getOWLAnonymousIndividual().getID().getID();

		/*
		 * Strips all characters from the beginning of the string, so that the
		 * actual ID number is left. Requires that the string ends with a
		 * number.
		 */
		int id = Integer.parseInt(s.replaceFirst("(\\D*)((^\\d)*)", ""));
		return new RDFResourceBlankNode(id, /*isIndividual*/ true, /*forceId*/true);

	}

	@Override
	public RDFTriple createTriple(Object subject, Object predicate,
			Object object) {

		RDFResource s = (RDFResource) subject;
        RDFResourceIRI p = (RDFResourceIRI) predicate;
		RDFResource o = (RDFResource) object;
		return new RDFTriple(s, p, o);

	}

	@Override
	public RDFTriple createLiteralTriple(Object subject, Object predicate,
			String litObject) {

		RDFResource s = (RDFResource) subject;
        RDFResourceIRI p = (RDFResourceIRI) predicate;
		RDFLiteral lit = new RDFLiteral(litObject, /* lang */ "", (IRI) null);
		return new RDFTriple(s, p, lit);

	}

	@Override
	public Object createGraph(Collection<TriplesMap> maps) {

		Set<RDFTriple> s = new HashSet<RDFTriple>();

		for (TriplesMap tm : maps) {
			Set<RDFTriple> stmts = tm.serialize(RDFTriple.class);

			for (RDFTriple tr : stmts)
				s.add(tr);
		}

		return s;

	}

	@Override
	public RDFResource getRDFType() {
		return createResource(OWLRDFVocabulary.RDF_TYPE.getIRI().toString());
	}

	@Override
	public Collection<Object> getSubjects(Object graph, Object pred, Object obj) {

		@SuppressWarnings("unchecked")
		Set<RDFTriple> s = (Set<RDFTriple>) graph;

		Collection<Object> c = new HashSet<Object>();

		for (RDFTriple t : s) {
			// Will not check literal triples.
			if ((pred == null || t.getPredicate().equals(pred))
					&& (obj == null || t.getObject().equals(obj))) {
				c.add(t.getSubject());
			}
		}

		return c;

	}

	@Override
	public Collection<Object> getObjects(Object graph, Object subj, Object pred) {

		@SuppressWarnings("unchecked")
		Set<RDFTriple> s = (Set<RDFTriple>) graph;

		Collection<Object> c = new HashSet<Object>();

		for (RDFTriple t : s) {

			if ((subj == null || t.getSubject().equals(subj))
					&& (pred == null || t.getPredicate().equals(pred))) {
				if(t.getObject() instanceof RDFLiteral){
					c.add(((RDFLiteral)t.getObject()).getLexicalValue());
				}else{
					c.add(t.getObject());
				}
			}

		}

		return c;

	}

	@Override
	public Class<RDFResource> getResourceClass() {
		return res;
	}

	@Override
	public Class<RDFTriple> getTripleClass() {
		return trpl;
	}

	@Override
	public Class<Set<RDFTriple>> getGraphClass() {
		return graph;
	}

    @Override
    public String toUnquotedString(Object iri) {
        /**
         * We need special treatment of IRI in OWLAPI because RDFResourceIRI.toString() generates quoted String
         * <http://...>
         */
        if(iri instanceof RDFResourceIRI){
            return ((RDFResourceIRI)iri).getIRI().toString();
        } else {
            return iri.toString();
        }
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((res == null) ? 0 : res.hashCode());
		result = prime * result + ((trpl == null) ? 0 : trpl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof OWLAPIConfiguration)) {
			return false;
		}
		OWLAPIConfiguration other = (OWLAPIConfiguration) obj;
		if (res == null) {
			if (other.res != null) {
				return false;
			}
		} else if (!res.equals(other.res)) {
			return false;
		}
		if (trpl == null) {
			if (other.trpl != null) {
				return false;
			}
		} else if (!trpl.equals(other.trpl)) {
			return false;
		}
		return true;
	}

}
