package org.perscholas.whatgoeswhere.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.perscholas.whatgoeswhere.models.BestOption;
import org.perscholas.whatgoeswhere.models.Employee;
import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.models.User;
import org.perscholas.whatgoeswhere.services.EmployeeService;
import org.perscholas.whatgoeswhere.services.ItemService;
import org.perscholas.whatgoeswhere.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	private EmployeeService employeeService;
	private ItemService itemService;
	private UserService userService;
	
	@Autowired
	public HomeController(ItemService itemService, UserService userService, EmployeeService employeeService) {
		this.employeeService = employeeService; 
		this.itemService = itemService;
		this.userService = userService;
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
//		model.addAttribute("bestOption", BestOption.Garbage);
		model.addAttribute("bestOptions", BestOption.values());
		
		return "additem";
	}
	@PostMapping("/additem")
	public String addItem(@ModelAttribute("item") Item item, Model model, HttpSession session) {
		
		LocalDateTime now = LocalDateTime.now();
		item.setAddedDate(now);
//		Item item = new Item(item.getName(), item.getCondition(), item.getBestOption(), item.getSpecialInstruction(), item.getNotes(), now);
		String username = getUserName(session);
		boolean isAddSuccessful = itemService.addItem(item, username);
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
//	@PostMapping("/additem")
//	public String addItem(@RequestParam("itemName") String name, @RequestParam("condition") String condition,@RequestParam("bestOption") BestOption bestOption,@RequestParam("specialInstruction") String specialInstruction,@RequestParam("notes") String notes, Model model, HttpSession session) {
//		System.out.println(bestOption);
//		LocalDateTime now = LocalDateTime.now();
//		Item item = new Item(name, condition, bestOption, specialInstruction, notes, now);
//		String username = getUserName(session);
//		boolean isAddSuccessful = itemService.addItem(item, username);
//		if (isAddSuccessful) {
//			return showListPage(model);
//		} else {
//			String message = name;
//			if (condition.length() != 0) {
//				message += " ("+ condition +")";
//			}
//			message += " already exists in the list.";
//			model.addAttribute("message", message);
//			return showAddItemPage(model, session);
//		}
//	}
	
	
	@GetMapping("/login")
	public String showLogInPage(Model model) {
		if (model.getAttribute("message") == null) {
			String message = "";
			model.addAttribute("message", message);
		}
		return "login";
	}
	@PostMapping("/login")
	public String logIn(@RequestParam("eMail") String email, @RequestParam("password") String password, Model model, HttpSession session) {
		User user = userService.findUserByEmail(email);		
		String message = "";
		if (user != null && user.getPassword().equals(password)) { // user is found and password matches.
			session.setAttribute("userName", user.getUsername());
			session.setAttribute("eMail", email);
			return showProfilePage(model, session);
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
			User newUser = new User("", "", "", "", "", null);
			model.addAttribute("user", newUser);
		}
		return "register";
	}
	@PostMapping("/register")
	public String addUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("eMail") String email, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, Model model, HttpSession session) {
		User userById = userService.findUserById(username);
		User userByEmail = userService.findUserByEmail(email);
		User newUser = new User(username, "", email, firstName, lastName, null);
		if (userById == null && userByEmail == null) {	// Both username and email don't exist
			userService.addUser(newUser);
			model.addAttribute("user", newUser);
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
	
	@GetMapping("/profile")
	public String showProfilePage(Model model, HttpSession session) {
		String username = getUserName(session);
		User user = userService.findUserById(username);
		model.addAttribute("user",user);
		List<Item> items = userService.getItems(user.getUsername());
		model.addAttribute("items", items);
		return "profile";
	}

	@GetMapping("/edititem")
	public String showEditItemPage(@RequestParam("itemId") int itemId, Model model) {		
		Item item = itemService.findItemById(itemId);
		model.addAttribute("item", item);
		return "edititem";
	}
	@PostMapping("/edititem")
	public String editItem(@RequestParam("itemName") String name, @RequestParam("condition") String state,@RequestParam("bestOption") BestOption bestOption,@RequestParam("specialInstruction") String specialInstruction,@RequestParam("notes") String notes,  Model model, HttpSession session) {
		Item item = itemService.findItemByNameAndState(name, state);
		item.setName(name);
		item.setCondition(state);
		item.setBestOption(bestOption);
		item.setSpecialInstruction(specialInstruction);
		item.setNotes(notes);
		itemService.updateItem(item);			
		return showProfilePage(model, session);
	}
	
	@PostMapping("/deleteitem")
	public String deleteItem(@RequestParam("itemId") int id, Model model, HttpSession session) {
		itemService.deleteItem(id);		
		return showProfilePage(model, session);
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
		userService.deleteUser(user);
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
	
	/*
	 * Connecting the JSP and model.
	 */

	@GetMapping("/home")
	public String showHomePage() {
		return "home";
	}
	@PostMapping("/search") // Match the form's action name
	public String searchEmployeeByNumber(@RequestParam("employeeNumber") Integer employeeNumber, 
			Model model) {
		Employee employee = employeeService.findEmployeeById(employeeNumber);
		model.addAttribute("employee", employee);
		return "index";
	}
	
}
