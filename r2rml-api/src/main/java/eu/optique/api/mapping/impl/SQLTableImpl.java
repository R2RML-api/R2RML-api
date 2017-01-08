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

import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.SQLTable;
import org.apache.commons.rdf.api.Triple;

/**
 * An implementation of a SQLTable.
 * 
 * @author Marius Strandhaug
 */
public class SQLTableImpl extends LogicalTableImpl implements SQLTable {

	String table;

	public SQLTableImpl(LibConfiguration c, String tableName) {
		super(c);

		setSQLTable(tableName);

        setResource(lc.getRDF().createBlankNode());
	}

	@Override
	public void setSQLTable(String tableName) {
		if (tableName != null) {
			table = tableName;
		} else {
			throw new NullPointerException("A SQLTable must have a table name.");
		}
	}

	@Override
	public String getSQLTableName() {
		return table;
	}

	@Override
	public String getSQLQuery() {
		return "SELECT * FROM " + table;
	}

	@Override
	public Set<Triple> serialize() {
		Set<Triple> stmtSet = new HashSet<Triple>();

        stmtSet.add(lc.getRDF().createTriple(res, lc.getRDF().createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), lc.getRDF().createIRI(R2RMLVocabulary.TYPE_BASE_TABLE_OR_VIEW)));

        stmtSet.add(lc.getRDF().createTriple(res, lc.getRDF().createIRI(R2RMLVocabulary.PROP_TABLE_NAME),
                lc.getRDF().createLiteral(getSQLTableName())));

		return stmtSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((res == null) ? 0 : res.hashCode());
		result = prime * result + ((table == null) ? 0 : table.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof SQLTableImpl))
			return false;

		SQLTableImpl other = (SQLTableImpl) obj;
		if (res == null) {
			if (other.res != null) {
				return false;
			}
		} else if (!res.equals(other.res)) {
			return false;
		}

		if (table == null) {
			if (other.table != null) {
				return false;
			}
		} else if (!table.equals(other.table)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "SQLTableImpl [table=" + table + ", res=" + res + "]";
	}

}
