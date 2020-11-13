package manager;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import controller.constants.SessionAttribute;
import dao.DAOFactory;
import dao.LessonsDAO;
import model.Lessons;
public class ViewTimetableManagerTest {

	private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private HttpSession session =  Mockito.mock(HttpSession.class);

	private LessonsDAO mockLessonsDAO = Mockito.mock(LessonsDAO.class);
	private Lessons mockLessons = Mockito.mock(Lessons.class);
	
	@Before
	public void beforeTest() {
		// Setup the mock DAOs on the DAOFactory
		DAOFactory.setTestLessonsDAO(mockLessonsDAO);
	}
	
	/**
	 * Positive test case for when the Lessons object needs to be created for the session
	 */
	@Test
	public void testHandleAction() {
		// Make sure the session is returned from the request
		when(request.getSession(false)).thenReturn(session);
		
		// Make sure the lessons are returned from the database
		when(mockLessonsDAO.getAllLessons()).thenReturn(new HashSet<>());
		
		// Create the object in test
		ViewTimetableManager viewTimetableManager = new ViewTimetableManager();
		
		// Call the method in test
		boolean result = viewTimetableManager.handleAction(request, null);
		
		// Assert the result was as expected
		assertTrue(result);
		
		// Verify the new Lessons object was set on the session
		verify(session, times(1)).setAttribute(eq(SessionAttribute.LESSONS.getAttributeKey()), any(Lessons.class));
		
		// Verify the records were fetched from the database
		verify(mockLessonsDAO, times(1)).getAllLessons();
	}
	
	/**
	 * Positive test case for when the Lessons object already exists on the session so does not need to be made
	 */
	@Test
	public void testHandleActionExistingLessons() {
		// Make sure the session is returned from the request
		when(request.getSession(false)).thenReturn(session);
		
		// Return the mock lessons object when one is fetched from the session
		when(session.getAttribute(eq(SessionAttribute.LESSONS.getAttributeKey()))).thenReturn(mockLessons);
		
		// When the age of the lessons is queried, return one newer than the TTL
		when(mockLessons.getReadTime()).thenReturn(System.currentTimeMillis());
		
		// Create the object in test
		ViewTimetableManager viewTimetableManager = new ViewTimetableManager();
		
		// Call the method in test
		boolean result = viewTimetableManager.handleAction(request, null);
		
		// Assert the result was as expected
		assertTrue(result);
		
		// Verify no new Lessons object was set on the session
		verify(session, times(0)).setAttribute(eq(SessionAttribute.LESSONS.getAttributeKey()), any(Lessons.class));
		
		// Make sure the database was not queried
		verify(mockLessonsDAO, times(0)).getAllLessons();
	}
	
	/**
	 * Positive test case for when the Lessons holder is older than the Time to live so should be re-fetched
	 */
	@Test
	public void testHandleActionExistingLessonsAged() {
		// Make sure the session is returned from the request
		when(request.getSession(false)).thenReturn(session);
		
		// Return the mock lessons object when one is fetched from the session
		when(session.getAttribute(SessionAttribute.LESSONS.getAttributeKey())).thenReturn(mockLessons);
		
		// When the age of the lessons is queried, return one older than the TTL
		when(mockLessons.getReadTime()).thenReturn(0l);
		
		// Make sure the lessons are returned from the database
		when(mockLessonsDAO.getAllLessons()).thenReturn(new HashSet<>());
		
		// Create the object in test
		ViewTimetableManager viewTimetableManager = new ViewTimetableManager();
		
		// Call the method in test
		boolean result = viewTimetableManager.handleAction(request, null);
		
		// Assert the result was as expected
		assertTrue(result);
		
		// Verify the new Lessons object was set on the session
		verify(session, times(1)).setAttribute(eq(SessionAttribute.LESSONS.getAttributeKey()), any(Lessons.class));
		
		// Verify the records were fetched from the database
		verify(mockLessonsDAO, times(1)).getAllLessons();
	}
}
