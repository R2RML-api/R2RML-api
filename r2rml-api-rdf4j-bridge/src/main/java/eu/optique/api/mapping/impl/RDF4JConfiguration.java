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
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;

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

	private Class<Resource> res = Resource.class;
	private Class<Statement> trpl = Statement.class;
	private Class<Model> graph = Model.class;

	@Override
	public org.apache.commons.rdf.api.IRI createResource(String iri) {
		return vf.createIRI(iri);
	}

	@Override
	public BlankNode createBNode() {
		return vf.createBlankNode();
	}

	@Override
	public Triple createTriple(BlankNodeOrIRI subject, org.apache.commons.rdf.api.IRI predicate, BlankNodeOrIRI object) {
		return vf.createTriple( subject, predicate, object);
	}

	@Override
	public Triple createLiteralTriple(BlankNodeOrIRI subject, org.apache.commons.rdf.api.IRI predicate,
                                      String litObject) {

		return vf.createTriple(subject, predicate,
				vf.createLiteral(litObject));

	}

	@Override
	public Graph createGraph(Collection<TriplesMap> maps) {
		Graph m = vf.createGraph();
//		m.setNamespace("rr", R2RMLVocabulary.NAMESPACE);

		for (TriplesMap tm : maps) {
            tm.serialize().forEach(m::add);
			//m.addAll(tm.serialize(Statement.class));
		}

		return m;

	}

	@Override
	public org.apache.commons.rdf.api.IRI getRDFType() {
	    return vf.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
    }

	@Override
    public Collection<BlankNodeOrIRI> getSubjects(Graph graph, org.apache.commons.rdf.api.IRI pred, RDFTerm obj){

	    return graph.stream(null, pred, obj).map(Triple::getSubject).collect(toSet());

//		Model m = ((Model) graph).filter(null, (IRI) pred, (Value) obj,
//				(Resource) null);
//
//		Collection<Object> c = new HashSet<>();
//		c.addAll(m.subjects());
//
//		return c;

	}

	@Override
	public Collection<RDFTerm> getObjects(Graph graph, BlankNodeOrIRI subject, org.apache.commons.rdf.api.IRI pred) {

        return graph.stream(subject, pred, null).map(Triple::getObject).collect(toSet());

//		Model m = ((Model) graph).filter((Resource) subj, (IRI) pred, null,
//				(Resource) null);
//
//		Collection<Object> c = new HashSet<>();
//
//		for(Value v : m.objects()){
//			if(v instanceof Literal){
//				Literal l = (Literal) v;
//				c.add(l.stringValue());
//			}else{
//				c.add(v);
//			}
//		}
//
//		return c;

	}

//	@Override
//	public Class<Resource> getResourceClass() {
//		return res;
//	}

	@Override
	public Class<Statement> getTripleClass() {
		return trpl;
	}

	@Override
	public Class<Model> getGraphClass() {
		return graph;
	}

    @Override
    public String getLexicalForm(RDFTerm node) {
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
		if (!(obj instanceof RDF4JConfiguration)) {
			return false;
		}
		RDF4JConfiguration other = (RDF4JConfiguration) obj;
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
