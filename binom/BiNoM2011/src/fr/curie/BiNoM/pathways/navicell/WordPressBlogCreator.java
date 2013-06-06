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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */
package fr.curie.BiNoM.pathways.navicell;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.bican.wordpress.Category;
import net.bican.wordpress.CommentCount;
import net.bican.wordpress.MediaObject;
import net.bican.wordpress.Page;
import net.bican.wordpress.User;
import net.bican.wordpress.Wordpress;
import redstone.xmlrpc.XmlRpcArray;
import redstone.xmlrpc.XmlRpcFault;
import fr.curie.BiNoM.pathways.navicell.ProduceClickableMap.NaviCellException;
import fr.curie.BiNoM.pathways.utils.Utils;

public class WordPressBlogCreator extends BlogCreator
{
	private static final int maximum_number_of_posts = 20480; // might not be enough
	static class Post implements BlogCreator.Post
	{
		final String hash;
		int post_id;
		final String title;
		final String body;
		final String cls;
		final List<String> modules;
		Post(int post_id)
		{
			this.hash = null;
			this.post_id = post_id;
			this.body = null;
			this.title = null;
			this.cls = null;
			modules = Collections.<String>emptyList();
		}
		Post(int page_id, String hash, String title, String body, XmlRpcArray xmlRpcArray, Set<String> entities, Set<String> module_set)
		{
			assert page_id >= 0 : page_id;
			this.hash = hash;
			this.post_id = page_id;
			this.body = body;
			this.title = title;
			if (xmlRpcArray.isEmpty())
			{
				this.cls = null;
				modules = Collections.<String>emptyList();
			}
			else
			{
				modules = new java.util.ArrayList<String>(xmlRpcArray.size() - 1);
				String c = null;
				for (Object k : xmlRpcArray)
				{
					if (entities.contains(k))
						c = (String)k;
					if (module_set.contains(k))
						modules.add((String)k);
				}
				this.cls = c;
				Collections.sort(modules);
			}
			
			assert hash != null : post_id + " " + body + " " + title;
		}
		String getHash() { return hash; }
		String getBody() { return body; }
		public int getPostId() { return post_id; }
		String getCls() { return cls; }
		List<String> getModules() { return modules; }
		public String getTitle()
		{
			return title;
		}
	}
	private final HashMap<String, Post> posts = new HashMap<String, Post>();
	private final HashMap<String, Post> used = new HashMap<String, Post>();
	private final Set<String> entities = new HashSet<String>();
	private final Set<String> modules = new HashSet<String>();
	private final int modules_category_id;
	private final int entities_category_id;
	
	private final Wordpress wp;
	private final String url;

