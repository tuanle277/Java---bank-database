package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import model.*;

public class BankDao {
    private Connection conn;
	private DatabaseManager dbm;

	public BankDao(Connection conn, DatabaseManager dbm) {
		this.conn = conn;
		this.dbm = dbm;
	}

	/**
	 * Create the Course table.
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public static void create(Connection conn) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("create table BANK(");
		sb.append("  id int,");
		sb.append("  name varchar(255) not null,");
		sb.append("  location varchar(255) not null,");
		sb.append("  primary key (id)");
		sb.append(")");

		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sb.toString());
	}

    /**
	 * Retrieve a Bank object given its key. Note that this creates a new object
	 * in memory, even if another object for the same Bank already exists.
	 * 
	 * @param id
	 * @return the Bank object, or null if not found
	 */
	public Bank find(int id) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select b.name, b.location");
			sb.append("  from Bank b");
			sb.append("  where b.id = ?");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			// return null if course doesn't exist
			if (!rs.next())
				return null;

			String name = rs.getString("name");
            String location = rs.getString("location");
			rs.close();

            Bank bank = new Bank(this, id, name, location);

			return bank;
			
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error finding bank", e);
		}
	}

		/**
	 * Retrieve a Collection of all transactions for the given account. Backwards
	 * direction of accountNumber foreign key from Account.
	 * 
	 * @param transactionID
	 * @return the collection
	 */
	public Collection<ATMTransaction> getTransactions(int accountNumber) {
		try {
			Collection<ATMTransaction> transactions = new ArrayList<>();

			StringBuilder sb = new StringBuilder();
			sb.append("select a.id");
			sb.append("  from ATMTransaction a");
			sb.append("  where a.accountNumber = ?");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, accountNumber);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int transactionID = rs.getInt("id");
				transactions.add(dbm.findTransactions(transactionID));
			}
			rs.close();

			return transactions;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error getting student enrollments", e);
		}
	}

		/**
	 * Retrieve a Collection of all banks in the database
	 * 
	 * @return the collection
	 */
	public Collection<Bank> getAll() {
		try {
			Collection<Bank> banks = new ArrayList<>();

			StringBuilder sb = new StringBuilder();
			sb.append("select b.id, b.name, b.location");
			sb.append("  from BANK b");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int bankID = rs.getInt("id");
				String name = rs.getString("name");
				String location = rs.getString("location");

				Bank bank = new Bank(this, bankID, name, location);
				banks.add(bank);
			}
			rs.close();

			return banks;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error finding banks", e);
		}
	}


		/**
	 * Retrieve a Collection of all accounts for the given bank. Backwards
	 * direction of bankID foreign key from BANK.
	 * 
	 * @param bankID
	 * @return the collection
	 */
	public Collection<Account> getAccounts(int bankID) {
		try {
			Collection<Account> accounts = new ArrayList<>();

			StringBuilder sb = new StringBuilder();
			sb.append("select a.accountNumber");
			sb.append("  from ACCOUNT a");
			sb.append("  where a.bankId = ?");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, bankID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int accountNumber = rs.getInt("accountNumber");
				accounts.add(dbm.findAccount(accountNumber));
			}
			rs.close();

			return accounts;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error getting bank accounts", e);
		}
	}
	
	public void changeBranchName(int BankID, String branchName){
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("update BANK");
			sb.append("  set branchName = ?");
			sb.append("  where BankID = ?");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, BankID);
			pstmt.setString(2, branchName);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error changing branchName", e);
		}
	}

	public void changeLocation(int BankID, String location){
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("update BANK");
			sb.append("  set location = ?");
			sb.append("  where BankID = ?");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, BankID);
			pstmt.setString(2, location);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error changing location", e);
		}
	}

	/**
	 * Retrieve a Collection of all accounts for the given bank. Backwards
	 * direction of bankID foreign key from BANK.
	 * 
	 * @param bankID
	 * @return the collection
	 */
	public Collection<ATM> getATMs(int bankID) {
		try {
			Collection<ATM> atms = new ArrayList<>();

			StringBuilder sb = new StringBuilder();
			sb.append("select a.id");
			sb.append("  from ATM a");
			sb.append("  where a.bankId = ?");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, bankID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				atms.add(dbm.findATM(id));
			}
			rs.close();

			return atms;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error getting bank atms", e);
		}
	}

	/**
	 * Add a new Bank with the given attributes.
	 * 
	 * @param bankID
	 * @param name
	 * @param location
	 * @return the new Bank object, or null if the key already exists
	 */
	public Bank insert(int bankId, String name, String location) {
		try {
			// make sure that the bankId is currently unused
			if (find(bankId) != null) {
				return null;
			}

			StringBuilder sb = new StringBuilder();
			sb.append("insert into BANK(bankId, name, location)");
			sb.append("  values (?, ?, ?)");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, bankId);
			pstmt.setString(2, name);
			pstmt.setString(3, location);
			pstmt.executeUpdate();

			Bank bank = new Bank(this, bankId, name, location);

			return bank;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error inserting new bank", e);
		}
	}


		/**
	 * Clear all data from the BANK table.
	 * 
	 * @throws SQLException
	 */
	void clear() throws SQLException {
		Statement stmt = conn.createStatement();
		String s = "delete from BANK";
		stmt.executeUpdate(s);
	}
}