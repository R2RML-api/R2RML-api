package rdf4jTest;

import eu.optique.r2rml.api.binding.rdf4j.RDF4JR2RMLMappingManager;
import eu.optique.r2rml.api.model.*;
import eu.optique.r2rml.api.model.impl.SQLBaseTableOrViewImpl;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

/**
 * JUnit Test Cases for RDFStar support
 * 
 * @author Lukas Sundqvist
 */
public class RDFStar_Test
{
	
	@Test
	public void test1() throws Exception{
		
		InputStream fis = getClass().getResourceAsStream("../mappingFiles/prof-rdfstar.ttl");
		
		RDF4JR2RMLMappingManager mm = RDF4JR2RMLMappingManager.getInstance();
		
		// Read the file into a model.
		RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);
		Model m = new LinkedHashModel();
		rdfParser.setRDFHandler(new StatementCollector(m));
		rdfParser.parse(fis, "testMapping");
		
		Collection<TriplesMap> coll = mm.importMappings(m);

		Assert.assertEquals(2, coll.size());
		Assert.assertTrue(coll.stream()
				.anyMatch(triplesMap -> triplesMap.getSubjectMap() instanceof RDFStarTermMap));
		Assert.assertTrue(coll.stream()
				.flatMap(triplesMap -> triplesMap.getPredicateObjectMaps().stream()
						.flatMap(predicateObjectMap -> predicateObjectMap.getObjectMaps().stream()))
				.anyMatch(objectMap -> objectMap instanceof RDFStarTermMap));
	}
}
