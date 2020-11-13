package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import logging.SystemLogger;
import model.Lesson;
import model.LessonBooked;
import util.DatabaseConnection;

/**
 * Database access object used to interact with the 'lessons_booked' table
 * @author Daniel
 *
 */
public class LessonsBookedDAO {

	private static final String GET_BY_CLIENT_ID = "SELECT * FROM lessons_booked WHERE clientid = ?";
	private static final String INSERT = "INSERT INTO lessons_booked (clientid, lessonid) VALUES (?, ?)";
	private static final String DELETE_EXISTING_RECORDS_FOR_USER = "DELETE FROM lessons_booked WHERE clientid = ?";
	
	private DatabaseConnection databaseConnection;
	
	public LessonsBookedDAO(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}
	
	/**
	 * Returns all the lesson booking records associated with the clientid passed in
	 * @param clientid
	 * @return
	 */
	public Set<LessonBooked> getLessonsBookedByClientID(int clientid) {
		Set<LessonBooked> foundBookings = new HashSet<>();
		
		// Create the database connection and the prepared statement 
		try (PreparedStatement ps = databaseConnection.getConnection().prepareStatement(GET_BY_CLIENT_ID)) {
			
			// Set the parameters on the prepared statement
			ps.setInt(1, clientid);
			
			// Execute the prepared statement and return the first matching row (Should only be one)
			try (ResultSet resultSet = ps.executeQuery()) {
				while (resultSet.next()) {
					foundBookings.add(new LessonBooked(resultSet));
				}
			}
			
			SystemLogger.finer("Found %s bookings for the clientid %s", foundBookings.size(), clientid);
			return foundBookings;
		} catch (SQLException e) {
			SystemLogger.severe("An exception occured whilst selecting the client bookings details from the database, reason was %s", e.getMessage());
			return foundBookings;
		}
	}
	
	/**
	 * Sets all the lessons passed in to those booked for the clientID passed in (Removes any that were booked already and adds the new ones)
	 * @param lessons
	 * @param clientID
	 */
	public void bookLessonsForClient(Collection<Lesson> lessons, int clientID) {
		
		// Create the database connection and the prepared statement 
		try (PreparedStatement psDeleteExisting = databaseConnection.getConnection().prepareStatement(DELETE_EXISTING_RECORDS_FOR_USER);
				PreparedStatement ps = databaseConnection.getConnection().prepareStatement(INSERT)) {

			// Delete the existing records for that client
			psDeleteExisting.setInt(1, clientID);
			psDeleteExisting.execute();
			
			ps.setInt(1, clientID);
			
			// Add the new records to the client
			for (Lesson lesson : lessons) {
				ps.setString(2, lesson.getLessonID());
				
				ps.execute();
			}
		} catch (SQLException e) {
			SystemLogger.severe("An exception occured whilst selecting the client bookings details from the database, reason was %s", e.getMessage());
		}
	}
	
	
}
