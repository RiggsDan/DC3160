package manager;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.constants.SessionAttribute;
import dao.DAOFactory;
import logging.SystemLogger;
import manager.factory.ActionManager;
import model.Lesson;
import model.SelectedLessons;

/**
 * Class to handle the lesson selection functionality of the system
 * 
 * @author Daniel
 *
 */
public class ChooseLessonManager implements ActionManager {


	@Override
	public boolean handleAction(HttpServletRequest request, HttpServletResponse response) {
		// Pull out the login details from the request
		String lessonID = request.getParameter("lessonID");
		
		SystemLogger.config("Adding the lesson with ID %s to the selection for user", lessonID);
		
		Lesson selectedLesson;
		try (DAOFactory daoFactory = new DAOFactory()) {
			selectedLesson = daoFactory.getLessonsDAO().getLessonByID(lessonID);
		} catch (SQLException e) {
			SystemLogger.severe("An exception occures whilst selecting the lesson with ID %s from the databasem reason was %s", lessonID, e.getMessage());
			return false;
		}
		
		HttpSession session = request.getSession(false);
		
		// Get the selected lessons holder from the session
		SelectedLessons selectedLessons = (SelectedLessons) session.getAttribute(SessionAttribute.SELECTED_LESSONS.getAttributeKey());
		
		// Add the new lesson
		selectedLessons.addSelectedLesson(selectedLesson);
		
		// Update the selected lessons on the session
		session.setAttribute(SessionAttribute.SELECTED_LESSONS.getAttributeKey(), selectedLessons);
		
		return true;
	}
}
