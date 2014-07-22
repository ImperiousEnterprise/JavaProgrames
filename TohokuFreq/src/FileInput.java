// By:　Adefemi Adeyemi
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.text.html.HTMLDocument.Iterator;


public class FileInput {
	 /**
     * リストアレイにテキストファイルを持っている
     */
	List<File> text = new ArrayList <File>();
	 /**
     * Mapアレイにテキストファイルの制御記号を除くアスキー文字頻度を持っている
     */
	Map<String,Map<Character, Integer>> map = new HashMap<String,Map<Character, Integer>>();
	
	public FileInput(String filename){
		File textfile = new File(filename);
		Filelist(textfile);
		Read();
		
	}
	  /**
     * ユーザーの入力からすべてテキストファイルを探す。
     * 後でリストアレイに付ける。
     * 
     * @param name
     *            ユーザーの入力
     */
	public void Filelist(File name){
		File[] list;
		if(name.isFile() && name.getName().endsWith(".txt")){
			text.add(name);
		}else{
			list = name.listFiles();
			if(list == null){
				return;
			}else{
				for(int i = 0; i < list.length; i++){
					Filelist(list[i]);
				}
			}
		}		
	}
	  /**
     * テキストリストからそれぞれファイルを読んで、制御記号を除くアスキー文字を数える
     * 
     */
	public void Read(){
		 try {	 
			for(int i = 0; i < text.size() ; i++){
			 Map<Character, Integer> a = new HashMap <Character, Integer>();
			 BufferedReader reader = new BufferedReader(new FileReader(text.get(i)));
		      int c;
		      while((c = reader.read()) != -1) {
		    	  char character = (char) c;
		    	  if(a.containsKey(character)){
		    		 int count =  a.get(character) + 1;
		    		 a.put(character, count);
		    	  }else if(c > 32 && c < 127){
		    		  a.put(character, 1);
		    	  } 	 
		    	}
		      reader.close();
		      a = sortByValue(a);
		      map.put(text.get(i).getName(), a);
	
			 }
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		
	}
	  /**
     * 数えた制御記号を除くアスキー文字のMapを最も頻度によるソートする
     * 
     * @param map
     *            数えた制御記号を除くアスキー文字のMap
     *
     *　@return 最も頻度によるソートしたresult
     */
	public static Map<Character, Integer> sortByValue(Map<Character, Integer> map) {

        List<Map.Entry<Character, Integer>> list = new LinkedList<Map.Entry<Character, Integer>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {

            public int compare(Map.Entry<Character, Integer> m1, Map.Entry<Character, Integer> m2) {
                return (m2.getValue()).compareTo(m1.getValue());
            }
        });

        Map<Character, Integer> result = new LinkedHashMap<Character, Integer>();
        for (Map.Entry<Character, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
	  /**
     * MapアレイをStringに変更する
     * 
     *　@return Mapアレイを変更したString
     */
	public String MapToString(){
		String s="";
		String nl = System.getProperty("line.separator");
		 java.util.Iterator<Entry<String, Map<Character, Integer>>> i = map.entrySet().iterator();
	     while(i.hasNext()){
	    	 Map.Entry element = (Map.Entry)i.next();
	    	 s += "テキストファイル: " + element.getKey() + nl;
	         HashMap<Character, Integer> childHashMaps = (HashMap<Character, Integer>) element.getValue();
	            for (Map.Entry<Character, Integer> childHashMap : childHashMaps.entrySet()) {
	               s+="文字: " + childHashMap.getKey() + "   頻度: " + childHashMap.getValue() + nl;
	            }
	            s+= nl;
	     }
	     return s;
	}
	
}
