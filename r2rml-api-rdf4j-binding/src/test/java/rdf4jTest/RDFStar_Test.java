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

import eu.optique.r2rml.api.binding.rdf4j.RDF4JR2RMLMappingManager;
import eu.optique.r2rml.api.model.*;
import eu.optique.r2rml.api.model.impl.SQLBaseTableOrViewImpl;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

/**
 * JUnit Test Cases for RDFStar support
 * 
 * @author Riccardo Mancini
 * @author Lukas Sundqvist
 */
public class RDFStar_Test
{
	
	@Test
	public void test1() throws Exception{
		
		InputStream fis = getClass().getResourceAsStream("../mappingFiles/prof-rdfstar.ttl");
		
		RDF4JR2RMLMappingManager mm = RDF4JR2RMLMappingManager.getInstance();
		
		// Read the file into a model.
		RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);
		Model m = new LinkedHashModel();
		rdfParser.setRDFHandler(new StatementCollector(m));
		rdfParser.parse(fis, "testMapping");
		
		Collection<TriplesMap> coll = mm.importMappings(m);
		
		Assert.assertTrue(coll.size()==2);
		
		Iterator<TriplesMap> it=coll.iterator();
		/*while(it.hasNext()){
			TriplesMap current=it.next();

			SubjectMap s=current.getSubjectMap();
			Template t=s.getTemplate();
			Assert.assertTrue(t.getColumnName(0).contains("EMPNO"));
			
			LogicalTable table=current.getLogicalTable();
			
			SQLBaseTableOrViewImpl ta= (SQLBaseTableOrViewImpl) table;
			Assert.assertTrue(ta.getTableName().contains("EMP2DEPT"));
				
		}*/
	}
	
	

	
	
	@Test
	@Ignore
	public void test2() throws Exception{
		
		InputStream fis = getClass().getResourceAsStream("../mappingFiles/test5.ttl");
		
		RDF4JR2RMLMappingManager mm = RDF4JR2RMLMappingManager.getInstance();
		
		// Read the file into a model.
		RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);
		Model m = new LinkedHashModel();
		rdfParser.setRDFHandler(new StatementCollector(m));
		rdfParser.parse(fis, "testMapping");
		
		Collection<TriplesMap> coll = mm.importMappings(m);
		
		Assert.assertTrue(coll.size()==1);
		
		Iterator<TriplesMap> it=coll.iterator();
		while(it.hasNext()){
			TriplesMap current=it.next();

			Iterator<PredicateObjectMap> pomit=current.getPredicateObjectMaps().iterator();
			PredicateObjectMap pom=pomit.next();
			
			Iterator<PredicateMap> pmit=pom.getPredicateMaps().iterator();
			while(pmit.hasNext()){
				PredicateMap p=pmit.next();
				boolean first=p.getConstant().toString().contains("department");
				
				Assert.assertTrue(first);
				
			}
			
			Iterator<ObjectMap> omit=pom.getObjectMaps().iterator();
			while(omit.hasNext()){
				ObjectMap o=omit.next();
				
				boolean first=o.getTemplate().getColumnName(0).contains("DEPTNO");
				
				Assert.assertTrue(first);

			}
		}
	}

	
}
