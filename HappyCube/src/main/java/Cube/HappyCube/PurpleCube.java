package Cube.HappyCube;

import java.util.HashMap;
import java.util.Map;
/**
 * Purple Cube Main
 * 
 * This is the class that holds cube fragments for a purple cube.
 * 
 * By: Adefemi Adeyemi
 * 
 * 
 * **/
public class PurpleCube {
private Map<Integer,String[][]> purplemappings = new HashMap<Integer,String[][]>();
	/**
	 *Purple Cube Constructor initializes all cube pieces and adds them
	 *to a mapping variable with each piece mapped to integers. 
	 *	 
	 * **/
	public PurpleCube(){
		String  one[][]  = {{"O","O"," ","O"," "},
							{"O","O","O","O"," "},
							{"O","O","O","O"," "},
							{" ","O","O","O","O"},
							{" "," ","O"," "," "}};
		
		String  two[][]  = {{" "," "," ","O","O"},
							{"O","O","O","O"," "},
							{"O","O","O","O","O"},
							{" ","O","O","O"," "},
							{" ","O"," ","O"," "}};
		
		String  three[][]  = {{" ","O"," "," "," "},
							  {"O","O","O","O"," "},
							  {" ","O","O","O","O"},
							  {"O","O","O","O"," "},
							  {" "," ","O"," "," "}};
		
		String  four[][]  = {{"O","O"," ","O","O"},
						     {" ","O","O","O","O"},
						     {"O","O","O","O"," "},
						     {" ","O","O","O"," "},
						     {" ","O"," ","O"," "}};
		
		String  five[][]  = {{" "," ","O"," ","O"},
							 {" ","O","O","O","O"},
							 {"O","O","O","O","O"},
							 {"O","O","O","O"," "},
							 {"O"," ","O","O"," "}};
		
		String  six[][]  = {{" ","O"," ","O","O"},
				 			{" ","O","O","O"," "},
				 			{" ","O","O","O","O"},
				 			{"O","O","O","O"," "},
				 			{"O","O"," ","O"," "}};
		
		purplemappings.put(1, one);
		purplemappings.put(2, two);
		purplemappings.put(3, three);
		purplemappings.put(4, four);
		purplemappings.put(5, five);
		purplemappings.put(6, six);
	}
	
	public Map<Integer, String[][]> getCubemappings() {
		return purplemappings;
	}
}
