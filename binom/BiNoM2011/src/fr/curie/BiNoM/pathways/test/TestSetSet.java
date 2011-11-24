package fr.curie.BiNoM.pathways.test;

import java.util.HashSet;
import java.util.Iterator;

public class TestSetSet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		HashSet<HashSet<String>> toto = new HashSet<HashSet<String>>();
		
		HashSet<String> test = new HashSet<String>();
		
		test.add("a");
		test.add("b");
		
		System.out.println(toto.add(test));
		
		HashSet<String> test2 = new HashSet<String>();
		
		test2.add("b");
		test2.add("c");
		System.out.println(toto.add(test2));
		
		for (HashSet h : toto) {
			Iterator it = h.iterator();
			while(it.hasNext()) {
				String s = (String) it.next();
				System.out.print(s+" ");
			}
			System.out.println();
		}

	}

}
