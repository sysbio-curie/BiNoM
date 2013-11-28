package fr.curie.BiNoM.pathways.utils.acsn;

import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import com.hp.hpl.jena.query.function.library.namespace;

import fr.curie.BiNoM.pathways.utils.Utils;

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
			
			Citation cit = convertPMIDtoCitation("10021362");
			System.out.println(cit.oneLineCitation());
			
			
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
    

	
	
}
