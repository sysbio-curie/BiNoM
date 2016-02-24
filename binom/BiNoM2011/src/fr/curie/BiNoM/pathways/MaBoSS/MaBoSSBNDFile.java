package fr.curie.BiNoM.pathways.MaBoSS;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import fr.curie.BiNoM.pathways.utils.Utils;

public class MaBoSSBNDFile {
	
	Vector<String> lines = null;
	String fileNamePrefix = null;
	String folder = null;
	Vector<String> cfgFiles = null;
	String file_bnd = null;
	String name = null;
	String description = null;
	
	public static int maxNumberOfLogicalRuleChanges = 1;
	public static boolean makeChangesIn2Rules = false;
	public static boolean makeChangesInAllRules = false;
	public static int numberOfSamplesModelVariants = 1000;
	
	HashMap<String,String> ruleModifications = new HashMap<String,String>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			/*String s = "logic = $Migration_up | (Invasion & EMT & VIM & !AKT1 & !p63 & !miR200 & AKT2 & ERK);";
			Vector<String> rules = generatePermutedLogicRules(s);
			//rules  = filterSequencesByEditingDistance(rules, s, maxNumberOfLogicalRuleChanges);
			//Vector<String> rules = getAllPossibleSequences(3);
			for(String sr: rules)
				System.out.println(sr);
			System.exit(0);*/
			
			String cfgFile = args[0];
			String bndFile = args[1];
			
			for(int i=0;i<args.length;i++){
				if(args[i].equals("-c"))
					cfgFile = args[i+1];
				if(args[i].equals("-b"))
					bndFile = args[i+1];
				if(args[i].equals("-level"))
					maxNumberOfLogicalRuleChanges = Integer.parseInt(args[i+1]);
				if(args[i].equals("-several"))
					makeChangesIn2Rules = true;
				if(args[i].equals("-all"))
					makeChangesInAllRules = true;
				if(args[i].equals("-samples"))
					numberOfSamplesModelVariants = Integer.parseInt(args[i+1]);
				
			}
			MaBoSSBNDFile bnd = new MaBoSSBNDFile();
			bnd.cfgFiles = new Vector<String>(); 
			StringTokenizer st = new StringTokenizer(cfgFile,"+");
			while(st.hasMoreTokens())
				bnd.cfgFiles.add(st.nextToken());
			bnd.load(bndFile);
			bnd.makeFolderWithAllLogicMutants();

		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public void load(String fn){
		fileNamePrefix = (new File(fn)).getName();
		file_bnd = fileNamePrefix;
		if(fileNamePrefix.endsWith(".bnd"))
			fileNamePrefix = fileNamePrefix.substring(0, fileNamePrefix.length()-4);
		folder = (new File(fn)).getParentFile().getAbsolutePath();
		lines = Utils.loadStringListFromFile(fn);
		name = fileNamePrefix+"__WT";
		description = "wild type model";
	}
	
