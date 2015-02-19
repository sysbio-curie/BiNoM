
/*
   BiNoM Cytoscape Plugin
   Copyright (C) 2006-2007 Curie Institute, 26 rue d'Ulm, 75005 Paris - FRANCE

   BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
*/

/*
  BiNoM authors:
	Andrei Zinovyev : http://www.ihes.fr/~zinovyev
	Eric Viara : http://www.sysra.com/viara
	Laurence Calzone : http://leibniz.biol.vt.edu/people/laurence/laurence.html
*/

package fr.curie.BiNoM.cytoscape.biopax;

import fr.curie.BiNoM.lib.AbstractTask;

import org.cytoscape.app.CyAppAdapter;

import java.io.File;
import java.net.URL;

import fr.curie.BiNoM.pathways.BioPAXToCytoscapeConverter;
import fr.curie.BiNoM.biopax.AbstractBioPAXImportTaskFactory;

public class BioPAXImportTaskFactory implements AbstractBioPAXImportTaskFactory {

    public BioPAXImportTaskFactory() {
    }

    public AbstractTask createTask(File file, URL url, String name, int algos[],
    		BioPAXToCytoscapeConverter.Option options,
    		boolean applyLayout) {

    	return new BioPAXImportTask(file, url, name, algos, options, true);
    }
}
