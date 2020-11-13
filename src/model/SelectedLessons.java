package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Holder object for the lessons the user has selected for booking
 * @author Daniel
 *
 */
public class SelectedLessons implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7859874356116523070L;
	
	private Map<String, Lesson> selectedLessonsCollection = new HashMap<>();
	
	public void addSelectedLesson(Lesson selectedLesson) {
		selectedLessonsCollection.put(selectedLesson.getLessonID(), selectedLesson);
	}
	
	public void addSelectedLessons(Collection<Lesson> selectedLessons) {
		for (Lesson selectedLesson : selectedLessons) {
			addSelectedLesson(selectedLesson);
		}
	}
	
	public void removeLessonByID(String lessonID) {
		selectedLessonsCollection.remove(lessonID);
	}
	
	public Map<String, Lesson> getSelectedLessonsCollection() {
		return selectedLessonsCollection;
	}
}
