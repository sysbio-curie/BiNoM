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

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.nio.channels.FileChannel;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import fr.curie.BiNoM.pathways.analysis.structure.*;
import fr.curie.BiNoM.pathways.utils.Pair;
import fr.curie.BiNoM.pathways.utils.SubnetworkProperties;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.wrappers.*;
import fr.curie.BiNoM.pathways.*;
import edu.rpi.cs.xgmml.*;

import org.sbml.x2001.ns.celldesigner.AnnotationDocument.Annotation;
import org.sbml.x2001.ns.celldesigner.CelldesignerAnnotationDocument.CelldesignerAnnotation;
import org.sbml.x2001.ns.celldesigner.CelldesignerAntisenseRNADocument.CelldesignerAntisenseRNA;
import org.sbml.x2001.ns.celldesigner.CelldesignerAntisensernaReferenceDocument.CelldesignerAntisensernaReference;
import org.sbml.x2001.ns.celldesigner.CelldesignerBaseProductDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerBaseReactantDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerBoundsDocument.CelldesignerBounds;
import org.sbml.x2001.ns.celldesigner.CelldesignerComplexSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerGeneDocument.CelldesignerGene;
import org.sbml.x2001.ns.celldesigner.CelldesignerGeneReferenceDocument.CelldesignerGeneReference;
import org.sbml.x2001.ns.celldesigner.CelldesignerLayerCompartmentAliasDocument.CelldesignerLayerCompartmentAlias;
import org.sbml.x2001.ns.celldesigner.CelldesignerLayerDocument.CelldesignerLayer;
import org.sbml.x2001.ns.celldesigner.CelldesignerLayerFreeLineDocument.CelldesignerLayerFreeLine;
import org.sbml.x2001.ns.celldesigner.CelldesignerListOfFreeLinesDocument.CelldesignerListOfFreeLines;
import org.sbml.x2001.ns.celldesigner.CelldesignerListOfModificationDocument.CelldesignerListOfModification;
import org.sbml.x2001.ns.celldesigner.CelldesignerListOfSquaresDocument.CelldesignerListOfSquares;
import org.sbml.x2001.ns.celldesigner.CelldesignerModificationDocument.CelldesignerModification;
import org.sbml.x2001.ns.celldesigner.CelldesignerNotesDocument.CelldesignerNotes;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerProteinReferenceDocument.CelldesignerProteinReference;
import org.sbml.x2001.ns.celldesigner.CelldesignerRNADocument.CelldesignerRNA;
import org.sbml.x2001.ns.celldesigner.CelldesignerRnaReferenceDocument.CelldesignerRnaReference;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesAliasDocument;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesDocument.CelldesignerSpecies;
import org.sbml.x2001.ns.celldesigner.CelldesignerSpeciesIdentityDocument.CelldesignerSpeciesIdentity;
import org.sbml.x2001.ns.celldesigner.ListOfModifiersDocument;
import org.sbml.x2001.ns.celldesigner.ListOfModifiersDocument.ListOfModifiers;
import org.sbml.x2001.ns.celldesigner.ModelDocument.Model;
import org.sbml.x2001.ns.celldesigner.ModifierSpeciesReferenceDocument.ModifierSpeciesReference;
import org.sbml.x2001.ns.celldesigner.NotesDocument.Notes;
import org.sbml.x2001.ns.celldesigner.ReactionDocument;
import org.sbml.x2001.ns.celldesigner.ReactionDocument.Reaction;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument.Species;
import org.sbml.x2001.ns.celldesigner.SpeciesReferenceDocument.SpeciesReference;
import org.apache.xmlbeans.*;

import com.thebuzzmedia.imgscalr.Scalr;

import fr.curie.BiNoM.pathways.utils.OptionParser;

public class ProduceClickableMap
{
	public static class NaviCellException extends java.lang.Exception
	{
		private static final long serialVersionUID = 5898681220888228646L;
		NaviCellException(String m)
		{
			super(m);
			Utils.eclipseParentErrorln(m);
		}
		NaviCellException(String m, java.lang.Throwable cause)
		{
			super(m, cause);
			Utils.eclipseParentErrorln(m + " " + cause);
		}
		@Override
		public String getMessage()
		{
			if (this.getCause() != null)
				return super.getMessage() + " " + this.getCause().getMessage();
			return super.getMessage();
		}
	};
	private static final String right_hand_tag = "navicell";

	
	static final String module_list_category_name = "module list";
	private static final String base_directory = "../../..";
	private static final String icons_directory = base_directory + "/map_icons";

	private static final String blog_icon = icons_directory + "/misc/blog.png";
	private static final String doc_directory = base_directory + "/doc";
	private static final String entity_icons_directory = icons_directory + "/entity";
	private static final String REACTION_CLASS_NAME = "REACTION";
	private static final String PHENOTYPE_CLASS_NAME = "PHENOTYPE";
	private static final String DRUG_CLASS_NAME = "DRUG";
	private static final String DEGRADED_CLASS_NAME = "DEGRADED";
	private static final String ION_CLASS_NAME = "ION";
	private static final String UNKNOWN_CLASS_NAME = "UNKNOWN";
	private static final String SIMPLE_MOLECULE_CLASS_NAME = "SIMPLE_MOLECULE";
	private static final String COMPLEX_CLASS_NAME = "COMPLEX";
	private static final String ANTISENSE_RNA_CLASS_NAME = "ANTISENSE_RNA";
	private static final String RNA_CLASS_NAME = "RNA";
	private static final String PROTEIN_CLASS_NAME = "PROTEIN";
	private static final String GENE_CLASS_NAME = "GENE";
	private static final String[] non_entities;
	static
	{
		non_entities = new String[]{DEGRADED_CLASS_NAME, COMPLEX_CLASS_NAME, PHENOTYPE_CLASS_NAME, DRUG_CLASS_NAME, ION_CLASS_NAME, UNKNOWN_CLASS_NAME, SIMPLE_MOLECULE_CLASS_NAME};
		Arrays.sort(non_entities);
	}
	
	private final static String included_blog_base = "clickmap_blog";
	private final static String included_map_base = "clickmap_map";
	
	private HashMap<String,Vector<Place>> placeMap = new HashMap<String,Vector<Place>>();
	public SbmlDocument cd = null;
	private final HashMap<String, XmlObject> species = new HashMap<String, XmlObject>(); // not used
	private final HashMap<String, Vector<String>> speciesAliases = new HashMap<String, Vector<String>>();
	private final HashMap<String, Vector<ReactionDocument.Reaction>> speciesInReactions = new HashMap<String, Vector<ReactionDocument.Reaction>>();
	private final HashMap<String, Species> speciesSBML = new HashMap<String, Species>();
	private final Map<String, EntityBase> entityIDToEntityMap;
	private final Map<String, Modification> speciesIDToModificationMap;
	
	final private HashMap<String, Vector<String>> module_species = new HashMap<String, Vector<String>>();
	final private HashMap<String, Vector<String>> module_proteins = new HashMap<String, Vector<String>>();
	
	final public HashMap<String, String> module_names = new HashMap<String, String>();
	
	final private HashMap pathways = new HashMap();
	
	final public HashMap<String, String> speciesModularModuleNameMap = new HashMap<String, String>();
	
	private int linewidth = 3;
	private float scale = 1f;
	
	public static String scriptFile = "";
	
	final private String module_name;
	
	private static final String celldesigner_suffix = ".xml";
	private static final String image_suffix = ".png";
	private static final String common_directory_name = "_common";
	static final String common_directory_url = "../" + common_directory_name;
	private static final String jstree_directory_url = "../../../lib/jstree_pre1.0_fix_1";
	static final String jquery_js = jstree_directory_url + "/_lib/jquery.js";
	
	private final String blog_name;
	private ImagesInfo scales;
	private ItemCloser right_panel;
	
	public ProduceClickableMap(final String blog_name, File input)
	{
		this.blog_name = blog_name;
		assert input.canRead() : "cannot read " + input;
		assert input.getName().endsWith(celldesigner_suffix) : "bad input file " + input + " (name must end in xml)";
		
		module_name = input.getName().substring(0, input.getName().length() - celldesigner_suffix.length());

		loadCellDesigner(input.getPath());
		final Map<String, EntityBase> _entityIDToEntityMap = new HashMap<String, EntityBase>();
		final Map<String, Modification> _speciesIDToModificationMap = new HashMap<String, Modification>();
		makeAllEntities(cd, _entityIDToEntityMap, _speciesIDToModificationMap);
		speciesIDToModificationMap = Collections.unmodifiableMap(_speciesIDToModificationMap);
		entityIDToEntityMap = Collections.unmodifiableMap(_entityIDToEntityMap);
		
		CellDesignerToCytoscapeConverter.createSpeciesMap(cd.getSbml());
		findAllPlacesInCellDesigner();
//		updateStandardNames();

		CellDesigner.entities = CellDesigner.getEntities(cd);
		
		calculate_all_modification_names(cd, _entityIDToEntityMap, _speciesIDToModificationMap);
	}

	static private void calculate_all_modification_names(SbmlDocument cd, final Map<String, EntityBase> _entityIDToEntityMap,
		final Map<String, Modification> _speciesIDToModificationMap)
	{
		/* this is really horrible but I need to have the name of the species and this can only
		 * be done once the maps are set up. Which maps? Who knows?
		 */
		int i = 0;
		for (Modification m : _speciesIDToModificationMap.values())
		{
			try
			{
				m.calculateName(cd);
			}
			catch (ClassCastException w)
			{
				// some names cannot de calculated
				Utils.eclipseErrorln("exceptions " + m.getId()+" Message: "+w.getStackTrace()[0]);
				i++;
			}
		}
		for (EntityBase e : _entityIDToEntityMap.values())
			for (Modification m : e.getModifications())
				try
				{
					m.calculateName(cd);
				}
				catch (ClassCastException w)
				{
					Utils.eclipseErrorln("exceptions " + m.getId());
					i++;
				}

		if (i != 0)
			Utils.eclipseErrorln(i + " exceptions");
	}

	public static void main(String args[])
	{
		/*
			if (false)	ProduceClickableMap clMap = new ProduceClickableMap();
			// position of reaction
			clMap.loadCellDesigner("/bioinfo/projects_prod/Curie_Servier/ClickMap_Stuart/examples/simpleReaction.xml");
			CellDesignerToCytoscapeConverter.createSpeciesMap(clMap.cd.getSbml());
			clMap.findAllPlacesInCellDesigner();
			Pair p1 = clMap.findCentralPlaceForReaction(clMap.cd.getSbml().getModel().getListOfReactions().getReactionArray(0));
			System.out.println(((Float)p1.o1)+","+((Float)p1.o2));
			Pair p2 = clMap.findCentralPlaceForReaction(clMap.cd.getSbml().getModel().getListOfReactions().getReactionArray(1));
			System.out.println(((Float)p2.o1)+","+((Float)p2.o2));			
			Pair p3 = clMap.findCentralPlaceForReaction(clMap.cd.getSbml().getModel().getListOfReactions().getReactionArray(2));
			System.out.println(((Float)p3.o1)+","+((Float)p3.o2));			
			String s = clMap.generateMapFile("/tmp/", "simpleReaction", "simpleReaction");

			// type of regulator which is s6 (species id)
			String id = "s6";
			ReactionDocument.Reaction rr = clMap.cd.getSbml().getModel().getListOfReactions().getReactionArray(2);
			for(int i=0;i<rr.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();i++){
				if(rr.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(i).getModifiers().equals(id))
					System.out.println("Type of  "+id+" is "+rr.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(i).getType());
			}

		//	System.out.println("clMap.getRegulatorType(" + id + ") " + clMap.getRegulatorType(id));

			// annotation of a complex which is s3 (species id)
			String complexSpecies = "s3";
			for(int i=0;i<clMap.cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
				if(clMap.cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i).getId().equals(complexSpecies))
					System.out.println("Annotation for "+complexSpecies+": "+Utils.getValue(clMap.cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i).getNotes()));
			}

//			System.exit(0);
			}
		 */

		OptionParser options = new fr.curie.BiNoM.pathways.utils.OptionParser(args, null);
		String base = null;
		File wordpress_cfg_file = null;
		File config = null;
		File source_directory = null;
		File destination = null;
		@SuppressWarnings("unused")
		boolean verbose = false;
		boolean make_tiles = true;
		String project_name = null;
		
		Boolean show_default_compartement_name = null;
		
		while (true)
		{
			Boolean b;
			File f;
			String s;
			if ((s = options.stringOption("base", "file name base")) != null)
				base = s;
			else if ((s = options.stringOption("name", "project name")) != null)
				project_name = s;
			else if ((f = options.fileOption("wordpress", "wordpress configuration file")) != null)
				wordpress_cfg_file = f;
			else if ((f = options.fileRequiredOption("destination", "destination directory")) != null)
				destination = f;
			else if ((f = options.fileRequiredOption("config", "configuration file")) != null)
				config = f;
			else if ((f = options.fileOption("source_directory", "directory containing cell designer files and images")) != null)
				source_directory = f;
			else if ((b = options.booleanOption("verbose", "verbose mode")) != null)
				verbose = b.booleanValue();
			else if ((b = options.booleanOption("tile", "force tile creation")) != null)
				make_tiles = b.booleanValue();
			else if ((b = options.booleanOption("notile", "do not force tile creation")) != null)
				make_tiles = !b.booleanValue();
			else if ((b = options.booleanOption("defcptname", "show default compartement name")) != null)
				show_default_compartement_name = b.booleanValue();
			else if ((b = options.booleanOption("nodefcptname", "don't show default compartement name")) != null)
				show_default_compartement_name = !b.booleanValue();
			else
				break;
		}
		options.done();
		
		final Properties configuration = load_config(config);
		
		if (project_name == null)
			project_name = configuration.getProperty("name", base);
		
		final String wordpress_server;
		final String wordpress_passwd;
		final String wordpress_user;
		final String wordpress_blogname;
		final File root;
		
		if (wordpress_cfg_file == null)
		{
			wordpress_server = wordpress_passwd = wordpress_user =  wordpress_blogname = null;
			root = destination;
		}
		else
		{
			
			final Properties wordpress_cfg = load_config(wordpress_cfg_file);
			wordpress_server = wordpress_cfg.getProperty("server", "localhost");
			wordpress_passwd = wordpress_cfg.getProperty("password");
			wordpress_user = wordpress_cfg.getProperty("user");
			wordpress_blogname = wordpress_cfg.getProperty("blog", project_name);
			if (wordpress_passwd == null)
				fatal_error("no password to connect to wordpress found in the configuration file: " + wordpress_cfg_file);
			if (wordpress_user == null)
				fatal_error("no user to connect to wordpress found in the configuration file: " + wordpress_cfg_file);
			root = mk_maps_directory(project_name, destination);
			
		}		
	
		if (show_default_compartement_name == null)
			show_default_compartement_name = "true".equalsIgnoreCase(configuration.getProperty("showDefaultCompartmentName", "false"));

		if (base == null && (base = configuration.getProperty("base")) == null)
			fatal_error("no base on the command line or in the configuration file");
		if (source_directory == null)
			source_directory = config.getParentFile();
		
		try
		{
			run(base, source_directory, make_tiles, project_name, show_default_compartement_name, wordpress_server,
				wordpress_passwd, wordpress_user, wordpress_blogname, root);
		}
		catch (NaviCellException e)
		{
			Utils.eclipseErrorln(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void run
	(
		final String base,
		final File source_directory,
		final boolean make_tiles,
		final String project_name,
		final boolean show_default_compartement_name,
		final String wordpress_server,
		final String wordpress_passwd,
		final String wordpress_user,
		final String wordpress_blogname,
		final File root
	) throws NaviCellException
	{
		CellDesignerToCytoscapeConverter.alwaysMentionCompartment = show_default_compartement_name;
		
		final String comment = make_tag_for_comments();
		
		final BlogCreator wp = wordpress_server == null ? new FileBlogCreator(root, comment) : new WordPressBlogCreator(wordpress_server, wordpress_blogname, wordpress_user, wordpress_passwd);

		final File data_directory = new File("bin/data");
		final File destination_common = new File(root, common_directory_name);
		if (!destination_common.exists() && !destination_common.mkdir())
			throw new NaviCellException("failed to make " + destination_common);

		try
		{
			copy_files(data_directory, destination_common);
		}
		catch (IOException e)
		{
			throw new NaviCellException("IO error installing static files", e);
		}
		
		final Map<String, ModuleInfo> modules = get_module_list(source_directory, base);

		final ProduceClickableMap master;
		try
		{
			master = process_a_map(project_name, root, base, source_directory, wp, make_tiles, modules);
		}
		catch (IOException e)
		{
			throw new NaviCellException("IO error creating map " + master_map_name, e);
		}

		for (final String map_name : modules.keySet())
		{
			if (!map_name.equals(master_map_name))
				try
				{
					process_a_map(map_name, master, root, base, source_directory, wp, make_tiles, modules);
				}
				catch (IOException e)
				{
					throw new NaviCellException("IO error creating map " + map_name, e);
				}
		}
		
		finish_right_panel_xml(master.right_panel, modules, master.cd.getSbml().getModel(), master.scales, project_name);
		
		wp.remove_old_posts();
	}
	
	private static String make_tag_for_comments()
	{
		final StringBuffer sb = new StringBuffer();
		try
		{
			final String host = java.net.InetAddress.getLocalHost().getHostName();
			sb.append(host);
			sb.append("\n");
		}
		catch (UnknownHostException e)
		{
		}
		java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z\n");
		sb.append(dateFormat.format(new Date()));
		
		sb.append("$Id$\n");
		sb.append("created by http://navicell.curie.fr/");

		return sb.toString();
	}

	private String get_map_title()
	{
		final Notes notes = cd.getSbml().getModel().getNotes();
		final String v;
		if (notes == null)
			v = module_name;
		else
		{
			final String text = Utils.getText(notes);
			if (text == null)
				v = module_name;
			else
			{
				final String t = text.trim();
				if (t.isEmpty())
					v = module_name;
				else
				{
					final int nl = t.indexOf('\n');
					v = nl < 0 ? t : t.substring(0, nl);
				}
			}
		}
		return v;
	}

	private static Map<String, ModuleInfo> get_module_list(final File source_directory, final String base)
	{
		final Map<String, ModuleInfo> list = new HashMap<String, ModuleInfo>();
		final FilenameFilter is_good_xml_file = new FilenameFilter()
		{
			@Override
			public boolean accept(final File dir, final String name)
			{
				if (!name.endsWith(celldesigner_suffix))
					return false;
				if (!name.startsWith(base))
					return false;
				if (base.isEmpty() && name.startsWith("."))
					return false;
				final String map_name = name.substring(0, name.length() - celldesigner_suffix.length());
				if (map_name.substring(base.length(), map_name.length()).equals(master_map_name))
					return false;
				final File image_file = new File(dir, map_name + "-0" + image_suffix);
				return image_file.exists();
			}
		};
		for (final File f : source_directory.listFiles(is_good_xml_file))
		{
			String base_name = f.getName();
			String map_name = base_name.substring(base.length(), base_name.length() - celldesigner_suffix.length());
			list.put(map_name, null);
		}
		return (list);
	}

	private static Properties load_config(File config)
	{
		final Properties configuration = new Properties();
		final FileInputStream config_stream;
		try
		{
			config_stream = new FileInputStream(config);
		}
		catch (FileNotFoundException e1)
		{
			System.err.println(e1.getMessage());
			System.exit(1);
			return configuration;
		}
		try
		{
			configuration.load(config_stream);
		}
		catch (IOException e1)
		{
			System.err.println("failed to load configuration file " + config + ": " + e1.getMessage());
			System.exit(1);
			return configuration;
		}
		return configuration;
	}
	
	static class ImagesInfo
	{
		final int minzoom, maxzoom, tile_width, tile_height, xshift_zoom0, yshift_zoom0, width_zoom0, height_zoom0;
		final double z;
		ImagesInfo(final int minzoom, final int maxzoom, final int tile_width, final int tile_height, final int xshift_zoom0, final int yshift_zoom0, final int width_zoom0, final int height_zoom0)
		{
			this.minzoom = minzoom;
			this.maxzoom = maxzoom;
			this.tile_width = tile_width;
			this.tile_height = tile_height;
			this.xshift_zoom0 = xshift_zoom0;
			this.yshift_zoom0 = yshift_zoom0;
			this.width_zoom0 = width_zoom0;
			this.height_zoom0 = height_zoom0;
			this.z = 1 << maxzoom;
		}
		double getX(double x) { return x / z + xshift_zoom0; }
		double getY(double y) { return y / z + yshift_zoom0; }
	};
	
	private static ImagesInfo make_tiles(File source_directory, String root, File outdir) throws IOException
	{
		int[] shifts = new int[2];
		final int xmargin = 10;
		final int tile_width = 256;
		final int tile_height = 256;
		final int max_width = tile_width - 2 * xmargin;
		final int max_height = tile_height;
		int count = 0;
		
		final BufferedImage tiled = new BufferedImage(tile_width, tile_height, BufferedImage.TYPE_INT_RGB);
		final java.awt.Graphics2D g = tiled.createGraphics();
		
		final int difference_zoom0_image0;
		final int xshift_zoom0;
		final int yshift_zoom0;
		final int width_zoom0;
		final int height_zoom0;
		
		{
			final File image_file0 = new File(source_directory, root + "-" + 0 + image_suffix);
			BufferedImage image0;
			try
			{
				image0 = ImageIO.read(image_file0);
			}
			catch (IOException e)
			{
				throw new IOException("failed to read image from " + image_file0, e);
			}
			final int width0 = image0.getWidth();
			final int height0 = image0.getHeight();
		
			difference_zoom0_image0 = Math.max(get_maxscale(height0, max_height), get_maxscale(width0, max_width));
			
			width_zoom0 = width0 >> difference_zoom0_image0;
			xshift_zoom0 = (max_width - width_zoom0) / 2 + xmargin;
			height_zoom0 = height0 >> difference_zoom0_image0;
			yshift_zoom0 = (max_height - height_zoom0) / 2;
			
			calculate_shifts(shifts, xshift_zoom0, yshift_zoom0, difference_zoom0_image0);
			count += write_tiles(image0, outdir, difference_zoom0_image0, tiled, g, tile_width, tile_height, shifts);
		}
		
		int last_found = difference_zoom0_image0;
		
		for (int file_number = 1;; file_number++)
		{
			final File image_file = new File(source_directory, root + "-" + file_number + image_suffix);
			final BufferedImage image;
			try
			{
				image = ImageIO.read(image_file);
			}
			catch (IOException e)
			{
				return new ImagesInfo(difference_zoom0_image0, last_found, tile_width, tile_height, xshift_zoom0, yshift_zoom0, width_zoom0, height_zoom0);
			}
			
			final int scale_factor = get_scale(image.getWidth(), image.getHeight(), image_file, max_width, max_height);
			assert scale_factor > last_found : scale_factor + " " + last_found;
			
			for (int i = last_found + 1; i <= scale_factor; i++)
			{
				final BufferedImage resize;
				if (i == scale_factor)
					resize = image;
				else
				{
					final int d = scale_factor - i;
					final int w = image.getWidth() >> d;
					resize = Scalr.resize(image, Scalr.Method.QUALITY, w, image.getHeight() >> d, Scalr.OP_ANTIALIAS);
					Utils.eclipsePrintln("resized image " + file_number + " to " + i + " by " + d + " width " + image.getWidth() + " -> " + w);
				}
				final BufferedImage padded_image = new BufferedImage(tile_width << i, tile_height << i, BufferedImage.TYPE_INT_RGB);
				padded_image.createGraphics().drawRenderedImage(resize, null);
				
				calculate_shifts(shifts, xshift_zoom0, yshift_zoom0, i);
				count += write_tiles(padded_image, outdir, i, tiled, g, tile_width, tile_height, shifts);
			}
			last_found = scale_factor;
		}
	}

	private static void calculate_shifts(int[] shifts, final int xshift, final int yshift, final int scale_factor)
	{
		shifts[0] = xshift << scale_factor;
		shifts[1] = yshift << scale_factor;
	}
	
	private static int get_scale(int width, int height, final File image, int width0, int height0)
	{
		/*
		if (width % width0 != 0)
			throw new Exception(image + "'s width " + width + " is not a multiple of " + image0 + "'s width " + width0);
		if (height % height0 != 0)
			throw new Exception(image + "'s height " + height + " is not a multiple of " + image0 + "'s height " + height0);
		final int scale = width / width0;
		if (scale != height / height0)
			throw new Exception(image + "'s height is not the same multiple of " + image0 + "'s height as is the width");
		*/
		return Math.max(get_maxscale(width, width0), get_maxscale(height, height0));
	}

	private static ImagesInfo get_zooms(File source_directory, String root) throws IOException
	{
		int last_found = 0;
		
		final File image_file0 = new File(source_directory, root + "-" + 0 + image_suffix);
		final BufferedImage image0 = ImageIO.read(image_file0);
		
		final int width = image0.getWidth();
		final int height = image0.getHeight();
		
		for (int file_number = last_found + 1;; file_number++)
		{
			final File image_file = new File(source_directory, root + "-" + file_number + image_suffix);
			final BufferedImage image;
			try
			{
				image = ImageIO.read(image_file);
			}
			catch (IOException e)
			{
				return null; //new int[]{ last_found, width, height };
			}
			last_found = get_scale(image.getWidth(), image.getHeight(), image_file, width, height);
		}
	}

	private static int get_maxscale(final int width, int base_width)
	{
		final double a = width / (double)base_width;
		final double b = Math.log(a) / Math.log(2);
		final double c = Math.ceil(b);
		return (int)c;
	}
	
	private static final String right_panel_list = "right_panel.xml";
	
	private static boolean empty_tiles(final File tiles_directory)
	{
		final File[] directories = tiles_directory.listFiles();
		if (directories == null)
			return false;
		
		for (final File directory : directories)
		{
			final File[] files = directory.listFiles();
			if (files != null)
				for (File file : files)
					file.delete();
			directory.delete();
		}
		return true;
	}
	
	private String get_map_notes()
	{
		final Notes notes = this.cd.getSbml().getModel().getNotes();
		return (notes == null) ? "" : Utils.getText(notes).trim();
	}

	private static void process_a_map(final String map, final ProduceClickableMap master, File destination, String base, File source_directory,
		BlogCreator wp, boolean make_tiles, Map<String, ModuleInfo> modules) throws IOException
	{
		final ProduceClickableMap clMap = make_clickmap(master.blog_name, map, base, source_directory);
		final String module_notes = clMap.get_map_notes(); //Utils.getText(clMap.cd.getSbml().getModel().getNotes()).trim();
		final BlogCreator.Post module_post = create_module_post(wp, module_notes, map);
		modules.put(map, new ModuleInfo(module_notes, module_post));

		final File this_map_directory = mk_maps_directory(map, destination);

		ImagesInfo scales = make_tiles(map, base, source_directory, make_tiles, this_map_directory);

		clMap.generatePages(wp, new File(this_map_directory, right_panel_list), scales, master.master_format);
		make_index_html(this_map_directory, master.blog_name, clMap.get_map_title(), map, scales, module_post, wp);
	}

	private static ProduceClickableMap process_a_map(final String blog_name, File destination, String base, File source_directory,
		BlogCreator wp, boolean make_tiles, Map<String, ModuleInfo> modules) throws IOException, NaviCellException
	{
		final String map = master_map_name;
		final ProduceClickableMap clMap = make_clickmap(blog_name, map, base, source_directory);
		final String module_notes = clMap.get_map_notes();

		final File this_map_directory = mk_maps_directory(map, destination);

		clMap.scales = make_tiles(map, base, source_directory, make_tiles, this_map_directory);

		clMap.master_format = new FormatProteinNotes(modules.keySet(), blog_name);
		clMap.right_panel = clMap.generatePages(wp, new File(this_map_directory, right_panel_list), clMap.scales, clMap.master_format, modules);
		final BlogCreator.Post module_post = create_module_post(wp, module_notes, map);
		make_index_html(this_map_directory, blog_name, clMap.get_map_title(), map, clMap.scales, module_post, wp);
		modules.put(map, new ModuleInfo(module_notes, module_post));
		return clMap;
	}

	private static ProduceClickableMap make_clickmap(final String blog_name, final String map, String base, File source_directory)
	{
		final ProduceClickableMap clMap = new ProduceClickableMap(blog_name, new File(source_directory, base + map + ".xml"));
		return clMap;
	}

	private static ImagesInfo make_tiles(final String map, String base, File source_directory, boolean make_tiles, final File this_map_directory) throws IOException
	{
		final File tiles_directory = new File(this_map_directory, "tiles");
		final boolean tiles_exist = tiles_directory.exists();
		ImagesInfo scales;
		if (make_tiles || !tiles_exist)
		{
			if (tiles_exist)
				empty_tiles(tiles_directory);
			tiles_directory.mkdir();
			scales = make_tiles(source_directory, base + map, tiles_directory);
		}
		else
			scales = get_zooms(source_directory, base + map);
		if (scales == null)
			Utils.eclipseErrorln("no images found");
		return scales;
	}

	private static File mk_maps_directory(final String map, File destination)
	{
		final File this_map_directory = new File(destination, map);
		this_map_directory.mkdir();
		return this_map_directory;
	}

	private static void copy_files(final File source, final File destination) throws IOException
	{
		for (final String suffix : new String[]{"js", "css"})
			for (final String base : new String[]{ included_blog_base, included_map_base })
				copy_file_between_directories(source, destination, base + "." + suffix);
	}

	private static void copy_file_between_directories(final File source, File destination, final String file) throws IOException
	{
		final File destFile = new File(destination, file);
		final File sourceFile = new File(source, file);
		copy_file(sourceFile, destFile);
	}

	private static void copy_file(final File sourceFile, final File destFile) throws IOException
	{
		if (destFile.getCanonicalFile().equals(destFile.getAbsoluteFile()))
			copyFile(sourceFile, destFile);
		else
			Utils.eclipsePrintln("skipping symlink " + destFile);
	}

	static private void fatal_error(String message)
	{
		Utils.eclipseParentErrorln(message);
		System.exit(1);
	}
	
	private static void copyFile(File sourceFile, File destFile) throws IOException
	{
		// http://stackoverflow.com/questions/106770/standard-concise-way-to-copy-a-file-in-java
		// https://gist.github.com/889747
		// if (!destFile.exists())
		// destFile.createNewFile();
		FileInputStream fIn = null;
		FileOutputStream fOut = null;
		FileChannel source = null;
		FileChannel destination = null;
		try
		{
			fIn = new FileInputStream(sourceFile);
			source = fIn.getChannel();
			fOut = new FileOutputStream(destFile);
			destination = fOut.getChannel();
			long transfered = 0;
			long bytes = source.size();
			while (transfered < bytes)
			{
				transfered += destination.transferFrom(source, 0, source.size());
				destination.position(transfered);
			}
		}
		finally
		{
			if (source != null)
			{
				source.close();
			}
			else
				if (fIn != null)
				{
					fIn.close();
				}
			if (destination != null)
			{
				destination.close();
			}
			else
				if (fOut != null)
				{
					fOut.close();
				}
		}
	}
	
	private static int write_tiles
	(
		final BufferedImage scaledImage, final File outdir,
		final int scale_factor, final BufferedImage tiled, final java.awt.Graphics2D g,
		int width, int height, int[] shifts
	)
	{
		final int xshift = shifts[0];
		final int yshift = shifts[1];
		final int nx = 1 << scale_factor;
		final int ny = nx;
		final AffineTransform af = new AffineTransform();
		int count = 0;
		final File zoom_dir = new File(outdir, scale_factor + "");
		zoom_dir.mkdir();
		for (int i = 0; i < ny; i++)
		{
			af.setToTranslation(xshift, yshift - height * i);
			for (int j = 0; j < nx; j++)
			{
				g.clearRect(0, 0, width, height);
				g.drawRenderedImage(scaledImage, af);
				final File png = new File(zoom_dir, j + "_" + i + image_suffix);
				try
				{
					ImageIO.write(tiled, "png", png);
					count++;
				}
				catch (IOException e)
				{
					Utils.eclipseErrorln("failed to write to " + png + ": " + e.getMessage());
				}
				af.translate(-width, 0);
			}
//			af.translate(-af.getTranslateX(), -height);
		}
		return count;
	}

	public void loadCellDesigner(String fn)
	{
		cd = CellDesigner.loadCellDesigner(fn);
		CellDesigner.entities = CellDesigner.getEntities(cd);
	}
	
	/* Function to find all coordinate information.
	 * */
	protected void findAllPlacesInCellDesigner(){
		System.out.println("Finding places in CellDesigner: "+this.module_name);
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spa = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			final String species_id = spa.getSpecies();
			if (speciesIDToModificationMap.get(species_id) == null)
				continue;
			Place place = new Place();
			place.id = spa.getId();
			place.x = Float.parseFloat(spa.getCelldesignerBounds().getX());
			place.y = Float.parseFloat(spa.getCelldesignerBounds().getY());
			place.positionx = place.x;
			place.positiony = place.y;
			place.width = Float.parseFloat(spa.getCelldesignerBounds().getW());
			place.height = Float.parseFloat(spa.getCelldesignerBounds().getH());
			place.type = place.RECTANGLE;
			place.sbmlid = species_id;
			
			Object obj = CellDesigner.entities.get(species_id);
			//System.out.println(obj.getClass().getName().toLowerCase());
			if(!obj.getClass().getName().toLowerCase().contains("celldesignerspecies")){
				SpeciesDocument.Species sp = (SpeciesDocument.Species)CellDesigner.entities.get(species_id);
				String entname = CellDesignerToCytoscapeConverter.getEntityName(sp.getId(),sp.getAnnotation().getCelldesignerSpeciesIdentity(),cd);
				place.label =  CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), sp.getId(), entname, sp.getCompartment(), true, true, "", cd); //spa.getSpecies();

				Vector<Place> vp = placeMap.get(place.id);
				if(vp==null) vp = new Vector<Place>();
				vp.add(place);
				placeMap.put(place.id,vp);
				species.put(species_id, spa);
				
				Vector<String> aliases = speciesAliases.get(species_id);
				if(aliases==null) aliases = new Vector<String>();
				aliases.add(spa.getId());
				speciesAliases.put(species_id,aliases);
				
				// Crazy! a species should only have one Entity, we don't need a Vector
				/*
				Vector<Entity> v = speciesEntities.get(species_id);
				if(v==null) v = new Vector<Entity>();
				v.add(e);
				speciesEntities.put(species_id,v);
				*/
			}
		}
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias cspa = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
			Place place = new Place();
			place.id = cspa.getId();
			place.x = Float.parseFloat(cspa.getCelldesignerBounds().getX());
			place.y = Float.parseFloat(cspa.getCelldesignerBounds().getY());
			place.positionx = place.x;
			place.positiony = place.y;
			place.width = Float.parseFloat(cspa.getCelldesignerBounds().getW());
			place.height = Float.parseFloat(cspa.getCelldesignerBounds().getH());
			place.type = place.RECTANGLE;
			place.sbmlid = cspa.getSpecies();

			try{
				SpeciesDocument.Species sp = (SpeciesDocument.Species)CellDesigner.entities.get(cspa.getSpecies());
				String entname = CellDesignerToCytoscapeConverter.getEntityName(sp.getId(),sp.getAnnotation().getCelldesignerSpeciesIdentity(),cd);
				place.label =  CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), sp.getId(), entname, sp.getCompartment(), true, true, "", cd); //spa.getSpecies();
	
				
				Vector<Place> vp = placeMap.get(place.id);
				if(vp==null) vp = new Vector<Place>();
				vp.add(place);
				placeMap.put(place.id,vp);
				species.put(cspa.getSpecies(), cspa);
				
