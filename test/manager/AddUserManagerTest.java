package manager;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import dao.ClientsDAO;
import dao.DAOFactory;

public class AddUserManagerTest {

	private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	
	private ClientsDAO mockClientsDAO = Mockito.mock(ClientsDAO.class);

	@Before
	public void beforeTest() {
		// Setup the mock DAOs on the DAOFactory
		DAOFactory.setTestClientsDAO(mockClientsDAO);

	}
	
	/**
	 * Positive test case for when the user creation is handled successfully
	 */
	@Test
	public void testHandleAction() {
		// Setup the expected attributes on the request
		when(request.getParameter(eq("newUsername"))).thenReturn("username");
		when(request.getParameter(eq("newPassword"))).thenReturn("password");
				
		// Create the object in test
		AddUserManager addUserManager = new AddUserManager();
		
		// Call the method in test
		boolean result = addUserManager.handleAction(request, null);
		
		// Assert the result was as expected
		assertTrue(result);
		
		// Verify the new record was added
		verify(mockClientsDAO, times(1)).addNewClientRecord(eq("username"), eq("password"));
	}
}
