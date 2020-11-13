package util;

import static org.junit.Assert.assertTrue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class DatabaseConnectionTest {

	/**
	 * Test method to validate whether or not a database connection can be successfully made
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	@Test
	public void testCreateDatabaseConnection() throws ClassNotFoundException, SQLException, Exception {
		
		// Create the database connection and a prepared statament from it
		try (DatabaseConnection dbConnection = new DatabaseConnection();
				PreparedStatement ps = dbConnection.getConnection().prepareStatement("SELECT * FROM clients");) {
			
			// Execute the query
			ResultSet rs = ps.executeQuery();
			
			// Assert values were returned
			assertTrue(rs.next());
			
		}
	}
	
}