	public WordPressBlogCreator(String wordpress_server, String wordpress_blogname, String wordpress_user, String wordpress_passwd, ProduceClickableMap.AtlasInfo atlasInfo) throws NaviCellException
	{
		url = wordpress_server + "/" + wordpress_blogname;
		wp = open_wordpress(url, wordpress_user, wordpress_passwd);
		String[] ids = read_categories(wp, entities, modules);
		modules_category_id = Integer.parseInt(ids[0]);
		entities_category_id = Integer.parseInt(ids[1]);

		final List<Page> recentPosts;
		try
		{
			recentPosts = wp.getRecentPosts(maximum_number_of_posts);
			verbose("retrieved " + recentPosts.size() + " posts");
		}
		catch (XmlRpcFault e)
		{
			throw new NaviCellException("fatal error retrieving posts from blog", e);
		}
		final User userInfo;
		try
		{
			userInfo = wp.getUserInfo();
		}
		catch (XmlRpcFault e)
		{
			throw new NaviCellException("fatal error retrieving user info from blog", e);
		}
		final Object userid = userInfo.getUserid();
		assert userid != null : userInfo;
		fill(userid, recentPosts, wp, ProduceClickableMap.isMapInAtlas(atlasInfo));
		verbose("stored " + posts.size() + " posts");

		make_map_icon(wp, url);
	}
	static private String[] read_categories(final Wordpress wp, Set<String> entities, Set<String> modules) throws NaviCellException
	{
		List<net.bican.wordpress.Category> cats;
		try
		{
			cats = wp.getCategories();
		}
		catch (XmlRpcFault e1)
		{
			throw new NaviCellException("fatal error retrieving Categories from blog", e1);
		}
		//		String[] id_names = new String[]{ "module hierarchy", "entity hierarchy", ProduceClickableMap.module_list_category_name, ProduceClickableMap.map_list_category_name };
		//Set<String>[] sets = new Set[] { modules, entities, null, null };
		String[] id_names = new String[]{ "module hierarchy", "entity hierarchy", ProduceClickableMap.module_list_category_name};
		Set<String>[] sets = new Set[] { modules, entities, null };
		String[] ids = new String[id_names.length];
		
		for (Category c : cats)
		{
			for (int i = 0; i < id_names.length; i++)
				if (id_names[i].equals(c.getCategoryName()))
					ids[i] = c.getCategoryId();
		}
		for (int i = 0; i < id_names.length; i++)
			if (ids[i] == null)
				throw new NaviCellException("fatal error: missing category: " + id_names[i]);
		for (Category c : cats)
			for (int i = 0; i < id_names.length; i++)
				if (sets[i] != null && ids[i].equals(c.getParentId()))
					sets[i].add(c.getCategoryName());
		return ids;
	}
	private void fill(final Object o_user_id, final List<net.bican.wordpress.Page> recentPosts, final Wordpress wp, boolean mapInAtlas)
	{
		String user_id = String.valueOf(o_user_id);
		int deleted = 0;
		for (final net.bican.wordpress.Page p : recentPosts)
		{
			if (p.getMt_allow_comments() != 0 && user_id.equals((String)p.getUserid()))
			{
				String body = p.getMt_text_more();
				final String[] r = findIdAndHashInBody(body);
				if (r != null)
				{
					if (posts.get(r[0]) != null)
						Utils.eclipsePrintln("duplicate entry for " + r[0]);
					else
						posts.put(r[0], new Post(p.getPostid(), r[1], p.getTitle(), body, p.getCategories(), entities, modules));
				}
				else if (!mapInAtlas && body.length() > 0)
				{
					try
					{
						wp.deletePost(p.getPostid(), "");
						deleted++;
					}
					catch (XmlRpcFault e)
					{
						Utils.eclipseErrorln("failed to delete post " + p.getPostid());
						e.printStackTrace();
					}
				}
			}
		}
		verbose(deleted + " posts deleted");
	}
	private static void debugMessage(String m)
	{
		Utils.eclipseParentPrintln(m);
	}
	private static void errorMessage(String m)
	{
		Utils.eclipseParentErrorln(m);
	}
	private void updatePageId(final String id, final int post_id)
	{
		assert posts.get(id) == null : id;
		final Post ip = used.get(id);
		assert ip != null : id + " " + post_id;
		if (ip != null)
		{
			debugMessage("hash change on " + id + " " + ip.post_id + " -> " + post_id);
			ip.post_id = post_id;
		}
		else
			debugMessage("hash change on " + id + " " + post_id);
	}
	private Post addPage(String id, int page_id)
	{
		assert posts.get(id) == null : id;
		assert used.get(id) == null : id;
		final Post post = new Post(page_id);
		used.put(id, post);
		return post;
	}
	@Override
	Post lookup(final String id)
	{
		final Post p = rlookup(id);
		return p;
	}
	private Post rlookup(final String id)
	{
		Post p = used.get(id);
		if (p != null)
			return p;
		p = posts.get(id);
		if (p == null)
			return null;
		posts.remove(id);
		used.put(id, p);
//		assert lookup(id) == p;
		return p;
	}
	static private String check_category_exists(String category, Wordpress wp, Set<String> existing, int parent)
	{
		if (!existing.contains(category))
		{
			existing.add(category);
			try
			{
				verbose("created category " + category + " in " + parent);
				wp.newCategory(category, category, parent);
			}
			catch (XmlRpcFault e)
			{
				e.printStackTrace();
				errorMessage("failed to create catagory " + category + " with parent " + parent);
			}
		}
		return category;
	}
	private String check_entity_class_exists_in_hierarchy(String cls, Wordpress wp)
	{
		return check_category_exists(cls, wp, entities, entities_category_id);
	}
	private String check_module_exists_in_hierarchy(String module, Wordpress wp)
	{
		return check_category_exists(module, wp, modules, modules_category_id);
	}

