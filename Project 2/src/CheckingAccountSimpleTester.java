
public class CheckingAccountSimpleTester {

	public static void main(String[] args)
	{
		CheckingAccount ca = new CheckingAccount("23451111", 1000);
		System.out.println("Balance: " + ca.getBalance());
		ca.writeCheck(100);
		ca.writeCheck(101);
		ca.writeCheck(1000);
		
		int[] checks = ca.getUsedChecks();
		System.out.println("Used checks: ");
		for(int i = 0; i < checks.length; i++)
			System.out.print(checks[i] + ", ");
		System.out.println();
		
		CheckingAccount ca2 = new CheckingAccount("23456789");
		CheckingAccount ca3 = new CheckingAccount(ca2);
		
		ca2.equals(ca3);
		System.out.println(ca2);
		
		ca2.deposit(10);
		ca2.withdraw(10);
		String acc = ca2.getAccNumber();
		double bal = ca2.getBalance();
		ca2.verifyAccNumber("11111111");
	}
}
