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
import eu.optique.r2rml.api.model.impl.SQLBaseTableOrViewImpl;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.graph.NodeFactory;
import org.junit.Assert;

import org.junit.Test;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import eu.optique.r2rml.api.model.LogicalTable;
import eu.optique.r2rml.api.model.ObjectMap;
import eu.optique.r2rml.api.model.PredicateMap;
import eu.optique.r2rml.api.model.PredicateObjectMap;
import eu.optique.r2rml.api.model.SubjectMap;
import eu.optique.r2rml.api.model.Template;
import eu.optique.r2rml.api.model.TriplesMap;
import eu.optique.r2rml.api.model.R2RMLVocabulary;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class TermType_Test {
	
	@Test
	public void test() throws Exception{
		
		InputStream fis = getClass().getResourceAsStream("../mappingFiles/test7.ttl");
		
		JenaR2RMLMappingManager mm = JenaR2RMLMappingManager.getInstance();

		JenaRDF jena = new JenaRDF();

        Model m = ModelFactory.createDefaultModel();
		m = m.read(fis, "http://example.com#testMapping", "TURTLE");
		Collection<TriplesMap> coll = mm.importMappings(m);
		
		Assert.assertTrue(coll.size()==1);
		
		Iterator<TriplesMap> it=coll.iterator();
		while(it.hasNext()){
			TriplesMap current=it.next();

			SubjectMap s=current.getSubjectMap();
			Template t=s.getTemplate();
			Assert.assertTrue(t.getColumnName(0).contains("ID"));
			
			LogicalTable table=current.getLogicalTable();
			
			
			if(table instanceof SQLBaseTableOrViewImpl){
				SQLBaseTableOrViewImpl ta= (SQLBaseTableOrViewImpl) table;
				Assert.assertTrue(ta.getTableName().contains("Student"));
			}else{
				Assert.assertTrue(false);
			}
			
			
			
			Iterator<PredicateObjectMap> pomit=current.getPredicateObjectMaps().iterator();
			while(pomit.hasNext()){
				PredicateObjectMap pom=pomit.next();
				
				Iterator<PredicateMap> pmit=pom.getPredicateMaps().iterator();
				while(pmit.hasNext()){
					PredicateMap p=pmit.next();
					boolean first=p.getConstant().contains("name");
					
					Assert.assertTrue(first);
					
				}
				
				Iterator<ObjectMap> omit=pom.getObjectMaps().iterator();
				while(omit.hasNext()){
					ObjectMap o=omit.next();
					
					Assert.assertEquals("\"FirstName\"", o.getTemplate().getColumnName(0));
					Assert.assertEquals("\"LastName\"", o.getTemplate().getColumnName(1));
					
					IRI u=o.getTermType();

                    Assert.assertEquals(jena.asJenaNode(u), NodeFactory.createURI(R2RMLVocabulary.TERM_LITERAL));
					
				}
			}
			
		}			
	}
	
}
