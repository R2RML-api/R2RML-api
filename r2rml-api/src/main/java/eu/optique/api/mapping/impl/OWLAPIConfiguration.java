package eu.optique.api.mapping.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.coode.owlapi.rdf.model.RDFLiteralNode;
import org.coode.owlapi.rdf.model.RDFResourceNode;
import org.coode.owlapi.rdf.model.RDFTriple;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.TriplesMap;

/**
 * The library configuration for the OWL API.
 * 
 * Uses org.coode.owlapi.rdf.model.RDFResourceNode as the resource class,
 * org.coode.owlapi.rdf.model.RDFTriple as the triple class and
 * java.util.Set<org.coode.owlapi.rdf.model.RDFTriple> as the graph class.
 * 
 * @author Marius Strandhaug
 */
public class OWLAPIConfiguration implements LibConfiguration {

	OWLOntologyManager manager;
	OWLDataFactory factory;

	private Class<RDFResourceNode> res = RDFResourceNode.class;
	private Class<RDFTriple> trpl = RDFTriple.class;
	@SuppressWarnings("unchecked")
	private Class<Set<RDFTriple>> graph = (Class<Set<RDFTriple>>) (Class<?>) Set.class;

	public OWLAPIConfiguration() {
		manager = OWLManager.createOWLOntologyManager();
		factory = manager.getOWLDataFactory();
	}

	@Override
	public RDFResourceNode createResource(String URI) {
		return new RDFResourceNode(IRI.create(URI));
	}

	@Override
	public RDFResourceNode createBNode() {

		String s = factory.getOWLAnonymousIndividual().getID().getID();

		/*
		 * Strips all characters from the beginning of the string, so that the
		 * actual ID number is left. Requires that the string ends with a
		 * number.
		 */
		int id = Integer.parseInt(s.replaceFirst("(\\D*)((^\\d)*)", ""));
		return new RDFResourceNode(id);

	}

	@Override
	public RDFTriple createTriple(Object subject, Object predicate,
			Object object) {

		RDFResourceNode s = (RDFResourceNode) subject;
		RDFResourceNode p = (RDFResourceNode) predicate;
		RDFResourceNode o = (RDFResourceNode) object;
		return new RDFTriple(s, p, o);

	}

	@Override
	public RDFTriple createLiteralTriple(Object subject, Object predicate,
			String litObject) {

		RDFResourceNode s = (RDFResourceNode) subject;
		RDFResourceNode p = (RDFResourceNode) predicate;
		RDFLiteralNode lit = new RDFLiteralNode(litObject, (IRI) null);
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
	public RDFResourceNode getRDFType() {
		return createResource(OWLRDFVocabulary.RDF_TYPE.getIRI().toString());
	}

	@Override
	public Collection<Object> getSubjects(Object graph, Object pred, Object obj) {

		@SuppressWarnings("unchecked")
		Set<RDFTriple> s = (Set<RDFTriple>) graph;

		Collection<Object> c = new HashSet<Object>();

		for (RDFTriple t : s) {
			// Will not check literal triples.
			if ((pred == null || t.getProperty().equals(pred))
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
					&& (pred == null || t.getProperty().equals(pred))) {
				if(t.getObject() instanceof RDFLiteralNode){
					c.add(((RDFLiteralNode)t.getObject()).getLiteral());
				}else{
					c.add(t.getObject());
				}
			}

		}

		return c;

	}

	@Override
	public Class<RDFResourceNode> getResourceClass() {
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
