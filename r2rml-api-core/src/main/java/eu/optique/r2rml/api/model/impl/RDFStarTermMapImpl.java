package eu.optique.r2rml.api.model.impl;

import eu.optique.r2rml.api.model.*;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;

import java.util.Collections;
import java.util.List;

public abstract class RDFStarTermMapImpl extends TermMapImpl implements RDFStarTermMap {

    private List<IRI> validTermTypes = Collections.singletonList(
            getRDF().createIRI(RDFStarVocabulary.TERM_STAR_TRIPLE));

    ObjectMap subject;
    PredicateMap predicate;
    ObjectMap object;

    RDFStarTermMapImpl(RDF rdf, ObjectMap subject, PredicateMap predicate, ObjectMap object) {
        super(rdf);
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    @Override
    public ObjectMap getSubject() {
        return subject;
    }

    @Override
    public PredicateMap getPredicate() {
        return predicate;
    }

    @Override
    public ObjectMap getObject() {
        return object;
    }

    @Override
    public void setDefaultTermType() {
        termTypeIRI = getRDF().createIRI(RDFStarVocabulary.TERM_STAR_TRIPLE);
    }


    @Override
    public List<IRI> getValidTermTypes() {
        return validTermTypes;
    }
}
