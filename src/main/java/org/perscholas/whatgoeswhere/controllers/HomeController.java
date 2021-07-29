package org.perscholas.whatgoeswhere.controllers;

import java.time.LocalDateTime;
import java.util.List;

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
	@GetMapping("/list")
	public String showListPage(Model model) {
		List<Item> items = itemService.getAllItems();
		model.addAttribute("items", items);
		return "list";
	}
	@GetMapping("/additem")
	public String showAddItemPage(Model model) {
		if (model.getAttribute("message") == null) {
			String message = "";
			model.addAttribute("message", message);
		}
		return "additem";
	}
	@PostMapping("/addNewItem")
	public String addItem(@RequestParam("itemName") String name, @RequestParam("condition") String condition,@RequestParam("bestOption") String bestOption,@RequestParam("specialInstruction") String specialInstruction,@RequestParam("notes") String notes, Model model) {
		LocalDateTime now = LocalDateTime.now();
		Item item = new Item(name, condition, bestOption, specialInstruction, notes, now);
		boolean isAddSuccessful = itemService.addItem(item);
		if (isAddSuccessful) {
			return showListPage(model);
		} else {
			String message = name;
			if (condition.length() != 0) {
				message += " ("+ condition +")";
			}
			message += " already exists in the list.";
			model.addAttribute("message", message);
			return showAddItemPage(model);
		}
	}
	@GetMapping("/edititem")
	public String showEditItemPage(@RequestParam("id") int itemId, @RequestParam("username") String username, Model model) {		
		Item item = itemService.findItemById(itemId);
		model.addAttribute("item", item);
		User user = userService.findUserById(username);
		model.addAttribute("user", user);
		return "edititem";
	}
	@PostMapping("/updateItem")
	public String editItem(@RequestParam("itemName") String name, @RequestParam("condition") String state,@RequestParam("bestOption") String bestOption,@RequestParam("specialInstruction") String specialInstruction,@RequestParam("notes") String notes,  @RequestParam("username") String username, Model model) {
		Item item = itemService.findItemByNameAndState(name, state);
		item.setName(name);
		item.setCondition(state);
		item.setBestOption(bestOption);
		item.setSpecialInstruction(specialInstruction);
		item.setNotes(notes);
		System.out.println("\n\n"+item.getNotes()+"\n\n");
		itemService.updateItem(item);	
		
		User user = userService.findUserById(username);
		model.addAttribute("user", user);
		return showProfilePage(model);
	}
	@PostMapping("/deleteitem")
	public String deleteItem(@RequestParam("id") int id,  @RequestParam("username") String username, Model model) {
		itemService.deleteItem(id);
		
		User user = userService.findUserById(username);
		model.addAttribute("user", user);
		return showProfilePage(model);
	}
	
	@GetMapping("/about")
	public String showAboutPage() {
		return "about";
	}
	@GetMapping("/login")
	public String showLogInPage(Model model) {
		if (model.getAttribute("message") == null) {
			String message = "";
			model.addAttribute("message", message);
		}
		return "login";
	}
	@PostMapping("/loginInfo")
	public String logIn(@RequestParam("eMail") String email, @RequestParam("password") String password, Model model) {
		User user = userService.findUserByEmail(email);
		if (user == null) {
			String message = "You haven't registered yet. Please click the link below the form to create a new account.";			
			model.addAttribute("message", message);
			return showLogInPage(model);
		} else if (user.getPassword().equals(password)) {
			model.addAttribute("user", user);
			return showProfilePage(model);
		} else {
			String message = "Credentials not correct. Please try again";			
			model.addAttribute("message", message);
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
	@PostMapping("/addNewUser")
	public String addUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("eMail") String email, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, Model model) {
		User user = userService.findUserById(username);
		User newUser = new User(username, password, email, firstName, lastName, null);
		if (user == null) {	// Username doesn't exist
			userService.addUser(newUser);
			model.addAttribute("user", newUser);
			return showProfilePage(model);
		} else { // Username already exists
			String message = "Username already exists. Choose a different one.";
			model.addAttribute("message", message);
			newUser = new User("", password, email, firstName, lastName, null);
			model.addAttribute("user", newUser); // to populate the form
			return showRegisterPage(model);
		}
	}
	
	@GetMapping("/profile")
	public String showProfilePage(Model model) {
		User user = (User) model.getAttribute("user");
		List<Item> items = userService.getItems(user.getUsername());
		model.addAttribute("items", items);
		return "profile";
	}
	
	@GetMapping("/deleteuser")
	public String showDeleteUserPage(@RequestParam("email") String email, Model model) {
		User user = userService.findUserByEmail(email);		
		model.addAttribute("user", user);
		return "deleteuser";
	}
	@PostMapping("/deleteUser")
	public String deleteUser(@RequestParam("email") String email, Model model) {
		User user = userService.findUserByEmail(email);		
		userService.deleteUser(user);
		return showIndexPage();
	}
	
	@GetMapping("/contact")
	public String showContactPage() {
		return "contact";
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
		System.out.println(employee);
		model.addAttribute("employee", employee);
		return "index";
	}
	@PostMapping("/find") // Match the form's action name
	public String searchItemName(@RequestParam("itemName") String itemName,			
			Model model) {	
		System.out.println(itemName);
		List<Item> items = itemService.findItemByName(itemName);
		System.out.println(items);
		model.addAttribute("items", items);
		return "index";
	}
	
}
