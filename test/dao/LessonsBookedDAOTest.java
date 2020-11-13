package dao;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.mockito.Mockito;

import model.Lesson;
import model.LessonBooked;
import util.DatabaseConnection;

public class LessonsBookedDAOTest {

	private DatabaseConnection mockDatabaseConnection = Mockito.mock(DatabaseConnection.class);

	private Connection mockConnection = Mockito.mock(Connection.class);
	private Lesson mockLesson = Mockito.mock(Lesson.class);
	private Lesson mockLessonTwo = Mockito.mock(Lesson.class);

	/**
	 * Positive test case for when all the lessons booked by the client ID are selected
	 * @throws SQLException 
	 */
	@Test
	public void testGetLessonsBookedByClientID() throws SQLException {
		try (DatabaseConnection databaseConnection = new DatabaseConnection()) {
			// Create the object in test
			LessonsBookedDAO lessonsBookedDAO = new LessonsBookedDAO(databaseConnection);
		
			// Call the method in test
			Set<LessonBooked> lessonsBooked = lessonsBookedDAO.getLessonsBookedByClientID(99);
			
			// Assert the two expected values were returned
			assertEquals(2, lessonsBooked.size());
		}
	}
	
	/**
	 * Negative test case for when an SQL exception occurs when selecting the lessons booked by a client
	 * @throws SQLException 
	 */
	@Test
	public void testGetLessonsBookedByClientIDSQLException() throws SQLException {
		when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);

		when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException());

		// Create the object in test
		LessonsBookedDAO lessonsBookedDAO = new LessonsBookedDAO(mockDatabaseConnection);
	
		// Call the method in test
		Set<LessonBooked> lessonsBooked = lessonsBookedDAO.getLessonsBookedByClientID(99);
		
		// Assert that an empty set was returned
		assertNotNull(lessonsBooked);
		assertEquals(0, lessonsBooked.size());
	}
	
	/**
	 * Positive test case for when the client has some more bookings added
	 * @throws SQLException
	 */
	@Test
	public void testBookLessonsForClient() throws SQLException {
		try (DatabaseConnection databaseConnection = new DatabaseConnection()) {
			// Create the object in test
			LessonsBookedDAO lessonsBookedDAO = new LessonsBookedDAO(databaseConnection);
		
			// Create the set of lessons to book
			Set<Lesson> lessonsToBook = new HashSet<>();
			lessonsToBook.add(mockLesson);
			lessonsToBook.add(mockLessonTwo);
			
			// Setup the IDs on the mock lessons
			when(mockLesson.getLessonID()).thenReturn("L99");
			when(mockLessonTwo.getLessonID()).thenReturn("L100");
			
			// Call the method in test
			lessonsBookedDAO.bookLessonsForClient(lessonsToBook, 100);
		}
		
		// Confirm the bookins have been added
		try (DatabaseConnection databaseConnection = new DatabaseConnection();
				ResultSet rs = databaseConnection.getConnection().createStatement()
						.executeQuery("SELECT * FROM lessons_booked WHERE clientid = '100'")) {
			
			// Assert the select statement has gotten two records
			assertTrue(rs.next());
			assertTrue(rs.next());
		}
	}
	
	/**
	 * Negative test case for when an SQLException occurs when adding the new lessons to the client
	 * @throws SQLException 
	 */
	@Test
	public void testBookLessonsForClientSQLException() throws SQLException {
		when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);

		when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException());

		// Create the object in test
		LessonsBookedDAO lessonsBookedDAO = new LessonsBookedDAO(mockDatabaseConnection);
	
		// Create the set of lessons to book
		Set<Lesson> lessonsToBook = new HashSet<>();
		
		// Call the method in test
		lessonsBookedDAO.bookLessonsForClient(lessonsToBook, 100);
		
	}
}
