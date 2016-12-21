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

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.URIImpl;
import org.eclipse.rdf4j.model.impl.ValueFactoryImpl;

import eu.optique.api.mapping.MappingFactory;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.SubjectMap;
import eu.optique.api.mapping.Template;
import eu.optique.api.mapping.impl.R2RMLVocabulary;
import eu.optique.api.mapping.impl.rdf4j.SesameR2RMLMappingManagerFactory;

public class SerializeSubMapTest {

	@Test
	public void test(){

		R2RMLMappingManager mm = new SesameR2RMLMappingManagerFactory().getR2RMLMappingManager();
		MappingFactory mfact = mm.getMappingFactory();
		ValueFactory myFactory = ValueFactoryImpl.getInstance();
		
		//SubjectMap
		String subMapURI = "http://data.example.com/resource/subject";
		Resource subRes = new URIImpl(subMapURI);
		Template templs =  mfact.createTemplate("http://data.example.com/employee/{EMPNO}");
		SubjectMap sm =  mfact.createSubjectMap(templs);
		sm.setResource(subRes);

		Set<Statement> stmts = sm.serialize(Statement.class);
		for(Statement stmt : stmts){
			if(myFactory.createURI(R2RMLVocabulary.PROP_TEMPLATE).equals(stmt.getPredicate())){
				
				Assert.assertTrue(stmt.getObject().stringValue().contains("{EMPNO}"));
			}
		}
	}
}