package manager;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import controller.constants.SessionAttribute;
import dao.DAOFactory;
import dao.LessonsDAO;
import model.Lesson;
import model.SelectedLessons;

public class ChooseLessonManagerTest {

	private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private HttpSession session =  Mockito.mock(HttpSession.class);

	private LessonsDAO mockLessonsDAO = Mockito.mock(LessonsDAO.class);
	private Lesson mockLesson = Mockito.mock(Lesson.class);
	private SelectedLessons selectedLessons = Mockito.mock(SelectedLessons.class);

	@Before
	public void beforeTest() {
		// Setup the mock DAOs on the DAOFactory
		DAOFactory.setTestLessonsDAO(mockLessonsDAO);
	}
	/**
	 * Positive test case for when the lesson is chosen successfully
	 */
	@Test
	public void testHandleAction() {
		// Setup the expected attributes on the request
		when(request.getParameter(eq("lessonID"))).thenReturn("L1");
				
		// Make sure the lesson object is returned from the database
		when(mockLessonsDAO.getLessonByID(eq("L1"))).thenReturn(mockLesson);
		
		// Setup the mock session to return
		when(request.getSession(false)).thenReturn(session);

		// Setup the Selected lessons attribute on the session
		when(session.getAttribute(SessionAttribute.SELECTED_LESSONS.getAttributeKey())).thenReturn(selectedLessons);

		// Create the object in test
		ChooseLessonManager chooseLessonManager = new ChooseLessonManager();
		
		// Call the method in test
		boolean result = chooseLessonManager.handleAction(request, null);
		
		// Assert the result was as expected
		assertTrue(result);
		
		// Verify the lesson was removed
		verify(selectedLessons, times(1)).addSelectedLesson(mockLesson);
		
		// Verify the Selected lessons was updated
		verify(session, times(1)).setAttribute(eq(SessionAttribute.SELECTED_LESSONS.getAttributeKey()),
				eq(selectedLessons));
	}
}
