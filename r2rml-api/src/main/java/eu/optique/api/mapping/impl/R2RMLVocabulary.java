/*******************************************************************************
 * Copyright 2013, the Optique Consortium
 *
 * Licensed under the Apache License, Version 2.0 (the "License";
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

/**
 * Vocabulary/String definitions for R2RML
 * 
 * @author michael.schmidt
 * @author timea.bagosi
 */
public class R2RMLVocabulary {

	// the R2RML namespace
	public static final String NAMESPACE = "http://www.w3.org/ns/r2rml#";

	// R2RML types
	public static final String TYPE_BASE_TABLE_OR_VIEW = NAMESPACE + "BaseTableOrView";
	public static final String TYPE_GRAPH_MAP = NAMESPACE + "GraphMap";
	public static final String TYPE_JOIN = NAMESPACE + "Join";
	public static final String TYPE_LOGICAL_TABLE = NAMESPACE + "LogicalTable";
	public static final String TYPE_OBJECT_MAP = NAMESPACE + "ObjectMap";
	public static final String TYPE_PREDICATE_MAP = NAMESPACE + "PredicateMap";
	public static final String TYPE_PREDICATE_OBJECT_MAP = NAMESPACE + "PredicateObjectMap";
	public static final String TYPE_R2RML_VIEW = NAMESPACE + "R2RMLView";
	public static final String TYPE_REF_OBJECT_MAP = NAMESPACE + "RefObjectMap";
	public static final String TYPE_SUBJECT_MAP = NAMESPACE + "SubjectMap";
	public static final String TYPE_TERM_MAP = NAMESPACE + "TermMap";
	public static final String TYPE_TRIPLES_MAP = NAMESPACE + "TriplesMap";

	// R2RML properties
	public static final String PROP_CHILD = NAMESPACE + "child";
	public static final String PROP_CLASS = NAMESPACE + "class";
	public static final String PROP_COLUMN = NAMESPACE + "column";
	public static final String PROP_DATATYPE = NAMESPACE + "datatype";
	public static final String PROP_CONSTANT = NAMESPACE + "constant";
	public static final String PROP_GRAPH = NAMESPACE + "graph";
	public static final String PROP_GRAPH_MAP = NAMESPACE + "graphMap";
	public static final String PROP_INVERSE_EXPRESSION = NAMESPACE + "inverseExpression";
	public static final String PROP_JOIN_CONDITION = NAMESPACE + "joinCondition";
	public static final String PROP_LANGUAGE = NAMESPACE + "language";
	public static final String PROP_LOGICAL_TABLE = NAMESPACE + "logicalTable";
	public static final String PROP_OBJECT = NAMESPACE + "object";
	public static final String PROP_OBJECT_MAP = NAMESPACE + "objectMap";
	public static final String PROP_PARENT = NAMESPACE + "parent";
	public static final String PROP_PARENT_TRIPLES_MAP = NAMESPACE + "parentTriplesMap";
	public static final String PROP_PREDICATE = NAMESPACE + "predicate";
	public static final String PROP_PREDICATE_MAP = NAMESPACE + "predicateMap";
	public static final String PROP_PREDICATE_OBJECT_MAP = NAMESPACE + "predicateObjectMap";
	public static final String PROP_SQL_QUERY = NAMESPACE + "sqlQuery";
	public static final String PROP_SQL_VERSION = NAMESPACE + "sqlVersion";
	public static final String PROP_SUBJECT = NAMESPACE + "subject";
	public static final String PROP_SUBJECT_MAP = NAMESPACE + "subjectMap";
	public static final String PROP_TABLE_NAME = NAMESPACE + "tableName";
	public static final String PROP_TEMPLATE = NAMESPACE + "template";
	public static final String PROP_TERM_TYPE = NAMESPACE + "termType";

	// R2RML other terms
	public static final String TERM_DEFAULT_GRAPH = NAMESPACE + "defaultGraph";
	public static final String TERM_SQL_2008 = NAMESPACE + "SQL2008";
	public static final String TERM_IRI = NAMESPACE + "IRI";
	public static final String TERM_BLANK_NODE = NAMESPACE + "BlankNode";
	public static final String TERM_LITERAL = NAMESPACE + "Literal";

}
