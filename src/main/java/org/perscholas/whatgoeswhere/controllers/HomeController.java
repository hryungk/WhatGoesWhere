package org.perscholas.whatgoeswhere.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.perscholas.whatgoeswhere.models.BestOption;
import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.models.User;
import org.perscholas.whatgoeswhere.models.UserItem;
import org.perscholas.whatgoeswhere.services.impl.ItemService;
import org.perscholas.whatgoeswhere.services.impl.UserItemService;
import org.perscholas.whatgoeswhere.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * Connecting the JSP and model.
 */
@Controller
public class HomeController {

	private ItemService itemService;
	private UserService userService;
	private UserItemService uiService;
	
	@Autowired
	public HomeController(ItemService itemService, UserService userService, UserItemService uiService) {
		this.itemService = itemService;
		this.userService = userService;
		this.uiService = uiService;
	}
	
	@GetMapping("/") // This is what you type for URL
	public String showIndexPage() {
		return "index"; // return this to WebAppconfig
	}
	@PostMapping("/find") // Match the form's action name
	public String searchItemName(@RequestParam("itemName") String itemName,			
			Model model) {	
		List<Item> items = itemService.findItemsByName(itemName);
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
		String username = getUserName(session);
		if (username == null) { // If not logged in, redirect to login page
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
	@Transactional
	@PostMapping("/additem")
	public String addItem(@ModelAttribute("item") Item item, Model model, HttpSession session, BindingResult errors) {
		if (errors.hasErrors()) {
			return "additem";			
		}
		// Try to find the item in the database
		Item itemFromDB = itemService.findItemByNameAndState(item.getName(), item.getCondition()); 
		if (itemFromDB != null) { // already exists in the database
			String message = item.getName();
			if (item.getCondition().length() != 0) {
				message += " ("+ item.getCondition() +")";
			}
			message += " already exists in the list.";
			model.addAttribute("message", message);
			return showAddItemPage(model, session);
		} else { // Item doesn't exist in the database
			LocalDateTime now = LocalDateTime.now();
			item.setAddedDate(now);
			String username = getUserName(session);
			User user = userService.findUserById(username);
			Hibernate.initialize(user.getItems());
			user.getItems().add(item);
			user = userService.update(user);
			
			boolean isAddSuccessful = user.getItems().contains(item);
			if (isAddSuccessful) {
				return showListPage(model);
			} else {
				String message = "Adding " + item.getName();
				if (item.getCondition().length() != 0) {
					message += " ("+ item.getCondition() +")";
				}
				message += " failed.";
				model.addAttribute("message", message);
				return showAddItemPage(model, session);
			}
		}
			
		
//		Item newItem = itemService.add(item);
//		if (newItem != null) { 
//			UserItem ui = uiService.add(new UserItem(username, newItem.getId()));
//			isAddSuccessful = ui != null;
//		}
//		boolean isAddSuccessful = itemService.addItem(item, username);
//		
//		if (isAddSuccessful) {
//			return showListPage(model);
//		} else {
//			String message = item.getName();
//			if (item.getCondition().length() != 0) {
//				message += " ("+ item.getCondition() +")";
//			}
//			message += " already exists in the list.";
//			model.addAttribute("message", message);
//			return showAddItemPage(model, session);
//		}
	}
	
	@GetMapping("/login")
	public String showLogInPage(Model model) {
		if (model.getAttribute("message") == null) {
			String message = "";
			model.addAttribute("message", message);
		}
		return "login";
	}
	@Transactional
	@PostMapping("/login")
	public String logIn(@RequestParam("eMail") String email, @RequestParam("password") String password, Model model, HttpSession session) {
		User user = userService.findUserByEmail(email);		
		String message = "";
		if (user != null && user.getPassword().equals(password)) { // user is found and password matches.
			session.setAttribute("userName", user.getUsername());
			session.setAttribute("eMail", email);
			
			return showProfilePage(model, session);
//			return "profile";
		} else {
			if (user == null) { // User is not found
				message = "You haven't registered yet. Please click the link below the form to create a new account.";	
			} else { // User is found but password doesn't match.
				message = "Credentials not correct. Please try again";
			}
			model.addAttribute("message", message);
			model.addAttribute("email", email);
			return showLogInPage(model);
		}
	}
	
	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		if (model.getAttribute("message") == null) {
			String message = "";
			model.addAttribute("message", message);
		}
		if (model.getAttribute("user") == null) {
			User newUser = new User("", "", "", "", "", new ArrayList<Item>());
			model.addAttribute("user", newUser);
		}
		return "register";
	}
	@PostMapping("/register")
	public String addUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("eMail") String email, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, Model model, HttpSession session) {
		User userById = userService.findUserById(username);
		User userByEmail = userService.findUserByEmail(email);
		User newUser = new User(username, password, email, firstName, lastName, new ArrayList<Item>());
		if (userById == null && userByEmail == null) {	// Both username and email don't exist
//			userService.addUser(newUser);
			userService.add(newUser);
//			model.addAttribute("user", newUser);
//			model.addAttribute("items", newUser.getItems());
			session.setAttribute("userName", username);
			return showProfilePage(model, session);
		} else { 
			String invalidEmailMessage = "";
			String invalidIdMessage = "";
			if (userByEmail != null) { // user email already exists in the system
				invalidEmailMessage = "Email address " + email + " already exists. Choose a different one.";
				newUser.setEmail("");
			}
			
			if (userById != null) { // username already exists in the system
				invalidIdMessage += "Username " + username + " already exists. Choose a different one.";
				newUser.setUsername("");
			}
			model.addAttribute("emailMessage", invalidEmailMessage);
			model.addAttribute("usernameMessage", invalidIdMessage);
			model.addAttribute("user", newUser); // to populate the form
			return showRegisterPage(model);
		}
	}
	
