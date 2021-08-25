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
 * 
 * @author Hyunryung Kim
 *
 */
@Controller
public class ServiceUtilities {	
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
	 * Attribute name for the client's email address
	 */
	static final String EMAIL_ATTRIBUTE = "email";
	/**
	 * JSP name for the main page
	 */
	static final String HOME_PAGE = "index";
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
	private ServiceUtilities(CredentialService credentialService) {
		ServiceUtilities.credentialService = credentialService;
		ServiceUtilities.loggerUtilities = setupLogger(ServiceUtilities.class.getName());
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
				// Print current user granted authorities
//				Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//				System.out.println(authorities);
				String username =  ((UserDetails)principal).getUsername();
				return credentialService.findByUsername(username);
			}
		}
	}
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
