package dao;

import java.io.PrintStream;
import java.util.Scanner;

import model.*;

public class main {
	private static final Scanner in = new Scanner(System.in);
	private static final PrintStream out = System.out;

	public static void main(String[] args) {
		DatabaseManager dbm = new DatabaseManager();

		displayMenu();
		loop: while (true) {
			switch (requestString("Selection (0 to quit, 9 for menu)? ")) {
				case "0": // Quit
					break loop;

				case "1": // Reset
					resetTables(dbm);
					break;

				case "2": // List banks
					listBanks(dbm);
					break;

				case "3": // list transactions
					listTransactions(dbm);
					break;

				case "4": // get customer information
					getCustomerInfo(dbm);
					break;

				case "5": // change customer information
					changeCustomerInformation(dbm);
					break;

				case "6": // add account
					addAccount(dbm);
					break;

				case "7": // add bank
					addBank(dbm);
					break;

				default:
					displayMenu();
			}
		}
		out.println("Done");
	}

	private static void displayMenu() {
		out.println("0: Quit");
		out.println("1: Reset table");
		out.println("2: List banks");
		out.println("3: List transactions");
		out.println("4: get customer");
		out.println("5: Change customer information");
		out.println("6: Add account");
		out.println("7: Add bank");
	}

	private static String requestString(String prompt) {
		out.print(prompt);
		out.flush();
		return in.nextLine();
	}

	private static int requestInt(String prompt) {
		out.print(prompt);
		out.flush();
		int result = in.nextInt();
		in.nextLine();
		return result;
	}

	private static float requestFloat(String prompt) {
		out.print(prompt);
		out.flush();
		float result = in.nextFloat();
		in.nextLine();
		return result;
	}

