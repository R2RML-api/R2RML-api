/*******************************************************************************
 * Copyright 2013, the Optique Consortium
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This first version of the R2RML API was developed jointly at the University of Oslo, 
 * the University of Bolzano, La Sapienza University of Rome, and fluid Operations AG, 
 * as part of the Optique project, www.optique-project.eu
 ******************************************************************************/
package eu.optique.api.mapping.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import eu.optique.api.mapping.GraphMap;
import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.LogicalTable;
import eu.optique.api.mapping.MappingFactory;
import eu.optique.api.mapping.ObjectMap;
import eu.optique.api.mapping.PredicateMap;
import eu.optique.api.mapping.PredicateObjectMap;
import eu.optique.api.mapping.R2RMLMappingCollection;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.R2RMLView;
import eu.optique.api.mapping.RefObjectMap;
import eu.optique.api.mapping.SubjectMap;
import eu.optique.api.mapping.TermMap;
import eu.optique.api.mapping.TriplesMap;
import eu.optique.api.mapping.TermMap.TermMapType;

/**
 * The class representing a collection of TriplesMaps, which is generated from a
 * given graph.
 * 
 * @author Timea Bagosi
 * 
 */
public class R2RMLMappingCollectionImpl implements R2RMLMappingCollection {

	// the set of generated mappings
	private Collection<TriplesMap> triplesMaps;

	// the value factory to create URIs
	private LibConfiguration lcfg;
	private MappingFactory mfact;

	// the sesame rdf graph to read the mappings from
	private Object graph = null;

	/**
	 * @param mm
	 *            - The R2RMLMappingManager that is used to get a mapping
	 *            factory.
	 * @param lc
	 *            - The LibConfiguration of the R2RMLMappingManager.
	 * @param graph
	 *            - The graph to generate TriplesMaps from.
	 * @throws InvalidR2RMLMappingException
	 *             if found and invalid mapping
	 */
	public R2RMLMappingCollectionImpl(R2RMLMappingManager mm,
			LibConfiguration lc, Object graph)
			throws InvalidR2RMLMappingException {
		lcfg = lc;
		mfact = mm.getMappingFactory();
		initialize(graph);
	}

	@Override
	public void addTriplesMap(TriplesMap mapping) {
		triplesMaps.add(mapping);
	}

	@Override
	public void addTriplesMaps(Collection<TriplesMap> mappings) {
		triplesMaps.addAll(mappings);
	}

	@Override
	public void initialize(Object graph) throws InvalidR2RMLMappingException {
		if (graph == null)
			throw new NullPointerException(
					"The RDF Graph supported for the mapping manager must not be null.");
		this.graph = graph;
		triplesMaps = readTriplesMaps();
	}

	@Override
	public Collection<TriplesMap> getTriplesMaps() {
		return triplesMaps;
	}

	/**
	 * This method processes the graph, creates the objects and populates the
	 * set of TriplesMaps
	 * 
	 * @return the set of TriplesMaps generated from the graph
	 * @throws InvalidR2RMLMappingException
	 *             if found something invalid/missing
	 */
	private Set<TriplesMap> readTriplesMaps()
			throws InvalidR2RMLMappingException {
		Set<TriplesMap> triples = new HashSet<TriplesMap>();
		// find triplesmap nodes
		// it has to have a logical table declaration
		Collection<Object> triplesMapNodes = lcfg.getSubjects(graph,
				lcfg.createResource(R2RMLVocabulary.PROP_LOGICAL_TABLE), null);

		// for each triplesmap node populate the triplesmap
		// and add it to the set of triplesmaps
		for (Object node : triplesMapNodes)
			triples.add(readTriplesMap(node));

		return triples;
	}