	private void updateBlogPost(Wordpress wp, int postid, String title, String body, String cls, List<String> modules, ProduceClickableMap.AtlasInfo atlasInfo)
	{
		//System.out.println("updateBlogPost: " + postid + " in " + cls);
		if (wp == null) {
			return;
		}
		assert postid >= 0 : postid + " " + title + " " + body;
		if (ProduceClickableMap.isMapInAtlas(atlasInfo)) {
			return;
		}
		final Page page;
		try
		{
			page = wp.getPost(postid);
		}
		catch (XmlRpcFault e)
		{
			e.printStackTrace();
			Utils.eclipsePrintln("fault looking for page " + postid);
			return;
		}
		catch (redstone.xmlrpc.XmlRpcException e)
		{
			e.printStackTrace();
			Utils.eclipsePrintln("exception looking for page " + postid);
			return;
		}
		check_entity_class_exists_in_hierarchy(cls, wp);
			
		redstone.xmlrpc.XmlRpcArray a = new redstone.xmlrpc.XmlRpcArray();
		a.add(cls);
		for (String k : modules) {
			a.add(check_module_exists_in_hierarchy(k, wp));
		}
		page.setCategories(a);
		page.setTitle(title);
		page.setDescription("");
		page.setExcerpt("");
		page.setMt_text_more(body);
		try
		{
			final boolean r = wp.editPost(postid, page, "published");
			if (r)
				assert postid == page.getPostid() : postid + " " + page.getPostid();
			else
				errorMessage("failed to update post for " + postid + " " +  page.getTitle());
		}
		catch (XmlRpcFault e)
		{
			e.printStackTrace();
			errorMessage("exception when updating " + postid + " " +  page.getTitle());
			return;
		}
	}
	static private void verbose(String s)
	{
		Utils.eclipseParentPrintln(s);
	}

	static private String[] findIdAndHashInBody(final String body)
	{
		if (body.startsWith(ProduceClickableMap.head_leadin))
		{
			final int end = body.indexOf(' ', ProduceClickableMap.head_leadin.length());
			if (end > 0)
			{
				final String[] r = body.substring(ProduceClickableMap.head_leadin.length(), end).split(ProduceClickableMap.head_seperator);
				return r.length == 2 ? r : null;
			}
		}
		return null;
	}

