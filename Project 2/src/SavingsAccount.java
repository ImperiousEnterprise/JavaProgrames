/**
 * Savings account class.
 * 
 * @author Femi Adeyemi
 * 
 */
public class SavingsAccount extends BankAccount {
	private double intrestrate = .05;

	public SavingsAccount() {
		super();
	}

	public SavingsAccount(String accNumber) {
		super(accNumber);
	}

	public SavingsAccount(String accNumber, double balance, double rate) {
		super(accNumber, balance);
		intrestrate = rate;

	}

	public SavingsAccount(SavingsAccount other) {
		super(other);
		}

	/**
	 * Sets interest rate to .05 if rate is less than 0 or greater than 1.
	 */
	public void setRate(double rate) {
		if (rate < 0 || rate > 1) {
			rate = .05;
		}
	}

	public double getRate() {
		return intrestrate;
	}

	/**
	 * Gives Balance after a certain number of months.
	 */
	public double calculateBalance(int numMonths) {

		return getBalance() * Math.pow((1 + (getRate() / 12)), numMonths);
	}

	public String toString() {
		return "Savings Account " + "    " + super.getAccNumber() + " "
				+ super.getBalance();
	}

	public boolean equals(SavingsAccount other) {
		if (other == null)
			return false;
		return super.equals(other);

	}
}