				Vector<String> aliases = speciesAliases.get(cspa.getSpecies());
				if(aliases==null) aliases = new Vector<String>();
				aliases.add(cspa.getId());
				speciesAliases.put(cspa.getSpecies(),aliases);
			/*
				Vector<Entity> v = speciesEntities.get(cspa.getSpecies());
				if(v==null) v = new Vector<Entity>();
				Vector<Entity> ve = getEntitiesInComplex(cspa.getSpecies());
				for(int k=0;k<ve.size();k++){
					Entity ent = ve.get(k);
					boolean found = false;
					if(ent!=null)
					for(int kk=0;kk<v.size();kk++){
						Entity e = v.get(kk);
						if(e!=null)if(e.id!=null){
						//System.out.println(e.id+"\t"+ent.id);
						if(e.id.equals(ent.id))
							found = true;
						}
					}
					if(!found)
						v.add(ent);
				}
				speciesEntities.put(cspa.getSpecies(),v);
				*/
			}catch(Exception e){
				System.out.println(place.sbmlid+" is not species in findAllPlacesInCellDesigner/elldesignerListOfComplexSpeciesAliases");
			}
		}
		// Now species comments
		for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			speciesSBML.put(sp.getId(), sp);
		}
		// Now reactions
		if(cd.getSbml().getModel().getListOfReactions()!=null)
			for(int i=0;i<cd.getSbml().getModel().getListOfReactions().getReactionArray().length;i++){
				ReactionDocument.Reaction r = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
				if(r.getListOfReactants()!=null)
				for(int j=0;j<r.getListOfReactants().getSpeciesReferenceArray().length;j++){
					String spid = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
					Vector<ReactionDocument.Reaction> v = speciesInReactions.get(spid);
					if(v==null)
						v = new Vector<ReactionDocument.Reaction>();
					v.add(r); speciesInReactions.put(spid,v);
				}
				if(r.getListOfProducts()!=null)
				for(int j=0;j<r.getListOfProducts().getSpeciesReferenceArray().length;j++){
					String spid = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
					Vector<ReactionDocument.Reaction> v = speciesInReactions.get(spid);
					if(v==null)
						v = new Vector<ReactionDocument.Reaction>();
					v.add(r); speciesInReactions.put(spid,v);
				}
				if(r.getListOfModifiers()!=null)
				for(int j=0;j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length;j++){
					String spid = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
					Vector<ReactionDocument.Reaction> v = speciesInReactions.get(spid);
					if(v==null)
						v = new Vector<ReactionDocument.Reaction>();
					v.add(r); speciesInReactions.put(spid,v);
				}
				
				generatePlacesForReaction(r);
				
			}
		
