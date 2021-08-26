package com.hyunryungkim.whatgoeswhere.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SeleniumConfig {

	@Bean
	public WebDriver getWebDriver(Environment env) {
		System.setProperty("webdriver.chrome.driver", "/Users/HRK/programming/selenium/chromedriver");
		WebDriver driver = new ChromeDriver();
		return driver;
	}
}