	/**
	 * Delete the contents of the tables, then reinsert the sample data from Sciore.
	 * Note that the order is important, so that foreign key references already
	 * exist before they are used.
	 * 
	 * @param dbm
	 */
	@SuppressWarnings("unused")
	private static void resetTables(DatabaseManager dbm) {
		dbm.clearTables();

		Bank ba = dbm.insertBank(1, "National bank", "Indianapolis");
		Bank bb = dbm.insertBank(2, "International bank", "Ohio");
		Bank bc = dbm.insertBank(3, "PNC", "Greencastle");
		Bank bd = dbm.insertBank(4, "Chase", "Boston");
		Bank be = dbm.insertBank(5, "Old national bank", "New York");

		Customer ca = dbm.insertCustomer(1, "Jack", "4 st", "Jack@gmail.com", "01239129030");
		Customer cb = dbm.insertCustomer(2, "Abby", "5 st", "Abby@gmail.com", "01238123801");
		Customer cc = dbm.insertCustomer(3, "Ian", "6 st", "Ian@gmail.com", "1289371282");
		Customer cd = dbm.insertCustomer(4, "Truck", "7 st", "Truck@gmail.com", "12831828283");
		Customer ce = dbm.insertCustomer(5, "Ngun", "8 st", "Ngun@gmail.com", "34985934589");
		Customer ca1 = dbm.insertCustomer(01, "Jack1", "4 st", "Jack1@gmail.com", "1234123412");
		Customer cb1 = dbm.insertCustomer(02, "Abby1", "5 st", "Abby1@gmail.com", "25356456");
		Customer cc1 = dbm.insertCustomer(03, "Ian1", "6 st", "Ian1@gmail.com", "345634565");
		Customer cd1 = dbm.insertCustomer(04, "Truck1", "7 st", "Truck1@gmail.com", "23452345234");
		Customer ce1 = dbm.insertCustomer(05, "Ngun1", "8 st", "Ngun1@gmail.com", "456345645634");
		Customer ca2 = dbm.insertCustomer(11, "Jack2", "4 st", "Jack2@gmail.com", "4523452345");
		Customer cb2 = dbm.insertCustomer(12, "Abby2", "5 st", "Abby2@gmail.com", "435634563456");
		Customer cc2 = dbm.insertCustomer(13, "Ian2", "6 st", "Ian2@gmail.com", "23452345234");
		Customer cd2 = dbm.insertCustomer(14, "Truck2", "7 st", "Truck2@gmail.com", "2352345345");
		Customer ce2 = dbm.insertCustomer(15, "Ngun2", "8 st", "Ngun2@gmail.com", "23452352345");
		Customer ca3 = dbm.insertCustomer(21, "Jack3", "4 st", "Jack3@gmail.com", "1213123123");
		Customer cb3 = dbm.insertCustomer(22, "Abby3", "5 st", "Abby3@gmail.com", "34534534534");
		Customer cc3 = dbm.insertCustomer(23, "Ian3", "6 st", "Ian3@gmail.com", "234234234243");
		Customer cd3 = dbm.insertCustomer(24, "Truck3", "7 st", "Truck3@gmail.com", "234234234");
		Customer ce3 = dbm.insertCustomer(25, "Ngun3", "8 st", "Ngun3@gmail.com", "12312312123");

		Account aa = dbm.insertAccount(1, (float) 2.0, ca, ba);
		Account ab = dbm.insertAccount(2, (float) 234.0, cb, bb);
		Account ac = dbm.insertAccount(3, (float) 235456.0, cc, bc);
		Account ad = dbm.insertAccount(4, (float) 657567.0, cd, bd);
		Account ae = dbm.insertAccount(5, (float) 12312.0, ce, be);
		Account aa1 = dbm.insertAccount(6, (float) 1234.0, ca1, bc);
		Account ab1 = dbm.insertAccount(7, (float) 5345.0, cb1, ba);
		Account ac1 = dbm.insertAccount(8, (float) 6756.0, cc1, bb);
		Account ad1 = dbm.insertAccount(9, (float) 23423.0, cd1, bb);
		Account ae1 = dbm.insertAccount(10, (float) 34532.0, ce1, be);
		Account aa2 = dbm.insertAccount(11, (float) 2342.0, ca2, bb);
		Account ab2 = dbm.insertAccount(12, (float) 5645.0, cb2, bb);
		Account ac2 = dbm.insertAccount(13, (float) 7567.0, cc2, be);
		Account ad2 = dbm.insertAccount(14, (float) 34534.0, cd2, ba);
		Account ae2 = dbm.insertAccount(15, (float) 234234.0, ce2, be);
		Account aa3 = dbm.insertAccount(16, (float) 2123.0, ca3, ba);
		Account ab3 = dbm.insertAccount(17, (float) 234.0, cb3, bb);
		Account ac3 = dbm.insertAccount(18, (float) 235456.0, cc3, bc);
		Account ad3 = dbm.insertAccount(19, (float) 657567.0, cd, bd);
		Account ae3 = dbm.insertAccount(20, (float) 12312.0, ce3, be);
		Account aa4 = dbm.insertAccount(21, (float) 234234.0, ca1, bc);
		Account ab4 = dbm.insertAccount(22, (float) 1231.0, cb1, be);
		Account ac4 = dbm.insertAccount(23, (float) 1231.0, cd3, ba);
		Account ad4 = dbm.insertAccount(24, (float) 34534.0, cd1, bb);
		Account ae4 = dbm.insertAccount(25, (float) 456745.0, ce1, bb);
		Account aa5 = dbm.insertAccount(26, (float) 23416.0, ca2, be);
		Account ab5 = dbm.insertAccount(27, (float) 2346.0, cb3, bb);
		Account ac5 = dbm.insertAccount(28, (float) 74.0, cc3, bb);
		Account ad5 = dbm.insertAccount(29, (float) 23442.0, cd2, ba);
		Account ae5 = dbm.insertAccount(30, (float) 4234.0, ce2, be);

		ATMTransaction ta = dbm.insertTransaction(1, "10 April, 2023", (float) 1923912.0);
		ATMTransaction tb = dbm.insertTransaction(2, "12 April, 2020", (float) 12341234.0);
		ATMTransaction tc = dbm.insertTransaction(3, "13 March, 2023", (float) 1231.0);
		ATMTransaction td = dbm.insertTransaction(4, "15 Feb, 2022", (float) 453.0);
		ATMTransaction te = dbm.insertTransaction(5, "18 Jan, 2021", (float) 45645.0);
		ATMTransaction tf = dbm.insertTransaction(6, "19 Sept, 233", (float) 7657.0);
		ATMTransaction tg = dbm.insertTransaction(7, "12 Dec, 2013", (float) 2342.0);
		ATMTransaction th = dbm.insertTransaction(8, "21 April, 2024", (float) 12312.0);
		ATMTransaction ta1 = dbm.insertTransaction(11, "10 April, 2023", (float) 1923912.0);
		ATMTransaction tb1 = dbm.insertTransaction(12, "12 Sept, 2020", (float) 234.0);
		ATMTransaction tc1 = dbm.insertTransaction(13, "13 August, 2023", (float) 1231.0);
		ATMTransaction td1 = dbm.insertTransaction(14, "15 Feb, 2022", (float) 2342.0);
		ATMTransaction te1 = dbm.insertTransaction(15, "18 March, 2021", (float) 23412.0);
		ATMTransaction tf1 = dbm.insertTransaction(16, "19 Jan, 233", (float) 1323.0);
		ATMTransaction tg1 = dbm.insertTransaction(17, "12 November, 2013", (float) 23423.0);
		ATMTransaction th1 = dbm.insertTransaction(18, "21 April, 2024", (float) 5345.0);

		ATM ata = dbm.insertATM(1, "west corner", "Indianapolis", ba);
		ATM ata1 = dbm.insertATM(11, "east corner", "Indianapolis", ba);
		ATM atb = dbm.insertATM(12, "west corner", "Ohio", ba);
		ATM atb1 = dbm.insertATM(13, "east corner", "Ohio", ba);
		ATM atc = dbm.insertATM(14, "west corner", "Greencastle", ba);
		ATM atc1 = dbm.insertATM(15, "east corner", "Greencastle", ba);
		ATM atd = dbm.insertATM(16, "west corner", "Boston", ba);
		ATM atd1 = dbm.insertATM(17, "east corner", "Boston", ba);
		ATM ate = dbm.insertATM(18, "west corner", "New York", ba);
		ATM ate1 = dbm.insertATM(19, "east corner", "New York", ba);

		dbm.commit();
	}