	@Transactional
	@GetMapping("/profile")
	public String showProfilePage(Model model, HttpSession session) {
		String username = getUserName(session);
		User user = userService.findUserById(username);

		model.addAttribute("user", user);
		
		Hibernate.initialize(user.getItems());
		model.addAttribute("items", user.getItems());
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
	@Transactional
	@PostMapping("/edititem")
	public String editItem(@ModelAttribute("item") Item uitem,  Model model, HttpSession session, BindingResult errors) {
		if (errors.hasErrors()) {
			return "edititem";
		}
		Item item = itemService.findItemById(uitem.getId());
		assert(item != null);
		Item itemByNS = itemService.findItemByNameAndState(uitem.getName(), uitem.getCondition());
		assert(itemByNS != null);
		if (itemByNS.equals(item)) { // this means the user didn't change name or condition
			item.setName(uitem.getName());
			item.setCondition(uitem.getCondition());
			item.setBestOption(uitem.getBestOption());
			item.setSpecialInstruction(uitem.getSpecialInstruction());
			item.setNotes(uitem.getNotes());	
			itemService.update(item);	
			return showProfilePage(model, session);
		} else {
			// Complete the logic. Reaching here means user changed either name or condition or both.
			// This could lead to duplicate entry in the database. Resolve the duplicate.
			return "edititem";
		}
	}
	
	@Transactional
	@PostMapping("/deleteitem")
	public String deleteItem(@RequestParam("itemId") int id, Model model, HttpSession session) {
//		uiService.deleteByItemId(id);
		itemService.deleteById(id);		
		return "redirect:/"+showProfilePage(model, session);
	}
		
	@GetMapping("/deleteuser")
	public String showDeleteUserPage(Model model, HttpSession session) {
		String username = getUserName(session);
		User user = userService.findUserById(username);		
		model.addAttribute("user", user);
		return "deleteuser";
	}
	@PostMapping("/deleteuser")
	public String deleteUser(@RequestParam("message") String message, Model model, HttpSession session) {
		String username = getUserName(session);
		User user = userService.findUserById(username);					
//		userService.deleteUser(user);
		userService.delete(user);
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
		String email = (String) session.getAttribute("eMail");
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

	private String getUserName(HttpSession session) {
		return (String) session.getAttribute("userName");
	}
	
}
