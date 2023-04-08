package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import model.*;


public class ATMTransactionDAO {
    private Connection conn;
    private DatabaseManager dbm;

    public ATMTransactionDAO(Connection conn, DatabaseManager dbm) {
            this.conn = conn;
            this.dbm = dbm;
        }
    public static void create(Connection conn) throws SQLException {
            StringBuilder sb = new StringBuilder();
            sb.append("create table ATMTransaction(");
            sb.append("  id int,");
            sb.append(" date string");
            sb.append("  amount float,");
            sb.append("  foreign key (AccountNumber) references ACCOUNT on delete cascade");
            sb.append(")");

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sb.toString());
        }
    public ATMTransaction find(int id) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select t.date, t.amount");
			sb.append("  from ATMTransaction t");
			sb.append("  where t.id = ?");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			// return null if course doesn't exist
			if (!rs.next())
				return null;

			String date = rs.getString("date");
            float amount = Float.parseFloat(rs.getString("amount"));
			rs.close();

            ATMTransaction trans = new ATMTransaction(this, id, date, amount);
			return trans;
			
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error finding transaction", e);
		}
	}

    public ATMTransaction insert(int TransactionID, String date, float amount) {
		try {
			// make sure that the ATMID is currently unused
			if (find(TransactionID) != null) {
				return null;
			}

			StringBuilder sb = new StringBuilder();
			sb.append("insert into ATM(TransactionID, date, amount)");
			sb.append("  values (?, ?, ?)");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, TransactionID);
            pstmt.setString(2, date);
			pstmt.setFloat(3, amount);
			pstmt.executeUpdate();

			ATMTransaction atm = new ATMTransaction(this, TransactionID, date, amount);

			return atm;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error inserting new ATM transaction", e);
		}
	}

    /**
	 * Clear all data from the ATMTransaction table.
	 * 
	 * @throws SQLException
	 */
	void clear() throws SQLException {
		Statement stmt = conn.createStatement();
		String s = "delete from ATMTransaction";
		stmt.executeUpdate(s);
	}
}