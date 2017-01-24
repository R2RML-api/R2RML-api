package eu.optique.r2rml.api.model.impl;

import java.util.List;

import eu.optique.r2rml.api.model.GraphMap;
import eu.optique.r2rml.api.model.InverseExpression;
import eu.optique.r2rml.api.model.Join;
import eu.optique.r2rml.api.model.LogicalTable;
import eu.optique.r2rml.api.MappingFactory;
import eu.optique.r2rml.api.model.ObjectMap;
import eu.optique.r2rml.api.model.PredicateMap;
import eu.optique.r2rml.api.model.PredicateObjectMap;
import eu.optique.r2rml.api.model.R2RMLView;
import eu.optique.r2rml.api.model.RefObjectMap;
import eu.optique.r2rml.api.model.SQLBaseTableOrView;
import eu.optique.r2rml.api.model.SubjectMap;
import eu.optique.r2rml.api.model.Template;
import eu.optique.r2rml.api.model.TriplesMap;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.RDFTerm;

/**
 * Implementation of the Mapping Factory interface.
 * 
 * @author Marius Strandhaug
 * @author Martin G. Skjæveland
 */
public class MappingFactoryImpl implements MappingFactory {

	private RDF rdf;

	public MappingFactoryImpl(RDF c) {
		rdf = c;
	}

	@Override
	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm) {
		return new TriplesMapImpl(rdf, lt, sm);
	}

	@Override
	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm, String triplesMapIdentifier) {
		return new TriplesMapImpl(rdf, lt, sm, triplesMapIdentifier);
	}

	@Override
	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm, PredicateObjectMap pom) {

		TriplesMap tm = new TriplesMapImpl(rdf, lt, sm);
		tm.addPredicateObjectMap(pom);
		return tm;
	}

	@Override
	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm, PredicateObjectMap pom, String triplesMapIdentifier) {
		TriplesMap tm = new TriplesMapImpl(rdf, lt, sm, triplesMapIdentifier);
		tm.addPredicateObjectMap(pom);
		return tm;
	}

	@Override
	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm, List<PredicateObjectMap> listOfPom) {

		TriplesMap tm = new TriplesMapImpl(rdf, lt, sm);

		for (PredicateObjectMap pom : listOfPom) {
			tm.addPredicateObjectMap(pom);
		}

		return tm;
	}

	@Override
	public PredicateObjectMap createPredicateObjectMap(PredicateMap pm, ObjectMap om) {

		return new PredicateObjectMapImpl(rdf, pm, om);
	}

	@Override
	public PredicateObjectMap createPredicateObjectMap(PredicateMap pm, RefObjectMap rom) {
		return new PredicateObjectMapImpl(rdf, pm, rom);
	}

	@Override
	public PredicateObjectMap createPredicateObjectMap(List<PredicateMap> pms, List<ObjectMap> oms, List<RefObjectMap> roms) {

		return new PredicateObjectMapImpl(rdf, pms, oms, roms);
	}

	@Override
	public R2RMLView createR2RMLView(String query) {
		return new R2RMLViewImpl(rdf, query);
	}

	@Override
	public SQLBaseTableOrView createSQLBaseTableOrView(String tableName) {
		return new SQLBaseTableOrViewImpl(rdf, tableName);
	}

	@Override
	public GraphMap createGraphMap(Template template) {
		return new GraphMapImpl(rdf, template);
	}

	@Override
	public GraphMap createGraphMap(String columnName) {
		return new GraphMapImpl(rdf, columnName);
	}

    @Override
    public GraphMap createGraphMap(IRI constant) {
        return new GraphMapImpl(rdf, constant);
    }

    @Override
	public SubjectMap createSubjectMap(Template template) {
		return new SubjectMapImpl(rdf, template);
	}

	@Override
	public SubjectMap createSubjectMap(String columnName) {
		return new SubjectMapImpl(rdf, columnName);
	}

    @Override
    public SubjectMap createSubjectMap(RDFTerm constant) {
        return new SubjectMapImpl(rdf, constant);
    }

    @Override
	public PredicateMap createPredicateMap(Template template) {
		return new PredicateMapImpl(rdf, template);
	}

	@Override
	public PredicateMap createPredicateMap(String columnName) {
		return new PredicateMapImpl(rdf, columnName);
	}

    @Override
    public PredicateMap createPredicateMap(IRI constant) {
        return new PredicateMapImpl(rdf, constant);
    }

	@Override
	public ObjectMap createObjectMap(Template template) {
		return new ObjectMapImpl(rdf, template);
	}

	@Override
	public ObjectMap createObjectMap(String columnName) {
		return new ObjectMapImpl(rdf, columnName);
	}

    @Override
    public ObjectMap createObjectMap(RDFTerm constant) {
        return new ObjectMapImpl(rdf, constant);
    }
	
	public RefObjectMap createRefObjectMap(TriplesMap parentMap) {
		return new RefObjectMapImpl(rdf, parentMap);
	}

	@Override
	public Join createJoinCondition(String childColumn, String parentColumn) {
		return new JoinImpl(rdf, childColumn, parentColumn);
	}

	@Override
	public Template createTemplate() {
		return new TemplateImpl();
	}

	@Override
	public Template createTemplate(String template) {
		return new TemplateImpl(template);
	}

	@Override
	public InverseExpression createInverseExpression(String invExp) {
		return new InverseExpressionImpl(invExp);
	}
}
