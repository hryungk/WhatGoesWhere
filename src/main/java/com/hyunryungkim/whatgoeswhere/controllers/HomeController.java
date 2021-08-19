package com.hyunryungkim.whatgoeswhere.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.RollbackException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyunryungkim.whatgoeswhere.exceptions.CredentialAlreadyExistsException;
import com.hyunryungkim.whatgoeswhere.exceptions.CredentialNotFoundException;
import com.hyunryungkim.whatgoeswhere.exceptions.ItemAlreadyExistsException;
import com.hyunryungkim.whatgoeswhere.models.BestOption;
import com.hyunryungkim.whatgoeswhere.models.Credential;
import com.hyunryungkim.whatgoeswhere.models.Item;
import com.hyunryungkim.whatgoeswhere.models.User;
import com.hyunryungkim.whatgoeswhere.services.CredentialService;
import com.hyunryungkim.whatgoeswhere.services.ItemService;
import com.hyunryungkim.whatgoeswhere.services.UserService;

/**
 * Spring MVC Controller class
 * Connects the JSP and model
 * 
 * @author Hyunryung Kim
 *
 */
@Controller
public class HomeController {
	/**
	 * Service object for Credential model
	 */
	private CredentialService credentialService;
	/**
	 * Service object for User model
	 */
	private UserService userService;
	/**
	 * Service object for Item model
	 */
	private ItemService itemService;
	/**
	 * Logger for logging errors when signing in and out.
	 */
	private Logger logger = Logger.getLogger(HomeController.class.getName());
	/**
	 * Attribute name for any message of errors
	 */
	private final String MESSAGE_ATTRIBUTE = "message";
	/**
	 * Attribute name for a collection of Item objects
	 */
	private final String ITEMS_ATTRIBUTE = "items";
	/**
	 * Attribute name for the client's email address
	 */
	private final String EMAIL_ATTRIBUTE = "email";
	/**
	 * JSP name for the main page
	 */
	private final String HOME_PAGE = "index";
	/**
	 * Class constructor binding various service classes
	 * 
	 * @param itemService a service object for Item model
	 * @param userService a service object for User model
	 * @param credentialService a service object for Credential model
	 * @see com.hyunryungkim.whatgoeswhere.services.ItemService
	 * @see com.hyunryungkim.whatgoeswhere.services.UserService
	 * @see com.hyunryungkim.whatgoeswhere.services.CredentialService
	 */
	@Autowired
	public HomeController(ItemService itemService, UserService userService, CredentialService credentialService) {
		this.itemService = itemService;
		this.userService = userService;
		this.credentialService = credentialService;
	}
	
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
		return HOME_PAGE; // return this to WebAppconfig
	}	
	/**
	 * Maps post method for the find request from the index page
	 * It adds the items to the model attribute to display the search result in the index page.
	 * 
	 * @param itemName the item's name submitted from the client
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the index page
	 */
	@PostMapping("/find") // Match the form's action name
	public String searchItemName(@RequestParam("itemName") String itemName,			
			Model model) {	
		List<Item> items = itemService.findByName(itemName);
		model.addAttribute(ITEMS_ATTRIBUTE, items);
		return HOME_PAGE;
	}
	
	/**
	 *  Maps get method for the list page
	 *  It grabs all items from the database and adds to the model attribute to display in the list page.
	 *  
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the list page
	 */
	@GetMapping("/list")
	public String showListPage(Model model) {
		List<Item> items = itemService.getAll();
		model.addAttribute(ITEMS_ATTRIBUTE, items);
		return "list";
	}
	
	/**
	 * Maps get method for the Add Item page
	 * It passes 3 model attributes:
	 * - message: for displaying any error message
	 * - item: to populate user input when redirected
	 * - BestOption: for bestOption select field) attributes to the JSP page.
	 * 
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the Add Item page
	 * @see com.hyunryungkim.whatgoeswhere.models.BestOption
	 */
	@GetMapping("/addItem")
	public String showAddItemPage(Model model) {
		if (model.getAttribute(MESSAGE_ATTRIBUTE) == null) {
			model.addAttribute(MESSAGE_ATTRIBUTE, "");
		}
		// Pass an Item object
		model.addAttribute("item", new Item());
		
		// Pass the BestOption enum values
		model.addAttribute("bestOptions", BestOption.values());
		
		return "add_item";
	}
	/**
	 * Maps post method for the addItem request 
	 * 
	 * @param item an Item object passed from the add_item JSP containing user input
	 * @param model a Model object holding model attributes
	 * @param errors result holder for DataBinder, capable of error registration
	 * @return the JSP name for the list page if no exception is caught, the Add Item page otherwise
	 * @see com.hyunryungkim.whatgoeswhere.models.Item
	 * @see org.springframework.validation.DataBinder
	 */
	@PostMapping("/addItem")
	public String addItem(@ModelAttribute("item") Item item, Model model, BindingResult errors) {
		if (errors.hasErrors()) {
			return "add_item";			
		}		
		try {
			User user = getUser();
			item.setAddedDate(LocalDateTime.now());	
			itemService.add(item, user.getId());
			return showListPage(model);
		} catch (CredentialNotFoundException e) {
			model.addAttribute(MESSAGE_ATTRIBUTE, e.getMessage()+" before adding an item.");
			return showAddItemPage(model);
		} catch (ItemAlreadyExistsException e) {
			model.addAttribute(MESSAGE_ATTRIBUTE,e.getMessage());
			return showAddItemPage(model);
		 }
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
		if (model.getAttribute(MESSAGE_ATTRIBUTE) == null) {
			model.addAttribute(MESSAGE_ATTRIBUTE, "");
		}
		return "login";
	}
