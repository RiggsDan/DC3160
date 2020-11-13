package manager;

import java.sql.SQLException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.constants.SessionAttribute;
import dao.DAOFactory;
import logging.SystemLogger;
import manager.factory.ActionManager;
import model.Client;
import model.LessonBooked;
import model.SelectedLessons;

/**
 * Class to handle the login functionality of the system
 * 
 * @author Daniel
 *
 */
public class LoginManager implements ActionManager {
	
	/**
	 * Handles the validation of whether the username and password is correct
	 * (Through a database lookup), if the validation is successful then a session
	 * will be created and the username will be set as the 'user' attribute
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public boolean handleAction(HttpServletRequest request, HttpServletResponse response) {
		// Pull out the login details from the request
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		SystemLogger.config("Handling the login of user %s with password %s", username, password);
		
		Client clientRecord;
		try (DAOFactory daoFactory = new DAOFactory()) {
			// Get the user from the database (Using both username AND password; means
			// password validation is done implicitly)
			clientRecord = daoFactory.getClientsDAO().getByUsernameAndPassword(username, password);
		
			// If no client record was returned then either the user doesn't exist OR the
			// password was wrong (Doesn't matter which, just return false)
			if (clientRecord == null) {
				SystemLogger.warning("The login attempt by user %s with %s was unsuccessfull", username, password);
				return false;
			}
		
			SystemLogger.fine("The Client record %s was found", clientRecord.toString());
		
			// Create the new session
			HttpSession session = request.getSession();
			session.setAttribute(SessionAttribute.USER.getAttributeKey(), clientRecord.getUsername());
			
			SelectedLessons selectedLessons = new SelectedLessons();
			
			Set<LessonBooked> bookedLessons = daoFactory.getLessonsBookedDAO().getLessonsBookedByClientID(clientRecord.getClientID());
			
			for (LessonBooked lessonBooked : bookedLessons) {
				selectedLessons.addSelectedLesson(daoFactory.getLessonsDAO().getLessonByID(lessonBooked.getLessonID()));
			}
			// Create the lesson selection holder object and add it to the session
			session.setAttribute(SessionAttribute.SELECTED_LESSONS.getAttributeKey(), selectedLessons);
					
			// Return true as a session has successfully been created
			SystemLogger.config("The user %s successfully logged in", clientRecord.getUsername());
			return true;
		} catch (SQLException e) {
			SystemLogger.severe("An exception occured whilst handling the login of user %s, reason was %s", username, e.getMessage());
			return false;
		}
	}
	
}
