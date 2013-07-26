/* Stuart Pook (Sysra) $Id$
 *
 * Copyright (C) 2011-2012 Curie Institute, 26 rue d'Ulm, 75005 Paris, France
 * 
 * BiNoM Cytoscape Plugin is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * BiNoM Cytoscape plugin is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */
package fr.curie.BiNoM.pathways.navicell;

interface Linker
{
	StringBuffer post_link_base(int post_id, StringBuffer notes);
	String getBlogLinker();
	String getMapIconURL();
	boolean isWordPress();
}

abstract public class BlogCreator implements Linker
{
	interface Post
	{
		int getPostId();
	}
	abstract void remove_old_posts(ProduceClickableMap.AtlasInfo atlasInfo) throws ProduceClickableMap.NaviCellException;
	abstract Post lookup(final String id);
	abstract Post updateBlogPostId(String id, String title, String body, ProduceClickableMap.AtlasInfo atlasInfo);
	abstract void updateBlogPostIfRequired(Post post, String title, String body, String reactionClassName, java.util.List<String> modules, ProduceClickableMap.AtlasInfo atlasInfo, boolean is_module);
}
