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

import java.util.HashSet;
import java.util.Set;

import eu.optique.api.mapping.InverseExpression;
import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.Template;
import eu.optique.api.mapping.TermMap;

/**
 * An implementation of a TermMap. A TermMap must either be a SubjectMap, a
 * PredicateMap, an ObjectMap or a GraphMap, and is therefore abstract.
 * 
 * @author Marius Strandhaug
 */
public abstract class TermMapImpl implements TermMap {

	TermMapType type;

	Object termtype;

	// Only one of these will be set.
	Template template;
	String constVal;
	String columnName;

	InverseExpression inverseExp;

	Object res;
	final LibConfiguration lc;

	public TermMapImpl(LibConfiguration c, TermMapType termMapType,
			Template template) {

		if (c == null) {
			throw new NullPointerException("LibConfiguration was null.");
		}

		lc = c;

		if (termMapType == null) {
			throw new NullPointerException("A TermMap must have a TermMapType.");
		} else if (template == null) {
			throw new NullPointerException(
					"A template-valued TermMap must have a Template.");
		} else if (termMapType != TermMapType.TEMPLATE_VALUED) {
			throw new IllegalStateException("Wrong TermMapType");
		} else {
			type = termMapType;
			setDefaultTermType();
			setTemplate(template);

			setResource(lc.createBNode());
		}
	}

	public TermMapImpl(LibConfiguration c, TermMapType termMapType,
			String columnOrConst) {

		if (c == null) {
			throw new NullPointerException("LibConfiguration was null.");
		}

		lc = c;

		if (termMapType == null) {
			throw new NullPointerException("The TermMapType is null.");
		} else if (columnOrConst == null) {
			throw new NullPointerException("ColumnOrConst is null.");
		} else {
			type = termMapType;
			setDefaultTermType();
			setResource(lc.createBNode());

			if (getTermMapType() == TermMapType.COLUMN_VALUED) {
				setColumn(columnOrConst);
			} else if (getTermMapType() == TermMapType.CONSTANT_VALUED) {
				setConstant(columnOrConst);
			} else {
				throw new IllegalStateException("Wrong TermMapType");
			}
		}
	}

	@Override
	public TermMapType getTermMapType() {
		return type;
	}

	@Override
	public void setTemplate(Template template) {
		if (getTermMapType() == TermMapType.TEMPLATE_VALUED) {
			if (template != null) {
				this.template = template;
			} else {
				throw new NullPointerException(
						"A template-valued TermMap must have a Template.");
			}
		} else {
			throw new IllegalStateException("Wrong TermMapType");
		}
	}

	@Override
	public void setConstant(String constVal) {
		if (getTermMapType() == TermMapType.CONSTANT_VALUED) {
			if (constVal != null) {
				this.constVal = constVal;
			} else {
				throw new NullPointerException(
						"A constant-valued TermMap must have a value.");
			}
		} else {
			throw new IllegalStateException("Wrong TermMapType");
		}
	}

	@Override
	public void setColumn(String columnName) {
		if (getTermMapType() == TermMapType.COLUMN_VALUED) {
			if (columnName != null) {
				this.columnName = columnName;
			} else {
				throw new NullPointerException(
						"A column-valued TermMap must have a column name.");
			}
		} else {
			throw new IllegalStateException("Wrong TermMapType");
		}
	}

	@Override
	public void setInverseExpression(InverseExpression invExp) {
		if (getTermMapType() == TermMapType.COLUMN_VALUED
				|| getTermMapType() == TermMapType.TEMPLATE_VALUED) {
			inverseExp = invExp;
		} else {
			throw new IllegalStateException("Wrong TermMapType");
		}
	}

	@Override
	public void setDefaultTermType() {
		termtype = lc.createResource(R2RMLVocabulary.TERM_IRI);
	}

	@Override
	public <R> R getTermType(Class<R> resourceClass) {
		return resourceClass.cast(termtype);
	}

	@Override
	public Template getTemplate() {
		return template;
	}

	@Override
	public String getTemplateString() {
		return template.toString();
	}

	@Override
	public String getConstant() {
		return constVal;
	}

	@Override
	public String getColumn() {
		return columnName;
	}

	@Override
	public InverseExpression getInverseExpression() {
		return inverseExp;
	}

	@Override
	public String getInverseExpressionString() {
		return inverseExp.toString();
	}

	@Override
	public void removeInverseExpression() {
		inverseExp = null;
	}

	@Override
	public void setResource(Object r) {
		if (r != null && !lc.getResourceClass().isInstance(r)) {
			throw new IllegalArgumentException("Parameter r is of type "
					+ r.getClass() + ". Should be an instance of "
					+ lc.getResourceClass() + ".");
		} else if (r == null) {
			throw new NullPointerException("A TermMap must have a resource.");
		}

		res = r;
	}

	@Override
	public <R> R getResource(Class<R> resourceClass) {
		return resourceClass.cast(res);
	}

	@Override
	public <T> Set<T> serialize(Class<T> tripleClass) {
		Set<T> stmtSet = new HashSet<T>();

		stmtSet.add(tripleClass.cast(lc.createTriple(res, lc.getRDFType(),
				lc.createResource(R2RMLVocabulary.TYPE_TERM_MAP))));

		if (type == TermMapType.COLUMN_VALUED) {
			stmtSet.add(tripleClass.cast(lc.createLiteralTriple(res,
					lc.createResource(R2RMLVocabulary.PROP_COLUMN), getColumn())));
		} else if (type == TermMapType.CONSTANT_VALUED) {
			stmtSet.add(tripleClass.cast(lc.createLiteralTriple(res,
					lc.createResource(R2RMLVocabulary.PROP_CONSTANT),
					getConstant())));
		} else if (type == TermMapType.TEMPLATE_VALUED) {
			stmtSet.add(tripleClass.cast(lc.createLiteralTriple(res,
					lc.createResource(R2RMLVocabulary.PROP_TEMPLATE),
					getTemplateString())));
		}

		// Will always have the term type explicitly listed.
		stmtSet.add(tripleClass.cast(lc.createTriple(res,
				lc.createResource(R2RMLVocabulary.PROP_TERM_TYPE), termtype)));
		
		if(getInverseExpression() != null){
			stmtSet.add(tripleClass.cast(lc.createLiteralTriple(res, 
					lc.createResource(R2RMLVocabulary.PROP_INVERSE_EXPRESSION), 
					getInverseExpressionString())));
		}

		return stmtSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result
				+ ((constVal == null) ? 0 : constVal.hashCode());
		result = prime * result + ((res == null) ? 0 : res.hashCode());
		result = prime * result
				+ ((termtype == null) ? 0 : termtype.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof TermMapImpl))
			return false;

		TermMapImpl other = (TermMapImpl) obj;
		if (columnName == null) {
			if (other.columnName != null) {
				return false;
			}
		} else if (!columnName.equals(other.columnName)) {
			return false;
		}

		if (constVal == null) {
			if (other.constVal != null) {
				return false;
			}
		} else if (!constVal.equals(other.constVal)) {
			return false;
		}

		if (res == null) {
			if (other.res != null) {
				return false;
			}
		} else if (!res.equals(other.res)) {
			return false;
		}

		if (termtype == null) {
			if (other.termtype != null) {
				return false;
			}
		} else if (!termtype.equals(other.termtype)) {
			return false;
		}

		if (type != other.type) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "TermMapImpl [type=" + type + ", termtype=" + termtype
				+ ", template=" + template + ", constVal=" + constVal
				+ ", columnName=" + columnName + ", inverseExp=" + inverseExp
				+ ", res=" + res + "]";
	}

}
