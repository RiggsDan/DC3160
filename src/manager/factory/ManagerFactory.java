package manager.factory;

import logging.SystemLogger;
import manager.AddUserManager;
import manager.CancelLessonManager;
import manager.CheckNameManager;
import manager.ChooseLessonManager;
import manager.FinaliseBookingManager;
import manager.LoginManager;
import manager.LogoutManager;
import manager.ViewTimetableManager;

/**
 * Factory class to return the appropriate ActionManager implementation for the ControllerAction type
 * @author Daniel
 *
 */
public class ManagerFactory {

	public ActionManager getActionManager(ControllerAction action) {
		switch (action) {
		case ADD_USER:
			return new AddUserManager();
		case CANCEL_LESSON:
			return new CancelLessonManager();
		case CHECK_NAME:
			return new CheckNameManager();
		case CHOOSE_LESSON:
			return new ChooseLessonManager();
		case FINALISE_BOOKING:
			return new FinaliseBookingManager();
		case LOGIN:
			return new LoginManager();
		case LOGOUT:
			return new LogoutManager();
		case VIEW_TIMETABLE:
			return new ViewTimetableManager();
		default:
			SystemLogger.finer("The action type of %s does not have an ActionManager", action);
			return null;
		}
	}
}
