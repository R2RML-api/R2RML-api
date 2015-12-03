package org.semanticweb.owlapi.rdf.turtle.parser;

import org.semanticweb.owlapi.model.IRI;

import java.io.InputStream;

/**
 * In OWLAPI v4, the scope of TurtleParser has been changed to package.
 * We here use this hacky class to access it
 *
 */
public class OWLAPIInternalTurtleParser extends TurtleParser {
    public OWLAPIInternalTurtleParser(InputStream is, TripleHandler handler, IRI base) {
        super(is, handler, base);
    }

    public void parse(){
        super.parseDocument();
    }
}
