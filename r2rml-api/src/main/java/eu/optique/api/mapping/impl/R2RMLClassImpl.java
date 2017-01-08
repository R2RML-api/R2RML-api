package eu.optique.api.mapping.impl;

import eu.optique.api.mapping.R2RMLClass;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.RDF;

import static java.util.Objects.requireNonNull;

/**
 * @author xiao
 */
public abstract class R2RMLClassImpl implements R2RMLClass {

    private final RDF rdf;
    BlankNodeOrIRI res;

    @Override
    public void setNode(BlankNodeOrIRI node) {
        this.res = requireNonNull(node);
    }

    @Override
    public BlankNodeOrIRI getNode() {
        return res;
    }

    R2RMLClassImpl(RDF rdf){
        this.rdf = rdf;
    }

    protected RDF getRDF() { return rdf; }
}
