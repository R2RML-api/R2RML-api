package eu.optique.r2rml.api.model.impl;

import eu.optique.r2rml.api.model.*;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;

import java.util.*;

/**
 * An implementation of a SubjectMap containing an embedded triple.
 *
 * @author Marius Strandhaug
 * @author Lukas Sundqvist
 */
public class RDFStarSubjectMapImpl extends RDFStarTermMapImpl implements SubjectMap {

    private ArrayList<IRI> classList;

    private ArrayList<GraphMap> graphList;

    RDFStarSubjectMapImpl(RDF c, ObjectMap subject, PredicateMap predicate, ObjectMap object) {
        super(c, subject, predicate, object);
        
        classList = new ArrayList<>();
        graphList = new ArrayList<>();
    }

    @Override
    public void addClass(IRI classURI) {
        classList.add(classURI);
    }

    @Override
    public void addGraphMap(GraphMap gm) {
        graphList.add(gm);
    }

    @Override
    public void addGraphMap(List<GraphMap> gms) {
        graphList.addAll(gms);
    }

    @Override
    public IRI getClass(int index) {
        return classList.get(index);
    }

    @Override
    public GraphMap getGraphMap(int index) {
        return graphList.get(index);
    }

    @Override
    public List<IRI> getClasses() {
        return Collections.unmodifiableList(classList);
    }

    @Override
    public List<GraphMap> getGraphMaps() {
        return Collections.unmodifiableList(graphList);
    }

    @Override
    public void removeClass(IRI classURI) {
        classList.remove(classURI);
    }

    @Override
    public void removeGraphMap(GraphMap gm) {
        graphList.remove(gm);
    }

    @Override
    public Set<Triple> serialize() {
        Set<Triple> stmtSet = new HashSet<Triple>();

        stmtSet.addAll(super.serialize());

        stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), getRDF().createIRI(R2RMLVocabulary.TYPE_SUBJECT_MAP)));


        for (IRI cl : classList) {
            stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI(R2RMLVocabulary.PROP_CLASS), cl));
        }

        for(GraphMap g : graphList){
            if(g.getTermMapType() == TermMap.TermMapType.CONSTANT_VALUED){
                // Use constant shortcut property.
                stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI(R2RMLVocabulary.PROP_GRAPH), g.getConstant()));
            }else{
                stmtSet.add(getRDF().createTriple(getNode(), getRDF().createIRI(R2RMLVocabulary.PROP_GRAPH_MAP), g.getNode()));
                stmtSet.addAll(g.serialize());
            }
        }

        stmtSet.addAll(subject.serialize());
        stmtSet.addAll(predicate.serialize());
        stmtSet.addAll(object.serialize());

        return stmtSet;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((classList == null) ? 0 : classList.hashCode());
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!super.equals(obj))
            return false;

        if (!(obj instanceof RDFStarSubjectMapImpl))
            return false;

        RDFStarSubjectMapImpl other = (RDFStarSubjectMapImpl) obj;
        if (classList == null) {
            if (other.classList != null) {
                return false;
            }
        } else if (!classList.equals(other.classList) || !subject.equals(other.subject) || !predicate.equals(other.predicate) || !object.equals(other.object)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RDFStarSubjectMapImpl [classList=" + classList + ", graphList=" +
                graphList + ", termMapType=" + termMapType + ", termTypeIRI="+ termTypeIRI +
                ", node=" + getNode() + "subject=(" + subject.toString() + "), predicate=(" +
                predicate.toString() + "), object=(" + object.toString() + ")] ";
    }

}
