package manager.factory;

/**
 * Enum representing the various end-points on the controller that have logic setup (Accessed under /do/*)
 * @author Daniel
 *
 */
public enum ControllerAction {
	LOGIN("/login", false),
	LOGOUT("/logout", true),
	ADD_USER("/addUser", false),
	VIEW_TIMETABLE("/viewTimetable", true),
	CHOOSE_LESSON("/chooseLesson", true),
	CANCEL_LESSON("/cancelLesson", true),
	VIEW_SELECTION("/viewSelection", true),
	FINALISE_BOOKING("/finaliseBooking", true),
	CHECK_NAME("/checkName", false);
	
	private String action;
	private boolean requiresSession;
	
	private ControllerAction(String action, boolean requiresSession) {
		this.action = action;
		this.requiresSession = requiresSession;
	}
	
	public String getAction() {
		return action;
	}
	
	public boolean requiresSession() {
		return requiresSession;
	}
	
	/**
	 * Returns the enum value associated with the action passed in
	 * @param action
	 * @return
	 */
	public static ControllerAction getByAction(String action) {
		// Loop over all the ControllerActions possible
		for (ControllerAction controllerActionToCheck : ControllerAction.values()) {
			
			// If the action of the enum match the enum passed in then return it
			if (controllerActionToCheck.getAction().equals(action)) {
				return controllerActionToCheck;
			}
		}
		
		// This is the case for when the action passed in is unexpected
		throw new IllegalArgumentException(String.format("An unexpected action of %s was passed in", action));
	}
}
