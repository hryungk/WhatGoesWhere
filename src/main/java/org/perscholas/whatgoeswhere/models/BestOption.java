package org.perscholas.whatgoeswhere.models;

public enum BestOption {
	Composting("Food & Yard Waste"),
	Recycling("Recycling"),
	Garbage("Garbage"),
	DropOff("Drop-off at local facilities");
	
	private String value;
	
	BestOption(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
