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

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mindswap.pellet.jena.PelletReasonerFactory;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.FileUtils;
import org.apache.jena.vocabulary.RDF;

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

@RunWith(Parameterized.class)
public class SerialisationTest {

	private final static String mappingFilesDir = "src/test/resources/mappingFiles/";
	private final String r2rmlURL = "http://www.w3.org/ns/r2rml";
	private String mappingFile;

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
		Model original    = getOntModel(readModel(file));

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

	@Parameters (name = "file: {0}")
	public static List<String[]> listMappingFiles () {
		List<String[]> parameters = new LinkedList<>();
		File directory = new File(mappingFilesDir);
		for (File file : directory.listFiles()){
			if (file.isFile()){
				parameters.add(new String[] {file.toString()});
			}
		}
		return parameters;
	}

	public SerialisationTest (String mappingFile) {
		this.mappingFile = mappingFile;
	}

	@Ignore("https://github.com/R2RML-api/R2RML-api/issues/4") 
	@Test public void test () throws InvalidR2RMLMappingException {
		test(mappingFile);
	}
}
