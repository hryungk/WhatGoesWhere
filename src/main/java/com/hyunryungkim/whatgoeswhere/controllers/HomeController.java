package com.hyunryungkim.whatgoeswhere.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hyunryungkim.whatgoeswhere.models.Credential;
import com.hyunryungkim.whatgoeswhere.models.User;

/**
 * Spring MVC Controller class
 * Connects the JSP and model
 * Includes public access page mappings
 * 
 * @author Hyunryung Kim
 *
 */
@Controller
public class HomeController {
	/**
	 * Maps get method for the index page
	 * The root location(/) is mapped to the index page.
	 * The returned string is passed to WebAppConfig to add prefix and suffix. 
	 * 
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the index page
	 */
	@GetMapping("/") // This is what you type for URL
	public String showIndexPage(Model model) {
		return PageName.HOME.getValue(); // return this to WebAppconfig
	}	
	
	/**
	 * Maps get method for login page
	 * It passes a message attribute for an error, if any.
	 * 
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the login page
	 */
	@GetMapping("/login")
	public String showLogInPage(Model model) {
		String message = (String) model.asMap().get(ControllerUtilities.MESSAGE_ATTRIBUTE);
		model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE, message);
		if (model.getAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE) == null) {
			model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE, "");
		}
		return PageName.LOGIN.getValue();
	}
//	@PostMapping("/performLogin")
//	public String logIn(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpServletRequest request) {
//		try {
//			Credential credential = credentialService.findByUsernameAndPassword(username, password);
//			User user = ServiceUtilities.getUser();
//			authWithHttpServletRequest(request, username, password); // login 	
//			return showProfilePage(model, session);
//		} catch (CredentialNotFoundException e) {
//			String message = e.getMessage();
//			model.addAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE, message);
//			model.addAttribute("username", username);
//			return showLogInPage(model);
//		}
//	}
	
//	@GetMapping("register/{username}")
//	public String passToRegister(Model model, @PathVariable("username") String username) {		
//		if (username != null) {			
//			System.out.println("passToRegister: " + username);
//		}
//		model.addAttribute("username", username);
//		return "register";
//	}
	
	/**
	 *  Maps get method for register page
	 *  It passes 2 model attributes:
	 * - message: for displaying any error message
	 * - user: to populate user input when redirected
	 * 
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the register page
	 */
	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		User user = (User) model.asMap().get(ControllerUtilities.USER_ATTRIBUTE);
		Credential credential = (Credential) model.asMap().get(ControllerUtilities.CREDENTIAL_ATTRIBUTE);
		String invalidEmailMessage = (String) model.asMap().get(ControllerUtilities.EMAIL_MESSAGE_ATTRIBUTE);
		String usernameMessage = (String) model.asMap().get(ControllerUtilities.USERNAME_MESSAGE_ATTRIBUTE);
		model.addAttribute(ControllerUtilities.USER_ATTRIBUTE, user);
		model.addAttribute(ControllerUtilities.CREDENTIAL_ATTRIBUTE, credential);
		model.addAttribute(ControllerUtilities.EMAIL_MESSAGE_ATTRIBUTE,invalidEmailMessage);
		model.addAttribute(ControllerUtilities.USERNAME_MESSAGE_ATTRIBUTE,usernameMessage);
		
		if (model.getAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE) == null) {
			model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE, "");
		}
		if (model.getAttribute(ControllerUtilities.USER_ATTRIBUTE) == null) {
			User newUser = new User();
			model.addAttribute(ControllerUtilities.USER_ATTRIBUTE, newUser);
		}
		if (model.getAttribute("credential") == null) {
			model.addAttribute("credential", new Credential());
		}
		return PageName.REGISTER.getValue();
	}
	
	/**
	 * Maps get method for the logoutSuccess page
	 * 
	 * @return the JSP name for the logoutSuccess page
	 */
	@GetMapping("/logoutSuccess")
	public String showLogoutSuccessPage() {
		return PageName.LOGOUT_SUCCESS.getValue();
	}

	/**
	 * Maps get method for the about page
	 * 
	 * @return the JSP name for the about page
	 */
	@GetMapping("/about")
	public String showAboutPage() {
		return PageName.ABOUT.getValue();
	}	
	
	/**
	 * Maps get method for the accessDenied page
	 * 
	 * @return the JSP name for the accessDenied page
	 */
	@GetMapping("/accessDenied")
	public String showAccessDeniedPage() {
		return PageName.ACCESS_DENIED.getValue();
	}
	
}
