package controller;


import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;

import manager.factory.ActionManager;
import manager.factory.ControllerAction;
import manager.factory.ManagerFactory;

public class ControllerTest {

	private HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	private HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
	
	private HttpSession session =  Mockito.mock(HttpSession.class);
	
	private ManagerFactory testManagerFactory = Mockito.mock(ManagerFactory.class);
	private ActionManager testActionManager = Mockito.mock(ActionManager.class);
	
	@Test
	public void testDoPostLogin() throws Exception {
		when(request.getPathInfo()).thenReturn("/login");
		
		// Create the object in test
		Controller controller = new Controller();
		
		controller.setTestManagerFactory(testManagerFactory);
		
		// Call the method in test
		controller.doPost(request, response);
		
		verify(testManagerFactory, times(1)).getActionManager(eq(ControllerAction.LOGIN));
		
		verify(testActionManager, times(1)).handleAction(request, response);
		
	}
}
