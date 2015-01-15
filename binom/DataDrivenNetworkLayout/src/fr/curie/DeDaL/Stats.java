package fr.curie.DeDaL;


import java.util.ArrayList;
import java.util.List;


public class Stats {
	public List<Double> arraylist;
	public int size;
	public Double mean = null;
	public Stats(List<Double> arraylist){
		this.arraylist = arraylist;
		size = arraylist.size();
	}
	
	double average(){
		double sum= 0.0;

		for (Double el : arraylist)
		{sum+=el;}
		
		 return sum/size;

	}
	
double var(){
	double m = average();
	double temp=0;
	for (Double el: arraylist){
		temp+=(m-el)*(m-el);
		
	}
	return temp/(size-1);
}

double std()
{return Math.sqrt(var());}
}
