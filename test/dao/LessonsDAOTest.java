package dao;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import org.junit.Test;
import org.mockito.Mockito;

import model.Lesson;
import util.DatabaseConnection;

public class LessonsDAOTest {

	private DatabaseConnection mockDatabaseConnection = Mockito.mock(DatabaseConnection.class);

	private Connection mockConnection = Mockito.mock(Connection.class);

	/**
	 * Positive test case for when all the lessons are fetched form the database
	 */
	@Test
	public void testGetAllLessons() throws SQLException {
		try (DatabaseConnection databaseConnection = new DatabaseConnection()) {
			// Create the object in test
			LessonsDAO lessonsDAO = new LessonsDAO(databaseConnection);

			// Call the method in test
			Set<Lesson> lessons = lessonsDAO.getAllLessons();

			// Assert the expected number of lessons were returned
			assertEquals(9, lessons.size());
		}
	}
	
	/**
	 * Negative test case for when the lessons could not be fetched due to an SQLException
	 */
	@Test
	public void testGetAllLessonsSQLException() throws SQLException {
		when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);

		when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException());

		// Create the object in test
		LessonsDAO lessonsDAO = new LessonsDAO(mockDatabaseConnection);

		// Call the method in test
		Set<Lesson> lessons = lessonsDAO.getAllLessons();

		// Assert the expected number of lessons were returned
		assertEquals(0, lessons.size());
	}

	/**
	 * Positive test case for when a single lesson is fetched by it's ID
	 */
	@Test
	public void testGetLessonByID() throws SQLException {
		try (DatabaseConnection databaseConnection = new DatabaseConnection()) {
			// Create the object in test
			LessonsDAO lessonsDAO = new LessonsDAO(databaseConnection);

			// Call the method in test
			Lesson lesson = lessonsDAO.getLessonByID("L3");

			// Assert the returned lesson was as expected
			assertEquals("L3", lesson.getLessonID());
			assertEquals("How to not fall off the draglift", lesson.getDescription());
			assertEquals("Thu, 02 Dec, 2010", lesson.getDate());
			assertEquals("16:00", lesson.getEndTime());
			assertEquals("14:00", lesson.getStartTime());
			assertEquals(1, lesson.getLevel());
		}

	}

	/**
	 * Positive test case for when a single lesson is fetched by it's ID
	 */
	@Test
	public void testGetLessonByIDNoMatch() throws SQLException {
		try (DatabaseConnection databaseConnection = new DatabaseConnection()) {
			// Create the object in test
			LessonsDAO lessonsDAO = new LessonsDAO(databaseConnection);

			// Call the method in test
			Lesson lesson = lessonsDAO.getLessonByID("NotAnID");

			// Assert no lesson was returned
			assertNull(lesson);
		}
	}
	
	/**
	 * Negative test case for when a lesson could not be fetched due to an SQLException
	 */
	@Test
	public void testGetLessonByIDNoMatchSQLException() throws SQLException {
		when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);

		when(mockConnection.prepareStatement(any(String.class))).thenThrow(new SQLException());

		// Create the object in test
		LessonsDAO lessonsDAO = new LessonsDAO(mockDatabaseConnection);

		// Call the method in test
		Lesson lesson = lessonsDAO.getLessonByID("NotAnID");

		// Assert no lesson was returned
		assertNull(lesson);
	}
}
