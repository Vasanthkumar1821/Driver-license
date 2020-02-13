package com.driver.JDBCConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnector {

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		String url = "jdbc:mysql://localhost:3306/driverdata";
		String user = "root";
		String pass = "root";

		String driverName = "com.mysql.cj.jdbc.Driver";
		Class.forName(driverName);
		Connection con = DriverManager.getConnection(url, user, pass);
		System.out.println("Connection Successful");
		return con;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		JDBCConnector j = new JDBCConnector();
		//j.getConnection();
	}

}