//		complex_composition = get_complex_compositions();
//		complex_to_modifications = make_complex_to_modifications();
	}
	
	static boolean equals(List<String> l1, List<String> l2)
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
	
	static private String get_reference_id(final CelldesignerSpeciesIdentity identity)
	{
		final String cls = Utils.getValue(identity.getCelldesignerClass());
		if (cls.equals(PROTEIN_CLASS_NAME))
		{
			final CelldesignerProteinReference ref = identity.getCelldesignerProteinReference();
			return Utils.getValue(ref);
		}
		else if (cls.equals(GENE_CLASS_NAME))
		{
			final CelldesignerGeneReference ref = identity.getCelldesignerGeneReference();
			return Utils.getValue(ref);
		}
		else if (cls.equals(RNA_CLASS_NAME))
		{
			final CelldesignerRnaReference ref = identity.getCelldesignerRnaReference();
			return Utils.getValue(ref);
		}
		else if (cls.equals(ANTISENSE_RNA_CLASS_NAME))
		{
			final CelldesignerAntisensernaReference ref = identity.getCelldesignerAntisensernaReference();
			return Utils.getValue(ref);
		}
		assert false : "class " + cls + " doesn't have a reference";
		return null;
	}

	static private String makeComplexId(final ArrayList<CelldesignerSpecies> components, final Map<String, String> includedSpeciesToSpeciesMap, Map<String, EntityBase> entities)
	{
		assert !components.isEmpty();
		String[] ids = new String[components.size()];
		int i = 0;
		for (final CelldesignerSpecies component : components)
		{
			final String component_id = component.getId();
			final String species = includedSpeciesToSpeciesMap.get(component_id);
			final EntityBase e = entities.get(species);
			if (e != null)
			{
				ids[i] = e.getId();
				assert !e.getCls().equals(COMPLEX_CLASS_NAME);
				assert !ids[i].isEmpty();
			}
			else
			{
				ids[i] = component_id;
				Utils.eclipseErrorln("complex contains a species " + component_id + " that does not have an Entity");
			}
			i++;
		}
		Arrays.sort(ids);
		
		final StringBuffer id_buf = new StringBuffer();
		for (final String id : ids)
		{
			if (id_buf.length() != 0)
				id_buf.append('_');
			id_buf.append(id);
		}
		assert id_buf.length() != 0;
		return id_buf.toString();
	}
	
	static private void completeComplexes(final SbmlDocument cd, Map<String, EntityBase> map, Map<String, String> includedSpeciesToEntityMap, Map<String, ArrayList<CelldesignerSpecies>> complexToIncludedSpeciesMap2)
	{
		final Model model = cd.getSbml().getModel();
		int done = 0;
		for (final Species sp : model.getListOfSpecies().getSpeciesArray())
			if (Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals(COMPLEX_CLASS_NAME))
			{
				final String species_id = sp.getId();
				final ArrayList<CelldesignerSpecies> list = complexToIncludedSpeciesMap2.get(species_id);
				if (list != null)
				{
					if (list.size() > 1)
					{
						final String complex_id = makeComplexId(list, includedSpeciesToEntityMap, map);
						final Complex complex = (Complex)map.get(complex_id);
						final boolean empty = complex.getComponents().isEmpty();
						final Modification modification = new Modification(sp, complex, cd);
						for (final CelldesignerSpecies included : list)
						{
							final CelldesignerSpeciesIdentity identity = included.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity();
							final String id = get_entity_id(identity);
							final EntityBase e = map.get(id);
							assert e != null : id + " " +species_id + " " + Utils.getValue(sp.getName());
							((Entity)e).addAssociated(modification);
							if (empty)
								complex.addComponent(e);
						}
						//complex.addModification(new Modification(sp, complex));
					}
					done += list.size();
				}
			}
		assert done == model.getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray().length :
			done + " " + model.getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray().length;
	}

	static private Modification makeClickMapEntity(final SbmlDocument cd, Species species, Map<String, EntityBase> entities, Map<String, Complex> complexes)
	{
		final String species_id = species.getId();
		final CelldesignerSpeciesIdentity identity = species.getAnnotation().getCelldesignerSpeciesIdentity();
		final String cls = Utils.getValue(identity.getCelldesignerClass());
		final EntityBase eb;
		final Modification m;
		if (cls.equals(COMPLEX_CLASS_NAME))
		{
			Complex e = complexes.get(species_id);
			if (e == null)
				complexes.put(species_id, e = new Complex(species_id));
			m = new Modification(species, e, cd);
			eb = e;
				
		}
		else
		{
			final String id = get_entity_id(identity);
			EntityBase e = entities.get(id);
			if (e == null)
			{
				assert Arrays.binarySearch(non_entities, cls) >= 0 : id + " " + cls;
				final String name = Utils.getValue(identity.getCelldesignerName());
				entities.put(id, e = new Entity(id, name, cls));
			}
			m = new Modification(species, e, cd);
			eb = e;
		}
		eb.addModification(m);
		return m;
	}

	static private Complex makeClickMapComplex(Map<String, Complex> complexes, final CelldesignerAnnotation celldesignerAnnotation, Map<String, EntityBase> entities2)
	{
		final String species_id = Utils.getValue(celldesignerAnnotation.getCelldesignerComplexSpecies());
		Complex e = complexes.get(species_id);
		if (e == null)
		{
			e = new Complex(species_id);
			complexes.put(species_id, e);
		}
		
		{
			final CelldesignerSpeciesIdentity identity = celldesignerAnnotation.getCelldesignerSpeciesIdentity();
			final String cls = Utils.getValue(identity.getCelldesignerClass());
			final int pos = Arrays.binarySearch(non_entities, cls);
			if (pos >= 0)
			{
				final String name = Utils.getValue(identity.getCelldesignerName());
				final String id = makeEntityId(pos, name);
				if (entities2.get(id) == null)
					entities2.put(id, new Entity(id, name, cls));
			}
		}
		
		return e;
	}

	static private void makeAllEntities(final SbmlDocument cd, final Map<String, EntityBase> entities, final Map<String, Modification> modifications)
	{
		assert entities.isEmpty() : entities.size();
		assert modifications.isEmpty() : modifications.size();
		
		final Map<String, ArrayList<CelldesignerSpecies>> complexToIncludedSpeciesMap = makeComplexToIncludedSpeciesMap(cd);
		

		final Model model = cd.getSbml().getModel();
		final Annotation annotation = model.getAnnotation();

		for (final CelldesignerProteinDocument.CelldesignerProtein prot : annotation.getCelldesignerListOfProteins().getCelldesignerProteinArray())
			add(entities, new Entity(prot.getId(), prot.getName(), PROTEIN_CLASS_NAME, prot.getCelldesignerNotes()));
		for (final CelldesignerGene gene : annotation.getCelldesignerListOfGenes().getCelldesignerGeneArray())
			add(entities, new Entity(gene.getId(), gene.getName(), GENE_CLASS_NAME, gene.getCelldesignerNotes()));
		for (final CelldesignerRNA rna : annotation.getCelldesignerListOfRNAs().getCelldesignerRNAArray())
			add(entities, new Entity(rna.getId(), rna.getName(), RNA_CLASS_NAME, rna.getCelldesignerNotes()));
		for (final CelldesignerAntisenseRNA anti : annotation.getCelldesignerListOfAntisenseRNAs().getCelldesignerAntisenseRNAArray())
			add(entities, new Entity(anti.getId(), anti.getName(), ANTISENSE_RNA_CLASS_NAME, anti.getCelldesignerNotes()));

		final Map<String, Complex> complexes = new HashMap<String, Complex>();
		for (final Species species : model.getListOfSpecies().getSpeciesArray())
		{
			final Modification m = makeClickMapEntity(cd, species, entities, complexes);
			check_put(modifications, m);
		}
		
		final Map<String, String> includedSpeciesToSpeciesMap = new HashMap<String, String>();

		for (final CelldesignerSpecies species : annotation.getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray())
		{
			final Complex e = makeClickMapComplex(complexes, species.getCelldesignerAnnotation(), entities);
			final Modification m = new Modification(species, e, cd);
			check_put(modifications, m);
			
			final String referenced = get_entity_id(species.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity());
			final String old = includedSpeciesToSpeciesMap.put(species.getId(), referenced);
			assert old == null || old.equals(referenced) : old + " " + referenced;
		}
		
		fixComplexes(cd, entities, modifications, complexToIncludedSpeciesMap, complexes, includedSpeciesToSpeciesMap);
	}

	static private void fixComplexes(final SbmlDocument cd, final Map<String, EntityBase> entities, final Map<String, Modification> modifications,
			final Map<String, ArrayList<CelldesignerSpecies>> complexToIncludedSpeciesMap, final Map<String, Complex> complexes,
			final Map<String, String> includedSpeciesToSpeciesMap)
	{
		for (final Complex s : complexes.values())
			s.fixId(includedSpeciesToSpeciesMap, complexToIncludedSpeciesMap, entities);
		final Complex[] array = new Complex[complexes.size()];
		complexes.values().toArray(array);
		final Comparator<? super Complex> sort_by_id = new Comparator<Complex>()
		{
			@Override
			public int compare(Complex arg0, Complex arg1)
			{
				return arg0.getId().compareTo(arg1.getId());
			}
		};
		Arrays.sort(array, sort_by_id);
		String id = null;
		Complex good = null;
		for (final Complex c : array)
			if (!c.getId().equals(id))
			{
				id = c.getId();
				good = c;
				final EntityBase put = entities.put(id, c);
				assert put == null : id;
			}
			else
				c.setGood(good);
		for (final Modification m : modifications.values())
			m.updateIfNotGood();
		completeComplexes(cd, entities, includedSpeciesToSpeciesMap, complexToIncludedSpeciesMap);
	}

	static private void check_put(final Map<String, Modification> modifications, Modification m)
	{
		final int n = modifications.size();
		modifications.put(m.getId(), m);
		assert n + 1 == modifications.size() : m.getId();
	}
	
	static private final String makeEntityId(final int pos, final String s)
	{
		final int min = ' ';
		final int max = '~';
		final BigInteger shift = BigInteger.valueOf(max - min + 1);
		java.math.BigInteger total = BigInteger.ZERO;
		
		for (int i = 0; i < s.length(); i++)
		{
			final int c = s.charAt(i);
			assert c >= min : c;
			assert c <= max : c;
			total = total.multiply(shift).add(BigInteger.valueOf(c - min));
		}
		total = total.multiply(BigInteger.valueOf(non_entities.length)).add(BigInteger.valueOf(pos));
		return total.toString(Character.MAX_RADIX);
	}
	
	static private String get_entity_id(final CelldesignerSpeciesIdentity identity)
	{
		final String cls = Utils.getValue(identity.getCelldesignerClass());
		final int pos = Arrays.binarySearch(non_entities, cls);
		if (pos < 0)
			return get_reference_id(identity);
		return makeEntityId(pos, Utils.getValue(identity.getCelldesignerName()));
	}
	
	static private PrintWriter indent(final PrintWriter output, int indent)
	{
		while (indent-- > 0)
			output.print('\t');
		return output;
	}
	
	static final private String cdata_end = "]]>";
	static final private String cdata_end_rep;
	static {
		cdata_end_rep = cdata_end.substring(0, cdata_end.length() - 1) + "&zwnj;" + cdata_end.substring(cdata_end.length() - 1, cdata_end.length());
	}
	
	private static final String default_language = "<name lang='en'>";

	static private void content_line(ItemCloser indent, String content, String content2)
	{
		indent.indent().println("<content>");
		indent.indent(1).print(default_language);
		indent.getOutput().print(content);
//		cdata(indent.getOutput(), content);
		indent.getOutput().println("</name>");
		indent.indent(1).print("<name lang='ln'>");
		cdata(indent.getOutput(), content2);
		indent.getOutput().println("</name>");
		indent.indent().println("</content>");
	}

	private static void cdata(final PrintWriter output, String content2)
	{
		output.print("<![CDATA[");
		output.print(content2.replace(cdata_end, cdata_end_rep));
		output.print(cdata_end);
	}
	
	static private void content_line_data(ItemCloser indent, String content)
	{
//		output.println("<content><name><![CDATA[" + content + "]]></name></content>");
		indent.indent().print("<content>");
		indent.getOutput().print(default_language);
		indent.getOutput().print(content);
		//cdata(indent.getOutput(), content);
		indent.getOutput().println("</name></content>");
	}
	
	static private void content_line(ItemCloser indent, String content)
	{
//		output.println("<content><name><![CDATA[" + content + "]]></name></content>");
		indent.indent().print("<content>");
		indent.indent().print(default_language);
		indent.getOutput().print(content);
		indent.getOutput().println("</name></content>");
	}

	static class ItemCloser
	{
		final ItemCloser parent;
		final int indent;
		PrintWriter output_;
		ItemCloser(final ItemCloser i)
		{
			parent = i;
			indent = i.indent + 1;
			output_= i.output_;
			assert output_ != null;
			assert parent != null;
		}
		ItemCloser(final PrintWriter o)
		{
			parent = null;
			indent = 1;
			output_ = o;
			assert o != null;
		}
		void close()
		{
			for (int i = 0; i < indent; i++)
				output_.print('\t');
			output_.println("</item>");
			output_ = null;
		}
		PrintWriter getOutput()
		{
			assert output_ != null;
			return output_;
		}
		int nextIndent() { return indent + 1; }
		PrintWriter indent()
		{
			return indent(0);
		}
		PrintWriter indent(int i)
		{
			i += indent;
			while (i-- > 0)
				output_.print('\t');
			return output_;
		}
		public ItemCloser getParent()
		{
			return parent;
		}
		public ItemCloser add()
		{
			return new ItemCloser(this);
		}
	}

	static private ItemCloser item_line(final ItemCloser indent, String id, String cls, String name, String type)
	{
		item_list_start(indent, id, cls);
		if (type != null)
			indent.getOutput().print(" rel=\"" + type + "\"");
		return item_line_end(indent, name);
	}

	private static ItemCloser item_line_end(final ItemCloser indent, String name)
	{
		indent.getOutput().println(">");
		content_line(indent.add(), name);
		return indent;
	}

	private static ItemCloser item_list_start(final ItemCloser indent, String id, String cls)
	{
		indent.indent().print("<item");
		if (id != null)
			indent.getOutput().print(" id=\"" + id + "\"");
		if (cls != null)
			indent.getOutput().print(" class=\"" + cls + "\"");
		return indent;
	}
	
	private static String make_right_hand_link_to_blog(int postid, String name)
	{
		StringBuffer sb = new StringBuffer("<img align='top' class='blogfromright' border='0' src='" + blog_icon + "' alt='");
		sb.append(postid).append("'/> ").append(name);
		return sb.toString();
	}
	
	private static void modification_line(final ItemCloser indent, Modification m,
		Map<String, Vector<String>> speciesAliases,
		Map<String, Vector<Place>> placeMap,
		String bubble,
		ImagesInfo scales
	)
	{
		item_list_start(indent, m.getId(), right_hand_tag);
		indent.getOutput().print(" position=\"");
		boolean first = true;
		for (final String shape_id : m.getShapeIds(speciesAliases))
		{
			final Vector<Place> places = placeMap.get(shape_id);
			assert places.size() == 1 : shape_id + " " + places.size();
			final Place place = places.get(0);
			if (first)
				first = false;
			else
				indent.getOutput().print(" ");
			
			indent.getOutput().print(scales.getX(place.x));
			indent.getOutput().print(";");
			indent.getOutput().print(scales.getY(place.y));
		}
		indent.getOutput().println("\">");
		content_line(indent.add(), m.getName(), bubble);
		indent.close();
	}
	
	/*
	static private void reactions_for_right_hand_panel(AllPosts all_posts, FormatProteinNotes format)
	{
		
		for (final ReactionDocument.Reaction r : model.getListOfReactions().getReactionArray())
		{
			final String title = r.getId();
			final String body = createReactionBody(r, format, false);
			final BlogCreater.Post post = updateBlogPostId(wp, all_posts, r.getId(), title, REACTION_CLASS_NAME, body);
	//		createReactionMarker(xml, r, post.getPostId(), format);
			updateBlogPostIfRequired(wp, post, title, body);
		}
	}
	*/
	
	static private ItemCloser generate_right_panel_xml(final File output_file, final Map<String, EntityBase> entityIDToEntityMap,
		final Map<String, Vector<String>> speciesAliases,
		final Map<String, Vector<Place>> placeMap,
		final FormatProteinNotes format,
		final BlogCreator wp,
		final SbmlDocument cd,
		final String blog_name,
		ImagesInfo scales
	) throws UnsupportedEncodingException, FileNotFoundException
	{
		final String encoding = "UTF-8";
		final PrintWriter output = new PrintWriter(output_file, encoding);
		output.println("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>");
		output.println("<root>");
		final ItemCloser entities = item_line(new ItemCloser(output), "entities", null, "Entities", null);
		final List<EntityBase> sorted = new ArrayList<EntityBase>();
		for (final String[] s : class_name_to_human_name)
		{
			final ItemCloser cls = create_entity_header(entities.add(), s);
			if (s[0].equals(REACTION_CLASS_NAME))
				return cls;
			sorted.clear();
			for (final EntityBase ent : entityIDToEntityMap.values())
				if (s[0].equals(ent.getCls()) && !ent.isBad())
					sorted.add(ent);
			
			Collections.sort(sorted);
			
			for (final EntityBase ent : sorted)
				add_modifications_to_right(speciesAliases, placeMap, format, cd, blog_name, scales, output, cls, ent);
			cls.close();
		}
		assert false;
		return entities;
		
	}

	private static void add_modifications_to_right(final Map<String, Vector<String>> speciesAliases, final Map<String, Vector<Place>> placeMap,
			final FormatProteinNotes format, final SbmlDocument cd, final String blog_name, ImagesInfo scales, final PrintWriter output,
			final ItemCloser cls, final EntityBase ent)
	{
		final ItemCloser entity = item_line(cls.add(), ent.getId(), null, make_right_hand_link_to_blog(ent.getPostId(), ent.getName()), null);
		
		class Modif
		{
			final Modification m;
			final boolean associated;
			Modif(Modification m, boolean associated)
			{
				this.m = m;
				this.associated = associated;
			}
		}
		final List<Modif> modifs = new ArrayList<Modif>(ent.getPostTranslational().size() + ent.getAssociated().size());
		for (final Modification m : ent.getPostTranslational())
			modifs.add(new Modif(m, false));
		for (final Modification m : ent.getAssociated())
			modifs.add(new Modif(m, true));
		Collections.sort(modifs, new Comparator<Modif>(){
			@Override
			public int compare(final Modif o1, final Modif o2)
			{
				final int r = modification_orderer.compare(o1.m, o2.m);
				return r != 0 ? r : (o1.associated == o2.associated ? 0 : o1.associated ? -1 : 1);
			}});
		
		for (final Modif q : modifs)
		{
			final Modification m = q.m;
			if (q.associated)
			{
				ItemCloser modif = item_list_start(entity.add(), null, m.getId() + " " + right_hand_tag);
				output.println(">");
				content_line_data(modif.add(), m.getName());
				modif.close();
			}
			else
			{
				if(ent!=null)if(ent.getPost()!=null){
					String b = create_entity_bubble(m, format, ent.getPost().getPostId(), ent, cd, blog_name);
					modification_line(entity.add(), m, speciesAliases, placeMap, b, scales);
				}else{
					System.out.println("ERROR: no Post for "+ent.getId());
				}
			}
		}
		
		entity.close();
	}

	private static ItemCloser create_entity_header(final ItemCloser entities, final String[] s)
	{
		final StringBuffer sb = new StringBuffer(s[1]).append(" <img border='0' src=");
		html_quote(sb, entity_icons_directory + "/" + s[0] + ".png");
		sb.append("/>");
		return item_line(entities , s[0], null, sb.toString(), s[0]);
	}
	static private void finish_right_panel_xml(final ItemCloser right)
	{
		final PrintWriter output = right_close_entities(right);
		right_close(output);
	}
	
	static private String make_module_bubble(String notes, int post_id)
	{
		String[] parts = notes.split("\n", 2);
		StringBuffer fw = new StringBuffer();
		fw.append("<b>").append(parts[0]).append("</b>");
		bubble_to_post_link_with_anchor(post_id, fw);
		if (parts.length > 1)
			fw.append("<br>\n").append(parts[1]);
		return fw.toString();
	}
	
	static class ModuleInfo
	{
		final private int post_id;
		private final String notes;
		ModuleInfo(String module_notes, BlogCreator.Post module_post)
		{
			post_id = module_post.getPostId();
			notes = module_notes;
		}
	}
	
	static private void finish_right_panel_xml(final ItemCloser right, Map<String, ModuleInfo> modules_set, Model model, ImagesInfo scales, String blog_name)
	{
		final PrintWriter output = right_close_entities(right);
		
		final Map<String, double[]> positions = new HashMap<String, double[]>();
		loop : for (CelldesignerLayer l : model.getAnnotation().getCelldesignerListOfLayers().getCelldesignerLayerArray())
		{
			final String name = Utils.getText(l.getName());
			final CelldesignerListOfFreeLines free = l.getCelldesignerListOfFreeLines();
			final CelldesignerListOfSquares squares = l.getCelldesignerListOfSquares();
			if (squares != null)
				for (CelldesignerLayerCompartmentAlias k : squares.getCelldesignerLayerCompartmentAliasArray())
				{
					final CelldesignerBounds bounds = k.getCelldesignerBounds();
					positions.put(name, new double[] { Double.valueOf(bounds.getX()), Double.valueOf(bounds.getY()) } );
					continue loop;
				}
			if (free != null)
				for (CelldesignerLayerFreeLine k : free.getCelldesignerLayerFreeLineArray())
				{
					final CelldesignerBounds bounds = k.getCelldesignerBounds();
					positions.put(name, new double[] { Double.valueOf(bounds.getSx()), Double.valueOf(bounds.getEx()) } );
					continue loop;
				}
			positions.put(name, null);
		}
	
		final ItemCloser modules = item_line(new ItemCloser(output), "modules", null, "Modules", null);
		for (Entry<String, ModuleInfo> k : modules_set.entrySet())
		{
			if (master_map_name.equals(k.getKey()))
				;
			else if (!positions.containsKey(k.getKey()))
				Utils.eclipseErrorln("no layer for " + k.getKey() + " in master map");
			else
			{
				double[] position = positions.get(k.getKey());
				if (position == null)
					Utils.eclipseErrorln("no objects in layer for " + k.getKey() + " in master map");
				else if (k.getValue() == null)
					Utils.eclipseErrorln("no post of module " + k.getKey());
				else
				{
					final int post_id = k.getValue().post_id;
					ItemCloser indent = modules.add();
					item_list_start(indent, make_module_id(k.getKey()), right_hand_tag);
					indent.getOutput().print(" position=\"");
					indent.getOutput().print(scales.getX(position[0]));
					indent.getOutput().print(";");
					indent.getOutput().print(scales.getY(position[1]));
					indent.getOutput().println("\">");
					content_line(indent.add(), make_right_hand_link_to_blog(post_id, k.getKey()), make_module_bubble(k.getValue().notes, post_id));
					indent.close();
				}
			}
		}
		modules.close();
		
		right_close(output);
	}

	private static void right_close(final PrintWriter output)
	{
		output.println("</root>");
		output.close();
	}

	private static PrintWriter right_close_entities(final ItemCloser right)
	{
		final PrintWriter output = right.getOutput();

		right.close();
		right.getParent().close();
		return output;
	}
	
	private void reaction_line(final ItemCloser indent, ReactionDocument.Reaction r, String bubble, ImagesInfo scales, int post_id)
	{
		final Pair position = findCentralPlaceForReaction(r);
		final float x = (Float)position.o1;
		final float y = (Float)position.o2;
		item_list_start(indent, r.getId(), right_hand_tag);
		indent.getOutput().print(" position=\"");
		indent.getOutput().print(scales.getX(x));
		indent.getOutput().print(";");
		indent.getOutput().print(scales.getY(y));
		indent.getOutput().println("\">");
		content_line(indent.add(), make_right_hand_link_to_blog(post_id, r.getId()), bubble);
		indent.close();
	}
	
	private FormatProteinNotes master_format;
	
	private void generatePages(final BlogCreator wp, File rpanel_index, ImagesInfo scales, FormatProteinNotes format) throws UnsupportedEncodingException, FileNotFoundException
	{
		final Model model = cd.getSbml().getModel();
		
		for (final EntityBase ent : entityIDToEntityMap.values())
			ent.setPost(wp);
		
		final ItemCloser right = generate_right_panel_xml(rpanel_index, entityIDToEntityMap, speciesAliases, placeMap, format, wp, cd, blog_name, scales);

		for (final ReactionDocument.Reaction r : model.getListOfReactions().getReactionArray())
		{
			final BlogCreator.Post post = wp.lookup(r.getId());
			
			if(post!=null){
				final String bubble = createReactionBubble(r, post.getPostId(), format, wp);
				reaction_line(right.add(), r, bubble, scales, post.getPostId());
			}else{
				System.out.println("ERROR: No post for "+r.getId());
			}
		}
		
		finish_right_panel_xml(right);
	}
	
	static private String make_module_id(String map_name)
	{
		return map_name + "__";
	}
	
	static private BlogCreator.Post create_module_post(final BlogCreator wp, final String module_notes, final String map_name)
	{
		final Hasher h = new Hasher();
		StringBuffer fw = create_buffer_for_post_body(h);
		String[] parts = module_notes.split("\n", 2);
		fw.append("<b>").append(parts[0]).append("</b>");
		show_map_and_markers_from_post(fw, map_name, Collections.<String>emptyList(), map_name);
		
		if (parts.length > 1)
			fw.append("<br>\n").append(parts[1]);
		final String id = make_module_id(map_name);
		String body = h.insert(fw, id).toString();
		final BlogCreator.Post post = wp.updateBlogPostId(id, map_name, body);
		wp.updateBlogPostIfRequired(post, map_name, body, module_list_category_name, Collections.<String>emptyList());
		return post;

	}

	private ItemCloser generatePages(final BlogCreator wp, File rpanel_index, ImagesInfo scales,
		final FormatProteinNotes format,
		Map<String, ModuleInfo> modules_set
	)
		throws UnsupportedEncodingException, FileNotFoundException, NaviCellException
	{
		final Model model = cd.getSbml().getModel();
		
		for (final EntityBase ent : entityIDToEntityMap.values())
		{
			if (ent instanceof Complex)
			{
				final Complex complex = (Complex)ent;
				final String body = create_complex_body(complex, format, ReactionDisplayType.FirstPass, wp);
				if (body != null)
					complex.setPost(wp.updateBlogPostId(complex.getId(), complex.getName(), body));
			}
			else if (!DEGRADED_CLASS_NAME.equals(ent.getCls()))
			{
				final String body = create_entity_body(format, ent, ReactionDisplayType.FirstPass, wp, null);
				ent.setPost(wp.updateBlogPostId(ent.getId(), ent.getName(), body));
			}
		}

		final ItemCloser right = generate_right_panel_xml(rpanel_index, entityIDToEntityMap, speciesAliases, placeMap, format, wp, cd, blog_name, scales);

		final List<String> modules = new ArrayList<String>();
		for (final ReactionDocument.Reaction r : model.getListOfReactions().getReactionArray())
		{
			modules.clear();
			final String title = r.getId();
			final String body = createReactionBody(r, format, wp);
			final BlogCreator.Post post = wp.updateBlogPostId(r.getId(), title, body);
			
			final String bubble = createReactionBubble(r, post.getPostId(), format, wp);
			reaction_line(right.add(), r, bubble, scales, post.getPostId());
			
			wp.updateBlogPostIfRequired(post, title, body, REACTION_CLASS_NAME, modules);
		}
		
		for (final EntityBase ent : entityIDToEntityMap.values())
		{
			modules.clear();
			if (ent instanceof Complex)
			{
				final Complex complex = (Complex)ent;
				final String body = create_complex_body(complex, format, ReactionDisplayType.SecondPass, wp);
				if (body != null)
				{
					final BlogCreator.Post post = complex.getPost(); // updateBlogPostId(wp, all_posts, complex.getId(), complex.getName(), COMPLEX_CLASS_NAME, body);
					wp.updateBlogPostIfRequired(post, complex.getName(), body, COMPLEX_CLASS_NAME, modules);
				}
			}
			else if (!DEGRADED_CLASS_NAME.equals(ent.getCls()))
			{
//				do_entity(wp, format, xml, all_posts, ent);
				final String body = create_entity_body(format, ent, ReactionDisplayType.SecondPass, wp, modules);
				final BlogCreator.Post post = ent.getPost(); // updateBlogPostId(wp, all_posts, ent.id, ent.label, ent.cls, body);
				wp.updateBlogPostIfRequired(post, ent.getName(), body, ent.getCls(), modules);
			}
		}
		return right;
		
/*		for (final CelldesignerRNA rna : annotation.getCelldesignerListOfRNAs().getCelldesignerRNAArray())
		{
			final Entity ent = entityIDToEntityMap.get(rna.getId());
			if (skip_entity(ent))
				continue;
			do_entity(wp, format, xml, all_posts, ent, rna.getCelldesignerNotes(), false);
		}
		for (final CelldesignerProteinDocument.CelldesignerProtein prot : annotation.getCelldesignerListOfProteins().getCelldesignerProteinArray())
		{
			assert seen.add(prot.getId()) : prot.getId();
			final Entity ent = entityIDToEntityMap.get(prot.getId());
			if (skip_entity(ent))
				continue;
			assert prot.getId().equals(ent.id);
			do_entity(wp, format, xml, all_posts, ent, prot.getCelldesignerNotes(), true);
		}

		for (final CelldesignerGeneDocument.CelldesignerGene gene : annotation.getCelldesignerListOfGenes().getCelldesignerGeneArray())
		{
			final Entity ent = entityIDToEntityMap.get(gene.getId());
			if (skip_entity(ent))
				continue;
			do_entity(wp, format, xml, all_posts, ent, gene.getCelldesignerNotes(), false);
		}
*/		
		
		//FileWriter fw = new FileWriter(path+"\"+folder+".html");
		// First, species
		/*
		Iterator it = species.keySet().iterator();
		while (false && it.hasNext())
		{
			final String id = (String)it.next();
			SpeciesDocument.Species sp = (SpeciesDocument.Species)CellDesigner.entities.get(id);
			String entname = CellDesignerToCytoscapeConverter.getEntityName(sp.getId(),sp.getAnnotation().getCelldesignerSpeciesIdentity(),cd);
			final String name = stripAt(CellDesignerToCytoscapeConverter.getSpeciesName(sp.getAnnotation().getCelldesignerSpeciesIdentity(), sp.getId(), entname, sp.getCompartment(), true, true, "", cd)); //spa.getSpecies();
			
			// gives the class which can be entity type name or complex
			String entity_class = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
			
//			sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference() if protein
//			sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerGeneReference() if gene
//			sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerRnaReference() if rna
//			
//			but if it is a complex
			
			// cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases()
			
			// just generate markers
			
			final String body = createSpeciesBody(modulefolder, all_posts, id, name, sp);
//			write_marker(xml, entity_class, id, name, body);
		}
	*/
		
		/*
		if (false)
		// genes
		for(int i=0;i<annotation.getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			// just generate blog entries
			CelldesignerGeneDocument.CelldesignerGene gene = annotation.getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			FileWriter fw = new FileWriter(path+"/"+folder+"/"+gene.getId()+".html");
			fw.write("<h3>Gene ("+gene.getId()+")</h3>\n");

			Entity ent = (Entity)entities.get(gene.getId());
			
			if(ent!=null)if(ent.standardName!=null)if(ent.label!=null){
				if(ent.standardName.equals(ent.label))
					fw.write("<b><font color=blue>"+gene.getName()+"</font></b>\n");
				else
					fw.write("<b><font color=blue>"+gene.getName()+" ("+ent.standardName+")</font></b>\n");

				String spid = ((SpeciesDocument.Species)ent.species.get(0)).getId();

				Vector aliases = (Vector)speciesAliases.get(spid);
				Vector<Place> places = placeMap.get((String)aliases.get(0));
				Place place = places.get(0);

		//		final String body = createGeneBody(gene);
		//		fw.write(body);
				final String entity_class = "gene";
//				updateBody(wp, all_posts.getPostId(gene.getId()), body);
//				write_marker(xml, entity_class, gene.getId(), gene.getName(), place, body);

				fw.write("\n\n<script>\n");
				fw.write("function getSize() {\n");
				fw.write("var result = {height:0, width:0};\n");

				fw.write("if (parseInt(navigator.appVersion)>3) {\n");
				fw.write("if (navigator.appName==\"Netscape\") {\n");
				fw.write("result.width = top.map.innerWidth-16;\n");
				fw.write("result.height = top.map.innerHeight-16;\n");
				fw.write("}\n");
				fw.write("if (navigator.appName.indexOf(\"Microsoft\")!=-1) {\n");
				fw.write("result.width = top.map.document.body.offsetWidth-20;\n");
				fw.write("result.height = top.map.document.body.offsetHeight-20;\n");
				fw.write("}\n");
				fw.write("}\n");
				fw.write("return result;\n");
				fw.write("}\n");
				fw.write("</script>\n\n");

				fw.close();
			}}
			*/
		/*
		if(false)
		// Generate reactions
		if(reactions!=null)
		for(int i=0;i<reactions.sizeOfReactionArray();i++){
			// generate blobs and blogs
			ReactionDocument.Reaction r = reactions.getReactionArray(i);
			String rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
			FileWriter fw = new FileWriter(path+"/"+folder+"/"+r.getId()+".html");
			
			Vector<Place> places = (Vector)placeMap.get(r.getId());
			float positionx = 0;
			float positiony = 0;
			if(places!=null){
			for(int j=0;j<places.size();j++){
				Place pl = places.get(j);
				positionx+=pl.positionx;
				positiony+=pl.positiony;
			}
			positionx/=places.size();
			positiony/=places.size();
			}
			
			fw.write("<h3>"+rtype+"("+r.getId()+")</h3>\n");
			
			fw.write("<b><font color=blue>"+getReactionString(r, cd, true,true)+"</font></b>\n");
			
			fw.write("<br><small><a href='"+r.getId()+".html' onClick='scale=top.menu.document.forms[\"coords\"].scale.value;top.map.window.scrollTo("+(int)(positionx)+"*scale-getSize().width/2,"+(int)(positiony)+"*scale-getSize().height/2)'><img alt='center map on reaction' border=0 src='center.gif'/> (center map)</a></small>");
			//fw.write("<small><a href='"+r.getId()+".html' onClick='top.map.document.map.src=\"./"+mapfolder+"/"+r.getId()+".png\";top.map.window.scrollTo("+(int)(positionx)+"-getSize().width/2,"+(int)(positiony)+"-getSize().height/2)'>(partial map)</a></small> - ");
			//fw.write("<small><a href='"+r.getId()+".html' onClick='top.map.document.map.src=\""+prname+".png\";top.map.window.scrollTo("+(int)(positionx)+"-getSize().width/2,"+(int)(positiony)+"-getSize().height/2)'>(full map)</a></small>");
			//fw.write("<small><a href='../"+prname+".html' target='map' onClick='top.map.window.scrollTo("+(int)(positionx)+"-getSize().width/2,"+(int)(positiony)+"-getSize().height/2)'>(full map)</a></small>");
			
			//fw.write("<hr><font color=red>Species involved:</font><br>\n");
			if(r.getNotes()!=null){			
				String comment = Utils.getValue(r.getNotes());
				comment = Utils.replaceString(comment, "Notes by CellDesigner", "");
				comment = checkCommentForXREFS(comment);
				fw.write("<hr><font color=red>Comments:</font><br>\n");
				fw.write("<p>"+comment+"\n");
			}
			
			fw.write("<hr><font color=red>In modules:</font><br>\n");

			Vector mnames = getModuleName(module_species, r.getId());
			fw.write("<ol>\n");
			for(int k=0;k<mnames.size();k++){
				String mname = (String)mnames.get(k);
				String mnamec = correctName(mname);
				fw.write("<li>Module: <a href='../"+modulefolder+"/"+subfolder+"/"+mnamec+".html'>"+mname+"</a>\n");
			}
			fw.write("</ol>\n");
			
			fw.write("\n\n<script>\n");
			fw.write("function getSize() {\n");
			fw.write("var result = {height:0, width:0};\n");
			fw.write("if (parseInt(navigator.appVersion)>3) {\n");
			fw.write("if (navigator.appName==\"Netscape\") {\n");
			fw.write("result.width = top.map.innerWidth-16;\n");
			fw.write("result.height = top.map.innerHeight-16;\n");
			fw.write("}\n");
			fw.write("if (navigator.appName.indexOf(\"Microsoft\")!=-1) {\n");
			fw.write("result.width = top.map.document.body.offsetWidth-20;\n");
			fw.write("result.height = top.map.document.body.offsetHeight-20;\n");
			fw.write("}\n");
			fw.write("}\n");
			fw.write("return result;\n");
		    fw.write("}\n");
			fw.write("</script>\n\n");
			
			fw.close();
		}
		*/
		// modules
		/*Iterator keys = module_species.keySet().iterator();
		while(keys.hasNext()){
			String mname = (String)keys.next();
			String mnamec = correctName(mname);
			//System.out.println(mnamec);
			FileWriter fw = new FileWriter(path+"/"+modulefolder+"/"+mnamec+"_module.html");
			fw.write("<h3>Module</h3>\n");
			
			fw.write("<b><font color=blue>"+mname+"</font></b>\n");
			
			//fw.write("<br><small><a href='"+mnamec+".html' onClick='fname=\"./modules/"+mnamec+"\"+top.menu.document.forms[\"coords\"].suffix.value+\".png\";top.map.document.map.src=fname''>(CellDesigner map)</a></small> - ");
			fw.write("<br><small><a href='"+mnamec+"_module.html' onClick='fname=\""+mnamec+"\"+top.menu.document.forms[\"coords\"].suffix.value+\".html\";top.map.window.location=fname''>(CellDesigner map)</a></small> - ");
			//fw.write("<small><a href='"+mname+".html' onClick='top.map.document.map.src=\"./"+modulefolder+"/"+mname+"_cyto.png\";'>(Cytoscape map)</a></small> - ");
			fw.write("<small><a href='../"+modulefolder+"/"+mnamec+"_cyto.html' target='map'>(Cytoscape map)</a></small>");
			//fw.write("<small><a href='"+mname+".html' onClick='top.map.document.map.src=\""+prname+".png\";'>(full map)</a></small>");
			//fw.write("<small><a href='../"+prname+".html' target='map'>(full map)</a></small>");
			
			fw.write("<hr><font color=red>Entities involved:</font><br>\n");
			
			Vector v = new Vector();
			HashMap entitiesMap = new HashMap();
			for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
				CelldesignerProteinDocument.CelldesignerProtein prot = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
				v.add(Utils.getValue(prot.getName()));
				entitiesMap.put(Utils.getValue(prot.getName()),prot);
			}
			for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
				CelldesignerGeneDocument.CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
				v.add(gene.getName()+" (gene)");
				entitiesMap.put(gene.getName()+" (gene)",gene);
			}
			Vector vv = (Vector)module_proteins.get(mname);
			fw.write("<ol>\n");
			for(int i=0;i<vv.size();i++){
				String ename = (String)vv.get(i);
				String eid = null;
				if(entitiesMap.get(ename)!=null){
					eid = ((CelldesignerProteinDocument.CelldesignerProtein)entitiesMap.get(ename)).getId();
				}
				if(entitiesMap.get(ename+" (gene)")!=null){
					eid = ((CelldesignerGeneDocument.CelldesignerGene)entitiesMap.get(ename+" (gene)")).getId();
				}
				//String eid = (String)entitiesMap.get(ename);
				if(eid!=null){
					fw.write("<li><a href='../"+folder+"/"+eid+".html'>"+ename+"</a>\n");
				}
			}
			fw.write("</ol>\n");
			
			//fw.write("<hr><font color=red><a href='modules.html' target='map'>Show full list of modules</a></font><br>\n");
			//fw.write("<hr><font color=red><a onClick='top.map.location.href=\"modules.html\"' href='"+prname+"_modulelist.html'>Show full list of modules</a></font><br>\n");
			
			fw.close();
		}*/
	}
	
	static private List<String> extract_ids(final List<Modification> sps)
	{
		final List<String> markers = new ArrayList<String>();
		for (final Modification sp : sps)
			markers.add(sp.getId());
		return Collections.unmodifiableList(markers);
	}
	
	static final String excerpt_marker = ""; //"<!--more-->\n";
	
	private static StringBuffer create_buffer_for_post_body(final Hasher h)
	{
		return new StringBuffer(excerpt_marker);
	}

	private String create_complex_body(final Complex complex, final FormatProteinNotes format, final ReactionDisplayType pass2, BlogCreator wp)
	{
		if (complex.getComponents().size() < 2)
			return null;

		final Hasher h = new Hasher();
		final StringBuffer fw = create_buffer_for_post_body(h);

		final String human_class = class_name_to_human_name_map.get(COMPLEX_CLASS_NAME);
		fw.append("<b>").append(human_class).append(' ').append(h.add(complex.getName())).append("</b>");
		visible_debug(fw, complex.getId());
		show_shapes_on_map_from_post(h, fw, extract_ids(complex.getModifications()), master_map_name, blog_name);

		fw.append("<hr><b>Complex composition:</b>\n");
		fw.append("<ol>\n");
		for (final Entity component : complex.getComponents())
		{
			fw.append("<li>");
			post_to_post_link_checked(component, makeFoldable(h.add(component.getName())), fw, pass2, wp);
			show_shapes_on_map_from_post(h, fw, extract_ids(component.getModifications()), master_map_name, blog_name);
		}
		fw.append("</ol>\n");

		fw.append("<hr>");
		final ArrayList<Modification> modifications = complex.getModifications();
		format.complex(complex, fw, h, cd, modifications);
		format_modifications(h, fw, true, modifications, pass2, wp);
		participates_in_reactions_split(complex.getModifications(), h, fw, pass2, wp);
		return h.insert(fw, complex.getId()).toString();
	}

	static private String make_complex_name(final ArrayList<Entity> composants)
	{
		final String[] names = new String[composants.size()];
		int i = 0;
		for (final Entity composant : composants)
			names[i++] = composant.label;
		Arrays.sort(names);
		final StringBuffer name_buf = new StringBuffer();
		for (final String name : names)
		{
			if (name_buf.length() != 0)
				name_buf.append(':');
			name_buf.append(name);
		}
		return name_buf.toString();
	}
	
	private StringBuffer formatProducts(StringBuffer fw, ReactionDocument.Reaction r, final Hasher h, ReactionDisplayType pass2, Linker wp)
	{
		return format_reactants_and_products(fw, h, r.getListOfProducts().getSpeciesReferenceArray(), pass2, wp);
	}

	private StringBuffer formatReactants(StringBuffer fw, ReactionDocument.Reaction r, final Hasher h, ReactionDisplayType pass2, Linker wp)
	{
		return format_reactants_and_products(fw, h, r.getListOfReactants().getSpeciesReferenceArray(), pass2, wp);
	}
	
	static private StringBuffer post_to_post_link_checked(final EntityBase e, String anchor, StringBuffer fw, ReactionDisplayType pass2, Linker wp)
	{
		final int postid = e.getPostId();
		if (postid >= 0)
			post_to_post_link(postid, fw, wp);
		else if (pass2 != ReactionDisplayType.FirstPass)
			Utils.eclipseErrorln(e.getId() + " does not have a post");
		fw.append(anchor);
		if (postid >= 0)
			fw.append("</a>");
		else
			fw.append(" <font color='red'>").append(e.getId()).append('=').append(postid).append("</font>");
		return fw;
	}

	private StringBuffer format_reactants_and_products(StringBuffer fw, final Hasher h, final SpeciesReference[] speciesReferenceArray,
		ReactionDisplayType pass2,
		Linker wp)
	{
		boolean first = true;
		for (final SpeciesReference ref : speciesReferenceArray)
		{
			final String species2 = h.add(ref.getSpecies());
			final Modification m = speciesIDToModificationMap.get(species2);
			final EntityBase e = m.getEntityBase();
			assert !(e instanceof Complex) || ((Complex)e).getGood() == null : species2 + " " + m.getId();
			
			if (first)
				first = false;
			else
				fw.append(" + ");
			
			show_links_to_post_and_map(species2, m, fw, h, pass2, wp);
		}
		if (first)
			fw.append("none");
		return fw;
	}
	
	enum ReactionDisplayType
	{
		FirstPass,
		SecondPass,
		ReactionPass
	}
	
	static private StringBuffer html_quote(String s)
	{
		return html_quote(new StringBuffer(), s);
	}
	
	static private StringBuffer html_quote(StringBuffer sb, String s)
	{
		if (s.indexOf('"') == -1)
			return sb.append('"').append(s).append('"');
		if (s.indexOf('\'') == -1)
			return sb.append('\'').append(s).append('\'');
		return sb.append('"').append(s.replace('"', '\'')).append('"');
	}
	
	static private StringBuffer do_span(StringBuffer fw, String class_name, String value)
	{
		fw.append("<span");
		html_quote(fw.append(" class="), class_name);
		html_quote(fw.append(" title="), value);
		return fw.append("></span>");
	}
	
	static private StringBuffer show_map_and_markers_from_post(StringBuffer fw, String map, List<String> entities, String title)
	{
		fw.append(" <a href='/maps/javascript_required.html' class='show_map_and_markers' title=");
		html_quote(fw, title);
		fw.append(">");
		do_span(fw, "map", map);
		for (String e : entities)
			do_span(fw, "entity", e);
		show_map_icon(fw);
		return fw.append("</a>");
	}

	private void show_links_to_post_and_map(final String species_id, final Modification m, StringBuffer fw, final Hasher h,
		ReactionDisplayType pass2, Linker wp)
	{
		if (m.isDegraded())
			fw.append("degraded");
		else if (m.isBad())
			fw.append(m.getId());
		else
		{
			final String name = h.add(m.getName());
			final String folded = makeFoldable(h.add(m.getName()));
			if (pass2 == ReactionDisplayType.ReactionPass)
			{
				assert h == null_hasher;
				show_markers_from_map(name, Arrays.asList(m.getId()), fw);
			}
			else
			{
				post_to_post_link_checked(m.getEntityBase(), folded, fw, pass2, wp);
				show_shapes_on_map(h, fw, m, master_map_name, blog_name);
			}
		}
	}

	private static String titlecase(String str)
	{
		if (str == null || str.isEmpty())
			return str;
		return new StringBuffer(str.length())
			.append(Character.toTitleCase(str.charAt(0)))
			.append(str.substring(1).toLowerCase())
			.toString();
	}
	
	private void formatRegulators(StringBuffer fw, ReactionDocument.Reaction r, Hasher h, ReactionDisplayType pass2, Linker wp)
	{
		final Annotation annotation = r.getAnnotation();
		if (annotation != null)
		{
			final CelldesignerListOfModification modifications = annotation.getCelldesignerListOfModification();
			if (modifications != null)
			{
				final Map<String, ArrayList<String>> type_map = new TreeMap<String, ArrayList<String>>();
				for (final CelldesignerModification modification : modifications.getCelldesignerModificationArray())
				{
					final String regulator = modification.getModifiers();
					final String type = titlecase(modification.getType());
					ArrayList<String> l = type_map.get(type);
					if (l == null)
						type_map.put(type, l = new ArrayList<String>());
					l.add(regulator);
				}

				if (!type_map.isEmpty())
				{
					fw.append("<dl>");
					for (final Entry<String, ArrayList<String>> g : type_map.entrySet())
					{
						fw.append("<dt>").append(h.add(g.getKey())).append("</dt><dd><ol>");
						final ArrayList<String> regulators = g.getValue();
						Collections.sort(regulators);
						for (final String v : regulators)
							show_regulators_in_post(fw.append("<li>"), h, v, pass2, wp);
						fw.append("</ol></dd>\n");
					}
					fw.append("</dl>");
				}
			}
		}
	}

	private StringBuffer show_regulators_in_post(StringBuffer fw, Hasher h, final String species_list, ReactionDisplayType pass2, Linker wp)
	{
		boolean first = true;
		for (final String sp : species_list.split(","))
		{
			if (first)
				first = false;
			else
				fw.append(" and ");
			final Modification m = speciesIDToModificationMap.get(sp);
			show_links_to_post_and_map(sp, m, fw, h, pass2, wp);
		}
		return fw;
	}
	
	private static String onclick_before = "<a href=\"javascript_required.html\" onclick='try { ";
	private static String onclick_after = " } catch (e) {}; return false;'";
	
	private String createReactionBubble(ReactionDocument.Reaction r, int post_id, FormatProteinNotes format, Linker wp)
	{
		final Hasher h = null_hasher;
		final StringBuffer fw = new StringBuffer();
		
		reaction_header(r, h, fw);
		bubble_to_post_link_with_anchor(post_id, fw);
		fw.append("\n<br>");
		
		reaction_body(r, format, h, fw, ReactionDisplayType.ReactionPass, wp);
		
		return fw.toString();
	}

	private String createReactionBody(ReactionDocument.Reaction r, FormatProteinNotes format, Linker wp)
	{
		final String id = r.getId();
		final Hasher h = new Hasher();
		final StringBuffer fw = create_buffer_for_post_body(h);
		
		reaction_header(r, h, fw);
		show_reaction_on_map(r, fw);
		fw.append("\n<br>");
		
		reaction_body(r, format, h, fw, ReactionDisplayType.SecondPass, wp);
		
		return h.insert(fw, id).toString();
	}

	private void reaction_body(ReactionDocument.Reaction r, FormatProteinNotes format, final Hasher h, final StringBuffer fw, final ReactionDisplayType pass, Linker wp)
	{
		show_reaction(r, h, fw, pass, null, wp);
		fw.append("\n<br><b>Reaction regulators:</b>\n");
		formatRegulators(fw, r, h, pass, wp);
		fw.append("\n");
		switch (pass)
		{
		case SecondPass: format.pmid_post(r, fw, h, cd);
			break;
		case ReactionPass:
			format.pmid_bubble(r, fw, cd);
			
		}
		fw.append("\n");
	}

	private StringBuffer reaction_header(ReactionDocument.Reaction r, final Hasher h, final StringBuffer fw)
	{
		final String rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
		return fw.append("Reaction ").append(h.add(rtype.toLowerCase())).append(' ').append(r.getId());
	}

	private StringBuffer show_reaction_on_map(ReactionDocument.Reaction r, final StringBuffer fw)
	{
		return show_map_and_markers_from_post(fw, master_map_name, Arrays.asList(r.getId()), r.getId());
	}

	static private void show_map_icon(final StringBuffer fw)
	{
		fw.append("<img border='0' src=");
		html_quote(fw, icons_directory + "/misc/map.png");
		fw.append(" alt='map'>");
	}

	private StringBuffer show_reaction(ReactionDocument.Reaction r, final Hasher h, final StringBuffer fw, ReactionDisplayType pass2,
		BlogCreator.Post post, Linker wp)
	{
		formatReactants(fw, r, h, pass2, wp);
		fw.append(" ");
		if (post != null)
			post_to_post_link(post.getPostId(), fw, wp);
		fw.append("&rarr;");
		if (post != null)
		{
			fw.append("</a>");
			show_reaction_on_map(r, fw);
		}
		fw.append(" ");
		return formatProducts(fw, r, h, pass2, wp);
	}

	static void debugMessage(String m)
	{
		Utils.eclipseParentPrintln(m);
	}
	static void errorMessage(String m)
	{
		Utils.eclipseParentErrorln(m);
	}
	private static final boolean make_blog = true;
	
	private static void verbose(String s)
	{
		Utils.eclipseParentPrintln(s);
	}
	
	static final String head_leadin = "<!-- hash=";
	static final String head_seperator = ",";

	static private class Hasher
	{
		// http://stackoverflow.com/questions/415953/generate-md5-hash-in-java
		final java.security.MessageDigest md;
		private Hasher()
		{
			java.security.MessageDigest md2;
			try
			{
				md2 = java.security.MessageDigest.getInstance("MD5");
			}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
				md2 = null;
			}
			md = md2;
		}

		String add(String v)
		{
			try
			{
				md.update(v.getBytes("UTF-8"));
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			return v;
		}

		String getHash()
		{
			return new java.math.BigInteger(1, md.digest()).toString(Character.MAX_RADIX);
		}
		StringBuffer insert(StringBuffer sb, String id)
		{
			return sb.insert(0, get_hash(id));
		}
		private String get_hash(String id)
		{
			return head_leadin + id + head_seperator + getHash() + " -->";
		}
	}
	private static StringBuffer add_link(final StringBuffer res, String tag, String value, String url)
	{
		res.append("<a target='_blank' href='http://");
		res.append(url);
		res.append(value).append("'>").append(tag).append(":").append(value).append("</a>");
		return res;
	}
	
		/*
	private static StringBuilder add_links(String comment, final StringBuilder res, Hasher h, Entity ent){
		if (comment == null)
			return res;
		Pattern pat = Pattern.compile("(HUGO|UNIPROT|PMID|PATHWAY|CHECKPOINT|LAYER):([a-zA-Z0-9]+)");
		
		String[][] urls = {
			{ "HUGO", "www.ncbi.nlm.nih.gov/sites/entrez?cmd=search&db=gene&dopt=full_report&term=" },
			{ "PMID", "www.ncbi.nlm.nih.gov/sites/entrez?Db=pubmed&Cmd=ShowDetailView&TermToSearch=" },
			{ "UNIPROT", "www.expasy.org/uniprot/" },
		};
		
		int prev = 0;
		for (Matcher m = pat.matcher(comment); m.find();)
		{
			res.append(comment, prev, m.start());
			prev = m.end();
			if (Arrays.binarySearch(layer_tags, m.group(1)) >= 0)
				show_shapes_on_map(h, res, ent, m.group(1), m.group(2));
			else
			{
				boolean done = false;
				for (String[] url : urls)
				{
					if (url[0].equals(m.group(1)))
					{
				//		add_link(res, m, url[1]);
						done = true;
						break;
					}
				}

				if (!done)
				{
					res.append("<b>").append(comment, m.start(1), m.end(1)).append("</b>");
					res.append(":");
					res.append("<i>").append(comment, m.start(2), m.end(2)).append("</i>");
				}

			}
		}
		res.append(comment, prev, comment.length());
		if (true) return res;
		
		Vector refs = new Vector();
		try{

			String dbid = "";
			StringTokenizer st = new StringTokenizer(comment," >:;\r\n");
			String s = "";
			while(st.hasMoreTokens()){
				String ss = st.nextToken();
				//System.out.println(ss);
				if(ss.toLowerCase().equals("pmid")){
					if(st.hasMoreTokens()){
						dbid = st.nextToken();
						res.append("<a target='_blank' href='http://www.ncbi.nlm.nih.gov/sites/entrez?Db=pubmed&Cmd=ShowDetailView&TermToSearch=").append(dbid).append("'>PMID:").append(dbid).append("</a> ");
					}
				}else if(ss.toLowerCase().equals("hugo")){
					if(st.hasMoreTokens()){
						dbid = st.nextToken();
						res.append("<a target='_blank' href='http://www.ncbi.nlm.nih.gov/sites/entrez?cmd=search&db=gene&dopt=full_report&term=").append(dbid).append("'>HUGO:").append(dbid).append("</a> ");
					}
				}else if(ss.toLowerCase().equals("uniprot")){
					while(st.hasMoreTokens()){
						dbid = st.nextToken();
						if(dbid.length()!=6){
							res.append(dbid).append(" ");
							break;
						}else
							res.append("<a target='_blank' href='http://www.expasy.org/uniprot/").append(dbid).append("'>UNIPROT:").append(dbid).append("</a> ");
					}
				}
				else
					res.append(ss).append(" ");
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
		*/
	
	static private class FormatProteinNotesBase
	{
		final private static String[] layer_tags = new String[]{ "CC_phase", "LAYER", "CHECKPOINT", "PATHWAY", "MODULE" };
		static
		{
			Arrays.sort(layer_tags);
		}
		protected static final String[] pmid_rule = {"PMID", "www.ncbi.nlm.nih.gov/sites/entrez?Db=pubmed&amp;Cmd=ShowDetailView&amp;TermToSearch=", "[0-9]+" };
		
		protected static final String[][] urls = {
//			{ "HUGO", "www.ncbi.nlm.nih.gov/sites/entrez?cmd=search&db=gene&dopt=full_report&term=" },
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
	
	static private class FormatProteinNotes extends FormatProteinNotesBase
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
						sb.append(makeFoldable(sp.getName()));
						visible_debug(sb, sp.getId());
						sb.append("\n").append(notes).append("\n");
					}
				}
			}
			return sb.toString();
		}
		interface ShowShapesOnMap
		{
			StringBuffer show_shapes_on_map(final Hasher h, StringBuffer fw, List<String> sps, final String map_name, final String blog_name);
		}
		
		static final ShowShapesOnMap show_shapes_on_map_from_post = new ShowShapesOnMap()
		{
			@Override
			public StringBuffer show_shapes_on_map(Hasher h, StringBuffer fw, List<String> sps, String map_name, String blog_name)
			{
				return ProduceClickableMap.show_shapes_on_map_from_post(h, fw, sps, map_name, blog_name);
			}
		};
		static final ShowShapesOnMap show_shapes_on_map_from_bubble = new ShowShapesOnMap()
		{
			@Override
			public StringBuffer show_shapes_on_map(Hasher h, StringBuffer fw, List<String> sps, String map_name, String blog_name)
			{
				return ProduceClickableMap.show_shapes_on_map_from_bubble(h, fw, sps, map_name, blog_name);
			}
		};
		StringBuffer pmid_post(ReactionDocument.Reaction r, StringBuffer fw, Hasher h, SbmlDocument cd)
		{
			final Notes notes = r.getNotes();
			return notes == null ? fw : format(Utils.getValue(notes), fw, h, Arrays.asList(r.getId()), pat_pmid, cd, null, show_shapes_on_map_from_post, null);
		}
		StringBuffer pmid_bubble(ReactionDocument.Reaction r, StringBuffer fw, SbmlDocument cd)
		{
			final Notes notes = r.getNotes();
			return notes == null ? fw : format(Utils.getValue(notes), fw, null_hasher, Arrays.asList(r.getId()), pat_pmid, cd, null, show_shapes_on_map_from_bubble, null);
		}
		StringBuffer bubble(final StringBuffer res, String comment, List<Modification> modifs, SbmlDocument cd)
		{
			return format(comment, res, null_hasher, extract_ids(modifs), pat_bubble, cd, null, show_shapes_on_map_from_bubble, null);
		}
		StringBuffer complex(Complex complex, final StringBuffer res, Hasher h, SbmlDocument cd, List<Modification> modifications)
		{
			return format(null, res, h, extract_ids(complex.getModifications()), pat_generic, cd, modifications, show_shapes_on_map_from_post, null);
		}
		StringBuffer full(final StringBuffer res, Hasher h, EntityBase ent, SbmlDocument cd, List<Modification> modifications, List<String> modules_found)
		{
			return format(ent.getComment(), res, h, extract_ids(ent.getModifications()), pat_generic, cd, modifications, show_shapes_on_map_from_post, modules_found).append('\n');
		}
		private StringBuffer format(String note, final StringBuffer res, Hasher h,
			List<String> all,
			Pattern pat,
			SbmlDocument cd,
			List<Modification> comments,
			ShowShapesOnMap show_shapes_on_map,
			List<String> modules_found
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
							show_shapes_on_map.show_shapes_on_map(h, res, all, arg, blog_name);
						}
					}
					else
					{	
						boolean done = false;
						for (final String[] entry : urls)
							if (entry[0].equals(tag))
							{
								m.appendReplacement(res, "");
								add_link(res, tag, m.group(offset + 2), entry[1]);
								done = true;
								break;
							}

						if (!done)
							m.appendReplacement(res, "<em>$0</em>");
					}
				}
			}
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
/*	
	private interface CDObject
	{
		String getId();
		CelldesignerNotes getCelldesignerNotes();
	};
	private static CDObject makeCDObject(CelldesignerGeneDocument.CelldesignerGene gene)
	{
		return new Gene2CDObject(gene);
	}
	private static CDObject makeCDObject(CelldesignerProteinDocument.CelldesignerProtein protein)
	{
		return new Protein2CDObject(protein);
	}
	private static CDObject makeCDObject(final CelldesignerRNA v)
	{
		return new RNA2CDObject(v);
	}
	private static class Gene2CDObject implements CDObject
	{
		private CelldesignerGeneDocument.CelldesignerGene gene;
		Gene2CDObject(CelldesignerGeneDocument.CelldesignerGene gene)
		{
			this.gene = gene;
		}
		@Override
		public String getId()
		{
			return gene.getId();
		}
		@Override
		public CelldesignerNotes getCelldesignerNotes()
		{
			return gene.getCelldesignerNotes();
		}
	};
	private static class Protein2CDObject implements CDObject
	{
		private CelldesignerProtein protein;

		Protein2CDObject(CelldesignerProteinDocument.CelldesignerProtein protein)
		{
			this.protein = protein;
		}
		@Override
		public String getId()
		{
			return protein.getId();
		}
		@Override
		public CelldesignerNotes getCelldesignerNotes()
		{
			return protein.getCelldesignerNotes();
		}
	};
	private static class RNA2CDObject implements CDObject
	{
		private CelldesignerRNA v;

		RNA2CDObject(CelldesignerRNA v)
		{
			this.v = v;
		}
		@Override
		public String getId()
		{
			return v.getId();
		}

		@Override
		public CelldesignerNotes getCelldesignerNotes()
		{
			return v.getCelldesignerNotes();
		}
	};
	*/
	
	static private final Hasher null_hasher = new Hasher()
	{
		@Override String add(String s) { return s; }
	};
	
	private static StringBuffer bubble_to_post_link_with_anchor(int post_id, final StringBuffer notes)
	{
		notes.append(" ");
		bubble_to_post_link(post_id, notes);
		notes.append("<img border='0' src=");
		html_quote(notes, blog_icon);
		return notes.append(" alt='blog'>").append("</a>");
	}
	
	static String href(int post_id, String text, BlogCreator wp)
	{
		return add_href(new StringBuffer(), post_id, text, wp).toString();
	}
	
	static StringBuffer add_href(StringBuffer fw, int post_id, String text, BlogCreator wp)
	{
		return post_to_post_link(post_id, fw, wp).append(text).append("</a>");
	}

