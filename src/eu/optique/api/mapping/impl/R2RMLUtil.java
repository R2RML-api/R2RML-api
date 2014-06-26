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

/**
 * Utility class to take care of some general transformations as trimming
 * apostrophes and others in the future.
 * 
 * @author Timea Bagosi
 * @author Marius Strandhaug
 */
public class R2RMLUtil {

	/**
	 * Trims all unescaped leading or trailing apostrophes from a string.
	 * 
	 * @param toTrim
	 *            - the string to trim
	 * @return the string without any leading or trailing apostrophes
	 */
	public static String trimAllApostrophes(String toTrim) {
		if (toTrim == null)
			return null;
		String newString = toTrim;
		while (newString.startsWith("\""))
			newString = newString.substring(1);
		while (newString.endsWith("\"")
				&& !isEscaped(newString.length() - 1, newString.toCharArray()))
			newString = newString.substring(0, newString.length() - 1);
		return newString;
	}

	/**
	 * Trims all unescaped leading or trailing apostrophes down to one from a
	 * given string
	 * 
	 * @param toTrim
	 *            - the string to trim
	 * @return the string left with one leading and/or trailing apostrophe
	 */
	public static String trimToOneApostrophe(String toTrim) {
		if (toTrim == null)
			return null;
		String newString = toTrim;
		while (newString.startsWith("\"\""))
			newString = newString.substring(1);
		while (newString.endsWith("\"\"")
				&& !isEscaped(newString.length() - 2, newString.toCharArray()))
			newString = newString.substring(0, newString.length() - 1);
		return newString;
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
	public static boolean isEscaped(int index, char[] chars) {
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

}
