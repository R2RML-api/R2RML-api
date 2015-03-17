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
package eu.optique.api.mapping;

/**
 * R2RML Inverse Expression
 * 
 * @author Marius Strandhaug
 */
public interface InverseExpression {

	/**
	 * Sets the string template of this InverseExpression.
	 * 
	 * @param strTemplate
	 *            The string template to set.
	 */
	public void setInverseExpression(String strTemplate);

	/**
	 * Get the string template of this InverseExpression.
	 * 
	 * @return The string template of this InverseExpression.
	 */
	public String getInverseExpression();

}
