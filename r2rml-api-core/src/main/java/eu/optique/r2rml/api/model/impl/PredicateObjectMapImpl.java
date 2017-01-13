/*******************************************************************************
 * Copyright 2013, the Optique Consortium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This first version of the R2RML API was developed jointly at the University of Oslo, 
 * the University of Bolzano, La Sapienza University of Rome, and fluid Operations AG, 
 * as part of the Optique project, www.optique-project.eu
 ******************************************************************************/
package eu.optique.r2rml.api.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eu.optique.r2rml.api.model.GraphMap;
import eu.optique.r2rml.api.model.ObjectMap;
import eu.optique.r2rml.api.model.PredicateMap;
import eu.optique.r2rml.api.model.PredicateObjectMap;
import eu.optique.r2rml.api.model.R2RMLVocabulary;
import eu.optique.r2rml.api.model.RefObjectMap;
import eu.optique.r2rml.api.model.TermMap;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;

/**
 * An implementation of a PredicateObjectMap.
 *
 * @author Marius Strandhaug
 */
public class PredicateObjectMapImpl extends MappingComponentImpl implements PredicateObjectMap {

    private ArrayList<PredicateMap> predList;

    private ArrayList<ObjectMap> objList;

    private ArrayList<RefObjectMap> refObjList;

    private ArrayList<GraphMap> graphList;

    PredicateObjectMapImpl(RDF rdf, PredicateMap pm, ObjectMap om) {

        super(rdf);
        predList = new ArrayList<PredicateMap>();
        objList = new ArrayList<ObjectMap>();
        refObjList = new ArrayList<RefObjectMap>();
        graphList = new ArrayList<GraphMap>();

        if (pm == null) {
            throw new NullPointerException(
                    "A PredicateObjectMap must have a PredicateMap.");
        } else if (om == null) {
            throw new NullPointerException(
                    "A PredicateObjectMap must have an ObjectMap or a RefObjectMap.");
        } else {
            addPredicateMap(pm);
            addObjectMap(om);

            setNode(getRDF().createBlankNode());
        }
    }

    PredicateObjectMapImpl(RDF rdf, PredicateMap pm, RefObjectMap rom) {
        super(rdf);

        predList = new ArrayList<>();
        objList = new ArrayList<>();
        refObjList = new ArrayList<>();
        graphList = new ArrayList<>();

        if (pm == null) {
            throw new NullPointerException(
                    "A PredicateObjectMap must have at least one PredicateMap.");
        } else if (rom == null) {
            throw new NullPointerException(
                    "A PredicateObjectMap must have an ObjectMap or a RefObjectMap.");
        } else {
            addPredicateMap(pm);
            addRefObjectMap(rom);

            setNode(getRDF().createBlankNode());
        }
    }

    public PredicateObjectMapImpl(RDF rdf, List<PredicateMap> pms, List<ObjectMap> oms, List<RefObjectMap> roms) {

        super(rdf);

        predList = new ArrayList<>();
        objList = new ArrayList<>();
        refObjList = new ArrayList<>();
        graphList = new ArrayList<>();

        if (pms == null) {
            throw new NullPointerException(
                    "A PredicateObjectMap must have at least one PredicateMap.");
        } else if (pms.isEmpty()) {
            throw new IllegalArgumentException(
                    "A PredicateObjectMap must have at least one PredicateMap.");
        } else if (oms == null && roms == null) {
            throw new NullPointerException(
                    "A PredicateObjectMap must have at least one ObjectMap or RefObjectMap.");
        } else if (oms == null && roms.isEmpty()) {
            throw new IllegalArgumentException(
                    "A PredicateObjectMap must have at least one ObjectMap or RefObjectMap.");
        } else if (roms == null && oms.isEmpty()) {
            throw new IllegalArgumentException(
                    "A PredicateObjectMap must have at least one ObjectMap or RefObjectMap.");
        } else if (oms != null && roms != null && oms.isEmpty()
                && roms.isEmpty()) {
            throw new IllegalArgumentException(
                    "A PredicateObjectMap must have at least one ObjectMap or RefObjectMap.");
        } else {

            for (PredicateMap pm : pms) {
                addPredicateMap(pm);
            }

            if (oms != null) {
                for (ObjectMap om : oms) {
                    addObjectMap(om);
                }
            }

            if (roms != null) {
                for (RefObjectMap rom : roms) {
                    addRefObjectMap(rom);
                }
            }

            setNode(getRDF().createBlankNode());
        }
    }

    @Override
    public void addPredicateMap(PredicateMap pm) {
        predList.add(pm);
    }

    @Override
    public void addObjectMap(ObjectMap om) {
        objList.add(om);
    }