        static private int createPost(Wordpress wp, String title, String cls)
        {
		Page page = new Page();
		assert !title.isEmpty();
		page.setTitle(title);
		if (cls != null) {
			redstone.xmlrpc.XmlRpcArray a = new redstone.xmlrpc.XmlRpcArray();
			a.add(cls);
			page.setCategories(a);
		}
		try
		{
			String page_id = wp.newPost(page, true);
			verbose("created post for " + title + " -> " + page_id);
			assert page_id != null;
			return Integer.parseInt(page_id);
		}
		catch (XmlRpcFault e)
		{
			Utils.eclipsePrintln("fault while creating post for " + title);
			e.printStackTrace();
			return -2;
		}
		catch (redstone.xmlrpc.XmlRpcException e)
		{
			Utils.eclipsePrintln("exception while creating post for " + title);
			e.printStackTrace();
			return -3;
		}
	}
        static private int updatePost(Wordpress wp, int postid, String title)
        {
		final CommentCount count;
		try
		{
			count = wp.getCommentsCount(postid);
		}
		catch (XmlRpcFault e)
		{
			Utils.eclipseErrorln("no page found for " + postid);
			e.printStackTrace();
			return postid;
		}
		if (count.getTotal_comments() == 0)
			return postid;
		
		if (count.getTotal_comments() == 1)
		{
			final List<net.bican.wordpress.Comment> comments;
			try
			{
				comments = wp.getComments(null, postid, 1, 0);
			}
			catch (XmlRpcFault e)
			{
				Utils.eclipseErrorln("unable to retrieve comments for " + postid);
				return postid;
			}
			if (comments.isEmpty())
			{
				Utils.eclipseErrorln("found zero comments for " + postid);
				return postid;
			}
			final Integer user_id;
			try
			{
				// FIXME this code has not been tested!
				user_id = wp.getUserInfo().getUserid();
			}
			catch (XmlRpcFault e)
			{
				Utils.eclipseErrorln("failed to find my UserId for " + title);
				e.printStackTrace();
				return -2;
			}
			if ((int)user_id == (int)comments.get(0).getUser_id())
				return postid;
		}
		
		final int new_post_id;
		Page page = new Page();
		page.setTitle(title);
		page.setMt_text_more("please reload, description to come");
		try
		{
			final String page_id = wp.newPost(page, true);
			Utils.eclipsePrintln("created updated post for " + title + " -> " + page_id);
			assert page_id != null : title;
			new_post_id = Integer.parseInt(page_id);
		}
		catch (XmlRpcFault e)
		{
			Utils.eclipseErrorln("failed to create new post for " + title);
			e.printStackTrace();
			return -2;
		}
		
		try
		{
			int commentid2 = wp.newComment(new_post_id, null, make_comment("post created because the ", postid, "original post", " was modified"), null , null, null);
			Utils.eclipsePrintln("created cross comment 2 for " + title + " " + commentid2);
			int commentid1 = wp.newComment(postid, null, make_comment("closed because modified, please see the ", new_post_id, "updated post", ""), null , null, null);
			Utils.eclipsePrintln("created cross comment 1 for " + title + " " + commentid1);
		}
		catch (XmlRpcFault e)
		{
			Utils.eclipseErrorln("failed to create cross comments for " + title + " " + postid + " " + new_post_id);
			e.printStackTrace();
			return -2;
		}
		
		try
		{
			final Page old_page = wp.getPost(postid);
			old_page.setMt_allow_comments(0);
			final boolean r = wp.editPost(postid, old_page, "published");
			if (r)
				Utils.eclipsePrintln("update post for " + postid);
			else
				Utils.eclipseErrorln("failed to update post for " + postid);
		}
		catch (XmlRpcFault e)
		{
			Utils.eclipseErrorln("no page found for " + postid);
			e.printStackTrace();
			return -3;
		}
		
		return new_post_id;
	}
        static String make_comment(String before, int post_id, String text, String after)
        {
        	StringBuffer sb = new  StringBuffer(before);
        	sb.append("<a href='");
        	post_link_base_static(post_id, sb);
        	sb.append("'");
        	sb.append(text);
        	sb.append("</a>");
        	sb.append(after);
        	return sb.toString();
        }
	@Override
        void remove_old_posts(ProduceClickableMap.AtlasInfo atlasInfo) throws NaviCellException
        {
		if (ProduceClickableMap.isMapInAtlas(atlasInfo)) {
			return;
		}
		Map<String, fr.curie.BiNoM.pathways.navicell.WordPressBlogCreator.Post> map = java.util.Collections.unmodifiableMap(posts);
		for (final Entry<String, WordPressBlogCreator.Post> entry : map.entrySet())
		{
			final WordPressBlogCreator.Post post = entry.getValue();
			final int post_id = post.getPostId();
			try
			{
				final Page page = wp.getPost(post_id);
				page.setMt_allow_comments(0);
				if (!wp.editPost(post_id, page, "published"))
				{
					Utils.eclipseErrorln("failed to disallow comments " + post_id + " " + post.getTitle());
					continue;
				}
				CommentCount count = wp.getCommentsCount(post_id);
				if (count.getTotal_comments() == 0)
				{
					wp.deletePost(post_id, "false");
					verbose("deleted old post " + post_id + " " + post.getTitle());
				}
				else
				{
					wp.newComment(post_id, null, "closed as deleted from map", null , null, null);
					verbose("closed old post " + post_id + " " + post.getTitle());
				}
			}
			catch (XmlRpcFault e)
			{
				throw new NaviCellException("fault removing old post " + post_id + " " + post.getTitle(), e);
			}
			catch (redstone.xmlrpc.XmlRpcException e)
			{
				throw new NaviCellException("exception removing old post " + post_id + " " + post.getTitle(), e);
			}
		}
	}
	@Override
        BlogCreator.Post updateBlogPostId(String id, String title, String body, ProduceClickableMap.AtlasInfo atlasInfo)
        {
		final Post info = lookup(id);
		//System.out.println("updateBlogPostId: " + id + " in " + (info != null ? info.cls : "<null>"));
		if (ProduceClickableMap.isMapInAtlas(atlasInfo)) {
			if (info == null) {
				//Utils.eclipsePrintln("should not create a new post for map in atlas " + id + " " + title);
				return addPage(id, createPost(wp, title, ProduceClickableMap.module_list_category_name));
			}
			return info;
		}
		if (body != null)
		{
			final String[] id_and_hash = findIdAndHashInBody(body);
			assert id_and_hash[0].equals(id) : id + " " + id_and_hash[0];
			final String new_hash = id_and_hash[1];

			if (info == null) {
				return addPage(id, createPost(wp, title, null));
			} else if (!info.getHash().equals(new_hash)) {
				updatePageId(id, updatePost(wp, info.getPostId(), title));
			}
		}
		return info;
	}
	@Override
        void updateBlogPostIfRequired(BlogCreator.Post p, String title, final String in_body, String entity_type, List<String> modules, ProduceClickableMap.AtlasInfo atlasInfo)
        {
		if (ProduceClickableMap.isMapInAtlas(atlasInfo)) {
			return;
		}

		Post info = (Post)p;
		final String cls = entity_type.toLowerCase();
		if (in_body == null)
			return;
//		assert !body.endsWith("\n") : "bodies must not end with a \\n: " + body;
		final String body = in_body.trim();
		assert body.isEmpty() || body.charAt(body.length() - 1) != '\n';
		title = title.trim();
		final boolean body_eq = body.equals(info.getBody());
		if (body_eq && !body_eq)
		{
			int i = 0;
			for (; i < body.length() && i < info.getBody().length(); i++)
				if (body.charAt(i) != info.getBody().charAt(i))
					break;
			i = Math.min(Math.min(info.getBody().length(), body.length()), i + 20);
			Utils.eclipsePrintln("old: " + info.getBody().substring(0, i));
			Utils.eclipsePrintln("new: " + body.substring(0, i));
		}
		final String r;
		if (!body_eq)
			r = "body";
		else if (!title.equals(info.getTitle().trim()))
			r = "title(" + info.getTitle() + " -> " + title + ")";
		else if (!cls.equals(info.getCls()))
			r = "type(" + info.getCls() + " -> " + cls + ")";
		else if (!equals(modules, info.getModules()))
			r = "modules";
		else
			r = null;
		if (r != null)
		{
			updateBlogPost(wp, info.getPostId(), title, body, cls, modules, atlasInfo);
			verbose("updated post for " + info.getPostId() + " as " + r + " changed: " +  title + "(" + cls + ")");
		}
	}
	
