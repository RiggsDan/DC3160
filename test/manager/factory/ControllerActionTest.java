package manager.factory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ControllerActionTest {

	/**
	 * Positive test case for when the Enum value is returned by the action passed in
	 */
	@Test
	public void testGetByActionLogin() {
		assertEquals(ControllerAction.LOGIN, ControllerAction.getByAction("/login"));
	}
	
	/**
	 * Positive test case for when the Enum value is returned by the action passed in
	 */
	@Test
	public void testGetByActionLogout() {
		assertEquals(ControllerAction.LOGOUT, ControllerAction.getByAction("/logout"));
	}
	
	/**
	 * Positive test case for when the Enum value is returned by the action passed in
	 */
	@Test
	public void testGetByActionAddUser() {
		assertEquals(ControllerAction.ADD_USER, ControllerAction.getByAction("/addUser"));
	}
	
	/**
	 * Positive test case for when the Enum value is returned by the action passed in
	 */
	@Test
	public void testGetByActionViewTimetable() {
		assertEquals(ControllerAction.VIEW_TIMETABLE, ControllerAction.getByAction("/viewTimetable"));
	}
	
	/**
	 * Positive test case for when the Enum value is returned by the action passed in
	 */
	@Test
	public void testGetByActionChooseLesson() {
		assertEquals(ControllerAction.CHOOSE_LESSON, ControllerAction.getByAction("/chooseLesson"));
	}
	
	/**
	 * Positive test case for when the Enum value is returned by the action passed in
	 */
	@Test
	public void testGetByActionCancelLesson() {
		assertEquals(ControllerAction.CANCEL_LESSON, ControllerAction.getByAction("/cancelLesson"));
	}
	
	/**
	 * Positive test case for when the Enum value is returned by the action passed in
	 */
	@Test
	public void testGetByActionViewSelection() {
		assertEquals(ControllerAction.VIEW_SELECTION, ControllerAction.getByAction("/viewSelection"));
	}
	
	/**
	 * Positive test case for when the Enum value is returned by the action passed in
	 */
	@Test
	public void testGetByActionFinaliseBooking() {
		assertEquals(ControllerAction.FINALISE_BOOKING, ControllerAction.getByAction("/finaliseBooking"));
	}
	
	/**
	 * Positive test case for when the Enum value is returned by the action passed in
	 */
	@Test
	public void testGetByActioncheckName() {
		assertEquals(ControllerAction.CHECK_NAME, ControllerAction.getByAction("/checkName"));
	}
	
	/**
	 * Negative test case for when the Enum value is is not found matching the action passed in
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetByActionUnexpectedAction() {
		ControllerAction.getByAction("/unexpectedAction");
	}
	
}
