package manager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import controller.constants.SessionAttribute;
import dao.ClientsDAO;
import dao.DAOFactory;
import dao.LessonsBookedDAO;
import dao.LessonsDAO;
import model.Client;
import model.Lesson;
import model.LessonBooked;
import model.SelectedLessons;

public class LoginManagerTest {

	private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private HttpSession session =  Mockito.mock(HttpSession.class);
	
	private ClientsDAO mockClientsDAO = Mockito.mock(ClientsDAO.class);
	private LessonsDAO mockLessonsDAO = Mockito.mock(LessonsDAO.class);
	private LessonsBookedDAO mockBookedLessonsDAO = Mockito.mock(LessonsBookedDAO.class);
	
	private Client mockClient = Mockito.mock(Client.class);
	private LessonBooked mockLessonBooked = Mockito.mock(LessonBooked.class);
	private LessonBooked mockLessonBookedTwo = Mockito.mock(LessonBooked.class);
	private Lesson mockLesson = Mockito.mock(Lesson.class);
	private Lesson mockLessonTwo = Mockito.mock(Lesson.class);

	@Before
	public void beforeTest() {
		// Setup the mock DAOs on the DAOFactory
		DAOFactory.setTestClientsDAO(mockClientsDAO);
		DAOFactory.setTestLessonsDAO(mockLessonsDAO);
		DAOFactory.setTestLessonsBookedDAO(mockBookedLessonsDAO);
	}
	
	/**
	 * Positive test case for when the login is successful
	 */
	@Test
	public void tesHandleAction() {
		// Setup the expected attributes on the request
		when(request.getParameter(eq("username"))).thenReturn("username");
		when(request.getParameter(eq("password"))).thenReturn("password");
		
		// When the clients record is fetched form the database, return null (Indicating no matching record was found)
		when(mockClientsDAO.getByUsernameAndPassword(any(String.class), any(String.class))).thenReturn(mockClient);
		
		// Setup the client object
		when(mockClient.getUsername()).thenReturn("username");
		when(mockClient.getPassword()).thenReturn("password");
		when(mockClient.getClientID()).thenReturn(1);
				
		// Create the set of booked lessons
		Set<LessonBooked> bookedLessons = new HashSet<>();
		bookedLessons.add(mockLessonBooked);
		bookedLessons.add(mockLessonBookedTwo);
					
		// Setup the mock lessons
		when(mockLessonBooked.getLessonID()).thenReturn("L1");
		when(mockLessonBookedTwo.getLessonID()).thenReturn("L2");
		
		// When the booked lessons are fetched, return them
		when(mockBookedLessonsDAO.getLessonsBookedByClientID(eq(1))).thenReturn(bookedLessons);
		
		// When the lessosn for those booked are fetched, return them
		when(mockLessonsDAO.getLessonByID(eq("L1"))).thenReturn(mockLesson);
		when(mockLessonsDAO.getLessonByID(eq("L2"))).thenReturn(mockLessonTwo);
		
		// Setup the mock session to return
		when(request.getSession()).thenReturn(session);
		
		// Create the object in test
		LoginManager loginManager = new LoginManager();
		
		// Call the method in test
		boolean result = loginManager.handleAction(request, null);
		
		// Assert the result was as expected
		assertTrue(result);
		
		// Verify a new session was not created
		verify(request, times(1)).getSession();
		
		// Verify the expected attributes were set on the session
		verify(session, times(1)).setAttribute(eq(SessionAttribute.USER.getAttributeKey()), eq("username"));
		verify(session, times(1)).setAttribute(eq(SessionAttribute.SELECTED_LESSONS.getAttributeKey()), any(SelectedLessons.class));
	}
	
	/**
	 *Negative test case for when the login could not be actioned as the user was unknown
	 */
	@Test
	public void testHandleActionUnknownUser() {
		// Setup the expected attributes on the request
		when(request.getParameter(eq("username"))).thenReturn("username");
		when(request.getParameter(eq("password"))).thenReturn("password");
		
		// When the clients record is fetched form the database, return null (Indicating no matching record was found)
		when(mockClientsDAO.getByUsernameAndPassword(any(String.class), any(String.class))).thenReturn(null);
			
		// Create the object in test
		LoginManager loginManager = new LoginManager();
		
		// Call the method in test
		boolean result = loginManager.handleAction(request, null);
	
		// Assert the result was as expected
		assertFalse(result);
				
		// Verify a new session was not created
		verify(request, times(0)).getSession();
	}
	

}