	static private boolean equals(List<String> l1, List<String> l2)
	{
		if (l1 == l2)
			return true;
		if (l1 == null || l2 == null || l1.size() != l2.size())
			return false;
		for (int i = 0; i < l1.size(); i++)
		{
			if (l1.get(i) == l2.get(i))
				continue;
			if (l1.get(i) == null || !l1.get(i).equals(l2.get(i)))
				return false;
		}
		return true;
	}
	
	static private Wordpress open_wordpress(String top_url, String wordpress_user, String wordpress_passwd) throws NaviCellException
	{
		final String url = top_url + "/xmlrpc.php";
		
//		System.setProperty("https.proxyHost", "www-cache-in.curie.fr");
//		System.setProperty("https.proxyPort", "3128");
		
		final Wordpress wp;
		try
		{
			wp = new Wordpress(wordpress_user, wordpress_passwd, url);
		}
		catch (java.net.MalformedURLException e1)
		{
			throw new NaviCellException("failed to connect to Wordpress at " + url, e1);
		}
		
		try
		{
//			Utils.eclipsePrintln("testing blog " + blog_name + " " + wp.getUserInfo().getUrl());

			final User userInfo = wp.getUserInfo();
			assert userInfo.getUserid() != null : userInfo;
			if (wp.getCategories().size() < 2)
				throw new NaviCellException("not enough categories for a usable blog " + wp.getCategories().size());
				
		}
		catch (XmlRpcFault e)
		{
			throw new NaviCellException("failed to query Wordpress at " + url, e);
		}
		
		Utils.eclipsePrintln("connected to blog " + url);

		return wp;
	}

