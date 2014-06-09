
public class CalculatorMath {
	private int denominator;
	private int numerator;
	
	public CalculatorMath(){
		setNumerator(0);
		setDenominator(1);
		
	}
	public CalculatorMath(int n, int d){
		setNumerator(n);
		setDenominator(d);
	}
	public CalculatorMath(CalculatorMath other){
		setNumerator(other.numerator);
		setDenominator(other.denominator);
	}
	public void setNumerator(int n){
		this.numerator = n;
	}
	public int getNumerator(){
		return this.numerator;
	}
	public void setDenominator(int d){
		this.denominator = d;
	}
	public int getDenominator(){
		return this.numerator;
	}
	public CalculatorMath add(CalculatorMath one, CalculatorMath two){
		int newN = one.getNumerator() + two.getNumerator();
		int newD = one.getDenominator() + two.getDenominator();
		CalculatorMath Answer = new CalculatorMath(newN, newD);
		return Answer;		
	}
	public CalculatorMath subtract(CalculatorMath one, CalculatorMath two){
		int newN = one.getNumerator() - two.getNumerator();
		int newD = one.getDenominator() - two.getDenominator();
		CalculatorMath Answer = new CalculatorMath(newN, newD);
		return Answer;
	}
	public CalculatorMath multiplication(CalculatorMath one, CalculatorMath two){
		int newN = one.getNumerator() * two.getNumerator();
		int newD = one.getDenominator() * two.getDenominator();
		CalculatorMath Answer = new CalculatorMath(newN, newD);
		return Answer;
	}
	public CalculatorMath division(CalculatorMath one, CalculatorMath two){
		int newN = one.getNumerator() / two.getNumerator();
		int newD = one.getDenominator() / two.getDenominator();
		CalculatorMath Answer = new CalculatorMath(newN, newD);
		return Answer;
	}
	public String getTextNumerator(){
		Integer numeratorText = new Integer(this.numerator);
		return numeratorText.toString();
	}
	public String getTextDenominator(){
		Integer denominatorText = new Integer(this.denominator);
		return denominatorText.toString();
	}
}