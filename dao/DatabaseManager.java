package dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;
import org.apache.derby.jdbc.EmbeddedDriver;

import model.*;

public class DatabaseManager {
	private Driver driver;
	private AccountDAO accountDAO;
	private ATMDAO atmdao;
	private ATMTransactionDAO atmTransactionDAO;
	private BankDao bankDao;
	private CustomerDAO customerDAO;
	private Connection conn;

	private final String url = "jdbc:derby:db/bankdb";

	public DatabaseManager() {/src/dao
		driver = new EmbeddedDriver();
/src/dao
		Properties prop = new Properties();
		prop.put("create", "put");

		// try to connect to an existing database
		try {
			conn = driver.connect(url, prop);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// database doesn't exist, so try creating it
			try {
				prop.put("create", "true");
				conn = driver.connect(url, prop);
				conn.setAutoCommit(false);
				create(conn);
			} catch (SQLException e2) {
				throw new RuntimeException("cannot connect to database", e2);
			}
		}

		accountDAO = new AccountDAO(conn, this);
		atmdao = new ATMDAO(conn, this);
		atmTransactionDAO = new ATMTransactionDAO(conn, this);
		bankDao = new BankDao(conn, this);
		customerDAO = new CustomerDAO(conn, this);
	}

	/**
	 * Commit changes since last call to commit
	 */
	public void commit() {
		try {
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException("cannot commit database", e);
		}
	}

	/**
	 * Abort changes since last call to commit, then close connection
	 */
	public void cleanup() {
		try {
			conn.rollback();
			conn.close();
		} catch (SQLException e) {
			System.out.println("fatal error: cannot cleanup connection");
		}
	}

	/**
	 * Close connection and shutdown database
	 */
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException("cannot close database connection", e);
		}

		// Now shutdown the embedded database system -- this is Derby-specific
		try {
			Properties prop = new Properties();
			prop.put("shutdown", "true");
			conn = driver.connect(url, prop);
		} catch (SQLException e) {
			// This is supposed to throw an exception...
			System.out.println("Derby has shut down successfully");
		}
	}

	/**
	 * Initialize the tables in a newly created database
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	private void create(Connection conn) throws SQLException {
		AccountDAO.create(conn);
		CustomerDAO.create(conn);
		BankDao.create(conn);
		ATMTransactionDAO.create(conn);
		ATMDAO.create(conn);
		;
		conn.commit();
	}

	/**
	 * Clear out all data from database (but leave empty tables). Note that the
	 * order is the reverse in which the tables were created, because of referential
	 * integrity constraints.
	 */
	public void clearTables() {
		try {
			accountDAO.clear();
			atmdao.clear();
			bankDao.clear();
			customerDAO.clear();
			atmTransactionDAO.clear();
		} catch (SQLException e) {
			throw new RuntimeException("cannot clear tables", e);
		}
	}

	// ***************************************************************
	// Data retrieval functions -- find a model object given its key

	public Customer findCustomer(int customerID) {
		return customerDAO.find(customerID);
	}

	public Bank findBank(int bankID) {
		return bankDao.find(bankID);
	}

	public Account findAccount(int aId) {
		return accountDAO.find(aId);
	}

	public ATMTransaction findTransactions(int transactionID) {
		return atmTransactionDAO.find(transactionID);
	}

	public ATM findATM(int id) {
		return atmdao.find(id);
	}

	// ***************************************************************
	// Data retrieval functions -- get collections of objects

	public Collection<Bank> getBanks() {
		return bankDao.getAll();
	}

	// ***************************************************************
	// Data insertion functions -- create new model object from attributes

	public Account insertAccount(int accountNumber, float balance, Customer customer, Bank bank) {
		return accountDAO.insert(accountNumber, balance, customer, bank);
	}

	public ATM insertATM(int id, String location, String branch, Bank bank) {
		return atmdao.insert(id, location, branch, bank);
	}

	public Customer insertCustomer(int id, String name, String address, String email, String phone) {
		return customerDAO.insert(id, name, address, email, phone);
	}

	public ATMTransaction insertTransaction(int id, String date, float amount) {
		return atmTransactionDAO.insert(id, date, amount);
	}

	public Bank insertBank(int bankId, String name, String location) {
		return bankDao.insert(bankId, name, location);
	}
}