//	@PostMapping("/performLogin")
//	public String logIn(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpServletRequest request) {
//		try {
//			Credential credential = credentialService.findByUsernameAndPassword(username, password);
//			User user = credential.getUser();
//			authWithHttpServletRequest(request, username, password); // login 	
//			return showProfilePage(model, session);
//		} catch (CredentialNotFoundException e) {
//			String message = e.getMessage();
//			model.addAttribute(MESSAGE_ATTRIBUTE, message);
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
		if (model.getAttribute(MESSAGE_ATTRIBUTE) == null) {
			model.addAttribute(MESSAGE_ATTRIBUTE, "");
		}
		if (model.getAttribute("user") == null) {
			User newUser = new User();
			model.addAttribute("user", newUser);
		}
		return "register";
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
	 * @return the JSP name for the profile page if no exception is caught, the register page otherwise
	 */
	@PostMapping("/registerNewUser")
	public String addUser(@RequestParam("userName") String username, @RequestParam("password") String password, @RequestParam("eMail") String email, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, Model model, HttpServletRequest request) {
		User newUser = new User(email, firstName, lastName, LocalDate.now(), new ArrayList<>());
		Credential credential = new Credential(username, password, newUser);
		try {	
			credentialService.add(credential);
			model.addAttribute("user", newUser);
			authWithHttpServletRequest(request, username, password); // login automatically
			return showProfilePage(model);
		} catch (CredentialAlreadyExistsException e) {
			model.addAttribute("usernameMessage", e.getMessage());
			if (userService.findByEmail(email) != null) {
				String invalidEmailMessage = "The email address " + email + " is already registered. Choose a different one.";				
				model.addAttribute("emailMessage", invalidEmailMessage);					
				newUser.setEmail("");
			}			
			model.addAttribute("user", newUser); // to populate the form
			model.addAttribute("username", "");
			return showRegisterPage(model);
		} catch (RollbackException e) {
			String invalidEmailMessage = "The email address " + email + " is already registered. Choose a different one.";
			model.addAttribute("emailMessage", invalidEmailMessage);	
			newUser.setEmail("");
			model.addAttribute("user", newUser); // to populate the form
			model.addAttribute("username", username);
			return showRegisterPage(model);
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
			Credential credential = getCredential();
			User user = getUser();
			model.addAttribute("user",user);
			model.addAttribute("userName", credential.getUsername());
			List<Item> items = user.getItems();
			model.addAttribute(ITEMS_ATTRIBUTE, items);
			return "profile";
		} catch (CredentialNotFoundException e) {
			model.addAttribute(MESSAGE_ATTRIBUTE, e.getMessage()+" before accessing your profile page.");
			return showLogInPage(model);
		}
	}

	/**
	 * Maps get method for the editItem page
	 * Passes two model attributes
	 * - item: an Item object to edit
	 * - bestOptions: a collection of BestOption enum values for select input  
	 * 
	 * @param itemId the id number of the item to be edited
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the editItem page
	 * @see com.hyunryungkim.whatgoeswhere.models.Item
	 * @see com.hyunryungkim.whatgoeswhere.models.BestOption
	 */
	@GetMapping("/editItem")
	public String showEditItemPage(@RequestParam("itemId") int itemId, Model model) {		
		Item item = itemService.findById(itemId);
		model.addAttribute("item", item);
		// Pass the BestOption enum values
		model.addAttribute("bestOptions", BestOption.values());
		return "edit_item";
	}
	/**
	 * Maps post method for the editItem request 
	 * 
	 * @param uitem an item with updated data from user input
	 * @param model a Model object holding model attributes
	 * @param errors result holder for DataBinder, capable of error registration
	 * @return the JSP name for the profile page if no exception is caught, the editItem page otherwise
	 * @see com.hyunryungkim.whatgoeswhere.models.Item
	 * @see org.springframework.validation.DataBinder
	 */
	@PostMapping("/editItem")
	public String editItem(@ModelAttribute("item") Item uitem,  Model model, BindingResult errors) {
		if (errors.hasErrors()) {
			return "edit_item";
		}		
		try {
			itemService.update(uitem);
			return showProfilePage(model);
		} catch(ItemAlreadyExistsException e) {
			model.addAttribute(MESSAGE_ATTRIBUTE,e.getMessage());
			return showEditItemPage(uitem.getId(), model);
		}
	}
	
	/**
	 * Maps post method for the deleteItem request
	 * 
	 * @param id the id number of the item to be deleted
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the profile page
	 */
	@PostMapping("/deleteItem")
	public String deleteItem(@RequestParam("itemId") int id, Model model) {
		itemService.delete(id);		
		return showProfilePage(model);
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
			model.addAttribute(EMAIL_ATTRIBUTE, getUserEmail());
			return "delete_user";
		} catch (CredentialNotFoundException e) {
			model.addAttribute(MESSAGE_ATTRIBUTE, e.getMessage()+" before deleting your account.");
			return showLogInPage(model);
		}
	}
	/**
	 * Maps post method for the deleteUser request
	 * This page is not accessible without a valid credential so the exception is not expected to be thrown. 
	 * 
	 * @param message a string of message from the user input
	 * @param model a Model object holding model attributes
	 * @param request a HttpServletRequest object to automatically log the user out after deleting its account
	 * @return the JSP name for the main page if no exception is caught, the login page otherwise
	 */
	@PostMapping("/deleteUser")
	public String deleteUser(@RequestParam(MESSAGE_ATTRIBUTE) String message, Model model, HttpServletRequest request) {
		try {
			Credential credential = getCredential();
			credentialService.delete(credential);
			logoutWithHttpServletRequest(request);
			
			// Send email to admin
			System.out.println(message);
			
			return showIndexPage(model);
		} catch (CredentialNotFoundException e) {
			model.addAttribute(MESSAGE_ATTRIBUTE, e.getMessage()+" before deleting your account.");
			return showLogInPage(model);
		}
	}
	
