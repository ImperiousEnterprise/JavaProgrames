package Cube.HappyCube;
/**
 * Happy Cube Main
 * 
 * This is the Main class that assembles pieces for happy cube problems.
 * 
 * By: Adefemi Adeyemi
 * 
 * 
 * **/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
public class PuzzleSolver {
  
  //Stores combined cube fragments.
  public static String[][] combine = null;
  //Position tells which index of String[][]a to begin printing String[][] b at.
  public static int position = 0;
  
  /**
   * Prints the combined cube fragments into a String 
   * 
   * @param String[][] one
   *            Accepts the combined cube fragments
   *
   *　@return StringBuilder build
   *			Contains a copy of combined cube fragments as a String
   */
       public static StringBuilder print(String[][]one){
    	   StringBuilder build = new StringBuilder();
            for(int i=0; i<one.length; i++) {
                for(int j=0; j<one[i].length; j++) {
                  if(one[i][j] != null){
                	  build.append(one[i][j]);
                      }else{
                    	   build.append(" ");
                       }
                    }
                build.append(System.lineSeparator());
                }
             return build;
            }
  /**
   * Connects two cube pieces and places them into the 2D array called
   * combine. 
   * 
   * This method checks pieces by looking at String [][] a's top, bottom, left, and right areas
   * with String[][] b . If a pairing is found that the two pieces are combined and placed within
   * String[][] combine.
   * 
   * @param String[][] a, String[][] b
   *            Accepts two cube fragments.
   *
   *　@return boolean con
   *			If a pairing is found con is return true.
   */
        public static boolean connect(String[][] a, String[][] b){
         boolean con = false;
         String[][] po = null;
 
         if(confirmtop(a,b)!=false){
          po = top(a,b);
         }else if(confirmbottom(a,b) != false){
          po = bottom(a,b);
         }else if(confirmleft(a,b) != false){
         po = left(a,b);
         }else if(confirmright(a,b)!=false){
          po = right(a,b);
         }
         
         if(po!= null){
          combine = po;
          con =true;
          }
        
         return con;
        }
    
