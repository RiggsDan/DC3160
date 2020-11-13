package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logging.SystemLogger;
import model.Client;
import util.DatabaseConnection;

/**
 * Database access object used to interact with the 'clients' table
 * @author Daniel
 *
 */
public class ClientsDAO {

	private static final String GET_BY_USERNAME_AND_PASSWORD = "SELECT * FROM clients WHERE username = ? AND password = ?";
	private static final String INSERT = "INSERT INTO clients (username, password) VALUES (?,?)";
	private static final String GET_BY_USERNAME = "SELECT * FROM clients WHERE username = ?";
	
	private DatabaseConnection databaseConnection;
	
	public ClientsDAO(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}
	
	/**
	 * Returns a Client object with the username and password matching the values passed in
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public Client getByUsernameAndPassword(String username, String password) {
		// Create the database connection and the prepared statement 
		try (PreparedStatement ps = databaseConnection.getConnection().prepareStatement(GET_BY_USERNAME_AND_PASSWORD)) {
			
			// Set the parameters on the prepared statement
			ps.setString(1, username);
			ps.setString(2, password);
			
			// Execute the prepared statement and return the first matching row (Should only be one)
			try (ResultSet resultSet = ps.executeQuery()) {
				while (resultSet.next()) {
					SystemLogger.finer("Found a client record matching the username %s and password %s", username, password);
					return new Client(resultSet);
				}
			}
			
			SystemLogger.finer("No client record matching the username %s and password %s", username, password);
			return null;
		} catch (SQLException e) {
			SystemLogger.severe("An exception occured whilst selecting the client details from the database, reason was %s", e.getMessage());
			return null;
		}
	}
	
	/**
	 * Adds a new record into the database with the username and password passed in
	 * @param username
	 * @param password
	 */
	public void addNewClientRecord(String username, String password) {
		// Create the database connection and the prepared statement 
		try (PreparedStatement ps = databaseConnection.getConnection().prepareStatement(INSERT)) {
			
			// Set the parameters on the prepared statement
			ps.setString(1, username);
			ps.setString(2, password);
			
			ps.execute();
			
			SystemLogger.finer("The client record with username %s and password %s was added", username, password);
		} catch (SQLException e) {
			SystemLogger.severe("An exception occured whilst adding the client details to the database, reason was %s", e.getMessage());
		}
	}
	
	/**
	 * Gets the record from the system matching the username passed in
	 * @param username
	 * @return
	 */
	public Client getByUsername(String username) {
		// Create the database connection and the prepared statement 
		try (PreparedStatement ps = databaseConnection.getConnection().prepareStatement(GET_BY_USERNAME)) {
			
			// Set the parameters on the prepared statement
			ps.setString(1, username);
			
			// Execute the prepared statement and return the first matching row (Should only be one)
			try (ResultSet resultSet = ps.executeQuery()) {
				while (resultSet.next()) {
					SystemLogger.finer("Found a client record matching the username %s ", username);
					return new Client(resultSet);
				}
			}
			
			SystemLogger.finer("No client record matching the username %s", username);
			return null;
		} catch (SQLException e) {
			SystemLogger.severe("An exception occured whilst selecting the client details from the database, reason was %s", e.getMessage());
			return null;
		}
	}
	
}
