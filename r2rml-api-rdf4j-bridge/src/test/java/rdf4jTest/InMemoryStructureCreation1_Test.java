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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;
import org.eclipse.rdf4j.model.URI;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.ValueFactoryImpl;

import eu.optique.api.mapping.LogicalTable;
import eu.optique.api.mapping.MappingFactory;
import eu.optique.api.mapping.ObjectMap;
import eu.optique.api.mapping.PredicateMap;
import eu.optique.api.mapping.PredicateObjectMap;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.SubjectMap;
import eu.optique.api.mapping.Template;
import eu.optique.api.mapping.TermMap.TermMapType;
import eu.optique.api.mapping.TriplesMap;
import eu.optique.api.mapping.impl.rdf4j.RDF4JR2RMLMappingManagerFactory;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class InMemoryStructureCreation1_Test {
	
	@Test
	public void test(){

		R2RMLMappingManager mm = new RDF4JR2RMLMappingManagerFactory().getR2RMLMappingManager();
		MappingFactory mfact = mm.getMappingFactory();
		
		//Table
		LogicalTable lt = mfact.createR2RMLView("SELECT * FROM TABLE");
		
		//SubjectMap
		Template templs = mfact.createTemplate("http://data.example.com/employee/{EMPNO}/{ROLE}");
		SubjectMap sm = mfact.createSubjectMap(templs);
		
		//Associated Classes
		ValueFactory myFactory = ValueFactoryImpl.getInstance();
		sm.addClass(myFactory.createURI("http://xmlns.com/foaf/0.1/", "Person"));
		sm.addClass(myFactory.createURI("http://example.com/", "Student"));
					
		//PredicateObjectMap
		PredicateMap pred = mfact.createPredicateMap(TermMapType.CONSTANT_VALUED, "http://example.com/role");
		Template templo = mfact.createTemplate("http://data.example.com/roles/{ROLE}");
		ObjectMap obm = mfact.createObjectMap(templo);
		PredicateObjectMap pom = mfact.createPredicateObjectMap(pred, obm);
		
		//Other PredicateObjectMap with DataType
		PredicateMap pred1 = mfact.createPredicateMap(TermMapType.CONSTANT_VALUED, "http://example.com/role1");
		ObjectMap obm1 = mfact.createObjectMap(TermMapType.COLUMN_VALUED, "ROLESS");
		obm1.setDatatype(myFactory.createURI("http://www.w3.org/2001/XMLSchema#", "positiveInteger"));
		PredicateObjectMap pom11 = mfact.createPredicateObjectMap(pred1, obm1);
		
		//TriplesMap
		TriplesMap tm = mfact.createTriplesMap(lt, sm);
		tm.addPredicateObjectMap(pom);
		tm.addPredicateObjectMap(pom11);
		
		Collection<TriplesMap> coll=new LinkedList<TriplesMap>();
		coll.add(tm);
		
		
		Iterator<TriplesMap> it=coll.iterator();
		while(it.hasNext()){
			TriplesMap current=it.next();
			
			int cont=0;
			Iterator<URI> iter=current.getSubjectMap().getClasses(URI.class).iterator();
			while(iter.hasNext()){
				iter.next();
				cont++;
			}
			
			Assert.assertTrue(cont==2);

		}
		
		
		it=coll.iterator();
		while(it.hasNext()){
			TriplesMap current=it.next();

			Iterator<PredicateObjectMap> pomit=current.getPredicateObjectMaps().iterator();
			while(pomit.hasNext()){
				PredicateObjectMap pom1=pomit.next();
				
				Iterator<PredicateMap> pmit=pom1.getPredicateMaps().iterator();
				while(pmit.hasNext()){
					PredicateMap p=pmit.next();
					Assert.assertTrue(p.getConstant().contains("role"));
					
				}
				
				Iterator<ObjectMap> omit=pom1.getObjectMaps().iterator();
				while(omit.hasNext()){
					ObjectMap o=omit.next();
					
					if(o.getDatatype(URI.class)==null){
						Assert.assertTrue(o.getTemplate().getColumnName(0).contains("ROLE"));
							
					}else{
						Assert.assertTrue(o.getDatatype(URI.class).toString().contains("positiveInteger"));
					}
				}
			}
		}		
	}
}
