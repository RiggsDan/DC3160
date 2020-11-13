package controller.constants;

/**
 * Enum representing the various attributes the system sets on the session
 * @author Daniel
 *
 */
public enum SessionAttribute {
	USER("user"),
	LESSONS("lessons"),
	SELECTED_LESSONS("selected_lessons");
	
	private String attributeKey;
	
	private SessionAttribute(String attributeKey) {
		this.attributeKey = attributeKey;
	}
	
	public String getAttributeKey() {
		return attributeKey;
	}
}
