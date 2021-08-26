package com.hyunryungkim.whatgoeswhere.controllers;

public enum PageName {
	HOME("index"), ABOUT("about"), ACCESS_DENIED("access_denied"), ADD_ITEM("add_item"), ADMIN("admin"),
	CONTACT_SUCCESS("contact_success"), CONTACT("contact"), DELETE_USER("delete_user"), EDIT_ITEM("edit_item"),
	ERROR("error"), LIST("list"), LOGIN("login"), LOGOUT_SUCCESS("logout_success"), PROFILE("profile"), 
	REGISTER("register"), UPDATE_PASSWORD("update_password"), LOGOUT("logout");
	
	private String value;
	
	private PageName(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
