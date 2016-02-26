package eu.optique.api.mapping.impl.sesame;

import java.util.Collection;
import java.util.HashSet;

import eu.optique.api.mapping.impl.R2RMLVocabulary;
import org.openrdf.model.Literal;
import org.openrdf.model.Model;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.RDF;

import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.TriplesMap;

/**
 * The library configuration for the OpenRDF Sesame API.
 * 
 * Uses org.openrdf.model.Resource as the resource class,
 * org.openrdf.model.Statement as the triple class and org.openrdf.model.Model
 * as the graph class.
 * 
 * @author Marius Strandhaug
 * @author xiao
 */
public class SesameConfiguration implements LibConfiguration {

	ValueFactory vf = ValueFactoryImpl.getInstance();

	private Class<Resource> res = Resource.class;
	private Class<Statement> trpl = Statement.class;
	private Class<Model> graph = Model.class;

	@Override
	public Resource createResource(String URI) {

		return vf.createURI(URI);

	}

	@Override
	public Resource createBNode() {

		return vf.createBNode();

	}

	@Override
	public Statement createTriple(Object subject, Object predicate,
			Object object) {

		return vf.createStatement((Resource) subject, (URI) predicate,
				(Resource) object);

	}

	@Override
	public Statement createLiteralTriple(Object subject, Object predicate,
			String litObject) {

		return vf.createStatement((Resource) subject, (URI) predicate,
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

		Model m = ((Model) graph).filter(null, (URI) pred, (Value) obj,
				(Resource) null);

		Collection<Object> c = new HashSet<Object>();
		c.addAll(m.subjects());

		return c;

	}

	@Override
	public Collection<Object> getObjects(Object graph, Object subj, Object pred) {

		Model m = ((Model) graph).filter((Resource) subj, (URI) pred, null,
				(Resource) null);

		Collection<Object> c = new HashSet<Object>();
		
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
		if (!(obj instanceof SesameConfiguration)) {
			return false;
		}
		SesameConfiguration other = (SesameConfiguration) obj;
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
