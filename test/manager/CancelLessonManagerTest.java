package manager;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import controller.constants.SessionAttribute;
import model.SelectedLessons;

public class CancelLessonManagerTest {

	private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private HttpSession session = Mockito.mock(HttpSession.class);
	private SelectedLessons selectedLessons = Mockito.mock(SelectedLessons.class);

	/**
	 * Positive test case for when the lesson is cancelled successfully
	 */
	@Test
	public void testHandleAction() {
		// Setup the expected attributes on the request
		when(request.getParameter(eq("lessonID"))).thenReturn("L1");

		// Setup the Selected lessons attribute on the session
		when(session.getAttribute(SessionAttribute.SELECTED_LESSONS.getAttributeKey())).thenReturn(selectedLessons);

		// Setup the mock session to return
		when(request.getSession(false)).thenReturn(session);

		// Create the object in test
		CancelLessonManager cancelLessonManager = new CancelLessonManager();

		// Call the method in test
		boolean result = cancelLessonManager.handleAction(request, null);

		// Assert the result was as expected
		assertTrue(result);
		
		// Verify the Selected lessons was updated
		verify(session, times(1)).setAttribute(eq(SessionAttribute.SELECTED_LESSONS.getAttributeKey()),
				eq(selectedLessons));

		// verify the lesson was removed
		verify(selectedLessons, times(1)).removeLessonByID(eq("L1"));
	}
}
