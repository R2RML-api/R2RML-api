package eu.optique.api.mapping.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import org.coode.owlapi.rdf.model.RDFLiteralNode;
import org.coode.owlapi.rdf.model.RDFResourceNode;
import org.coode.owlapi.rdf.model.RDFTriple;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.rdf.syntax.RDFConsumer;
import org.semanticweb.owlapi.rdf.syntax.RDFParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import uk.ac.manchester.cs.owl.owlapi.turtle.parser.ParseException;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.TripleHandler;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.TurtleParser;

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
			os.write(tr.getProperty().toString().getBytes());
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
	 * @throws ParseException
	 */
	public static Set<RDFTriple> readTurtle(InputStream is)
			throws ParseException {

		Set<RDFTriple> s = new HashSet<RDFTriple>();

		TurtleParser tp = new TurtleParser(is, new RDFConsAdapter(s), IRI.create("").toString());
		tp.parseDocument();

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
	 */
	public static Set<RDFTriple> readRDFXML(InputSource is)
			throws SAXException, IOException {

		Set<RDFTriple> s = new HashSet<RDFTriple>();

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
		public void addModelAttribte(String key, String value)
				throws SAXException {

			// Do nothing.

		}

		@Override
		public void endModel() throws SAXException {

			// Do nothing.

		}

		@Override
		public void includeModel(String logicalURI, String physicalURI)
				throws SAXException {

			// Do nothing.

		}

		@Override
		public void logicalURI(String logicalURI) throws SAXException {

			// Do nothing.

		}

		@Override
		public void startModel(String physicalURI) throws SAXException {

			// Do nothing.

		}

		@Override
		public void statementWithLiteralValue(String subject, String predicate,
				String object, String language, String datatype)
				throws SAXException {

			RDFResourceNode s = new RDFResourceNode(IRI.create(subject));
			RDFResourceNode p = new RDFResourceNode(IRI.create(predicate));
			RDFLiteralNode o = new RDFLiteralNode(object, (IRI) null);

			RDFTriple triple = new RDFTriple(s, p, o);
			read.add(triple);

		}

		@Override
		public void statementWithResourceValue(String subject,
				String predicate, String object) throws SAXException {

			RDFResourceNode s = new RDFResourceNode(IRI.create(subject));
			RDFResourceNode p = new RDFResourceNode(IRI.create(predicate));
			RDFResourceNode o = new RDFResourceNode(IRI.create(object));

			RDFTriple triple = new RDFTriple(s, p, o);
			read.add(triple);

		}

		@Override
		public void handleBaseDirective(String base) {
			// Do nothing
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
		public void handleTriple(IRI subject, IRI predicate, IRI object) {

			try {
				statementWithResourceValue(subject.toString(),
						predicate.toString(), object.toString());
			} catch (SAXException e) {
				// Will never happen.
				e.printStackTrace();
			}

		}

		@Override
		public void handleTriple(IRI subject, IRI predicate, String object) {
			try {
				statementWithLiteralValue(subject.toString(),
						predicate.toString(), object, null, null);
			} catch (SAXException e) {
				// Will never happen.
				e.printStackTrace();
			}

		}

		@Override
		public void handleTriple(IRI subject, IRI predicate, String object,
				String lang) {
			try {
				statementWithLiteralValue(subject.toString(),
						predicate.toString(), object, lang, null);
			} catch (SAXException e) {
				// Will never happen.
				e.printStackTrace();
			}

		}

		@Override
		public void handleTriple(IRI subject, IRI predicate, String object,
				IRI datatype) {
			try {
				statementWithLiteralValue(subject.toString(),
						predicate.toString(), object, null, datatype.toString());
			} catch (SAXException e) {
				// Will never happen.
				e.printStackTrace();
			}

		}

	}

}
