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

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.FileUtils;
import com.hp.hpl.jena.vocabulary.RDF;

import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.impl.InvalidR2RMLMappingException;
import eu.optique.api.mapping.impl.jena.JenaR2RMLMappingManagerFactory;
import eu.optique.api.mapping.TriplesMap;

import eu.optique.api.mapping.impl.R2RMLVocabulary;

/**
 * JUnit Test Cases
 * 
 * @author Martin G. Skjæveland
 */
public class SerialisationTest {

	private final String mappingFilesDir = "src/test/resources/mappingFiles/";
	private final String r2rmlURL = "http://www.w3.org/ns/r2rml";

	public Model readModel (String file) {
		return FileManager.get().loadModel(file);//, FileUtils.langTurtle);	
	}
	
	public OntModel getOntModel (Model model) {
		OntModel ontmodel = ModelFactory.createOntologyModel(PelletReasonerFactory.THE_SPEC);
		ontmodel.read(r2rmlURL);
		ontmodel.add(model);
		return ontmodel;
	}

	public static Model getSerialisedModel (Model model) throws InvalidR2RMLMappingException {
		R2RMLMappingManager mm = new JenaR2RMLMappingManagerFactory().getR2RMLMappingManager();
		Collection<TriplesMap> tripleMaps = mm.importMappings(model);
		Model out = mm.exportMappings(tripleMaps, Model.class);
		return out;
	}

	public void test (String file) throws InvalidR2RMLMappingException {
		// read file and apply reasoning to saturate according to R2RML vocabulary.
		Model original    = getOntModel(readModel(mappingFilesDir + file));
		
		// get serialisation
		Model serialised  = getSerialisedModel(original);
		
		// get diff
		Model diff = ModelFactory.createDefaultModel();
		diff.add(serialised).remove(original);

		// custom removal of axioms added in parsing:
		// object maps are not added in reasoning step due to a disjunction class range
		diff.remove(diff.listStatements(null, RDF.type, ResourceFactory.createResource(R2RMLVocabulary.TYPE_OBJECT_MAP)));
		// Term maps are not added in reasoning step
		diff.remove(diff.listStatements(null, ResourceFactory.createProperty(R2RMLVocabulary.PROP_TERM_TYPE), (RDFNode)null));
		
		boolean test = diff.size() == 0;
		if (! test) {
			System.out.println(file);
			diff.write(System.out, FileUtils.langTurtle);
		}
		assertEquals(true, test);
	}

	@Test public void test12 () throws InvalidR2RMLMappingException {
		test("test12.ttl");
	}
	@Test public void test13 () throws InvalidR2RMLMappingException {
		test("test13.ttl");
	}
	@Test public void test14 () throws InvalidR2RMLMappingException {
		test("test14.ttl");
	}
	@Test public void test15 () throws InvalidR2RMLMappingException {
		test("test15.ttl");
	}
	@Test public void test16 () throws InvalidR2RMLMappingException {
		test("test16.ttl");
	}
	@Test public void test17 () throws InvalidR2RMLMappingException {
		test("test17.ttl");
	}
	@Test public void test18 () throws InvalidR2RMLMappingException {
		test("test18.ttl");
	}
	@Test public void test19 () throws InvalidR2RMLMappingException {
		test("test19.ttl");
	}
	@Test public void test2 () throws InvalidR2RMLMappingException {
		test("test2.ttl");
	}
	@Test public void test20 () throws InvalidR2RMLMappingException {
		test("test20.ttl");
	}
	@Test public void test21 () throws InvalidR2RMLMappingException {
		test("test21.rdf");
	}
	@Test public void test22 () throws InvalidR2RMLMappingException {
		test("test22.rdf");
	}
	@Test public void test23 () throws InvalidR2RMLMappingException {
		test("test23.rdf");
	}
	@Test public void test24 () throws InvalidR2RMLMappingException {
		test("test24.ttl");
	}
	@Test public void test25 () throws InvalidR2RMLMappingException {
		test("test25.ttl");
	}
	@Test public void test26 () throws InvalidR2RMLMappingException {
		test("test26.ttl");
	}
	@Test public void test27 () throws InvalidR2RMLMappingException {
		test("test27.ttl");
	}
	@Test public void test28 () throws InvalidR2RMLMappingException {
		test("test28.ttl");
	}
	@Test public void test29 () throws InvalidR2RMLMappingException {
		test("test29.ttl");
	}
	@Test public void test3 () throws InvalidR2RMLMappingException {
		test("test3.ttl");
	}
	@Test public void test30 () throws InvalidR2RMLMappingException {
		test("test30.ttl");
	}
	@Test public void test4 () throws InvalidR2RMLMappingException {
		test("test4.ttl");
	}
	@Test public void test5 () throws InvalidR2RMLMappingException {
		test("test5.ttl");
	}
	@Test public void test6 () throws InvalidR2RMLMappingException {
		test("test6.ttl");
	}
	@Test public void test7 () throws InvalidR2RMLMappingException {
		test("test7.ttl");
	}
	@Test public void test8 () throws InvalidR2RMLMappingException {
		test("test8.ttl");
	}
	@Test public void test9 () throws InvalidR2RMLMappingException {
		test("test9.ttl");
	}
	@Test public void test10 () throws InvalidR2RMLMappingException {
		test("test10.ttl");
	}

}
