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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	final private static String[] layer_tags = new String[]{ "CC_phase", "LAYER", "CHECKPOINT", "PATHWAY", "MODULE" };
	static
	{
		java.util.Arrays.sort(layer_tags);
	}
	protected static final String[] pmid_rule = {"PMID", "www.ncbi.nlm.nih.gov/sites/entrez?Db=pubmed&amp;Cmd=ShowDetailView&amp;TermToSearch=", "[0-9]+" };
	
	protected static final String[][] urls = {
//		{ "HUGO", "www.ncbi.nlm.nih.gov/sites/entrez?cmd=search&db=gene&dopt=full_report&term=" },
		{ "HUGO", "www.genenames.org/cgi-bin/quick_search.pl?.cgifields=type&type=equals&num=50&submit=Submit&search=", "[A-Z0-9]+" },
		{ "HGNC", "www.genenames.org/data/hgnc_data.php?hgnc_id=", "[0-9]+" },
		{ "ENTREZ", "www.ncbi.nlm.nih.gov/gene/", "[0-9]+" },
		{ "UNIPROT", "www.expasy.org/uniprot/", "[A-Z][A-Z0-9]{5}" },
		{ "PUBCHEM", "pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?sid=", "[0-9][-0-9]*[0-9]" },
		{ "KEGGCOMPOUND", "www.genome.jp/dbget-bin/www_bget?cpd:", "[A-Z0-9]+" },
		{ "CAS", "www.chemnet.com/cas/supplier.cgi?l=&exact=dict&f=plist&mark=&submit.x=43&submit.y=12&submit=search&terms=", "[0-9][-0-9]*[0-9]" },
		{ "CHEBI", "www.ebi.ac.uk/chebi/searchId.do?chebiId=CHEBI:", "[0-9]+" },
		{ "KEGGDRUG", "www.genome.jp/dbget-bin/www_bget?dr:", "[A-Z0-9]+" },
		pmid_rule
	};
	protected static final Pattern pat_generic;
	protected static final Pattern pat_bubble;
	protected static final Pattern pat_pmid;
	static
	{
		final String block_name_pattern = "\\p{javaUpperCase}[\\p{javaUpperCase}\\p{javaLowerCase}\\p{Digit}]*";
		final StringBuilder sb = new StringBuilder();
		sb.append("\\b(").append(block_name_pattern).append(")(_begin):");
		sb.append("|");
		sb.append("\\b(").append(block_name_pattern).append(")(_end)\\b");
		for (final String tag : layer_tags)
			sb.append("|\\b(").append(tag).append(":)([A-Z][A-Z0-9_]*)\\b");
		pat_pmid = Pattern.compile(add_link_rule(new StringBuilder(sb), pmid_rule).toString());

		for (final String[] s : urls)
		{
			s[1] = s[1].replace("&", "&amp;");
			add_link_rule(sb, s);
		}

		pat_bubble = pat_generic = Pattern.compile(sb.toString());
		//pat_bubble = Pattern.compile(sb.append("|(\n+)").toString());
	}
	private static StringBuilder add_link_rule(final StringBuilder sb, final String[] s)
	{
		return sb.append("|\\b(").append(s[0]).append(")(:)(").append(s[2]).append(")");
	}
	protected static final String[] colours = { "cyan", "LightGreen", "LightGoldenRodYellow", "Khaki", "SpringGreen", "Yellow" };
}

public class FormatProteinNotes extends FormatProteinNotesBase
{
	final private HashMap<String, String> colour_map = new HashMap<String, String>();
	final private Set<String> modules;
	final private String blog_name;
	public FormatProteinNotes(final Set<String> modules, final String blog_name)
	{
		this.modules = modules;
		this.blog_name = blog_name;
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
			if (sb.length() > 0)
				sb.append("\n");
			for (final Modification sp : modifications)
			{
				final String notes = sp.getNotes();
				if (notes != null)
				{
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
			return ProduceClickableMap.show_shapes_on_map_from_post(h, fw, sps, map_name, blog_name, wp);
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
		while (m.find())
		{
			int offset = 1;
			while (m.start(offset) == m.end(offset))
				offset++;
			final String tag = m.group(offset);
			if (tag.startsWith("\n") && tag.endsWith("\n"))
			{
				m.appendReplacement(res, (after_block) ? "\n" : "<bR>\n");
			}
			else
			{
				final String arg = m.group(offset + 1);
				if ("_end".equals(arg))
				{
					if (block != null && block.equals(tag))
					{
						block = null;
						m.appendReplacement(res, "</p>");
						after_block = true;
					}
					else
						m.appendReplacement(res, "$0");
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
							.append("</b><Br>");
						after_block = false;
					}
					else
						m.appendReplacement(res, "$0");
				}
				else if (tag.endsWith(":"))
				{
					m.appendReplacement(res, "");
					res.append(tag + arg);
					if (modules.contains(arg))
					{
						if (modules_found != null)
							modules_found.add(arg);
						show_shapes_on_map.show_shapes_on_map(h, res, all, arg, blog_name, wp);
					}
				}
				else
				{	
					boolean done = false;
					for (final String[] entry : urls)
						if (entry[0].equals(tag))
						{
							m.appendReplacement(res, "");
							ProduceClickableMap.add_link(res, tag, m.group(offset + 2), entry[1]);
							done = true;
							break;
						}

					if (!done)
						m.appendReplacement(res, "<em>$0</em>");
				}
			}
		}
		m.appendTail(res);
		if (modules_found != null)
			Collections.sort(modules_found);
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
