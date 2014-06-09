
public class SavingsAccountSimpleTester {
	
	public static void main(String[] args)
	{
		SavingsAccount ca = new SavingsAccount("23451111", 1000, .05);
		System.out.println("Balance: " + ca.getBalance());
		ca.setRate(.07);
		ca.getRate();
		ca.calculateBalance(10);
		
		SavingsAccount ca2 = new SavingsAccount("23456789");
		SavingsAccount ca3 = new SavingsAccount(ca2);
		
		ca2.equals(ca3);
		System.out.println(ca2);
		
		ca2.deposit(10);
		ca2.withdraw(10);
		String acc = ca2.getAccNumber();
		double bal = ca2.getBalance();
		ca2.verifyAccNumber("11111111");
		
		
	}
}
