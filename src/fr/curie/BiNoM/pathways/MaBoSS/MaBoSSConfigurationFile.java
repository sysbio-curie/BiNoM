package fr.curie.BiNoM.pathways.MaBoSS;

import java.io.File;
import java.io.FileWriter;
import java.util.StringTokenizer;
import java.util.Vector;

import vdaoengine.data.VDataTable;
import fr.curie.BiNoM.pathways.utils.Utils;

public class MaBoSSConfigurationFile {

	Vector<String> lines = null;
	Vector<String> variables = null;
	String fileNamePrefix = null;
	String folder = null;
	boolean doSingleMutants = true;
	boolean doDoubleMutants = true;
	boolean doOverExpression = true;	
	Vector<String> excludeVariables = new Vector<String>();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String cfgFile = args[0];
		String bndFile = args[1];
		boolean doSingleMutants = true;
		boolean doDoubleMutants = true;
		boolean doOverExpression = true;
		Vector<String> excludeVariables = new Vector<String>();
		
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-c"))
				cfgFile = args[i+1];
			if(args[i].equals("-b"))
				bndFile = args[i+1];
			if(args[i].equals("-single"))
				doDoubleMutants = false;
			if(args[i].equals("-double"))
				doSingleMutants = false;
			if(args[i].equals("-exclude")){
				String excludeString = args[i+1];
				StringTokenizer st = new StringTokenizer(excludeString,",");
				while(st.hasMoreTokens())
					excludeVariables.add(st.nextToken());
			}
			if(args[i].equals("-onlyko")){
				doOverExpression = false;
			}
			
		}
		MaBoSSConfigurationFile cfg = new MaBoSSConfigurationFile();
		cfg.doDoubleMutants = doDoubleMutants;
		cfg.doSingleMutants = doSingleMutants;
		cfg.excludeVariables = excludeVariables;
		cfg.doOverExpression = doOverExpression;
		cfg.load(cfgFile);
		cfg.makeFolderWithAllMutants(bndFile);
	}
	
	public void load(String fn){
		fileNamePrefix = (new File(fn)).getName();
		if(fileNamePrefix.endsWith(".cfg"))
			fileNamePrefix = fileNamePrefix.substring(0, fileNamePrefix.length()-4);
		folder = (new File(fn)).getParentFile().getAbsolutePath();
		lines = Utils.loadStringListFromFile(fn);
		detectVariables();
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
	
	public void detectVariables(){
		variables = new Vector<String>();
		for(String line: lines){
			if(line.contains(".is_internal")){
				StringTokenizer st = new StringTokenizer(line,"=");
				String name = st.nextToken();
				String value = st.nextToken().trim();
				if(value.endsWith(";"))
					value = value.substring(0, value.length()-1);
				if(name.endsWith(".is_internal"))
					name = name.substring(0, name.length()-12);
				if(value.trim().equals("1")){
					variables.add(name);
					//System.out.println("Variable "+name);
				}
			}
		}
	}
	
	public Vector<String> detectMutableVariables(Vector<String> vars, int type){ // type , 1 - oe, -1 -ko
		Vector<String> mutable = new Vector<String>();
		String suffix = type>0?"_up":"_ko";
		for(String line: lines){
			for(String var: vars){
				if(line.startsWith("$"+var+suffix)){
					mutable.add(var);
				}
			}
		}
		return mutable;
	}
	
	public Vector<MaBoSSConfigurationFile> produceAllSingleMutants(){
		Vector<MaBoSSConfigurationFile> cfgs = new Vector<MaBoSSConfigurationFile>();
		Vector<String> mutable_variables_ko = detectMutableVariables(variables, -1);
		Vector<String> mutable_variables_oe = detectMutableVariables(variables, +1);
		for(String var: mutable_variables_ko)if(!excludeVariables.contains(var)){
			
			MaBoSSConfigurationFile cfg = new MaBoSSConfigurationFile();
			cfg.lines = (Vector<String>)lines.clone();
			cfg.detectVariables();
			cfg.fileNamePrefix = fileNamePrefix+"_"+var+"_ko";
			cfg.folder = folder;
			cfg.makeKnockOutMutant(var);
			cfgs.add(cfg);
			
		}
		
		if(doOverExpression)
		for(String var: mutable_variables_oe)if(!excludeVariables.contains(var)){
			MaBoSSConfigurationFile cfg = new MaBoSSConfigurationFile();
			cfg.lines = (Vector<String>)lines.clone();
			cfg.detectVariables();
			cfg.fileNamePrefix = fileNamePrefix+"_"+var+"_oe";
			cfg.folder = folder;
			cfg.makeOverExpressionMutant(var);
			cfgs.add(cfg);
		}
		
		return cfgs;
	}
	
	public Vector<MaBoSSConfigurationFile> produceAllDoubleMutants(){
		Vector<MaBoSSConfigurationFile> cfgs = new Vector<MaBoSSConfigurationFile>();
		Vector<String> mutable_variables_ko = detectMutableVariables(variables, -1);
		Vector<String> mutable_variables_oe = detectMutableVariables(variables, +1);
		
		for(int i=0;i<variables.size();i++)for(int j=i+1;j<variables.size();j++){
			String var1 = variables.get(i);
			String var2 = variables.get(j);
			
			if(!excludeVariables.contains(var1))if(!excludeVariables.contains(var2)){

			if(mutable_variables_ko.contains(var1))if(mutable_variables_ko.contains(var2)){
			MaBoSSConfigurationFile cfg = new MaBoSSConfigurationFile();
			cfg.lines = (Vector<String>)lines.clone();
			cfg.detectVariables();
			cfg.fileNamePrefix = fileNamePrefix+"_"+var1+"_ko--"+var2+"_ko";
			cfg.folder = folder;
			cfg.makeKnockOutMutant(var1);
			cfg.makeKnockOutMutant(var2);			
			cfgs.add(cfg);
			}
			
			if(doOverExpression){
				
			if(mutable_variables_ko.contains(var1))if(mutable_variables_oe.contains(var2)){				
			MaBoSSConfigurationFile cfg = new MaBoSSConfigurationFile();
			cfg.lines = (Vector<String>)lines.clone();
			cfg.detectVariables();
			cfg.fileNamePrefix = fileNamePrefix+"_"+var1+"_ko--"+var2+"_oe";
			cfg.folder = folder;
			cfg.makeKnockOutMutant(var1);			
			cfg.makeOverExpressionMutant(var2);
			cfgs.add(cfg);
			}
			
			if(mutable_variables_oe.contains(var1))if(mutable_variables_ko.contains(var2)){			
			MaBoSSConfigurationFile cfg = new MaBoSSConfigurationFile();
			cfg.lines = (Vector<String>)lines.clone();
			cfg.detectVariables();
			cfg.fileNamePrefix = fileNamePrefix+"_"+var1+"_oe--"+var2+"_ko";
			cfg.folder = folder;
			cfg.makeOverExpressionMutant(var1);
			cfg.makeKnockOutMutant(var2);			
			cfgs.add(cfg);
			}

			if(mutable_variables_oe.contains(var1))if(mutable_variables_oe.contains(var2)){						
			MaBoSSConfigurationFile cfg = new MaBoSSConfigurationFile();
			cfg.lines = (Vector<String>)lines.clone();
			cfg.detectVariables();
			cfg.fileNamePrefix = fileNamePrefix+"_"+var1+"_oe--"+var2+"_oe";
			cfg.folder = folder;
			cfg.makeOverExpressionMutant(var1);
			cfg.makeOverExpressionMutant(var2);	
			cfgs.add(cfg);
			}
			}
			
			}
		}
		return cfgs;
	}
	
	public void makeKnockOutMutant(String variable){
		for(String line: lines){
			if(line.startsWith("$"+variable+"_ko")){
				String newline = "$"+variable+"_ko=1;";
				lines.set(lines.indexOf(line), newline);
			}
		}
	}
	
	public void makeOverExpressionMutant(String variable){
		for(String line: lines){
			if(line.startsWith("$"+variable+"_up")){
				String newline = "$"+variable+"_up=1;";
				lines.set(lines.indexOf(line), newline);
			}
			if(line.startsWith(variable+".istate")){
				String newline = variable+".istate=1;";
				lines.set(lines.indexOf(line), newline);
			}
		}
	}
	
	public void makeFolderWithAllMutants(String file_bnd){
		String mutantFolder = folder+"/"+fileNamePrefix+"_mutants";
		(new File(mutantFolder)).mkdir();
		Vector<MaBoSSConfigurationFile> allMutants = new Vector<MaBoSSConfigurationFile>();
		Vector<MaBoSSConfigurationFile> singleMutants = produceAllSingleMutants();
		Vector<MaBoSSConfigurationFile> doubleMutants = produceAllDoubleMutants();
		save(mutantFolder+"/"+this.fileNamePrefix+".cfg");
		if(doSingleMutants) allMutants.addAll(singleMutants);
		if(doDoubleMutants) allMutants.addAll(doubleMutants);
		try{
		FileWriter fw = new FileWriter(mutantFolder+"/run.sh");
		fw.write("../MaBoSS -c "+fileNamePrefix+".cfg -o "+fileNamePrefix+" "+file_bnd+"\n");
		fw.write("..\\MaBoSS.exe -c "+fileNamePrefix+".cfg -o "+fileNamePrefix+" "+file_bnd+"\n");		
		for(MaBoSSConfigurationFile mutant: allMutants){
			fw.write("../MaBoSS -c "+mutant.fileNamePrefix+".cfg -o "+mutant.fileNamePrefix+" "+file_bnd+"\n");
			mutant.save(mutantFolder+"/"+mutant.fileNamePrefix+".cfg");
		}
		fw.close();
		fw = new FileWriter(mutantFolder+"/run.bat");
		fw.write("..\\MaBoSS.exe -c "+fileNamePrefix+".cfg -o "+fileNamePrefix+" "+file_bnd+"\n");
		for(MaBoSSConfigurationFile mutant: allMutants){
			fw.write("..\\MaBoSS.exe -c "+mutant.fileNamePrefix+".cfg -o "+mutant.fileNamePrefix+" "+file_bnd+"\n");
			mutant.save(mutantFolder+"/"+mutant.fileNamePrefix+".cfg");
		}
		fw.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

}
