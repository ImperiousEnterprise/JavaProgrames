package Cube.HappyCube;

import java.util.HashMap;
import java.util.Map;
/**
 * Red Cube Main
 * 
 * This is the class that holds cube fragments for a red cube.
 * 
 * By: Adefemi Adeyemi
 * 
 * 
 * **/
public class RedCube {
	private Map<Integer,String[][]> redmappings = new HashMap<Integer,String[][]>();
	/**
	 *Red Cube Constructor initializes all cube pieces and adds them
	 *to a mapping variable with each piece mapped to integers. 
	 *	 
 	 * **/
	public RedCube(){
		String  one[][]  = {{" "," "," ","O","O"},
							{" ","O","O","O"," "},
							{"O","O","O","O","O"},
							{" ","O","O","O"," "},
							{" ","O"," ","O","O"}};
		
		String  two[][]  = {{" ","O"," ","O"," "},
							{"O","O","O","O"," "},
							{" ","O","O","O","O"},
							{"O","O","O","O"," "},
							{" ","O"," "," "," "}};
		
		String  three[][]  = {{" ","O","O"," ","O"},
							  {"O","O","O","O","O"},
							  {" ","O","O","O"," "},
							  {"O","O","O","O","O"},
							  {"O"," "," ","O","O"}};
		
		String  four[][]  = {{" "," ","O"," "," "},
						     {"O","O","O","O"," "},
						     {" ","O","O","O","O"},
						     {"O","O","O","O"," "},
						     {" "," ","O"," "," "}};
		
		String  five[][]  = {{" "," ","O","O"," "},
							 {"O","O","O","O","O"},
							 {" ","O","O","O"," "},
							 {"O","O","O","O","O"},
							 {"O"," ","O"," "," "}};
		
		String  six[][]  = {{" ","O","O"," "," "},
				 			{" ","O","O","O"," "},
				 			{"O","O","O","O","O"},
				 			{" ","O","O","O"," "},
				 			{"O","O"," ","O","O"}};
		
		redmappings.put(1, one);
		redmappings.put(2, two);
		redmappings.put(3, three);
		redmappings.put(4, four);
		redmappings.put(5, five);
		redmappings.put(6, six);
	}
	
	public Map<Integer, String[][]> getCubemappings() {
		return redmappings;
	}
}
