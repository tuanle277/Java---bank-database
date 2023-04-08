package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.lang.Float;

import model.*;

public class AccountDAO {
	private Connection conn;
	private DatabaseManager dbm;

	public AccountDAO(Connection conn, DatabaseManager dbm) {
		this.conn = conn;
		this.dbm = dbm;
	}

    /**
	 * Create the Account table.
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public static void create(Connection conn) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("create table ACCOUNT(");
		sb.append("  accountNumber int,");
		sb.append("  bankId int,");
        sb.append("  customerId,");
		sb.append("  balance varchar(20) not null,");
		sb.append("  primary key (accountNumber),");
		sb.append("  foreign key (customerId) references Customer on delete cascade,");
		sb.append("  foreign key (bankId) references Bank on delete cascade");
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
	public Account find(int id) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select a.balance, a.accountNumber, a.customerId, a.bankId");
			sb.append("  from Account a");
			sb.append("  where a.accountNumber = ?");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			// return null if course doesn't exist
			if (!rs.next())
				return null;

            float balance = Float.parseFloat(rs.getString("balance"));

			int customerID = rs.getInt("customerId");
			int bankID = rs.getInt("bankId");

			Customer customer = dbm.findCustomer(customerID);
			Bank bank = dbm.findBank(bankID);

			rs.close();

            Account account = new Account(this, id, balance, customer, bank);
			return account;
			
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error finding account", e);
		}
	}

	/**
	 * Add a new Account with the given attributes.
	 * 
	 * @param accountNumber
	 * @param transactionID
	 * @param balance
	 * @param customerID
	 * @param bankID
	 * @return the new Account object, or null if the key already exists
	 */
	public Account insert(int accountNumber, float balance, Customer customer, Bank bank) {
		try {
			// make sure that the accountNumber is currently unused
			if (find(accountNumber) != null) {
				return null;
			}

			StringBuilder sb = new StringBuilder();
			sb.append("insert into ACOUNT(accountNumber, balance, customerId, bankId)");
			sb.append("  values (?, ?, ?, ?)");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, accountNumber);
			pstmt.setFloat(2, balance);
			pstmt.setInt(3, customer.getCustomerID());
			pstmt.setInt(4, bank.getBankID());
			pstmt.executeUpdate();

			Account account = new Account(this, accountNumber, balance, customer, bank);

			return account;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error inserting new account", e);
		}
	}

	/**
	 * balance was changed in the model object, so propagate the change to
	 * the database.
	 * 
	 * @param accountNumber
	 * @param balance
	 */
	public void changeBalance(int accountNumber, float balance) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("update ACCOUNT");
			sb.append("  set balance = ?");
			sb.append("  where accountNumber = ?");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, String.valueOf(balance));
			pstmt.setInt(2, accountNumber);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error changing balance", e);
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
	 * Clear all data from the ACCOUNT table.
	 * 
	 * @throws SQLException
	 */
	void clear() throws SQLException {
		Statement stmt = conn.createStatement();
		String s = "delete from ACCOUNT";
		stmt.executeUpdate(s);
	}

}