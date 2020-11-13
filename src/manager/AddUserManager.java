package manager;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import logging.SystemLogger;
import manager.factory.ActionManager;

/**
 * Class to handle the user creation (signup) functionality of the system
 * 
 * @author Daniel
 *
 */
public class AddUserManager implements ActionManager {

	/**
	 * Handles the creation of the user
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public boolean handleAction(HttpServletRequest request, HttpServletResponse response) {
		// Pull out the login details from the request
		String newUsername = request.getParameter("newUsername");
		String newPassword = request.getParameter("newPassword");

		SystemLogger.config("Handling the creation of user %s with password %s", newUsername, newPassword);

		try (DAOFactory daoFactory = new DAOFactory()) {
			daoFactory.getClientsDAO().addNewClientRecord(newUsername, newPassword);
		} catch(SQLException e) {
			SystemLogger.severe("An exception occured whilst adding the new client record, reason was %s", e.getMessage());
			return false;
		}
		
		SystemLogger.config("User %s created successfully", newUsername);
		return true;
	}
}
