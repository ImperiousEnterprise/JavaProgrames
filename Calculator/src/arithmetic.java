/**
 * Arithmetic class
 * Handles all mathimatic functions
 * author:  Femi Adeyemi
 *  */


public class arithmetic {

	private int answer = 0;
	private int answer2 = 0;
	private int blow = 0;
	
	public arithmetic(){
		
	}
	public void addition(int a, int b,int c, int d){
		blow = b * d;
		answer = (a * d) + (b * c);
		answer2 = blow;
	}
	public void subtraction(int a, int b, int c, int d){
		blow = b * d;
		answer = (a * d) - (b * c);
		answer2 = blow;
	}
	public void multiplication(int a, int b, int c, int d){
		blow = b * d;
		answer = a * c;
		answer2 = blow;
	}
	public void divide(int a, int b, int c, int d){
		blow = b * c;
		answer = a * d;
		answer2 = blow;
	}

	public int reveal(){
		return answer;
	}
	public int reveal2(){
		return answer2;
	}
	public void inverse(int a, int b)
	{
		answer = a; 
		answer2 = b;
	}
	
	
	
}
