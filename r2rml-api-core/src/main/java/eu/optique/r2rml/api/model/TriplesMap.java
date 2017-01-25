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
package eu.optique.r2rml.api.model;

import java.util.Collection;
import java.util.List;

/**
 * R2RML Triples Map
 * 
 * @author Marius Strandhaug
 * @author Martin G. Skjæveland
 */
@W3C_R2RML_Recommendation(R2RMLVocabulary.TYPE_TRIPLES_MAP)
public interface TriplesMap extends MappingComponent {

	/**
	 * Set the LogicalTable of this TriplesMap. A TriplesMap must have exactly
	 * one LogicalTable.
	 * 
	 * @param lt
	 *            The LogicalTable that will be added.
	 */
	@W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_LOGICAL_TABLE)
	public void setLogicalTable(LogicalTable lt);

	/**
	 * Set the SubjectMap of this TriplesMap. A TriplesMap must have exactly one
	 * SubjectMap.
	 * 
	 * @param sm
	 *            The SubjectMap that will be added.
	 */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_SUBJECT_MAP)
	public void setSubjectMap(SubjectMap sm);

	/**
	 * Adds a PredicateObjectMap to this TriplesMap. The PredicateObjectMap will
	 * be added to the end of the PredicateObjectMap list. A TriplesMap can have
	 * any number of PredicateObjectMaps.
	 * 
	 * @param pom
	 *            The PredicateObjectMap that will be added.
	 */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_PREDICATE_OBJECT_MAP)
	public void addPredicateObjectMap(PredicateObjectMap pom);
	
	/**
	 * Adds a collection of PredicateObjectMaps to this TriplesMap. The PredicateObjectMap will
	 * be added to the end of the PredicateObjectMap list. A TriplesMap can have
	 * any number of PredicateObjectMaps.
	 * 
	 * @param poms
	 *            The PredicateObjectMaps that will be added.
	 */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_PREDICATE_OBJECT_MAP)
	public void addPredicateObjectMaps(Collection<PredicateObjectMap> poms);

	/**
	 * Get the LogicalTable associated with this TriplesMap.
	 * 
	 * @return The LogicalTable of this TriplesMap.
	 */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_LOGICAL_TABLE)
	public LogicalTable getLogicalTable();

	/**
	 * Get the SubjectMap associated with this TriplesMap.
	 * 
	 * @return The SubjectMap of this TriplesMap.
	 */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_SUBJECT_MAP)
	public SubjectMap getSubjectMap();

	/**
	 * Get the PredicateObjectMap located at the given index.
	 * 
	 * @param index
	 *            The index of the PredicateObjectMap.
	 * @return The PredicateObjectMap located at the given index.
	 * @throws IndexOutOfBoundsException
	 *             If the given index is out of range.
	 */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_PREDICATE_OBJECT_MAP)
	public PredicateObjectMap getPredicateObjectMap(int index);

	/**
	 * Returns an unmodifiable view of the list of PredicateObjectMaps that have
	 * been added to this TriplesMap.
	 * 
	 * @return An unmodifiable list of PredicateObjectMaps.
	 */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_PREDICATE_OBJECT_MAP)
	public List<PredicateObjectMap> getPredicateObjectMaps();

	/**
	 * Remove the PredicateObjectMap given by the parameter, from the
	 * TriplesMap. The subsequent PredicateObjectMaps in the list will be
	 * shifted left.
	 * 
	 * @param pom
	 *            The PredicateObjectMap to be removed.
	 */
    @W3C_R2RML_Recommendation(R2RMLVocabulary.PROP_PREDICATE_OBJECT_MAP)
	public void removePredicateObjectMap(PredicateObjectMap pom);

	

}
