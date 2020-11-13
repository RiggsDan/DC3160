package manager.factory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface representing the methods available to any handlers of the system actions
 * @author Daniel
 *
 */
public interface ActionManager {

	/**
	 * Method to do the work associated with the action type of the action manager
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean handleAction(HttpServletRequest request, HttpServletResponse response);
}
