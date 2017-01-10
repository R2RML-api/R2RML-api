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

import eu.optique.r2rml.api.binding.rdf4j.RDF4JR2RMLMappingManager;
import org.junit.Assert;
import org.junit.Test;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;

import eu.optique.r2rml.api.model.TriplesMap;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class Equal_Test
{
	
	@Test
	public void test() throws Exception{
		InputStream fis = getClass().getResourceAsStream("../mappingFiles/test30.ttl");
		
		RDF4JR2RMLMappingManager mm = new RDF4JR2RMLMappingManager.Factory().getR2RMLMappingManager();
		
		// Read the file into a model.
		RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);
		Model m = new LinkedHashModel();
		rdfParser.setRDFHandler(new StatementCollector(m));
		rdfParser.parse(fis, "testMapping");
		
		Collection<TriplesMap> coll = mm.importMappings(m);
		
		Assert.assertTrue(coll.size()==2);
		
		/*
		Iterator<TriplesMap> i=coll.iterator();
		TriplesMap first=i.next();
		TriplesMap second=i.next();

                // The following tests are invalid since they compare object identities
		// //the equals method should not take into account the resource element!
		// Assert.assertTrue(first.getLogicalTable().equals(second.getLogicalTable())); //different objects with the same characteristics
		// Assert.assertTrue(first.getSubjectMap().equals(second.getSubjectMap()));
		// Assert.assertTrue(first.getPredicateObjectMap(0).equals(second.getPredicateObjectMap(0)));
			*/
	}

	
}
