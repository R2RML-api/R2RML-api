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

import eu.optique.r2rml.api.model.InverseExpression;

/**
 * An implementation of an InverseExpression.
 * 
 * @author Marius Strandhaug
 */
public class InverseExpressionImpl implements InverseExpression {

	String invExp;

	public InverseExpressionImpl(String inverseExpression) {
		invExp = inverseExpression;
	}

	@Override
	public void setInverseExpression(String strTemplate) {
		invExp = strTemplate;
	}

	@Override
	public String getInverseExpression() {
		return invExp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((invExp == null) ? 0 : invExp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof InverseExpressionImpl))
			return false;

		InverseExpressionImpl other = (InverseExpressionImpl) obj;
		if (invExp == null) {
			if (other.invExp != null) {
				return false;
			}
		} else if (!invExp.equals(other.invExp)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return invExp;
	}

}
