package fr.curie.BiNoM.pathways.utils.acsn;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.sbml.x2001.ns.celldesigner.ReactionDocument;
import org.sbml.x2001.ns.celldesigner.SbmlDocument;
import org.sbml.x2001.ns.celldesigner.SpeciesDocument;

import com.hp.hpl.jena.query.function.library.namespace;

import fr.curie.BiNoM.pathways.analysis.structure.Graph;
import fr.curie.BiNoM.pathways.utils.GMTFile;
import fr.curie.BiNoM.pathways.utils.ModifyCellDesignerNotes;
import fr.curie.BiNoM.pathways.utils.SubnetworkProperties;
import fr.curie.BiNoM.pathways.utils.Utils;
import fr.curie.BiNoM.pathways.utils.ModifyCellDesignerNotes.AnnotationSection;
import fr.curie.BiNoM.pathways.wrappers.CellDesigner;
import fr.curie.BiNoM.pathways.wrappers.XGMML;

public class ConnectionToDatabases {

	/**
	 * @param args
	 */
	
	public class Citation{
		public String pmid = "";
		public String shortCitation = "";		
		public String volume = "";		
		public String title = "";
		public String pages = "";
		public String journal = "";
		public String issue = "";
		public int year = 0;
		public String firstAuthorName = "";
		public String allAuthors = "";
		public boolean isReview = false;
		public String oneLineCitation(){
			String res = "";
			res+=allAuthors;
			res+=" ("+year+") ";
			res+=title+" ";
			res+=journal+" ";
			if(!issue.equals(""))
				res+=volume+"("+issue+");";
			else
				res+=volume+";";
			res+=pages+".";
			return res;
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			//findLinksToAtlasOfOncology("c:/datas/acsn/allnames.txt");
			//findLinksReactome("c:/datas/acsn/uniprots.txt");
			//findKeggLinks("c:/datas/acsn/allnames.txt");
			//makeIdentifiersTable("c:/datas/acsn/allnames.txt");
			//makeIdentifiersTable("c:/datas/acsn/test1.txt");
			
			/*Citation cit = convertPMIDtoCitation("17334950");
			System.out.println(cit.oneLineCitation());
			System.out.println("Review="+cit.isReview);*/
			
			// Get all confidence reference scores
			//SbmlDocument sbml = CellDesigner.loadCellDesigner("C:/Datas/acsn/assembly/acsn_src/acsn_master.xml");
			/*SbmlDocument sbml = CellDesigner.loadCellDesigner("C:/Datas/acsn/FunctionalConfidence/test/cellcycle_master.xml");
			HashMap<String, Float> dscores = CalculateReferenceConfidenceScores(sbml,"C:/Datas/acsn/assembly/acsn_src/acsn_bib.txt");
			Set<String> keys = dscores.keySet();
			for(String s: keys){
				System.out.println(s+"\t"+dscores.get(s));
			}*/
			
			// Get confidence functional scores
			/*String prefix = "C:/Datas/acsn/FunctionalConfidence/test/cellcycle_neighbourhoods";
			GMTFile gmt = new GMTFile();
			gmt.load(prefix+".gmt");
			// First, compute distance matrix, can be commented if the file exists			
			//Graph ppi_network = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/hprd9/hprd9_pc_clicks.xgmml"));
			//calculatePPIDistanceMatrix(gmt.allnames,ppi_network,prefix+"_dist.txt");
			// Computing the score
			HashMap<String, Float> fscores = CalculateFunctionalConfidenceScores(gmt,prefix+"_dist.txt",prefix+"_dist_nodes.txt", prefix+"_fscores.txt");
			Set<String> fkeys = fscores.keySet();
			for(String s: fkeys){
				float score = fscores.get(s);
				System.out.println(s+"\t"+score);
			}*/
			
			String folder = "C:/Datas/acsn/assembly/";
			String prefix = "cellcycle_src/cellcycle_master";
			SbmlDocument cd = CellDesigner.loadCellDesigner(folder+prefix+".xml");
			annotateCellDesignerFileWithConfidenceScores(cd,folder+"_confidence_scores/cellcycle_dscores.txt",folder+"_confidence_scores/cellcycle_fscores.txt");
			CellDesigner.saveCellDesigner(cd, folder+prefix+"_mod.xml");
			
			
			
			// Global distance matrix
			/*GMTFile gmt = new GMTFile();
			gmt.load("C:/Datas/acsn/assembly/_confidence_scores/merged.gmt");
			Graph ppi_network = XGMML.convertXGMMLToGraph(XGMML.loadFromXMGML("c:/datas/hprd9/hprd9_pc_clicks.xgmml"));
			calculatePPIDistanceMatrix(gmt.allnames,ppi_network,"C:/Datas/acsn/assembly/_confidence_scores/acsn_dist.txt");*/
			
			/*computeNullDistributionForFunctionalScore(1000, 10, "C:/Datas/acsn/assembly/_confidence_scores/acsn_dist.txt","C:/Datas/acsn/assembly/_confidence_scores/acsn_dist_nodes.txt","C:/Datas/acsn/assembly/_confidence_scores/acsn_fscorenulldistribution.txt");
			GMTFile gmt = new GMTFile();
			gmt.load("C:/Datas/acsn/assembly/_confidence_scores/merged.gmt");			
			CalculateFunctionalConfidenceScores(gmt, "C:/Datas/acsn/assembly/_confidence_scores/acsn_dist.txt", "C:/Datas/acsn/assembly/_confidence_scores/acsn_dist_nodes.txt", "C:/Datas/acsn/assembly/_confidence_scores/acsn_fscorerealdistribution.txt");*/
			
			
			//computeScoresForACSN();
			
		}catch(Exception e){
			e.printStackTrace();
		}

	
	}

