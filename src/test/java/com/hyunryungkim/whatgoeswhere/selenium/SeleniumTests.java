package com.hyunryungkim.whatgoeswhere.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hyunryungkim.whatgoeswhere.config.WebAppConfig;
import com.hyunryungkim.whatgoeswhere.controllers.PageName;
import com.hyunryungkim.whatgoeswhere.exceptions.CredentialAlreadyExistsException;
import com.hyunryungkim.whatgoeswhere.exceptions.CredentialNotFoundException;
import com.hyunryungkim.whatgoeswhere.models.Credential;
import com.hyunryungkim.whatgoeswhere.models.ModelUtilities;
import com.hyunryungkim.whatgoeswhere.models.User;
import com.hyunryungkim.whatgoeswhere.services.CredentialService;

import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Run the Spring project first and then run this test.
 */
@ExtendWith(SpringExtension.class)  // This doesn't really change.
@ContextConfiguration(classes = { WebAppConfig.class })
@WebAppConfiguration("WebContent") // Letting it know where web content is (folder name)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SeleniumTests {
	
	private WebDriver driver;
	private String path = "http://localhost:8080/" + ModelUtilities.ROOT_DIRECTORY + "/";
	User user1, user2;
	String password1, password2;
	Credential credentialUser, credentialAdmin;
	CredentialService credentialService;
	
	@Autowired
	public SeleniumTests(WebDriver driver, CredentialService credentialService) {
		this.driver = driver;
		this.credentialService = credentialService;
	}
	
	@BeforeAll
	public void setup() throws CredentialAlreadyExistsException {
		// Users for corresponding credential
 		LocalDate now_ld = LocalDate.now();
 		user1 = new User("testuser1@email.com", "FirstName1", "LastName1", now_ld, null);
 		user2 = new User("testuser2@email.com", "FirstName2","LastName2", now_ld, null);
 		
 		// Add credentials to the database to use for testing if they don't already exist.
 		password1 = "testuser11234";
 		password2 = "testuser21234";
	    credentialUser = new Credential("testuser1", password1, user1); 
	    credentialAdmin = new Credential("testuser2", password2, user2);
	    credentialAdmin.setUserRole("ROLE_ADMIN");
	    
	    credentialUser = credentialService.add(credentialUser);
	    credentialAdmin = credentialService.add(credentialAdmin); 
	}
	
	@Test
//	@Disabled
	void testLoginPage() {
		driver.get(path + PageName.LOGIN.getValue());
		assertEquals("Sign In", driver.getTitle());
	}
	
	@Test	
//	@Disabled
	void testLogin() throws InterruptedException {
		driver.get(path + PageName.LOGIN.getValue());
		WebElement usernameInput = driver.findElement(By.cssSelector("#input-username"));
		usernameInput.sendKeys(credentialUser.getUsername());
		
		WebElement passwordInput = driver.findElement(By.cssSelector("body > section > div > form > fieldset > div:nth-child(4) > input[type=password]"));
		passwordInput.sendKeys(password1);
		
		assertTrue(credentialService.checkIfValidOldPassword(password1, credentialUser.getPassword()));
		
		WebElement submitInput = driver.findElement(By.cssSelector("body > section > div > form > input"));
		submitInput.click();
		
		assertEquals("What Goes Where in Redmond", driver.getTitle());
		
		// Sign out
		driver.get(path + PageName.LOGOUT.getValue());		
	}
	
	@Test	
//	@Disabled
	void testProfile() throws InterruptedException {
		driver.get(path + PageName.PROFILE.getValue());
		WebElement usernameInput = driver.findElement(By.cssSelector("#input-username"));
		usernameInput.sendKeys(credentialUser.getUsername());
		
		WebElement passwordInput = driver.findElement(By.cssSelector("body > section > div > form > fieldset > div:nth-child(4) > input[type=password]"));
		passwordInput.sendKeys(password1);
		
		WebElement submitInput = driver.findElement(By.cssSelector("body > section > div > form > input"));
		submitInput.click();		
		
		assertEquals("My Profile", driver.getTitle());
		
		WebElement username= driver.findElement(By.cssSelector("#table-profile > tbody > tr:nth-child(2) > td"));
		assertTrue(username.getText().contains(credentialUser.getUsername()));	
		
		driver.get(path + PageName.ADMIN.getValue());		
		assertEquals("Access Denied", driver.getTitle());

		// Sign out
		driver.get(path + PageName.LOGOUT.getValue());	
	}
	
	@Test	
//	@Disabled
	void testLoginAdmin() throws InterruptedException {
		driver.get(path+PageName.LOGIN.getValue());
		WebElement usernameInput = driver.findElement(By.cssSelector("#input-username"));
		usernameInput.sendKeys(credentialAdmin.getUsername());
		
		WebElement passwordInput = driver.findElement(By.cssSelector("body > section > div > form > fieldset > div:nth-child(4) > input[type=password]"));
		passwordInput.sendKeys(password2);
		
		WebElement submitInput = driver.findElement(By.cssSelector("body > section > div > form > input"));
		submitInput.click();
		
		WebElement dropdown = driver.findElement(By.cssSelector("#navbarDropdown"));
		dropdown.click();
		WebElement adminMenu= driver.findElement(By.cssSelector("#ddi-4"));
		adminMenu.click();		
		assertEquals("Admin Page", driver.getTitle());	

		// Sign out
		driver.get(path + PageName.LOGOUT.getValue());	
	}
	
	@AfterAll
	void clearSetup() {
		try {
			credentialService.delete(credentialUser);
			credentialService.delete(credentialAdmin);			
		} catch (CredentialNotFoundException e) {
			e.printStackTrace();
		}				
	}
}
