package manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.constants.SessionAttribute;
import logging.SystemLogger;
import manager.factory.ActionManager;

/**
 * Class to handle the logout functionality of the system
 * @author Daniel
 *
 */
public class LogoutManager implements ActionManager {

	/**
	 * Handles the logout of the user (Invalidates the current session)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public boolean handleAction(HttpServletRequest request, HttpServletResponse response) {
		String username = (String) request.getSession(false).getAttribute(SessionAttribute.USER.getAttributeKey());
		SystemLogger.config("Handling the logout of user %s", username);
		
		// Invalidate the current session
		request.getSession(false).invalidate();
		
		
		SystemLogger.config("User %s logged out successfully", username);
		return true;
	}
}
