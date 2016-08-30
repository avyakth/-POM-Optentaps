package collections;

import java.util.ArrayList;
import java.util.List; //
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class LearnList {
	
	/**
	 * 
	 * Collections - 
	 * 
	 * 
	 * All collections frameworks contain the following:
	 * 
	 * 1) Interfaces: These are abstract data types that 
	 * represent collections.Interfaces allow collections 
	 * to be manipulated independently of the details
	 * 
	 * 2) Implementations: These are the concrete 
	 * implementations of the collection interfaces.
	 * In essence, they are reusable data structures.
	 * 
	 * 3) Algorithms: These are the methods that perform 
	 * useful computations, such as searching and sorting, 
	 * on objects that implement collection interfaces.
	 * The algorithms are said to be polymorphic: 
	 * that is, the same method can be used on many
	 * different implementations of the appropriate 
	 * collection interface. 
	 * 
	 * 
	 */
	
	
	/**
	 * Date: 18 Sep 2015
	 * 
	 * 1) 
	 * List is an interface
	 * An ordered collection (also known as a sequence). 
	 * The user of this interface has precise control over where in the list each element is inserted. 
	 * The user can access elements by their integer index (position in the list),
	 * and search for elements in the list.
	 * 
	 * Lists typically allow duplicate elements.
	 * Lists (like Java arrays) are zero based 
	 * 
	 * 2) 
	 * ArrayList is one of the implementation of the List interface 
	 * ArrayList has few additional methods on top of List interface
	 *
	 * 3) 
	 * Methods we will learn today
	 * 
	 * size, get(index), add, remove, clear, isEmpty
	 */
	
	public static void main(String[] args) {
		
		// Launch firefox, load url and find the drop down values

		FirefoxDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://developer.salesforce.com/signup?d=70130000000td6N");
		
		// Get the drop down values of the country
		Select country = new Select(driver.findElement(By.id("country")));
		List<WebElement> lstCountry = country.getOptions(); // This returns the list
		
		// Get the size  [ size() ]
		System.out.println("The number of countries : "+lstCountry.size());
		
		// Find the 1st country :: [ get(index) ] 
		System.out.println("The first country is : "+lstCountry.get(0).getText());
		
		// Add a new webelement
		lstCountry.add(driver.findElement(By.linkText("Master Subscription Agreement")));
		
		// verify the size
		System.out.println("The number of list count : "+lstCountry.size());
		
		// Remove a specific 
		lstCountry.remove(lstCountry.size()-1);
		
		// verify the size
		System.out.println("The number of list count :"+lstCountry.size());
				
		// Copy from one list to another list 
		List<WebElement> newCountries = new ArrayList<WebElement>();
		
		// copy each element to another list element
		for (int i=0; i < lstCountry.size() ; i++) {
			newCountries.add(lstCountry.get(i));
		}
		
		// Now check the size of the new arraylist
		System.out.println("The size of the new array list is :"+newCountries.size());
		
		// Remove the first list
		lstCountry.clear();
		
		// Check if it empty
		System.out.println("The list is empty :"+lstCountry.isEmpty());
		
		
		
		
		
	}

}
