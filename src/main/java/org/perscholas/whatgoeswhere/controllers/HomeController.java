package org.perscholas.whatgoeswhere.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.perscholas.whatgoeswhere.exceptions.CredentialAlreadyExistsException;
//import org.perscholas.whatgoeswhere.exceptions.CredentialNotFoundException;
import org.perscholas.whatgoeswhere.exceptions.ItemAlreadyExistsException;
import org.perscholas.whatgoeswhere.models.BestOption;
import org.perscholas.whatgoeswhere.models.Credential;
import org.perscholas.whatgoeswhere.models.Item;
import org.perscholas.whatgoeswhere.models.User;
import org.perscholas.whatgoeswhere.services.CredentialService;
import org.perscholas.whatgoeswhere.services.ItemService;
import org.perscholas.whatgoeswhere.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	final Logger LOGGER = Logger.getLogger(HomeController.class.getName());
	
	@Autowired
	public HomeController(ItemService itemService, UserService userService, CredentialService credentialService) {
		this.itemService = itemService;
		this.userService = userService;
		this.credentialService = credentialService;
	}
	
	@GetMapping("/") // This is what you type for URL
	public String showIndexPage(Model model) {
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
	public String showAddItemPage(Model model) {
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
	public String addItem(@ModelAttribute("item") Item item, Model model, BindingResult errors) {
		if (errors.hasErrors()) {
			return "add_item";			
		}
		item.setAddedDate(LocalDateTime.now());	
		User user = getUser();
		try {
			itemService.add(item, user.getId());
			return showListPage(model);
		} catch(ItemAlreadyExistsException e) {
			model.addAttribute("message",e.getMessage());
			return showAddItemPage(model);
		}
	}
	
	@GetMapping("/login")
	public String showLogInPage(Model model) {
		if (model.getAttribute("message") == null) {
			String message = "";
			model.addAttribute("message", message);
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
//			model.addAttribute("message", message);
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
		return "register";
	}
	@PostMapping("/registerNewUser")
	public String addUser(@RequestParam("userName") String username, @RequestParam("password") String password, @RequestParam("eMail") String email, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, Model model, HttpServletRequest request) {
		User newUser = new User(email, firstName, lastName, LocalDate.now(), new ArrayList<Item>());
		Credential credential = new Credential(username, password, newUser);
		try {			
			if (userService.findUserByEmail(email) != null) {
				String invalidEmailMessage = "The email address " + email + " is already registered. Choose a different one.";
				model.addAttribute("emailMessage", invalidEmailMessage);	
			}
			credentialService.add(credential);
			model.addAttribute("user", newUser);
			authWithHttpServletRequest(request, username, password); // login automatically
			return showProfilePage(model);
		} catch (CredentialAlreadyExistsException e) {
			username = "";
			model.addAttribute("usernameMessage", e.getMessage());
			model.addAttribute("user", newUser); // to populate the form
			model.addAttribute("username", username);
			return showRegisterPage(model);
		} 		
	}
	
	@GetMapping("/profile")
	public String showProfilePage(Model model) {		
		Credential credential = getCredential();
		User user = credential.getUser();
		model.addAttribute("user",user);
		model.addAttribute("userName", credential.getUsername());
		List<Item> items = user.getItems();
		model.addAttribute("items", items);
		return "profile";
	}

	@GetMapping("/editItem")
	public String showEditItemPage(@RequestParam("itemId") int itemId, Model model) {		
		Item item = itemService.findItemById(itemId);
		model.addAttribute("item", item);
		// Pass the BestOption enum values
		model.addAttribute("bestOptions", BestOption.values());
		return "edit_item";
	}
	@PostMapping("/editItem")
	public String editItem(@ModelAttribute("item") Item uitem,  Model model, BindingResult errors) {
		if (errors.hasErrors()) {
			return "edit_item";
		}		
		try {
			itemService.update(uitem);
			return showProfilePage(model);
		} catch(ItemAlreadyExistsException e) {
			model.addAttribute("message",e.getMessage());
			return showEditItemPage(uitem.getId(), model);
		}
	}
	
	@PostMapping("/deleteitem")
	public String deleteItem(@RequestParam("itemId") int id, Model model) {
		itemService.delete(id);		
		return showProfilePage(model);
	}
		
	@GetMapping("/deleteUser")
	public String showDeleteUserPage(Model model) {		
		model.addAttribute("email", getUserEmail());
		return "delete_user";
	}
	@PostMapping("/deleteUser")
	public String deleteUser(@RequestParam("message") String message, Model model, HttpServletRequest request) {
		Credential credential = getCredential();
		credentialService.delete(credential);

		logoutWithHttpServletRequest(request);
		
		// Send email to admin
		System.out.println(message);
		
		return showIndexPage(model);
	}
	
//	@GetMapping("/logout") 
//	public String logout(HttpSession session) {
//		session.invalidate(); // On clicking logout links, the session is to be invalidated.
//		return "index";
//	}

	@GetMapping("/logoutSuccess")
	public String showLogoutSuccessPage() {
		return "logout_success";
	}

	@GetMapping("/about")
	public String showAboutPage() {
		return "about";
	}
	
	@GetMapping("/contact")
	public String showContactPage(Model model) {
		String email = getUserEmail();
		model.addAttribute("email", email);
		return "contact";
	}
	@PostMapping("/contact")
	public String contact(@RequestParam("message") String message, @RequestParam("eMail") String email, Model model) {
		System.out.println("email: " + email);
		System.out.println("message: " + message);
		
		// Send email to admin
		
		
		return "index";
	}

	@GetMapping("/admin")
	public String showAdminPage() {
		return "admin";
	}
	
	@GetMapping("/accessDenied")
	public String showAccessDeniedPage() {
		return "access_denied";
	}
	
	private void logoutWithHttpServletRequest(HttpServletRequest request) {
		try {
			request.logout();
		} catch (ServletException e) {
			LOGGER.log(Level.WARNING, "Error while logout ", e);
		}
		
	}
	private void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
	    try {
	        request.login(username, password);
	    } catch (ServletException e) {
	        LOGGER.log(Level.WARNING, "Error while login ", e);
	    }
	}
	
	private Credential getCredential() {		
		// Get current user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		} else {
			Object principal = authentication.getPrincipal();
			boolean isAnonymous = principal.equals("anonymousUser");
			if (isAnonymous) {
				return null;
			} else {
				// Print current user granted authorities
//				Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//				System.out.println(authorities);
				String username =  ((UserDetails)principal).getUsername();
				return credentialService.findByUsername(username);
			}
		}
	}
	
	private User getUser() {
		Credential credential = getCredential();
		return credential.getUser();
	}
	
	private String getUserEmail() {
		User user = getUser();
		if (user == null)
			return null;
		return user.getEmail();
	}	
}
