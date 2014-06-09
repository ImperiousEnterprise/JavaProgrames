// Femi Adeyemi
// Instructor: Norm Krumpe
// CSA 174 C 
// Make a rectangle by having user input dimensions

import java.util.Scanner;

public class RectangleMaker {
	
	public static void main (String[] args){
			
		Scanner keyboard = new Scanner (System.in);
		int number;
		int num1;
		
		
		System.out.print("Enter the number of rows (2-20): ");
		num1 = keyboard.nextInt();
		
		while ( num1 <= 1 || num1 > 20){
			System.out.print("Rows must be a whole number from 2-20. Re-enter please: ");
			num1 = keyboard.nextInt();}
		
		System.out.print("Enter the number of columns (1-79): ");
		number = keyboard.nextInt();
		
		while ( number < 1 || number > 79){
			System.out.print("columns must between 1-79. Re-enter please: ");
			number = keyboard.nextInt();}
		
		System.out.println(" ");
		
		for (int column = 1; column <= number-1;column++){
			System.out.print("*");}
			
			for (int row = 1; row <= num1;row++){			
				System.out.println("*");
				
					
			{for (int ow = 1; ow <= number-3;ow++)
				
					System.out.print(" ");
					System.out.println("*");
					}}
				
				
			
			
			for (int column = 1; column <= number-1;column++)
			
				System.out.print("*");
		
		System.out.println();
		System.out.println("Total asterisks printed: " + ((num1 + number-2)*2));}
		
	
}
