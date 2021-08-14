package org.perscholas.whatgoeswhere.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.perscholas.whatgoeswhere.exceptions.CredentialAlreadyExistsException;
import org.perscholas.whatgoeswhere.exceptions.CredentialNotFoundException;
import org.perscholas.whatgoeswhere.exceptions.ItemAlreadyExistsException;
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
		
	@GetMapping("/addItem")
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
		
		return "add_item";
	}
	@PostMapping("/addItem")
	public String addItem(@ModelAttribute("item") Item item, Model model, HttpSession session, BindingResult errors) {
		if (errors.hasErrors()) {
			return "add_item";			
		}
		item.setAddedDate(LocalDateTime.now());
		User user = getUserByEmail(session);		
		try {
			itemService.add(item, user.getId());
			return showListPage(model);
		} catch(ItemAlreadyExistsException e) {
			model.addAttribute("message",e.getMessage());
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
		try {
			Credential credential = credentialService.findByUsernameAndPassword(username, password);
			User user = credential.getUser();
			session.setAttribute("userName", username);
			session.setAttribute("eMail", user.getEmail());
			return showProfilePage(model, session);
		} catch (CredentialNotFoundException e) {
			String message = e.getMessage();
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
		User newUser = new User(email, firstName, lastName, LocalDate.now(), new ArrayList<Item>());
		Credential credential = new Credential(username, password, newUser);
		try {			
			if (userService.findUserByEmail(email) != null) {
				String invalidEmailMessage = "The email address " + email + " is already registered. Choose a different one.";
				model.addAttribute("emailMessage", invalidEmailMessage);	
			}
			credential = credentialService.add(credential);
			model.addAttribute("user", newUser);
			session.setAttribute("userName", username);
			session.setAttribute("eMail", email);
			return showProfilePage(model, session);
		} catch (CredentialAlreadyExistsException e) {
			username = "";
			model.addAttribute("usernameMessage", e.getMessage());
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

	@GetMapping("/editItem")
	public String showEditItemPage(@RequestParam("itemId") int itemId, Model model) {		
		Item item = itemService.findItemById(itemId);
		model.addAttribute("item", item);
		System.out.println(item);
		// Pass the bestoption enum values
		model.addAttribute("bestOptions", BestOption.values());
		return "edit_item";
	}
	@PostMapping("/editItem")
	public String editItem(@ModelAttribute("item") Item uitem,  Model model, HttpSession session, BindingResult errors) {
		if (errors.hasErrors()) {
			return "edit_item";
		}		
		try {
			itemService.update(uitem);
			return showProfilePage(model, session);
		} catch(ItemAlreadyExistsException e) {
			model.addAttribute("message",e.getMessage());
			return showEditItemPage(uitem.getId(), model);
		}
		
	}
	
	@PostMapping("/deleteitem")
	public String deleteItem(@RequestParam("itemId") int id, Model model, HttpSession session) {
		itemService.delete(id);		
		return showProfilePage(model, session);
	}
		
	@GetMapping("/deleteUser")
	public String showDeleteUserPage(Model model, HttpSession session) {		
		User user = getUserByEmail(session);	
		model.addAttribute("user", user);
		return "delete_user";
	}
	@PostMapping("/deleteUser")
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