	private static final String map_icon_base = "map.png";
	static private final String map_icon_url_base = "files/" + map_icon_base;

	public static java.io.File createTempDirectory() throws IOException
	{
		// http://stackoverflow.com/questions/617414/create-a-temporary-directory-in-java
		
		final java.io.File temp = java.io.File.createTempFile("temp", Long.toString(System.nanoTime()));

		if (!temp.delete())
			throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());

		if (!temp.mkdir())
			throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());

		return temp;
	}
	
	private void make_map_icon(final Wordpress wp, String url) throws NaviCellException
	{
		boolean exists = does_map_icon_exist(url);
		if (!exists)
		{
			final String resource_name = ProduceClickableMap.data_directory + "/" + map_icon_base;
			final InputStream resource = getClass().getResourceAsStream(resource_name);
			Utils.eclipsePrintln("icon does not exist " + url + " copy from " + getClass().getResource(resource_name).toString());
			
			final java.io.File dir;
			try
			{
				dir = createTempDirectory();
			}
			catch (IOException e1)
			{
				throw new NaviCellException("unable to make a temporary directory for map icon", e1);
			}
			final java.io.File tmp = new java.io.File(dir, map_icon_base);
			try
			{
				try
				{
					final java.io.FileOutputStream out = new java.io.FileOutputStream(tmp);
					ProduceClickableMap.copy_file(resource, out);
					out.close();
				}
				catch (IOException e1)
				{
					throw new NaviCellException("unable to write the temporary file for map icon", e1);
				}

				try
				{
					final MediaObject icon = wp.newMediaObject("image/png", tmp, false);
					Utils.eclipsePrintln(icon.toString());
					Utils.eclipsePrintln("created " + icon.toOneLinerString());
				}
				catch (XmlRpcFault e)
				{
					throw new NaviCellException("unable to upload map icon to blog", e);
				}
				if (!does_map_icon_exist(url))
					throw new NaviCellException("put " + map_icon_base + " into the blog but couldn't find it");
			}
			finally
			{
				tmp.delete();
				if (!dir.delete())
					throw new NaviCellException("failed to remove temporary directory " + dir);
			}
		}
	}
	static private boolean does_map_icon_exist(String url) throws NaviCellException
	{
		final java.net.URL u;
		try
		{
			u = new java.net.URL(url + "/" + map_icon_url_base);
		}
		catch (MalformedURLException e)
		{
			throw new NaviCellException("failed to query Wordpress at " + url, e);
		}
		try
		{
			final java.io.InputStream is = u.openStream();
			is.read();
			is.close();
		}
		catch (IOException e)
		{
			return false;
		}

		return true;
	}

	private static final String post_prefix = "index.php?p=";
	private static StringBuffer post_link_base_static(int post_id, StringBuffer notes)
	{
		return notes.append(post_prefix).append(post_id);
	}
	@Override
	public StringBuffer post_link_base(int post_id, StringBuffer notes)
	{
		return post_link_base_static(post_id, notes);
	}
	@Override
	public String getBlogLinker()
	{
		return "function blog_link(postid) { return '" + url + "/" + post_prefix + "' + postid; }";
	}
	@Override
	public String getMapIconURL()
	{
		return "../" + map_icon_url_base;
	}

	@Override
	public boolean isWordPress()
	{
		return true;
	}
}
