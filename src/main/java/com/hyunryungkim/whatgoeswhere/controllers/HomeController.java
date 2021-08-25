package com.hyunryungkim.whatgoeswhere.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
	 * Class constructor binding various service classes
	 * 
	 * @param itemService a service object for Item model
	 * @param userService a service object for User model
	 * @see com.hyunryungkim.whatgoeswhere.services.ItemService
	 * @see com.hyunryungkim.whatgoeswhere.services.UserService
	 * @see com.hyunryungkim.whatgoeswhere.services.CredentialService
	 */
	@Autowired
	public HomeController(ItemService itemService, UserService userService) {
		this.itemService = itemService;
		this.userService = userService;
		this.credentialService = ServiceUtilities.credentialService;
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
		return ServiceUtilities.HOME_PAGE; // return this to WebAppconfig
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
		model.addAttribute(ServiceUtilities.ITEMS_ATTRIBUTE, items);
		return ServiceUtilities.HOME_PAGE;
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
		model.addAttribute(ServiceUtilities.ITEMS_ATTRIBUTE, items);
		model.addAttribute("role",ServiceUtilities.getRole());
		
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
		if (model.getAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE) == null) {
			model.addAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE, "");
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
			User user = ServiceUtilities.getUser();
			item.setAddedDate(LocalDateTime.now());	
			itemService.add(item, user.getId());
			return showListPage(model);
		} catch (CredentialNotFoundException e) {
			model.addAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE, e.getMessage()+" before adding an item.");
			return showAddItemPage(model);
		} catch (ItemAlreadyExistsException e) {
			model.addAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE,e.getMessage());
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
		if (model.getAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE) == null) {
			model.addAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE, "");
		}
		return "login";
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
		if (model.getAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE) == null) {
			model.addAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE, "");
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
			ServiceUtilities.authWithHttpServletRequest(request, username, password); // login automatically
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
			Credential credential = ServiceUtilities.getCredential();
			User user = ServiceUtilities.getUser();
			model.addAttribute("user",user);
			model.addAttribute("userName", credential.getUsername());
			List<Item> items = user.getItems();
			model.addAttribute(ServiceUtilities.ITEMS_ATTRIBUTE, items);
			return "profile";
		} catch (CredentialNotFoundException e) {
			model.addAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE, e.getMessage()+" before accessing your profile page.");
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
	public String showEditItemPage(@RequestParam("itemId") int itemId, @RequestParam("pageName") String pageName, Model model) {		
		Item item = itemService.findById(itemId);
		model.addAttribute("item", item);
		// Pass the BestOption enum values
		model.addAttribute("bestOptions", BestOption.values());
		model.addAttribute("pageName", pageName);
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
	public String editItem(@ModelAttribute("item") Item uitem, @RequestParam("pageName") String pageName,  Model model, BindingResult errors) {
		if (errors.hasErrors()) {
			return "edit_item";
		}		
		try {
			itemService.update(uitem);
			if (pageName.equals("profile"))
				return showProfilePage(model);
			else if (pageName.equals("list"))
				return showListPage(model);
			else
				return showProfilePage(model);
		} catch(ItemAlreadyExistsException e) {
			model.addAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE,e.getMessage());
			return showEditItemPage(uitem.getId(), pageName, model);
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
	public String deleteItem(@RequestParam("itemId") int id, @RequestParam("pageName") String pageName, Model model) {
		itemService.delete(id);		
		if (pageName.equals("profile"))
			return showProfilePage(model);
		else if (pageName.equals("list"))
			return showListPage(model);
		else
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
			model.addAttribute(ServiceUtilities.EMAIL_ATTRIBUTE, ServiceUtilities.getUserEmail());
			return "delete_user";
		} catch (CredentialNotFoundException e) {
			model.addAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE, e.getMessage()+" before deleting your account.");
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
	 * Maps get method for the admin page
	 * 
	 * @return the JSP name for the admin page
	 */
	@GetMapping("/admin")
	public String showAdminPage(Model model) {
		List<Credential> credentials = credentialService.getAll();
		model.addAttribute("credentials", credentials);
		return "admin";
	}
	
	/**
	 * Maps get method for the editUser page
	 * Passes a model attributes
	 * - user: a User object to edit
	 * 
	 * @param userId the id number of the user to be edited
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the editUser page
	 * @see com.hyunryungkim.whatgoeswhere.models.User
	 */
	@GetMapping("/editUser")
	public String showEditUserPage(@RequestParam("userId") int userId, Model model) {		
		User user = userService.findById(userId);
		model.addAttribute("user", user);
		return "edit_user";
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
	@PostMapping("/deleteUserById")
	public String deleteUserById(@RequestParam("userId") int userId, Model model, HttpServletRequest request) {
		try {
			Credential credential =  credentialService.findById(userId);
			credentialService.delete(credential);
			return showAdminPage(model);
		} catch (CredentialNotFoundException e) {
			model.addAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE, e.getMessage()+" as an Admin before deleting a user account.");
			return showLogInPage(model);
		}
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
	 *  Maps get method for update password page
	 * 
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the update_password page
	 */
	@GetMapping("/updatePassword")
	public String showUpdatePasswordPage(Model model) {
		if (model.getAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE) == null) {
			model.addAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE, "");
		}
		return "update_password";
	}
	@PostMapping("/updatePassword")
	public String updatePassword(@RequestParam("oldPassword")String oldPassword, @RequestParam("newPassword")String newPassword, Model model) {
		try {
			Credential credential = ServiceUtilities.getCredential();
			if (credentialService.checkIfValidOldPassword(oldPassword, credential.getPassword())) {
				credential.setPassword(newPassword);
				credentialService.update(credential);
				return showProfilePage(model);
			} else {
				model.addAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE, "Your password does not match. Try again.");
				return showUpdatePasswordPage(model);
			}
		} catch (CredentialNotFoundException e) {
			model.addAttribute(ServiceUtilities.MESSAGE_ATTRIBUTE, e.getMessage()+" before accessing your profile page.");
			return showUpdatePasswordPage(model);
		} 
	}

	
}
