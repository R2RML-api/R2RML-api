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
package eu.optique.r2rml.api.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import eu.optique.r2rml.api.model.Template;

/**
 * An implementation of a Template.
 * 
 * @author Marius Strandhaug
 */
public class TemplateImpl implements Template {

	private ArrayList<String> segList;
	private ArrayList<String> colList;

	TemplateImpl() {
		segList = new ArrayList<>();
		colList = new ArrayList<>();
	}

	TemplateImpl(String template) {
		segList = new ArrayList<>();
		colList = new ArrayList<>();

		char[] chars = template.toCharArray();

		/*
		 * Go through the characters looking for unescaped curly braces. It adds
		 * the string segments and column names to the list, while also checking
		 * that the template is valid.
		 */
		boolean braceFound = false;
		int prev = 0;
		int index = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '{' && !isEscaped(i, chars)) {
				if (!braceFound) {
					braceFound = true;

					addStringSegment(index, template.substring(prev, i));
					prev = i + 1;
				} else {
					throw new IllegalArgumentException(
							"Illegal template syntax. The curly braces don't match.");
				}
			} else if (chars[i] == '}' && !isEscaped(i, chars)) {
				if (braceFound) {
					braceFound = false;

					addColumnName(index++, template.substring(prev, i));
					prev = i + 1;
				} else {
					throw new IllegalArgumentException(
							"Illegal template syntax. The curly braces don't match.");
				}
			}
		}

		// The template had an unmatched left curly brace at the end.
		if (braceFound) {
			throw new IllegalArgumentException(
					"Illegal template syntax. The curly braces don't match.");
		} else {
			if (chars[chars.length - 1] != '}'
					|| isEscaped(chars.length - 1, chars))
				addStringSegment(index, template.substring(prev, chars.length));
		}
	}

    /**
	 * Check if the character on the given index is escaped.
	 *
	 * @param index
	 *            The index of the possibly escaped character.
	 * @param chars
	 *            The array of characters to look in.
	 * @return True if the character was escaped, false otherwise.
	 */
	private static boolean isEscaped(int index, char[] chars) {
		if (index >= chars.length) {
			throw new ArrayIndexOutOfBoundsException();
		}

		boolean odd = false;
		for (int i = index - 1; i >= 0; i--) {
			if (chars[i] == '\\') {
				odd = !odd;
			} else {
				return odd;
			}
		}

		return odd;
	}

    @Override
	public String getStringSegment(int segIndex) {
		return segList.get(segIndex);
	}

	@Override
	public void addStringSegment(int segIndex, String segment) {
		try {
			// Remove the segment currently at this position.
			segList.remove(segIndex);
		} catch (IndexOutOfBoundsException i) {
			// Just continue. This isn't a problem here.
		}

		segList.add(segIndex, segment);
	}

	@Override
	public String getColumnName(int colIndex) {
		return colList.get(colIndex);
	}

	@Override
	public List<String> getColumnNames() {
		return Collections.unmodifiableList(colList);
	}

	@Override
	public void addColumnName(int colIndex, String columnName) {
		try {
			// Remove the column currently at this position.
			colList.remove(colIndex);
		} catch (IndexOutOfBoundsException i) {
			// Just continue. This isn't a problem here.
		}

		colList.add(colIndex, columnName);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colList == null) ? 0 : colList.hashCode());
		result = prime * result + ((segList == null) ? 0 : segList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof TemplateImpl))
			return false;

		TemplateImpl other = (TemplateImpl) obj;
		if (colList == null) {
			if (other.colList != null) {
				return false;
			}
		} else if (!colList.equals(other.colList)) {
			return false;
		}

		if (segList == null) {
			if (other.segList != null) {
				return false;
			}
		} else if (!segList.equals(other.segList)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		Iterator<String> segIt = segList.listIterator();
		Iterator<String> colIt = colList.listIterator();

		StringBuilder sb = new StringBuilder();

		while (segIt.hasNext()) {
			sb.append(segIt.next());

			if (colIt.hasNext()) {
				sb.append("{" + colIt.next() + "}");
			} else {
				break;
			}
		}

		return sb.toString();
	}
}
