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
package owlapiTest;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import junit.framework.Assert;

import org.coode.owlapi.rdf.model.RDFTriple;
import org.junit.Test;

import eu.optique.api.mapping.ObjectMap;
import eu.optique.api.mapping.PredicateObjectMap;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.R2RMLMappingManagerFactory;
import eu.optique.api.mapping.TriplesMap;
import eu.optique.api.mapping.impl.OWLAPIUtil;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class TriplesMapsWithMultiplePOMap_Test
{
	
	@Test
	public void test() throws Exception{
		
		FileInputStream fis = new FileInputStream(new File("test/mappingFiles/test18.ttl"));
		
		R2RMLMappingManager mm = R2RMLMappingManagerFactory.getOWLAPIMappingManager();
		Set<RDFTriple> triples = OWLAPIUtil.readTurtle(fis);
		Collection<TriplesMap> coll = mm.importMappings(triples);
		
		Iterator<TriplesMap> it=coll.iterator();
		
		boolean livesMatched = false;
		boolean iousMatched = false;
		
		while(it.hasNext()){
			TriplesMap current=it.next();
			
			Iterator<PredicateObjectMap> iter=current.getPredicateObjectMaps().iterator();
			while(iter.hasNext()){
				PredicateObjectMap m=iter.next();

				Iterator<ObjectMap> ii=m.getObjectMaps().iterator();
				while(ii.hasNext()){
					ObjectMap o=ii.next();
					if(o.getConstant()!=null){
						if(o.getConstant().contains("Lives")){
							livesMatched=true;			
						}
						
						if(o.getConstant().contains("IOUs")){
							iousMatched=true;			
						}
					}
				}
			}
		}
		
		Assert.assertTrue(livesMatched && iousMatched);			
	}

	
}
