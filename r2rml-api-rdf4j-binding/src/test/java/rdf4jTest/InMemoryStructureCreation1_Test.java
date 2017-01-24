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

import eu.optique.r2rml.api.binding.rdf4j.RDF4JR2RMLMappingManager;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.junit.Assert;
import org.junit.Test;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.ValueFactoryImpl;

import eu.optique.r2rml.api.model.LogicalTable;
import eu.optique.r2rml.api.MappingFactory;
import eu.optique.r2rml.api.model.ObjectMap;
import eu.optique.r2rml.api.model.PredicateMap;
import eu.optique.r2rml.api.model.PredicateObjectMap;
import eu.optique.r2rml.api.model.SubjectMap;
import eu.optique.r2rml.api.model.Template;
import eu.optique.r2rml.api.model.TermMap.TermMapType;
import eu.optique.r2rml.api.model.TriplesMap;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class InMemoryStructureCreation1_Test {
	
	@Test
	public void test(){

		RDF4JR2RMLMappingManager mm = RDF4JR2RMLMappingManager.getInstance();
		MappingFactory mfact = mm.getMappingFactory();

        RDF4J rdf4j = new RDF4J();

		//Table
		LogicalTable lt = mfact.createR2RMLView("SELECT * FROM TABLE");
		
		//SubjectMap
		Template templs = mfact.createTemplate("http://data.example.com/employee/{EMPNO}/{ROLE}");
		SubjectMap sm = mfact.createSubjectMap(templs);
		
		//Associated Classes
		ValueFactory myFactory = ValueFactoryImpl.getInstance();
		sm.addClass((IRI) rdf4j.asRDFTerm(myFactory.createURI("http://xmlns.com/foaf/0.1/", "Person")));
		sm.addClass((IRI) rdf4j.asRDFTerm(myFactory.createURI("http://example.com/", "Student")));
					
		//PredicateObjectMap
		PredicateMap pred = mfact.createPredicateMap(TermMapType.CONSTANT_VALUED, (IRI) rdf4j.asRDFTerm(myFactory.createURI("http://example.com/role")));
		Template templo = mfact.createTemplate("http://data.example.com/roles/{ROLE}");
		ObjectMap obm = mfact.createObjectMap(templo);
		PredicateObjectMap pom = mfact.createPredicateObjectMap(pred, obm);
		
		//Other PredicateObjectMap with DataType
		PredicateMap pred1 = mfact.createPredicateMap(TermMapType.CONSTANT_VALUED, (IRI) rdf4j.asRDFTerm(myFactory.createURI("http://example.com/role1")));
		ObjectMap obm1 = mfact.createObjectMap(TermMapType.COLUMN_VALUED, "ROLESS");
		obm1.setDatatype((IRI) rdf4j.asRDFTerm(myFactory.createURI("http://www.w3.org/2001/XMLSchema#", "positiveInteger")));
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
			Iterator<IRI> iter=current.getSubjectMap().getClasses().iterator();
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
					Assert.assertTrue(p.getConstant().toString().contains("role"));
					
				}
				
				Iterator<ObjectMap> omit=pom1.getObjectMaps().iterator();
				while(omit.hasNext()){
					ObjectMap o=omit.next();
					
					if(o.getDatatype()==null){
						Assert.assertTrue(o.getTemplate().getColumnName(0).contains("ROLE"));
							
					}else{
						Assert.assertTrue(o.getDatatype().toString().contains("positiveInteger"));
					}
				}
			}
		}		
	}
}
