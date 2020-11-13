package model;

import java.io.Serializable;
import java.util.Set;

/**
 * Holder object for a collection of lessons
 * @author Daniel
 *
 */
public class Lessons implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -951580781946976747L;
	
	private Set<Lesson> allLessons;
	private long readTime;
	
	public Lessons(Set<Lesson> allLessons, long readTime) {
		this.allLessons = allLessons;
		this.readTime = readTime;
	}
	
	public Set<Lesson> getAllLessons() {
		return allLessons;
	}

	public long getReadTime() {
		return readTime;
	}

}
