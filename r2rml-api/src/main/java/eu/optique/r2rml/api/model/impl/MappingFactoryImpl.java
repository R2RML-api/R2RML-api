package eu.optique.r2rml.api.model.impl;

import java.util.List;

import eu.optique.r2rml.api.model.GraphMap;
import eu.optique.r2rml.api.model.InverseExpression;
import eu.optique.r2rml.api.model.Join;
import eu.optique.r2rml.api.model.LogicalTable;
import eu.optique.r2rml.api.model.MappingFactory;
import eu.optique.r2rml.api.model.ObjectMap;
import eu.optique.r2rml.api.model.PredicateMap;
import eu.optique.r2rml.api.model.PredicateObjectMap;
import eu.optique.r2rml.api.model.R2RMLView;
import eu.optique.r2rml.api.model.RefObjectMap;
import eu.optique.r2rml.api.model.SQLTable;
import eu.optique.r2rml.api.model.SubjectMap;
import eu.optique.r2rml.api.model.Template;
import eu.optique.r2rml.api.model.TriplesMap;
import eu.optique.r2rml.api.model.TermMap;
import org.apache.commons.rdf.api.RDF;

/**
 * Implementation of the Mapping Factory interface.
 * 
 * @author Marius Strandhaug
 * @author Martin G. Skjæveland
 */
public class MappingFactoryImpl implements MappingFactory {

	private RDF lc;

	public MappingFactoryImpl(RDF c) {
		lc = c;
	}

	@Override
	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm) {
		return new TriplesMapImpl(lc, lt, sm);
	}

	@Override
	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm, String triplesMapIdentifier) {
		return new TriplesMapImpl(lc, lt, sm, triplesMapIdentifier);
	}

	@Override
	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm,
			PredicateObjectMap pom) {

		TriplesMap tm = new TriplesMapImpl(lc, lt, sm);
		tm.addPredicateObjectMap(pom);
		return tm;
	}

	@Override
	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm, PredicateObjectMap pom, String triplesMapIdentifier) {
		TriplesMap tm = new TriplesMapImpl(lc, lt, sm, triplesMapIdentifier);
		tm.addPredicateObjectMap(pom);
		return tm;
	}

	@Override
	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm,
			List<PredicateObjectMap> listOfPom) {

		TriplesMap tm = new TriplesMapImpl(lc, lt, sm);

		for (PredicateObjectMap pom : listOfPom) {
			tm.addPredicateObjectMap(pom);
		}

		return tm;
	}

	@Override
	public PredicateObjectMap createPredicateObjectMap(PredicateMap pm,
			ObjectMap om) {

		return new PredicateObjectMapImpl(lc, pm, om);
	}

	@Override
	public PredicateObjectMap createPredicateObjectMap(PredicateMap pm,
			RefObjectMap rom) {
		return new PredicateObjectMapImpl(lc, pm, rom);
	}

	@Override
	public PredicateObjectMap createPredicateObjectMap(List<PredicateMap> pms,
			List<ObjectMap> oms, List<RefObjectMap> roms) {

		return new PredicateObjectMapImpl(lc, pms, oms, roms);
	}

	@Override
	public R2RMLView createR2RMLView(String query) {
		return new R2RMLViewImpl(lc, query);
	}

	@Override
	public SQLTable createSQLBaseTableOrView(String tableName) {
		return new SQLTableImpl(lc, tableName);
	}

	@Override
	public GraphMap createGraphMap(Template template) {
		return new GraphMapImpl(lc, TermMap.TermMapType.TEMPLATE_VALUED, template);
	}

	@Override
	public GraphMap createGraphMap(TermMap.TermMapType type, String columnOrConst) {
		return new GraphMapImpl(lc, type, columnOrConst);
	}

	@Override
	public SubjectMap createSubjectMap(Template template) {
		return new SubjectMapImpl(lc, TermMap.TermMapType.TEMPLATE_VALUED, template);
	}

	@Override
	public SubjectMap createSubjectMap(TermMap.TermMapType type, String columnOrConst) {
		return new SubjectMapImpl(lc, type, columnOrConst);
	}

	@Override
	public PredicateMap createPredicateMap(Template template) {
		return new PredicateMapImpl(lc, TermMap.TermMapType.TEMPLATE_VALUED, template);
	}

	@Override
	public PredicateMap createPredicateMap(TermMap.TermMapType type,
			String columnOrConst) {
		return new PredicateMapImpl(lc, type, columnOrConst);
	}

	@Override
	public ObjectMap createObjectMap(Template template) {
		return new ObjectMapImpl(lc, TermMap.TermMapType.TEMPLATE_VALUED, template);
	}

	@Override
	public ObjectMap createObjectMap(TermMap.TermMapType type, String columnOrConst) {
		return new ObjectMapImpl(lc, type, columnOrConst);
	}
	
	public RefObjectMap createRefObjectMap(TriplesMap parentMap) {
		return new RefObjectMapImpl(lc, parentMap);
	}

	@Override
	public Join createJoinCondition(String childColumn, String parentColumn) {
		return new JoinImpl(lc, childColumn, parentColumn);
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
