package manager;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.constants.SessionAttribute;
import dao.DAOFactory;
import logging.SystemLogger;
import manager.factory.ActionManager;
import model.Lesson;
import model.Lessons;

/**
 * Class to handle the timetable view functionality of the system
 * @author Daniel
 *
 */
public class ViewTimetableManager implements ActionManager {
	
	private static final long MAX_AGE_OF_LESSONS = 60*1000;

	/**
	 * Method that sets up the required attributes on the session for displaying the full list of available lessons
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public boolean handleAction(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		
		// The first time the timetable view is shown, the session will not have the timetables set.
		if (session.getAttribute(SessionAttribute.LESSONS.getAttributeKey()) == null) {
			SystemLogger.config("The full lessons list has not yet been setup for this session, will fetch now");
			session.setAttribute(SessionAttribute.LESSONS.getAttributeKey(), getLessons());
			
			return true;
		}
		
		// Get the existing lessons object from the session
		Lessons existingLessons = (Lessons) session.getAttribute(SessionAttribute.LESSONS.getAttributeKey());
		
		if (existingLessons.getReadTime() < System.currentTimeMillis() - MAX_AGE_OF_LESSONS) {
			SystemLogger.config("The existing lessons list has aged beyond the time to live of %s, will be refetched", MAX_AGE_OF_LESSONS);
			
			session.setAttribute(SessionAttribute.LESSONS.getAttributeKey(), getLessons());
			
			return true;
		}

		// At this point we have a lessons object we are happy to keep using so no need to re-fetch
		SystemLogger.fine("The full lessons list of the session already exist and were refreshed within the time to live of %s, will not refetch", MAX_AGE_OF_LESSONS);
		return true;
	}
	
	/**
	 * Creates and returns a holder object representing the available lessons
	 * @return
	 */
	private static Lessons getLessons() {
		SystemLogger.fine("Creating a new lessons object");
		
		// Get the current time (This will enable a sensible 'time to live' to be actioned for the lessons)
		long updateTime = System.currentTimeMillis();
		
		Set<Lesson> lessonsFromDatabase = new HashSet<>();
		try (DAOFactory daoFactory = new DAOFactory()) {
			// Get the lessons from the database
			lessonsFromDatabase = daoFactory.getLessonsDAO().getAllLessons();
		} catch(SQLException e) {
			SystemLogger.severe("An exception occured whilst selecting the existing lessons from the database, reason was %s", e.getMessage());
		}
		
		// Create the holder object to associate the lessons with when they were selected
		Lessons lessons = new Lessons(lessonsFromDatabase, updateTime);
		SystemLogger.fine("Created a new lessons object at time %s", updateTime);
		
		return lessons;
	}

}
