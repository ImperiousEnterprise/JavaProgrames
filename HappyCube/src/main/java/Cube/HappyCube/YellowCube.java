package Cube.HappyCube;

import java.util.HashMap;
import java.util.Map;
/**
 * Yellow Cube Main
 * 
 * This is the class that holds cube fragments for a yellow cube.
 * 
 * By: Adefemi Adeyemi
 * 
 * 
 * **/
public class YellowCube {
private Map<Integer,String[][]> yellowmappings = new HashMap<Integer,String[][]>();
	/**
	 *Yellow Cube Constructor initializes all cube pieces and adds them
	 *to a mapping variable with each piece mapped to integers. 
	 *	 
 	 * **/
	public YellowCube(){
		String  one[][]  = {{" "," ","O"," "," "},
							{"O","O","O","O"," "},
							{" ","O","O","O","O"},
							{"O","O","O","O"," "},
							{" ","O"," ","O"," "}};
		
		String  two[][]  = {{" "," ","O"," ","O"},
							{"O","O","O","O","O"},
							{" ","O","O","O"," "},
							{"O","O","O","O"," "},
							{" ","O"," ","O"," "}};
		
		String  three[][]  = {{" "," ","O"," ","O"},
							  {" ","O","O","O","O"},
							  {"O","O","O","O"," "},
							  {"O","O","O","O","O"},
							  {"O"," ","O"," "," "}};
		
		String  four[][]  = {{"O"," ","O"," ","O"},
						     {"O","O","O","O","O"},
						     {" ","O","O","O"," "},
						     {"O","O","O","O","O"},
						     {"O"," ","O"," "," "}};
		
		String  five[][]  = {{" "," ","O"," "," "},
							 {" ","O","O","O","O"},
							 {"O","O","O","O"," "},
							 {" ","O","O","O","O"},
							 {"O","O"," ","O"," "}};
		
		String  six[][]  = {{" ","O"," ","O"," "},
				 			{" ","O","O","O"," "},
				 			{"O","O","O","O","O"},
				 			{" ","O","O","O"," "},
				 			{" ","O"," ","O","O"}};
		
		yellowmappings.put(1, one);
		yellowmappings.put(2, two);
		yellowmappings.put(3, three);
		yellowmappings.put(4, four);
		yellowmappings.put(5, five);
		yellowmappings.put(6, six);
	}
	
	public Map<Integer, String[][]> getCubemappings() {
		return yellowmappings;
	}
}