    public static void findLinksToAtlasOfOncology(String fn) throws Exception{
  	  Vector<String> list = Utils.loadStringListFromFile(fn);
  	  Vector<String> listIDs = new Vector<String>();
  	  for(int i=0;i<list.size();i++)
  		  listIDs.add("");
  	  String alphabet[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
  	  for(int i=0;i<alphabet.length;i++){
  		  String link = "http://atlasgeneticsoncology.org//Indexbyalpha/idxa_"+alphabet[i]+".html";
  		  String page = Utils.downloadURL(link);
  		  LineNumberReader lr = new LineNumberReader(new StringReader(page));
  		  String s = null;
  		  while((s=lr.readLine())!=null){
  			  String key = "<font size=-1><b>";
  			  if(s.contains(key)){
  				  int k = s.indexOf("<font size=-1><b>");
  				  String s1 = s.substring(k+key.length(), s.length());
  				  StringTokenizer st = new StringTokenizer(s1,"<");
  				  s1 = st.nextToken();
  				  if(list.contains(s1)){
  					  String key1 = "<A HREF=../Genes/";
  					  k = s.indexOf(key1);
  					  if(k!=-1){
  						  String s2 = s.substring(k+key1.length(),s.length());
  						  String t[] = s2.split(" ");
  						  s2 = t[0];
  						  String ref = null;
  						  if(s2.endsWith(".html")){
  							  ref = s2.substring(0, s2.length()-5);
  							  listIDs.set(list.indexOf(s1), ref);
  							  //System.out.println("Found "+s1+":"+ref);
  						  }
  					  }
  				  }
  			  }
  		  }
  	  }
  	  for(int i=0;i<list.size();i++){
  		  System.out.println(list.get(i)+"\t"+listIDs.get(i));
  	  }
    }
    
    public static void findLinksReactome(String fn) throws Exception{
    	  Vector<String> list = Utils.loadStringListFromFile(fn);
      	  Vector<String> listIDs = new Vector<String>();
      	  for(int i=0;i<list.size();i++)
      		  listIDs.add("");

      	  for(int i=0;i<list.size();i++){
      		  System.out.print(i+" ");
      		  String link = "http://www.reactome.org/cgi-bin/search2?OPERATOR=ALL&SPECIES=48887&QUERY="+list.get(i);
      		  String page = Utils.downloadURL(link);
      		  LineNumberReader lr = new LineNumberReader(new StringReader(page));
      		  String s = null;
      		  String key = "UniProt:<span style=\"background:#ffff99\">"+list.get(i);
      		  String id = ""; 
      		  while((s=lr.readLine())!=null){
      			  if(s.contains(key)){
      				  String key1 = "DB=gk_current&ID=";
      				  id = s.substring(s.indexOf(key1)+key1.length(), s.length()).split("\"")[0];
      			  }
      		  }
      		  listIDs.set(i,id);
      	  }
      	  System.out.println();
      	  
      	  for(int i=0;i<list.size();i++){
      		  System.out.println(list.get(i)+"\t"+listIDs.get(i));
      	  }
    	
    }
    
    public static void findKeggLinks(String fn) throws Exception{
  	  Vector<String> list = Utils.loadStringListFromFile(fn);
  	  Vector<String> listIDs = new Vector<String>();
  	  for(int i=0;i<list.size();i++)
  		  listIDs.add("");

  	  for(int i=0;i<list.size();i++){
  		  System.out.print(i+" ");
  		  String link = "http://www.genome.jp/dbget-bin/www_bfind_sub?mode=bfind&max_hit=1000&dbkey=hsa&keywords="+list.get(i)+"&mode=bfind";
  		  String page = Utils.downloadURL(link);
  		  LineNumberReader lr = new LineNumberReader(new StringReader(page));
  		  String key = "<a href=\"/dbget-bin/www_bget?hsa:";
  		  String s = null;
  		  while((s=lr.readLine())!=null){
  			  if(s.contains(key)){
  				  while(s.contains(key)){
  					  String key1 = "<div style=\"margin-left:2em\">";
  					  String key2 = "</div></div>";
  					  String s1 = s.substring(s.indexOf(key1)+key1.length(),s.indexOf(key2));
  					  String names[] = s1.split(",");
  					  for(int j=0;j<names.length;j++){
  						  if(names[j].trim().toUpperCase().equals(list.get(i).toUpperCase())){
  							  String id = s.substring(s.indexOf(key)+key.length(), s.length()).split("\"")[0];
  							  listIDs.set(list.indexOf(list.get(i)), id);
  						  }
  					  }
  					  s = s.substring(s.indexOf(key2)+key2.length(),s.length());
  				  }
  			  }
  		  }
  	  }
  	  System.out.println();
  	  
  	  for(int i=0;i<list.size();i++){
  		  System.out.println(list.get(i)+"\t"+listIDs.get(i));
  	  }
    }
    
    public static void makeIdentifiersTable(String fn) throws Exception{
  	  Vector<String> list = Utils.loadStringListFromFile(fn);
  	  Vector<Vector<String>> listIDs = new Vector<Vector<String>>();
  	  Vector<String> idnames = new Vector<String>();
  	  for(int i=0;i<list.size();i++){
  		  System.out.print(i+" ");
  		  String id = list.get(i);
  		  Vector<String> ids = Utils.guessProteinIdentifiers(id);
  		  listIDs.add(ids);
  		  for(int j=0;j<ids.size();j++){
  			  String name = ids.get(j).split(":")[0];
  			  if(!idnames.contains(name))
  				  idnames.add(name);
  		  }
  	  }
  	  System.out.println();
  	  for(int i=0;i<idnames.size();i++) System.out.print(idnames.get(i)+"\t"); System.out.println();
  	  for(int i=0;i<listIDs.size();i++){
  		  String idm[] = new String[idnames.size()];
  		  for(int j=0;j<idm.length;j++) idm[j] = "";
  		  Vector<String> ids = listIDs.get(i);
  		  for(int j=0;j<ids.size();j++){
  			  String id = ids.get(j);
  			  String v[] = id.split(":");
  			  String name = v[0];
  			  if(v.length>1)
  				  idm[idnames.indexOf(name)] = v[1];
  		  }
  		  for(int j=0;j<idm.length;j++) System.out.print(idm[j]+"\t"); System.out.println();
  	  }
  }
    
    
  public static Citation convertPMIDtoCitation(String pmid){
	  Citation citation = new ConnectionToDatabases().new Citation();
	  /*Random r = new Random();
	  citation.year = 1990+(int)(r.nextFloat()*23);
	  citation.firstAuthorName = "Author";
	  citation.pmid = pmid;
	  citation.oneLineCitation = "Author 1, Author 2. "+citation.year+". Science";*/
	  
	  String record = Utils.downloadURL("http://www.ncbi.nlm.nih.gov/pubmed/"+pmid+"?report=xml&format=text");
	  record = record.replace("&lt;", "<");
	  record = record.replace("&gt;", ">");
	  try{
	  if(record.length()<10) citation = null;
	  else{
		  LineNumberReader lr = new LineNumberReader(new StringReader(record));
		  String s = null;
		  Vector<String> authors = new Vector<String>();
		  
		  while((s=lr.readLine())!=null){
			  s = Utils.cutFirstLastNonVisibleSymbols(s);
			  //System.out.println(s);
			  if(s.startsWith("<Year>"))
				  citation.year = Integer.parseInt(s.substring(6, 10));
			  if(s.startsWith("<ISOAbbreviation>"))
				  citation.journal = cutBetweenTags(s,"ISOAbbreviation");
			  if(s.startsWith("<Volume>"))
				  citation.volume = cutBetweenTags(s,"Volume");
			  if(s.startsWith("<Issue>"))
				  citation.issue = cutBetweenTags(s,"Issue");
			  citation.title = cutBetweenTags(record,"ArticleTitle");
			  if(s.startsWith("<LastName>")){
				  String lastname = cutBetweenTags(s,"LastName");
				  authors.add(lastname);
			  }
			  if(s.startsWith("<Initials>")){
				  String initials = cutBetweenTags(s,"Initials");
				  String lastname = authors.get(authors.size()-1);
				  authors.set(authors.size()-1, lastname+" "+initials);
			  }
			  if(s.startsWith("<MedlinePgn>")){
				  citation.pages = cutBetweenTags(s,"MedlinePgn");
			  }
			  if(s.startsWith("<PublicationType")){
				  if(s.toLowerCase().contains("review"))
					  citation.isReview = true;
			  }
		  }
		  
		  if(authors.size()>0)
			  citation.firstAuthorName = authors.get(0);
		  for(String author: authors){
			  citation.allAuthors+=author+", ";
		  }
		  if(citation.allAuthors.endsWith(", "))
			  citation.allAuthors = citation.allAuthors.substring(0, citation.allAuthors.length()-2);
		  
	  }
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	  return citation;
  }
  
  public static String cutBetweenTags(String s, String tag){
	  String res = "";
	  int k1 = s.indexOf("<"+tag+">");
	  int k2 = s.indexOf("</"+tag+">");
	  if((k1>=0)&(k2>=0))
		  res = s.substring(k1+tag.length()+2, k2);
	  return res;
  }
  
  public static HashMap<String, Float> CalculateReferenceConfidenceScores(SbmlDocument sbml, String bibfile){
	  
	  float maxScoreValue = 5;
	  
	  HashMap<String, Float> dscores = new HashMap<String, Float>();
	  Vector<String> refs = Utils.loadStringListFromFile(bibfile);
	  HashMap<String, String> citations = new HashMap<String,String>();
	  for(String s: refs){
		  StringTokenizer st = new StringTokenizer(s,"\t");
		  String pmid = st.nextToken();
		  String citation = st.nextToken();
		  if(pmid.startsWith("PMID:"))
			  pmid = pmid.substring(5, pmid.length());
		  citations.put(pmid, citation);
	  }
	  for(ReactionDocument.Reaction r: sbml.getSbml().getModel().getListOfReactions().getReactionArray()){
		  String id = r.getId();
		  String note = Utils.getValue(r.getNotes());
		  float dscore = CalculateReferenceConfidenceScoreFromText(note, citations);
		  if(dscore>maxScoreValue)
			  dscore = maxScoreValue;
		  dscores.put(id, dscore);
	  }
	  for(SpeciesDocument.Species sp: sbml.getSbml().getModel().getListOfSpecies().getSpeciesArray()){
		  String clss = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
		  if(clss.equals("COMPLEX")){
			  String id = sp.getId();
			  String note = Utils.getValue(sp.getNotes());
			  float dscore = CalculateReferenceConfidenceScoreFromText(note, citations);
			  if(dscore>maxScoreValue)
				  dscore = maxScoreValue;
			  dscores.put(id, dscore);
		  }
	  }
	  return dscores;
  }
  
  public static float CalculateReferenceConfidenceScoreFromText(String text, HashMap<String, String> citations){
	  float dscore = 0f;
	  float scoreSimpleCitation = 1f;
	  float scoreReviewCitation = 0f;
	  Vector<String> pmids = ACSNProcedures.getAllPMIDsFromCellDesigner(text);
	  for(String p: pmids){
		  String citation = citations.get(p);
		  if(citation!=null){
		  if(citation.contains("[REVIEW]"))
			  dscore+=scoreReviewCitation;
		  else
			  dscore+=scoreSimpleCitation;
		  }
	  }
	  return dscore;
  }
  
  public static void calculatePPIDistanceMatrix(Vector<String> listOfNodes, Graph ppi_network, String fn)  throws Exception{
	  SubnetworkProperties snp = new SubnetworkProperties();
	  snp.network = ppi_network;
	  snp.selectNodesFromList(listOfNodes);
	  
	  Vector<String> selected = new Vector<String>();
	  FileWriter fw = new FileWriter(fn.substring(0, fn.length()-4)+"_nodes.txt");
	  for(int i=0;i<snp.subnetwork.Nodes.size();i++) selected.add(snp.subnetwork.Nodes.get(i).Id);
	  for(String s: selected) fw.write(s+"\n"); fw.close();
	  
	  double distMat[][] = snp.connectByShortestPaths(snp.subnetwork, ppi_network);
	  snp.saveDistanceMatrix(distMat, fn, true);
  }
  
  public static HashMap<String, Float> CalculateFunctionalConfidenceScores(GMTFile neighbours, String distMatrixFile, String nodeListFile, String outFileName) throws Exception{
  	HashMap<String, Float> fscores = new HashMap<String, Float>();
  	int distanceMatrix[][] = loadIntegerDistanceMatrix(distMatrixFile, nodeListFile);
  	Vector<String> nodes = Utils.loadStringListFromFile(nodeListFile);
  	// just small acceleration
  	HashMap<String, Integer> nodeIndex = new HashMap<String, Integer>();
  	for(int i=0;i<nodes.size();i++) nodeIndex.put(nodes.get(i), i);
  	
  	//FileWriter fw2 = new FileWriter(outFileName+"2");
  	//FileWriter fw3 = new FileWriter(outFileName+"3");
  	//FileWriter fw4 = new FileWriter(outFileName+"4");
  	//FileWriter fw5 = new FileWriter(outFileName+"5");
  	
  	for(int i=0;i<neighbours.setnames.size();i++){
  		String name = neighbours.setnames.get(i);
  		HashSet<String> set = neighbours.sets.get(i);
  		Vector<Integer> setvindex = new Vector<Integer>();
  		for(String s: set){
  			if(s!=null)if(nodeIndex.get(s)!=null){
  			int k=nodeIndex.get(s);
  			setvindex.add(k);
  			}
  		}

  		float score = 0;
  		if(setvindex.size()>1){
  			score = ComputeFunctionalConfidenceScoreFromDistanceMatrix(setvindex, distanceMatrix, 5);
  			//if(setvindex.size()==2) fw2.write(score+"\n");
  			//if(setvindex.size()==3) fw3.write(score+"\n");
  			//if(setvindex.size()==4) fw4.write(score+"\n");
  			//if(setvindex.size()==5) fw5.write(score+"\n");
  		}
  		
  		fscores.put(name, score);
  	}
  	
  	//fw2.close();
  	//fw3.close();
  	//fw4.close();
  	//fw5.close();
  	
  	if(outFileName!=null){
  	FileWriter fw = new FileWriter(outFileName);
	Set<String> fkeys = fscores.keySet();
	for(String s: fkeys){
		float score = fscores.get(s);
		fw.write(s+"\t"+score+"\n");
	}
  	fw.close();
  	}
  	
  	return fscores;
  }

  public static float ComputeFunctionalConfidenceScoreFromDistanceMatrix(Vector<Integer> setvindex, int distanceMatrix[][], int maxScoreValue){
	float score = 0f;
	Vector<Integer> distances = new Vector<Integer>();
	for(int k=0;k<setvindex.size();k++)
		for(int j=k+1;j<setvindex.size();j++){
			distances.add(distanceMatrix[setvindex.get(k)][setvindex.get(j)]);
		}
	float f[] = new float[distances.size()];
	for(int k=0;k<distances.size();k++) f[k] = distances.get(k);
	//Float md = Utils.calcMedian(f);
	Float md = Utils.calcMean(f);
	
	md = (float)((int)(md+0.5));
	
	score = md;
	score = score-1;
	score = maxScoreValue - score;
	if(score<0) score = 0;
	/*if(score==0) score=maxScoreValue+1;
	score = score-1;
	if(score>maxScoreValue) score = maxScoreValue;
	score = maxScoreValue-score;*/
	return score;
  }

  
  public static Vector<Vector<Float>> computeNullDistributionForFunctionalScore(int sampleSize, int maxsetsize, String distMatrixFile, String nodeListFile, String outFileName) throws Exception{
	  Vector<Vector<Float>> nulldist = new Vector<Vector<Float>>();
	  Vector<String> nodes = Utils.loadStringListFromFile(nodeListFile);
	  int distanceMatrix[][] = loadIntegerDistanceMatrix(distMatrixFile, nodeListFile);
	  	// just small acceleration
	  	HashMap<String, Integer> nodeIndex = new HashMap<String, Integer>();
	  	for(int i=0;i<nodes.size();i++) nodeIndex.put(nodes.get(i), i);
	  
	  Random r = new Random();
	  for(int i=2;i<maxsetsize;i++){
	  Vector<Float> scores = new Vector<Float>();
		  for(int j=0;j<sampleSize;j++){
		  Vector<String> sample = new Vector<String>();
			  for(int l=0;l<i;l++){			  
				  int k = r.nextInt(nodes.size());
				  sample.add(nodes.get(k));
			  }
		  Vector<Integer> setvindex = new Vector<Integer>();
		  for(String s: sample) setvindex.add(nodeIndex.get(s));
		  float score = ComputeFunctionalConfidenceScoreFromDistanceMatrix(setvindex,distanceMatrix,5);
		  scores.add(score);
		  }
		  nulldist.add(scores);
	  }
	  
	  FileWriter fw = new FileWriter(outFileName);
	  for(int i=0;i<nulldist.size();i++){
		  for(int j=0;j<nulldist.get(i).size();j++)
			  fw.write(nulldist.get(i).get(j)+"\t");
		  fw.write("\n");
	  }
	  fw.close();
	  
	  return nulldist;
  }
  
  public static int[][] loadIntegerDistanceMatrix(String fn, String nodeListFile) throws Exception{
	  Vector<String> nodes = Utils.loadStringListFromFile(nodeListFile);
	  int dm[][] = new int[nodes.size()][nodes.size()];
	  Vector<Integer> alldistvalues = new Vector<Integer>();
	  LineNumberReader lr = new LineNumberReader(new FileReader(fn));
	  String s = null;
	  int i=0;
	  while((s=lr.readLine())!=null){
		  StringTokenizer st = new StringTokenizer(s,"\t");
		  int k=0;
		  while(st.hasMoreTokens()){
			  String ds = st.nextToken();
			  if(ds.equals("-")){
				  dm[i][k] = 10;
			  }else{
				  dm[i][k] = Integer.parseInt(ds);
			  }
			  dm[k][i] = dm[i][k];
			  alldistvalues.add(dm[k][i]);
			  k++;
		  }
		  i++;
	  }
	  FileWriter fw = new FileWriter(fn.substring(0, fn.length()-4)+".allvalues");
	  for(int k: alldistvalues) fw.write(k+"\n");
	  return dm;
  }
  
  public static void annotateCellDesignerFileWithConfidenceScores(SbmlDocument cd, String DScoreFile, String FScoreFile) throws Exception{
	  System.out.println("Annotating confidence scores for "+cd.getSbml().getModel().getId());
	  Vector<String> dscoreslist = Utils.loadStringListFromFile(DScoreFile);
	  HashMap<String, Float> dscores = new HashMap<String, Float>(); 
	  for(String s: dscoreslist){
		  StringTokenizer st = new StringTokenizer(s,"\t");
		  String name = st.nextToken();
		  float score = Float.parseFloat(st.nextToken());
		  dscores.put(name, score);
	  }
	  Vector<String> fscoreslist = Utils.loadStringListFromFile(FScoreFile);
	  HashMap<String, Float> fscores = new HashMap<String, Float>(); 
	  for(String s: fscoreslist){
		  StringTokenizer st = new StringTokenizer(s,"\t");
		  String name = st.nextToken();
		  float score = Float.parseFloat(st.nextToken());
		  fscores.put(name, score);
	  }	  
	  
	  ModifyCellDesignerNotes mn = new ModifyCellDesignerNotes();
	  mn.sbmlDoc = cd;
	  mn.formatAnnotation = false;
	  mn.comments = mn.exportCellDesignerNotes();
	  mn.splitCommentsIntoNotes();
	  
	  for(int i=0;i<cd.getSbml().getModel().getListOfReactions().sizeOfReactionArray();i++){
		  String reid = cd.getSbml().getModel().getListOfReactions().getReactionArray()[i].getId();
		  
		  int dscore = 0; 
		  int fscore = 0;
		  
		  if(dscores.get(reid)!=null)
			  dscore = (int)(dscores.get(reid).floatValue());
		  if(fscores.get(reid)!=null)
			  fscore = (int)(fscores.get(reid).floatValue());
		  
		  ModifyNotesWithConfidences(reid, mn, dscore, fscore);
	  }
	  
	  for(SpeciesDocument.Species sp: cd.getSbml().getModel().getListOfSpecies().getSpeciesArray()){
		  String clss = Utils.getValue(sp.getAnnotation().getCelldesignerSpeciesIdentity().getCelldesignerClass());
		  if(clss.equals("COMPLEX")){
			  String id = sp.getId();
			  int dscore = 0; 
			  int fscore = 0;			  
			  if(dscores.get(id)!=null)
				  dscore = (int)(dscores.get(id).floatValue());
			  if(fscores.get(id)!=null)
				  fscore = (int)(fscores.get(id).floatValue());
			  ModifyNotesWithConfidences(id, mn, dscore, fscore);
		  }
	  }
	  
	  //System.out.println((new File(DScoreFile)).getParent());
	  mn.comments = mn.mergeNotesIntoComments();
	  //Utils.saveStringToFile(mn.comments, (new File(DScoreFile)).getParent()+"/comments.txt");
	  mn.ModifyCellDesignerNotes();
  }
  
  public static void ModifyNotesWithConfidences(String id, ModifyCellDesignerNotes mn, int dscore, int fscore){
	  int k = mn.keys.indexOf(id);
	  String note = mn.noteAdds.get(k);
	  Vector<AnnotationSection> secs = mn.divideInSections(note);
	  //secs = mn.reformatSectionsWithDefaultSection(secs);
	  AnnotationSection confidenceSection = mn.getSectionByName(secs, "Confidence");
	  if(confidenceSection==null){
		  confidenceSection = (new ModifyCellDesignerNotes()).new AnnotationSection();
		  confidenceSection.name = "Confidence";
		  secs.add(confidenceSection);
	  }
		  if(confidenceSection.content.trim().equals("")){
			  String color = "red";
			  if(fscore==0) color = "black";
			  if(fscore==1) color = "purple";
			  if(fscore==2) color = "blue";
			  if(fscore==3) color = "green";
			  if(fscore==4) color = "orange";
			  confidenceSection.content+="CONFIDENCE:"+dscore+"_"+fscore+" REF="+dscore+" <span style='color:"+color+"'>FUNC="+fscore+"</span>";
		  }
	  note = "";
	  for(int j=1;j<secs.size();j++) note+=secs.get(j).toString();
	  mn.noteAdds.set(k, note);

  }
  
  public static void computeScoresForACSN() throws Exception{
	  
	  SbmlDocument sbml = CellDesigner.loadCellDesigner("C:/Datas/acsn/assembly/apoptosis_src/apoptosis_master.xml");
	  HashMap<String, Float> dscores = CalculateReferenceConfidenceScores(sbml,"C:/Datas/acsn/assembly/acsn_src/acsn_bib.txt");
	  writeScoresToFile(dscores,"C:/Datas/acsn/assembly/_confidence_scores/apoptosis_dscores.txt");	
	  
	  sbml = CellDesigner.loadCellDesigner("C:/Datas/acsn/assembly/cellcycle_src/cellcycle_master.xml");
	  dscores = CalculateReferenceConfidenceScores(sbml,"C:/Datas/acsn/assembly/acsn_src/acsn_bib.txt");
	  writeScoresToFile(dscores,"C:/Datas/acsn/assembly/_confidence_scores/cellcycle_dscores.txt");
	  
	  sbml = CellDesigner.loadCellDesigner("C:/Datas/acsn/assembly/dnarepair_src/dnarepair_master.xml");
	  dscores = CalculateReferenceConfidenceScores(sbml,"C:/Datas/acsn/assembly/acsn_src/acsn_bib.txt");
	  writeScoresToFile(dscores,"C:/Datas/acsn/assembly/_confidence_scores/dnarepair_dscores.txt");
	  
	  sbml = CellDesigner.loadCellDesigner("C:/Datas/acsn/assembly/emtcellmotility_src/emtcellmotility_master.xml");
	  dscores = CalculateReferenceConfidenceScores(sbml,"C:/Datas/acsn/assembly/acsn_src/acsn_bib.txt");
	  writeScoresToFile(dscores,"C:/Datas/acsn/assembly/_confidence_scores/emtcellmotility_dscores.txt");
	  
	  sbml = CellDesigner.loadCellDesigner("C:/Datas/acsn/assembly/survival_src/survival_master.xml");
	  dscores = CalculateReferenceConfidenceScores(sbml,"C:/Datas/acsn/assembly/acsn_src/acsn_bib.txt");
	  writeScoresToFile(dscores,"C:/Datas/acsn/assembly/_confidence_scores/survival_dscores.txt");
	  
	  GMTFile gmt = new GMTFile();
	  gmt.load("C:/Datas/acsn/assembly/_confidence_scores/apoptosis.gmt");			
	  CalculateFunctionalConfidenceScores(gmt, "C:/Datas/acsn/assembly/_confidence_scores/acsn_dist.txt", "C:/Datas/acsn/assembly/_confidence_scores/acsn_dist_nodes.txt", "C:/Datas/acsn/assembly/_confidence_scores/apoptosis_fscores.txt");

	  gmt = new GMTFile();
	  gmt.load("C:/Datas/acsn/assembly/_confidence_scores/cellcycle.gmt");			
	  CalculateFunctionalConfidenceScores(gmt, "C:/Datas/acsn/assembly/_confidence_scores/acsn_dist.txt", "C:/Datas/acsn/assembly/_confidence_scores/acsn_dist_nodes.txt", "C:/Datas/acsn/assembly/_confidence_scores/cellcycle_fscores.txt");
	  
	  gmt = new GMTFile();
	  gmt.load("C:/Datas/acsn/assembly/_confidence_scores/dnarepair.gmt");			
	  CalculateFunctionalConfidenceScores(gmt, "C:/Datas/acsn/assembly/_confidence_scores/acsn_dist.txt", "C:/Datas/acsn/assembly/_confidence_scores/acsn_dist_nodes.txt", "C:/Datas/acsn/assembly/_confidence_scores/dnarepair_fscores.txt");
	  
	  gmt = new GMTFile();
	  gmt.load("C:/Datas/acsn/assembly/_confidence_scores/emtcellmotility.gmt");			
	  CalculateFunctionalConfidenceScores(gmt, "C:/Datas/acsn/assembly/_confidence_scores/acsn_dist.txt", "C:/Datas/acsn/assembly/_confidence_scores/acsn_dist_nodes.txt", "C:/Datas/acsn/assembly/_confidence_scores/emtcellmotility_fscores.txt");
	  
	  gmt = new GMTFile();
	  gmt.load("C:/Datas/acsn/assembly/_confidence_scores/survival.gmt");			
	  CalculateFunctionalConfidenceScores(gmt, "C:/Datas/acsn/assembly/_confidence_scores/acsn_dist.txt", "C:/Datas/acsn/assembly/_confidence_scores/acsn_dist_nodes.txt", "C:/Datas/acsn/assembly/_confidence_scores/survival_fscores.txt");	  
	  
  }
  
  public static void writeScoresToFile(HashMap<String, Float> fscores, String fn) throws Exception{
	  FileWriter fw = new FileWriter(fn);
		Set<String> fkeys = fscores.keySet();
		for(String s: fkeys){
			float score = fscores.get(s);
			fw.write(s+"\t"+score+"\n");
		}
	  	fw.close();
  }
    

	
	
}
