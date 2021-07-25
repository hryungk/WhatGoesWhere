package org.perscholas.whatgoeswhere.controllers;

import java.time.LocalDate;

import org.perscholas.whatgoeswhere.models.Employee;
import org.perscholas.whatgoeswhere.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	private EmployeeService employeeService;
	
	@Autowired
	public HomeController(EmployeeService employeeService) {
		this.employeeService = employeeService; 
	}
	
	@GetMapping("/") // This is what you type for URL
	public String showIndexPage() {
		return "index"; // return this to WebAppconfig
	}
	@GetMapping("/list")
	public String showListPage() {
		return "list";
	}
	@GetMapping("/additem")
	public String showAddItemPage() {
		return "additem";
	}
	@GetMapping("/edititem")
	public String showEditItemPage() {
		return "edititem";
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
	public String showProfilePage() {
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
			@RequestParam("dateInput") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, 
			Model model) {
		System.out.println(date);
		Employee employee = employeeService.findEmployeeById(employeeNumber);
		System.out.println(employee);
		model.addAttribute("employee", employee);
		return "index";
	}
//	@PostMapping("/find") // Match the form's action name
//	public String searchItemName(@RequestParam("itemName") String itemName,			
//			Model model) {		
//		Item employee = employeeService.findEmployeeById(itemName);
//		System.out.println(employee);
//		model.addAttribute("employee", employee);
//		return "index";
//	}
	
	@GetMapping("/home")
	public String showHomePage() {
		return "home";
	}
}
