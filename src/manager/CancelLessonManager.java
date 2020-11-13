package manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.constants.SessionAttribute;
import logging.SystemLogger;
import manager.factory.ActionManager;
import model.SelectedLessons;

/**
 * Class to handle the lesson cancelling functionality of the system
 * 
 * @author Daniel
 *
 */
public class CancelLessonManager implements ActionManager  {

	/**
	 * Removes the cancelled lesson from the 'selected lessons' of the session
	 */
	@Override
	public boolean handleAction(HttpServletRequest request, HttpServletResponse response) {
		// Pull out the login details from the request
		String lessonID = request.getParameter("lessonID");
		
		SystemLogger.config("Removing the lesson with ID %s from the selection for user", lessonID);
		
		HttpSession session = request.getSession(false);
		
		// Get the selected lessons holder from the session
		SelectedLessons selectedLessons = (SelectedLessons) session.getAttribute(SessionAttribute.SELECTED_LESSONS.getAttributeKey());
		
		// Add the new lesson
		selectedLessons.removeLessonByID(lessonID);
		
		// Update the selected lessons on the session
		session.setAttribute(SessionAttribute.SELECTED_LESSONS.getAttributeKey(), selectedLessons);
		
		return true;
	}
}
