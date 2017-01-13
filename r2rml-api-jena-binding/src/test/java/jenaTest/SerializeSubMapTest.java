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

import java.util.Set;

import eu.optique.r2rml.api.binding.jena.JenaR2RMLMappingManager;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.junit.Assert;

import org.junit.Test;

import org.apache.jena.rdf.model.ResourceFactory;

import eu.optique.r2rml.api.MappingFactory;
import eu.optique.r2rml.api.model.SubjectMap;
import eu.optique.r2rml.api.model.Template;
import eu.optique.r2rml.api.model.R2RMLVocabulary;

public class SerializeSubMapTest {

	@Test
	public void test(){

		JenaR2RMLMappingManager mm = JenaR2RMLMappingManager.getInstance();
		MappingFactory mfact = mm.getMappingFactory();

		JenaRDF jena = new JenaRDF();

		//SubjectMap
		String subMapURI = "http://data.example.com/resource/subject";
		Node subRes = NodeFactory.createURI(subMapURI);
		Template templs =  mfact.createTemplate("http://data.example.com/employee/{EMPNO}");
		SubjectMap sm =  mfact.createSubjectMap(templs);
		sm.setNode((BlankNodeOrIRI) jena.asRDFTerm(subRes));

		Set<Triple> stmts = sm.serialize();
		for(Triple stmt : stmts){
			if(ResourceFactory.createResource(R2RMLVocabulary.PROP_TEMPLATE).equals(stmt.getPredicate())){
				
				Assert.assertTrue(stmt.getObject().toString().contains("{EMPNO}"));
			}
		}
	}
}
