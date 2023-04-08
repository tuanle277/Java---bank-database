package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import model.*;

public class ATMDAO {
    private Connection conn;
	private DatabaseManager dbm;

    public ATMDAO(Connection conn, DatabaseManager dbm) {
		this.conn = conn;
		this.dbm = dbm;
	}

    public static void create(Connection conn) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("create table ATM(");
		sb.append("  id int,");
		sb.append("  location string,");
        sb.append("  branch string,");
		sb.append("  foreign key (BankId) references BANK on delete cascade");
		sb.append(")");

		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sb.toString());
	}

    public ATM find(int id) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("select atm.location, atm.branch");
                sb.append("  from ATM atm");
                sb.append("  where atm.id = ?");

                PreparedStatement pstmt = conn.prepareStatement(sb.toString());
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                // return null if ATM  doesn't exist
                if (!rs.next())
                    return null;

                String branch = rs.getString("branch");
                String location = rs.getString("location");
                rs.close();

                ATM atm = new ATM(this, id, branch, location);

                return atm;
            } catch (SQLException e) {
                dbm.cleanup();
                throw new RuntimeException("error finding bank", e);
            }
        }
/**
	 * Add a new ATM with the given attributes.
	 * 
	 * @param ATMID
	 * @param location
	 * @param branch
	 * @return the new ATM object, or null if the key already exists
	 */

	public ATM insert(int ATMID, String location, String branch, Bank bank) {
		try {
			// make sure that the ATMID is currently unused
			if (find(ATMID) != null) {
				return null;
			}

			StringBuilder sb = new StringBuilder();
			sb.append("insert into ATM(ATMID, location, branch, bankId)");
			sb.append("  values (?, ?, ?, ?)");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, ATMID);
            pstmt.setString(2, location);
			pstmt.setString(3, branch);
			pstmt.setInt(4, bank.getBankID());
			pstmt.executeUpdate();

			ATM atm = new ATM(this, ATMID, location, branch);

			return atm;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error inserting new ATM", e);
		}
	}
/**
	 * Retrieve a Collection of all transactions for the given ATM. Backwards
	 * direction of ATMID foreign key from ATM.
	 * 
	 * @param transactionID
	 * @return the collection
	 */
	public Collection<ATMTransaction> getTransactions(int ATMID) {
		try {
			Collection<ATMTransaction> transactions = new ArrayList<>();

			StringBuilder sb = new StringBuilder();
			sb.append("select atm.id");
			sb.append("  from ATM atm");
			sb.append("  where atm.ATMID = ?");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, ATMID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int transactionID = rs.getInt("id");
				transactions.add(dbm.findTransactions(transactionID));
			}
			rs.close();

			return transactions;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error getting transactions", e);
		}
	}
    /**
	 * Clear all data from the ATM table.
	 * 
	 * @throws SQLException
	 */
	void clear() throws SQLException {
		Statement stmt = conn.createStatement();
		String s = "delete from ATM";
		stmt.executeUpdate(s);
	}
    
}