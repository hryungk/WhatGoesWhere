package org.perscholas.whatgoeswhere.exceptions;

public class ItemAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ItemAlreadyExistsException() {
		super();
	}
	
	public ItemAlreadyExistsException(String message) {
		super(message);
	}
	
	public ItemAlreadyExistsException(String name, String condition) {
		this("An item " + name + (condition.equals("") ? "" : " (" + condition +")") + " already exists in the list");
		
	}
	
}
