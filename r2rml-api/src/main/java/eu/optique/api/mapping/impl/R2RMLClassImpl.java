package eu.optique.api.mapping.impl;

import eu.optique.api.mapping.R2RMLClass;
import org.apache.commons.rdf.api.BlankNodeOrIRI;

import static java.util.Objects.requireNonNull;

/**
 * @author xiao
 */
public abstract class R2RMLClassImpl implements R2RMLClass {

    BlankNodeOrIRI res;

    @Override
    public void setNode(BlankNodeOrIRI node) {
        this.res = requireNonNull(node);
    }

    @Override
    public BlankNodeOrIRI getNode() {
        return res;
    }
}
