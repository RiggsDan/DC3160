package manager;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import dao.ClientsDAO;
import dao.DAOFactory;
import model.Client;

public class CheckNameManagerTest {

	private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

	private ClientsDAO mockClientsDAO = Mockito.mock(ClientsDAO.class);
	
	private Client mockClient = Mockito.mock(Client.class);
	
	private PrintWriter printWriter = Mockito.mock(PrintWriter.class);

	@Before
	public void beforeTest() {
		// Setup the mock DAOs on the DAOFactory
		DAOFactory.setTestClientsDAO(mockClientsDAO);
	}
	
	/**
	 * positive test case for when the username is free
	 * @throws IOException 
	 */
	@Test
	public void testHandleAction() throws IOException {
		// Setup the expected attributes on the request
		when(request.getParameter(eq("newUsername"))).thenReturn("newUsername");

		// Make sure a client record is returned from the database
		when(mockClientsDAO.getByUsername(eq("newUsername"))).thenReturn(null);
		
		// Setup the printWrite to return
		when(response.getWriter()).thenReturn(printWriter);
		
		// Create the object in test
		CheckNameManager checkNameManager = new CheckNameManager();

		// Call the method in test
		boolean result = checkNameManager.handleAction(request, response);

		// Assert the result was as expected
		assertTrue(result);
		
		// Verify the expected JSON was written out
		verify(printWriter, times(1)).println(eq("{\"isUsernameValid\":true}"));
	}
	
	/**
	 * Negative test case for when the username is already in use
	 * @throws IOException 
	 */
	@Test
	public void testHandleActionUserExists() throws IOException {
		// Setup the expected attributes on the request
		when(request.getParameter(eq("newUsername"))).thenReturn("newUsername");

		// Make sure a client record is returned from the database
		when(mockClientsDAO.getByUsername(eq("newUsername"))).thenReturn(mockClient);
		
		// Setup the printWrite to return
		when(response.getWriter()).thenReturn(printWriter);
		
		// Create the object in test
		CheckNameManager checkNameManager = new CheckNameManager();

		// Call the method in test
		boolean result = checkNameManager.handleAction(request, response);

		// Assert the result was as expected
		assertTrue(result);
		
		// Verify the expected JSON was written out
		verify(printWriter, times(1)).println(eq("{\"isUsernameValid\":false}"));
	}
	
}
