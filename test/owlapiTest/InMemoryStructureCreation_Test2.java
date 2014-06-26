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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import junit.framework.Assert;

import org.coode.owlapi.rdf.model.RDFResourceNode;
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;

import eu.optique.api.mapping.LogicalTable;
import eu.optique.api.mapping.MappingFactory;
import eu.optique.api.mapping.ObjectMap;
import eu.optique.api.mapping.PredicateMap;
import eu.optique.api.mapping.PredicateObjectMap;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.R2RMLMappingManagerFactory;
import eu.optique.api.mapping.R2RMLView;
import eu.optique.api.mapping.SubjectMap;
import eu.optique.api.mapping.Template;
import eu.optique.api.mapping.TriplesMap;
import eu.optique.api.mapping.TermMap.TermMapType;
import eu.optique.api.mapping.impl.R2RMLViewImpl;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class InMemoryStructureCreation_Test2 {
	
	@Test
	public void test(){

		R2RMLMappingManager mm = R2RMLMappingManagerFactory.getOWLAPIMappingManager();
		MappingFactory mfact = mm.getMappingFactory();
	
		//Table
		R2RMLView s = mfact.createR2RMLView("SELECT * FROM TABLE");
		
		//SQL Versions
		s.addSQLVersion(new RDFResourceNode(IRI.create("http://www.w3.org/ns/r2rml#","SQL2008")));
		LogicalTable lt = s;

		//SubjectMap
		Template templs = mfact.createTemplate("http://data.example.com/employee/{EMPNO}/{ROLE}");
		SubjectMap sm = mfact.createSubjectMap(templs);
		
		//Associated Classes
		sm.addClass(new RDFResourceNode(IRI.create("http://xmlns.com/foaf/0.1/", "Person")));
		sm.addClass(new RDFResourceNode(IRI.create("http://example.com/", "Student")));
					
		//PredicateObjectMap
		PredicateMap pred = mfact.createPredicateMap(TermMapType.CONSTANT_VALUED, "http://example.com/role");
		Template templo = mfact.createTemplate("http://data.example.com/roles/{ROLE}");
		ObjectMap obm = mfact.createObjectMap(templo);
		PredicateObjectMap pom = mfact.createPredicateObjectMap(pred, obm);
		
		//Other PredicateObjectMap with DataType
		PredicateMap pred1 = mfact.createPredicateMap(TermMapType.CONSTANT_VALUED, "http://example.com/role1");
		ObjectMap obm1 = mfact.createObjectMap(TermMapType.COLUMN_VALUED, "ROLESS");
		obm1.setDatatype(new RDFResourceNode(IRI.create("http://www.w3.org/2001/XMLSchema#", "positiveInteger")));
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
			Iterator<RDFResourceNode> iter=current.getSubjectMap().getClasses(RDFResourceNode.class).iterator();
			while(iter.hasNext()){
				iter.next();
				cont++;
			}
			
			Assert.assertTrue(cont==2);
			
			LogicalTable t=current.getLogicalTable();
			if (t instanceof R2RMLViewImpl){
				R2RMLViewImpl vi=(R2RMLViewImpl)t;
				cont=0;
				iter=vi.getSQLVersions(RDFResourceNode.class).iterator();
				while(iter.hasNext()){
					RDFResourceNode ss=iter.next();
					Assert.assertTrue(ss.toString().contains("SQL2008"));
					cont++;
				}
				
				Assert.assertTrue(cont==1);
			}
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
					
					if(o.getDatatype(RDFResourceNode.class)==null){
						Assert.assertTrue(o.getTemplate().getColumnName(0).contains("ROLE"));
							
					}else{
						Assert.assertTrue(o.getDatatype(RDFResourceNode.class).toString().contains("positiveInteger"));
					}
				}
			}
		}
	}
}