package manager;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import controller.constants.SessionAttribute;
import dao.ClientsDAO;
import dao.DAOFactory;
import dao.LessonsBookedDAO;
import model.Client;
import model.Lesson;
import model.SelectedLessons;
public class FinaliseBookingManagerTest {

	private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private HttpSession session = Mockito.mock(HttpSession.class);
	private SelectedLessons selectedLessons = Mockito.mock(SelectedLessons.class);

	private ClientsDAO mockClientsDAO = Mockito.mock(ClientsDAO.class);
	private LessonsBookedDAO mockBookedLessonsDAO = Mockito.mock(LessonsBookedDAO.class);
	
	private Client mockClient = Mockito.mock(Client.class);
	private Lesson mockLesson = Mockito.mock(Lesson.class);
	private Lesson mockLessonTwo = Mockito.mock(Lesson.class);
	
	@Before
	public void beforeTest() {
		// Setup the mock DAOs on the DAOFactory
		DAOFactory.setTestClientsDAO(mockClientsDAO);
		DAOFactory.setTestLessonsBookedDAO(mockBookedLessonsDAO);
	}
	
	/**
	 * Positive test case for when the bookings are finalised successfully
	 */
	@Test
	public void testHandleAction() {
		// Setup the Selected lessons attribute on the session
		when(session.getAttribute(SessionAttribute.SELECTED_LESSONS.getAttributeKey())).thenReturn(selectedLessons);
		when(session.getAttribute(SessionAttribute.USER.getAttributeKey())).thenReturn("username");
		
		// Setup the mock session to return
		when(request.getSession(false)).thenReturn(session);

		// Create the set of lessons to book
		Map<String, Lesson> lessonsToBook = new HashMap<String, Lesson>();
		lessonsToBook.put("L1", mockLesson);
		lessonsToBook.put("L2", mockLessonTwo);
		
		when(selectedLessons.getSelectedLessonsCollection()).thenReturn(lessonsToBook);
	
		// Make sure the client record is returned from the database
		when(mockClientsDAO.getByUsername(eq("username"))).thenReturn(mockClient);
		
		when(mockClient.getClientID()).thenReturn(99);
		
		// Create the object in test
		FinaliseBookingManager finaliseBookingManager = new FinaliseBookingManager();

		// Call the method in test
		boolean result = finaliseBookingManager.handleAction(request, null);
		
		// Assert the result was as expected
		assertTrue(result);
		
		verify(mockBookedLessonsDAO, times(1)).bookLessonsForClient(eq(lessonsToBook.values()), eq(99));
	}
}
