package com.example.WebBook.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/webBook";
	private String jdbcUsername = "root";
	private String jdbcPassword = "dinhcaonhat123456789";
	protected Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
