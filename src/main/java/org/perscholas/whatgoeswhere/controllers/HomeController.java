package org.perscholas.whatgoeswhere.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.perscholas.whatgoeswhere.models.BestOption;
import org.perscholas.whatgoeswhere.models.Credential;
import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.models.User;
import org.perscholas.whatgoeswhere.services.CredentialService;
import org.perscholas.whatgoeswhere.services.ItemService;
import org.perscholas.whatgoeswhere.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * Connecting the JSP and model.
 */

@Controller
public class HomeController {
	
	private CredentialService credentialService;
	private UserService userService;
	private ItemService itemService;
	
	@Autowired
	public HomeController(ItemService itemService, UserService userService, CredentialService credentialService) {
		this.itemService = itemService;
		this.userService = userService;
		this.credentialService = credentialService;
	}
	
	@GetMapping("/") // This is what you type for URL
	public String showIndexPage() {
		return "index"; // return this to WebAppconfig
	}
	@PostMapping("/find") // Match the form's action name
	public String searchItemName(@RequestParam("itemName") String itemName,			
			Model model) {	
		List<Item> items = itemService.findItemByName(itemName);
		model.addAttribute("items", items);
		return "index";
	}
	
	@GetMapping("/list")
	public String showListPage(Model model) {
		List<Item> items = itemService.getAllItems();
		model.addAttribute("items", items);
		return "list";
	}
		
	@GetMapping("/additem")
	public String showAddItemPage(Model model, HttpSession session) {
		String email = getUserEmail(session);
		if (email == null) { // If not logged in, redirect to login page
			return showLogInPage(model);
		}
		if (model.getAttribute("message") == null) {
			String message = "";
			model.addAttribute("message", message);
		}
		// Pass an Item object
		model.addAttribute("item", new Item());
		
		// Pass the bestoption enum values
		model.addAttribute("bestOptions", BestOption.values());
		
		return "additem";
	}
	@PostMapping("/additem")
	public String addItem(@ModelAttribute("item") Item item, Model model, HttpSession session, BindingResult errors) {
		if (errors.hasErrors()) {
			return "additem";			
		}
		LocalDateTime now = LocalDateTime.now();
		item.setAddedDate(now);
		User user = getUserByEmail(session);	
		boolean isAddSuccessful = itemService.addItem(item, user.getId());
		if (isAddSuccessful) {
			return showListPage(model);
		} else {
			String message = item.getName();
			if (item.getCondition().length() != 0) {
				message += " ("+ item.getCondition() +")";
			}
			message += " already exists in the list.";
			model.addAttribute("message", message);
			return showAddItemPage(model, session);
		}
	}
	
