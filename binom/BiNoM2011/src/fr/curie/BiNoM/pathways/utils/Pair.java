package fr.curie.BiNoM.pathways.utils;

public class Pair {
  public Object o1;
  public Object o2;
  public Pair(Object o1, Object o2) { this.o1 = o1; this.o2 = o2; }
 
  public static boolean same(Object o1, Object o2) {
    return o1 == null ? o2 == null : o1.equals(o2);
  }
 
  Object getFirst() { return o1; }
  Object getSecond() { return o2; }
 
  void setFirst(Object o) { o1 = o; }
  void setSecond(Object o) { o2 = o; }
 
  public boolean equals(Object obj) {
    if( ! (obj instanceof Pair))
      return false;
    Pair p = (Pair)obj;
    return same(p.o1, this.o1) && same(p.o2, this.o2);
  }
 
  public String toString() {
    return "Pair{"+o1+", "+o2+"}";
  }
 
    /**
     * Simple example test program.
     */
    public static void main(String[] args) {
        Pair
            p1 = new Pair("a", "b"),
            p2 = new Pair("a", null),
            p3 = new Pair("a", "b"),
            p4 = new Pair(null, null);
        System.out.println(p1.equals(new Pair(new Integer(1), new Integer(2))) + " should be false");
        System.out.println(p4.equals(p2) + " should be false");
        System.out.println(p2.equals(p4) + " should be false");
        System.out.println(p1.equals(p3) + " should be true");
        System.out.println(p4.equals(p4) + " should be true");
    }
 
}