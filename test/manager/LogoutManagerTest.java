package manager;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

public class LogoutManagerTest {

	private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private HttpSession session = Mockito.mock(HttpSession.class);

	/**
	 * Positive test case for when the user logged out successfully
	 */
	@Test
	public void testHandleAction() {
		// Setup the mock session to return
		when(request.getSession(false)).thenReturn(session);

		// Create the object in test
		LogoutManager logoutManager = new LogoutManager();

		// Call the method in test
		boolean result = logoutManager.handleAction(request, null);

		// Assert the response was as expected
		assertTrue(result);

		// Verify the session was invalidated
		verify(session, times(1)).invalidate();

	}
}
