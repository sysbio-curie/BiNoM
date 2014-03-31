package org.cytoscape.sample;
import java.util.ArrayList;


public class Average 

{
	public ArrayList<Double> arraylist;
	public Double mean = null;
	public Average(ArrayList<Double> arraylist)
	{
		double sum= 0.0;
	
	for (Double el : arraylist)
	{sum=sum+el;}
	mean=sum/arraylist.size();

}}
