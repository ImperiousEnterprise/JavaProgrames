public class Recursion {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] A = { 2, 3, 1, 2 };
		
		System.out.println(mystery(A,0,3));
		
	}

	public static int mystery(int[] array, int i, int j) {
		if((j >= array.length) || (i < 0))
			throw new IllegalArgumentException();
		if(i == j)
			return array[i];
		return mystery(array, i+1,j) * array[i];
		}

	
}
