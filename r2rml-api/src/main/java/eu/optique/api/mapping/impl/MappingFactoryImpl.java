package eu.optique.api.mapping.impl;

import java.util.List;

import eu.optique.api.mapping.GraphMap;
import eu.optique.api.mapping.InverseExpression;
import eu.optique.api.mapping.Join;
import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.LogicalTable;
import eu.optique.api.mapping.MappingFactory;
import eu.optique.api.mapping.ObjectMap;
import eu.optique.api.mapping.PredicateMap;
import eu.optique.api.mapping.PredicateObjectMap;
import eu.optique.api.mapping.R2RMLView;
import eu.optique.api.mapping.RefObjectMap;
import eu.optique.api.mapping.SQLTable;
import eu.optique.api.mapping.SubjectMap;
import eu.optique.api.mapping.Template;
import eu.optique.api.mapping.TriplesMap;
import eu.optique.api.mapping.TermMap.TermMapType;

/**
 * Implementation of the Mapping Factory interface.
 * 
 * @author Marius Strandhaug
 * @author Martin G. Skjæveland
 */
public class MappingFactoryImpl implements MappingFactory {

	private LibConfiguration lc;

	public MappingFactoryImpl(LibConfiguration c) {
		lc = c;
	}

	@Override
	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm) {
		return new TriplesMapImpl(lc, lt, sm);
	}

	@Override
	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm,
			PredicateObjectMap pom) {

		TriplesMap tm = new TriplesMapImpl(lc, lt, sm);
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
		return new GraphMapImpl(lc, TermMapType.TEMPLATE_VALUED, template);
	}

	@Override
	public GraphMap createGraphMap(TermMapType type, String columnOrConst) {
		return new GraphMapImpl(lc, type, columnOrConst);
	}

	@Override
	public SubjectMap createSubjectMap(Template template) {
		return new SubjectMapImpl(lc, TermMapType.TEMPLATE_VALUED, template);
	}

	@Override
	public SubjectMap createSubjectMap(TermMapType type, String columnOrConst) {
		return new SubjectMapImpl(lc, type, columnOrConst);
	}

	@Override
	public PredicateMap createPredicateMap(Template template) {
		return new PredicateMapImpl(lc, TermMapType.TEMPLATE_VALUED, template);
	}

	@Override
	public PredicateMap createPredicateMap(TermMapType type,
			String columnOrConst) {
		return new PredicateMapImpl(lc, type, columnOrConst);
	}

	@Override
	public ObjectMap createObjectMap(Template template) {
		return new ObjectMapImpl(lc, TermMapType.TEMPLATE_VALUED, template);
	}

	@Override
	public ObjectMap createObjectMap(TermMapType type, String columnOrConst) {
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
