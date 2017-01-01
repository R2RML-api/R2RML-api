/*******************************************************************************
 * Copyright 2013, the Optique Consortium
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
package rdf4jTest;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.URI;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;

import eu.optique.api.mapping.LogicalTable;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.TriplesMap;
import eu.optique.api.mapping.impl.R2RMLViewImpl;
import eu.optique.api.mapping.impl.rdf4j.RDF4JR2RMLMappingManagerFactory;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class SQLVersion_Test
{
	
	@Test
	public void test() throws Exception{
		InputStream fis = getClass().getResourceAsStream("../mappingFiles/test15.ttl");
		
		R2RMLMappingManager mm = new RDF4JR2RMLMappingManagerFactory().getR2RMLMappingManager();
		
		// Read the file into a model.
		RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);
		Model m = new LinkedHashModel();
		rdfParser.setRDFHandler(new StatementCollector(m));
		rdfParser.parse(fis, "testMapping");
		
		Collection<TriplesMap> coll = mm.importMappings(m);
		
		Assert.assertTrue(coll.size()==1);
		
		Iterator<TriplesMap> it=coll.iterator();
		while(it.hasNext()){
			TriplesMap current=it.next();
			
			LogicalTable t=current.getLogicalTable();
			
			if (t instanceof R2RMLViewImpl){
				R2RMLViewImpl vi=(R2RMLViewImpl)t;

				int cont=0;
				Iterator<URI> iter=vi.getSQLVersions(URI.class).iterator();
				while(iter.hasNext()){
					URI s=iter.next();
					Assert.assertTrue(s.toString().contains("SQL2008"));
					cont++;
				}
				
				Assert.assertTrue(cont==1);
			}
		}
			
	}

	
}