//	@GetMapping("/logout") 
//	public String logout(HttpSession session) {
//		session.invalidate(); // On clicking logout links, the session is to be invalidated.
//		return HOME_PAGE;
//	}

	/**
	 * Maps get method for the logoutSuccess page
	 * 
	 * @return the JSP name for the logoutSuccess page
	 */
	@GetMapping("/logoutSuccess")
	public String showLogoutSuccessPage() {
		return "logout_success";
	}

	/**
	 * Maps get method for the about page
	 * 
	 * @return the JSP name for the about page
	 */
	@GetMapping("/about")
	public String showAboutPage() {
		return "about";
	}
	
	/**
	 * Maps get method for the contact page
	 * Sends a model attribute email of the current user. 
	 * 
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the contact page
	 */
	@GetMapping("/contact")
	public String showContactPage(Model model) {
		try {
			String email = getUserEmail();
			model.addAttribute(EMAIL_ATTRIBUTE, email);
			return "contact";
		} catch (CredentialNotFoundException e) {
			model.addAttribute(EMAIL_ATTRIBUTE, "");
			return "contact";
		}
	}
	/**
	 * Maps post method for the contact request	 * 
	 * 
	 * @param message a string of message from the user input
	 * @param email an email address from the user input
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the main page
	 */
	@PostMapping("/contact")
	public String contact(@RequestParam(MESSAGE_ATTRIBUTE) String message, @RequestParam("eMail") String email, Model model) {
		System.out.println("email: " + email);
		System.out.println("message: " + message);
		
		// Send email to admin
		
		
		return HOME_PAGE;
	}

	/**
	 * Maps get method for the admin page
	 * 
	 * @return the JSP name for the admin page
	 */
	@GetMapping("/admin")
	public String showAdminPage() {
		return "admin";
	}
	
	/**
	 * Maps get method for the accessDenied page
	 * 
	 * @return the JSP name for the accessDenied page
	 */
	@GetMapping("/accessDenied")
	public String showAccessDeniedPage() {
		return "access_denied";
	}
	
	/**
	 * Logs out the current user from the request
	 * 
	 * @param request a HttpServletRequest object to automatically log the user out after registration
	 */
	private void logoutWithHttpServletRequest(HttpServletRequest request) {
		try {
			request.logout();
		} catch (ServletException e) {
			logger.log(Level.WARNING, "Error while logout ", e);
		}
		
	}
	
	/**
	 * Logs in a new user with provided credentials
	 * 
	 * @param request a HttpServletRequest object to automatically log the user in after registration
	 * @param username a string of username from the user input
	 * @param password a string of password from the user input
	 */
	private void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
	    try {
	        request.login(username, password);
	    } catch (ServletException e) {
	        logger.log(Level.WARNING, "Error while login ", e);
	    }
	}
	
	/**
	 * Returns the current user's Credential 
	 * 
	 * @return a Credential object of the current user if found, null otherwise 
	 * @throws CredentialNotFoundException If a valid Credential is not found
	 * @see com.hyunryungkim.whatgoeswhere.models.Credential
	 */
	private Credential getCredential() throws CredentialNotFoundException {		
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
	/**
	 * Returns the User object of the current user
	 * 
	 * @return the User object of the current user
	 * @throws CredentialNotFoundException If a valid Credential is not found
	 */
	private User getUser() throws CredentialNotFoundException {
		Credential credential = getCredential();		
		return credential.getUser();
	}	
	/**
	 * Returns the email address of the current user
	 * 
	 * @return the email address of the current user
	 * @throws CredentialNotFoundException If a valid Credential is not found
	 */
	private String getUserEmail() throws CredentialNotFoundException {
		User user = getUser();
		return user.getEmail();
	}	
}
