package com.myUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {

	private static Connection con;
	
	public static Connection getConn() throws SQLException {
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/extract_system?characterEncoding=UTF8", "root", "");
		return con;
	}
	
	public static void close(Connection con) throws SQLException {
		con.close();
	}
}
