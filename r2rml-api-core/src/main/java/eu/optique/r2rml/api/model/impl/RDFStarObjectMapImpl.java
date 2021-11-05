package eu.optique.r2rml.api.model.impl;

import eu.optique.r2rml.api.model.ObjectMap;
import eu.optique.r2rml.api.model.PredicateMap;
import eu.optique.r2rml.api.model.R2RMLVocabulary;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;

import java.util.HashSet;
import java.util.Set;

/**
 * An implementation of an ObjectMap containing an embedded triple.
 *
 * @author Lukas Sundqvist
 */
public class RDFStarObjectMapImpl extends RDFStarTermMapImpl implements ObjectMap {

    RDFStarObjectMapImpl(RDF rdf, ObjectMap subject, PredicateMap predicate, ObjectMap object) {
        super(rdf, subject, predicate, object);
    }

    @Override
    public void setLanguageTag(String lang) {
        throw new IllegalStateException("The TermMapType is " + this.termMapType +
                ", which does not support language tags.");
    }

    @Override
    public void setDatatype(IRI datatypeURI) {
        throw new IllegalStateException("The TermMapType is " + this.termMapType +
                ", which does not support data types.");
    }

    @Override
    public String getLanguageTag() {
        return null;
    }

    @Override
    public IRI getDatatype() {
        return null;
    }

    @Override
    public void removeDatatype() {

    }

    @Override
    public void removeLanguageTag() {

    }

    @Override
    public Set<Triple> serialize() {
        Set<Triple> stmtSet = new HashSet<>();

        stmtSet.addAll(super.serialize());

        stmtSet.add(getRDF().createTriple(getNode(),
                getRDF().createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
                getRDF().createIRI(R2RMLVocabulary.TYPE_OBJECT_MAP)));

        stmtSet.addAll(subject.serialize());
        stmtSet.addAll(predicate.serialize());
        stmtSet.addAll(object.serialize());

        return stmtSet;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + ((predicate == null) ? 0 : predicate.hashCode());
        result = prime * result + ((object == null) ? 0 : object.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!super.equals(obj))
            return false;

        if (!(obj instanceof RDFStarObjectMapImpl))
            return false;

        RDFStarObjectMapImpl other = (RDFStarObjectMapImpl) obj;
        if (!subject.equals(other.subject) || !predicate.equals(other.predicate) || !object.equals(other.object)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "RDFStarObjectMapImpl [termMapType=" + termMapType + ", termTypeIRI="+ termTypeIRI +
                ", node=" + getNode() + "subject=(" + subject.toString() + "), predicate=(" +
                predicate.toString() + "), object=(" + object.toString() + ")] ";
    }
}
