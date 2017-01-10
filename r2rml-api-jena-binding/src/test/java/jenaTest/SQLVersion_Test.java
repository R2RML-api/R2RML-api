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
package jenaTest;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import eu.optique.r2rml.api.binding.jena.JenaR2RMLMappingManager;
import org.apache.commons.rdf.api.IRI;
import org.junit.Assert;

import org.junit.Test;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import eu.optique.r2rml.api.model.LogicalTable;
import eu.optique.r2rml.api.model.TriplesMap;
import eu.optique.r2rml.api.model.impl.R2RMLViewImpl;

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
		
		JenaR2RMLMappingManager mm = new JenaR2RMLMappingManager.Factory().getR2RMLMappingManager();

		Model m = ModelFactory.createDefaultModel();
		m = m.read(fis,"testMapping", "TURTLE");
		Collection<TriplesMap> coll = mm.importMappings(m);
		
		Assert.assertTrue(coll.size()==1);
		
		Iterator<TriplesMap> it=coll.iterator();
		while(it.hasNext()){
			TriplesMap current=it.next();
			
			LogicalTable t=current.getLogicalTable();
			
			if (t instanceof R2RMLViewImpl){
				R2RMLViewImpl vi=(R2RMLViewImpl)t;

				int cont=0;
				Iterator<IRI> iter=vi.getSQLVersions().iterator();
				while(iter.hasNext()){
					IRI s=iter.next();
					Assert.assertTrue(s.toString().contains("SQL2008"));
					cont++;
				}
				
				Assert.assertTrue(cont==1);
			}
		}
			
	}

	
}
