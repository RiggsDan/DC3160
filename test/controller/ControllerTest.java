package controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
	private ServletContext servletContext = Mockito.mock(ServletContext.class);
	private RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);

	private HttpSession session = Mockito.mock(HttpSession.class);

	private ManagerFactory testManagerFactory = Mockito.mock(ManagerFactory.class);
	private ActionManager testActionManager = Mockito.mock(ActionManager.class);

	/**
	 * Positive test case for when the login function is called
	 * @throws Exception
	 */
	@Test
	public void testDoPostLogin() throws Exception {
		// Make sure the ActionManager is returned from the factory correctly
		when(testManagerFactory.getActionManager(eq(ControllerAction.LOGIN))).thenReturn(testActionManager);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
		
		// Ensure the expected path is set
		when(request.getPathInfo()).thenReturn("/login");
		
		// Create the object in test (overriding the getServletContext() method as it will not be set in junit)
		Controller controller = new Controller() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
		// Mock the request dispatched fetch
		when(servletContext.getRequestDispatcher(any(String.class))).thenReturn(requestDispatcher);
		
		// Set the test factory manager 
		controller.setTestManagerFactory(testManagerFactory);
		
		// Call the method in test
		controller.doPost(request, response);
		
		// Verify that the correct action manager was fetched
		verify(testManagerFactory, times(1)).getActionManager(eq(ControllerAction.LOGIN));
		
		// Verify the handle action method was called
		verify(testActionManager, times(1)).handleAction(request, response);
		
		// Verify the request was forwarded
		verify(servletContext, times(1)).getRequestDispatcher(any(String.class));
		
	}
	
	/**
	 * Negative test case for when the login function is called but the user already has a session
	 * @throws Exception
	 */
	@Test
	public void testDoPostLoginExistingSession() throws Exception {
		// Make sure the ActionManager is returned from the factory correctly
		when(testManagerFactory.getActionManager(eq(ControllerAction.LOGIN))).thenReturn(testActionManager);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
		
		// Return the session
		when(request.getSession(false)).thenReturn(session);
		
		// Ensure the expected path is set
		when(request.getPathInfo()).thenReturn("/login");
		
		// Create the object in test (overriding the getServletContext() method as it will not be set in junit)
		Controller controller = new Controller() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
		// Mock the request dispatched fetch
		when(servletContext.getRequestDispatcher(any(String.class))).thenReturn(requestDispatcher);
		
		// Set the test factory manager 
		controller.setTestManagerFactory(testManagerFactory);
		
		// Call the method in test
		controller.doPost(request, response);
		
		// Verify that no action manager was fetched
		verify(testManagerFactory, times(0)).getActionManager(any(ControllerAction.class));
		
		// Verify the response was redirected action method was called
		verify(response, times(1)).sendRedirect(null);
		
	}
	
	/**
	 * Positive test case for when the login function is called
	 * @throws Exception
	 */
	@Test
	public void testDoPostLoginFailedAction() throws Exception {
		// Make sure the ActionManager is returned from the factory correctly
		when(testManagerFactory.getActionManager(eq(ControllerAction.LOGIN))).thenReturn(testActionManager);
		
		// When the handle action method is called, return false
		when(testActionManager.handleAction(request, response)).thenReturn(false);
		
		// Ensure the expected path is set
		when(request.getPathInfo()).thenReturn("/login");
		
		// Create the object in test (overriding the getServletContext() method as it will not be set in junit)
		Controller controller = new Controller() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
		// Mock the request dispatched fetch
		when(servletContext.getRequestDispatcher(any(String.class))).thenReturn(requestDispatcher);
		
		// Set the test factory manager 
		controller.setTestManagerFactory(testManagerFactory);
		
		// Call the method in test
		controller.doPost(request, response);
		
		// Verify that the correct action manager was fetched
		verify(testManagerFactory, times(1)).getActionManager(eq(ControllerAction.LOGIN));
		
		// Verify the handle action method was called
		verify(testActionManager, times(1)).handleAction(request, response);
		
		// Verify the response was redirected action method was called
		verify(response, times(1)).sendRedirect(null);
		
	}
	
	/**
	 * Positive test case for when the login function is called
	 * @throws Exception
	 */
	@Test
	public void testDoPostAddUser() throws Exception {
		// Make sure the ActionManager is returned from the factory correctly
		when(testManagerFactory.getActionManager(eq(ControllerAction.ADD_USER))).thenReturn(testActionManager);
		
		// Return the session
		when(request.getSession(false)).thenReturn(session);
				
		// Ensure the expected path is set
		when(request.getPathInfo()).thenReturn("/addUser");
		
		// Create the object in test (overriding the getServletContext() method as it will not be set in junit)
		Controller controller = new Controller() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
		// Mock the request dispatched fetch
		when(servletContext.getRequestDispatcher(any(String.class))).thenReturn(requestDispatcher);
		
		// Set the test factory manager 
		controller.setTestManagerFactory(testManagerFactory);
		
		// Call the method in test
		controller.doPost(request, response);
		
		// Verify that no action manager was fetched
		verify(testManagerFactory, times(0)).getActionManager(any(ControllerAction.class));
		
		// Verify the response was redirected action method was called
		verify(response, times(1)).sendRedirect(null);
		
	}
	

	/**
	 * Positive test case for when the login function is called
	 * @throws Exception
	 */
	@Test
	public void testDoPostAddUserHasSession() throws Exception {
		// Make sure the ActionManager is returned from the factory correctly
		when(testManagerFactory.getActionManager(eq(ControllerAction.ADD_USER))).thenReturn(testActionManager);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
	
		// Return the session
		when(request.getSession(false)).thenReturn(null);
				
		// Ensure the expected path is set
		when(request.getPathInfo()).thenReturn("/addUser");
		
		// Create the object in test (overriding the getServletContext() method as it will not be set in junit)
		Controller controller = new Controller() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
		// Mock the request dispatched fetch
		when(servletContext.getRequestDispatcher(any(String.class))).thenReturn(requestDispatcher);
		
		// Set the test factory manager 
		controller.setTestManagerFactory(testManagerFactory);
		
		// Call the method in test
		controller.doPost(request, response);
		
		// Verify that the correct action manager was fetched
		verify(testManagerFactory, times(1)).getActionManager(eq(ControllerAction.ADD_USER));
		
		// Verify the handle action method was called
		verify(testActionManager, times(1)).handleAction(request, response);
		
		// Verify the response was redirected action method was called
		verify(response, times(1)).sendRedirect(null);
		
	}
	
	/**
	 * Positive test case for when the view timetable function is called
	 * @throws Exception
	 */
	@Test
	public void testDoPostLogout() throws Exception {
		// Make sure the ActionManager is returned from the factory correctly
		when(testManagerFactory.getActionManager(eq(ControllerAction.LOGOUT))).thenReturn(testActionManager);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
	
		// Return the session
		when(request.getSession(false)).thenReturn(session);
				
		// Ensure the expected path is set
		when(request.getPathInfo()).thenReturn("/logout");
		
		// Create the object in test (overriding the getServletContext() method as it will not be set in junit)
		Controller controller = new Controller() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
		// Mock the request dispatched fetch
		when(servletContext.getRequestDispatcher(any(String.class))).thenReturn(requestDispatcher);
		
		// Set the test factory manager 
		controller.setTestManagerFactory(testManagerFactory);
		
		// Call the method in test
		controller.doPost(request, response);
		
		// Verify that the correct action manager was fetched
		verify(testManagerFactory, times(1)).getActionManager(eq(ControllerAction.LOGOUT));
		
		// Verify the handle action method was called
		verify(testActionManager, times(1)).handleAction(request, response);
		
		// Verify the response was redirected action method was called
		verify(response, times(1)).sendRedirect(null);
		
	}
	
	/**
	 * Positive test case for when the view timetable function is called
	 * @throws Exception
	 */
	@Test
	public void testDoPostViewTimetable() throws Exception {
		// Make sure the ActionManager is returned from the factory correctly
		when(testManagerFactory.getActionManager(eq(ControllerAction.VIEW_TIMETABLE))).thenReturn(testActionManager);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
	
		// Return the session
		when(request.getSession(false)).thenReturn(session);
				
		// Ensure the expected path is set
		when(request.getPathInfo()).thenReturn("/viewTimetable");
		
		// Create the object in test (overriding the getServletContext() method as it will not be set in junit)
		Controller controller = new Controller() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
		// Mock the request dispatched fetch
		when(servletContext.getRequestDispatcher(any(String.class))).thenReturn(requestDispatcher);
		
		// Set the test factory manager 
		controller.setTestManagerFactory(testManagerFactory);
		
		// Call the method in test
		controller.doPost(request, response);
		
		// Verify that the correct action manager was fetched
		verify(testManagerFactory, times(1)).getActionManager(eq(ControllerAction.VIEW_TIMETABLE));
		
		// Verify the handle action method was called
		verify(testActionManager, times(1)).handleAction(request, response);
		
		// Verify the request was forwarded
		verify(servletContext, times(1)).getRequestDispatcher(any(String.class));
		
	}
	
	/**
	 * Positive test case for when the choose lesson function is called
	 * @throws Exception
	 */
	@Test
	public void testDoPostChooseLesson() throws Exception {
		// Make sure the ActionManager is returned from the factory correctly
		when(testManagerFactory.getActionManager(eq(ControllerAction.CHOOSE_LESSON))).thenReturn(testActionManager);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
	
		// Return the session
		when(request.getSession(false)).thenReturn(session);
				
		// Ensure the expected path is set
		when(request.getPathInfo()).thenReturn("/chooseLesson");
		
		// Create the object in test (overriding the getServletContext() method as it will not be set in junit)
		Controller controller = new Controller() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
		// Mock the request dispatched fetch
		when(servletContext.getRequestDispatcher(any(String.class))).thenReturn(requestDispatcher);
		
		// Set the test factory manager 
		controller.setTestManagerFactory(testManagerFactory);
		
		// Call the method in test
		controller.doPost(request, response);
		
		// Verify that the correct action manager was fetched
		verify(testManagerFactory, times(1)).getActionManager(eq(ControllerAction.CHOOSE_LESSON));
		
		// Verify the handle action method was called
		verify(testActionManager, times(1)).handleAction(request, response);
		
		// Verify the request was forwarded
		verify(servletContext, times(1)).getRequestDispatcher(any(String.class));
		
	}
	
	/**
	 * Positive test case for when the cancel lessonfunction is called
	 * @throws Exception
	 */
	@Test
	public void testDoPostCancelLesson() throws Exception {
		// Make sure the ActionManager is returned from the factory correctly
		when(testManagerFactory.getActionManager(eq(ControllerAction.CANCEL_LESSON))).thenReturn(testActionManager);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
	
		// Return the session
		when(request.getSession(false)).thenReturn(session);
				
		// Ensure the expected path is set
		when(request.getPathInfo()).thenReturn("/cancelLesson");
		
		// Create the object in test (overriding the getServletContext() method as it will not be set in junit)
		Controller controller = new Controller() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
		// Mock the request dispatched fetch
		when(servletContext.getRequestDispatcher(any(String.class))).thenReturn(requestDispatcher);
		
		// Set the test factory manager 
		controller.setTestManagerFactory(testManagerFactory);
		
		// Call the method in test
		controller.doPost(request, response);
		
		// Verify that the correct action manager was fetched
		verify(testManagerFactory, times(1)).getActionManager(eq(ControllerAction.CANCEL_LESSON));
		
		// Verify the handle action method was called
		verify(testActionManager, times(1)).handleAction(request, response);
		
		// Verify the request was forwarded
		verify(servletContext, times(1)).getRequestDispatcher(any(String.class));
		
	}
	
	/**
	 * Positive test case for when the view selection function is called
	 * @throws Exception
	 */
	@Test
	public void testDoPostViewSelection() throws Exception {
		// Make sure the ActionManager is returned from the factory correctly
		when(testManagerFactory.getActionManager(eq(ControllerAction.VIEW_SELECTION))).thenReturn(testActionManager);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
	
		// Return the session
		when(request.getSession(false)).thenReturn(session);
				
		// Ensure the expected path is set
		when(request.getPathInfo()).thenReturn("/viewSelection");
		
		// Create the object in test (overriding the getServletContext() method as it will not be set in junit)
		Controller controller = new Controller() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
		// Mock the request dispatched fetch
		when(servletContext.getRequestDispatcher(any(String.class))).thenReturn(requestDispatcher);
		
		// Set the test factory manager 
		controller.setTestManagerFactory(testManagerFactory);
		
		// Call the method in test
		controller.doPost(request, response);
		
		// Verify that the correct action manager was fetched
		verify(testManagerFactory, times(1)).getActionManager(eq(ControllerAction.VIEW_SELECTION));
		
		// Verify the handle action method was called
		verify(testActionManager, times(1)).handleAction(request, response);
		
		// Verify the request was forwarded
		verify(servletContext, times(1)).getRequestDispatcher(any(String.class));
		
	}
	
	/**
	 * Positive test case for when the finalise booking function is called
	 * @throws Exception
	 */
	@Test
	public void testDoPostFinaliseBooking() throws Exception {
		// Make sure the ActionManager is returned from the factory correctly
		when(testManagerFactory.getActionManager(eq(ControllerAction.FINALISE_BOOKING))).thenReturn(testActionManager);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
	
		// Return the session
		when(request.getSession(false)).thenReturn(session);
				
		// Ensure the expected path is set
		when(request.getPathInfo()).thenReturn("/finaliseBooking");
		
		// Create the object in test (overriding the getServletContext() method as it will not be set in junit)
		Controller controller = new Controller() {/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
		// Mock the request dispatched fetch
		when(servletContext.getRequestDispatcher(any(String.class))).thenReturn(requestDispatcher);
		
		// Set the test factory manager 
		controller.setTestManagerFactory(testManagerFactory);
		
		// Call the method in test
		controller.doPost(request, response);
		
		// Verify that the correct action manager was fetched
		verify(testManagerFactory, times(1)).getActionManager(eq(ControllerAction.FINALISE_BOOKING));
		
		// Verify the handle action method was called
		verify(testActionManager, times(1)).handleAction(request, response);
		
		// Verify the request was forwarded
		verify(servletContext, times(1)).getRequestDispatcher(any(String.class));
		
	}
	
	/**
	 * Positive test case for when the check name function is called
	 * @throws Exception
	 */
	@Test
	public void testDoPostCheckName() throws Exception {
		// Make sure the ActionManager is returned from the factory correctly
		when(testManagerFactory.getActionManager(eq(ControllerAction.CHECK_NAME))).thenReturn(testActionManager);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
		
		// When the handle action method is called, return true
		when(testActionManager.handleAction(request, response)).thenReturn(true);
	
		// Return the session
		when(request.getSession(false)).thenReturn(null);
				
		// Ensure the expected path is set
		when(request.getPathInfo()).thenReturn("/checkName");
		
		// Create the object in test (overriding the getServletContext() method as it will not be set in junit)
		Controller controller = new Controller() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public ServletContext getServletContext() {
				return servletContext;
			}
		};
		
		// Mock the request dispatched fetch
		when(servletContext.getRequestDispatcher(any(String.class))).thenReturn(requestDispatcher);
		
		// Set the test factory manager 
		controller.setTestManagerFactory(testManagerFactory);
		
		// Call the method in test
		controller.doPost(request, response);
		
		// Verify that the correct action manager was fetched
		verify(testManagerFactory, times(1)).getActionManager(eq(ControllerAction.CHECK_NAME));
		
		// Verify the handle action method was called
		verify(testActionManager, times(1)).handleAction(request, response);
		
		// Verify the request was neither forwarded nor redirected
		verify(servletContext, times(0)).getRequestDispatcher(any(String.class));
		verify(response, times(0)).sendRedirect(null);
	}
}
