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

import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;


import org.junit.Test;

import eu.optique.api.mapping.LogicalTable;
import eu.optique.api.mapping.PredicateObjectMap;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.impl.owlapi.OWLAPIR2RMLMappingManagerFactory;
import eu.optique.api.mapping.SubjectMap;
import eu.optique.api.mapping.TriplesMap;
import eu.optique.api.mapping.impl.owlapi.OWLAPIUtil;
import org.semanticweb.owlapi.io.RDFTriple;

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
		
		R2RMLMappingManager mm = new OWLAPIR2RMLMappingManagerFactory().getR2RMLMappingManager();

		Set<RDFTriple> s = OWLAPIUtil.readTurtle(fis);
		Collection<TriplesMap> coll = mm.importMappings(s);
		
		Assert.assertTrue(coll.size()==2);
		
		Iterator<TriplesMap> i=coll.iterator();
		TriplesMap first=i.next();
		TriplesMap second=i.next();
		
		Assert.assertTrue(coll.contains(first)); //the same objects
		Assert.assertTrue(coll.contains(second));

                // The following tests are invalid since they compare object identities
		Set<LogicalTable> slt=new HashSet<LogicalTable>();
		slt.add(first.getLogicalTable());
		// Assert.assertTrue(slt.contains(second.getLogicalTable())); //different objects with the same characteristics
		
		Set<SubjectMap> ssm=new HashSet<SubjectMap>();
		ssm.add(first.getSubjectMap());
		// Assert.assertTrue(ssm.contains(second.getSubjectMap()));
		
		Set<PredicateObjectMap> spom=new HashSet<PredicateObjectMap>();
		spom.add(first.getPredicateObjectMap(0));
		// Assert.assertTrue(spom.contains(second.getPredicateObjectMap(0)));
		
		//the hashCode and the equals method should not take into account the resource element!
			
	}

	
}
