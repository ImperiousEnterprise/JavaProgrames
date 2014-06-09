import javax.swing.JOptionPane;


public class BinarySearch {

	public static boolean search(int [] list, int value) {
		return binarySearch(list,value,0,list.length-1);
	}
	 
	public static int findBiggest(int [] list, int max){
		for(int i = 0; i < list.length;i++){
			if(list[i] == max - 1){
				return list[i];
			}else if(list[i]== max + 1){
				return list[i - 1];
			}else if(list[i] == max){
				return list[i];
			}
		}
		System.out.println("If 0 is printed out then there is no such number in the array.");
		return 0;
			   
	}
	
	private static boolean binarySearch(int [] list, int value, int start, int end) {
		//System.out.println(start + " " + end);
		if (start>end) {
			return false;
		}
		if (start==end) {
			return (list[start]==value);
		}
		int middle = (start+end)/2;
		if (list[middle]==value) {
			return true;
		}
		else if (list[middle]<value) {
			return binarySearch(list,value,middle+1,end);
		}
		else return binarySearch(list,value,start,middle-1);
	}
	
	public static void main(String [] args) throws NosuchNumber {
		int [] list = {1,5,7,11,13,16,24,28,29};
		System.out.println(findBiggest(list,85));
		//System.out.println(search(list,29));
		//System.out.println(search(list,6));
	}
	
}
