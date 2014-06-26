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

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Test;
import org.openrdf.model.Model;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.StatementCollector;

import eu.optique.api.mapping.LogicalTable;
import eu.optique.api.mapping.ObjectMap;
import eu.optique.api.mapping.PredicateMap;
import eu.optique.api.mapping.PredicateObjectMap;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.R2RMLMappingManagerFactory;
import eu.optique.api.mapping.SubjectMap;
import eu.optique.api.mapping.Template;
import eu.optique.api.mapping.TriplesMap;
import eu.optique.api.mapping.impl.SQLTableImpl;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class MultiplePredicateObjectMap_Test
{
	
	@Test
	public void test1()throws Exception{
		FileInputStream fis = new FileInputStream(new File("test/mappingFiles/test4.ttl"));
		
		R2RMLMappingManager mm = R2RMLMappingManagerFactory.getSesameMappingManager();
		
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

			SubjectMap s=current.getSubjectMap();
			Template t=s.getTemplate();
			Assert.assertTrue(t.getColumnName(0).contains("EMPNO"));
			Assert.assertTrue(t.getColumnName(1).contains("DEPTNO"));
			
			
			LogicalTable table=current.getLogicalTable();
			SQLTableImpl ta= (SQLTableImpl) table;
			Assert.assertTrue(ta.getSQLTableName().contains("EMP2DEPT"));
		
		}			
	}
	
	

	
	
	@Test
	public void test2() throws Exception{
		
		FileInputStream fis = new FileInputStream(new File("test/mappingFiles/test4.ttl"));
		
		R2RMLMappingManager mm = R2RMLMappingManagerFactory.getSesameMappingManager();
		
		// Read the file into a model.
		RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);
		Model m = new LinkedHashModel();
		rdfParser.setRDFHandler(new StatementCollector(m));
		rdfParser.parse(fis, "testMapping");
		
		Collection<TriplesMap> coll = mm.importMappings(m);
		
		Assert.assertTrue(coll.size()==1);
		
		boolean department=false;
		boolean employee=false;
		boolean EMPNO=false;
		boolean DEPTNO=false;
		
		Iterator<TriplesMap> it=coll.iterator();
		while(it.hasNext()){
			TriplesMap current=it.next();

			Iterator<PredicateObjectMap> pomit=current.getPredicateObjectMaps().iterator();
		
			int cont=0;
			while(pomit.hasNext()){
				PredicateObjectMap pom=pomit.next();
				
				Iterator<PredicateMap> pmit=pom.getPredicateMaps().iterator();
				
				while(pmit.hasNext()){
					PredicateMap p=pmit.next();
					if(p.getConstant().contains("department")){
						department=true;
					}
					
					if(p.getConstant().contains("employee")){
						employee=true;
					}
				}
				
				Iterator<ObjectMap> omit=pom.getObjectMaps().iterator();
				while(omit.hasNext()){
					ObjectMap o=omit.next();
					
					if(o.getTemplate().getColumnName(0).contains("EMPNO")){
						EMPNO=true;
					}
					
					if(o.getTemplate().getColumnName(0).contains("DEPTNO")){
						DEPTNO=true;
					}
				}
				cont++;
			}
			Assert.assertTrue(cont==2);
			Assert.assertTrue(department && employee);
			Assert.assertTrue(EMPNO && DEPTNO);

		}
	}

	
}
