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

import java.util.Set;

import org.junit.Assert;


import org.junit.Test;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFResourceIRI;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.IRI;

import eu.optique.api.mapping.MappingFactory;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.impl.owlapi.OWLAPIR2RMLMappingManagerFactory;
import eu.optique.api.mapping.SubjectMap;
import eu.optique.api.mapping.Template;
import eu.optique.api.mapping.impl.R2RMLVocabulary;

public class SerializeSubMapTest {

	@Test
	public void test(){
		
		R2RMLMappingManager mm = new OWLAPIR2RMLMappingManagerFactory().getR2RMLMappingManager();
		MappingFactory mfact = mm.getMappingFactory();
		
		//SubjectMap
		String subMapURI = "http://data.example.com/resource/subject";
		RDFResource subRes = new RDFResourceIRI(IRI.create(subMapURI));
		Template templs = mfact.createTemplate("http://data.example.com/employee/{EMPNO}");
		SubjectMap sm = mfact.createSubjectMap(templs);
		sm.setResource(subRes);
		Set<RDFTriple> triples = sm.serialize(RDFTriple.class);
		
		for(RDFTriple tr : triples){
			if(IRI.create(R2RMLVocabulary.PROP_TEMPLATE).equals(tr.getPredicate().getIRI())){
				Assert.assertTrue(tr.getObject().toString().contains("{EMPNO}"));
			}				
		}
	}
}