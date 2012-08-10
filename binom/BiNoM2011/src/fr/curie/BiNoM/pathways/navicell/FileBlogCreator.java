/* Stuart Pook (Sysra) $Id$
 *
 * Copyright (C) 2011-2012 Curie Institute, 26 rue d'Ulm, 75005 Paris, France
 * 
 * BiNoM Cytoscape Plugin is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * BiNoM Cytoscape plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA,USA.
 */

package fr.curie.BiNoM.pathways.navicell;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

class FileBlogCreator extends BlogCreator
{
	class Post implements BlogCreator.Post
	{
		private String entity_type;
		public Post(int post_id, String id, String title, String body)
		{
			this.id = id;
			this.title = title;
			this.body = body;
			this.post_id = post_id;
			entity_type = null;
		}
		public int getPostId() { return post_id; }
		final int post_id;
		final String id;
		final String title;
		private String body;
		void setEntityType(String e)
		{
			assert entity_type == null || e == entity_type : entity_type + " " + e;
			entity_type = e;
		}
		void setBody(String e)
		{
			body = e;
		}
	}
	private final Map<String, Post> id_to_post = new java.util.HashMap<String, Post>();
	private final File root_directory;
	private final String comment;
	FileBlogCreator(final File destination, String comment)
	{
		root_directory = destination;
		this.comment = comment;
	}
	@Override
	Post lookup(final String id)
	{
		return id_to_post.get(id);
	}
	@Override
	BlogCreator.Post updateBlogPostId(String id, String title, String body)
	{
		final Post post = new Post(id_to_post.size(), id, title, body);
		final Post old = id_to_post.put(id, post);
		assert old == null : old.id + " " + post.id;
		return post;
	}
	@Override
	void updateBlogPostIfRequired(BlogCreator.Post bpost, String title, String body, String entity_type,
			List<String> modules)
	{
		final Post post = (Post)bpost;
		assert title == post.title : post.title + " " + title;
		post.setEntityType(entity_type);
		post.setBody(body);
	}
	static private final String post_prefix = "p";
	static private final String post_suffix = ".html";
	static private final String blog_location = "_blog";
	@Override
	void remove_old_posts() throws ProduceClickableMap.NaviCellException
	{
		final java.io.File rd = root_directory;
		rd.mkdir();
		
		final java.io.File root = new java.io.File(rd, blog_location);
		root.mkdir();
		for (final Post p : id_to_post.values())
		{
			final java.io.File f = new java.io.File(root, post_prefix + Integer.toString(p.getPostId()) + ".html");
			final java.io.FileWriter fw;
			try
			{
				fw = new java.io.FileWriter(f);
			}
			catch (IOException e)
			{
				throw new ProduceClickableMap.NaviCellException("failed to create/open " + f, e);
			}
			final java.io.BufferedWriter html = new java.io.BufferedWriter(fw);
			final String common = ProduceClickableMap.common_directory_url;
			try
			{
				html.write("<html>\n");
				html.write("<!--\n" + comment + "\n$Id$\n-->\n");
				html.write("<head>\n");
				
				html.write("<script type='text/javascript'>var map_location='..'</script>\n");
				html.write("<script type='text/javascript' src='" + ProduceClickableMap.jquery_js + "'></script>\n");
				html.write("<script type='text/javascript' src='" + common + "/clickmap_blog.js'></script>\n");
				html.write("<style src='" + common + "/clickmap_blog.css'></style>\n");
				
				html.write("<title>\n");
				html.write(p.title);
				html.write("\n</title>\n");
				html.write("</head>\n");
				html.write("<body>\n");
				html.write(p.body);
				html.write("\n</body>\n");
				html.write("</html>\n");
				html.close();
			}
			catch (IOException e)
			{
				throw new ProduceClickableMap.NaviCellException("failed to write " + f, e);
			}
		}
	}
	@Override
	public StringBuffer post_link_base(int post_id, StringBuffer notes)
	{
		return notes.append(post_prefix).append(post_id).append(post_suffix);
	}
	@Override
	public String getBlogLinker()
	{
		return "function blog_link(postid) { return '../" + blog_location + "/" + post_prefix + "' + postid + '" + post_suffix + "'; }";
	}
}
