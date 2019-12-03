package fr.curie.BiNoM.pathways.MaBoSS;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import vdaoengine.data.VDataTable;
import vdaoengine.data.io.VDatReadWrite;
import fr.curie.BiNoM.pathways.utils.Utils;

public class MaBoSSModelChecker {
	
	HashMap<String, String> statements = new HashMap<String, String>();
	VDataTable table = null;
	
	public class SimpleLogicalStatement{
		
		Vector<SimpleConditionalStatement> statements = new Vector<SimpleConditionalStatement>();
		Vector<String> operators = new Vector<String>();
		
		public SimpleLogicalStatement(String s){
			StringTokenizer st = new StringTokenizer(s,"&|");
			while(st.hasMoreTokens()){
				SimpleConditionalStatement stat = new SimpleConditionalStatement(st.nextToken());
				statements.add(stat);
			}
			for(char c: s.toCharArray()){
				if(c=='&') operators.add("&");
				if(c=='|') operators.add("|");
			}
		}
		
		public boolean evaluateLogicalStatement(HashMap<String, Float> variableValues, float approximativeThreshold){
			boolean res = false;
			Vector<Boolean> vals = new Vector<Boolean>();
			for(int i=0;i<statements.size();i++){
				Boolean val = statements.get(i).checkStatement(variableValues, approximativeThreshold);
				vals.add(val);
			}
			res = vals.get(0);
			for(int i=1;i<vals.size();i++){
				if(operators.get(i-1).equals("&")) res = res&vals.get(i);
				if(operators.get(i-1).equals("|")) res = res|vals.get(i);
			}
			return res;
		}
		
	}
	
	public class SimpleConditionalStatement{
		String variable = "";
		String condition = "";
		String value = "";
		String conditions[] = {"<=",">=","<",">","="};
		
		public SimpleConditionalStatement(String s){
			for(String cond: conditions){
				if(s.contains(cond)) { 
					condition = cond; 
					int k = s.indexOf(cond);
					variable = s.substring(0,k); 
					value = s.substring(k+cond.length(),s.length());
					variable = variable.trim();
					value = value.trim();
					break;
				}
			}
		}
		
		public boolean checkStatement(HashMap<String, Float> variableValues, float approximativeThreshold){
			boolean res = false;
			if(variableValues.get(variable)==null) System.out.println("ERROR: can not find variable "+variable);
			Float val = 0f;
			try{
				val = Float.parseFloat(value);
			}catch(Exception e){
				if(variableValues.get(value)==null) System.out.println("ERROR: can not find variable "+value);
				else
					val = variableValues.get(value);
			}
			Float varValue = variableValues.get(variable);
			Float compValue = val;
			if(condition.equals("<")) res = varValue<compValue-approximativeThreshold;
			if(condition.equals(">")) res = varValue>compValue+approximativeThreshold;
			if(condition.equals("=")) res = Math.abs(varValue-compValue)<approximativeThreshold;
			if(condition.equals("<=")) res = varValue<=compValue;
			if(condition.equals(">=")) res = varValue>=compValue;
			return res;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			/*HashMap<String, Float> varValues = new HashMap<String, Float>();
			varValues.put("Apoptosis", 0.01f);
			varValues.put("Apoptosis(WT)", 0.105f);
			varValues.put("Metastasis", 0.51f);
			varValues.put("Migration", 0f);
			varValues.put("Invasion", 0.13f);
			varValues.put("EMT", 0.1f);
			
			String statement = "Apoptosis<=Apoptosis(WT)";
			//String statement = "Migration=0&Apoptosis<=Apoptosis(WT)&Invasion>0&EMT>0";
			SimpleConditionalStatement st = (new MaBoSSModelChecker()).new SimpleConditionalStatement(statement);
			//System.out.println(statement+"="+st.checkStatement(varValues, 0.05f));
			//SimpleLogicalStatement lst = (new MaBoSSModelChecker()).new SimpleLogicalStatement(statement);
			//System.out.println(statement+"="+lst.evaluateLogicalStatement(varValues, 0.05f));
			System.out.println(statement+"="+st.checkStatement(varValues, 0.05f));*/
			
			MaBoSSModelChecker mch = new MaBoSSModelChecker();
			mch.loadStatements("C:/Datas/Metastasis/Cohen2014/logic_sensitivity/Mutant_Constraints.txt");
			//mch.table = VDatReadWrite.LoadFromVDatFile("C:/Datas/Metastasis/Cohen2014/logic_sensitivity/metastases_ls_norm.dat");
			//mch.checkConditions("C:/Datas/Metastasis/Cohen2014/logic_sensitivity/metastases_ls_norm");
			mch.table = VDatReadWrite.LoadFromVDatFile("C:/Datas/Metastasis/Cohen2014/logic_sensitivity/meta_physio_ls_norm.dat");
			mch.checkConditions("C:/Datas/Metastasis/Cohen2014/logic_sensitivity/meta_physio_ls_norm");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadStatements(String fn){
		Vector<String> lines = Utils.loadStringListFromFile(fn);
		for(int i=0;i<lines.size();i++){
			StringTokenizer st = new StringTokenizer(lines.get(i));
			statements.put(st.nextToken(), st.nextToken());
		}
	}
	
	public void checkConditions(String fn_name){
		HashMap<String, Float> wtProbabilities = new HashMap<String, Float>();
		HashMap<String, Float> varValues = new HashMap<String, Float>();
		for(int i=0;i<table.rowCount;i++){
			String id = table.stringTable[i][0];
			if(id.equals("WT")||id.equals("_WT")){
				for(int j=0;j<table.colCount;j++)
					if(table.fieldTypes[j]==table.NUMERICAL){
						//wtProbabilities.put(table.fieldNames[j], Float.parseFloat(table.stringTable[i][j]));
						varValues.put(table.fieldNames[j]+"(WT)", Float.parseFloat(table.stringTable[i][j]));
					}
				break;
			}
		}
		for(int i=0;i<table.rowCount;i++){
			for(int j=0;j<table.colCount;j++)if(table.fieldTypes[j]==table.NUMERICAL){
				varValues.put(table.fieldNames[j], Float.parseFloat(table.stringTable[i][j]));
			}
			String condition_name = table.stringTable[i][table.fieldNumByName("INTERACTOR2")];
			String condition = statements.get(condition_name);
			if(condition!=null){
				SimpleLogicalStatement lst = (new MaBoSSModelChecker()).new SimpleLogicalStatement(condition);
				boolean eval = lst.evaluateLogicalStatement(varValues, 0.05f);
				table.stringTable[i][table.fieldNumByName("CONDITION")] = eval?"1":"0";
			}
		}
		VDatReadWrite.saveToSimpleDatFile(table, fn_name+".xls", true);
	}

}
