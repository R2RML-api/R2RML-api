package eu.optique.api.mapping.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;


import org.semanticweb.owlapi.io.RDFLiteral;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFResourceBlankNode;
import org.semanticweb.owlapi.io.RDFResourceIRI;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.IRI;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFConsumer;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFParser;
import org.semanticweb.owlapi.rdf.turtle.parser.OWLAPIInternalTurtleParser;
import org.semanticweb.owlapi.rdf.turtle.parser.TripleHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.annotation.Nonnull;


/**
 * Utility class for parsing and writing OWL API RDFTriples to a stream.
 * 
 * @author Marius Strandhaug
 */
public class OWLAPIUtil {

	/**
	 * A very simple RDF Turtle writer.
	 * 
	 * @param os
	 *            The outputstream to write to.
	 * @param s
	 *            The set of RDFTriples that will be written.
	 * @throws IOException
	 */
	public static void writeTurtle(OutputStream os, Set<RDFTriple> s)
			throws IOException {

		for (RDFTriple tr : s) {
			os.write(tr.getSubject().toString().getBytes());
			os.write(" ".getBytes());
			os.write(tr.getPredicate().toString().getBytes());
			os.write(" ".getBytes());

			// Strip down to only one < and > on each side, if present.
			String o = tr.getObject().toString();
				while (o.startsWith("<<"))
					o = o.substring(1);
				while (o.endsWith(">>"))
					o = o.substring(0, o.length() - 1);

			os.write(o.getBytes());
			os.write(" .\n".getBytes());
		}

		os.close();

	}

	/**
	 * A Turtle parser.
	 * 
	 * @param is
	 *            The InputStream to read from.
	 * @return The set of RDFTriples that have been read.
	 */
	public static Set<RDFTriple> readTurtle(InputStream is)
            throws OWLOntologyCreationException, IOException {


//        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
//
//        OWLOntology ontology = manager.createOntology();
//
//        TurtleOntologyParserFactory f = new TurtleOntologyParserFactory();
//        OWLParser parser = f.createParser();
//
//        parser.parse(new StreamDocumentSource(is), ontology, new OWLOntologyLoaderConfiguration());
//
//
//        RDFTranslator rdfTranslator = new RDFTranslator(manager,ontology,false,
//                new OWLAnonymousIndividualsWithMultipleOccurrences()
//        );
//
//        rdfTranslator.visit(ontology);
//
//        for (OWLAxiom axiom : ontology.getAxioms()) {
//            axiom.accept(rdfTranslator);
//        }
//
//
//        ontology.accept(rdfTranslator);
//
//        Set<RDFTriple> s = rdfTranslator.getGraph().getAllTriples();
//		TurtleParser tp = new TurtleParser(is, new RDFConsAdapter(s), IRI.create("").toString());
//		tp.parseDocument();

        Set<RDFTriple> s = new HashSet<>();

        IRI base = IRI.create("http://example.com/base/");

        OWLAPIInternalTurtleParser tp = new OWLAPIInternalTurtleParser(is, new RDFConsAdapter(s), base);
        tp.parse();

        return s;


	}

	/**
	 * An RDF/XML parser.
	 * 
	 * @param is
	 *            The InputStream to read from.
	 * @return The set of RDFTriples that have been read.
	 * @throws SAXException
	 * @throws IOException
     *
     * @deprecated only TURTLE is the standard for the syntax of R2RML mappings
	 */
    @Deprecated
	public static Set<RDFTriple> readRDFXML(InputSource is)
			throws SAXException, IOException {

		Set<RDFTriple> s = new HashSet<>();

		RDFParser r = new RDFParser();
		r.parse(is, new RDFConsAdapter(s));

		return s;

	}

	private static class RDFConsAdapter implements RDFConsumer, TripleHandler {

		Set<RDFTriple> read;

		RDFConsAdapter(Set<RDFTriple> s) {
			read = s;
		}

        @Override
        public void startModel(@Nonnull IRI physicalURI) {
            // Do nothing.
        }

        @Override
		public void endModel() {

			// Do nothing.

		}

		@Override
		public void includeModel(String logicalURI, String physicalURI) {

			// Do nothing.

		}

        @Nonnull
        @Override
        public IRI remapIRI(@Nonnull IRI i) {
            return null;
        }

        @Nonnull
        @Override
        public String remapOnlyIfRemapped(@Nonnull String i) {
            return null;
        }

        @Override
        public void addPrefix(String abbreviation, String value) {

        }


		@Override
		public void statementWithLiteralValue(String subject, String predicate,
				String object, String language, String datatype) {

			RDFResource s = getRDFResourceFromString(subject);
			RDFResourceIRI p = new RDFResourceIRI(IRI.create(predicate));

            IRI datatypeIRI = null;
            if(datatype != null){
                datatypeIRI = IRI.create(datatype);
            }

			RDFLiteral o = new RDFLiteral(object, language, datatypeIRI);

			RDFTriple triple = new RDFTriple(s, p, o);
			read.add(triple);

		}

        private RDFResource getRDFResourceFromString(String subject) {
            if(subject.startsWith("_:")){
                return new RDFResourceBlankNode(IRI.create(subject), true, true);
            }else {
                return new RDFResourceIRI(IRI.create(subject));
            }
        }

        @Override
        public void statementWithLiteralValue(@Nonnull IRI subject, @Nonnull IRI predicate, @Nonnull String object, String language, IRI datatype) {
            RDFResource s = new RDFResourceIRI(subject);
            RDFResourceIRI p = new RDFResourceIRI(predicate);
            RDFLiteral o = new RDFLiteral(object, language, datatype);

            RDFTriple triple = new RDFTriple(s, p, o);
            read.add(triple);
        }

        @Override
        public void logicalURI(@Nonnull IRI logicalURI) {

        }

        @Override
		public void statementWithResourceValue(String subject,
				String predicate, String object) {

			RDFResource s = getRDFResourceFromString(subject);
			RDFResourceIRI p = new RDFResourceIRI(IRI.create(predicate));
			RDFResource o = getRDFResourceFromString(object);

			RDFTriple triple = new RDFTriple(s, p, o);
			read.add(triple);

		}

        @Override
        public void statementWithResourceValue(@Nonnull IRI subject, @Nonnull IRI predicate, @Nonnull IRI object) {

        }


		@Override
		public void handleComment(String comment) {
			// Do nothing
		}

		@Override
		public void handleEnd() {
			// Do nothing
		}

		@Override
		public void handlePrefixDirective(String prefixName, String prefix) {
			// Do nothing
		}

        @Override
        public void handleBaseDirective(IRI base) {

        }

        @Override
		public void handleTriple(IRI subject, IRI predicate, IRI object) {
            statementWithResourceValue(subject.toString(), predicate.toString(), object.toString());
        }

		@Override
		public void handleTriple(IRI subject, IRI predicate, String object) {
            statementWithLiteralValue(subject.toString(), predicate.toString(), object, /*lang*/ "", null);

        }

		@Override
		public void handleTriple(IRI subject, IRI predicate, String object, String lang) {
            statementWithLiteralValue(subject.toString(), predicate.toString(), object, lang, null);

        }

		@Override
		public void handleTriple(IRI subject, IRI predicate, String object,
				IRI datatype) {
            statementWithLiteralValue(subject.toString(), predicate.toString(), object, /*lang*/ "", datatype.toString());
        }

	}

}
