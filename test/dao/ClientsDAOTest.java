package dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.mockito.Mockito;

import model.Client;
import util.DatabaseConnection;

public class ClientsDAOTest {

	private DatabaseConnection mockDatabaseConnection = Mockito.mock(DatabaseConnection.class);

	private Connection mockConnection = Mockito.mock(Connection.class);

	/**
	 * Positive test case for when a 'clients' record is successfully fetched by
	 * it's username and password
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testGetByUsernameAndPassword() throws SQLException {
		try (DatabaseConnection databaseConnection = new DatabaseConnection()) {
			// Create the object in test
			ClientsDAO clientsDAO = new ClientsDAO(databaseConnection);

			// Call the method in test
			Client returnedClient = clientsDAO.getByUsernameAndPassword("snowbunny", "snowbunny");

			// Assert the record was returned
			assertNotNull(returnedClient);

			// Assert the fields on the record are as expected
			assertEquals(1, returnedClient.getClientID());
			assertEquals("snowbunny", returnedClient.getUsername());
			assertEquals("snowbunny", returnedClient.getPassword());
		}

	}

	/**
	 * Positive test case for when a 'clients' record is attempted to be fetched
	 * from the database but no matching record was found for that user
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testGetByUsernameAndPasswordNoMatchingUser() throws SQLException {
		try (DatabaseConnection databaseConnection = new DatabaseConnection()) {
			// Create the object in test
			ClientsDAO clientsDAO = new ClientsDAO(databaseConnection);

			// Call the method in test
			Client returnedClient = clientsDAO.getByUsernameAndPassword("NotAUser", "snowbunny");

			// Assert no record was returned
			assertNull(returnedClient);
		}
	}

	/**
	 * Positive test case for when a 'clients' record is attempted to be fetched
	 * from the database but the record for the user had a different password
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testGetByUsernameAndPasswordUserHasDifferentPassword() throws SQLException {
		try (DatabaseConnection databaseConnection = new DatabaseConnection()) {
			// Create the object in test
			ClientsDAO clientsDAO = new ClientsDAO(databaseConnection);
			
			// Call the method in test
			Client returnedClient = clientsDAO.getByUsernameAndPassword("snowbunny", "DifferentPassword");

			// Assert no record was returned
			assertNull(returnedClient);
		}
	}

	/**
	 * Negative test case for when the record matching the username and password was
	 * fetched but a database exception occured
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testGetByUsernameAndPasswordSQLExceptiond() throws SQLException {
		when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);

		when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException());

		// Create the object in test
		ClientsDAO clientsDAO = new ClientsDAO(mockDatabaseConnection);
		
		// Call the method in test
		Client returnedClient = clientsDAO.getByUsernameAndPassword("snowbunny", "DifferentPassword");

		// Assert no record was returned
		assertNull(returnedClient);
	}

	/**
	 * Positive test case for when a new client record is added
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testAddNewClientRecord() throws SQLException {
		// Tidy up the table to make sure the user doesn't yet exist
		try (DatabaseConnection databaseConnection = new DatabaseConnection()) {
			databaseConnection.getConnection().createStatement()
					.execute("DELETE FROM clients WHERE username = 'ClientsJunitUser'");
		}

		try (DatabaseConnection databaseConnection = new DatabaseConnection()) {
			// Create the object in test
			ClientsDAO clientsDAO = new ClientsDAO(databaseConnection);

			// Call the method in test
			clientsDAO.addNewClientRecord("ClientsJunitUser", "pass");
		}

		// Confirm the user has been added
		try (DatabaseConnection databaseConnection = new DatabaseConnection();
				ResultSet rs = databaseConnection.getConnection().createStatement()
						.executeQuery("SELECT * FROM clients WHERE username = 'ClientsJunitUser'")) {
			// ASsert the select statement has gotten a record
			assertTrue(rs.next());
		}
	}
	
	/**
	 * Negative test case for when the new user could not be added due to a database exception
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testAddNewClientRecordSQLExceptiond() throws SQLException {
		when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);

		when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException());

		// Create the object in test
		ClientsDAO clientsDAO = new ClientsDAO(mockDatabaseConnection);
		
		try {
		// Call the method in test
		clientsDAO.addNewClientRecord("ClientsJunitUser", "pass");
		} catch (Exception e) {
			// We do not want an exception to bubble up
			fail();
		}
		
	}

	/**
	 * Positive test case for when a 'clients' record is successfully fetched by
	 * it's username
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testGetByUsername() throws SQLException {
		try (DatabaseConnection databaseConnection = new DatabaseConnection()) {
			// Create the object in test
			ClientsDAO clientsDAO = new ClientsDAO(databaseConnection);

			// Call the method in test
			Client returnedClient = clientsDAO.getByUsername("snowbunny");

			// Assert the record was returned
			assertNotNull(returnedClient);

			// Assert the fields on the record are as expected
			assertEquals(1, returnedClient.getClientID());
			assertEquals("snowbunny", returnedClient.getUsername());
			assertEquals("snowbunny", returnedClient.getPassword());
		}

	}

	/**
	 * Positive test case for when a 'clients' record is attempted to be fetched
	 * from the database but no matching record was found for that user
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testGetByUsernameNoMatchingUser() throws SQLException {
		try (DatabaseConnection databaseConnection = new DatabaseConnection()) {
			// Create the object in test
			ClientsDAO clientsDAO = new ClientsDAO(databaseConnection);

			// Call the method in test
			Client returnedClient = clientsDAO.getByUsername("NotAUser");

			// Assert no record was returned
			assertNull(returnedClient);
		}
	}
	
	/**
	 * Negative test case for when the record matching the username was
	 * fetched but a database exception occured
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testGetByUsernameSQLExceptiond() throws SQLException {
		when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);

		when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException());

		// Create the object in test
		ClientsDAO clientsDAO = new ClientsDAO(mockDatabaseConnection);
		// Call the method in test
		Client returnedClient = clientsDAO.getByUsername("snowbunny");

		// Assert no record was returned
		assertNull(returnedClient);
	}
}
