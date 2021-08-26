package com.hyunryungkim.whatgoeswhere.controllers;

import java.util.Collection;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.hyunryungkim.whatgoeswhere.exceptions.CredentialNotFoundException;
import com.hyunryungkim.whatgoeswhere.models.Credential;
import com.hyunryungkim.whatgoeswhere.models.User;
import com.hyunryungkim.whatgoeswhere.services.CredentialService;

/**
 * Utility class for Controllers
 * Includes name of model attributes
 * Methods to retrieve current user info
 * 
 * @author Hyunryung Kim
 *
 */
@Controller
public class ControllerUtilities {	
	/**
	 * Service object for Credential model
	 */
	static CredentialService credentialService;
	/**
	 * Attribute name for any message of errors
	 */
	static final String MESSAGE_ATTRIBUTE = "message";
	/**
	 * Attribute name for a collection of Item objects
	 */
	static final String ITEMS_ATTRIBUTE = "items";
	/**
	 * Attribute name for a User object
	 */
	static final String USER_ATTRIBUTE = "user";
	/**
	 * Attribute name for a username string
	 */
	static final String USERNAME_ATTRIBUTE = "username";
	/**
	 * Attribute name for the client's email address
	 */
	static final String EMAIL_ATTRIBUTE = "email";
	/**
	 * Attribute name for the error message with email 
	 */
	static final String EMAIL_MESSAGE_ATTRIBUTE = "emailMessage";
	/**
	 * Attribute name for the error message with username
	 */
	static final String USERNAME_MESSAGE_ATTRIBUTE = "usernameMessage";
	/**
	 * Logger object for login and out
	 */
	private static Logger loggerUtilities;
	/**
	 * Class constructor binding various service classes
	 * Only constructor autowiring works. 
	 * 
	 * @param credentialService a service object for Credential model
	 */
	@Autowired
	private ControllerUtilities(CredentialService credentialService) {
		ControllerUtilities.credentialService = credentialService;
		ControllerUtilities.loggerUtilities = setupLogger(ControllerUtilities.class.getName());
	}
	
	/**
	 * Returns the current user's Credential 
	 * 
	 * @return a Credential object of the current user if found, null otherwise 
	 * @throws CredentialNotFoundException If a valid Credential is not found
	 * @see com.hyunryungkim.whatgoeswhere.models.Credential
	 */
	public static Credential getCredential() throws CredentialNotFoundException {		
		// Get current user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new CredentialNotFoundException();
		} else {
			Object principal = authentication.getPrincipal();
			boolean isAnonymous = principal.equals("anonymousUser");
			if (isAnonymous) {
				throw new CredentialNotFoundException();
			} else {
				// Retrieves current user granted authorities
				String username =  ((UserDetails)principal).getUsername();
				return credentialService.findByUsername(username);
			}
		}
	}
	/**
	 * Returns the current user's role
	 * 
	 * @return a string containing the role of current user
	 */
	public static String getRole() {
		// Get current user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return "";
		} else {
			Object principal = authentication.getPrincipal();
			boolean isAnonymous = principal.equals("anonymousUser");
			if (isAnonymous) {
				return "";
			} else {
				// Print current user granted authorities
				Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
				return authorities.toString();
			}
		}
	}
	
	/**
	 * Returns the User object of the current user
	 * 
	 * @return the User object of the current user
	 * @throws CredentialNotFoundException If a valid Credential is not found
	 */
	public static User getUser() throws CredentialNotFoundException {
		Credential credential = getCredential();		
		return credential.getUser();
	}	
	
	/**
	 * Returns the email address of the current user
	 * 
	 * @return the email address of the current user
	 * @throws CredentialNotFoundException If a valid Credential is not found
	 */
	public static String getUserEmail() throws CredentialNotFoundException {
		User user = getUser();
		return user.getEmail();
	}
	
	/**
	 * Returns a logger for the provided class name
	 * 
	 * @param className a string containing the name of calling class
	 * @return a Logger object for the calling class
	 */
	public static Logger setupLogger(String className) {
		Logger logger = Logger.getLogger(className);
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.INFO);
		Handler ch = new ConsoleHandler();
		logger.addHandler(ch);
		return logger;
	}
	
	
	/**
	 * Logs out the current user from the request
	 * 
	 * @param request a HttpServletRequest object to automatically log the user out after registration
	 */
	static void logoutWithHttpServletRequest(HttpServletRequest request) {
		try {
			request.logout();
		} catch (ServletException e) {
			loggerUtilities.log(Level.WARNING, "Error while logout ", e);
		}
	}
	
	/**
	 * Logs in a new user with provided credentials
	 * 
	 * @param request a HttpServletRequest object to automatically log the user in after registration
	 * @param username a string of username from the user input
	 * @param password a string of password from the user input
	 */
	static void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
	    try {
	        request.login(username, password);
	    } catch (ServletException e) {
	        loggerUtilities.log(Level.WARNING, "Error while login ", e);
	    }
	}
}
