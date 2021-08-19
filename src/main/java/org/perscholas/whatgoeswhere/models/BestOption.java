package org.perscholas.whatgoeswhere.models;

/**
 * Enumeration class containing best options for waste disposal
 * This is provided to the user to choose from when adding/updating an Item 
 * 
 * @author Hyunryung Kim
 * @see org.perscholas.whatgoeswhere.models.Item
 *
 */
public enum BestOption {
	COMPOSTING("Food & Yard Waste"),
	RECYCLING("Recycling"),
	GARBAGE("Garbage"),
	DROPOFF("Drop-off at local facilities");
	
	/**
	 * String value to the enum
	 */
	private String value;
	
	/**
	 * Class constructor specifying a value of this enum
	 * 
	 * @param value a string value to assign to this enum
	 */
	BestOption(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
