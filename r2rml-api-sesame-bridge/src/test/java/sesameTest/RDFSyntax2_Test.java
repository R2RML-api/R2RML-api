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
package sesameTest;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.openrdf.model.Model;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.StatementCollector;

import eu.optique.api.mapping.PredicateObjectMap;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.RefObjectMap;
import eu.optique.api.mapping.SubjectMap;
import eu.optique.api.mapping.Template;
import eu.optique.api.mapping.TriplesMap;
import eu.optique.api.mapping.impl.sesame.SesameR2RMLMappingManagerFactory;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class RDFSyntax2_Test {
	
	@Test
	public void test() throws Exception{
		
		InputStream fis = getClass().getResourceAsStream("../mappingFiles/test23.ttl");

        R2RMLMappingManager mm = new SesameR2RMLMappingManagerFactory().getR2RMLMappingManager();
		
		// Read the file into a model.
		RDFParser rdfParser = Rio.createParser(RDFFormat.RDFXML);
		Model m = new LinkedHashModel();
		rdfParser.setRDFHandler(new StatementCollector(m));
		rdfParser.parse(fis, "testMapping");
		
		Collection<TriplesMap> coll = mm.importMappings(m);
		
		Assert.assertTrue(coll.size()==2);
		
		Iterator<TriplesMap> it=coll.iterator();
		while(it.hasNext()){
			TriplesMap current=it.next();

			Iterator<PredicateObjectMap> pomit=current.getPredicateObjectMaps().iterator();
			int cont=0;
			while(pomit.hasNext()){
				pomit.next();
				cont++;
			}
			
			if(cont==1){
				SubjectMap s=current.getSubjectMap();
				Template t=s.getTemplate();
				Assert.assertTrue(t.getColumnName(0).contains("Sport"));

			}
			else if(cont==4){
				
				pomit=current.getPredicateObjectMaps().iterator();
				
				while(pomit.hasNext()){
					PredicateObjectMap pom=pomit.next();
					
					if(pom.getPredicateMaps().iterator().next().getConstant().contains("Sport")){
						Iterator<RefObjectMap> gmit=pom.getRefObjectMaps().iterator();
						while(gmit.hasNext()){
							RefObjectMap rom=gmit.next();
							Assert.assertTrue(rom.getParentMap()!=null);
						}	
					}
					
				}
			}else throw new Exception("The number PredicateObjectMaps must be 1 for the TriplesMap2 or 4 for the TriplesMap1!");
			
		}			
		
	}
	
	
}
