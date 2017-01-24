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
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.ValueFactoryImpl;
import org.junit.Assert;
import org.junit.Test;
import eu.optique.r2rml.api.model.GraphMap;
import eu.optique.r2rml.api.model.Join;
import eu.optique.r2rml.api.model.LogicalTable;
import eu.optique.r2rml.api.MappingFactory;
import eu.optique.r2rml.api.model.ObjectMap;
import eu.optique.r2rml.api.model.PredicateMap;
import eu.optique.r2rml.api.model.PredicateObjectMap;
import eu.optique.r2rml.api.model.RefObjectMap;
import eu.optique.r2rml.api.model.SubjectMap;
import eu.optique.r2rml.api.model.Template;
import eu.optique.r2rml.api.model.TriplesMap;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class InMemoryStructureCreation3_Test{
	
	@Test
	public void test(){

		RDF4JR2RMLMappingManager mm = RDF4JR2RMLMappingManager.getInstance();
		MappingFactory mfact = mm.getMappingFactory();
		ValueFactory myFactory = ValueFactoryImpl.getInstance();
        RDF4J rdf4j = new RDF4J();

		//Table
		LogicalTable lt = mfact.createR2RMLView("SELECT * FROM TABLE");
		
		//SubjectMap
		Template templs = mfact.createTemplate("http://data.example.com/employee/{EMPNO}/{ROLE}");
		SubjectMap sm = mfact.createSubjectMap(templs);

		//GraphMap
		sm.addGraphMap(mfact.createGraphMap((IRI) rdf4j.asRDFTerm(myFactory.createURI("http://example.com/graph/sports"))));
		
		//PredicateObjectMap
		PredicateMap pred = mfact.createPredicateMap((IRI)rdf4j.asRDFTerm(myFactory.createURI("http://example.com/role")));
		Template templo = mfact.createTemplate("http://data.example.com/roles/{ROLE}");
		ObjectMap obm = mfact.createObjectMap(templo);
		PredicateObjectMap pom = mfact.createPredicateObjectMap(pred, obm);
		
		//TriplesMap
		TriplesMap tm =  mfact.createTriplesMap(lt, sm);
		tm.addPredicateObjectMap(pom);
		
		//RefObjectMap with join condition
		RefObjectMap romi = mfact.createRefObjectMap(tm);
		romi.addJoinCondition(mfact.createJoinCondition("\"Sport\"", "\"ID\""));
		pom.addRefObjectMap(romi);
		
		Collection<TriplesMap> coll=new LinkedList<TriplesMap>();
		coll.add(tm);
		
		
		Iterator<TriplesMap> it=coll.iterator();
		while(it.hasNext()){
			TriplesMap current=it.next();

			SubjectMap s=current.getSubjectMap();
			Iterator<GraphMap> gmit=s.getGraphMaps().iterator();
			
			int conta=0;
			while(gmit.hasNext()){
				GraphMap g=gmit.next();
				Assert.assertTrue(g.getConstant().toString().contains("http://example.com/graph/sports"));
				conta++;
			}
			Assert.assertTrue(conta==1);
		
			
			Iterator<RefObjectMap> romit=pom.getRefObjectMaps().iterator();
			while(romit.hasNext()){
				RefObjectMap rom=romit.next();
				
				Assert.assertTrue(rom.getParentMap()!=null);
				
				Iterator<Join> itjoin=rom.getJoinConditions().iterator();
				
				int joincont=0;
				while(itjoin.hasNext()){
					itjoin.next();
					joincont++;
				}	
				Assert.assertTrue(joincont==1); 
			}
			
			
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
					Assert.assertTrue(o.getTemplate().getColumnName(0).contains("ROLE"));
				
				}
			}
		}
	}
}
