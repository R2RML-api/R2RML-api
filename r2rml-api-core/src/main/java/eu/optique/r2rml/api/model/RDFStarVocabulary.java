/*******************************************************************************
 * Copyright 2013, the Optique Consortium
 *
 * Licensed under the Apache License, Version 2.0 (the "License";
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
package eu.optique.r2rml.api.model;

/**
 * Vocabulary/String definitions for Ontop's unofficial RDF-star extension to R2RML
 *
 * @author Lukas Sundqvist
 */
public class RDFStarVocabulary {

    public static final String NAMESPACE = "http://ontop-vkg.org/2021/rdfstar#";

    public static final String PROP_STAR_SUBJECT = NAMESPACE + "subject";
    public static final String PROP_STAR_PREDICATE = NAMESPACE + "predicate";
    public static final String PROP_STAR_OBJECT = NAMESPACE + "object";

    public static final String TERM_STAR_TRIPLE = NAMESPACE + "RDFStarTermType";
}