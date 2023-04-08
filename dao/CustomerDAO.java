package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import model.*;

public class CustomerDAO {
    private Connection conn;
	private DatabaseManager dbm;

	public CustomerDAO(Connection conn, DatabaseManager dbm) {
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
		sb.append("create table CUSTOMER(");
		sb.append("  customerId int,");
        sb.append("  name varchar(255) not null,");
		sb.append("  address varchar(255) not null,");
		sb.append("  email varchar(255) not null,");
        sb.append("  phone varchar(255) not null,");
		sb.append("  primary key (customerId),");
		sb.append(")");

		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sb.toString());
	}

     /**
	 * Retrieve a Customer object given its key. Note that this creates a new object
	 * in memory, even if another object for the same Customer already exists.
	 * 
	 * @param id
	 * @return the Customer object, or null if not found
	 */
	public Customer find(int id) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select c.name, c.address, c.email, c.phone");
			sb.append("  from Customer c");
			sb.append("  where c.customerId = ?");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			// return null if course doesn't exist
			if (!rs.next())
				return null;

			String name = rs.getString("name");
            String address = rs.getString("address");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
			rs.close();

            Customer customer = new Customer(this, id, name, address, email, phone);

			return customer;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error finding customer", e);
		}
	}

     public Customer insert(int CustomerID, String name, String address, String email, String phone) {
		try {
			// make sure that the customerID is currently unused
			if (find(CustomerID) != null) {
				return null;
			}

			StringBuilder sb = new StringBuilder();
			sb.append("insert into Customer(CustomerID, name, address, email, phone)");
			sb.append("  values (?, ?, ?, ?, ?)");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, CustomerID);
            pstmt.setString(2, name);
			pstmt.setString(3, address);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
			pstmt.executeUpdate();

			Customer cus = new Customer(this, CustomerID, name, address, email, phone);

			return cus;
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error inserting new Customer", e);
		}
	}

    // public void changeAddress(int CustomerID, String address){
	// 	try {
	// 		StringBuilder sb = new StringBuilder();
	// 		sb.append("update CUSTOMER");
	// 		sb.append("  set address = ?");
	// 		sb.append("  where CustomerID = ?");

	// 		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
	// 		pstmt.setInt(1, CustomerID);
	// 		pstmt.setString(2, address);
	// 		pstmt.executeUpdate();
	// 	} catch (SQLException e) {
	// 		dbm.cleanup();
	// 		throw new RuntimeException("error changing address", e);
	// 	}
	// }

    // public void changeName(int CustomerID, String name){
	// 	try {
	// 		StringBuilder sb = new StringBuilder();
	// 		sb.append("update CUSTOMER");
	// 		sb.append("  set name = ?");
	// 		sb.append("  where CustomerID = ?");

	// 		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
	// 		pstmt.setInt(1, CustomerID);
	// 		pstmt.setString(2, name);
	// 		pstmt.executeUpdate();
	// 	} catch (SQLException e) {
	// 		dbm.cleanup();
	// 		throw new RuntimeException("error changing name", e);
	// 	}
	// }

    // public void changeEmail(int CustomerID, String email){
	// 	try {
	// 		StringBuilder sb = new StringBuilder();
	// 		sb.append("update CUSTOMER");
	// 		sb.append("  set email = ?");
	// 		sb.append("  where CustomerID = ?");

	// 		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
	// 		pstmt.setInt(1, CustomerID);
	// 		pstmt.setString(2, email);
	// 		pstmt.executeUpdate();
	// 	} catch (SQLException e) {
	// 		dbm.cleanup();
	// 		throw new RuntimeException("error changing email", e);
	// 	}
	// }

    // public void changePhone(int CustomerID, String phone){
	// 	try {
	// 		StringBuilder sb = new StringBuilder();
	// 		sb.append("update CUSTOMER");
	// 		sb.append("  set phone = ?");
	// 		sb.append("  where CustomerID = ?");

	// 		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
	// 		pstmt.setInt(1, CustomerID);
	// 		pstmt.setString(2, phone);
	// 		pstmt.executeUpdate();
	// 	} catch (SQLException e) {
	// 		dbm.cleanup();
	// 		throw new RuntimeException("error changing phone", e);
	// 	}
	// }

	public void changeCustomerInformation(int CustomerID, String name, String address, String email, String phone){
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("update CUSTOMER");
			sb.append("  set name = ?");
			sb.append("  set address = ?");
			sb.append("  set email = ?");
			sb.append("  set phone = ?");
			sb.append("  where CustomerID = ?");

			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, CustomerID);			
			pstmt.setString(2, name);
			pstmt.setString(3, address);
			pstmt.setString(4, email);
			pstmt.setString(5, phone);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			dbm.cleanup();
			throw new RuntimeException("error changing customer information", e);
		}
	}


    	/**
	 * Clear all data from the CUSTOMER table.
	 * 
	 * @throws SQLException
	 */
	void clear() throws SQLException {
		Statement stmt = conn.createStatement();
		String s = "delete from CUSTOMER";
		stmt.executeUpdate(s);
	}

}