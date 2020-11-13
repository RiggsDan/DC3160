package manager;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.constants.SessionAttribute;
import dao.DAOFactory;
import logging.SystemLogger;
import manager.factory.ActionManager;
import model.Client;
import model.SelectedLessons;

/**
 * Class to handle the finalisation of the lesson booking functionality of the
 * system
 * 
 * @author Daniel
 *
 */
public class FinaliseBookingManager implements ActionManager {

	@Override
	public boolean handleAction(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);

		// Get the selected lessons holder from the session
		SelectedLessons selectedLessons = (SelectedLessons) session
				.getAttribute(SessionAttribute.SELECTED_LESSONS.getAttributeKey());

		try (DAOFactory daoFactory = new DAOFactory()) {
			Client client = daoFactory.getClientsDAO().getByUsername((String) session.getAttribute(SessionAttribute.USER.getAttributeKey()));

			daoFactory.getLessonsBookedDAO().bookLessonsForClient(selectedLessons.getSelectedLessonsCollection().values(),client.getClientID());

			return true;
		} catch (SQLException e) {
			SystemLogger.severe("An excpetion occured whilst finalising the bookking of the user, reason was %s", e.getMessage());
			return false;
		}
	}
}