/*	static StringBuffer post_link_base(int post_id, final StringBuffer notes)
	{
		return notes.append("index.php?p=").append(post_id);
	}
*/
	static private StringBuffer post_to_post_link(int post_id, final StringBuffer notes, Linker wp)
	{
		notes.append("<a href=\"");
		wp.post_link_base(post_id, notes);
		return notes.append("\">");
	}

	private static StringBuffer bubble_to_post_link(int post_id, final StringBuffer notes)
	{
//		return notes.append("<a href=\"/annotations/" + blog_name + "?p=").append(post_id).append("\" target=\"blog_").append(blog_name).append("\">");
		return notes.append(onclick_before)
			.append("show_blog(")
			.append(post_id)
			.append(");")
			.append(onclick_after)
			.append(" title=\"post ")
			.append(post_id)
			.append("\">");
	}
	/*
	private void create_entity_marker(FormatProteinNotes format, MarkerManager markers, int post_id, Notes celldesignerNotes, Entity ent) throws IOException
	{
		final String notes = celldesignerNotes == null ? null :Utils.getValue(celldesignerNotes);
		create_entity_marker(format, markers, post_id, ent);
	}
	
	private void create_entity_marker(FormatProteinNotes format, MarkerManager markers, int post_id, CelldesignerNotes celldesignerNotes, Entity ent) throws IOException
	{
		final String notes = celldesignerNotes == null ? null :Utils.getValue(celldesignerNotes);
		create_entity_marker(format, markers, post_id, ent);
	}
	*/
	private static final String js_show_markers = onclick_before + "show_markers(";
	
	static private StringBuffer split_complex_for_marker(final Modification modification, final String name, final StringBuffer fw)
	{
		final Complex complex = modification.getComplex();
		if (complex == null)
			fw.append(name);
		else
		{
			boolean first = true;
			final String csep = ":";
			composants : for (final String s : name.split(csep))
			{
				final char sep = '|';
				if (first)
					first = false;
				else
					fw.append(csep);
				final String[] t = s.split("\\" + sep, 2);
				for (final Entity m : complex.getComponents())
					if (m.getName().trim().replace("(", "").replace(")","").equals(t[0].trim().replace("(", "").replace(")","")))
					{
						show_markers_from_map(m, fw);
//						fw.append('[').append(t[0]).append(']');
						if (t.length == 2)
							fw.append(sep).append(t[1]);
						continue composants;
					}
				Utils.eclipseError("for " + name + " " + complex.getId() + " did not find \"" + t[0] + "\" in");
				for (final Entity m : complex.getComponents())
					System.err.print(" \"" + m.getName()+"\"");
				System.err.println();
					
//				assert false : name + " " + t[0];
			}
		}
		return fw;
	}
	
	private static String create_entity_bubble(final Modification modification, FormatProteinNotes format, int post_id, EntityBase ent, SbmlDocument cd, String blog_name)
	{
		final ArrayList<Modification> one_mod = new ArrayList<Modification>(1);
		one_mod.add(null);
		
		final StringBuffer body_buf = new StringBuffer();
		body_buf.append("<b><big>");
		final String human_cls = class_name_to_human_name_map.get(ent.getCls());
		body_buf.append(human_cls == null ? ent.getCls() : human_cls);
		visible_debug(body_buf, ent.getId());
		body_buf.append("<br>\n");
		
		show_markers_from_map(ent, body_buf);
		bubble_to_post_link_with_anchor(post_id, body_buf);
		body_buf.append("</big></b>");
		body_buf.append("\n<p>");
		
		one_mod.set(0, modification);
		format.bubble(body_buf, ent.getComment(), ent.getModifications(), cd);
		
		body_buf.append("<hr>\n<b>Modification:</b>");
		visible_debug(body_buf, modification.getId());
		body_buf.append("<br>\n");
		final int pos = modification.getName().lastIndexOf('@');
		if (pos <= 0)
			split_complex_for_marker(modification, modification.getName(), body_buf);
		else
			split_complex_for_marker(modification, modification.getName().substring(0, pos), body_buf).append("<br>\nin ").append(modification.getName().substring(pos + 1));
		body_buf.append("<br>\n");
	
		format.bubble(body_buf, modification.getNotes(), Arrays.asList(modification), cd);
		return body_buf.toString();
	}

	static private void show_markers_from_map(EntityBase ent, final StringBuffer fw)
	{
		show_markers_from_map(ent.getName(), extract_ids(ent.getModifications()), fw);
	}
	
	static private void show_markers_from_map(String name, List<String> markers, final StringBuffer fw)
	{
		
		final String title = modifications_title(null_hasher, markers);
		
		final String folded = makeFoldable(name);
		
		if (markers.isEmpty())
			fw.append(folded);
		else
		{
			fw.append(js_show_markers).append("[");
			int n = 0;
			for (final String s : markers)
				html_quote(fw.append(n++ > 0 ? ", " : ""), s);
			fw.append("])")
				.append(onclick_after)
				.append(" title=\"")
				.append(title)
				.append("\">")
				.append(folded)
				.append("</a>");
		}
		
		
		
//		if (show_modifications(null_hasher, body_buf, ent.getModifications(), title, js_show_markers))
//			body_buf.append(ent.getName());
//		else
//			body_buf.append(")").append(onclick_after).append(" title=\"").append(title).append("\">").append(ent.getName()).append("</a>");
	}

	private String create_entity_body(final FormatProteinNotes format, final EntityBase ent, ReactionDisplayType pass2, BlogCreator wp, List<String> modules)
	{
		final Hasher h = new Hasher();
		final StringBuffer fw = create_buffer_for_post_body(h);
		
		final String human = class_name_to_human_name_map.get(h.add(ent.getCls()));
		fw.append("<b>").append(human == null ? ent.getCls() : human).append(" ").append(h.add(ent.getName())).append("</b> ");
		show_shapes_on_master_map(h, fw, ent);
		visible_debug(fw, ent.getId());
		fw.append("<Br>");
		format.full(fw, h, ent, cd, ent.getPostTranslational(), modules);
		format_modifications(h, fw, true, ent.getModifications(), pass2, wp);

		participates_in_reactions_split(ent.getModifications(), h, fw, pass2, wp);

		return h.insert(fw, ent.getId()).toString();
	}
	
	private final static Comparator<Reaction> reaction_comparator = new Comparator<Reaction>()
	{
		@Override
		public int compare(final Reaction arg0, final Reaction arg1)
		{
			return arg0.getId().compareTo(arg1.getId());
		}
	};
	
	private static boolean is_catalyser(String species_id, ReactionDocument.Reaction r)
	{
		final String id = species_id;
		final ListOfModifiers listOfModifiers = r.getListOfModifiers();
		if (listOfModifiers != null)
		{
			final ModifierSpeciesReference[] modifiers = listOfModifiers.getModifierSpeciesReferenceArray();
			if (modifiers != null)
				for (ModifierSpeciesReference modifier : modifiers)
				{
					if (modifier.getSpecies().equals(id))
						return true;
				}
		}
		return false;
	}

	private void participates_in_reactions_split(final List<Modification> arrayList, final Hasher h, final StringBuffer fw, ReactionDisplayType pass2, BlogCreator wp)
	{
		final ArrayList<ReactionDocument.Reaction> catalysers = new ArrayList<ReactionDocument.Reaction>();
		final ArrayList<ReactionDocument.Reaction> others = new ArrayList<ReactionDocument.Reaction>();
		
		for (final Modification sp : arrayList)
		{
			final String id = sp.getId();
			final Vector<ReactionDocument.Reaction> v = speciesInReactions.get(id);
			if (v != null)
				for (final ReactionDocument.Reaction r : v)
				{
					if (is_catalyser(sp.getModificationId(), r))
						catalysers.add(r);
					else
						others.add(r);
				}
		}
		fw.append(heading_font_on).append("Participates in reactions:").append(heading_font_off).append("<br>");
		show_reactions(others, "Reactant or Product", fw, h, pass2, wp);
		show_reactions(catalysers, "Catalyser", fw, h, pass2, wp);
	}

	private void show_reactions(ArrayList<ReactionDocument.Reaction> reactions, String as, final StringBuffer fw, final Hasher h, ReactionDisplayType pass2, BlogCreator wp)
	{
		Collections.sort(reactions, reaction_comparator);
		if (as != null)
			fw.append("<b>").append("As ").append(as).append(":").append("</b>");
		fw.append("<ol>");
		ReactionDocument.Reaction previous = null;
		for (final ReactionDocument.Reaction r : reactions)
		{
			if (r != previous)
			{
				final BlogCreator.Post post = wp.lookup(r.getId());
				if (post == null && pass2 != ReactionDisplayType.FirstPass)
					Utils.eclipsePrintln("missing post for reaction " + r.getId());
				show_reaction(r, h, fw.append("<li>"), pass2, post, wp).append("\n");
			}
			previous = r;
		}
		fw.append("</ol>");
	}
	
	private static String makeFoldable(final String s)
	{
//		String code = "<wbr>";
		String code = "&#8203;";
		return s.replace(":", ":" + code).replace("|", "|" + code);
	}
	static private class Modification
	{
		String name_;
		final String modification_id;
		final String cls;
		EntityBase entity;
		private Complex complex;
		final String notes;
		String getName()
		{
			assert !isDegraded() : modification_id + " " + getEntityBase().getId();
			assert !isBad() : modification_id + " " + getEntityBase().getId();
			if (name_ == null)
			{
//				name_ = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd, modification_id, true, true);
				assert name_ != null && !name_.isEmpty() : modification_id;
			}
			return name_;
		}
		void calculateName(SbmlDocument cd)
		{
			if (name_ == null && !isDegraded() && !isBad())
			{
				name_ = CellDesignerToCytoscapeConverter.convertSpeciesToName(cd, modification_id, true, true);
				assert name_ != null && !name_.isEmpty() : modification_id;
			}
		}
		public boolean isBad()
		{
			return getEntityBase().isBad();
		}
		public void updateIfNotGood()
		{
			if (complex != null && complex.getGood() != null)
			{
				assert entity == complex;
				entity = complex = complex.getGood();
				assert complex.getGood() == null : modification_id;
			}
		}
		boolean isDegraded()
		{
			return entity.isDegraded();
		}
		Complex getComplex()
		{
			return complex;
		}
		public EntityBase getEntityBase()
		{
			return entity;
		}
		public String getNotes()
		{
			return notes;
		}
		public String getModificationId()
		{
			return modification_id;
		}
		private String create_notes(String s)
		{
			return (s == null) ? null : s.trim();
		}
		Modification(SpeciesDocument.Species modification, final EntityBase e, SbmlDocument cd)
		{
			assert !(e instanceof Complex) : modification.getId();
			this.modification_id = modification.getId();
			this.name_ = null;
				
			this.cls = Utils.getValue(modification.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
			this.entity = e;
			this.notes = create_notes(Utils.getValue(modification.getNotes()));
			this.complex = null;
		}
		Modification(SpeciesDocument.Species modification, final Complex c, SbmlDocument cd)
		{
			this.modification_id = modification.getId();
			this.name_ = null;
			this.cls = Utils.getValue(modification.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
			this.entity = c;
			notes = create_notes(Utils.getValue(modification.getNotes()));
			this.complex = c;
		}
		public Modification(CelldesignerSpecies modification, Complex e, SbmlDocument cd)
		{
			this.modification_id = modification.getId();
			this.name_ = null;
			this.cls = Utils.getValue(modification.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
			this.entity = e;
			notes = create_notes(Utils.getValue(modification.getCelldesignerNotes()));
			this.complex = e;
		}
		String getCls() { return cls; }
		private String getId() { return modification_id; }
		public boolean isComplex()
		{
			return getName().indexOf(":") >= 0;
		}
		public int cntComplexes()
		{
			return count_chars(':');
		}
		public int cntModifications()
		{
			return count_chars('|');
		}
		private int count_chars(char c)
		{
			int n = 0;
			int pos = -1;
			while ((pos = getName().indexOf(c, pos + 1)) >= 0)
				n++;
			return n;
		}
		public String getCompartment()
		{
			int pos = getName().indexOf('@');
			return pos < 0 ? "" : getName().substring(pos + 1);
		}
		public Vector<String> getShapeIds(Map<String, Vector<String>> speciesAliases)
		{
			return speciesAliases.get(getId());
		}
		public StringBuffer add_link_to_markers(StringBuffer fw, Hasher h, boolean link, ReactionDisplayType pass2, String blog_name, Linker wp)
		{
			final String folded = makeFoldable(h.add(getName()));
			if (link)
				post_to_post_link_checked(getEntityBase(), folded, fw, pass2, wp);
			else
				fw.append(folded);
			
			return show_shapes_on_map(h, fw, this, master_map_name, blog_name);
		}
	};
	
	static final private Comparator<Modification> entry_sort = new Comparator<Modification>()
	{
		int diff(boolean b1, boolean b2)
		{
			return (b1 ? 1 : 0) - (b2 ? 1 : 0);
		}
		@Override
		public int compare(Modification o1, Modification o2)
		{
			final int r = cmp(o1, o2);
			assert r == 0 && cmp(o2, o1) == 0 || r < 0 && cmp(o2, o1) > 0 || r > 0 && cmp(o2, o1) < 0 : o1 + " " + o2;
			return r;
		}
		private int cmp(Modification o1, Modification o2)
		{
			int r;
			if ((r = diff(o1.isComplex(), o2.isComplex())) != 0)
				return r;
			if ((r = o1.getCompartment().compareTo(o2.getCompartment())) != 0)
				return r;
			if ((r = o1.cntComplexes() - o2.cntComplexes()) != 0)
				return r;
			if ((r = o1.cntModifications() - o2.cntModifications()) != 0)
				return r;
			if ((r = o1.getName().length() - o2.getName().length()) != 0)
				return r;
			if ((r = o1.getName().compareTo(o2.getName())) != 0)
				return r;
			return 0;
		}
	};
	static private final String heading_font_on = "<b><font size='+2'>";
	static private final String heading_font_off = "</font></b>";
	
	static private final Comparator<Modification> modification_orderer = new Comparator<Modification>()
	{
		int count(final String s, final char c)
		{
			int n = 0;
			int p = -1;
			while ((p = s.indexOf(c, p + 1)) != -1)
				n++;
			return n;
		}
		int count(final Modification s1, final Modification s2, final char c)
		{
			return count(s1.getName(), c) - count(s2.getName(), c);
		}
		@Override
		public int compare(final Modification o1, final Modification o2)
		{
			final int c1 = count(o1.getName(), ':');
			final int c2 = count(o2.getName(), ':');
			
			int r;
			if ((r = (c1 > 0 ? 1 : 0) - (c2 > 0 ? 1 : 0)) != 0)
				return r;
			
			if ((r = o1.getCompartment().compareTo(o2.getCompartment())) != 0)
				return r;
			if ((r = c1 - c2) != 0)
				return r;
			if ((r = count(o1, o2, '|')) != 0)
				return r;
			if ((r = o1.getName().length() - o2.getName().length()) != 0)
				return r;
			return o1.getName().compareTo(o2.getName());
		}
	};
	
	private static List<Modification> sort_modifications(List<Modification> modifications)
	{
		final List<Modification> copy = new ArrayList<Modification>(modifications);
		Collections.sort(copy, modification_orderer);
		return copy;
	}

	private void format_modifications(final Hasher h, final StringBuffer fw, final boolean show_complexes, ArrayList<Modification> modifications,
		ReactionDisplayType pass2,
		Linker wp)
	{
		fw.append("<hr>");
		fw.append(heading_font_on).append("Modifications:").append(heading_font_off).append("\n");

		final String complexes = "Participates in complexes:";
		boolean complex = false;
		String compartment = null;
		final String list_off = "</ol>";
		for (final Modification m : sort_modifications(modifications))
		{
			final String list_on = "<ol>";
			if (!complex && m.isComplex())
			{
				if (compartment != null)
					fw.append(list_off);
				compartment = null;
				complex = true;
				if (!show_complexes)
					break;
				fw.append(heading_font_on).append(complexes).append(heading_font_off).append("<br>");
			}
			if (!m.getCompartment().equals(compartment))
			{
				if (compartment != null)
					fw.append(list_off);
				compartment = m.getCompartment();
				h.add(compartment);
				fw.append("In compartment: ").append(compartment).append("<br>");
				fw.append(list_on);
			}
			fw.append("<li>");
			m.add_link_to_markers(fw, h, complex, pass2, blog_name, wp);
			fw.append("\n");
		}
		if (compartment != null)
			fw.append(list_off);
		if (!complex)
			fw.append(heading_font_on).append(complexes).append(heading_font_off).append("<ol></ol>");
	}

	final private static String[] layer_tags = new String[]{ "CC_phase", "LAYER", "CHECKPOINT", "PATHWAY" };
	{
		Arrays.sort(layer_tags);
	}
	
	private StringBuffer show_shapes_on_master_map(final Hasher h, StringBuffer fw, final EntityBase ent)
	{
		show_shapes_on_map_from_post(h, fw, extract_ids(ent.getModifications()), master_map_name, blog_name);
		return fw;
	}

	static final private String master_map_name = "master";
	
	private static StringBuffer visible_debug(StringBuffer fw, String v)
	{
		if (true)
			fw.append(" <font size=\"3\" face=\"sans-serif\" color=\"green\">").append(v).append("</font>");
		return fw;
	}
	static private StringBuffer show_shapes_on_map(final Hasher h, StringBuffer fw, Modification modif, final String map_name, final String blog_name)
	{
		return show_shapes_on_map_from_post(h, fw, Arrays.asList(modif.getId()), map_name, blog_name);
	}

	static private StringBuffer show_shapes_on_map_from_bubble(final Hasher h, StringBuffer fw, List<String> markers, final String map_name, final String blog_name)
	{
//		final boolean first = show_modifications(h, fw, sps, title,
//			" " + onclick_before + "show_map_and_markers(\"" + blog_name + "\", \"" + map_name + "\", ");
		
		final String title = modifications_title(h, markers);
		
		if (markers.isEmpty())
			return fw;

		fw.append(onclick_before).append( "show_map_and_markers(");
		html_quote(fw, map_name);
		fw.append(", [");
		int n = 0;
		for (String e : markers)
			html_quote(fw.append(n++ == 0 ? "" : ", "), e);
		fw.append("]);").append(onclick_after);
		
		html_quote(fw.append(" title="), title);
		fw.append(">");
		show_map_icon(fw);
		return fw.append("</a>");
	}

	static private StringBuffer show_shapes_on_map_from_post(final Hasher h, StringBuffer fw, List<String> sps, final String map_name, final String blog_name)
	{
//		final boolean first = show_modifications(h, fw, sps, title,
//			" " + onclick_before + "show_map_and_markers(\"" + blog_name + "\", \"" + map_name + "\", ");
		
		final String title = modifications_title(h, sps);
		show_map_and_markers_from_post(fw, map_name, sps, title);
		
		return fw;
	}

	static private String modifications_title(final Hasher h, final List<String> sps)
	{
		final StringBuffer title = new StringBuffer();
		for (final String sp : sps)
		{
			h.add(sp);
			title.append(title.length() == 0 ? "" : " ").append(sp);
		}
		if (title.length() != 0)
			title.append(" ");
		return title.append(sps.size()).append(" modifs").toString();
	}

	private class Place{
		String id;
		String label;
		String sbmlid;
		float x = 0;
		float y = 0;
		float centerx = 0;
		float centery = 0;
		float positionx = 0;
		float positiony = 0;
		float width = 0;
		float height = 0;
		float radius = 0;
		int type = 0;
		String coords = "";
		public int RECTANGLE = 0;
		public int CIRCLE = 1;
		public int POLY = 2;
	}
	
	static abstract class EntityBase implements Comparable<EntityBase>
	{
		@Override
		public int compareTo(EntityBase arg0)
		{
			return getName().compareTo(arg0.getName());
		}
		public void setPost(BlogCreator posts)
		{
			setPost(posts.lookup(getId()));
		}
		protected BlogCreator.Post post;
		public int getPostId()
		{
			return post == null ? -3 : post.getPostId();
		}
		public List<Modification> getAssociated()
		{
			// TODO Auto-generated method stub
			return Collections.<Modification>emptyList();
		}
		public boolean isBad()
		{
			return false;
		}
		public boolean isDegraded()
		{
			return getCls().equals(DEGRADED_CLASS_NAME);
		}
		public BlogCreator.Post getPost() { return post; }
		public BlogCreator.Post setPost(BlogCreator.Post p) { return post = p; }
		abstract String getComment();
		abstract String getId();
		abstract String getCls();
		abstract String getName();
		private final ArrayList<Modification> modifications = new ArrayList<Modification>();
		public ArrayList<Modification> getModifications()
		{
			return modifications;
		}
		protected void addModification_(Modification sp)
		{
			modifications.add(sp);
		}
		protected void addModification(Modification sp)
		{
			post_translational.add(sp);
			modifications.add(sp);
		}
		private final ArrayList<Modification> post_translational = new ArrayList<Modification>();
		public ArrayList<Modification> getPostTranslational()
		{
			return post_translational;
		}
		final void addPostTranslational(Modification sp)
		{
			assert !COMPLEX_CLASS_NAME.equals((String)sp.getCls());
			for (final Modification y : post_translational)
				assert y.entity != sp.entity;
			post_translational.add(sp);
			modifications.add(sp);
		}
		final void addPostTranslational_(Modification sp)
		{
			post_translational.add(sp);
		}
		private void moveTo(EntityBase good2)
		{
			good2.modifications.addAll(modifications);
			modifications.clear();
			good2.post_translational.addAll(post_translational);
			post_translational.clear();
		}
	}
	
	static class Entity extends EntityBase {
		String id;
		String label;
		String standardName_;
		String cls;
		String comment = "";
		private final ArrayList<Modification> associated = new ArrayList<Modification>();
		Entity() {}
		public String getComment() { return comment; }
		public String getName() { return label; }
		public String getCls() { return cls; }
		@Override
		public ArrayList<Modification> getAssociated()
		{
			return associated;
		}
		public String getId()
		{
			return id;
		}
		Entity(String id, String label, String cls, final CelldesignerNotes notes)
		{
			this(id, label, cls, Utils.getValue(notes));
		}
		Entity(String id, String label, String cls, final String notes)
		{
			assert id.indexOf('<') < 0 && id.indexOf(' ') < 0: id;
			assert !id.startsWith("s") : id;
//			assert !cls.equals(COMPLEX_CLASS_NAME) : id + " " + label;
			this.id = id;
			this.label = label;
			this.cls = cls;
			
			String res = notes == null ? "" : notes.trim();
			standardName_ = getStandardName(res);
			if (standardName_.equals(""))
				standardName_ = label;
			comment = res;
		}
		Entity(String id, String label, String cls, final Notes notes)
		{
			this(id, label, cls, Utils.getValue(notes));
		}
		public Entity(String id2, XmlAnySimpleType name, String cls, CelldesignerNotes notes)
		{
			this(id2, Utils.getValue(name), cls, notes);
		}
		public Entity(String id2, XmlAnySimpleType name, String complexClassName, Notes notes)
		{
			this(id2, Utils.getValue(name), complexClassName, notes);
		}
		public Entity(String id, String label, String cls)
		{
			this(id, label, cls, (String)null);
		}
		public void addAssociated(Modification sp)
		{
			assert COMPLEX_CLASS_NAME.equals(sp.getCls());
			associated.add(sp);
			addModification_(sp);
		}
		protected void addModification(Modification sp)
		{
			if (COMPLEX_CLASS_NAME.equals(sp.getCls()))
				associated.add(sp);
			else
				addPostTranslational_(sp);
			addModification_(sp);
		}
	}
	
	static class Complex extends EntityBase
	{
		public BlogCreator.Post setPost(BlogCreator.Post p) {
			return super.setPost(p); }
		public void setGood(Complex good)
		{
			this.good = good;
			moveTo(good);
		}
		private void moveTo(Complex good2)
		{
			super.moveTo(good2);
			assert components.isEmpty() : id + " " + label;
		}
		public Complex getGood()
		{
			return good;
		}
		private Complex good;
		private String id;
		private String label;
		private final ArrayList<Entity> components = new ArrayList<Entity>();
		ArrayList<Entity> getComponents() { return components; }
		public void addModification(final Modification modification)
		{
			//	assert !COMPLEX_CLASS_NAME.equals(modification.getCls()) : id + " " + modification.getId();
			super.addModification(modification);
			assert label == null : id + " " + label;
		}
		String fixId(final Map<String, String> includedSpeciesToSpeciesMap, Map<String, ArrayList<CelldesignerSpecies>> complexToIncludedSpeciesMap, Map<String, EntityBase> entities)
		{
			final ArrayList<CelldesignerSpecies> list = complexToIncludedSpeciesMap.get(id);
			if (list == null ||list.size() < 2)
				return id;
			final String nid = makeComplexId(list, includedSpeciesToSpeciesMap, entities);
			assert !nid.isEmpty();
			return id = nid;
		}
		public String getId()
		{
			return id;
		}
		Complex(String id)
		{
			assert !id.isEmpty();
			this.id = id;
			label = null;
			good = null;
		}
		Entity addComponent(final EntityBase eb)
		{
			Entity e = (Entity)eb;
			components.add(e);
			return e;
		}
		@Override
		public String getComment()
		{
			return null;
		}
		@Override
		public String getCls()
		{
			return COMPLEX_CLASS_NAME;
		}
		@Override
		public String getName()
		{
			if (label == null)
				label = make_complex_name(components);
			return label;
		}
		public boolean isBad()
		{
			return getComponents().size() <= 1;
		}
	}
	
	static private EntityBase add(final Map<String, EntityBase> entities, final EntityBase ent)
	{
		final int n = entities.size();
		entities.put(ent.getId(), ent);
		assert n + 1 == entities.size() : ent.getId();
		return ent;
	}
	static private Map<String, ArrayList<CelldesignerSpecies>> makeComplexToIncludedSpeciesMap(final SbmlDocument cd)
	{
		final Map<String, ArrayList<CelldesignerSpecies>> map = new HashMap<String, ArrayList<CelldesignerSpecies>>();
		for (final CelldesignerSpecies sp : cd.getSbml().getModel().getAnnotation().getCelldesignerListOfIncludedSpecies().getCelldesignerSpeciesArray())
		{
			if (sp.getCelldesignerAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass().equals(DEGRADED_CLASS_NAME))
				continue;
			final String key = Utils.getValue(sp.getCelldesignerAnnotation().getCelldesignerComplexSpecies());
			ArrayList<CelldesignerSpecies> l = map.get(key);
			if (l == null)
				map.put(key, l = new ArrayList<CelldesignerSpecies>());
			l.add(sp);
		}
		return Collections.unmodifiableMap(map);
	}
	
	private String getReactionString(ReactionDocument.Reaction r, SbmlDocument sbmlDoc, boolean realNames, boolean insertLinks){
		  String reactionString = "";
		  String rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
		  ListOfModifiersDocument.ListOfModifiers lm = r.getListOfModifiers();
		  for(int j=0;j<r.getListOfReactants().sizeOfSpeciesReferenceArray();j++){
		    String s = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
		    // find bubble (marker) for s
		    if(realNames){
		      s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,s,true,true);
		    }
		    if((s!=null)&&(!s.startsWith("null"))){
		    	if(insertLinks)
		    		reactionString+="<a href='"+r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies()+".html'>"+s+"</a>";
		    	else
		    		reactionString+=s;
		    if(j<r.getListOfReactants().sizeOfSpeciesReferenceArray()-1) reactionString+="+";
		    }
		  }
		  if(lm!=null){
		  reactionString+=" - ";
		  for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
		    String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
		    if(realNames){
		      s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,s,true,true);
		    }
		    if((s!=null)&&(!s.startsWith("null"))){
		    //reactionString+=s;
		    	if(insertLinks)		    	
		    		reactionString+="<a href='"+r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies()+".html'>"+s+"</a>";
		    	else
		    		reactionString+=s;
		    if(j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray()-1) reactionString+=" - ";
		    }
		  }}
		  String reaction="-";
		  if(rtype.toLowerCase().indexOf("transcription")>=0)
			  reaction="--";
		  if(rtype.toLowerCase().indexOf("unknown")>=0)
			  reaction+="?";
		  if(rtype.toLowerCase().indexOf("inhibition")>=0)
			  reaction+="|";
		  else
			  reaction+=">";
		  if(rtype.toLowerCase().indexOf("transport")>=0)
			  reaction="-t->";
		  reaction = " "+reaction+" ";
		  reactionString +=reaction;
		  for(int j=0;j<r.getListOfProducts().sizeOfSpeciesReferenceArray();j++){
		    String s = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
		    if(realNames){
		      s = CellDesignerToCytoscapeConverter.convertSpeciesToName(sbmlDoc,s,true,true);
		    }
		    if((s!=null)&&(!s.startsWith("null"))){
		    //reactionString+=s;
		    	if(insertLinks)		
		    		reactionString+="<a href='"+r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies()+".html'>"+s+"</a>";
		    	else
		    		reactionString+=s;
		    if(j<r.getListOfProducts().sizeOfSpeciesReferenceArray()-1) reactionString+="+";
		    }
		  }
		  /*if(lm!=null){
		  reactionString+="+";
		  for(int j=0;j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray();j++){
		    String s = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
		    if(realNames){
		      s = convertSpeciesToName(sbmlDoc,s,true,true);
		    }
		    if((s!=null)&&(!s.startsWith("null"))){
		    reactionString+=s;
		    if(j<r.getListOfModifiers().sizeOfModifierSpeciesReferenceArray()-1) reactionString+="+";
		    }
		  }}*/
		  return reactionString;
		}
	
	private static String getStandardName(String comment){
		String res = "";
		Vector refs = new Vector();
		try{

			String dbid = "";
			StringTokenizer st = new StringTokenizer(comment," >:;\r\n");
			String s = "";
			while(st.hasMoreTokens()){
				String ss = st.nextToken();
				//System.out.println(ss);
				if(ss.toLowerCase().equals("hugo")){
					if(st.hasMoreTokens()){
						dbid = st.nextToken();
						res+=dbid;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	private Pair findCentralPlaceForReaction(ReactionDocument.Reaction r){
		Pair position = new Pair(0f,0f);
		String rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
		Vector<Place> startPoints = new Vector<Place>();
		Vector<Place> endPoints = new Vector<Place>();
		Vector<Place> modPoints = new Vector<Place>();
		
		for(int i=0;i<r.getAnnotation().getCelldesignerBaseReactants().sizeOfCelldesignerBaseReactantArray();i++){
			CelldesignerBaseReactantDocument.CelldesignerBaseReactant react = r.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(i);
			String anchor = null;
			if(react.getCelldesignerLinkAnchor()!=null)
				anchor = react.getCelldesignerLinkAnchor().getPosition().toString();
			String alias = react.getAlias();
			Vector<Place> v = placeMap.get(alias);
			if(v!=null){
				Place place = v.get(0);
				Place point = getAnchorPosition(place,anchor);
				point.centerx = place.x+0.5f*place.width;
				point.centery = place.y+0.5f*place.height;
				startPoints.add(point);
			}else{
				String spid = Utils.getValue(react.getSpecies());
				System.out.println("ERROR: no places found for alias "+alias+", species "+spid+" ("+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd.getSbml(),spid,true,true)+")");
			}
		}
		for(int i=0;i<r.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();i++){
			CelldesignerBaseProductDocument.CelldesignerBaseProduct react = r.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray(i);
			String anchor = null;
			if(react.getCelldesignerLinkAnchor()!=null)
				anchor = react.getCelldesignerLinkAnchor().getPosition().toString();
			String alias = react.getAlias();
			Vector<Place> v = placeMap.get(alias);
			if(v!=null){
				Place place = v.get(0);
				Place point = getAnchorPosition(place,anchor);
				point.centerx = place.x+0.5f*place.width;
				point.centery = place.y+0.5f*place.height;
				endPoints.add(point);
			}else{
				System.out.println("Place is not found for alias "+alias+" in generatePlacesForReaction");
			}
		}
		
		Vector v = new Vector();
		
		if (one_reactant_one_product.contains(rtype)){
			Place axis1 = new Place();
			Place axis2 = new Place();
			if(startPoints.size()>0)if(endPoints.size()>0){
			getCoordAxes(startPoints,endPoints,axis1,axis2,r);
			
			if(r.getAnnotation().getCelldesignerEditPoints()==null){
				Place place = new Place();
				place.id = r.getId();
				place.sbmlid = r.getId();
				place.label = getReactionString(r, cd, true, false);
				place.type = place.POLY;
				place.coords="";
				place.coords+=""+(int)(startPoints.get(0).x*scale-linewidth)+","+(int)(startPoints.get(0).y*scale-linewidth)+",";
				place.coords+=""+(int)(startPoints.get(0).x*scale+linewidth)+","+(int)(startPoints.get(0).y*scale+linewidth)+",";
				place.coords+=""+(int)(startPoints.get(0).x*scale+axis1.x*scale+linewidth)+","+(int)(startPoints.get(0).y*scale+axis1.y*scale+linewidth)+",";			
				place.coords+=""+(int)(startPoints.get(0).x*scale+axis1.x*scale-linewidth)+","+(int)(startPoints.get(0).y*scale+axis1.y*scale-linewidth);
				place.positionx = 0.5f*(startPoints.get(0).x*scale+endPoints.get(0).x*scale);
				place.positiony = 0.5f*(startPoints.get(0).y*scale+endPoints.get(0).y*scale);
				position.o1 = (Float)place.positionx;
				position.o2 = (Float)place.positiony;
				v.add(place);
			}else{
				Vector<Place> points = readEditPoints(Utils.getValue(r.getAnnotation().getCelldesignerEditPoints()));
				addPolyLine(startPoints.get(0).x, startPoints.get(0).y, endPoints.get(0).x, endPoints.get(0).y, points, r, v);
				int kk = -1;
				if(points.size()>0)
					kk = (int)(0.5f*(float)points.size());
				position = getKthPoint(startPoints.get(0).x, startPoints.get(0).y, endPoints.get(0).x, endPoints.get(0).y, points, r, v, kk);
			}
		}}else
		if(rtype.equals("HETERODIMER_ASSOCIATION")){
			Place axis1 = new Place();
			Place axis2 = new Place();
			if(startPoints.size()>=1)if(endPoints.size()>=1){
			getCoordAxes(startPoints,endPoints,axis1,axis2,r);
			Vector<Place> points = readEditPoints(Utils.getValue(r.getAnnotation().getCelldesignerEditPoints()));
			Place central = getAbsolutePosition(startPoints.get(0).centerx,startPoints.get(0).centery,axis1,axis2,points.get(points.size()-1).x,points.get(points.size()-1).y);
			int num0 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum0());
			int num1 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum1());
			int num2 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum2());
			int k=0;
			Vector<Place> ep = new Vector<Place>();
			for(int i=0;i<num0;i++){
				ep.add(points.get(k));
				k++;
			}
			//addPolyLine(central.x,central.y,startPoints.get(0).x,startPoints.get(0).y,ep,r,v);
			ep = new Vector<Place>();
			for(int i=0;i<num1;i++){
				ep.add(points.get(k));
				k++;
			}
			//addPolyLine(central.x,central.y,startPoints.get(1).x,startPoints.get(1).y,ep,r,v);			
			ep = new Vector<Place>();
			for(int i=0;i<num2;i++){
				ep.add(points.get(k));
				k++;
			}
			float x = central.x*0.5f+endPoints.get(0).x*0.5f;
			float y = central.y*0.5f+endPoints.get(0).y*0.5f;
			addPolyLine(central.x,central.y,endPoints.get(0).x,endPoints.get(0).y,ep,r,v);
			int kk = -1;
			if(ep.size()>0)
				kk = (int)(0.5f*(float)ep.size());
			position = getKthPoint(central.x,central.y,endPoints.get(0).x,endPoints.get(0).y,ep,r,v, kk);
			
		}}else
		if(rtype.equals("DISSOCIATION")){
			Place axis1 = new Place();
			Place axis2 = new Place();
			if(startPoints.size()>0)if(endPoints.size()>0){
			getCoordAxes(startPoints,endPoints,axis1,axis2,r);
			Vector<Place> points = readEditPoints(Utils.getValue(r.getAnnotation().getCelldesignerEditPoints()));
			Place central = getAbsolutePosition(startPoints.get(0).centerx,startPoints.get(0).centery,axis1,axis2,points.get(points.size()-1).x,points.get(points.size()-1).y);
			int num0 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum0());
			int num1 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum1());
			int num2 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum2());
			int k=0;
			Vector<Place> ep = new Vector<Place>();
			for(int i=0;i<num0;i++){
				ep.add(points.get(k));
				k++;
			}
			addPolyLine(central.x,central.y,startPoints.get(0).x,startPoints.get(0).y,ep,r,v);
			int kk = -1;
			if(ep.size()>0)
				kk = (int)(0.5f*(float)ep.size());
			position = getKthPoint(central.x,central.y,startPoints.get(0).x,startPoints.get(0).y,ep,r,v,kk);
			
			ep = new Vector<Place>();
			for(int i=0;i<num1;i++){
				ep.add(points.get(k));
				k++;
			}
			//addPolyLine(central.x,central.y,endPoints.get(0).x,endPoints.get(0).y,ep,r,v);			
			ep = new Vector<Place>();
			for(int i=0;i<num2;i++){
				ep.add(points.get(k));
				k++;
			}
			//addPolyLine(central.x,central.y,endPoints.get(1).x,endPoints.get(1).y,ep,r,v);			
		}}else
			Utils.eclipseErrorln("In generatePlacesForReaction "+rtype+" not found for "+r.getId());
		
		if(v.size()>0)
			placeMap.put(r.getId(), v);
		
		
		return position;
	}
	
	private static final Set<String> one_reactant_one_product = Collections.unmodifiableSet
	(
		new HashSet<String>()
		{
			private static final long serialVersionUID = 6409895886399401092L;
			{
				add("STATE_TRANSITION");
				add("TRANSPORT");
				add("TRANSCRIPTIONAL_INHIBITION");
				add("TRANSCRIPTION");
				add("TRANSCRIPTIONAL_ACTIVATION");
				add("UNKNOWN_TRANSITION");
				add("KNOWN_TRANSITION_OMITTED");
				add("TRANSLATIONAL_INHIBITION");
				add("UNKNOWN_CATALYSIS");
				add("TRANSLATION");
				add("NEGATIVE_INFLUENCE");
				add("POSITIVE_INFLUENCE");
				add("UNKNOWN_POSITIVE_INFLUENCE");
				add("UNKNOWN_NEGATIVE_INFLUENCE");
				add("TRUNCATION");
			}
		}
	);

	/* generates a polygon (for an image map) along reactions
	 */
	private void generatePlacesForReaction(ReactionDocument.Reaction r){
		
		final String rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
		//System.out.println(r.getId()+"\t"+rtype);
		
		Vector<Place> startPoints = new Vector<Place>();
		Vector<Place> endPoints = new Vector<Place>();
		for(int i=0;i<r.getAnnotation().getCelldesignerBaseReactants().sizeOfCelldesignerBaseReactantArray();i++){
			CelldesignerBaseReactantDocument.CelldesignerBaseReactant react = r.getAnnotation().getCelldesignerBaseReactants().getCelldesignerBaseReactantArray(i);
			String anchor = null;
			if(react.getCelldesignerLinkAnchor()!=null)
				anchor = react.getCelldesignerLinkAnchor().getPosition().toString();
			String alias = react.getAlias();
			Vector<Place> v = placeMap.get(alias);
			if(v!=null){
				Place place = v.get(0);
				Place point = getAnchorPosition(place,anchor);
				point.centerx = place.x+0.5f*place.width;
				point.centery = place.y+0.5f*place.height;
				startPoints.add(point);
			}else{
				String spid = Utils.getValue(react.getSpecies());
				System.out.println("ERROR: no places found for alias "+alias+", species "+spid+" ("+CellDesignerToCytoscapeConverter.convertSpeciesToName(cd.getSbml(),spid,true,true)+")");
			}
		}
		for(int i=0;i<r.getAnnotation().getCelldesignerBaseProducts().sizeOfCelldesignerBaseProductArray();i++){
			CelldesignerBaseProductDocument.CelldesignerBaseProduct react = r.getAnnotation().getCelldesignerBaseProducts().getCelldesignerBaseProductArray(i);
			String anchor = null;
			if(react.getCelldesignerLinkAnchor()!=null)
				anchor = react.getCelldesignerLinkAnchor().getPosition().toString();
			String alias = react.getAlias();
			Vector<Place> v = placeMap.get(alias);
			if(v!=null){
				Place place = v.get(0);
				Place point = getAnchorPosition(place,anchor);
				point.centerx = place.x+0.5f*place.width;
				point.centery = place.y+0.5f*place.height;
				endPoints.add(point);
			}else{
				System.out.println("Place is not found for alias "+alias+" in generatePlacesForReaction");
			}
		}
		/*for(int i=0;i<r.getAnnotation().getCelldesignerListOfModification().sizeOfCelldesignerModificationArray();i++){
			CelldesignerModificationDocument.CelldesignerModification cdm = r.getAnnotation().getCelldesignerListOfModification().getCelldesignerModificationArray(i);
			String alias = cdm.getAliases();
			Vector<Place> v = placeMap.get(alias);
			cdm.ge
		}*/
		
		
		Vector v = new Vector();
		
		if (one_reactant_one_product.contains(rtype)) {
			Place axis1 = new Place();
			Place axis2 = new Place();
			if(startPoints.size()>0)if(endPoints.size()>0){
			getCoordAxes(startPoints,endPoints,axis1,axis2,r);
			
			if(r.getAnnotation().getCelldesignerEditPoints()==null){
				Place place = new Place();
				place.id = r.getId();
				place.sbmlid = r.getId();
				place.label = getReactionString(r, cd, true, false);
				place.type = place.POLY;
				place.coords="";
				place.coords+=""+(int)(startPoints.get(0).x*scale-linewidth)+","+(int)(startPoints.get(0).y*scale-linewidth)+",";
				place.coords+=""+(int)(startPoints.get(0).x*scale+linewidth)+","+(int)(startPoints.get(0).y*scale+linewidth)+",";
				place.coords+=""+(int)(startPoints.get(0).x*scale+axis1.x*scale+linewidth)+","+(int)(startPoints.get(0).y*scale+axis1.y*scale+linewidth)+",";			
				place.coords+=""+(int)(startPoints.get(0).x*scale+axis1.x*scale-linewidth)+","+(int)(startPoints.get(0).y*scale+axis1.y*scale-linewidth);
				place.positionx = 0.5f*(startPoints.get(0).x*scale+endPoints.get(0).x*scale);
				place.positiony = 0.5f*(startPoints.get(0).y*scale+endPoints.get(0).y*scale);
				v.add(place);
			}else{
				Vector<Place> points = readEditPoints(Utils.getValue(r.getAnnotation().getCelldesignerEditPoints()));
				addPolyLine(startPoints.get(0).x, startPoints.get(0).y, endPoints.get(0).x, endPoints.get(0).y, points, r, v);
				/*points.add(endPoints.get(0));
				Place current = new Place();
				current.x = startPoints.get(0).x;
				current.y = startPoints.get(0).y;
				for(int j=0;j<points.size();j++){
					Place place = new Place();
					place.id = r.getId();
					place.sbmlid = r.getId();
					place.label = getReactionString(r, cd, true, false);
					place.type = place.POLY;
					if(j==points.size()-1){
						place.x = points.get(j).x;
						place.y = points.get(j).y;
					}
					else{
						place.x = startPoints.get(0).x+axis1.x*points.get(j).x+axis2.x*points.get(j).y;
						place.y = startPoints.get(0).y+axis1.y*points.get(j).x+axis2.y*points.get(j).y;
					}
					place.coords="";
					place.coords+=""+(int)(current.x-3)+","+(int)(current.y-3)+",";
					place.coords+=""+(int)(place.x-3)+","+(int)(place.y-3)+",";
					place.coords+=""+(int)(place.x+3)+","+(int)(place.y+3)+",";
					place.coords+=""+(int)(current.x+3)+","+(int)(current.y+3);
					current.x = place.x;
					current.y = place.y;
					v.add(place);
				}*/
			}
		}}else
		if(rtype.equals("HETERODIMER_ASSOCIATION")){
			Place axis1 = new Place();
			Place axis2 = new Place();
			if(startPoints.size()>=1)if(endPoints.size()>=1){
			getCoordAxes(startPoints,endPoints,axis1,axis2,r);
			Vector<Place> points = readEditPoints(Utils.getValue(r.getAnnotation().getCelldesignerEditPoints()));
			Place central = getAbsolutePosition(startPoints.get(0).centerx,startPoints.get(0).centery,axis1,axis2,points.get(points.size()-1).x,points.get(points.size()-1).y);
			int num0 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum0());
			int num1 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum1());
			int num2 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum2());
			int k=0;
			Vector<Place> ep = new Vector<Place>();
			for(int i=0;i<num0;i++){
				ep.add(points.get(k));
				k++;
			}
			addPolyLine(central.x,central.y,startPoints.get(0).x,startPoints.get(0).y,ep,r,v);
			ep = new Vector<Place>();
			for(int i=0;i<num1;i++){
				ep.add(points.get(k));
				k++;
			}
			addPolyLine(central.x,central.y,startPoints.get(1).x,startPoints.get(1).y,ep,r,v);			
			ep = new Vector<Place>();
			for(int i=0;i<num2;i++){
				ep.add(points.get(k));
				k++;
			}
			addPolyLine(central.x,central.y,endPoints.get(0).x,endPoints.get(0).y,ep,r,v);			
		}}else
		if(rtype.equals("DISSOCIATION")){
			Place axis1 = new Place();
			Place axis2 = new Place();
			if(startPoints.size()>0)if(endPoints.size()>0){
			getCoordAxes(startPoints,endPoints,axis1,axis2,r);
			Vector<Place> points = readEditPoints(Utils.getValue(r.getAnnotation().getCelldesignerEditPoints()));
			Place central = getAbsolutePosition(startPoints.get(0).centerx,startPoints.get(0).centery,axis1,axis2,points.get(points.size()-1).x,points.get(points.size()-1).y);
			int num0 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum0());
			int num1 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum1());
			int num2 = Integer.parseInt(r.getAnnotation().getCelldesignerEditPoints().getNum2());
			int k=0;
			Vector<Place> ep = new Vector<Place>();
			for(int i=0;i<num0;i++){
				ep.add(points.get(k));
				k++;
			}
			addPolyLine(central.x,central.y,startPoints.get(0).x,startPoints.get(0).y,ep,r,v);
			ep = new Vector<Place>();
			for(int i=0;i<num1;i++){
				ep.add(points.get(k));
				k++;
			}
			addPolyLine(central.x,central.y,endPoints.get(0).x,endPoints.get(0).y,ep,r,v);			
			ep = new Vector<Place>();
			for(int i=0;i<num2;i++){
				ep.add(points.get(k));
				k++;
			}
			addPolyLine(central.x,central.y,endPoints.get(1).x,endPoints.get(1).y,ep,r,v);			
		}}else
			Utils.eclipseErrorln("In generatePlacesForReaction "+rtype+" not found for "+r.getId());
		
		if(v.size()>0)
			placeMap.put(r.getId(), v);
	}
	
	private Pair getKthPoint(float x1, float y1, float x2, float y2, Vector<Place> editpoints, ReactionDocument.Reaction r, Vector v, int k){
		Pair pos = new Pair(0f,0f);
		x1 = x1*scale;
		y1 = y1*scale;
		x2 = x2*scale;
		y2 = y2*scale;
		Place axis1 = new Place();
		Place axis2 = new Place();
		axis1.x = x2-x1;
		axis1.y = y2-y1;
		axis2.x = -axis1.y;
		axis2.y = axis1.x;
		
		float x = 0f;
		float y = 0f;

		if(k>=0){
			x = x1+axis1.x*editpoints.get(k).x+axis2.x*editpoints.get(k).y;
			y = y1+axis1.y*editpoints.get(k).x+axis2.y*editpoints.get(k).y;
		}else{
			x = (x1+x2)/2f;
			y = (y1+y2)/2f;
		}
		
		pos.o1 = x;
		pos.o2 = y;
		
		return pos;
	}
	
	private void addPolyLine(float x1, float y1, float x2, float y2, Vector<Place> editpoints, ReactionDocument.Reaction r, Vector v){
		x1 = x1*scale;
		y1 = y1*scale;
		x2 = x2*scale;
		y2 = y2*scale;
		Place axis1 = new Place();
		Place axis2 = new Place();
		axis1.x = x2-x1;
		axis1.y = y2-y1;
		axis2.x = -axis1.y;
		axis2.y = axis1.x;
		Place place = new Place();
		place.id = r.getId();
		place.sbmlid = r.getId();
		place.label = getReactionString(r, cd, true, false);
		place.type = place.POLY;
		place.coords="";
		place.coords+=""+(int)(x1-linewidth)+","+(int)(y1-linewidth)+",";
		int n = 1;
		place.positionx+=x1;
		place.positiony+=y1;
		for(int i=0;i<editpoints.size();i++){
			float x = x1+axis1.x*editpoints.get(i).x+axis2.x*editpoints.get(i).y;
			float y = y1+axis1.y*editpoints.get(i).x+axis2.y*editpoints.get(i).y;
			place.coords+=""+(int)(x-linewidth)+","+(int)(y-linewidth)+",";
			place.positionx+=x;
			place.positiony+=y;
			n++;
		}
		place.coords+=""+(int)(x2-linewidth)+","+(int)(y2-linewidth)+",";
		place.coords+=""+(int)(x2+linewidth)+","+(int)(y2+linewidth)+",";
		place.positionx+=x2;
		place.positiony+=y2;
		n++;
		for(int i=editpoints.size()-1;i>=0;i--){
			place.coords+=""+(int)(x1+axis1.x*editpoints.get(i).x+axis2.x*editpoints.get(i).y+linewidth)+","+(int)(y1+axis1.y*editpoints.get(i).x+axis2.y*editpoints.get(i).y+linewidth)+",";
		}
		place.coords+=""+(int)(x1+linewidth)+","+(int)(y1+linewidth);
		place.positionx/=n;
		place.positiony/=n;
		v.add(place);
	}
	
	private Place getAbsolutePosition(float origx, float origy, Place axis1, Place axis2, float x, float y){
		Place place = new Place();
		place.x = origx+axis1.x*x+axis2.x*y;
		place.y = origy+axis1.y*x+axis2.y*y;
		return place;
	}
	
	private void addLineRelativeRelative(float origx, float origy, Place axis1, Place axis2, float x1, float y1, float x2, float y2, ReactionDocument.Reaction r, Vector v){
		Place place = new Place();
		place.id = r.getId();
		place.sbmlid = r.getId();
		place.label = getReactionString(r, cd, true, false);
		place.type = place.POLY;
		place.coords="";
		place.coords+=""+(int)(origx+axis1.x*x1+axis2.x*y1-linewidth)+","+(int)(origy+axis1.y*x1+axis2.y*y1-linewidth)+",";
		place.coords+=""+(int)(origx+axis1.x*x2+axis2.x*y2-linewidth)+","+(int)(origy+axis1.y*x2+axis2.y*y2-linewidth)+",";		
		place.coords+=""+(int)(origx+axis1.x*x2+axis2.x*y2+linewidth)+","+(int)(origy+axis1.y*x2+axis2.y*y2+linewidth)+",";
		place.coords+=""+(int)(origx+axis1.x*x1+axis2.x*y1+linewidth)+","+(int)(origy+axis1.y*x1+axis2.y*y1+linewidth);		
		v.add(place);
	}
	private void addLineRelativeAbsolute(float origx, float origy, Place axis1, Place axis2, float x1, float y1, float x2, float y2, ReactionDocument.Reaction r, Vector v){
		Place place = new Place();
		place.id = r.getId();
		place.sbmlid = r.getId();
		place.label = getReactionString(r, cd, true, false);
		place.type = place.POLY;
		place.coords="";
		place.coords+=""+(int)(origx+axis1.x*x1+axis2.x*y1-linewidth)+","+(int)(origy+axis1.y*x1+axis2.y*y1-linewidth)+",";
		place.coords+=""+(int)(x2-linewidth)+","+(int)(y2-linewidth)+",";		
		place.coords+=""+(int)(x2+linewidth)+","+(int)(y2+linewidth)+",";
		place.coords+=""+(int)(origx+axis1.x*x1+axis2.x*y1+linewidth)+","+(int)(origy+axis1.y*x1+axis2.y*y1+linewidth);		
		v.add(place);
	}
	
	private Vector<Place> readEditPoints(String editS){
		Vector<Place> res = new Vector<Place>();
		StringTokenizer st = new StringTokenizer(editS," ,");
		while(st.hasMoreTokens()){
			Place pl = new Place();
			pl.x = Float.parseFloat(st.nextToken());
			pl.y = Float.parseFloat(st.nextToken());
			res.add(pl);
		}
		return res;
	}
	
	private void getCoordAxes(Vector<Place> starts, Vector<Place> ends, Place x1, Place x2,ReactionDocument.Reaction r){
		String rtype = Utils.getValue(r.getAnnotation().getCelldesignerReactionType());
		if(rtype.equals("STATE_TRANSITION")||rtype.equals("TRANSPORT")||rtype.equals("TRANSCRIPTIONAL_INHIBITION")||rtype.equals("TRANSCRIPTIONAL_ACTIVATION")||rtype.equals("UNKNOWN_TRANSITION")||rtype.equals("KNOWN_TRANSITION_OMITTED")||rtype.equals("TRANSLATIONAL_INHIBITION")||rtype.equals("UNKNOWN_CATALYSIS")){		
		//if(rtype.equals("STATE_TRANSITION")||rtype.equals("TRANSPORT")||){
			x1.x = ends.get(0).x-starts.get(0).x;
			x1.y = ends.get(0).y-starts.get(0).y;
			x2.x = -x1.y;
			x2.y = x1.x;
			float x = -x1.y;
			float y = x1.x;
			//x2.x = x2.x/(float)Math.sqrt(x*x+y*y);
			//x2.y = x2.y/(float)Math.sqrt(x*x+y*y);
		}
		if(rtype.equals("HETERODIMER_ASSOCIATION")){
			x1.x = starts.get(1).centerx-starts.get(0).centerx;
			x1.y = starts.get(1).centery-starts.get(0).centery;
			x2.x = ends.get(0).centerx-starts.get(0).centerx;
			x2.y = ends.get(0).centery-starts.get(0).centery;
		}
		if(rtype.equals("DISSOCIATION")){
			x1.x = ends.get(0).centerx-starts.get(0).centerx;
			x1.y = ends.get(0).centery-starts.get(0).centery;
			x2.x = ends.get(1).centerx-starts.get(0).centerx;
			x2.y = ends.get(1).centery-starts.get(0).centery;
		}
	}
	
	private Place getAnchorPosition(Place place, String anchor){
		Place pl = new Place();
		if(anchor==null){
			pl.x = place.x+0.5f*place.width;
			pl.y = place.y+0.5f*place.height;
		}else{
		if(anchor.equals("E")){
			pl.x = place.x+place.width;
			pl.y = place.y+0.5f*place.height;
		}
		if(anchor.equals("ENE")){
			pl.x = place.x+place.width;
			pl.y = place.y+0.25f*place.height;
		}
		if(anchor.equals("NE")){
			pl.x = place.x+place.width;
			pl.y = place.y;
		}
		if(anchor.equals("ESE")){
			pl.x = place.x+place.width;
			pl.y = place.y+0.75f*place.height;
		}
		if(anchor.equals("SE")){
			pl.x = place.x+place.width;
			pl.y = place.y+place.height;
		}
		if(anchor.equals("W")){
			pl.x = place.x;
			pl.y = place.y+0.5f*place.height;
		}
		if(anchor.equals("WNW")){
			pl.x = place.x;
			pl.y = place.y+0.25f*place.height;
		}
		if(anchor.equals("NW")){
			pl.x = place.x;
			pl.y = place.y;
		}
		if(anchor.equals("WSW")){
			pl.x = place.x;
			pl.y = place.y+0.75f*place.height;
		}
		if(anchor.equals("SW")){
			pl.x = place.x;
			pl.y = place.y+place.height;
		}
		if(anchor.equals("N")){
			pl.x = place.x+0.5f*place.width;
			pl.y = place.y;
		}
		if(anchor.equals("NNW")){
			pl.x = place.x+0.25f*place.width;
			pl.y = place.y;
		}
		if(anchor.equals("NNE")){
			pl.x = place.x+0.75f*place.width;
			pl.y = place.y;
		}
		if(anchor.equals("S")){
			pl.x = place.x+0.5f*place.width;
			pl.y = place.y+place.height;
		}
		if(anchor.equals("SSW")){
			pl.x = place.x+0.25f*place.width;
			pl.y = place.y+place.height;
		}
		if(anchor.equals("SSE")){
			pl.x = place.x+0.75f*place.width;
			pl.y = place.y+place.height;
		}}
		return pl;
	}
	
	/* generates submaps each representing a protein
	
	private void generatePartialMaps(String path, String pathname, String folder) throws Exception{
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray().length;i++){
			CelldesignerSpeciesAliasDocument.CelldesignerSpeciesAlias spa = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfSpeciesAliases().getCelldesignerSpeciesAliasArray(i);
			Object obj = CellDesigner.entities.get(spa.getSpecies());
			if(!obj.getClass().getName().toLowerCase().contains("celldesignerspecies")){			
				species.put(spa.getSpecies(), spa);
			}
			Vector<Entity> v = speciesEntities.get(spa.getSpecies());
			if(v==null) v = new Vector<Entity>();
			v.add(getEntity(spa.getSpecies()));
			speciesEntities.put(spa.getSpecies(),v);
		}
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray().length;i++){
			CelldesignerComplexSpeciesAliasDocument.CelldesignerComplexSpeciesAlias cspa = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfComplexSpeciesAliases().getCelldesignerComplexSpeciesAliasArray(i);
			Object obj = CellDesigner.entities.get(cspa.getSpecies());
			if(!obj.getClass().getName().toLowerCase().contains("celldesignerspecies")){			
				species.put(cspa.getSpecies(), cspa);
			}
			Vector v = speciesEntities.get(cspa.getSpecies());
			if(v==null) v = new Vector();
			v.add(getEntitiesInComplex(cspa.getSpecies())); // should ge put all?
			speciesEntities.put(cspa.getSpecies(),v);
		}
		for(int i=0;i<cd.getSbml().getModel().getListOfReactions().getReactionArray().length;i++){
			ReactionDocument.Reaction r = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			if(r.getListOfReactants()!=null)
			for(int j=0;j<r.getListOfReactants().getSpeciesReferenceArray().length;j++){
				String spid = r.getListOfReactants().getSpeciesReferenceArray(j).getSpecies();
				Vector v = (Vector)speciesInReactions.get(spid);
				if(v==null)
					v = new Vector();
				v.add(r); speciesInReactions.put(spid,v);
			}
			if(r.getListOfProducts()!=null)
			for(int j=0;j<r.getListOfProducts().getSpeciesReferenceArray().length;j++){
				String spid = r.getListOfProducts().getSpeciesReferenceArray(j).getSpecies();
				Vector v = (Vector)speciesInReactions.get(spid);
				if(v==null)
					v = new Vector();
				v.add(r); speciesInReactions.put(spid,v);
			}
			if(r.getListOfModifiers()!=null)
			for(int j=0;j<r.getListOfModifiers().getModifierSpeciesReferenceArray().length;j++){
				String spid = r.getListOfModifiers().getModifierSpeciesReferenceArray(j).getSpecies();
				Vector v = (Vector)speciesInReactions.get(spid);
				if(v==null)
					v = new Vector();
				v.add(r); speciesInReactions.put(spid,v);
			}
		}
		
		// for species
		Iterator it = species.keySet().iterator();
		int kk=0;
		for(int j=0;j<0;j++){
		//while(it.hasNext()){
			String id = (String)it.next();
			SpeciesDocument.Species sp = (SpeciesDocument.Species)CellDesigner.entities.get(id);
//		    System.out.println(""+(kk++)+" "+Utils.getValue(sp.getName()));
			
			if(!Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass()).equals("DEGRADED")){
			Vector species = new Vector();
			Vector reactions = new Vector();
			Vector speciesAliases = new Vector();
			Vector degraded = new Vector();
						
			Vector v = (Vector)speciesInReactions.get(sp.getId());
			if(v!=null)
				for(int i=0;i<v.size();i++){
					ReactionDocument.Reaction r = (ReactionDocument.Reaction)v.get(i);
					reactions.add(r.getId());
				}
			
//			System.out.println(reactions.size());
			CytoscapeToCellDesignerConverter ctc = new CytoscapeToCellDesignerConverter();
			ProduceClickableMap clMap = new ProduceClickableMap();
			HashMap hm = CellDesigner.entities;
		    clMap.loadCellDesigner(path+"/"+pathname+".xml");
			ctc.filterIDsCompleteReactions(clMap.cd, species, speciesAliases, reactions, degraded);
			CellDesigner.saveCellDesigner(clMap.cd, path+"/"+folder+"/"+sp.getId()+".xml");
			CellDesigner.entities = hm;
			}
		}
		// for reactions
		//for(int i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
		for(int i=0;i<0;i++){
			Vector species = new Vector();
			Vector reactions = new Vector();
			Vector speciesAliases = new Vector();
			Vector degraded = new Vector();
			ReactionDocument.Reaction r = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			reactions.add(r.getId());
			HashMap hm = CellDesigner.entities;
			CytoscapeToCellDesignerConverter ctc = new CytoscapeToCellDesignerConverter();
			ProduceClickableMap clMap = new ProduceClickableMap();			
		    clMap.loadCellDesigner(path+"/"+pathname+".xml");
			ctc.filterIDsCompleteReactions(clMap.cd, species, speciesAliases, reactions, degraded);
			CellDesigner.saveCellDesigner(clMap.cd, path+"/"+folder+"/"+r.getId()+".xml");
			CellDesigner.entities = hm;
		}
		// for proteins
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein prot = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			Entity ent = (Entity)entities.get(prot.getId());
			if(ent!=null){

			Vector species = new Vector();
			Vector reactions = new Vector();
			Vector speciesAliases = new Vector();
			Vector degraded = new Vector();
//			System.out.println((i+1)+"\t"+ent.label+"\t"+ent.id+"\t"+ent.species.size());			
			for(int j=0;j<ent.species.size();j++){
				SpeciesDocument.Species sp = (SpeciesDocument.Species)ent.species.get(j);
				Vector v = (Vector)speciesInReactions.get(sp.getId());
				if(v!=null)
					for(int k=0;k<v.size();k++){
						ReactionDocument.Reaction r = (ReactionDocument.Reaction)v.get(k);
						reactions.add(r.getId());
					}
			}
			CytoscapeToCellDesignerConverter ctc = new CytoscapeToCellDesignerConverter();
			ProduceClickableMap clMap = new ProduceClickableMap();
			HashMap hm = CellDesigner.entities;
		    clMap.loadCellDesigner(path+"/"+pathname+".xml");
			ctc.filterIDsCompleteReactions(clMap.cd, species, speciesAliases, reactions, degraded);
			CellDesigner.saveCellDesigner(clMap.cd, path+"/"+folder+"/"+ent.id+".xml");
			CellDesigner.entities = hm;
			}else{
				System.out.println("=============================================");				
				System.out.println("ERROR: Protein id="+prot.getId()+" NOT FOUND!");
				System.out.println("=============================================");
			}
		}
	}
	*/
	
	private void readModules(String fn_species, String fn_proteins, String fn_pathways) throws Exception{
		LineNumberReader lr = new LineNumberReader(new FileReader(fn_species));
		String s = null;
		while((s=lr.readLine())!=null){
			StringTokenizer st = new StringTokenizer(s,"\t");
			String mname = st.nextToken(); st.nextToken();
			Vector v = new Vector();
			while(st.hasMoreTokens())
				v.add(st.nextToken());
			module_species.put(mname, v);
			Collections.sort(v);
		}
		lr.close();
		lr = new LineNumberReader(new FileReader(fn_proteins));
		s = null;
		while((s=lr.readLine())!=null){
			StringTokenizer st = new StringTokenizer(s,"\t");
			String mname = st.nextToken(); st.nextToken();
			Vector v = new Vector();
			while(st.hasMoreTokens())
				v.add(st.nextToken());
			module_proteins.put(mname, v);
			Collections.sort(v);
		}
		lr.close();
		lr = new LineNumberReader(new FileReader(fn_pathways));
		s = null;
		while((s=lr.readLine())!=null){
			StringTokenizer st = new StringTokenizer(s,"\t");
			String mname = st.nextToken(); st.nextToken();
			Vector v = new Vector();
			while(st.hasMoreTokens())
				v.add(st.nextToken());
			pathways.put(mname, v);
			Collections.sort(v);
		}
		lr.close();		
	}
	
	public Vector getModuleName(HashMap modules, String id){
		Vector res = new Vector();
		Set keys = modules.keySet();
		Iterator it = keys.iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			Vector v = (Vector)modules.get(key);
			if(v.indexOf(id)>=0){
				res.add(key);
			}
		}
		Collections.sort(res);
		return res;
	}
	
	private void makeCytoscapeMaps(String path, String pagefolder, String sizesfile) throws Exception{
		
		HashMap sizes = new HashMap();
		LineNumberReader lr = new LineNumberReader(new FileReader(sizesfile));
		String s = null;
		while((s=lr.readLine())!=null){
			StringTokenizer st = new StringTokenizer(s,"\t");
			String name = st.nextToken();
			Place pl = new Place();
			pl.x = Float.parseFloat(st.nextToken());
			pl.y = Float.parseFloat(st.nextToken());
			pl.width = Float.parseFloat(st.nextToken());
			sizes.put(name, pl);
		}
		
		File f = new File(path);
		File modules[] = f.listFiles();
		for(int i=0;i<modules.length;i++){
			if(modules[i].getName().endsWith(".xgmml")){
				String name = modules[i].getName().substring(0,modules[i].getName().length()-6);
				if(name.endsWith(".xml"))
					name = name.substring(0,name.length()-4);
				GraphDocument grDoc = XGMML.loadFromXMGML(modules[i].getAbsolutePath());
				Graph graph = XGMML.convertXGMMLToGraph(grDoc);
				double minx = Double.POSITIVE_INFINITY;
				double miny = Double.POSITIVE_INFINITY;
				double maxx = -Double.POSITIVE_INFINITY;
				double maxy = -Double.POSITIVE_INFINITY;
				
				for(int j=0;j<grDoc.getGraph().getNodeArray().length;j++){
					GraphicNode n = grDoc.getGraph().getNodeArray()[j];
					if(n.getGraphics().getX()<minx) minx = n.getGraphics().getX();
					if(n.getGraphics().getY()<miny) miny = n.getGraphics().getY();
					if(n.getGraphics().getX()+n.getGraphics().getW()>maxx) maxx = n.getGraphics().getX()+n.getGraphics().getW();
					if(n.getGraphics().getY()+n.getGraphics().getH()>maxy) maxy = n.getGraphics().getY()+n.getGraphics().getH();
				}
				FileWriter fw = new FileWriter(path+"../"+name+"_cyto.html");
				fw.write("<img usemap=\"#immapdata\" name=\"map\" src=\""+name+"_cyto.png\"><map name=\"immapdata\" href=\"\">\n");
				fw.write("<map name=\"immapdata\" href=\"\">\n");
				
				float imw = (float)(maxx-minx); float imh = (float)(maxy-miny);
				float shiftx = 0f;
				Place pl = (Place)sizes.get(name);
				if(pl!=null){
					imw = pl.x;
					imh = pl.y;
					shiftx = pl.width;
				}
				
				for(int j=0;j<grDoc.getGraph().getNodeArray().length;j++){
					GraphicNode n = grDoc.getGraph().getNodeArray()[j];
					double x = shiftx+(n.getGraphics().getX()-minx)*imw/(maxx-minx);
					double y = (n.getGraphics().getY()-miny)*imh/(maxy-miny);
					double w = n.getGraphics().getW();
					double h = n.getGraphics().getH();
					Vector v = ((Node)graph.Nodes.get(j)).getAttributesWithSubstringInName("CELLDESIGNER_SPECIES");
					String id = ((Attribute)v.get(0)).value;
					if(id==null){
						v = ((Node)graph.Nodes.get(j)).getAttributesWithSubstringInName("CELLDESIGNER_REACTION");
						id = ((Attribute)v.get(0)).value;
					}

					String coords = (int)x+","+(int)y+","+(int)(x+w)+","+(int)(y+h);
					fw.write("<area shape=\"RECT\" alt=\""+n.getLabel()+"\" coords=\""+coords+"\" href=\"../"+pagefolder+"/"+id+".html\" target='info' />\n");
				}
				fw.write("</map>\n");
				fw.close();
			}
		}
	}
	
	private static String correctName(String name){
		  name = Utils.replaceString(name," ","_");
		  name = Utils.replaceString(name,"*","_");
		  name = Utils.replaceString(name,"-","_");
		  name = Utils.replaceString(name,"[","_");
		  name = Utils.replaceString(name,"]","_");
		  name = Utils.replaceString(name,"__","_");
		  name = Utils.replaceString(name,"__","_");
		  name = Utils.replaceString(name,":","_");
		  name = Utils.replaceString(name,"/","_");
		  if(name.endsWith("_"))
		    name = name.substring(0,name.length()-1);
		  if(name.startsWith("_"))
			    name = name.substring(1,name.length());

		  byte mc[] = name.getBytes();
		  StringBuffer sb = new StringBuffer(name);
		  for(int i=0;i<mc.length;i++)
		    //System.out.println(name.charAt(i)+"\t"+mc[i]);
		    if(mc[i]<=0)
		      sb.setCharAt(i,'_');
		  return sb.toString();
		}
	
	private static void decomposeIntoModulesByTagValue(String filename, String tagName, String path) throws Exception{
		Vector species = new Vector();
		Vector speciesAliases = new Vector();
		Vector reactions = new Vector();
		Vector degraded = new Vector();
		
		SbmlDocument map = CellDesigner.loadCellDesigner(filename);
		CellDesigner.entities = CellDesigner.getEntities(map);
		
		CellDesignerToCytoscapeConverter c2c = new CellDesignerToCytoscapeConverter();
		Graph graph = XGMML.convertXGMMLToGraph(c2c.getXGMMLGraph("temp", map.getSbml()));
		
		//XGMML.saveToXGMML(XGMML.convertGraphToXGMML(graph),filename+".xgmml");
		
		HashMap<String, Vector<String>> nodesList = new HashMap<String,Vector<String>>();
		HashMap<String, Vector<String>> speciesList = new HashMap<String,Vector<String>>();
		HashMap<String, Vector<String>> aliasesList = new HashMap<String,Vector<String>>();
		
		
		for(int i=0;i<graph.Nodes.size();i++){
			Node n = graph.Nodes.get(i);
			Vector<String> v = n.getAttributeValues(tagName);
			for(int j=0;j<v.size();j++){
				StringTokenizer st = new StringTokenizer(v.get(j),"@@");
				while(st.hasMoreTokens()){
					String key = st.nextToken();
					key = Utils.replaceString(key, "/", "_");
					key = Utils.replaceString(key, "-", "_");
					Vector<String> vals = speciesList.get(key);
					if(vals==null) vals = new Vector<String>();
					String id = n.getFirstAttributeValue("CELLDESIGNER_SPECIES");
					if(id!=null){
						vals.add(id);
						speciesList.put(key, vals);
					}
					Vector<String> aliases = aliasesList.get(key);
					if(aliases==null) aliases = new Vector<String>();
					id = n.getFirstAttributeValue("CELLDESIGNER_ALIAS");
					if(id!=null){
						aliases.add(id);
						aliasesList.put(key, aliases);
					}
					Vector<String> nodes = nodesList.get(key);
					if(nodes==null) nodes = new Vector<String>();
					nodes.add(n.Id);
					nodesList.put(key, nodes);
				}
			}
		}
		
				
		Iterator<String> it = speciesList.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			SubnetworkProperties snp = new SubnetworkProperties();
			snp.subnetwork = new Graph();
			Vector<String> nodes = nodesList.get(key);
			//for(int i=0;i<nodes.size();i++)
			//	System.out.println(key+"\t"+nodes.get(i));
			for(int i=0;i<nodes.size();i++)
				snp.subnetwork.addNode(graph.getNode(nodes.get(i)));
			snp.subnetwork.addConnections(graph);
			snp.addFirstNeighbours(snp.subnetwork,graph,true);
			reactions.clear();
			for(int i=0;i<snp.subnetwork.Nodes.size();i++){
				Node n = snp.subnetwork.Nodes.get(i);
				String id = n.getFirstAttributeValue("CELLDESIGNER_REACTION");
				if(id!=null){
					//System.out.println("REACTION +"+id);
					reactions.add(id);
				}
			}
			SbmlDocument cd = CellDesigner.loadCellDesigner(filename);
			CellDesigner.entities = CellDesigner.getEntities(cd);
			species = speciesList.get(key);
			speciesAliases = aliasesList.get(key);
			CytoscapeToCellDesignerConverter.filterIDsCompleteReactions
				(cd, species, speciesAliases, reactions, degraded);
			CellDesigner.saveCellDesigner(cd, path+"/"+key+".xml");
		}
		
		/*HashMap<String, Vector<String>> entityList = new HashMap<String,Vector<String>>();
		
		for(int i=0;i<map.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein protein = map.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			String notes = Utils.getValue(protein.getCelldesignerNotes());
			Vector<String> vals = Utils.extractAllStringBetween(notes, tagName, " ");
			for(int j=0;j<vals.size();j++){
				Vector<String> ents = entityList.get(vals.get(j));
				if(ents==null) ents = new Vector<String>();
				ents.add(protein.getId());
				entityList.put(vals.get(j), ents);
			}
		}
		for(int i=0;i<map.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			CelldesignerGeneDocument.CelldesignerGene gene = map.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			String notes = Utils.getValue(gene.getCelldesignerNotes());
			Vector<String> vals = Utils.extractAllStringBetween(notes, tagName, " ");
			for(int j=0;j<vals.size();j++){
				Vector<String> ents = entityList.get(vals.get(j));
				if(ents==null) ents = new Vector<String>();
				ents.add(gene.getId());
				entityList.put(vals.get(j), ents);
			}
		}
		for(int i=0;i<map.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().sizeOfCelldesignerRNAArray();i++){
			CelldesignerRNADocument.CelldesignerRNA rna = map.getSbml().getModel().getAnnotation().getCelldesignerListOfRNAs().getCelldesignerRNAArray(i);
			String notes = Utils.getValue(rna.getCelldesignerNotes());
			Vector<String> vals = Utils.extractAllStringBetween(notes, tagName, " ");
			for(int j=0;j<vals.size();j++){
				Vector<String> ents = entityList.get(vals.get(j));
				if(ents==null) ents = new Vector<String>();
				ents.add(rna.getId());
				entityList.put(vals.get(j), ents);
			}
		}
		for(int i=0;i<map.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
			SpeciesDocument.Species sp = map.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
			if(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference()!=null){
				String id = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerProteinReference());
				Vector<String> ents = entityList.get(id);
				if(ents.size()>0)
				for(int j=0;j<)
			}
		}*/
	}
	
	private void find_the_position_of_the_OVAL_in_OVAL_xml_for_centering()
	{
		if (false)
		for (CelldesignerLayer l : cd.getSbml().getModel().getAnnotation().getCelldesignerListOfLayers().getCelldesignerLayerArray())
		{
			for (CelldesignerLayerFreeLine k : l.getCelldesignerListOfFreeLines().getCelldesignerLayerFreeLineArray())
			{
				final CelldesignerBounds bounds = k.getCelldesignerBounds();
				bounds.getSx();
				bounds.getEx();
			}
			for (CelldesignerLayerCompartmentAlias k : l.getCelldesignerListOfSquares().getCelldesignerLayerCompartmentAliasArray())
			{
				final CelldesignerBounds bounds = k.getCelldesignerBounds();
				bounds.getX();
				bounds.getW();
			}
			l.getName();
			final Notes notes = cd.getSbml().getModel().getNotes();
			
		}
	}
	/*
	private void makeScriptString(){
		script = "<script>\n";
		script+="window.onscroll = saveCoordsTiny;\n";
		script+="window.onload = scrollToSavedTiny;\n";
		script+="var disableSaving = 0;\n";
		script+="function showLabel(id){\n";
		script+="d = top.menu.document.getElementById(\"showhide\");\n";
		script+="d.innerHTML = '<small>'+id+'</small>';\n";
		script+="}\n";
		script+="function saveCoordsTiny()\n";
		script+="{\n";
		script+="var scrollX, scrollY;\n";
		script+="if(document.all)\n";
		script+="{\n";
		script+="if (!document.documentElement.scrollLeft)\n";
		script+="scrollX = document.body.scrollLeft;\n";
		script+="else\n";
		script+="scrollX = document.documentElement.scrollLeft;\n";
		script+="if (!document.documentElement.scrollTop)\n";
		script+="scrollY = document.body.scrollTop;\n";
		script+="else\n";
		script+="scrollY = document.documentElement.scrollTop;\n";
		script+="}\n";
		script+="else\n";
		script+="{\n";
		script+="scrollX = window.pageXOffset;\n";
		script+="scrollY = window.pageYOffset;\n";
		script+="}\n";
		script+="if(scrollX>0)if(scrollY>0)if(disableSaving==0){\n";
		script+="top.menu.document.forms['coords'].xCoordHolder.value = scrollX/0.3;\n";
		script+="top.menu.document.forms['coords'].yCoordHolder.value = scrollY/0.3;\n";
		script+="}\n";
		script+="}\n";
		script+="function scrollToSavedTiny(){\n";
		script+="disableSaving = 1;\n";
		script+="window.scrollTo(top.menu.document.forms['coords'].xCoordHolder.value*0.3,top.menu.document.forms['coords'].yCoordHolder.value*0.3);\n";
		script+="top.menu.document.forms['coords'].scale.value = 1;\n";
		script+="top.menu.document.forms['coords'].suffix.value = \"_small\";\n";
		script+="disableSaving = 0;\n";
		script+="}\n";
		script+="function getSize() {\n";
		script+="var result = {height:0, width:0};\n";
		script+="if (parseInt(navigator.appVersion)>3) {\n";
		script+="if (navigator.appName==\"Netscape\") {\n";
		script+="result.width = top.map.innerWidth-16;\n";
		script+="result.height = top.map.innerHeight-16;\n";
		script+="}\n";
		script+="if (navigator.appName.indexOf(\"Microsoft\")!=-1) {\n";
		script+="result.width = top.map.document.body.offsetWidth-20;\n";
		script+="result.height = top.map.document.body.offsetHeight-20;\n";
		script+="}\n";
		script+="}\n";
		script+="return result;\n";
		script+="}\n";
		script+="</script>\n";
	}
	/*
	private void makeMenu(){
		menu = "<html>\n";
		menu+= "<body>\n";
		menu+= "<b>"+title+" pathway diagram:</b> (<a href=\"diagram_help.html\" target=\"info\">help</a>)<b>:</b> \n";
		menu+= "<font size=+1><a href=\""+name+".html\" target=\"map\">DETAILED</a></font> -\n";
		menu+= "<font size=-1><a href=\""+name+"_compressed.html\" target=\"map\">COMPRESSED</a></font> -\n";
		menu+= "<font size=+0><a href=\""+name+"_structural.html\" target=\"map\">StRuCtUrAl</a></font> - \n";
		menu+= "<font size=+0><a href=\""+name+"_modular.html\" target=\"map\"><b>modular</b></a></font>\n";
		menu+= "&nbsp;&nbsp;\n";
		menu+= "<b>Panel:</b>\n";
		menu+= "<a href=\"pages/"+name+"_list.html\" target=\"info\">all entities</a>\n";
		menu+= "&nbsp;&nbsp;\n";
		menu+= "<b>Element:</b>\n";
		menu+= "<span id=\"showhide\"></span>\n";
		menu+= "<form id='coords'>\n";
		menu+= "<INPUT type=\"hidden\" id=\"xCoordHolder\">\n";
		menu+= "<INPUT type=\"hidden\" id=\"yCoordHolder\">\n";
		menu+= "<INPUT type=\"hidden\" id=\"scale\" value=\"1\">\n";
		menu+= "<INPUT type=\"hidden\" id=\"suffix\" value=\"\">\n";
		menu+= "</form>\n";
		menu+= "<script>\n";
		menu+= "window.onload = checkBrowser;\n";
		menu+= "function checkBrowser(){if (navigator.appName.indexOf(\"Microsoft\")>=0) {alert(\"WARNING: The map does not work correctly with Internet Explorer browser!\n Please use Firefox or Google Chrome instead.\");}}\n\n";
		menu+= "function getCoordsX()\n";
		menu+= "{\n";
		menu+= "return document.forms['coords'].xCoordHolder.value;\n";
		menu+= "}\n";
		menu+= "function getCoordsY()\n";
		menu+= "{\n";
		menu+= "return document.forms['coords'].yCoordHolder.value;\n";
		menu+= "}\n";
		menu+= "</script>\n";
		menu+= "</body>\n";
		menu+= "</html>\n";		
	}
	*/
	/*
	private void makeFrame(){
		frame = "<html>\n";
		frame+="<frameset rows=\"40,100%\">\n";
		frame+="<frame src=\"menu.html\" name=\"menu\">\n";
		frame+="<FRAMESET COLS=\"80%,20%\">\n";
		frame+="<FRAME SRC=\""+name+".html\" NAME=\"map\">\n";
		frame+="<FRAME SRC=\"pages/"+name+"_list.html\"\n";
		frame+="NAME=\"info\">\n";
		frame+="</FRAMESET>\n";
		frame+="</FRAMESET>\n";
		frame+="</html>\n";
	}
	*/
	/* for each XML file in the folder, generates an HTML image map
	 */
	/*
	private void convertXMLsInFolderToImageMaps(String folder,String pathToPages) throws Exception{
		System.out.println("Creating image maps in folder "+folder+"...");
		File f = new File(folder);
		File files[] = f.listFiles();
		for(int i=0;i<files.length;i++)if(files[i].getName().toLowerCase().endsWith("xml")){
			ProduceClickableMap clMap = new ProduceClickableMap();
			clMap.name = files[i].getName().substring(0,files[i].getName().length()-4);
			clMap.path = folder;
			File fh = new File(clMap.path+clMap.name+".html");
			if(!fh.exists()){
				clMap.loadCellDesigner(files[i].getAbsolutePath());
				clMap.findAllPlacesInCellDesigner();
				clMap.makeScriptString();
				String mapString = clMap.generateMapFile(clMap.path, clMap.name, pathToPages);
//				System.out.println(files[i].getName()+"\t"+mapString.length());
			}
		}
	}
	*/
	/*
	private void createModuleMaps() throws Exception{
		File fd = new File(this.path+"/"+this.modulefolder);
		fd.mkdir();
		fd = new File(this.path+"/"+this.modulefolder+"/"+this.subfolder);
		fd.mkdir();
		File f = new File(this.path+"modules.txt");
		if(f.exists()){
			// read map of module names
			Vector<String> vs = Utils.loadStringListFromFile(f.getAbsolutePath());
			for(int i=0;i<vs.size();i++){
				StringTokenizer st = new StringTokenizer(vs.get(i),"\t");
				speciesModularModuleNameMap.put(st.nextToken(), st.nextToken());
			}
			// correct modular imagemap (change species ids to module names)
			String modularMapFileName = path+name+"_modular.html";
			String modularMap = Utils.loadString(modularMapFileName);
			Iterator<String> keys = speciesModularModuleNameMap.keySet().iterator();
			while(keys.hasNext()){
				String species = keys.next(); String moduleName = speciesModularModuleNameMap.get(species);
				modularMap = Utils.replaceString(modularMap,this.subfolder+"/"+species+".html",this.modulefolder+"/"+this.subfolder+"/"+moduleName+".html");
			}
			FileWriter fw = new FileWriter(modularMapFileName);
			fw.write(modularMap+"\n");
			fw.close();
			// read all xmls and fill module_species and module_proteins
			keys = speciesModularModuleNameMap.keySet().iterator();
			while(keys.hasNext()){
				String species = keys.next(); String moduleName = speciesModularModuleNameMap.get(species);
				SbmlDocument cd = CellDesigner.loadCellDesigner(this.path+"/"+this.modulefolder+"/"+moduleName+"_c.xml");
				Vector<String> allspecies = new Vector<String>();
				Vector<String> allproteins = new Vector<String>();
				Vector<String> allproteinnames = new Vector<String>();
				for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
					SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
					allspecies.add(sp.getId());
				}
				for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
					CelldesignerProteinDocument.CelldesignerProtein p = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
					allproteins.add(p.getId());
					allproteinnames.add(Utils.getValue(p.getName()));
				}
				for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
					CelldesignerGeneDocument.CelldesignerGene g = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
					allproteins.add(g.getId());
					allproteinnames.add(g.getName());
				}
				module_species.put(moduleName, allspecies);
				module_proteins.put(moduleName, allproteins);
				module_protein_names.put(moduleName, allproteinnames);
			}
			// get names of modules from their ids
			SbmlDocument cd = CellDesigner.loadCellDesigner(this.path+"/"+name+"_modular.xml");
			for(int i=0;i<cd.getSbml().getModel().getListOfSpecies().sizeOfSpeciesArray();i++){
				SpeciesDocument.Species sp = cd.getSbml().getModel().getListOfSpecies().getSpeciesArray(i);
				String moduleId = speciesModularModuleNameMap.get(sp.getId());
				module_names.put(moduleId, Utils.getValue(sp.getName()));
			}
			// generate module description pages
			generateModulePages();
			// generate image maps for modules
			convertXMLsInFolderToImageMaps(path+modulefolder+"/","../"+subfolder);
		}else{
			System.out.println("No file modules.txt found! CAN NOT create modules!");
		}
	}
	*/
	/*
	private void generateModulePages() throws Exception{
		Iterator keys = module_species.keySet().iterator();
		while(keys.hasNext()){
			String mname = (String)keys.next();
			String mnamec = correctName(mname);
			FileWriter fw = new FileWriter(path+"/"+this.modulefolder+"/"+this.subfolder+"/"+mnamec+".html");
			fw.write("<h3>Module</h3>\n");
			
			fw.write("<b><font color=blue>"+module_names.get(mname)+"</font></b>\n");
			
			//fw.write("<br><small><a href='../"+mnamec+".html' target='map' onClick='fname=\""+mnamec+"\"+top.menu.document.forms[\"coords\"].suffix.value+\".html\";top.map.window.location=fname''>(g.layout)</a></small> - ");
			fw.write("<br><small><a href='../"+mnamec+"_c.html' target='map'>(compact)</a></small>");
			
			fw.write("<hr><font color=red>Entities involved:</font><br>\n");
			
//			Vector v = new Vector();
//			HashMap entitiesMap = new HashMap();
//			for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
//				CelldesignerProteinDocument.CelldesignerProtein prot = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
//				v.add(Utils.getValue(prot.getName()));
//				entitiesMap.put(Utils.getValue(prot.getName()),prot);
//			}
//			for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
//				CelldesignerGeneDocument.CelldesignerGene gene = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
//				v.add(gene.getName()+" (gene)");
//				entitiesMap.put(gene.getName()+" (gene)",gene);
//			}
			Vector<String> vv = module_proteins.get(mname);
			Vector<String> vvn = module_protein_names.get(mname);
			fw.write("<ol>\n");
			for(int i=0;i<vv.size();i++){
				String ename = vvn.get(i);
				String eid = vv.get(i);
//				if(entitiesMap.get(ename)!=null){
//					eid = ((CelldesignerProteinDocument.CelldesignerProtein)entitiesMap.get(ename)).getId();
//				}
//				if(entitiesMap.get(ename+" (gene)")!=null){
//					eid = ((CelldesignerGeneDocument.CelldesignerGene)entitiesMap.get(ename+" (gene)")).getId();
//				}
				if(eid!=null){
					fw.write("<li><a href='../../"+subfolder+"/"+eid+".html'>"+ename+"</a>\n");
				}
			}
			fw.write("</ol>\n");
			fw.close();
		}		
	}
	*/
	/* creates a file with all annotations
	 */
	/*
	private void printAnnotation(SbmlDocument cd){
		CellDesigner.entities = CellDesigner.getEntities(cd);
		CellDesignerToCytoscapeConverter.createSpeciesMap(cd.getSbml());
		updateStandardNames();
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().sizeOfCelldesignerProteinArray();i++){
			CelldesignerProteinDocument.CelldesignerProtein p = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfProteins().getCelldesignerProteinArray(i);
			String annot = Utils.getValue(p.getCelldesignerNotes());
			if(annot!=null){			
				System.out.println("### "+p.getId());
				System.out.println("### "+Utils.getValue(p.getName()));
				System.out.println(annot);
				System.out.println();
			}
		}
		for(int i=0;i<cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().sizeOfCelldesignerGeneArray();i++){
			CelldesignerGeneDocument.CelldesignerGene p = cd.getSbml().getModel().getAnnotation().getCelldesignerListOfGenes().getCelldesignerGeneArray(i);
			String annot = Utils.getValue(p.getCelldesignerNotes());
			if(annot!=null){			
				System.out.println("### "+p.getId());
				System.out.println("### "+p.getName());
				System.out.println(annot);
				System.out.println();
			}
		}		
		for(int i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
			ReactionDocument.Reaction r = cd.getSbml().getModel().getListOfReactions().getReactionArray(i);
			String annot = Utils.getValue(r.getNotes());
			if(annot!=null){			
				System.out.println("### "+r.getId());
				System.out.println("### "+this.getReactionString(r, cd, true, false));
				System.out.println(annot);
				System.out.println();
			}
		}
	}
	*/
	/*
	private void importAnnotations(SbmlDocument cd, String annotations) throws Exception{
		CellDesigner.entities = CellDesigner.getEntities(cd);
		CellDesignerToCytoscapeConverter.createSpeciesMap(cd.getSbml());
		updateStandardNames();
		LineNumberReader lr = new LineNumberReader(new StringReader(annotations));
		String s = null;
		String currentEntity = null;
		String currentAnnotation = null;
		while((s=lr.readLine())!=null){
			if(s.startsWith("###")){
				if(currentEntity!=null){
					// import annotation text for a current entity
					CellDesigner.entities.get(currentEntity);
				}
				StringTokenizer st = new StringTokenizer(s,"\t");
				st.nextToken(); currentEntity = st.nextToken(); currentAnnotation = "";
				lr.readLine();
			}else{
				currentAnnotation+=s+"\n";
			}
		}
	}
	*/

	static class MarkerManager
	{
		private final Set<String> seen = new HashSet<String>();
		private final BufferedWriter markers;
		private final File outfile;
		private final File tmp;
		private BufferedReader existing;
		MarkerManager(final File outfile) throws IOException
		{
			this.outfile = outfile;
			final java.text.DateFormat formatter = java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.LONG, java.text.DateFormat.LONG);
			tmp = File.createTempFile("click_map", null, outfile.getParentFile());
			final FileWriter fileWriter = new FileWriter(tmp);
			markers = new BufferedWriter(fileWriter);
			markers.write("<?xml version=\"1.0\" encoding=\"" + fileWriter.getEncoding() + "\"?>\n");
			markers.write("<!-- " + formatter.format(new Date()) + " " + System.getProperty("user.name") + " $Id$ -->\n");
			BufferedReader existing;
			try
			{
				existing = new BufferedReader(new FileReader(outfile));
			}
			catch (IOException e)
			{
				existing = null;
			}
			if (!initial_line(existing) || !initial_line(existing))
				existing = null;
			this.existing = existing;
			check_write("<markers>\n");
		}

		void create(String entity_class, String body, final String title, String shape_id, final int x, final int y)
		throws IOException
		{
			assert seen.add(shape_id) : "marker for " + shape_id + " created twice";
			check_write("<marker class=\"");
			check_write(entity_class.toUpperCase(Locale.ENGLISH));
			check_write("\" id=\"");
			check_write(shape_id);
			check_write("\" name=\"");
			check_write(title);
			check_write("\"");
			check_write(" x=\"" + x + "\" y=\"" + y + "\">\n<notes>\n");
			check_write("<![CDATA[");
			check_write(body.replace("]]", "]&#xfeff;]&#xfeff;"));
			check_write("]]>\n");
			check_write("</notes>\n</marker>\n\n");
		}

		void close() throws IOException
		{
			check_write("</markers>\n");
			markers.close();
			final boolean identical;
			if (existing == null)
				identical = false;
			else
			{
				identical = existing.read() == -1;
				existing.close();
			}

			if (identical)
				tmp.delete();
			else if (!tmp.renameTo(outfile))
				throw new IOException("failed to rename " + tmp + " to " + outfile);
			else
				Utils.eclipsePrintln("renamed " + tmp + " to " + outfile);
		}

		private void check_write(String s) throws IOException
		{
			compare(s);
			markers.write(s);
		}
		private boolean initial_line(BufferedReader existing) throws IOException
		{
			if (existing == null)
				return false;
			final String s = existing.readLine();
			return s != null && !s.isEmpty() && s.startsWith("<") && s.endsWith(">");
		}

		private void compare(String s) throws IOException
		{
			if (existing != null)
				for (int i = 0; i < s.length(); i++)
					if (s.charAt(i) != existing.read())
					{
						existing.close();
						existing = null;
						return;
					}
		}
	}
	
	static private final String[][] class_name_to_human_name =
	{
		{ PROTEIN_CLASS_NAME, "Proteins", "Protein" },
		{ GENE_CLASS_NAME, "Genes", "Gene" },
		{ RNA_CLASS_NAME, "RNAs", "RNA" },
		{ ANTISENSE_RNA_CLASS_NAME, "antisense RNAs", "Antisense RNA" },
		{ SIMPLE_MOLECULE_CLASS_NAME, "Simple molecules", "Simple molecule" },
		{ ION_CLASS_NAME, "Ions", "Ion" },
		{ DRUG_CLASS_NAME, "Drugs", "Drug" },
		{ PHENOTYPE_CLASS_NAME, "Phenotypes", "Phenotype" },
		{ UNKNOWN_CLASS_NAME, "Unknown" },
		{ COMPLEX_CLASS_NAME, "Complexes", "Complex" },
		{ REACTION_CLASS_NAME, "Reactions", "Reaction" }
	};
	static private final Map<String, String> class_name_to_human_name_map;
	static {
		final Map<String, String> map = new HashMap<String, String>();
		for (String[] s : class_name_to_human_name)
			map.put(s[0], s.length == 2 ? s[1] : s[2]);
		class_name_to_human_name_map = Collections.unmodifiableMap(map);
	}
	
	private static void make_index_html(final File this_map_directory, final String blog_name, final String title,
		final String map_name,
		ImagesInfo scales,
		BlogCreator.Post module_post,
		final BlogCreator wp) throws FileNotFoundException
	{
		final PrintStream out = new PrintStream(new FileOutputStream(new File(this_map_directory, "index.html")));
		
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		out.println("<html xmlns='http://www.w3.org/1999/xhtml' xml:lang='en'>");
		out.println("<!-- $Id$ -->");
		out.println("<head>");
		out.println("<meta http-equiv='content-type' content='text/html;charset=UTF-8'/>");
		out.println("<title>" + blog_name + " &#x2014; " + title + "</title>");
		/*
		out.println("<script src='http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key=" + key + "' type='text/javascript'></script>");
		out.println("<script src='http://gmaps-utility-library.googlecode.com/svn/trunk/markermanager/release/src/markermanager.js' type='text/javascript'></script>");
		*/
		
		out.println("<link rel='stylesheet' type='text/css' href=\"" + common_directory_url + "/" + included_map_base + ".css\"/>");
		out.println("<script src='https://maps.googleapis.com/maps/api/js?sensor=false' type='text/javascript'></script>");
//		out.println("<script src='/javascript/jquery/jquery.js' type='text/javascript'></script>");
		out.println("<script src='" + jquery_js + "' type='text/javascript'></script>");
		out.println("<script src='" + jstree_directory_url + "/jquery.jstree.js" + "' type='text/javascript'></script>");
	
		out.println("<script src=\"" + common_directory_url + "/" + included_map_base + ".js\" type='text/javascript'></script>");

		final String map_div_name = "map"; // see css
		final String marker_div_name = "marker_checkboxes"; // see css
		
		out.println("<script type='text/javascript'>");
		out.println("$(document).ready(function(){");
		out.print("clickmap_start(");
		out.print(html_quote(blog_name));
		out.print(html_quote(new StringBuffer(","), map_name).toString());
		out.print(", '#" + marker_div_name + "'");
		out.print(", '" + map_div_name + "'");
		out.print(", '" + right_panel_list + "'");
		out.print(", ");
		out.print(scales.minzoom);
		out.print(", ");
		out.print(scales.maxzoom);
		out.print(", ");
		out.print(scales.tile_width);
		out.print(", ");
		out.print(scales.tile_height);
		out.print(", ");
		out.print(scales.width_zoom0);
		out.print(", ");
		out.print(scales.height_zoom0);
		out.print(", ");
		out.print(scales.xshift_zoom0);
		out.print(", ");
		out.print(scales.yshift_zoom0);
		out.println(")");
		out.println("});");
		out.println(wp.getBlogLinker());
		out.println("</script>");
		
		out.println("</head>");
		out.println("<body>");
		
			
		out.println("<noscript>");
		out.println("JavaScript must be enabled in order for you to use NaviCell.");
		out.println("However, it seems JavaScript is either disabled or not supported by your browser.");
		out.println("To view the maps, enable JavaScript by changing your browser options and then try again.");
		out.println("</noscript>");
		
		out.print("<div id='header'>");
		out.print(" <div class='header-left'>");
		out.print("<a href='/' target='_blank'><img border='0' src=");
		out.print(html_quote(new StringBuffer(), icons_directory + "/misc/map_top_panel_logo.png"));
		out.print("/></a>");
		out.println("</div>");
		out.println("<div class='header-centre'>");
		out.println(title);
		out.println("</div>");
		out.println("<div class='header-right'>");
		
		out.println(bubble_to_post_link_with_anchor(module_post.getPostId(), new StringBuffer()).toString());
		
		doc_in_new_window(out, "map_symbols", "map symbols");
		out.print(" ");
		doc_in_new_window(out, "map_help", "help");
		
		out.println("<input type='text' size='14' id='query_text'/>");
//		out.print("<input type='button' class='button' value='Search' id='search' style='' />");
		out.println("</div>");
		out.println("</div>");

		out.print("<div id='" + map_div_name);
		out.println("'>The map is loading. If this message isn't replaced by the map in a few seconds then have a look in your navigator's error console.</div>");
		out.println("<div id='side_bar'>");
		
		out.println("<div id='" + marker_div_name + "'></div>");
		
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		assert !out.checkError();
		out.close();
	}

	private static void doc_in_new_window(final PrintStream out, String page, String text)
	{
		html_in_new_window(out, doc_directory + "/" + page + ".html", text);
	}

	private static void html_in_named_window(final PrintStream out, String url, String text, String target)
	{
		out.print("<a href=");
		out.print(html_quote(new StringBuffer(), url));
		out.print(" target=" + html_quote(new StringBuffer(), target) + ">");
		out.print(text);
		out.print("</a>");
	}

	private static void html_in_new_window(final PrintStream out, String url, String text)
	{
		html_in_named_window(out, url, text, "_blank");
	}
}