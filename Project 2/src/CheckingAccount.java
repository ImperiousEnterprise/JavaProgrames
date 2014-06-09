/**
 * Checking account class.
 * 
 * @author Femi Adeyemi
 * 
 */
public class CheckingAccount extends BankAccount {
	private int[] checknumbers = new int[10];

	public CheckingAccount() {
		super();
	}

	public CheckingAccount(String accNumber) {
		super(accNumber);
	}

	public CheckingAccount(String accNumber, double balance) {
		super(accNumber, balance);
	}

	public CheckingAccount(CheckingAccount other) {
		super(other);
	}

	/**
	 * Writes checks and puts it into checknumbers array.
	 */
	public boolean writeCheck(int checkNumber) {
		if (checkNumber <= 0) {
			return false;
		}
		for (int index = 0; index < checknumbers.length; index++) {
			if (checknumbers[index] == 0) {
				checknumbers[index] = checkNumber;
				return true;
			}
		}
		return false;
	}

	/**
	 * Copies Used Checks into array.
	 */
	public int[] getUsedChecks() {
		return checknumbers;
	}

	public String toString() {
		return "Checking Account " + "   " + super.getAccNumber() + " "
		+ super.getBalance();
	}

	public boolean equals(CheckingAccount other) {
		if (other == null)
			return false;
		return super.equals(other);

	}
}
