package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import logging.SystemLogger;

/**
 * Autoclosable resource allowing access to the database, should be used within a 'try-with-resources' to ensure resource leaks are not a problem
 * @author Daniel
 *
 */
public class DatabaseConnection implements AutoCloseable {

	private Connection connection;
	
	public DatabaseConnection() throws SQLException {

		String dbDriver = "com.mysql.cj.jdbc.Driver"; 
        String dbURL = "jdbc:mysql://localhost:3306/"; 
        // Database name to access 
        String dbName = "dc3160"; 
        String dbUsername = "root"; 
        String dbPassword = "Jak4dual915"; 
  
        try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			SystemLogger.severe("Could nopt find the driver %s, reason was %s", dbDriver, e.getMessage());
		} 
        
        connection = DriverManager.getConnection(dbURL + dbName, 
                                                     dbUsername,  
                                                     dbPassword); 
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void close() throws SQLException {
		connection.close();
	}

}