	/**
	 * Read and return one TriplesMap given a Resource node in the graph.
	 * 
	 * @param node
	 *            - the Resource node of the TriplesMap in the graph
	 * @return the generated TriplesMap object
	 * @throws InvalidR2RMLMappingException
	 *             if found something invalid/missing
	 */
	private TriplesMap readTriplesMap(Object node)
			throws InvalidR2RMLMappingException {
		// create a TriplesMap populating each argument
		LogicalTable logicalTable = readLogicalTable(node);
		SubjectMap subjectMap = readSubjectMap(node);
		List<PredicateObjectMap> predObjMaps = readPredicateObjectMaps(node);

		TriplesMap triplesMap = mfact.createTriplesMap(logicalTable,
				subjectMap, predObjMaps);
		triplesMap.setResource(node);
		return triplesMap;
	}

	/**
	 * This method reads and creates a LogicalTable given a TriplesMap Resource
	 * node.
	 * 
	 * @param node
	 *            the TriplesMap Resource node
	 * @return the created LogicalTable object
	 * @throws InvalidR2RMLMappingException
	 *             if there's no logicalTable node
	 */
	private LogicalTable readLogicalTable(Object node)
			throws InvalidR2RMLMappingException {
		LogicalTable toReturn = null;
		// find logical table nodes
		Collection<Object> logicalTableNode = lcfg.getObjects(graph, node,
				lcfg.createResource(R2RMLVocabulary.PROP_LOGICAL_TABLE));
		// must be exactly one logicaltable node
		if (logicalTableNode.size() != 1) {
			throw new InvalidR2RMLMappingException(
					"Invalid mapping: TriplesMap node without LogicalTable node!");
		} else {

			Object logicalTable = logicalTableNode.toArray()[0];
			boolean isSQLTable = false;

			// look for tableName
			String tableName = readResource(logicalTable,
					lcfg.createResource(R2RMLVocabulary.PROP_TABLE_NAME));
			if (tableName != null) {
				isSQLTable = true;
				toReturn = mfact.createSQLBaseTableOrView(tableName);
			}

			// look for sql query
			String query = readResource(logicalTable,
					lcfg.createResource(R2RMLVocabulary.PROP_SQL_QUERY));
			if (query != null) {
				if (isSQLTable) {
					throw new InvalidR2RMLMappingException(
							"Invalid mapping: Logical table has both a tablename and a SQL query!");
				}

				toReturn = mfact.createR2RMLView(query);

				// look for sql version -> what to do with it?
				String version = readResource(logicalTable,
						lcfg.createResource(R2RMLVocabulary.PROP_SQL_VERSION));
				if (version != null) {
					((R2RMLView) toReturn).addSQLVersion(lcfg
							.createResource(version));
				}
			}

			if (toReturn == null) {
				throw new InvalidR2RMLMappingException(
						"Invalid mapping: Logical table has no tablename or SQL query!");
			}

			toReturn.setResource(logicalTable);
			return toReturn;
		}
	}

	/**
	 * This method processes the given Resource node as subject by finding the
	 * object node with the given resourceType URI as predicate. Finds and
	 * returns the first object.
	 * 
	 * @param node
	 *            The Resource node as subject
	 * @param resourceType
	 *            The URI as predicate
	 * @return the extracted first object as String
	 */
	private String readResource(Object node, Object resourceType) {
		// look for resourceType declaration
		Collection<Object> obj = lcfg.getObjects(graph, node, resourceType);
		if (obj.size() == 1)
			return obj.iterator().next().toString();
		return null;
	}

	/**
	 * This method processes the given Resource node as subject by finding the
	 * object node with the given resourceType URI as predicate. Finds and
	 * returns the first object.
	 * 
	 * @param node
	 *            The Resource node as subject
	 * @param resourceType
	 *            The URI as predicate
	 * @return the extracted first object as String
	 */
	private List<String> readResources(Object node, Object resourceType) {
		List<String> resources = new ArrayList<String>();
		// look for resourceType declaration
		Collection<Object> obj = lcfg.getObjects(graph, node, resourceType);
		if (obj.size() > 0) {
			for (Object val : obj) {
				resources.add(val.toString());
			}
			return resources;
		}
		return null;
	}

