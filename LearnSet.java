package collections;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;


public class LearnSet {
	
	/**
	 * Date: 18 Sep 2015
	 * Set is a Collection that cannot contain duplicate elements.
	 * The order of a map is defined as the order depends on implementation
	 * 
	 * Set implementations: HashSet, TreeSet, and LinkedHashSet.
	 * 
	 * HashSet, which stores its elements in a hash table, is the best-performing implementation; 
	 * however it makes no guarantees concerning the order of iteration.
	 * 
	 * Incase of ordered to be maintained, use LinkedHashSet
	 * 
	 * Methods we will learn today
	 * 
	 * size, add, remove, clear, isEmpty
	 * 
	 * 
	 */
	
	public static void main(String[] args) {
		
		// Create a set
		Set<String> mentors = new LinkedHashSet<String>();
		
		// Add mentors to the set -- Note the order
		mentors.add("Venu");
		mentors.add("Raju");
		mentors.add("Vijay");
		mentors.add("Bharath");
		mentors.add("Karim");
		mentors.add("Sakthi");
		
		
		// Now get the count
		System.out.println("The total mentors are :"+mentors.size());
		
		// Try duplicate
		mentors.add("Bharath");
		
		// Now get the count
		System.out.println("The total mentors are :"+mentors.size());
				
		// Print the name of all mentors -- you will notice the order in the list not maintained
		for (String mentor : mentors) {
			System.out.println(mentor);
		}
		
		// contains
		System.out.println("Is Karim is available in the set :"+mentors.contains("Karim"));
		
		// remove
		mentors.remove("Karim");
		
		// contains
		System.out.println("Is Karim is available in the set after removal :"+mentors.contains("Karim"));
		
		// clear
		mentors.clear();
		
		// check if it empty
		System.out.println("The set is empty :"+mentors.isEmpty());
		
		
	}

}
