package com.hyunryungkim.whatgoeswhere.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyunryungkim.whatgoeswhere.exceptions.CredentialNotFoundException;
import com.hyunryungkim.whatgoeswhere.models.Credential;
import com.hyunryungkim.whatgoeswhere.services.CredentialService;

/**
 * Spring MVC Controller class
 * Connects the JSP and model
 * 
 * @author Hyunryung Kim
 *
 */
@Controller
public class CredentialController {
	/**
	 * Service object for Credential model
	 */
	private CredentialService credentialService = ControllerUtilities.credentialService;
	
	/**
	 * Maps get method for the admin page
	 * 
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the admin page
	 */
	@GetMapping("/admin")
	public String showAdminPage(Model model) {
		List<Credential> credentials = credentialService.findAll();
		model.addAttribute("credentials", credentials);
		return PageName.ADMIN.getValue();
	}
	
	/**
	 * Maps post method for the deleteUser request
	 * This page is not accessible without a valid credential so the exception is not expected to be thrown. 
	 * 
	 * @param userId an integer containing user's id to delete
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
			model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE, e.getMessage()+" as an Admin before deleting a user account.");
			return PageName.LOGIN.getValue();
		}
	}
	
	/**
	 *  Maps get method for update password page
	 * 
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the update_password page
	 */
	@GetMapping("/updatePassword")
	public String showUpdatePasswordPage(Model model) {
		if (model.getAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE) == null) {
			model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE, "");
		}
		return PageName.UPDATE_PASSWORD.getValue();
	}
	/**
	 * Maps post method for update password page
	 * 
	 * @param oldPassword a string of old password to match credential
	 * @param newPassword a string of new password 
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the profile if successful, update_password page otherwise
	 */
	@PostMapping("/updatePassword")
	public String updatePassword(@RequestParam("oldPassword")String oldPassword, @RequestParam("newPassword")String newPassword, Model model) {
		try {
			Credential credential = ControllerUtilities.getCredential();
			if (credentialService.checkIfValidOldPassword(oldPassword, credential.getPassword())) {
				credential.setPassword(newPassword);
				credentialService.update(credential);
				return "redirect:/"+PageName.PROFILE.getValue();
			} else {
				model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE, "Your password does not match. Try again.");
				return showUpdatePasswordPage(model);
			}
		} catch (CredentialNotFoundException e) {
			model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE, e.getMessage()+" before accessing your profile page.");
			return showUpdatePasswordPage(model);
		} 
	}
}