	/**
	 * This method processes the given Resource node as subject by finding the
	 * object node with the given resourceType URI as predicate. Finds and
	 * returns the first object.
	 * 
	 * @param node
	 *            The Resource node as subject
	 * @param resourceType
	 *            The URI as predicate
	 * @return the extracted first object as URI
	 */
	private Object readResourceURI(Object node, Object resourceType) {
		// look for resourceType declaration
		Collection<Object> obj = lcfg.getObjects(graph, node, resourceType);
		if (obj.size() == 1)
			return obj.toArray()[0];
		return null;
	}

	/**
	 * This method reads and returns all the object nodes of a given subject
	 * Resource node and a URI predicate.
	 * 
	 * @param node
	 *            The Resource node as subject
	 * @param resourceType
	 *            The URI as predicate
	 * @return The set of matched objects as URIs.
	 */
	private Set<Object> readResourceURIs(Object node, Object resourceType) {
		Set<Object> resources = new HashSet<Object>();
		// look for resourceType declarations
		Collection<Object> obj = lcfg.getObjects(graph, node, resourceType);
		for (Object value : obj)
			resources.add(value);
		return resources;
	}

	/**
	 * This method reads and returns the list of GraphMap-s of a given Resource
	 * node.
	 * 
	 * @param node
	 *            - the Resource node in which to look for graphMaps
	 * @return the created list of GraphMap objects
	 */
	private List<GraphMap> readGraphMap(Object node) {
		List<GraphMap> graphMapList = new ArrayList<GraphMap>();
		// look for graph declaration
		Collection<Object> graphDecl = lcfg.getObjects(graph, node,
				lcfg.createResource(R2RMLVocabulary.PROP_GRAPH));
		if (graphDecl.size() > 0) {
			for (Object val : graphDecl) {
				graphMapList.add(mfact.createGraphMap(
						TermMapType.CONSTANT_VALUED, val.toString()));
			}
		} else {
			// look for graphMap nodes
			Collection<Object> graphMaps = lcfg.getObjects(graph, node,
					lcfg.createResource(R2RMLVocabulary.PROP_GRAPH_MAP));
			for (Object value : graphMaps) {
				Object graphMapNode = value;
				// create graphMap object
				GraphMap graphMap = (GraphMap) readTermMap(graphMapNode,
						lcfg.createResource(R2RMLVocabulary.PROP_GRAPH_MAP));
				graphMap.setResource(graphMapNode);
				// add it to the list
				graphMapList.add(graphMap);
			}
		}
		return graphMapList;
	}

	/**
	 * Reads and returns the SubjectMap generated from a given Resource node.
	 * 
	 * @param node
	 *            - the Resource node in which to look for SubjectMap
	 * @return the created SubjectMap object
	 * @throws InvalidR2RMLMappingException
	 *             if there's no subjectMap node
	 */
	private SubjectMap readSubjectMap(Object node)
			throws InvalidR2RMLMappingException {
		// look for subject declaration -> constant
		String subject = readResource(node,
				lcfg.createResource(R2RMLVocabulary.PROP_SUBJECT));
		if (subject != null) {
			// return constant valued
			return mfact.createSubjectMap(TermMapType.CONSTANT_VALUED, subject);
		} else {
			// look for subjectMap declarations
			Collection<Object> subjectMapNode = lcfg.getObjects(graph, node,
					lcfg.createResource(R2RMLVocabulary.PROP_SUBJECT_MAP));
			if (subjectMapNode.size() != 1) {
				throw new InvalidR2RMLMappingException(
						"Invalid mapping: TriplesMap without subjectMap node");
			} else {
				Object subjectNode = subjectMapNode.toArray()[0];

				// create the subjectMap object
				SubjectMap subjectMap = (SubjectMap) readTermMap(subjectNode,
						lcfg.createResource(R2RMLVocabulary.PROP_SUBJECT_MAP));
				if (subjectMap == null)
					return null;

				// look for termtype -> iri or bnode
				Object termtype = readResourceURI(subjectNode,
						lcfg.createResource(R2RMLVocabulary.PROP_TERM_TYPE));
				// set termtype if not null
				if (termtype != null)
					subjectMap.setTermType(termtype);

				// look for context graphMap
				List<GraphMap> graphMaps = readGraphMap(subjectNode);
				for (GraphMap graphMap : graphMaps)
					subjectMap.addGraphMap(graphMap);

				// look for inverseExpression
				String invExpr = readResource(
						subjectNode,
						lcfg.createResource(R2RMLVocabulary.PROP_INVERSE_EXPRESSION));
				if (invExpr != null)
					subjectMap.setInverseExpression(mfact
							.createInverseExpression(invExpr));

				// look for class declarations
				Set<Object> classes = readResourceURIs(subjectNode,
						lcfg.createResource(R2RMLVocabulary.PROP_CLASS));

				// add all declared classes
				for (Object subjectClass : classes)
					subjectMap.addClass(subjectClass);

				subjectMap.setResource(subjectNode);
				return subjectMap;
			}
		}
	}

