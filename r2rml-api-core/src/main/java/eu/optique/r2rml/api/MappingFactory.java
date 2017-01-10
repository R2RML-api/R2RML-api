package eu.optique.r2rml.api;

import eu.optique.r2rml.api.model.GraphMap;
import eu.optique.r2rml.api.model.InverseExpression;
import eu.optique.r2rml.api.model.Join;
import eu.optique.r2rml.api.model.LogicalTable;
import eu.optique.r2rml.api.model.ObjectMap;
import eu.optique.r2rml.api.model.PredicateMap;
import eu.optique.r2rml.api.model.PredicateObjectMap;
import eu.optique.r2rml.api.model.R2RMLView;
import eu.optique.r2rml.api.model.RefObjectMap;
import eu.optique.r2rml.api.model.SQLTable;
import eu.optique.r2rml.api.model.SubjectMap;
import eu.optique.r2rml.api.model.Template;
import eu.optique.r2rml.api.model.TermMap;
import eu.optique.r2rml.api.model.TriplesMap;

import java.util.List;

/**
 * Mapping factory that creates the components of R2RML mappings.
 * 
 * @author Marius Strandhaug
 * @author Martin G. Skjæveland
 */
public interface MappingFactory {

	/**
	 * Create a new TriplesMap with the given LogicalTable and SubjectMap.
	 * 
	 * @param lt
	 *            The LogicalTable of the TriplesMap. This must either be a
	 *            SQLTable or a R2RMLView.
	 * @param sm
	 *            The SubjectMap of the TriplesMap.
	 * @return The created TriplesMap.
	 */
	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm);

	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm, String triplesMapIdentifier);

	/**
	 * Create a new TriplesMap with the given LogicalTable, SubjectMap and
	 * PredicateObjectMap.
	 * 
	 * @param lt
	 *            The LogicalTable of the TriplesMap. This must either be a
	 *            SQLTable or a R2RMLView.
	 * @param sm
	 *            The SubjectMap of the TriplesMap.
	 * @param pom
	 *            A PredicateObjectMap for the TriplesMap.
	 * @return The created TriplesMap.
	 */
	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm,
			PredicateObjectMap pom);

	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm,
									   PredicateObjectMap pom, String triplesMapIdentifier);
	/**
	 * Create a new TriplesMap with the given LogicalTable, SubjectMap and list
	 * of PredicateObjectMaps.
	 * 
	 * @param lt
	 *            The LogicalTable of the TriplesMap. This must either be a
	 *            SQLTable or a R2RMLView.
	 * @param sm
	 *            The SubjectMap of the TriplesMap.
	 * @param listOfPom
	 *            The list of PredicateObjectMaps that will be added to the
	 *            TriplesMap.
	 * @return The created TriplesMap.
	 */
	public TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm,
			List<PredicateObjectMap> listOfPom);

	/**
	 * Create a new PredicateObjectMap with the given PredicateMap and
	 * ObjectMap.
	 * 
	 * @param pm
	 *            The PredicateMap for the PredicateObjectMap.
	 * @param om
	 *            The ObjectMap for the PredicateObjectMap.
	 * @return The created PredicateObjectMap.
	 */
	public PredicateObjectMap createPredicateObjectMap(PredicateMap pm,
			ObjectMap om);

	/**
	 * Create a new PredicateObjectMap with the given PredicateMap and
	 * RefObjectMap.
	 * 
	 * @param pm
	 *            The PredicateMap for the PredicateObjectMap.
	 * @param rom
	 *            The RefObjectMap for the PredicateObjectMap.
	 * @return The created PredicateObjectMap.
	 */
	public PredicateObjectMap createPredicateObjectMap(PredicateMap pm,
			RefObjectMap rom);

	/**
	 * Creates a new PredicateObjectMap with the given lists of PredicateMaps,
	 * ObjectMaps and RefObjectMaps. The lists must contain at least one
	 * PredicateMap and at least one of either an ObjectMap or a RefObjectMap.
	 * 
	 * @param pms
	 *            The list of PredicateMaps.
	 * @param oms
	 *            The list of ObjectMaps.
	 * @param roms
	 *            The list of RefObjectMaps.
	 * @return The created PredicateObjectMap.
	 */
	public PredicateObjectMap createPredicateObjectMap(List<PredicateMap> pms,
			List<ObjectMap> oms, List<RefObjectMap> roms);

	/**
	 * Create a new R2RMLView with the given SQL query.
	 * 
	 * @param query
	 *            The SQL query for the R2RMLView.
	 * @return The created R2RMLView.
	 */
	public R2RMLView createR2RMLView(String query);

	/**
	 * Create a new SQLTable with the given table name.
	 * 
	 * @param tableName
	 *            The table name for the SQLTable.
	 * @return The created SQLTable.
	 */
	public SQLTable createSQLBaseTableOrView(String tableName);

	/**
	 * Create a new GraphMap with the given template. The term map type of the
	 * GraphMap will be set to TermMapType.TEMPLATE_VALUED.
	 * 
	 * @param template
	 *            The template for the GraphMap.
	 * @return The created GraphMap.
	 */
	public GraphMap createGraphMap(Template template);

	/**
	 * Create a new GraphMap with the given term map type and a column or
	 * constant value. The term map type of the GraphMap must either be
	 * TermMapType.CONSTANT_VALUED or TermMapType.COLUMN_VALUED.
	 * 
	 * @param type
	 *            The term map type for the GraphMap.
	 * @param columnOrConst
	 *            The value for the GraphMap.
	 * @return The created GraphMap.
	 */
	public GraphMap createGraphMap(TermMap.TermMapType type, String columnOrConst);

	/**
	 * Create a new SubjectMap with the given template. The term map type of the
	 * SubjectMap will be set to TermMapType.TEMPLATE_VALUED.
	 * 
	 * @param template
	 *            The template for the SubjectMap.
	 * @return The created SubjectMap.
	 */
	public SubjectMap createSubjectMap(Template template);

	/**
	 * Create a new SubjectMap with the given term map type and a column or
	 * constant value. The term map type of the SubjectMap must either be
	 * TermMapType.CONSTANT_VALUED or TermMapType.COLUMN_VALUED.
	 * 
	 * @param type
	 *            The term map type for the SubjectMap.
	 * @param columnOrConst
	 *            The value for the SubjectMap.
	 * @return The created SubjectMap.
	 */
	public SubjectMap createSubjectMap(TermMap.TermMapType type, String columnOrConst);

	/**
	 * Create a new PredicateMap with the given template. The term map type of
	 * the PredicateMap will be set to TermMapType.TEMPLATE_VALUED.
	 * 
	 * @param template
	 *            The template for the PredicateMap.
	 * @return The created PredicateMap.
	 */
	public PredicateMap createPredicateMap(Template template);

	/**
	 * Create a new PredicateMap with the given term map type and a column or
	 * constant value. The term map type of the PredicateMap must either be
	 * TermMapType.CONSTANT_VALUED or TermMapType.COLUMN_VALUED.
	 * 
	 * @param type
	 *            The term map type for the PredicateMap.
	 * @param columnOrConst
	 *            The value for the PredicateMap.
	 * @return The created PredicateMap.
	 */
	public PredicateMap createPredicateMap(TermMap.TermMapType type,
			String columnOrConst);

	/**
	 * Create a new ObjectMap with the given template. The term map type of the
	 * ObjectMap will be set to TermMapType.TEMPLATE_VALUED.
	 * 
	 * @param template
	 *            The template for the ObjectMap.
	 * @return The created ObjectMap.
	 */
	public ObjectMap createObjectMap(Template template);

	/**
	 * Create a new ObjectMap with the given term map type and a column or
	 * constant value. The term map type of the ObjectMap must either be
	 * TermMapType.CONSTANT_VALUED or TermMapType.COLUMN_VALUED.
	 * 
	 * @param type
	 *            The term map type for the ObjectMap.
	 * @param columnOrConst
	 *            The value for the ObjectMap.
	 * @return The created ObjectMap.
	 */
	public ObjectMap createObjectMap(TermMap.TermMapType type, String columnOrConst);

	/**
	 * Create a new RefObjectMap with the given resource for the parent triples
	 * map.
	 * 
	 * @param parentMap The parent triples map for the RefObjectMap.
	 * @return The created RefObjectMap.
	 */
	public RefObjectMap createRefObjectMap(TriplesMap parentMap);

	/**
	 * Create a new Join with the given child and parent columns.
	 * 
	 * @param childColumn
	 *            The child column of the Join.
	 * @param parentColumn
	 *            The parent column of the Join.
	 * @return The created Join.
	 */
	public Join createJoinCondition(String childColumn, String parentColumn);

	/**
	 * Create a new template.
	 * 
	 * @return The created template.
	 */
	public Template createTemplate();

	/**
	 * Create a new template given a template string.
	 * 
	 * @param template
	 *            The template as a string.
	 * @return The created template.
	 */
	public Template createTemplate(String template);

	/**
	 * Create a new InverseExpression with the given string template.
	 * 
	 * @param invExp
	 *            The string template for the inverse expression.
	 * @return The created InverseExpression.
	 */
	public InverseExpression createInverseExpression(String invExp);
}