	@GetMapping("/login")
	public String showLogInPage(Model model) {
		if (model.getAttribute("message") == null) {
			String message = "";
			model.addAttribute("message", message);
		}
		String username = (String) model.getAttribute("username");
		if (username != null) {			
			System.out.println("login get mapping: " + username);
		}
		return "login";
	}
	@PostMapping("/login")
	public String logIn(@RequestParam("userName") String username, @RequestParam("password") String password, Model model, HttpSession session) {
		Credential credential = credentialService.findByUsername(username);	
		String message = "";
		if (credential != null && credential.getPassword().equals(password)) { // User name is found and password matches.
			User user = credential.getUser();
			session.setAttribute("userName", username);
			session.setAttribute("eMail", user.getEmail());
			return showProfilePage(model, session);
		} else {
			if (credential == null) { // User name is not found
				message = "You haven't registered yet. Please click the link below the form to create a new account.";	
			} else { // User name is found but password doesn't match.
				message = "Credentials not correct. Please try again";
			}
			model.addAttribute("message", message);
			model.addAttribute("username", username);
			return showLogInPage(model);
		}
	}
	
//	@GetMapping("register/{username}")
//	public String passToRegister(Model model, @PathVariable("username") String username) {		
//		if (username != null) {			
//			System.out.println("passToRegister: " + username);
//		}
//		model.addAttribute("username", username);
//		return "register";
//	}
	
	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		if (model.getAttribute("message") == null) {
			String message = "";
			model.addAttribute("message", message);
		}
		if (model.getAttribute("user") == null) {
			User newUser = new User();
			model.addAttribute("user", newUser);
		}
		String username = (String) model.getAttribute("username");
		if (username != null) {			
			System.out.println("going to register page: "+username);
		}
		return "register";
	}
	@PostMapping("/register")
	public String addUser(@RequestParam("userName") String username, @RequestParam("password") String password, @RequestParam("eMail") String email, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, Model model, HttpSession session) {
		Credential credential = credentialService.findByUsername(username);	
		User userByEmail = userService.findUserByEmail(email);
		User newUser = new User(email, firstName, lastName, LocalDate.now(), new ArrayList<Item>());
		if (credential == null && userByEmail == null) {	// Both credential and email don't exist
			credential = credentialService.add(new Credential(username, password, newUser));
			System.out.println(credential);
			model.addAttribute("user", newUser);
			session.setAttribute("userName", username);
			session.setAttribute("eMail", email);
			return showProfilePage(model, session);
		} else { 
			String invalidEmailMessage = "";
			String invalidUsernameMessage = "";
			if (userByEmail != null) { // user email already exists in the system
				invalidEmailMessage = "Email address " + email + " already exists. Choose a different one.";
				newUser.setEmail("");
			}
			
			if (credential != null) { // username already exists in the system
				invalidUsernameMessage += "Username " + username + " already exists. Choose a different one.";
				credential.setUsername("");
			}
			model.addAttribute("emailMessage", invalidEmailMessage);
			model.addAttribute("usernameMessage", invalidUsernameMessage);
			model.addAttribute("user", newUser); // to populate the form
			model.addAttribute("username", username);
			return showRegisterPage(model);
		}
	}
	
	@GetMapping("/profile")
	public String showProfilePage(Model model, HttpSession session) {		
		User user = getUserByEmail(session);	
		model.addAttribute("user",user);
		List<Item> items = user.getItems();
		model.addAttribute("items", items);
		return "profile";
	}

	@GetMapping("/edititem")
	public String showEditItemPage(@RequestParam("itemId") int itemId, Model model) {		
		Item item = itemService.findItemById(itemId);
		model.addAttribute("item", item);

		// Pass the bestoption enum values
		model.addAttribute("bestOptions", BestOption.values());
		return "edititem";
	}
	@PostMapping("/edititem")
	public String editItem(@ModelAttribute("item") Item uitem,  Model model, HttpSession session, BindingResult errors) {
		if (errors.hasErrors()) {
			return "edititem";
		}
		Item itemById = itemService.findItemById(uitem.getId());
		assert(itemById != null); // can't be null because we're editing an existing id
		Item itemByNS = itemService.findItemByNameAndState(uitem.getName(), uitem.getCondition());
		// If user changed neither name nor condition OR user changed it but an matching item doesn't exist in the db
		if (itemById.equals(itemByNS) || !itemById.equals(itemByNS) && itemByNS==null) {
			itemById.setName(uitem.getName());
			itemById.setCondition(uitem.getCondition());
			itemById.setBestOption(uitem.getBestOption());
			itemById.setSpecialInstruction(uitem.getSpecialInstruction());
			itemById.setNotes(uitem.getNotes());
			itemService.update(itemById);			
			return showProfilePage(model, session);
		} else { // user changed either name or condition or both such that there is a duplicate
			String message = "An item " + uitem.getName() + " (" + uitem.getCondition() +") already exists in the list";
			model.addAttribute("message",message);
			return "edititem";
			 
		}
	}
	
	@PostMapping("/deleteitem")
	public String deleteItem(@RequestParam("itemId") int id, Model model, HttpSession session) {
		itemService.deleteItem(id);		
		return showProfilePage(model, session);
	}
		
	@GetMapping("/deleteuser")
	public String showDeleteUserPage(Model model, HttpSession session) {		
		User user = getUserByEmail(session);	
		model.addAttribute("user", user);
		return "deleteuser";
	}
	@PostMapping("/deleteuser")
	public String deleteUser(@RequestParam("message") String message, Model model, HttpSession session) {
		String username =  (String) session.getAttribute("userName");
		Credential credential = credentialService.findByUsername(username);
		credentialService.delete(credential);
		session.invalidate();
		
		// Send email to admin
		System.out.println(message);
		
		
		return showIndexPage();
	}
	
	@GetMapping("/logout") 
	public String logout(HttpSession session) {
		session.invalidate(); // On clicking logout links, the session is to be invalidated.
		return showIndexPage();
	}

	@GetMapping("/about")
	public String showAboutPage() {
		return "about";
	}
	
	@GetMapping("/contact")
	public String showContactPage(HttpSession session, Model model) {
		String email = getUserEmail(session);
		model.addAttribute("email", email);
		return "contact";
	}
	@PostMapping("/contact")
	public String contact(@RequestParam("message") String message, @RequestParam("eMail") String email, Model model, HttpSession session) {
		System.out.println("email: " + email);
		System.out.println("message: " + message);
		
		// Send email to admin
		
		
		return showIndexPage();
	}
	
	private User getUserByEmail(HttpSession session) {
		String email = getUserEmail(session);
		return userService.findUserByEmail(email);
	}
	private String getUserEmail(HttpSession session) {
		return (String) session.getAttribute("eMail");
	}	
}
