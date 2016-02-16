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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

import eu.optique.api.mapping.GraphMap;
import eu.optique.api.mapping.Join;
import eu.optique.api.mapping.LogicalTable;
import eu.optique.api.mapping.MappingFactory;
import eu.optique.api.mapping.ObjectMap;
import eu.optique.api.mapping.PredicateMap;
import eu.optique.api.mapping.PredicateObjectMap;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.impl.jena.JenaR2RMLMappingManagerFactory;
import eu.optique.api.mapping.RefObjectMap;
import eu.optique.api.mapping.SubjectMap;
import eu.optique.api.mapping.Template;
import eu.optique.api.mapping.TriplesMap;
import eu.optique.api.mapping.TermMap.TermMapType;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class InMemoryStructureCreation3_Test{
	
	@Test
	public void test(){
		
		R2RMLMappingManager mm = new JenaR2RMLMappingManagerFactory().getR2RMLMappingManager();
		MappingFactory mfact = mm.getMappingFactory();

		//Table
		LogicalTable lt = mfact.createR2RMLView("SELECT * FROM TABLE");
		
		//SubjectMap
		Template templs = mfact.createTemplate("http://data.example.com/employee/{EMPNO}/{ROLE}");
		SubjectMap sm = mfact.createSubjectMap(templs);
		
		//GraphMap
		sm.addGraphMap(mfact.createGraphMap(TermMapType.CONSTANT_VALUED, "http://example.com/graph/sports"));
			
		//PredicateObjectMap
		PredicateMap pred = mfact.createPredicateMap(TermMapType.CONSTANT_VALUED, "http://example.com/role");
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
				Assert.assertTrue(g.getConstant().contains("http://example.com/graph/sports"));
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
					Assert.assertTrue(p.getConstant().contains("role"));
					
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
