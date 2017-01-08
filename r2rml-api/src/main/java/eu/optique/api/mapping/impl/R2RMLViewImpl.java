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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.R2RMLView;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Triple;

/**
 * An implementation of a R2RMLView.
 * 
 * @author Marius Strandhaug
 */
public class R2RMLViewImpl extends LogicalTableImpl implements R2RMLView {

	String sqlQuery;
	ArrayList<IRI> versionList;

	public R2RMLViewImpl(LibConfiguration c, String sqlQuery) {
		super(c);

		setR2RMLView(sqlQuery);

		versionList = new ArrayList<>();

        setResource(lc.getRDF().createBlankNode());
	}

	@Override
	public void setR2RMLView(String sqlQuery) {
		if (sqlQuery != null) {
			this.sqlQuery = sqlQuery;
		} else {
			throw new NullPointerException(
					"An R2RMLView must have a SQL query.");
		}
	}

	@Override
	public void addSQLVersion(IRI version) {
		versionList.add(version);
	}

	@Override
	public String getSQLQuery() {
		return sqlQuery;
	}

	@Override
	public IRI getSQLVersion(int index) {
		return versionList.get(index);
	}

	@Override
	public List<IRI> getSQLVersions() {
//		List<R> l = new ArrayList<R>();
//		for (Object o : versionList) {
//			l.add(resourceClass.cast(o));
//		}
		return Collections.unmodifiableList(versionList);
	}

	@Override
	public void removeSQLVersion(IRI version) {
		versionList.remove(version);
	}

	@Override
	public Set<Triple> serialize() {
		Set<Triple> stmtSet = new HashSet<>();

        stmtSet.add(lc.getRDF().createTriple(res, lc.getRDFType(), lc.getRDF().createIRI(R2RMLVocabulary.TYPE_R2RML_VIEW)));

        stmtSet.add(lc.getRDF().createTriple(res, lc.getRDF().createIRI(R2RMLVocabulary.PROP_SQL_QUERY),
                lc.getRDF().createLiteral(getSQLQuery())));

		for (IRI version : versionList) {
            stmtSet.add(lc.getRDF().createTriple(res, lc.getRDF().createIRI(R2RMLVocabulary.PROP_SQL_VERSION), version));
		}

		return stmtSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((res == null) ? 0 : res.hashCode());
		result = prime * result
				+ ((sqlQuery == null) ? 0 : sqlQuery.hashCode());
		result = prime * result
				+ ((versionList == null) ? 0 : versionList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof R2RMLViewImpl))
			return false;

		R2RMLViewImpl other = (R2RMLViewImpl) obj;
		if (res == null) {
			if (other.res != null) {
				return false;
			}
		} else if (!res.equals(other.res)) {
			return false;
		}

		if (sqlQuery == null) {
			if (other.sqlQuery != null) {
				return false;
			}
		} else if (!sqlQuery.equals(other.sqlQuery)) {
			return false;
		}

		if (versionList == null) {
			if (other.versionList != null) {
				return false;
			}
		} else if (!versionList.equals(other.versionList)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "R2RMLViewImpl [sqlQuery=" + sqlQuery + ", versionList="
				+ versionList + ", res=" + res + "]";
	}

}
