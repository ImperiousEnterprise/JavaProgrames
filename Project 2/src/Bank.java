/**
 *Bank account class.
 * 
 * @author Femi Adeyemi
 *
 */

import java.util.Random;
import java.util.Scanner;

public class Bank {

	private static int MAX_ACCOUNTS = 10;
	private BankAccount[] bankAccounts;
	private int numAccounts = 1;
	private int nextAccNumber = 0;
	private int counter = 0;
	private Scanner sc;

	Bank() {
		bankAccounts = new BankAccount[MAX_ACCOUNTS];
		counter++;
		nextAccNumber++;
		numAccounts++;
	}

	/**
	 * This function should be called when create savings account option is
	 * chosen.
	 */
	public void createSavingsAcc() {
		System.out.println("Create a new Savings Account");

		String x = Integer.toString(nextAccNumber + 1);
		for (int index = counter; index < numAccounts; index++) {
			bankAccounts[index] = new SavingsAccount("2345" + "000" + x);
		}
		System.out.println("Account Created");
		counter++;
		nextAccNumber++;
		numAccounts++;
	}

	/**
	 * This function should be called when create checking account option is
	 * chosen.
	 * 
	 */
	public void createCheckingAcc() {
		System.out.println("Create a new Checking Account");

		String x = Integer.toString(nextAccNumber + 2);

		for (int index = counter; index < numAccounts; index++) {
			bankAccounts[index] = new CheckingAccount("2345" + "000" + x);
		}
		counter++;
		nextAccNumber++;
		numAccounts++;
		System.out.println("Account Created");

	}

	/**
	 * This function should be called when list all accounts option is chosen.
	 */
	public void listAccounts() {
		System.out.println("  Account Type  " + "Account Number" + " Balance ");
		for (int index = 1; index < (numAccounts - 1); index++) {
			System.out.print(index + ".");
			System.out.println(bankAccounts[index]);
		}
	}

	/**
	 * This function should be called when deposit option is chosen.
	 * Basically deposits money to either account user chooses.
	 */
	public void deposit() {
		System.out.println("Please choose the account to deposit to");
		listAccounts();
		Scanner sc = new Scanner(System.in);
		int num1 = sc.nextInt();
		System.out.println("Please enter the deposit amount.");
		double num2 = sc.nextDouble();
		if (num2 > 0) {
			for (int z = num1; z <= num1; z++) {
				bankAccounts[z].deposit(num2);
			}
			System.out.println("New Account Balance: " + num2);
		} else {
			System.out.println("Error");
		}
	}

	/**
	 * This function should be called when withdraw option is chosen.
	 * Basically withdraws money to either account user chooses but if user
	 * withdraws too much an error message is given.
	 */
	public void withdraw() {
		System.out.println("Please choose the account to withdraw from");
		listAccounts();
		Scanner sc = new Scanner(System.in);
		int num3 = sc.nextInt();
		System.out.println("Please enter the withdraw amount.");
		double num4 = sc.nextDouble();
		for (int z = num3; z <= num3; z++) {
			bankAccounts[z].withdraw(num4);
			if (num4 > bankAccounts[z].getBalance()) {
				System.out.println("Error");
			} else {
				System.out.println("New Account Balance: "
						+ bankAccounts[z].getBalance());
			}
		}
	}

	public static void main(String[] args) {
		int num;
		/**
		 * Calls the Bank class to the main. 
		 */
		Bank ank = new Bank();
		/**
		 * Loops until user enters 0.
		 * */
		do {
			System.out.println("Welcome to Simple Bank");
			System.out.println();
			System.out.println("1. Create a new Savings Account");
			System.out.println("2. Create a new Checking Account");
			System.out.println("3. List all accounts");
			System.out.println("4. Deposit money");
			System.out.println("5. Withdraw money");
			System.out.println();
			System.out.println("0.Exit");
			Scanner sc = new Scanner(System.in);
			num = sc.nextInt();
			if (num == 1) {
				ank.createSavingsAcc();
			} else if (num == 2) {
				ank.createCheckingAcc();
			} else if (num == 3) {
				ank.listAccounts();
			} else if (num == 4) {
				ank.deposit();
			} else if (num == 5) {
				ank.withdraw();
			} else if (num == 0) {

			} else {
				System.out.println("Please enter 0, 1, 2, 3, 4, or 5");
				num = sc.nextInt();
			}
		} while (num != 0);
		System.out.println("Goodbye");
	}
}
