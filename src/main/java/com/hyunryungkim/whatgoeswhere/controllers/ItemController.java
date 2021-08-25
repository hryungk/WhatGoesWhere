package com.hyunryungkim.whatgoeswhere.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyunryungkim.whatgoeswhere.exceptions.CredentialNotFoundException;
import com.hyunryungkim.whatgoeswhere.exceptions.ItemAlreadyExistsException;
import com.hyunryungkim.whatgoeswhere.models.BestOption;
import com.hyunryungkim.whatgoeswhere.models.Item;
import com.hyunryungkim.whatgoeswhere.models.User;
import com.hyunryungkim.whatgoeswhere.services.ItemService;
import com.hyunryungkim.whatgoeswhere.services.UserService;

/**
 * Spring MVC ItemController class
 * Mappings that involve Email service
 * 
 * @author Hyunryung Kim
 *
 */
@Controller
public class ItemController {
	/**
	 * Service object for Item model
	 */
	private ItemService itemService;
	
	/**
	 * Class constructor binding various service classes
	 * 
	 * @param itemService a service object for Item model
	 * @see com.hyunryungkim.whatgoeswhere.services.ItemService
	 */
	@Autowired
	public ItemController(ItemService itemService, UserService userService) {
		this.itemService = itemService;
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
		model.addAttribute(ControllerUtilities.ITEMS_ATTRIBUTE, items);
		return PageName.HOME.getValue();
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
		model.addAttribute(ControllerUtilities.ITEMS_ATTRIBUTE, items);
		model.addAttribute("role",ControllerUtilities.getRole());
		
		return PageName.LIST.getValue();
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
		if (model.getAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE) == null) {
			model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE, "");
		}
		// Pass an Item object
		model.addAttribute("item", new Item());
		
		// Pass the BestOption enum values
		model.addAttribute("bestOptions", BestOption.values());
		
		return PageName.ADD_ITEM.getValue();
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
			return PageName.ADD_ITEM.getValue();			
		}		
		try {
			User user = ControllerUtilities.getUser();
			item.setAddedDate(LocalDateTime.now());	
			itemService.add(item, user.getId());
			return showListPage(model);
		} catch (CredentialNotFoundException e) {
			model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE, e.getMessage()+" before adding an item.");
			return showAddItemPage(model);
		} catch (ItemAlreadyExistsException e) {
			model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE,e.getMessage());
			return showAddItemPage(model);
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
		return PageName.EDIT_ITEM.getValue();
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
			return PageName.EDIT_ITEM.getValue();
		}		
		try {
			itemService.update(uitem);
			if (pageName.equals(PageName.PROFILE.getValue())) {
				return "redirect:/"+PageName.PROFILE.getValue();
			} else if (pageName.equals(PageName.LIST.getValue())) {
				return showListPage(model);
			} else {
				return "redirect:/"+PageName.PROFILE.getValue();
			}
		} catch(ItemAlreadyExistsException e) {
			model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE,e.getMessage());
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
		if (pageName.equals(PageName.PROFILE.getValue())) {
			return "redirect:/"+PageName.PROFILE.getValue();
		} else if (pageName.equals(PageName.LIST.getValue())) {
			return showListPage(model);
		} else {
			return "redirect:/"+PageName.PROFILE.getValue();
		}
	}
}
