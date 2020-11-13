package manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import logging.SystemLogger;
import manager.factory.ActionManager;

/**
 * Class to handle the username validation functionality of the system
 * 
 * @author Daniel
 *
 */
public class CheckNameManager implements ActionManager {

	@Override
	public boolean handleAction(HttpServletRequest request, HttpServletResponse response) {
		// Pull out the login details from the request
		String newUsername = request.getParameter("newUsername");
		SystemLogger.config("Checking if username %s was already in use", newUsername);
		
		// If the username was found in the database
		boolean isUsernameValid = false;
		
		try (DAOFactory daoFactory = new DAOFactory()) {
			isUsernameValid= (daoFactory.getClientsDAO().getByUsername(newUsername) == null);
		} catch (SQLException e) {
			SystemLogger.severe("An exception occured whilst getting the client record from the database, reason was %s", e.getMessage());
			return false;
		}
		
		if (isUsernameValid) {
			SystemLogger.fine("Username %s was not found in the database so is free", newUsername);
		} else {
			SystemLogger.fine("Username %s was found in the database so is not free", newUsername);
		}
		
		response.setContentType("application/json;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			// Write out the JSON for the boolean value of 'if the response is valid'
			out.println(String.format("{\"isUsernameValid\":%s}", isUsernameValid));
		} catch (IOException e) {
			SystemLogger.severe("An exception occured whilst writing the JSON response, reason was %s", e.getMessage());
			return false;
		}
		
		return true;
	}
}