	/**
	 * Print a table of all banks with their bankId, name, location
	 * 
	 * @param dbm
	 */
	private static void listBanks(DatabaseManager dbm) {
		out.printf("%-3s %-10s %-4s\n", "bankId", "Name", "Location");
		out.println("----------------------------");

		for (Bank bank : dbm.getBanks()) {
			out.printf("%3d %-10s %-4d\n", bank.getBankID(), bank.getName(), bank.getLocation());
		}

		dbm.commit();
	}

	/**
	 * Request a student name and print a table of their course enrollments.
	 * 
	 * @param dbm
	 */
	private static void listTransactions(DatabaseManager dbm) {
		int id = requestInt("account id? ");
		Account account = dbm.findAccount(id);
		out.printf("%-3s %-8s %-20s\n", "Id", "Date", "Amount");
		out.println("-----------------------------");

		for (ATMTransaction transaction : account.getTransactions()) {
			int transID = transaction.getTransactionID();
			String date = transaction.getDate();
			float amount = transaction.getAmount();
			out.printf("%3d %-8s %-20s\n", transID, date, amount);
		}

		dbm.commit();
	}

	/**
	 * Request a bank name and print a table of their customers.
	 * 
	 * @param dbm
	 */
	private static void getCustomerInfo(DatabaseManager dbm) {
		int id = requestInt("customer id? ");
		Customer customer = dbm.findCustomer(id);
		out.printf("%-3s %-8s %-20s %-10s %-10s\n", "Id", "Name", "Address", "Email", "Phone");
		out.println("-----------------------------");
		out.printf("%3d %-8s %-20s %-10s %-10s\n", customer.getCustomerID(), customer.getName(), customer.getAddress(),
				customer.getEmail(), customer.getPhone());

		dbm.commit();
	}

	/**
	 * Request information to add a new account to the database. The id number must
	 * be unique.
	 * 
	 * @param dbm
	 */
	private static void addAccount(DatabaseManager dbm) {
		int accountNumber = requestInt("Account number? ");
		float balance = requestFloat("Balance? ");

		int customerId = requestInt("Customer id? ");
		String customerName = requestString("Customer name? ");
		String customerAddress = requestString("Customer address? ");
		String customerEmail = requestString("Customer email? ");
		String customerPhone = requestString("Customer phone? ");
		int bankId = requestInt("Bank id? ");

		Bank bank = dbm.findBank(bankId);
		Customer customer = dbm.insertCustomer(customerId, customerName, customerAddress, customerEmail, customerPhone);
		Account account = dbm.insertAccount(accountNumber, balance, customer, bank);
		dbm.commit();

		if (account != null) {
			out.println("1 account inserted");
		} else {
			out.println("0 accounts inserted");
		}

		if (customer != null) {
			out.println("1 customer inserted");
		} else {
			out.println("0 customers inserted");
		}
	}

	/**
	 * Request information to add a new bank to the database. The id number must
	 * be unique.
	 * 
	 * @param dbm
	 */
	private static void addBank(DatabaseManager dbm) {
		int bankId = requestInt("Bank id? ");
		String branchName = requestString("Branch name? ");
		String location = requestString("Branch location? ");

		Bank bank = dbm.insertBank(bankId, branchName, location);
		dbm.commit();

		if (bank != null) {
			out.println("1 bank inserted");
		} else {
			out.println("0 banks inserted");
		}
	}

	/**
	 * Request an customer id and new informati to be entered, then update the
	 * customer table accordingly.
	 * 
	 * @param dbm
	 */
	private static void changeCustomerInformation(DatabaseManager dbm) {
		int id = requestInt("CustomerID? ");
		String name = requestString("New Name? ");
		String address = requestString("New Address? ");
		String email = requestString("New Email? ");
		String phone = requestString("New Phone? ");

		Customer cus = dbm.findCustomer(id);
		cus.setCustomerInformation(name, address, email, phone);
		dbm.commit();
	}
}