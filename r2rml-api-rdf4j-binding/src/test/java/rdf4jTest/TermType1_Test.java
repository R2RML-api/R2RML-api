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

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import eu.optique.r2rml.api.binding.rdf4j.RDF4JR2RMLMappingManager;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.junit.Assert;
import org.junit.Test;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.ValueFactoryImpl;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;

import eu.optique.r2rml.api.model.LogicalTable;
import eu.optique.r2rml.api.model.PredicateObjectMap;
import eu.optique.r2rml.api.model.SubjectMap;
import eu.optique.r2rml.api.model.Template;
import eu.optique.r2rml.api.model.TriplesMap;
import eu.optique.r2rml.api.model.impl.R2RMLVocabulary;
import eu.optique.r2rml.api.model.impl.SQLTableImpl;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class TermType1_Test {
	
	@Test
	public void test() throws Exception{

		ValueFactory myFactory = ValueFactoryImpl.getInstance();
		
		InputStream fis = getClass().getResourceAsStream("../mappingFiles/test8.ttl");
		
		RDF4JR2RMLMappingManager mm = RDF4JR2RMLMappingManager.getInstance();

        RDF4J rdf4j = new RDF4J();

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
			Assert.assertTrue(t.getColumnName(0).contains("fname"));
			
			IRI u=s.getTermType();

            Assert.assertEquals(u, rdf4j.asRDFTerm(myFactory.createIRI(R2RMLVocabulary.TERM_BLANK_NODE)));

			Iterator<IRI> ituri=s.getClasses().iterator();
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
