package com.hyunryungkim.whatgoeswhere.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyunryungkim.whatgoeswhere.exceptions.CredentialNotFoundException;
import com.hyunryungkim.whatgoeswhere.models.Credential;
import com.hyunryungkim.whatgoeswhere.services.EmailService;

/**
 * Spring MVC Email Controller class
 * Mappings that involve Email service
 * 
 * @author Hyunryung Kim
 *
 */
@Controller
//@ControllerAdvice // Make this controller to be invoked even when HomeController is not called.
public class EmailController {
	/**
	 * Service object for Java Mail
	 */
	private EmailService emailService;
	/**
	 * Logger object for this class
	 */
	private Logger logger = ControllerUtilities.setupLogger(EmailController.class.getName());
	/**
	 * Username for gmail credential
	 */
	private String username="";
	
	/**
	 * Class constructor binding email service classes
	 * 
	 * @param emailService a service object mapping EmailService class
	 * @see com.hyunryungkim.whatgoeswhere.services.EmailService
	 */
	@Autowired
	public EmailController(EmailService emailService) {
		this.emailService = emailService;
		// Load secret properties file to fetch your username and password. 
		File file = new File("/Users/HRK/eclipse-repository/WhatGoesWhere/secret.properties");
		Map<String, String> map = new HashMap<>();
		try {
				Scanner scan = new Scanner(file);
				while(scan.hasNext()) {
					String[] arr = scan.next().split("=");				
					map.put(arr[0], arr[1]);
				}
				scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		username = map.get("username");
	}
	
	/**
	 * Maps get method for the contact page
	 * Sends a model attribute email of the current user. 
	 * 
	 * @param model a Model object holding model attributes
	 * @return the JSP name for the contact page
	 */
	@GetMapping("/contact")
	public String showContactPage(Model model) {
		String email = "";
		try {
			email = ControllerUtilities.getUserEmail();
		} catch (CredentialNotFoundException e) {
			logger.info("Anonymous user accessing contact page");
		}
		model.addAttribute(ControllerUtilities.EMAIL_ATTRIBUTE, email);
		return PageName.CONTACT.getValue();
	}	
	/**
	 * Maps post method for the contact page
	 * 
	 * @param subject a string of email subject
	 * @param message a string of email message body
	 * @param mailFrom a string of email address from the user input
	 * @param model a Model object holding model attributes 
	 * @return the JSP name for the contact-success page
	 */
	@PostMapping("/contact")
	public String sendEmailToClient(@RequestParam("subject") String subject, @RequestParam("message") String message, @RequestParam("eMail") String mailFrom, Model model) {		
		logger.log(Level.INFO,"Sender?= {0}, Subject?= {1}, Message?= {2}\n", new String[] {mailFrom, subject, message});
		
		message = "From: " + mailFrom + "\n" + message;
		emailService.sendSimpleMessage(mailFrom, username, subject, message);
		
		model.addAttribute("title", "Contact Success");
		model.addAttribute("messageHead","You message has been sent.");
		model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE,"Thank you for contacting us! We will get back to you shortly.");
		return showContactSuccessPage(model);
	}
	
	/**
	 * Maps get method for the contact-success page
	 * 
	 * @param model a Model object holding model attributes 
	 * @return the JSP name for the contact-success page
	 */
	@GetMapping("/contactSuccess") 
	public String showContactSuccessPage(Model model) {
		return PageName.CONTACT_SUCCESS.getValue();
	}	
	
	/**
	 * Maps post method for the deleteUser request
	 * This page is not accessible without a valid credential so the exception is not expected to be thrown. 
	 * 
	 * @param message a string of message from the user input
	 * @param model a Model object holding model attributes
	 * @param request a HttpServletRequest object to automatically log the user out after deleting its account
	 * @return the JSP name for the main page if no exception is caught, the login page otherwise
	 */
	@PostMapping("/deleteUser")
	public String deleteUser(@RequestParam(ControllerUtilities.MESSAGE_ATTRIBUTE) String message, Model model, HttpServletRequest request) {
		try {
			// Send email to admin
			Credential credential = ControllerUtilities.getCredential();
			String mailFrom = ControllerUtilities.getUserEmail();
			String subject = String.format("User account #%d (%s) was deleted", credential.getId(), credential.getUsername());
			message = "From: " + mailFrom + "\n" + message;
			logger.log(Level.INFO,"Sender?= {0}, Subject?= {1}, Message?= {2}\n", new String[] {mailFrom, subject, message});			
			
			emailService.sendSimpleMessage(mailFrom, username, subject, message);
			
			model.addAttribute("title", "Deletion Success");
			model.addAttribute("messageHead","You have successfully deleted your account.");
			model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE,"We also received your note. We hope to see you again!");
			
			ControllerUtilities.credentialService.delete(credential);	
			ControllerUtilities.logoutWithHttpServletRequest(request);
			return showContactSuccessPage(model);
		} catch (CredentialNotFoundException e) {
			model.addAttribute(ControllerUtilities.MESSAGE_ATTRIBUTE, e.getMessage()+" before deleting your account.");
			return PageName.LOGIN.getValue();
		}
	}
	
}