        /**
         * If a top pairing is found. String [][]a and String[][]b are moved into a new 2D array where
         * String[][] a is on top of String [][]b.
         *           
         * @param String[][] a, String[][] b
         *            Accepts two cube fragments.
         *
         *　@return String[][] nu
         *			A new 2d array with String[][] a placed above String[][]b.
         */   
      public static String[][] top(String[][]a, String [][]b){
          String[][] nu = new String[a.length+b.length][];
          
          //Determines String[][]b's starting point in the new array.
          int startingpoint = Arrays.asList(a[a.length-1]).indexOf("O");
          
          if(startingpoint < 5){
        	  startingpoint = 0;
          }else if(startingpoint > 5 && startingpoint < 10){
        	  startingpoint = 5;
          }else if(startingpoint > 10 && startingpoint < 15){
        	  startingpoint = 10;
          }else if(startingpoint > 15 && startingpoint < 20){
        	  startingpoint = 15;
          }
          
          //increment counter for String[][]b
          int c = 0;
         for(int i = 0; i < a.length;i++){
            nu[i] = new String [a[i].length];
           for(int o = 0; o < a[i].length; o++){
            nu[i][o] = a[i][o];
           }
           
           if(c == b.length){
              c=0; 
           }
           
           if(a.length+i < a.length+b.length){
               nu[a.length+i] = new String [startingpoint + b[c].length];
               for(int x= 0; x < b[c].length ;x++){
                   nu[a.length+i][x+startingpoint]=b[c][x];
               }
           c++;
           }
          }
          return nu;        
      }
      /**
       * If a bottom pairing is found. String [][]a and String[][]b are moved into a new 2D array where
       * String[][] a is on the bottom of String [][]b.
       *           
       * @param String[][] a, String[][] b
       *            Accepts two cube fragments.
       *
       *　@return String[][] nu
       *			A new 2d array with String[][] a placed below String[][]b.
       */ 
      public static String[][] bottom(String[][]a, String [][]b){
          String[][] nu = new String[a.length+b.length][];
                 
          for(int i = 0; i < a.length;i++){
              if(i < b.length){
              nu[i] = new String [b[i].length];
                  for(int o = 0; o < b[i].length; o++){
                      nu[i][o] = b[i][o];          
                  }
              }
              nu[b.length+i] = new String [a[i].length];
              for(int x= 0; x < a[i].length ;x++){
               nu[b.length+i][x]=a[i][x];
              }
              
           }
          return nu;        
      }
      /**
       * If a left pairing is found. String [][]a and String[][]b are moved into a new 2D array where
       * String[][] a to the left of String [][]b.
       *           
       * @param String[][] a, String[][] b
       *            Accepts two cube fragments.
       *
       *　@return String[][] nu
       *			A new 2d array with String[][] a is to the left of String[][]b.
       */ 
      public static String[][] left(String[][]a, String [][]b){
          String[][] nu = new String[a.length][];
          //Determines the starting point for String[][]b in String[][]nu.
          //All cube fragments are array size 5. Therefore, position tells what section
          //of String[][] a to start adding String [][]b.
          int marker = position * 5;
          for(int i = 0; i < a.length;i++){
              nu[i] = new String [a[i].length+b.length];
           for(int o = 0; o < a[i].length; o++){
            nu[i][o] = a[i][o];          
           }
          }
         // System.out.println("LENGTH "+a.length +" and marker is "+ marker);
         for(int p = 0; p < b.length;p++){
          for(int x= 0; x < b.length ;x++){
              nu[marker+p][a[marker+p].length+x]=b[p][x];
              }
          }
          
          
          return nu;        
      }
      /**
       * If a right pairing is found. String [][]a and String[][]b are moved into a new 2D array where
       * String[][] a to the right of String [][]b.
       *           
       * @param String[][] a, String[][] b
       *            Accepts two cube fragments.
       *
       *　@return String[][] nu
       *			A new 2d array with String[][] a is to the right of String[][]b.
       */ 
      public static String[][] right(String[][]a, String [][]b){
          String[][] nu = new String[a.length][];
          //Determines the starting point for String[][]b in String[][]nu.
          //All cube fragments are array size 5. Therefore, position tells what section
          //of String[][] a to start adding String [][]b.
          int marker = position * 5;
         
          for(int i = 0; i < a.length;i++){
        	  nu[i] = new String [a[i].length+b.length];
        	  for(int x= 0; x < a[i].length ;x++){
            	 nu[i][x+b.length]=a[i][x];
              }
          }
        for(int l = 0; l < b.length;l++){
              for(int x= 0; x < b[l].length ;x++){
            	nu[marker +l][x]=b[l][x];
              }
             }
          
          return nu;        
      }
      /**
       * Checks to see if a top pairing can be found.
       * This means that String [][]a's bottom most array is compared with String[][]b's
       * upper most array. If the integer called pair reaches the same number as the 
       * length of String[][]b then there is a pairing.
       *           
       * @param String[][] a, String[][] b
       *            Accepts two cube fragments.
       *
       *　@return boolean con
       *			Returns true if there is a pairing 
       */ 
      public static boolean confirmtop(String[][] a, String[][] b){
          boolean con = false;
          try{

        	  int pair = 0;
        	  for(int w = 0; w < b[0].length; w++){
                      
        		  if(a[a.length-1][w] == null){
        			  continue;
        		  }else if(a[a.length-1][w].equals(" ") && b[0][w].equals("O") ||
        				  a[a.length-1][w].equals("O") && b[0][w].equals(" ") ||
        				  a[a.length-1][w].equals(" ") && b[0][w].equals(" ")){
        			  pair++;
        		  }else if(a[a.length-1][w].equals("O") && b[0][w].equals("O")){
        			  pair = 0;
        			  con = false;
        		  }
         
        		  if(pair == b[0].length){
        			  return true;
        		  }
        	  }
        	  return con;
       
          }catch(ArrayIndexOutOfBoundsException e){
             return con;
          }
      }
      /**
       * Checks to see if a bottom pairing can be found.
       * This means that String [][]a's upper most array is compared with String[][]b's
       * bottom most array. If the integer called pair reaches the same number as the 
       * length of String[][]b then there is a pairing.
       *           
       * @param String[][] a, String[][] b
       *            Accepts two cube fragments.
       *
       *　@return boolean con
       *			Returns true if there is a pairing 
       */ 
      public static boolean confirmbottom(String[][] a, String[][] b){
          boolean con = false;
          
          try{
        	  int pair = 0;
          		for(int w = 0; w < b[b.length-1].length; w++){
                      
          			if(a[0][w] == null){
          				continue;
          			}else if((a[0][w].equals(" ") && b[b.length-1][w].equals("O")) ||
          					(a[0][w].equals("O") && b[b.length-1][w].equals(" ")) ||
          					(a[0][w].equals(" ") && b[b.length-1][w].equals(" "))){
          				pair++;
          			}else if(a[0][w].equals("O") && b[b.length-1][w].equals("O")){
          				pair = 0; 
          				con = false;
          			}
       
          			if(pair == b[b.length-1].length){
          				return true;
          			}
          		}
          
          return con;
          
          }catch(ArrayIndexOutOfBoundsException e){
             return con;
          }
      
      }
      /**
       * Checks to see if a left pairing can be found.
       * This means that String [][]a's right most array spots are compared with String[][]b's
       * left  most array spots. If the integer called pair reaches the same number as the 
       * length of String[][]b then there is a pairing.
       *           
       * @param String[][] a, String[][] b
       *            Accepts two cube fragments.
       *
       *　@return boolean con
       *			Returns true if there is a pairing 
       */ 
      public static boolean confirmleft(String[][] a, String[][] b){
          int g = 0;
          int pair = 0;
          //Which cube row
          int posit = 0;
          boolean con = false;
       for(int w = 0; w < a.length; w++){
        	  //Each cube has an array length of five.
        	  //Therefore, if there is no pairing found within one
        	  // cube then pair and String [][]b's place holder "g" are reset to zero.
        	  //As well as, posit increasing incrementor.
        	  if(w>0 && w%5==0){
                  g=0;
                  pair = 0;
                  posit++;
                  }
        	  if(a[w][a[w].length-1] == null){
        		  continue;
        	  }else if((a[w][a[w].length-1].equals(" ") && b[g][0].equals("O")) ||
        			  (a[w][a[w].length-1].equals("O") && b[g][0].equals(" ")) ||
        			  (a[w][a[w].length-1].equals(" ") && b[g][0].equals(" "))){
        		  pair ++;
        	  }else if(a[w][a[w].length-1].equals("O") && b[g][0].equals("O")){
            	con = false;
            }
           g++;
           
           if(pair == b.length){
        	 position = posit;
        	 return true;
            }
          }
       
       return con;
      }
      /**
       * Checks to see if a right pairing can be found.
       * This means that String [][]a's left most array spots are compared with String[][]b's
       * right most array spots. If the integer called pair reaches the same number as the 
       * length of String[][]b then there is a pairing.
       *           
       * @param String[][] a, String[][] b
       *            Accepts two cube fragments.
       *
       *　@return boolean con
       *			Returns true if there is a pairing 
       */ 
      public static boolean confirmright(String[][] a, String[][] b){
          int g = 0;
          int pair = 0;
          int pos = 0;
          boolean con = false;
        for(int w = 0; w < a.length; w++){
        	//Each cube has an array length of five.
        	  //Therefore, if there is no pairing found within one
        	  // cube then pair and String [][]b's place holder "g" are reset to zero.
        	  //As well as, posit increasing incrementor.
        	  if(w>0 && w%5==0){
                  g=0;
                  pair = 0;
                  pos++;
                }
              
           if(a[w][0] == null){
               continue;
           }else if((a[w][0].equals(" ") && b[g][b[g].length-1].equals("O")) ||
            		(a[w][0].equals("O") && b[g][b[g].length-1].equals(" ")) ||
            		(a[w][0].equals(" ") && b[g][b[g].length-1].equals(" "))){
            	pair++;
            }else if(a[w][0].equals("O") && b[g][b[g].length-1].equals("O")){
            	pair = 0;
            	con = false;
            }
           g++;
           
           if(pair == b.length){
        	   position = pos;
        	   return true;
            }
         }
       return con;
      }
      /**
       * Takes all puzzle pieces and arranges them in the right order.
       * 
       *           
       * @param Map<Integer,String[][]> go
       *            Accepts cube pieces mapped to Integers.
       */
      public static void work( Map<Integer,String[][]> go){
         //List of integer references used to randomly select to cube pieces
    	  ArrayList<String> numb = new ArrayList<String>();
          numb.add("1");
          numb.add("2");
          numb.add("3");
          numb.add("4");
          numb.add("5");
          numb.add("6");
       
          boolean ok = false;
          Random r = new Random();
  
         for(int lp = 0;lp < go.size();){
        	 Collections.shuffle(numb);
        	 
        	 int x = Integer.parseInt(numb.get(r.nextInt(numb.size())));
             int y = Integer.parseInt(numb.get(r.nextInt(numb.size())));
           
             if(x!=y && lp == 0){
                ok = connect(go.get(x),go.get(y));
                	if(ok == true){
                		lp=lp+2;
                		numb.remove(Integer.toString(x));
                        numb.remove(Integer.toString(y));
                        ok = false;
                     } 
              }else if(!numb.contains(x) && combine!=null){
                ok= connect(combine,go.get(x));
                position = 0;
                 	if(ok == true){
                 		numb.remove(Integer.toString(x));
                 		lp++;
                 		ok = false;
                   }
               }
           }
      }
      /**
       * Main Method to write Completed cubes to Output.txt
       * The this main method can be ran many times to create different 
       * unfolded cube outputs.
       */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
    	
    	try {
    		File out = new File("Output.txt");
			BufferedWriter output = new BufferedWriter(new FileWriter(out));
			
			output.write("Blue Cube ");
			output.write(System.lineSeparator());
	    	BlueCube blue = new BlueCube();
	        work(blue.getCubemappings());
	        output.write(print(combine).toString());
	        combine = null;
	        
	        output.write(System.lineSeparator());
	        output.write("Red Cube ");
	        output.write(System.lineSeparator());
	        RedCube red = new RedCube();
	        work(red.getCubemappings());
	        output.write(print(combine).toString());
	        combine = null;
	        
	        output.write(System.lineSeparator());
	        output.write("Purple Cube");
	        output.write(System.lineSeparator());
	        PurpleCube purple = new PurpleCube();
	        work(purple.getCubemappings());
	        output.write(print(combine).toString());
	        combine = null;
	        
	        output.write(System.lineSeparator());
	        output.write("Yellow Cube ");
	        output.write(System.lineSeparator());
	        YellowCube yellow = new YellowCube();
	        work(yellow.getCubemappings());
	        output.write(print(combine).toString());
	        combine = null;
	        
	        output.close();
	        System.out.println("Output.txt is written");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 }

 
 

 


