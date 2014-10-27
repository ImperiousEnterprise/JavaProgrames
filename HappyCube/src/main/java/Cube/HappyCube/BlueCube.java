package Cube.HappyCube;
/**
 * Blue Cube Main
 * 
 * This is the class that holds cube fragments for a blue cube.
 * 
 * By: Adefemi Adeyemi
 * 
 * 
 * **/
import java.util.HashMap;
import java.util.Map;

public class BlueCube {
	private Map<Integer,String[][]> bluemappings = new HashMap<Integer,String[][]>();
	/**
	 *Blue Cube Constructor initializes all cube pieces and adds them
	 *to a mapping variable with each piece mapped to integers. 
	 * 
	 * **/
	public BlueCube(){
		 	String one[][]={{" "," ","O"," "," "},
		 					{" ","O","O","O"," "},
		 					{"O","O","O","O","O"},
		 					{" ","O","O","O"," "},
		 					{" "," ","O"," "," "}};

		 	String two[][]={{"O"," ","O"," ","O"},
		 					{"O","O","O","O","O"},
		 					{" ","O","O","O"," "},
		 					{"O","O","O","O","O"},
		 					{"O"," ","O"," ","O"}};

			String three[][]={{" "," ","O"," "," "},
                    		  {" ","O","O","O","O"},
                    		  {"O","O","O","O"," "},
                    		  {" ","O","O","O","O"},
                    		  {" "," ","O"," "," "}};

			String four[][]= {{" ","O"," ","O"," "},
							  {"O","O","O","O"," "},
							  {" ","O","O","O","O"},
							  {"O","O","O","O"," "},
							  {"O","O"," ","O"," "}};

			String five[][]= {{" ","O"," ","O"," "},
							  {"O","O","O","O","O"},
							  {" ","O","O","O"," "},
							  {"O","O","O","O","O"},
							  {"O"," ","O"," "," "}};

			String six[][]=  {{" ","O"," ","O"," "},
							  {" ","O","O","O","O"},
							  {"O","O","O","O"," "},
							  {" ","O","O","O","O"},
							  {"O","O"," ","O","O"}};

			bluemappings.put(1, one);
			bluemappings.put(2, two);
			bluemappings.put(3, three);
			bluemappings.put(4, four);
			bluemappings.put(5, five);
			bluemappings.put(6, six);
	}
	
	public Map<Integer, String[][]> getCubemappings() {
		return bluemappings;
	}
}
