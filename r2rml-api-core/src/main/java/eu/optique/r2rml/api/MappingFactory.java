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
import eu.optique.r2rml.api.model.SQLBaseTableOrView;
import eu.optique.r2rml.api.model.SubjectMap;
import eu.optique.r2rml.api.model.Template;
import eu.optique.r2rml.api.model.TriplesMap;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDFTerm;

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
     * @param lt The LogicalTable of the TriplesMap. This must either be a
     *           SQLBaseTableOrView or a R2RMLView.
     * @param sm The SubjectMap of the TriplesMap.
     * @return The created TriplesMap.
     */
    TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm);

    TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm, String triplesMapIdentifier);

    /**
     * Create a new TriplesMap with the given LogicalTable, SubjectMap and
     * PredicateObjectMap.
     *
     * @param lt  The LogicalTable of the TriplesMap. This must either be a
     *            SQLBaseTableOrView or a R2RMLView.
     * @param sm  The SubjectMap of the TriplesMap.
     * @param pom A PredicateObjectMap for the TriplesMap.
     * @return The created TriplesMap.
     */
    TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm, PredicateObjectMap pom);

    TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm, PredicateObjectMap pom, String triplesMapIdentifier);

    /**
     * Create a new TriplesMap with the given LogicalTable, SubjectMap and list
     * of PredicateObjectMaps.
     *
     * @param lt        The LogicalTable of the TriplesMap. This must either be a
     *                  SQLBaseTableOrView or a R2RMLView.
     * @param sm        The SubjectMap of the TriplesMap.
     * @param listOfPom The list of PredicateObjectMaps that will be added to the
     *                  TriplesMap.
     * @return The created TriplesMap.
     */
    TriplesMap createTriplesMap(LogicalTable lt, SubjectMap sm, List<PredicateObjectMap> listOfPom);

    /**
     * Create a new PredicateObjectMap with the given PredicateMap and
     * ObjectMap.
     *
     * @param pm The PredicateMap for the PredicateObjectMap.
     * @param om The ObjectMap for the PredicateObjectMap.
     * @return The created PredicateObjectMap.
     */
    PredicateObjectMap createPredicateObjectMap(PredicateMap pm, ObjectMap om);

    /**
     * Create a new PredicateObjectMap with the given PredicateMap and
     * RefObjectMap.
     *
     * @param pm  The PredicateMap for the PredicateObjectMap.
     * @param rom The RefObjectMap for the PredicateObjectMap.
     * @return The created PredicateObjectMap.
     */
    PredicateObjectMap createPredicateObjectMap(PredicateMap pm, RefObjectMap rom);

    /**
     * Creates a new PredicateObjectMap with the given lists of PredicateMaps,
     * ObjectMaps and RefObjectMaps. The lists must contain at least one
     * PredicateMap and at least one of either an ObjectMap or a RefObjectMap.
     *
     * @param pms  The list of PredicateMaps.
     * @param oms  The list of ObjectMaps.
     * @param roms The list of RefObjectMaps.
     * @return The created PredicateObjectMap.
     */
    PredicateObjectMap createPredicateObjectMap(List<PredicateMap> pms,
                                                List<ObjectMap> oms, List<RefObjectMap> roms);

    /**
     * Create a new R2RMLView with the given SQL query.
     *
     * @param query The SQL query for the R2RMLView.
     * @return The created R2RMLView.
     */
    R2RMLView createR2RMLView(String query);

    /**
     * Create a new SQLBaseTableOrView with the given table name.
     *
     * @param tableName The table name for the SQLBaseTableOrView.
     * @return The created SQLBaseTableOrView.
     */
    SQLBaseTableOrView createSQLBaseTableOrView(String tableName);

    /**
     * Create a new GraphMap with the given template. The term map type of the
     * GraphMap will be set to TermMapType.TEMPLATE_VALUED.
     *
     * @param template The template for the GraphMap.
     * @return The created GraphMap.
     */
    GraphMap createGraphMap(Template template);

    /**
     * Create a new GraphMap with the given term map type and a column or
     * constant value. The term map type of the GraphMap will be set to
     * TermMapType.COLUMN_VALUED.
     *
     * @param columnName The value for the GraphMap.
     * @return The created GraphMap.
     */
    GraphMap createGraphMap(String columnName);

    /**
     * Create a new GraphMap with the given term map type and a column or
     * constant value. The term map type of the GraphMap will be set to
     * TermMapType.CONSTANT_VALUED .
     *
     * @param constant The value for the GraphMap.
     * @return The created GraphMap.
     */
    GraphMap createGraphMap(IRI constant);

    /**
     * Create a new SubjectMap with the given template. The term map type of the
     * SubjectMap will be set to TermMapType.TEMPLATE_VALUED.
     *
     * @param template The template for the SubjectMap.
     * @return The created SubjectMap.
     */
    SubjectMap createSubjectMap(Template template);

    /**
     * Create a new SubjectMap with the given term map type and a column or
     * constant value. The term map type of the SubjectMap will be set to TermMapType.COLUMN_VALUED.
     *
     * @param columnName The value for the SubjectMap.
     * @return The created SubjectMap.
     */
    SubjectMap createSubjectMap(String columnName);

    /**
     * Create a new SubjectMap with the given term map type and a column or
     * constant value. The term map type of the SubjectMap will be set to
     * TermMapType.CONSTANT_VALUED.
     *
     * @param constant The value for the SubjectMap.
     * @return The created SubjectMap.
     */
    SubjectMap createSubjectMap(IRI constant);

    /**
     * Create a new PredicateMap with the given template. The term map type of
     * the PredicateMap will be set to TermMapType.TEMPLATE_VALUED.
     *
     * @param template The template for the PredicateMap.
     * @return The created PredicateMap.
     */
    PredicateMap createPredicateMap(Template template);

    /**
     * Create a new PredicateMap with the given term map type and a column or
     * constant value. The term map type of the PredicateMap will be set to TermMapType.COLUMN_VALUED.
     *
     * @param columnName The value for the PredicateMap.
     * @return The created PredicateMap.
     */
    PredicateMap createPredicateMap(String columnName);

    /**
     * Create a new PredicateMap with the given term map type and a column or
     * constant value. The term map type of the PredicateMap will be set to
     * TermMapType.CONSTANT_VALUED.
     *
     * @param constant The value for the PredicateMap.
     * @return The created PredicateMap.
     */
    PredicateMap createPredicateMap(IRI constant);

    /**
     * Create a new ObjectMap with the given template. The term map type of the
     * ObjectMap will be set to TermMapType.TEMPLATE_VALUED.
     *
     * @param template The template for the ObjectMap.
     * @return The created ObjectMap.
     */
    ObjectMap createObjectMap(Template template);

    /**
     * Create a new ObjectMap with the given term map type and a column or
     * constant value. The term map type of the ObjectMap will be set to TermMapType.COLUMN_VALUED.
     * TermMapType.CONSTANT_VALUED or TermMapType.COLUMN_VALUED.
     *
     * @param columnName The value for the ObjectMap.
     * @return The created ObjectMap.
     */
    ObjectMap createObjectMap(String columnName);

    /**
     * Create a new ObjectMap with the given term map type and a column or
     * constant value. The term map type of the ObjectMap will be set to TermMapType.CONSTANT_VALUED.
     *
     * @param constant The value for the ObjectMap.
     * @return The created ObjectMap.
     */
    ObjectMap createObjectMap(RDFTerm constant);

    /**
     * Create a new RefObjectMap with the given resource for the parent triples
     * map.
     *
     * @param parentMap The parent triples map for the RefObjectMap.
     * @return The created RefObjectMap.
     */
    RefObjectMap createRefObjectMap(TriplesMap parentMap);

    /**
     * Create a new Join with the given child and parent columns.
     *
     * @param childColumn  The child column of the Join.
     * @param parentColumn The parent column of the Join.
     * @return The created Join.
     */
    Join createJoinCondition(String childColumn, String parentColumn);

    /**
     * Create a new template.
     *
     * @return The created template.
     */
    Template createTemplate();

    /**
     * Create a new template given a template string.
     *
     * @param template The template as a string.
     * @return The created template.
     */
    Template createTemplate(String template);

    /**
     * Create a new InverseExpression with the given string template.
     *
     * @param invExp The string template for the inverse expression.
     * @return The created InverseExpression.
     */
    InverseExpression createInverseExpression(String invExp);
}
