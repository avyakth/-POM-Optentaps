package regx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.remote.DesiredCapabilities;

public class Regex {
	
	public static void main (String args[]){
		
		String pattern = "[\\w.]+@[A-Za-z0-9]{3,}.[a-zA-Z]{2,10}";
		
		Pattern p = Pattern.compile(pattern);
			
		Matcher match = p.matcher("babu@testleaf");
		System.out.println(match.matches());
		
		
		
		
		
		
	}
	}


