package model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Holder class representing a single row from the 'lessons' table
 * @author Daniel
 *
 */
public class Lesson implements Serializable{
    
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -2353982582581949346L;
	
	private static final String DATE_FORMAT = "E, dd MMM, yyyy";
	private static final String TIME_FORMAT = "kk:mm";
	
	private String description;
    private String startTime;
    private String date;
    private String endTime;
    private int level;
    private String lessonID;
    
    /**
	 * Creates the Client object based on the record the pointer is currently set to on the ResultSet
	 * @param resultSet
	 * @throws SQLException
	 */
	public Lesson(ResultSet resultSet) throws SQLException {
		description = resultSet.getString(Column.DESCRIPTION.getColumnName());
		level = resultSet.getInt(Column.LEVEL.getColumnName());
		
		Date startTimeDate = resultSet.getTimestamp(Column.START_DATE_TIME.getColumnName());
		
		SimpleDateFormat dateformatter = new SimpleDateFormat(DATE_FORMAT);
        date = dateformatter.format(startTimeDate);
       
        SimpleDateFormat timeformatter = new SimpleDateFormat(TIME_FORMAT);
        startTime = timeformatter.format(startTimeDate);
        	
        Date endTimeDate = resultSet.getTimestamp(Column.END_DATE_TIME.getColumnName());
		endTime = timeformatter.format(endTimeDate);
		lessonID = resultSet.getString(Column.LESSON_ID.getColumnName());
	}
    
     /* Getter methods for all the properties of the object.
     * At the moment, this class has no setters.
     */
    public String getLessonID() {
        return lessonID;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return this.date;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {  
            return true;  
        }  
        
        if (otherObject instanceof Lesson) {  
        	return (this.description == ((Lesson) otherObject).description &&
        	this.lessonID == ((Lesson) otherObject).lessonID &&
        	this.date == ((Lesson) otherObject).date &&
        	this.startTime == ((Lesson) otherObject).startTime &&
        	this.endTime == ((Lesson) otherObject).endTime &&
        	this.level == ((Lesson) otherObject).level);
        } else {
        	return false;
        }
    	
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(description, lessonID, date, startTime, endTime, level);
    }
    
    public enum Column {
		DESCRIPTION("description"),
		LEVEL("level"),
		START_DATE_TIME("startDateTime"),
		END_DATE_TIME("endDateTime"),
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
