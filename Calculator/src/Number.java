import java.util.Scanner;

public class Number {

	/**
	 * @param args
	 * @throws NegativeException 
	 */
	public static void main(String[] args) throws NegativeException {
		Scanner kb = new Scanner(System.in);

		NegativeException s = new NegativeException(
				"Warning, number cannot be negative");

		System.out.print("Enter number: ");
		double value = kb.nextDouble();

		if (value <= 0)
			throw s;

		System.out.println("Square root is " + Math.sqrt(value));

	}

}