	/**
	 * Reads and returns the list of PredicateObjectMap-s of a given Resource
	 * node.
	 * 
	 * @param node
	 *            - the Resource node in which to look for PredicateObjectMaps
	 * @return the created list of PredicateObjectMap objects
	 * @throws InvalidR2RMLMappingException
	 *             if there's no predicate or objectMap
	 */
	private List<PredicateObjectMap> readPredicateObjectMaps(Object node)
			throws InvalidR2RMLMappingException {
		List<PredicateObjectMap> predObjs = new ArrayList<PredicateObjectMap>();

		// find predicateobjectmap nodes
		Collection<Object> predicateObjectNodes = lcfg.getObjects(graph, node,
				lcfg.createResource(R2RMLVocabulary.PROP_PREDICATE_OBJECT_MAP));
		// for each predicateobjectmap find predicatemap, objectmap
		// and add it to the list to be returned
		for (Object predobj : predicateObjectNodes) {
			Object predobjNode = predobj;

			List<PredicateMap> predicateMaps = readPredicateMaps(predobjNode);

			List<ObjectMap> objectMaps = readObjectMaps(predobjNode);

			List<RefObjectMap> refObjectMaps = readRefObjectMaps(predobjNode,
					node);

			if (predicateMaps.isEmpty()) {
				throw new InvalidR2RMLMappingException(
						"Invalid mapping: PredicateObjectMap has no PredicateMap!");
			} else if (objectMaps.isEmpty() && refObjectMaps.isEmpty()) {
				throw new InvalidR2RMLMappingException(
						"Invalid mapping: PredicateObjectMap has no ObjectMaps or RefObjectMaps!");
			}

			PredicateObjectMap predobjMap = mfact.createPredicateObjectMap(
					predicateMaps, objectMaps, refObjectMaps);

			// look for context graphMap
			List<GraphMap> graphMaps = readGraphMap(predobjNode);
			for (GraphMap graphMap : graphMaps)
				predobjMap.addGraphMap(graphMap);

			// add predobjmap to the list to be returned
			if (predobjMap != null) {
				predobjMap.setResource(predobjNode);
				predObjs.add(predobjMap);
			}

		}
		return predObjs;
	}

	/**
	 * Reads and returns the PredicateMaps generated from a given Resource node.
	 * 
	 * @param node
	 *            - the Resource node in which to look for PredicateMaps
	 * @return the created list of PredicateMap objects
	 */
	private List<PredicateMap> readPredicateMaps(Object node) {
		List<PredicateMap> predicateMaps = new ArrayList<PredicateMap>();
		// look for predicate declaration -> constant
		List<String> predicates = readResources(node,
				lcfg.createResource(R2RMLVocabulary.PROP_PREDICATE));
		if (predicates != null) {
			for (String predicate : predicates) {
				// return constant valued
				predicateMaps.add(mfact.createPredicateMap(
						TermMapType.CONSTANT_VALUED, predicate));
			}
		}

		// there might still be predicateMap declarations

		// look for predicateMap declaration
		Collection<Object> predicateMapNodes = lcfg.getObjects(graph, node,
				lcfg.createResource(R2RMLVocabulary.PROP_PREDICATE_MAP));
		for (Object predMapNode : predicateMapNodes) {

			Object predicateNode = predMapNode;

			// create the predicateMap object
			PredicateMap predicateMap = (PredicateMap) readTermMap(
					predicateNode,
					lcfg.createResource(R2RMLVocabulary.PROP_PREDICATE_MAP));

			// termtype can be only iri which is the default
			// so no reading of termtype needed
			predicateMap.setResource(predicateNode);

			predicateMaps.add(predicateMap);
		}

		return predicateMaps;
	}

