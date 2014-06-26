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

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.openrdf.model.Model;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.StatementCollector;

import eu.optique.api.mapping.LogicalTable;
import eu.optique.api.mapping.PredicateObjectMap;
import eu.optique.api.mapping.R2RMLMappingManager;
import eu.optique.api.mapping.R2RMLMappingManagerFactory;
import eu.optique.api.mapping.SubjectMap;
import eu.optique.api.mapping.TriplesMap;

/**
 * JUnit Test Cases
 * 
 * @author Riccardo Mancini
 */
public class Contains_Test
{
	@Test
	public void test() throws Exception{
		FileInputStream fis = new FileInputStream(new File("test/mappingFiles/test30.ttl"));
		
		R2RMLMappingManager mm = R2RMLMappingManagerFactory.getSesameMappingManager();
		
		// Read the file into a model.
		RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);
		Model m = new LinkedHashModel();
		rdfParser.setRDFHandler(new StatementCollector(m));
		rdfParser.parse(fis, "testMapping");
		
		Collection<TriplesMap> coll = mm.importMappings(m);
		
		Assert.assertTrue(coll.size()==2);
		
		Iterator<TriplesMap> i=coll.iterator();
		TriplesMap first=i.next();
		TriplesMap second=i.next();
		
		Assert.assertTrue(coll.contains(first)); //the same objects
		Assert.assertTrue(coll.contains(second));
		
		Set<LogicalTable> slt=new HashSet<LogicalTable>();
		slt.add(first.getLogicalTable());
		Assert.assertTrue(slt.contains(second.getLogicalTable())); //different objects with the same characteristics
		
		Set<SubjectMap> ssm=new HashSet<SubjectMap>();
		ssm.add(first.getSubjectMap());
		Assert.assertTrue(ssm.contains(second.getSubjectMap()));
		
		Set<PredicateObjectMap> spom=new HashSet<PredicateObjectMap>();
		spom.add(first.getPredicateObjectMap(0));
		Assert.assertTrue(spom.contains(second.getPredicateObjectMap(0)));
		
		//the hashCode and the equals method should not take into account the resource element!
			
	}

	
}
