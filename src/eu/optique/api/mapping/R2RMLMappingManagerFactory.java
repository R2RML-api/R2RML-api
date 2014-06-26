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
package eu.optique.api.mapping;

import java.util.HashMap;

import eu.optique.api.mapping.impl.JenaConfiguration;
import eu.optique.api.mapping.impl.OWLAPIConfiguration;
import eu.optique.api.mapping.impl.R2RMLMappingManagerImpl;
import eu.optique.api.mapping.impl.SesameConfiguration;

/**
 * Factory creating a new instance of the MappingManager service.
 * 
 * @author Marius Strandhaug
 */
public class R2RMLMappingManagerFactory {

	static HashMap<LibConfiguration, R2RMLMappingManager> map = new HashMap<LibConfiguration, R2RMLMappingManager>();

	/**
	 * @return A R2RMLMappingManager configured with Jena.
	 */
	public static R2RMLMappingManager getJenaMappingManager() {

		LibConfiguration jc = new JenaConfiguration();

		if (map.containsKey(jc)) {
			return map.get(jc);
		} else {
			R2RMLMappingManager mm = new R2RMLMappingManagerImpl(jc);
			map.put(jc, mm);
			return mm;
		}

	}

	/**
	 * @return A R2RMLMappingManager configured with OpenRDF Sesame.
	 */
	public static R2RMLMappingManager getSesameMappingManager() {

		LibConfiguration sc = new SesameConfiguration();

		if (map.containsKey(sc)) {
			return map.get(sc);
		} else {
			R2RMLMappingManager mm = new R2RMLMappingManagerImpl(sc);
			map.put(sc, mm);
			return mm;
		}

	}

	/**
	 * @return A R2RMLMappingManager configured with OWLAPI.
	 */
	public static R2RMLMappingManager getOWLAPIMappingManager() {

		LibConfiguration oac = new OWLAPIConfiguration();

		if (map.containsKey(oac)) {
			return map.get(oac);
		} else {
			R2RMLMappingManager mm = new R2RMLMappingManagerImpl(oac);
			map.put(oac, mm);
			return mm;
		}

	}

	/**
	 * @param lc
	 *            - A LibConfiguration that will be used by the
	 *            R2RMLMappingManager.
	 * @return A R2RMLMappingManager configured with a custom LibConfiguration.
	 */
	public static R2RMLMappingManager getMappingManager(LibConfiguration lc) {

		if (lc == null) {
			throw new NullPointerException(
					"An R2RMLMappingManager must have a LibConfiguration");
		}

		if (map.containsKey(lc)) {
			return map.get(lc);
		} else {
			R2RMLMappingManager mm = new R2RMLMappingManagerImpl(lc);
			map.put(lc, mm);
			return mm;
		}

	}

}
