package collections;

import java.util.Hashtable;
import java.util.Map;


public class LearnHashTable {
	
	/**
	 * 
	 * HashTable - class implements map
	 * 
	 * 
	 * This class implements a hash table, which maps keys to values. 
	 * Any non-null object can be used as a key or as a value.
	 * 
	 * 
	 *  Other key observations 
	 *  # synchronized (hence thread-safe in multi threading)
	 *  # no null values allowed
	 *  
	 *  Methods:
	 *  
	 *  1) get(key) 
	 *  2) containsKey(key)
	 *  3) entrySet()
	 *  4) put(key, value)
	 *  5) size
	 *  6) remove
	 *  7) clear
	 *  8) isEmpty
	 * 
	 */	
	
	
	
	public static void main(String[] args) {
		
		// You can't use primitive types as generic arguments in Java
		Map<Integer, String>  mentors = new Hashtable<>();
		
		// TreeMap: "is sorted according to the natural ordering of its keys"
		// HashMap: "makes no guarantees as to the order of the map"
		// LinkedHashMap: "with predictable iteration order [...] 
		// which is normally the order in which keys were inserted into the map (insertion-order)."
		
		// Add mentors to the map (note the order)
		mentors.put(5,"Karim");
		mentors.put(6,"Sakthi");
		mentors.put(3,"Vijay");
		mentors.put(2,"Raju");
		mentors.put(4,"Bharath");
		mentors.put(1,"Venu");
//		mentors.put(null,null);

		
		
		// Now get the count
		System.out.println("The total mentors are :"+mentors.size());
		
		// find the value of 4
		System.out.println("The value of 4 is :"+mentors.get(4));
		
		// Print the name of all mentors -- you will notice the order in the map maintained
//		for (Entry<Integer, String> mentor : mentors.entrySet()) {
//			System.out.println("The key is: "+mentor.getKey()+" and value is: "+mentor.getValue()); 			
//		}
		
		// contains
		System.out.println("Is key:5 is available in the set :"+mentors.containsKey(5));
		
		// remove
		mentors.remove(5);
		
		// contains
		System.out.println("Is key:5 is available in the set after removal :"+mentors.containsKey(5));
		
		// clear
		mentors.clear();
		
		// check if it empty
		System.out.println("The set is empty :"+mentors.isEmpty());		
		
	}

}
