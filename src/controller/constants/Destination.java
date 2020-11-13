package controller.constants;

/**
 * Enum representing the various destination pages of the system
 * @author Daniel
 *
 */
public enum Destination {
	LESSON_TIMETABLE_VIEW("/LessonTimetableView.jspx"),
	LESSON_SELECTION_VIEW("/LessonSelectionView.jspx");
	
	private String url;
	
	private Destination(String url) {
		this.url = url;
	}
	
	public String getURL() {
		return url;
	}
}
