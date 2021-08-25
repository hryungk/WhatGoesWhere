package com.hyunryungkim.whatgoeswhere.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hyunryungkim.whatgoeswhere.exceptions.CredentialAlreadyExistsException;
import com.hyunryungkim.whatgoeswhere.exceptions.CredentialNotFoundException;
import com.hyunryungkim.whatgoeswhere.models.Credential;
import com.hyunryungkim.whatgoeswhere.models.Item;
import com.hyunryungkim.whatgoeswhere.models.User;
import com.hyunryungkim.whatgoeswhere.services.CredentialService;
import com.hyunryungkim.whatgoeswhere.services.UserService;

/**
 * Spring MVC User Controller class
 * Mappings that involve Email service
 * 
 * @author Hyunryung Kim
 *
 */
@Controller
public class UserController {
	/**
	 * Service object for User model
	 */
	private UserService userService;
	/**
	 * Service object for Credential model
	 */
	private CredentialService credentialService;
	
	/**
	 * Class constructor binding various service classes
	 * 
	 * @param userService a service object for User model
	 * @see com.hyunryungkim.whatgoeswhere.services.UserService
	 * @see com.hyunryungkim.whatgoeswhere.services.CredentialService
	 */
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
		this.credentialService = ControllerUtilities.credentialService;
	}
	
	/**
	 * Maps post method for registerNewUser request
	 * 
	 * @param username the username of the new credential
	 * @param password the password of the new credential
	 * @param email the email of the new user
	 * @param firstName the first name of the new user
	 * @param lastName the last name of the new user
	 * @param model a Model object holding model attributes
	 * @param request a HttpServletRequest object to automatically log the user in after registration
	 * @param ra a RedirectAttributes object that holds flash attributes 
	 * @return the JSP name for the profile page if no exception is caught, the register page otherwise
	 */
	@PostMapping("/registerNewUser")
	public String addUser(@RequestParam("userName") String username, @RequestParam("password") String password, @RequestParam("eMail") String email, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, Model model, HttpServletRequest request, RedirectAttributes ra) {
		User newUser = new User(email, firstName, lastName, LocalDate.now(), new ArrayList<>());
		Credential credential = new Credential(username, password, newUser);
		try {	
			credentialService.add(credential);
//			model.addAttribute("user", newUser);
			ControllerUtilities.authWithHttpServletRequest(request, username, password); // login automatically
			return showProfilePage(model);
		} catch (CredentialAlreadyExistsException e) {
			ra.addFlashAttribute(ControllerUtilities.USERNAME_MESSAGE_ATTRIBUTE, e.getMessage());
			if (userService.findByEmail(email) != null) {
				String invalidEmailMessage = "The email address " + email + " is already registered. Choose a different one.";				
				ra.addFlashAttribute(ControllerUtilities.EMAIL_MESSAGE_ATTRIBUTE, invalidEmailMessage);
				newUser.setEmail("");
			}			
			ra.addFlashAttribute(ControllerUtilities.USER_ATTRIBUTE, newUser);
			ra.addFlashAttribute(ControllerUtilities.USERNAME_ATTRIBUTE, "");
			return "redirect:/"+PageName.REGISTER.getValue();
		} catch (RollbackException e) {
			String invalidEmailMessage = "The email address " + email + " is already registered. Choose a different one.";
			ra.addFlashAttribute(ControllerUtilities.EMAIL_MESSAGE_ATTRIBUTE, invalidEmailMessage);
			newUser.setEmail("");
			ra.addFlashAttribute(ControllerUtilities.USER_ATTRIBUTE, newUser);
			ra.addFlashAttribute(ControllerUtilities.USERNAME_ATTRIBUTE, username);
			return "redirect:/"+PageName.REGISTER.getValue();
		}
	}
	
	/**
	 * Maps get method for profile page
	 * This page is not accessible without a valid credential so the exception is not expected to be thrown.
	 * 
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the profile page if no exception is caught, the login page otherwise 
	 */
	@GetMapping("/profile")
	public String showProfilePage(Model model) {				
		try {
			Credential credential = ControllerUtilities.getCredential();
			User user = ControllerUtilities.getUser();
			model.addAttribute("user",user);
			model.addAttribute("userName", credential.getUsername());
			List<Item> items = user.getItems();
			model.addAttribute(ControllerUtilities.ITEMS_ATTRIBUTE, items);
			return PageName.PROFILE.getValue();
		} catch (CredentialNotFoundException e) {
			model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE, e.getMessage()+" before accessing your profile page.");
			return "forward:/"+PageName.LOGIN.getValue();
		}
	}
	
	/**
	 * Maps get method for the deleteUser page
	 * Sends a model attribute email of the current user. 
	 * This page is not accessible without a valid credential so the exception is not expected to be thrown. 
	 * 
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the deleteUser page if no exception is caught, the login page otherwise
	 */
	@GetMapping("/deleteUser")
	public String showDeleteUserPage(Model model) {		
		try {
			model.addAttribute(ControllerUtilities.EMAIL_ATTRIBUTE, ControllerUtilities.getUserEmail());
			return PageName.DELETE_USER.getValue();
		} catch (CredentialNotFoundException e) {
			model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE, e.getMessage()+" before deleting your account.");
			return "forward:/"+PageName.LOGIN.getValue();
		}
	}
}