	public void makeFolderWithAllLogicMutants(){
		String mutantFolder = folder+"/"+fileNamePrefix+"_mutants_logics";
		(new File(mutantFolder)).mkdir();
		try{
		FileWriter fw = new FileWriter(mutantFolder+"/run.sh");
		FileWriter fwdesc = new FileWriter(mutantFolder+"/descriptions.txt");
		//fw.write("../MaBoSS -c "+cfgFile+".cfg -o "+name+"_out "+file_bnd+"\n");
		int k = 0;
		
		Vector<MaBoSSBNDFile> allMutants = makeLogicMutants();
		if(makeChangesIn2Rules){
			Vector<MaBoSSBNDFile> mutantsIn2Rules = makeLogicMutants2Rules();
			for(MaBoSSBNDFile f: mutantsIn2Rules)
				allMutants.add(f);
		}
		if(makeChangesInAllRules){
			Vector<MaBoSSBNDFile> mutantsInAllRules = makeLogicMutantsAllRulesSample();
			for(MaBoSSBNDFile f: mutantsInAllRules)
				allMutants.add(f);
		}
		int num = allMutants.size();
		
		
		for(MaBoSSBNDFile mutant: allMutants){for(String cfgFile: cfgFiles){
			String cfg = (new File(cfgFile)).getName();
			if(cfgFile.endsWith(".cfg")) cfg = cfg.substring(0, cfg.length()-4);
			k++;
			fw.write("echo \""+k+"/"+num+": "+mutant.name+"\"\n");
			String mutant_name = mutant.name+"_lm";
			if(!cfg.equals("WT"))
				mutant_name = mutant.name+"_lm--"+cfg+"_cm";
			fw.write("../MaBoSS -c "+cfgFile+" -o "+mutant_name+" "+mutant.file_bnd+"\n");
			mutant.save(mutantFolder+"/"+mutant.name+".bnd");
		}
		String mutant_short_name = mutant.name;
		if(mutant_short_name.startsWith(mutant.fileNamePrefix))
			mutant_short_name = mutant_short_name.substring(mutant.fileNamePrefix.length()+1, mutant_short_name.length());
		fwdesc.write(mutant_short_name+"\t"+mutant.description+"\n");
		}
		fw.close();
		fwdesc.close();
		
		fw = new FileWriter(mutantFolder+"/run.bat");
		//fw.write("..\\MaBoSS.exe -c "+cfgFile+" -o "+fileNamePrefix+" "+file_bnd+"\n");
		for(MaBoSSBNDFile mutant: allMutants){
			for(String cfgFile: mutant.cfgFiles){
			String cfg = (new File(cfgFile)).getName();
			if(cfg.endsWith(".cfg")) cfg = cfg.substring(0, cfg.length()-4);
			String mutant_name = mutant.name+"_lm";
			if(!cfg.equals("WT"))
				mutant_name = mutant.name+"_lm--"+cfg+"_cm";
			fw.write("..\\MaBoSS.exe -c "+cfgFile+" -o "+mutant_name+" "+mutant.file_bnd+"\n");
			System.out.println(mutant.file_bnd);
		}}
		fw.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Vector<MaBoSSBNDFile> makeLogicMutants(){
		Vector<MaBoSSBNDFile> mutants = new Vector<MaBoSSBNDFile>();
		this.file_bnd = file_bnd.substring(0, file_bnd.length()-4)+"__WT.bnd";
		mutants.add(this);
		Vector<String> newLines = new Vector<String>();
		String currentNode = "";
		for(int l=0;l<lines.size();l++){
			String line = lines.get(l);
			if(line.trim().startsWith("Node")){
				StringTokenizer st = new StringTokenizer(line,"\t {}");
				st.nextToken();
				currentNode = st.nextToken();
			}
			if(!line.trim().startsWith("logic")){
				newLines.add(line);
			}else{
				Vector<String> rules = generatePermutedLogicRules(line, true);
				int k=0;
				for(String rule: rules)if(!rule.equals(line)){
					MaBoSSBNDFile mutant = new MaBoSSBNDFile();
					mutant.fileNamePrefix = fileNamePrefix;
					mutant.folder = folder;
					mutant.cfgFiles = cfgFiles;
					k++;
					mutant.name = fileNamePrefix + "_"+currentNode+"_"+k;
					System.out.println("Adding mutant "+mutant.name);
					mutant.file_bnd = mutant.name+".bnd";
					int level = calcEditingDistance(rule.trim(),line.trim());
					mutant.description = currentNode+";"+rule+";LEVEL"+level;
					mutant.lines = new Vector<String>();
					ruleModifications.put(currentNode, rule);
					for(int j=0;j<newLines.size();j++)
						mutant.lines.add(newLines.get(j));
					mutant.lines.add(rule);
					for(int ll=l+1;ll<lines.size();ll++)
						mutant.lines.add(lines.get(ll));
					mutants.add(mutant);
				}
				newLines.add(line);
			}
		}
		return mutants;
	}
	
	public Vector<MaBoSSBNDFile> makeLogicMutants2Rules(){
		Vector<MaBoSSBNDFile> mutants = new Vector<MaBoSSBNDFile>();
		HashMap<String,String> allrules = allLogicalRules();
		Vector<String> nodes = new Vector<String>();
		Iterator<String> keys = allrules.keySet().iterator();
		while(keys.hasNext()) nodes.add(keys.next());
		
		for(int i=0;i<nodes.size();i++)
			for(int j=i+1;j<nodes.size();j++){
				int k=0;
				String rulei = allrules.get(nodes.get(i));
				String rulej = allrules.get(nodes.get(j));
				int maxtemp = maxNumberOfLogicalRuleChanges;
				maxNumberOfLogicalRuleChanges = 1;
				Vector<String> modsi = generatePermutedLogicRules(rulei, false);
				Vector<String> modsj = generatePermutedLogicRules(rulej, false);
				maxNumberOfLogicalRuleChanges = maxtemp;
				for(int ii=0;ii<modsi.size();ii++)
					for(int jj=0;jj<modsj.size();jj++){
						k++;
						String s = nodes.get(i)+"_"+nodes.get(j)+"_"+k;
						MaBoSSBNDFile mutant = new MaBoSSBNDFile();
						mutant.fileNamePrefix = fileNamePrefix;
						mutant.folder = folder;
						mutant.cfgFiles = cfgFiles;
						mutant.name = fileNamePrefix +"_"+ s;
						System.out.println("Adding mutant "+mutant.name);
						mutant.file_bnd = mutant.name+".bnd";
						mutant.description = nodes.get(i)+";"+modsi.get(ii)+nodes.get(j)+";"+modsj.get(jj)+";LEVEL2.5";
						mutant.lines = new Vector<String>();
						for(int kk=0;kk<lines.size();kk++){
							String line = lines.get(kk);
							if(line.trim().equals(rulei.trim())) line = modsi.get(ii);
							if(line.trim().equals(rulej.trim())) line = modsj.get(jj);
							mutant.lines.add(line);
						}
						mutants.add(mutant);
					}
			}
		return mutants;
	}
	
	public HashMap<String,String> allLogicalRules(){
		HashMap<String,String> allrules = new HashMap<String,String>();
		String currentNode = "";
		for(int l=0;l<lines.size();l++){
			String line = lines.get(l);
			if(line.trim().startsWith("Node")){
				StringTokenizer st = new StringTokenizer(line,"\t {}");
				st.nextToken();
				currentNode = st.nextToken();
			}
			if(line.trim().startsWith("logic")){
				allrules.put(currentNode, line);
			}
		}
		return allrules;
	}
	
	public Vector<MaBoSSBNDFile> makeLogicMutantsAllRulesSample(){
		Vector<MaBoSSBNDFile> mutants = new Vector<MaBoSSBNDFile>();
		HashMap<String,String> allrules = allLogicalRules();
		
		return mutants;
	}
	
	
	public static Vector<String> generatePermutedLogicRules(String s, boolean includeWTsequence){
		Vector<String> logics = new Vector<String>();
		String wtsequence = "";
		for(char c: s.toCharArray()){
			if(c=='|') wtsequence+="|";
			if(c=='&') wtsequence+="&";
		}
		if(!wtsequence.equals("")){
			//Vector<String> seqTable = getAllPossibleSequences(wtsequence.length());
			Vector<String> seqTable = getMutantSequences(wtsequence, maxNumberOfLogicalRuleChanges);
			seqTable  = filterSequencesByEditingDistance(seqTable, wtsequence, maxNumberOfLogicalRuleChanges);
			seqTable.remove(seqTable.indexOf(wtsequence));
			if(includeWTsequence)
				seqTable.insertElementAt(wtsequence, 0);
			for(int k=0;k<seqTable.size();k++){
			String rule = new String(s);
			int l = 0;
			for(int j=0;j<rule.length();j++){
				char c = rule.charAt(j);
				if((c=='|')||(c=='&')){
					rule = rule.substring(0, j)+seqTable.get(k).charAt(l)+rule.substring(j+1,rule.length());
					l++;
				}
			}
			logics.add(rule);
			}
		}
		return logics;
	}
	
	public static Vector<String> getAllPossibleSequences(int length){
		Vector<String> seqs = new Vector<String>();
		int num = (int)(Math.pow(2, length)+0.2); 
		for(int i=0;i<num;i++){
			String s = Integer.toBinaryString(i);
			String prefix = "";
			for(int k=s.length();k<length;k++)
				prefix+="0";
			s = prefix+s;
			s = Utils.replaceString(s, "0", "|");
			s = Utils.replaceString(s, "1", "&");
			seqs.add(s);
		}
		return seqs;
	}
	
	public static Vector<String> getMutantSequences(String s, int level){
		Vector<String> res = new Vector<String>();
		res.add(s);
		if((level==1)||(level==2)){
			for(int i=0;i<s.length();i++){
				String s1 = new String(s);
				char s1c[] = s1.toCharArray();
				if(s1c[i]=='&') s1c[i] = '|';
				else
				if(s1c[i]=='|') s1c[i] = '&';
				s1 = new String(s1c);
				res.add(s1);
			}
		}
		if(level==2){
			for(int i=0;i<s.length();i++)for(int j=i+1;j<s.length();j++)if(i!=j){
				String s1 = new String(s);
				char s1c[] = s1.toCharArray();
				if(s1c[i]=='&') s1c[i] = '|'; else
				if(s1c[i]=='|') s1c[i] = '&'; 
				if(s1c[j]=='&') s1c[j] = '|'; else
				if(s1c[j]=='|') s1c[j] = '&';
				s1 = new String(s1c);
				res.add(s1);
			}
		}
		return res;
	}
	
	public void save(String fn){
		try{
		FileWriter fw = new FileWriter(fn);
		for(String line: lines)
			fw.write(line+"\n");
		fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Vector<String> filterSequencesByEditingDistance(Vector<String> seqTable, String wtsequence, int maxNumberOfLogicalRuleChanges){
		Vector<String> res = new Vector<String>();
		for(int i=0;i<seqTable.size();i++){
			String s = seqTable.get(i);
			int dist = calcEditingDistance(s,wtsequence);
			if(dist<=maxNumberOfLogicalRuleChanges)
				res.add(s);
		}
		return res;
	}
	
	public static int calcEditingDistance(String s1, String s2){
		int ml = Math.max(s1.length(), s2.length());
		int dist = 0;
		for(int i=0;i<ml;i++){
			if(s1.charAt(i)!=s2.charAt(i))
				dist++;
		}
		return dist;
	}
	
	

}
