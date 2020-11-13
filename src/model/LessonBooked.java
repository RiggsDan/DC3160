package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Holder class representing a single row from the 'lessons_booked' table
 * @author Daniel
 *
 */
public class LessonBooked {

	private int clientID;
	private String lessonID;
	
	/**
	 * Creates the LessonBooked object based on the record the pointer is currently set to on the ResultSet
	 * @param resultSet
	 * @throws SQLException
	 */
	public LessonBooked(ResultSet resultSet) throws SQLException {
		clientID = resultSet.getInt(Column.CLIENT_ID.getColumnName());
		lessonID = resultSet.getString(Column.LESSON_ID.getColumnName());
	}
	
	/**
	 * @return the ID of this record from the database
	 */
	public int getClientID() {
		return clientID;
	}
	
	/**
	 * @return the lessonID of this record from the database
	 */
	public String getLessonID() {
		return lessonID;
	}
	
	public String toString() {
		return String.format("LessonBooked object with details: [clientID=%s], [lessonID=%s]", clientID, lessonID);
	}
	
	
	public enum Column {
		CLIENT_ID("clientid"),
		LESSON_ID("lessonid");
		
		private String columnName;
		
		private Column(String columnName) {
			this.columnName = columnName;
		}
		
		public String getColumnName() {
			return columnName;
		}
	}
}