	/**
	 * Reads and returns the ObjectMasp generated from a given Resource node.
	 * 
	 * @param node
	 *            - the Resource node in which to look for ObjectMap
	 * @return the created list of ObjectMap object or null if it's refObjectMap
	 */
	private List<ObjectMap> readObjectMaps(Object node) {
		List<ObjectMap> objectMaps = new ArrayList<ObjectMap>();
		// look for predicate declaration -> constant
		List<String> objects = readResources(node,
				lcfg.createResource(R2RMLVocabulary.PROP_OBJECT));
		if (objects != null) {
			for (String object : objects) {
				// return constant valued
				objectMaps.add(mfact.createObjectMap(
						TermMapType.CONSTANT_VALUED, object));
			}
		}

		// look for objectMap declaration
		Collection<Object> objectMapNodes = lcfg.getObjects(graph, node,
				lcfg.createResource(R2RMLVocabulary.PROP_OBJECT_MAP));
		for (Object objectMapNode : objectMapNodes) {

			Object objectNode = objectMapNode;

			// create the objectMap object
			ObjectMap objectMap = (ObjectMap) readTermMap(objectNode,
					lcfg.createResource(R2RMLVocabulary.PROP_OBJECT_MAP));
			if (objectMap != null) {

				// look for termtype -> iri or bnode or literal
				Object termtype = readResourceURI(objectNode,
						lcfg.createResource(R2RMLVocabulary.PROP_TERM_TYPE));
				// set termtype if not null
				if (termtype != null)
					objectMap.setTermType(termtype);

				// look for inverseExpression
				String invExpr = readResource(
						objectNode,
						lcfg.createResource(R2RMLVocabulary.PROP_INVERSE_EXPRESSION));
				if (invExpr != null)
					objectMap.setInverseExpression(mfact
							.createInverseExpression(invExpr));

				// look for datatype
				Object datatype = readResourceURI(objectNode,
						lcfg.createResource(R2RMLVocabulary.PROP_DATATYPE));
				if (datatype != null)
					objectMap.setDatatype(datatype);

				// look for language
				String lang = readResource(objectNode,
						lcfg.createResource(R2RMLVocabulary.PROP_LANGUAGE));
				if (lang != null)
					objectMap.setLanguageTag(lang);

				objectMap.setResource(objectNode);
				objectMaps.add(objectMap);
			}
		}

		return objectMaps;
	}