    @Override
    public void addRefObjectMap(RefObjectMap rom) {
        refObjList.add(rom);
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
    public GraphMap getGraphMap(int index) {
        return graphList.get(index);
    }

    @Override
    public RefObjectMap getRefObjectMap(int index) {
        return refObjList.get(index);
    }

    @Override
    public ObjectMap getObjectMap(int index) {
        return objList.get(index);
    }

    @Override
    public PredicateMap getPredicateMap(int index) {
        return predList.get(index);
    }

    @Override
    public List<GraphMap> getGraphMaps() {
        return Collections.unmodifiableList(graphList);
    }

    @Override
    public List<RefObjectMap> getRefObjectMaps() {
        return Collections.unmodifiableList(refObjList);
    }

    @Override
    public List<ObjectMap> getObjectMaps() {
        return Collections.unmodifiableList(objList);
    }

    @Override
    public List<PredicateMap> getPredicateMaps() {
        return Collections.unmodifiableList(predList);
    }

    @Override
    public void removeGraphMap(GraphMap gm) {
        graphList.remove(gm);
    }

    @Override
    public void removeRefObjectMap(RefObjectMap rom) {
        /*
		 * The predicate object map must contain at least one object map or
		 * referencing object map.
		 */
        if (refObjList.size() + objList.size() == 1) {
            throw new IllegalStateException(
                    "Can't remove the last ObjectMap or RefObjectMap.");
        }

        refObjList.remove(rom);
    }

    @Override
    public void removeObjectMap(ObjectMap om) {
		/*
		 * The predicate object map must contain at least one object map or
		 * referencing object map.
		 */
        if (objList.size() == 1) {
            throw new IllegalStateException(
                    "Can't remove the last ObjectMap or RefObjectMap.");
        }

        objList.remove(om);

    }

    @Override
    public void removePredicateMap(PredicateMap pm) {
        // The predicate object map must contain at least one predicate map.
        if (predList.size() == 1) {
            throw new IllegalStateException(
                    "Can't remove the last PredicateMap.");
        }

        predList.remove(pm);

    }

    @Override
    public void setNode(BlankNodeOrIRI node) {
        if (node == null) {
            throw new NullPointerException(
                    "A PredicateObjectMap must have a resource.");
        }

        this.node = (BlankNodeOrIRI) node;
    }

    @Override
    public BlankNodeOrIRI getNode() {
        return node;
    }

    @Override
    public Set<Triple> serialize() {
        Set<Triple> stmtSet = new HashSet<>();

        stmtSet.add(getRDF().createTriple(node, getRDF().createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), getRDF().createIRI(R2RMLVocabulary.TYPE_PREDICATE_OBJECT_MAP)));

        for (PredicateMap pm : predList) {
            if (pm.getTermMapType() == TermMap.TermMapType.CONSTANT_VALUED) {
                // Use constant shortcut property.
                stmtSet.add(getRDF().createTriple(node, getRDF().createIRI(R2RMLVocabulary.PROP_PREDICATE), getRDF().createIRI(pm.getConstant())));
            } else {
                stmtSet.add(getRDF().createTriple(node, getRDF().createIRI(R2RMLVocabulary.PROP_PREDICATE_MAP), pm.getNode()));
                stmtSet.addAll(pm.serialize());
            }
        }

        for (ObjectMap om : objList) {
            if (om.getTermMapType() == TermMap.TermMapType.CONSTANT_VALUED) {
                // Use constant shortcut property.
                if (om.getTermType().equals(
                        getRDF().createIRI(R2RMLVocabulary.TERM_IRI))) {

                    stmtSet.add(getRDF().createTriple(node, getRDF().createIRI(R2RMLVocabulary.PROP_OBJECT), getRDF().createIRI(om.getConstant())));

                } else if (om.getTermType().equals(
                        getRDF().createIRI(R2RMLVocabulary.TERM_LITERAL))) {

                    stmtSet.add(getRDF().createTriple(node, getRDF().createIRI(R2RMLVocabulary.PROP_OBJECT),
                            getRDF().createLiteral(om.getConstant())));

                }
            } else {
                stmtSet.add(getRDF().createTriple(node, getRDF().createIRI(R2RMLVocabulary.PROP_OBJECT_MAP), om.getNode()));
                stmtSet.addAll(om.serialize());
            }
        }

        for (RefObjectMap rom : refObjList) {
            stmtSet.add(getRDF().createTriple(node, getRDF().createIRI(R2RMLVocabulary.PROP_OBJECT_MAP), rom.getNode()));
            stmtSet.addAll(rom.serialize());
        }

        for (GraphMap g : graphList) {
            if (g.getTermMapType() == TermMap.TermMapType.CONSTANT_VALUED) {
                // Use constant shortcut property.
                stmtSet.add(getRDF().createTriple(node, getRDF().createIRI(R2RMLVocabulary.PROP_GRAPH), getRDF().createIRI(g.getConstant())));
            } else {
                stmtSet.add(getRDF().createTriple(node, getRDF().createIRI(R2RMLVocabulary.PROP_GRAPH_MAP), g.getNode()));
                stmtSet.addAll(g.serialize());
            }
        }

        return stmtSet;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((objList == null) ? 0 : objList.hashCode());
        result = prime * result
                + ((predList == null) ? 0 : predList.hashCode());
        result = prime * result + ((node == null) ? 0 : node.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof PredicateObjectMapImpl))
            return false;

        PredicateObjectMapImpl other = (PredicateObjectMapImpl) obj;
        if (objList == null) {
            if (other.objList != null) {
                return false;
            }
        } else if (!objList.equals(other.objList)) {
            return false;
        }

        if (predList == null) {
            if (other.predList != null) {
                return false;
            }
        } else if (!predList.equals(other.predList)) {
            return false;
        }

        if (node == null) {
            if (other.node != null) {
                return false;
            }
        } else if (!node.equals(other.node)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "PredicateObjectMapImpl [predList=" + predList + ", objList="
                + objList + ", node=" + node + "]";
    }

}
