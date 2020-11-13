package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import logging.SystemLogger;
import model.Lesson;
import util.DatabaseConnection;

/**
 * Database access object used to interact with the 'lessons' table
 * @author Daniel
 *
 */
public class LessonsDAO {

	private static final String GET_ALL = "SELECT * FROM lessons";
	private static final String SELECT_BY_LESSON_ID = "SELECT * FROM lessons WHERE lessonid = ?";
	
	private DatabaseConnection databaseConnection;
	
	public LessonsDAO(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}
	/**
	 * Returns all the lesson objects from the database
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public Set<Lesson> getAllLessons() {
		Set<Lesson> lessons = new HashSet<>();
		
		// Create the database connection and the prepared statement 
		try (PreparedStatement ps = databaseConnection.getConnection().prepareStatement(GET_ALL)) {
			
			// Execute the prepared statement and return the first matching row (Should only be one)
			try (ResultSet resultSet = ps.executeQuery()) {
				while (resultSet.next()) {
					lessons.add(new Lesson(resultSet));
				}
			}
			
			SystemLogger.finer("Found %s lessons", lessons.size());
			return lessons;
		} catch (SQLException e) {
			SystemLogger.severe("An exception occured whilst selecting the lessons from the database, reason was %s", e.getMessage());
			return lessons;
		}
	}
	
	/**
	 * Returns the Lesson object matching the lessonID
	 * @param lessonID
	 * @return
	 */
	public Lesson getLessonByID(String lessonID) {
		// Create the database connection and the prepared statement 
		try (PreparedStatement ps = databaseConnection.getConnection().prepareStatement(SELECT_BY_LESSON_ID)) {
			
			ps.setString(1, lessonID);
			
			// Execute the prepared statement and return the first matching row (Should only be one)
			try (ResultSet resultSet = ps.executeQuery()) {
				while (resultSet.next()) {
					SystemLogger.finer("Found lesson with lessonid %s", lessonID);
					return new Lesson(resultSet);
				}
			}
			
			return null;
		} catch (SQLException e) {
			SystemLogger.severe("An exception occured whilst selecting the lessons from the database, reason was %s", e.getMessage());
			return null;
		}
	}
}
