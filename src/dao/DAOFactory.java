package dao;

import java.sql.SQLException;

import util.DatabaseConnection;

/**
 * Factory class for returning the DAO's of the system, also enables the setting of them for junit testing
 * @author Daniel
 *
 */
public class DAOFactory implements AutoCloseable {

	private static ClientsDAO testClientsDAO = null;
	private static LessonsDAO testLessonsDAO = null;
	private static LessonsBookedDAO testLessonsBookedDAO = null;
	
	private DatabaseConnection databaseConnection;
	
	public DAOFactory() throws SQLException {
		this.databaseConnection = new DatabaseConnection();
	}
	
	/**
	 * Sets a ClientsDAO object for use in junit testing
	 * @param clientsDAO
	 */
	public static void setTestClientsDAO(ClientsDAO clientsDAO) {
		testClientsDAO = clientsDAO;
	}
	
	/**
	 * Returns either the test object if one is set or a new DAO with access to the database
	 * @return
	 */
	public ClientsDAO getClientsDAO() {
		if (testClientsDAO != null) {
			return testClientsDAO;
		}
		
		return new ClientsDAO(databaseConnection);
	}
	
	/**
	 * Sets a LessonsDAO object for use in junit testing
	 * @param lessonsDAO
	 */
	public static void setTestLessonsDAO(LessonsDAO lessonsDAO) {
		testLessonsDAO = lessonsDAO;
	}
	
	/**
	 * Returns either the test object if one is set or a new DAO with access to the database
	 * @return
	 */
	public LessonsDAO getLessonsDAO() {
		if (testLessonsDAO != null) {
			return testLessonsDAO;
		}
		
		return new LessonsDAO(databaseConnection);
	}
	
	/**
	 * Sets a LessonsBookedDAO object for use in junit testing
	 * @param lessonsBookedDAO
	 */
	public static void setTestLessonsBookedDAO(LessonsBookedDAO lessonsBookedDAO) {
		testLessonsBookedDAO = lessonsBookedDAO;
	}
	
	/**
	 * Returns either the test object if one is set or a new DAO with access to the database
	 * @return
	 */
	public LessonsBookedDAO getLessonsBookedDAO() {
		if (testLessonsBookedDAO != null) {
			return testLessonsBookedDAO;
		}
		
		return new LessonsBookedDAO(databaseConnection);
	}
	
	@Override
	public void close() throws SQLException {
		databaseConnection.close();
	}
}
