package org.perscholas.whatgoeswhere.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.perscholas.whatgoeswhere.models.Employee;
import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.services.EmployeeService;
import org.perscholas.whatgoeswhere.services.ItemService;
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
	
	@Autowired
	public HomeController(ItemService itemService, EmployeeService employeeService) {
		this.employeeService = employeeService; 
		this.itemService = itemService;
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
	public String showAddItemPage() {
		return "additem";
	}
	@PostMapping("/addNewItem")
	public String addItem(@RequestParam("itemName") String name, @RequestParam("condition") String condition,@RequestParam("bestOption") String bestOption,@RequestParam("specialInstruction") String specialInstruction,@RequestParam("notes") String notes, Model model) {
		LocalDateTime now = LocalDateTime.now();
		Item item = new Item(name, condition, bestOption, specialInstruction, notes, now);
		itemService.addItem(item);
		return showListPage(model);
	}
	@GetMapping("/edititem")
	public String showEditItemPage(Model model) {
		
		return "edititem";
	}
	@PostMapping("/updateItem")
	public String editItem(@RequestParam("itemName") String name, @RequestParam("condition") String state,@RequestParam("bestOption") String bestOption,@RequestParam("specialInstruction") String specialInstruction,@RequestParam("notes") String notes, Model model) {
		Item item = itemService.findItemByNameAndState(name, state);
		item.setName(name);
		item.setCondition(state);
		item.setBestOption(bestOption);
		item.setSpecialInstruction(specialInstruction);
		item.setNotes(notes);
		itemService.updateItem(item);		
		return showProfilePage(model);
	}
	@GetMapping("/about")
	public String showAboutPage() {
		return "about";
	}
	@GetMapping("/login")
	public String showLogInPage() {
		return "login";
	}
	@GetMapping("/register")
	public String showRegisterPage() {
		return "register";
	}
	@GetMapping("/profile")
	public String showProfilePage(Model model) {
		
		return "profile";
	}
	@GetMapping("/contact")
	public String showContactPage() {
		return "contact";
	}
	
	/*
	 * Connecting the JSP and model.
	 */
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
	
	@GetMapping("/home")
	public String showHomePage() {
		return "home";
	}
}
