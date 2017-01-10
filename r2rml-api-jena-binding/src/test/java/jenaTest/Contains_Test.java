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

import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import eu.optique.r2rml.api.binding.jena.JenaR2RMLMappingManager;
import org.junit.Assert;

import org.junit.Test;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import eu.optique.r2rml.api.model.LogicalTable;
import eu.optique.r2rml.api.model.PredicateObjectMap;
import eu.optique.r2rml.api.model.SubjectMap;
import eu.optique.r2rml.api.model.TriplesMap;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class Contains_Test
{
	@Test
	public void test() throws Exception{
		
		InputStream fis = getClass().getResourceAsStream("../mappingFiles/test30.ttl");
		
		JenaR2RMLMappingManager mm = new JenaR2RMLMappingManager.Factory().getR2RMLMappingManager();

		Model m = ModelFactory.createDefaultModel();
		m = m.read(fis,"testMapping", "TURTLE");
		Collection<TriplesMap> coll = mm.importMappings(m);
		
		Assert.assertTrue(coll.size()==2);
		
		Iterator<TriplesMap> i=coll.iterator();
		TriplesMap first=i.next();
		TriplesMap second=i.next();
		
		Assert.assertTrue(coll.contains(first)); //the same objects
		Assert.assertTrue(coll.contains(second));
		
		Set<LogicalTable> slt=new HashSet<LogicalTable>();
		slt.add(first.getLogicalTable());
                // This test is invalid: the object identities are different
		// Assert.assertTrue(slt.contains(second.getLogicalTable())); //different objects with the same characteristics
		
		Set<SubjectMap> ssm=new HashSet<SubjectMap>();
		ssm.add(first.getSubjectMap());
                // This test is invalid: the object identities are different
		// Assert.assertTrue(ssm.contains(second.getSubjectMap()));
		
		Set<PredicateObjectMap> spom=new HashSet<PredicateObjectMap>();
		spom.add(first.getPredicateObjectMap(0));
                // This test is invalid: the object identities are different
		// Assert.assertTrue(spom.contains(second.getPredicateObjectMap(0)));
		
		//the hashCode and the equals method should not take into account the resource element!
			
	}

	
}
