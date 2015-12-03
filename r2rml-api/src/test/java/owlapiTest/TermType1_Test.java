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

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import junit.framework.Assert;

import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFResourceIRI;
import org.semanticweb.owlapi.io.RDFTriple;
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;

import eu.optique.api.mapping.LogicalTable;
import eu.optique.api.mapping.PredicateObjectMap;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.R2RMLMappingManagerFactory;
import eu.optique.api.mapping.SubjectMap;
import eu.optique.api.mapping.Template;
import eu.optique.api.mapping.TriplesMap;
import eu.optique.api.mapping.impl.OWLAPIUtil;
import eu.optique.api.mapping.impl.R2RMLVocabulary;
import eu.optique.api.mapping.impl.SQLTableImpl;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class TermType1_Test {
	
	@Test
	public void test() throws Exception{
		
		InputStream fis = getClass().getResourceAsStream("../mappingFiles/test8.ttl");
		
		R2RMLMappingManager mm = R2RMLMappingManagerFactory.getOWLAPIMappingManager();
		Set<RDFTriple> triples = OWLAPIUtil.readTurtle(fis);
		Collection<TriplesMap> coll = mm.importMappings(triples);
		
		Assert.assertTrue(coll.size()==1);
		
		Iterator<TriplesMap> it=coll.iterator();
		while(it.hasNext()){
			TriplesMap current=it.next();

			SubjectMap s=current.getSubjectMap();
			Template t=s.getTemplate();
			Assert.assertTrue(t.getColumnName(0).contains("fname"));
			
			RDFResource u=s.getTermType(RDFResource.class);
			Assert.assertEquals(u, new RDFResourceIRI(IRI.create(R2RMLVocabulary.TERM_BLANK_NODE)));
		
			
			Iterator<RDFResource> ituri=s.getClasses(RDFResource.class).iterator();
			int cont=0;
			while(ituri.hasNext()){
				ituri.next();
				cont++;
			}
			
			Assert.assertTrue(cont==1);
				
			LogicalTable table=current.getLogicalTable();
			
			if(table instanceof SQLTableImpl){
				SQLTableImpl ta= (SQLTableImpl) table;
				Assert.assertTrue(ta.getSQLTableName().contains("IOUs"));
			}else{
				Assert.assertTrue(false);
			}
			
			
			
			Iterator<PredicateObjectMap> pomit=current.getPredicateObjectMaps().iterator();
			cont=0;
			while(pomit.hasNext()){
				pomit.next();
				cont++;
			}
			Assert.assertTrue(cont==3);
			
			
		}			
	}
	
	
	
}
