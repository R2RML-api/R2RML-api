package org.semanticweb.owlapi.rdf.turtle.parser;

import org.semanticweb.owlapi.model.IRI;

import java.io.InputStream;

/**
 * In OWLAPI v4, the scope of TurtleParser has been changed to package, we here use the trick to use it
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
