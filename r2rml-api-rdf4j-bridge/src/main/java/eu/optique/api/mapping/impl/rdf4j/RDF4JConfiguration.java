package eu.optique.api.mapping.impl.rdf4j;

import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.TriplesMap;
import eu.optique.api.mapping.impl.R2RMLVocabulary;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.RDF;

import java.util.Collection;
import java.util.HashSet;

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

	private ValueFactory vf = SimpleValueFactory.getInstance();

	private Class<Resource> res = Resource.class;
	private Class<Statement> trpl = Statement.class;
	private Class<Model> graph = Model.class;

	@Override
	public Resource createResource(String iri) {

		return vf.createIRI(iri);

	}

	@Override
	public Resource createBNode() {

		return vf.createBNode();

	}

	@Override
	public Statement createTriple(Object subject, Object predicate,
			Object object) {

		return vf.createStatement((Resource) subject, (IRI) predicate,
				(Resource) object);

	}

	@Override
	public Statement createLiteralTriple(Object subject, Object predicate,
			String litObject) {

		return vf.createStatement((Resource) subject, (IRI) predicate,
				vf.createLiteral(litObject));

	}

	@Override
	public Object createGraph(Collection<TriplesMap> maps) {

		Model m = new LinkedHashModel();
		m.setNamespace("rr", R2RMLVocabulary.NAMESPACE);

		for (TriplesMap tm : maps) {
			m.addAll(tm.serialize(Statement.class));
		}

		return m;

	}

	@Override
	public Resource getRDFType() {
		return RDF.TYPE;
	}

	@Override
	public Collection<Object> getSubjects(Object graph, Object pred, Object obj) {

		Model m = ((Model) graph).filter(null, (IRI) pred, (Value) obj,
				(Resource) null);

		Collection<Object> c = new HashSet<>();
		c.addAll(m.subjects());

		return c;

	}

	@Override
	public Collection<Object> getObjects(Object graph, Object subj, Object pred) {

		Model m = ((Model) graph).filter((Resource) subj, (IRI) pred, null,
				(Resource) null);

		Collection<Object> c = new HashSet<>();
		
		for(Value v : m.objects()){
			if(v instanceof Literal){
				Literal l = (Literal) v;
				c.add(l.stringValue());
			}else{
				c.add(v);
			}
		}

		return c;

	}

	@Override
	public Class<Resource> getResourceClass() {
		return res;
	}

	@Override
	public Class<Statement> getTripleClass() {
		return trpl;
	}

	@Override
	public Class<Model> getGraphClass() {
		return graph;
	}

    @Override
    public String getLexicalForm(Object node) {
        return node.toString();
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
