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

import java.util.List;

/**
 * R2RML Template
 * 
 * @author Marius Strandhaug
 */
public interface Template {

	/**
	 * Gets the string segment at the given index.
	 * 
	 * @param segIndex
	 *            The index of the string segment.
	 * @return The string segment at the given index.
	 */
	String getStringSegment(int segIndex);

    /**
     * Gets the string segments
     * @return The string segments
     */
    List<String> getStringSegments();

    String getTemplateStringWithoutColumnNames();

    /**
	 * Set the string segment on the given index. Any previously set segments on
	 * this index will be removed. Adding a new string segment (that don't
	 * overwrite older ones), must be done by adding it with an index one higher
	 * that the previously highest index.
	 * 
	 * @param segIndex
	 *            The index where the string segment will be set.
	 * @param segment
	 *            The string segment to insert.
	 * @throws IndexOutOfBoundsException
	 *             If the index is larger than the previously highest index plus
	 *             one.
	 */
	void addStringSegment(int segIndex, String segment);

	/**
	 * Gets the column name at the given index.
	 * 
	 * @param colIndex
	 *            The index of the column name.
	 * @return The column name at the given index.
	 */
	String getColumnName(int colIndex);

	/**
	 * Returns an unmodifiable view of all the column names in this template.
	 * 
	 * @return An unmodifiable view of all column names.
	 */
	List<String> getColumnNames();

	/**
	 * Set the column name on the given index. Any previously set column names
	 * on this index will be removed. Adding new column name (that don't
	 * overwrite older ones), must be done by adding it with an index one higher
	 * that the previously highest index.
	 * 
	 * @param colIndex
	 *            The index where the column name will be set.
	 * @param columnName
	 *            The column name to insert.
	 * @throws IndexOutOfBoundsException
	 *             If the index is larger than the previously highest index plus
	 *             one.
	 */
	void addColumnName(int colIndex, String columnName);
}
