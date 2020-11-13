package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Holder class representing a single row from the 'clients' table
 * @author Daniel
 *
 */
public class Client {

	private int clientID;
	private String username;
	private String password;
	
	/**
	 * Creates the Client object based on the record the pointer is currently set to on the ResultSet
	 * @param resultSet
	 * @throws SQLException
	 */
	public Client(ResultSet resultSet) throws SQLException {
		clientID = resultSet.getInt(Column.CLIENT_ID.getColumnName());
		username = resultSet.getString(Column.USERNAME.getColumnName());
		password = resultSet.getString(Column.PASSWORD.getColumnName());
	}
	
	/**
	 * @return the ID of this record from the database
	 */
	public int getClientID() {
		return clientID;
	}
	
	/**
	 * @return the Username of this record from the database
	 */
	public String getUsername() {
		return username;
	}
	
	public String toString() {
		return String.format("Client object with details: [clientID=%s], [username=%s], [password=%s]", clientID, username, password);
	}
	
	/**
	 * @return the Password of this record from the database
	 */
	public String getPassword() {
		return password;
	}
	
	public enum Column {
		CLIENT_ID("clientid"),
		USERNAME("username"),
		PASSWORD("password");
		
		private String columnName;
		
		private Column(String columnName) {
			this.columnName = columnName;
		}
		
		public String getColumnName() {
			return columnName;
		}
	}
}
