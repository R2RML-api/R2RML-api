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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collection;

import eu.optique.api.mapping.impl.RDF4JR2RMLMappingManager;
import eu.optique.api.mapping.impl.RDF4JR2RMLMappingManagerFactory;
import org.apache.commons.rdf.rdf4j.RDF4J;
import org.junit.Assert;
import org.junit.Test;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;

import eu.optique.api.mapping.TriplesMap;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class SerilizationMapping_Test
{
	
	@Test
	public void test1() throws Exception {

		try{

			InputStream fis = getClass().getResourceAsStream("../mappingFiles/artist.ttl");
			
			RDF4JR2RMLMappingManager mm = new RDF4JR2RMLMappingManagerFactory().getR2RMLMappingManager();

			RDF4J rdf4J = new RDF4J();

			// Read the file into a model.
			RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);
			Model m = new LinkedHashModel();
			rdfParser.setRDFHandler(new StatementCollector(m));
			rdfParser.parse(fis, "testMapping");
			
			Collection<TriplesMap> coll = mm.importMappings(m);
			
                        File fout = File.createTempFile("artistNew", "ttl");
                        fout.deleteOnExit();
			FileOutputStream fos = new FileOutputStream(fout);
			Model out = mm.exportMappings(coll).asModel().get();
			Rio.write(out, fos, RDFFormat.TURTLE);
			
			FileInputStream fis1 = new FileInputStream(fout);
			Model m1 = new LinkedHashModel();
			rdfParser.setRDFHandler(new StatementCollector(m1));
			rdfParser.parse(fis1, "testMapping1");
			Collection<TriplesMap> coll1 = mm.importMappings(m1);
			
			Assert.assertTrue(fis1!=null);
			Assert.assertTrue(coll1 != null && !coll1.isEmpty());
			
			Assert.assertTrue(true);
			
		}catch (NullPointerException e){
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	
}