	/**
	 * Reads and returns a specific type of TermMap from a given Resource node.
	 * 
	 * @param node
	 *            - the Resource node in which to look
	 * @param type
	 *            - the URI that can be one of
	 *            SubjectMap/PredicateMap/ObjectMap/GraphMap
	 * @return the created TermMap object
	 */
	private TermMap readTermMap(Object node, Object type) {

		// look for template
		String resource = readResource(node,
				lcfg.createResource(R2RMLVocabulary.PROP_TEMPLATE));
		TermMapType termMapType = TermMapType.TEMPLATE_VALUED;

		// look for column
		if (resource == null) {
			resource = readResource(node,
					lcfg.createResource(R2RMLVocabulary.PROP_COLUMN));
			termMapType = TermMapType.COLUMN_VALUED;
		}

		// look for constant
		if (resource == null) {
			resource = readResource(node,
					lcfg.createResource(R2RMLVocabulary.PROP_CONSTANT));
			termMapType = TermMapType.CONSTANT_VALUED;
		}

		if (resource != null) {
			if (type.equals(lcfg
					.createResource(R2RMLVocabulary.PROP_SUBJECT_MAP))) {
				// return the corresponding subjectMap
				if (termMapType.equals(TermMapType.TEMPLATE_VALUED))
					return mfact.createSubjectMap(mfact
							.createTemplate(resource));
				else
					return mfact.createSubjectMap(termMapType, resource);

			} else if (type.equals(lcfg
					.createResource(R2RMLVocabulary.PROP_PREDICATE_MAP))) {
				// return the corresponding predicateMap
				if (termMapType.equals(TermMapType.TEMPLATE_VALUED))
					return mfact.createPredicateMap(mfact
							.createTemplate(resource));
				else
					return mfact.createPredicateMap(termMapType, resource);

			} else if (type.equals(lcfg
					.createResource(R2RMLVocabulary.PROP_OBJECT_MAP))) {
				// return the corresponding objectMap
				if (termMapType.equals(TermMapType.TEMPLATE_VALUED))
					return mfact
							.createObjectMap(mfact.createTemplate(resource));
				else
					return mfact.createObjectMap(termMapType, resource);
			} else if (type.equals(lcfg
					.createResource(R2RMLVocabulary.PROP_GRAPH_MAP))) {
				// return the corresponding graphMap
				if (termMapType.equals(TermMapType.TEMPLATE_VALUED))
					return mfact.createGraphMap(mfact.createTemplate(resource));
				else
					return mfact.createGraphMap(termMapType, resource);
			}
		}
		return null;

	}

	/**
	 * Reads and returns the RefObjectMaps of a given Resource node.
	 * 
	 * @param pomNode
	 *            - the Resource node (predicateObjectMap) in which to look for
	 *            RefObjectMap
	 * @param tmNode
	 *            - the Resource node (of the TriplesMap) needed for child
	 *            logical table
	 * @return the created list of RefObjectMap objects
	 * @throws InvalidR2RMLMappingException
	 *             if refObjectMap has no parentTriplesMap
	 */
	private List<RefObjectMap> readRefObjectMaps(Object pomNode, Object tmNode)
			throws InvalidR2RMLMappingException {
		List<RefObjectMap> refObjectMaps = new ArrayList<RefObjectMap>();

		// look for objectMap declaration
		Collection<Object> objectMapNodes = lcfg.getObjects(graph, pomNode,
				lcfg.createResource(R2RMLVocabulary.PROP_OBJECT_MAP));
		for (Object objectMapNode : objectMapNodes) {

			Object objectNode = objectMapNode;

			// look for parentTriplesMap keyword
			Iterator<Object> parentTriplesMap = lcfg
					.getObjects(
							graph,
							objectNode,
							lcfg.createResource(R2RMLVocabulary.PROP_PARENT_TRIPLES_MAP))
					.iterator();
			if (!parentTriplesMap.hasNext()) {
				continue;
			}

			// create refObjMap object
			Object parentNode = parentTriplesMap.next();

			RefObjectMap refObjectMap = mfact.createRefObjectMap(parentNode);
			refObjectMap.setResource(objectNode);
			refObjectMap.setChildLogicalTable(readLogicalTable(tmNode));
			refObjectMap.setParentLogicalTable(readLogicalTable(parentNode));

			// look for join condition
			Collection<Object> joinConditions = lcfg.getObjects(graph,
					objectNode,
					lcfg.createResource(R2RMLVocabulary.PROP_JOIN_CONDITION));
			for (Object value : joinConditions) {
				Object joinCondition = value;

				// look for child declaration
				String childColumn = readResource(joinCondition,
						lcfg.createResource(R2RMLVocabulary.PROP_CHILD));

				// look for parent declaration
				String parentColumn = readResource(joinCondition,
						lcfg.createResource(R2RMLVocabulary.PROP_PARENT));

				// add join condition to refobjMap instance
				if (childColumn != null && parentColumn != null)
					refObjectMap.addJoinCondition(mfact.createJoinCondition(
							childColumn, parentColumn));
			}
			refObjectMaps.add(refObjectMap);
		}

		return refObjectMaps;
	}

}
