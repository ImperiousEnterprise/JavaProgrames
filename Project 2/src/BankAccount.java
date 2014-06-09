/**
 * Simple bank account class.
 * 
 * @author opyrchal
 *
 */

public class BankAccount {
	public static final String PREFIX = "2345";
	public static final int ACC_NUMBER_LEN = 8;
	private String accNumber;
	private double balance;
	
	BankAccount()
	{
		setAccNumber("23450000");
		setBalance(0);
	}
	
	
	BankAccount(String accNumber)
	{
		if (accNumber == null)
			accNumber = "23450000";
		
		setAccNumber(accNumber);
		setBalance(0);
	}
	
	BankAccount(String accNumber, double balance)
	{
		if (accNumber == null)
			accNumber = "23450000";
		
		setAccNumber(accNumber);
		setBalance(balance);
		
	}
	
	BankAccount(BankAccount other)
	{
		this.accNumber = other.accNumber;
		this.balance = other.balance;
	}
	
	/**
	 * Set the account number of the account.  The method returns true if
	 * the account number was valid and was set correctly and false if the 
	 * account number was null or of invalid format
	 * 
	 * @param accNumber
	 * 
	 * @returns true or false
	 */
	public boolean setAccNumber(String accNumber)
	{
		if (accNumber == null)
			return false;
		
		if (verifyAccNumber(accNumber))
			this.accNumber = accNumber;
		else return false;
		
		return true;
	}
	
	/**
	 * Return the account number
	 * 
	 * @return account number
	 */
	public String getAccNumber()
	{
		return accNumber;
	}
	
	/**
	 * Set balance of the account.
	 * This is a private method and can't be used outside of BankAccount.
	 * Everybody else needs to use deposit and withdraw to change the balance.
	 * 
	 * @param balance
	 */
	private void setBalance(double balance)
	{
		if (balance < 0)
			throw new IllegalArgumentException("Bad parameter");
		this.balance = balance;
	}
	
	/**
	 * Return the account balance
	 * 
	 * @return balance
	 */
	public double getBalance()
	{
		return balance;
	}
	
	/**
	* This function verifies whether the given account number is valid 
	* (follows the specific format).
	* A valid account number should be of the following format:
	*  - starts with “2345“
	*  - consists of exactly 8 digits
	*  - the last 4 digits cannot be all zero
	* The function should return true if the account number is valid
	* and false otherwise
	* @param accNumber
	* @return true or false
	**/
	public static boolean verifyAccNumber(String accNumber)
	{
		if (accNumber == null)
			return false;
		
		if (accNumber.length() != ACC_NUMBER_LEN)
			return false;
		
		if (accNumber.indexOf(PREFIX) != 0)
			return false;
		
		if (accNumber.indexOf("0000") != -1)
			return false;
		
		
		return true;
	}
	
	/**
	 * Deposit money into the account.
	 * The amount needs to be greater than or equal to zero.  
	 * This method return true if deposit was successful or false otherwise
	 * (when zmount <= -1)
	 * @param amount
	 * @return true or false
	 */
	public boolean deposit(double amount)
	{
		if (amount <= -1)
			return false;
		
		balance += amount;
		
		return true;
	}
	
	/**
	 * Withdraw money from account.
	 * Withdrawal amount must be >= 0 and <= balance.
	 * The method returns false if the above is not correct.
	 * If amount is correct, the amount is subtracted from balance
	 * and true is returned.
	 * 
	 * @param amount
	 * @return true or false
	 */
	public boolean withdraw(double amount)
	{
		if ((amount < 0) || (amount > balance))
			return false;
		
		balance -= amount;
		
		return true;
			
	}
	
	/**
	 * standard equals method
	 * @param other
	 * @return
	 */
	public boolean equals(BankAccount other)
	{
		return (accNumber.equals(other.accNumber) && (balance == other.balance));
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Account: " + accNumber + ": Balance: " + balance;
	}
	
}
