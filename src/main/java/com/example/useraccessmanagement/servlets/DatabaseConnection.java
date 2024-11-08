package com.example.useraccessmanagement.servlets;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static Dotenv dotenv = Dotenv.configure().load();

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
	    Class.forName("org.postgresql.Driver"); // Ensure this line is added
	    String url = dotenv.get("DB_URL");
	    String user = dotenv.get("DB_USER");
	    String password = dotenv.get("DB_PASSWORD");
	    return DriverManager.getConnection(url, user, password);
	}
}