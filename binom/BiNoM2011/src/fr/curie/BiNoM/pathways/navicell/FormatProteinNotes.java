/* Stuart Pook (Sysra) $Id$
 *
 * Copyright (C) 2011, 2012 Curie Institute, 26 rue d'Ulm, 75005 Paris, France
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map.Entry;

import org.sbml.x2001.ns.celldesigner.ReactionDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.NotesDocument.Notes;

import fr.curie.BiNoM.pathways.navicell.ProduceClickableMap.Complex;
import fr.curie.BiNoM.pathways.navicell.ProduceClickableMap.EntityBase;
import fr.curie.BiNoM.pathways.navicell.ProduceClickableMap.Hasher;
import fr.curie.BiNoM.pathways.navicell.ProduceClickableMap.Modification;
import fr.curie.BiNoM.pathways.utils.Utils;

class FormatProteinNotesBase
{
	final private static String[] layer_tags = new String[]{ "CC_phase", "LAYER", "CHECKPOINT", "PATHWAY", "MODULE"};
	static
	{
		java.util.Arrays.sort(layer_tags);
	}

	protected String[][] xrefs;
	protected ProduceClickableMap.AtlasInfo atlasInfo;
	protected Pattern pat_generic;
	protected Pattern pat_bubble;
	protected Pattern pat_pmid;

	protected static String substitute(String val, final HashMap<String, String> value_map) {
		for (Entry<String, String> e : value_map.entrySet()) {
			val = val.replaceAll(e.getKey(), e.getValue());
		}
		return val;
	}

	void complete_atlas_info()
        {
		if (atlasInfo == null) {
			return;
		}
		String mapUrlTempl = null;
		String moduleUrlTempl = null;

		for (final String[] entry : xrefs) {
			if (entry[0].equals("MAP")) {
				mapUrlTempl = entry[1];
			} else if (entry[0].equals("MODULE")) {
				moduleUrlTempl = entry[1];
			}
		}

		String error = "";
		Vector<ProduceClickableMap.AtlasMapInfo> mapInfo_v = atlasInfo.mapInfo_v;
		int map_size = mapInfo_v.size();
		for (int nn = 0; nn < map_size; ++nn) {
			ProduceClickableMap.AtlasMapInfo mapInfo = mapInfo_v.get(nn);
			if (mapUrlTempl != null) {
				if (mapInfo.url == null) {
					HashMap<String, String> value_map = new HashMap<String, String>();
					value_map.put("%MAP%", mapInfo.id);
					mapInfo.url = substitute(mapUrlTempl, value_map);
				}
			}
			if (mapInfo.url == null) {
				error += (error.length() > 0 ? "\n" : "") + "map " + mapInfo.id + " has no URL";
			}
			Vector<ProduceClickableMap.AtlasModuleInfo> moduleInfo_v = mapInfo.moduleInfo_v;
			int module_size = moduleInfo_v.size();
			for (int jj = 0; jj < module_size; ++jj) {
				ProduceClickableMap.AtlasModuleInfo moduleInfo = moduleInfo_v.get(jj);
				if (moduleUrlTempl != null) {
					if (moduleInfo.url == null) {
						HashMap<String, String> value_map = new HashMap<String, String>();
						value_map.put("%MAP%", mapInfo.id);
						value_map.put("%MODULE%", moduleInfo.name);
						moduleInfo.url = substitute(moduleUrlTempl, value_map);
					}
				}

				if (moduleInfo.url == null) {
					error += (error.length() > 0 ? "\n" : "") + "module " + mapInfo.id + "/" + moduleInfo.name + " has no URL";
				}
			}
		}

		if (error.length() > 0) {
			System.err.println(error);
			System.exit(1);
		}
        }

	void make_xref_patterns()
	{
		if (xrefs == null) {
			return;
		}
		final String block_name_pattern = "\\p{javaUpperCase}[\\p{javaUpperCase}\\p{javaLowerCase}\\p{Digit}_]*";
		final StringBuilder sb = new StringBuilder();
		sb.append("\\b(").append(block_name_pattern).append(")(_begin):");
		sb.append("|");
		sb.append("\\b(").append(block_name_pattern).append(")(_end)\\b");
		for (final String tag : layer_tags) {
			sb.append("|\\b(").append(tag).append(":)([A-Z][A-Z0-9_]*)\\b");
		}

		for (final String[] s : xrefs)
		{
			if (s[0].equals("PMID")) {
				pat_pmid = Pattern.compile(add_link_rule(new StringBuilder(sb), s).toString());
				break;
			}
		}
		for (final String[] s : xrefs)
		{
			s[1] = s[1].replace("&", "&amp;");
			add_link_rule(sb, s);
		}

		//System.out.println("SB [" + sb + "]");
		pat_bubble = pat_generic = Pattern.compile(sb.toString());
	}
	private static StringBuilder add_link_rule(final StringBuilder sb, final String[] s)
	{
		if (isValidEntry(s, 3)) {
			return sb.append("|").append(s[3]).append("(").append(s[2]).append(")");
		}
		return sb.append("|").append("\\b(").append(s[0]).append(")(:)(").append(s[2]).append(")");
	}
	protected static final String[] colours = { "cyan", "LightGreen", "LightGoldenRodYellow", "Khaki", "SpringGreen", "Yellow" };

	protected static boolean isValidEntry(String[] entry, int ind) {
		return entry.length > ind && entry[ind].length() > 0 && !entry[ind].equals("-");
	}
}

public class FormatProteinNotes extends FormatProteinNotesBase
{
	final private HashMap<String, String> colour_map = new HashMap<String, String>();
	final private Set<String> modules;
	final private String blog_name;
	public FormatProteinNotes(final Set<String> modules, final ProduceClickableMap.AtlasInfo atlasInfo, String[][] xrefs, final String blog_name)
	{
		this.modules = modules;
		this.atlasInfo = atlasInfo;
		this.blog_name = blog_name;
		this.xrefs = xrefs;
		make_xref_patterns();
		complete_atlas_info();
	}
	private String get_colour(String name)
	{
		String colour = colour_map.get(name);
		if (colour == null)
			colour_map.put(name, colour = colours[colour_map.size() < colours.length ? colour_map.size() : 0]);
		return colour;
	}
	private String build_comment(final String start, final List<Modification> modifications)
	{
		final StringBuffer sb = new StringBuffer(start == null ? "" : start);
		if (modifications != null)
		{
			
			if (sb.length() > 0) {
				sb.append("\n");
			}				
			

			for (final Modification sp : modifications)
			{
				final String notes = sp.getNotes();
				if (notes != null)if(!notes.trim().equals(""))
				{
					if(sp.getName()==null){
						sb.append("NONAME");
						System.out.println("ERROR: Species "+sp.getId()+" does not have name!");
					}else
						sb.append(ProduceClickableMap.makeFoldable(sp.getName()));
					ProduceClickableMap.visible_debug(sb, sp.getId());
					sb.append("\n").append(notes).append("\n");
				}
			}
		}
		return sb.toString();
	}
	interface ShowShapesOnMap
	{
		StringBuffer show_shapes_on_map(final Hasher h, StringBuffer fw, List<String> sps, final String map_name, final String blog_name, Linker wp);
	}
	
	static final ShowShapesOnMap show_shapes_on_map_from_post = new ShowShapesOnMap()
	{
		@Override
		public StringBuffer show_shapes_on_map(Hasher h, StringBuffer fw, List<String> sps, String map_name, String blog_name, Linker wp)
		{
			return ProduceClickableMap.show_map_from_post(h, fw, map_name, blog_name, wp);
		}
	};
	static final ShowShapesOnMap show_shapes_on_map_from_bubble = new ShowShapesOnMap()
	{
		@Override
		public StringBuffer show_shapes_on_map(Hasher h, StringBuffer fw, List<String> sps, String map_name, String blog_name, Linker wp)
		{
			return ProduceClickableMap.show_shapes_on_map_from_bubble(h, fw, sps, map_name, blog_name, wp);
		}
	};
	static final ShowShapesOnMap show_map_module_from_bubble = new ShowShapesOnMap()
	{
		@Override
		public StringBuffer show_shapes_on_map(Hasher h, StringBuffer fw, List<String> sps, String map_name, String blog_name, Linker wp)
		{
			return ProduceClickableMap.open_map_from_bubble(fw, map_name);
		}
	};
	StringBuffer pmid_post(ReactionDocument.Reaction r, StringBuffer fw, Hasher h, SbmlDocument cd, Linker wp)
	{
		final Notes notes = r.getNotes();
		return notes == null ? fw : format(Utils.getValue(notes), fw, h, java.util.Arrays.asList(r.getId()), pat_pmid, cd, null, show_shapes_on_map_from_post, null, wp);
	}
	StringBuffer pmid_bubble(ReactionDocument.Reaction r, StringBuffer fw, SbmlDocument cd, Linker wp)
	{
		final Notes notes = r.getNotes();
		return notes == null ? fw : format(Utils.getValue(notes), fw, ProduceClickableMap.null_hasher, java.util.Arrays.asList(r.getId()), pat_pmid, cd, null, show_shapes_on_map_from_bubble, null, wp);
	}
	StringBuffer module_post(StringBuffer result, String comment, Linker wp)
	{
		return format(comment, result, ProduceClickableMap.null_hasher, java.util.Collections.<String>emptyList(), pat_bubble, null, null, show_shapes_on_map_from_post, null, wp);
	}
	StringBuffer module_bubble(StringBuffer result, String comment, Linker wp)
	{
		return format(comment, result, ProduceClickableMap.null_hasher, java.util.Collections.<String>emptyList(), pat_bubble, null, null, show_map_module_from_bubble, null, wp);
	}
	StringBuffer bubble(final StringBuffer res, String comment, List<Modification> modifs, SbmlDocument cd, Linker wp)
	{
		return format(comment, res, ProduceClickableMap.null_hasher, ProduceClickableMap.extract_ids(modifs), pat_bubble, cd, null, show_shapes_on_map_from_bubble, null, wp);
	}
	StringBuffer complex(Complex complex, final StringBuffer res, Hasher h, SbmlDocument cd, List<Modification> modifications, Linker wp)
	{
		return format(null, res, h, ProduceClickableMap.extract_ids(complex.getModifications()), pat_generic, cd, modifications, show_shapes_on_map_from_post, null, wp);
	}
	StringBuffer full(final StringBuffer res, Hasher h, EntityBase ent, SbmlDocument cd, List<Modification> modifications, List<String> modules_found, Linker wp)
	{
		return format(ent.getComment(), res, h, ProduceClickableMap.extract_ids(ent.getModifications()), pat_generic, cd, modifications, show_shapes_on_map_from_post, modules_found, wp).append('\n');
	}

	private StringBuffer format(String note, final StringBuffer res, Hasher h,
		List<String> all,
		Pattern pat,
		SbmlDocument cd_, // not used
		List<Modification> comments,
		ShowShapesOnMap show_shapes_on_map,
		List<String> modules_found,
		Linker wp
	)
	{
		final String comment = build_comment(note, comments);
		
		hash(comment, h);

		String block = null;
		
		final Matcher m = pat.matcher(comment);
		boolean after_block = false;
		HashMap<String, String> value_map = new HashMap<String, String>();
		ProduceClickableMap.AtlasMapInfo mapInfo = null;
		while (m.find())
		{
			int offset = 1;
			while (m.start(offset) == m.end(offset))
				offset++;
			final String tag = m.group(offset);
			if (tag.startsWith("\n") && tag.endsWith("\n"))
			{
				//m.appendReplacement(res, (after_block) ? "\n" : "<br>\n");
				//m.appendReplacement(res, (after_block) ? "" : "<br>");
				m.appendReplacement(res, "");
			}
			else
			{
				final String arg = m.group(offset + 1);
				//System.out.println("tag [" + tag + "], arg [" + arg + "]");
				if ("_end".equals(arg))
				{
					if (block != null && block.equals(tag))
					{
						block = null;
						m.appendReplacement(res, "</p>");
						after_block = true;
					}
					else {
						m.appendReplacement(res, "$0");
					}
				}
				else if ("_begin".equals(arg))
				{
					if (block == null)
					{
						block = tag;
						m.appendReplacement(res, "");
						res.append("<p style=\"background: ")
							.append(get_colour(block))
							.append("\">")
							.append("<b>")
							.append(block)
							.append("</b>");
						//.append("</b><br>");
						after_block = false;
					}
					else {
						m.appendReplacement(res, "$0");
					}
				}
				else if (tag.endsWith(":"))
				{
					//System.out.println("  -> endsWidth :");
					m.appendReplacement(res, "");
					res.append(tag + arg);
					if (modules.contains(arg)) {
						//System.out.println("module found [" + arg + "]");
						if (modules_found != null)
							modules_found.add(arg);
						show_shapes_on_map.show_shapes_on_map(h, res, all, arg, blog_name, wp);
					} else {
						//System.out.println("module NOT found [" + arg + "]");
					}
					after_block = false;
				}
				else
				{	
					boolean done = false;
					for (final String[] entry : xrefs)
						if (entry[0].equals(tag))
						{
							m.appendReplacement(res, "");
							String value = m.group(offset + 2);
							value_map.put("%" + entry[0] + "%", value);
							String url = substitute(entry[1], value_map);
							String xtag = isValidEntry(entry, 4) ? substitute(entry[4], value_map) : tag + ":";
							if (tag.equals("MAP")) {
								mapInfo = atlasInfo.getMapInfo(value);
								if (mapInfo != null) {
									value = mapInfo.getName();
									assert mapInfo.url != null;
									url = mapInfo.url;
								}
								res.append(xtag).append(value).append("&nbsp;");
								show_shapes_on_map.show_shapes_on_map(h, res, all, url, blog_name, wp);
							} else if (tag.equals("MODULE")) {
								if(mapInfo==null)
									System.out.println("ERROR: Map info = null for "+value);
								else{
								ProduceClickableMap.AtlasModuleInfo moduleInfo = mapInfo.getModuleInfo(value);
								if (moduleInfo != null) {
									assert moduleInfo.url != null;
									url = moduleInfo.url;
								}
								res.append(xtag).append(value).append("&nbsp;");
								show_shapes_on_map.show_shapes_on_map(h, res, all, url, blog_name, wp);
								}
							} else {
								String target = isValidEntry(entry, 5) ? substitute(entry[5], value_map) : "_blank";
								if (isValidEntry(entry, 6) && entry[6].equalsIgnoreCase("icon")) {
									res.append(xtag).append(value).append("&nbsp;");
									StringBuffer tmp = new StringBuffer();
									ProduceClickableMap.show_map_icon(tmp, wp);
									ProduceClickableMap.add_link(res, "", tmp.toString(), url, target);
								} else {
									ProduceClickableMap.add_link(res, xtag, value, url, target);
								}
							}
							done = true;
							break;
						}

					if (!done) {
						m.appendReplacement(res, "<em>$0</em>");
					}
					after_block = false;
				}
			}
		}
		m.appendTail(res);
		if (modules_found != null) {
			Collections.sort(modules_found);
		}
		res.replace(0, res.length(), res.toString().replaceAll("\\\n", "<BR>").replaceAll("<BR></p>", "</p>").replaceAll("</p><BR>", "</p>"));
		return res;
	}
	private void hash(final String comment, final Hasher h)
	{
		final String[] split = comment.split("[\n \t\r]+");
		for (final String s : split)
		{
			if (!s.isEmpty())
			{
				h.add(s);
				h.add(" ");
			}
		}
	}
}
