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

import eu.optique.api.mapping.LibConfiguration;
import eu.optique.api.mapping.LogicalTable;
import eu.optique.api.mapping.SerializeR2RML;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.RDFTerm;

/**
 * The abstract superclass for R2RMLViewImpl and SQLTableImpl.
 * 
 * @author Marius Strandhaug
 */
public abstract class LogicalTableImpl implements LogicalTable, SerializeR2RML {

	BlankNodeOrIRI res;
	final LibConfiguration lc;

	public LogicalTableImpl(LibConfiguration c) {

		if (c == null) {
			throw new NullPointerException("LibConfiguration was null.");
		}

		lc = c;
	}

	@Override
	public abstract String getSQLQuery();

	@Override
	public void setResource(RDFTerm r) {
		 if (r == null) {
			throw new NullPointerException("A LogicalTable must have a resource.");
		}

		res = (BlankNodeOrIRI) r;
	}

	@Override
	public BlankNodeOrIRI getResource() {
		return res;
	}

}
