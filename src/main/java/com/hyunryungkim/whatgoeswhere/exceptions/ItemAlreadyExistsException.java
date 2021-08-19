package com.hyunryungkim.whatgoeswhere.exceptions;

/**
 * Exception class thrown when an Item already exists in the system
 * 
 * @author Hyunryung Kim
 * @see com.hyunryungkim.whatgoeswhere.models.Item
 */
public class ItemAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Class constructor specifying the exception message
	 * 
	 * @param message a string containing the error message
	 */
	public ItemAlreadyExistsException(String message) {
		super(message);
	}	
	/**
	 * Class constructor specifying the error message with a name and condition of the item
	 * Passes the message to the other constructor.
	 * 
	 * @param name a string containing the name of the item
	 * @param condition a string containing the condition of the item
	 */
	public ItemAlreadyExistsException(String name, String condition) {
		this("An item " + name + (condition.equals("") ? "" : " (" + condition +")") + " already exists in the list");
	}
	
}
