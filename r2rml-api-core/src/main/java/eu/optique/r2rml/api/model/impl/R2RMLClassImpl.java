package eu.optique.r2rml.api.model.impl;

import eu.optique.r2rml.api.model.R2RMLClass;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.RDF;

import static java.util.Objects.requireNonNull;

/**
 * @author xiao
 */
public abstract class R2RMLClassImpl implements R2RMLClass {

    private final RDF rdf;

    protected  BlankNodeOrIRI node;

    @Override
    public void setNode(BlankNodeOrIRI node) {
        this.node = requireNonNull(node);
    }

    @Override
    public BlankNodeOrIRI getNode() {
        return node;
    }

    R2RMLClassImpl(RDF rdf){
        this.rdf = rdf;
    }

    protected RDF getRDF() { return rdf; }
}
