package controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.constants.Destination;
import logging.SystemLogger;
import manager.factory.ActionManager;
import manager.factory.ControllerAction;
import manager.factory.ManagerFactory;


@WebServlet(value = "/do/*")
public class Controller extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3975176143903646005L;

	private ManagerFactory testManagerFactory;
	
	/** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	// Get the action to be performed from the path info and convert it to an Enum value
    	String action = request.getPathInfo();
    	ControllerAction controllerAction = ControllerAction.getByAction(action);
    	
    	SystemLogger.config("The controller has been requested to perform the %s action", controllerAction);
    	
    	// Check if a session is required for the requested action, if one is and doesn't exist the redirect to the home page
    	if (controllerAction.requiresSession() && request.getSession(false) == null) {
    		SystemLogger.warning("The user tried to access an operation requiring a session without having logged in, returning them to the home page");
    		response.sendRedirect(request.getContextPath());
    		return;
    	}
    	
    	ActionManager actionManager = getActionManager(controllerAction);
    	
    	boolean actionResult = true;
    	// If an actionManager was found then call handleAction on it
    	if (actionManager != null) {
    		actionResult = actionManager.handleAction(request, response);
    	}
    	
    	// Handle any navigation required based on the action type
    	switch (controllerAction) {
    		case LOGIN:
    			if (actionResult) {
    				handleForward(request, response, ("/do" + ControllerAction.VIEW_TIMETABLE.getAction()));
    			
    			} else {
    				response.sendRedirect(request.getContextPath());
    			}
    			break;
			case CHOOSE_LESSON:
				handleForward(request, response, Destination.LESSON_TIMETABLE_VIEW.getURL());
				break;
			case ADD_USER:
				response.sendRedirect(request.getContextPath());
	    		break;
			case FINALISE_BOOKING:
				handleForward(request, response, Destination.LESSON_SELECTION_VIEW.getURL());
				break;
			case LOGOUT:
				response.sendRedirect(request.getContextPath());
				break;
			case VIEW_SELECTION:
				handleForward(request, response, Destination.LESSON_SELECTION_VIEW.getURL());
				break;
			case VIEW_TIMETABLE:
				handleForward(request, response, Destination.LESSON_TIMETABLE_VIEW.getURL());
				break;
			case CANCEL_LESSON:
				handleForward(request, response, Destination.LESSON_SELECTION_VIEW.getURL());
				break;
			case CHECK_NAME:
				break;
				
    	}
    	
    	SystemLogger.config("Handling of %s action completed", controllerAction);
        

    }
    
    /**
     * Method to return the correct ActionManager for the action type passed in
     * @param controllerAction
     * @return
     */
    private ActionManager getActionManager(ControllerAction controllerAction) {
    	if (testManagerFactory != null) {
    		return testManagerFactory.getActionManager(controllerAction);
    	}
    	
    	ManagerFactory managerFactory = new ManagerFactory();
    	
    	return managerFactory.getActionManager(controllerAction);
    }
    
    public void setTestManagerFactory(ManagerFactory managerFactory) {
    	testManagerFactory = managerFactory;
    }
    
    /**
     * @param request
     * @param response
     * @param destination
     * @throws ServletException
     * @throws IOException
     */
    private void handleForward(HttpServletRequest request, HttpServletResponse response, String destination) throws ServletException, IOException {
    	ServletContext servletContext = this.getServletContext();
    	
    	// This is just for junits as the SevletContext will not be set
    	if (servletContext != null) {
    		servletContext.getRequestDispatcher(destination).forward(request, response);
    	}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	processRequest(request, response);

    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	processRequest(request, response);
    }
    
    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
