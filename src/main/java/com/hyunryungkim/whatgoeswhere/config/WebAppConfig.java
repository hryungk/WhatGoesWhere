package com.hyunryungkim.whatgoeswhere.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Web Application Configuration class
 * Contains view resolver and resource handler methods.
 * 
 * @author Hyunryung Kim
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.hyunryungkim.whatgoeswhere")
public class WebAppConfig implements WebMvcConfigurer{
	/**
	 * Attaches prefix and suffix for the controller mapping
	 * 
	 * @return an Internal Resource View Resolver
	 * @see org.springframework.web.servlet.view.InternalResourceViewResolver
	 */
	@Bean
	InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver irvr = new InternalResourceViewResolver();
		irvr.setPrefix("/WEB-INF/views/");
		irvr.setSuffix(".jsp");
		return irvr;
//		return new InternalResourceViewResolver("/WEB-INF/views/",".jsp");
	}

//	@Bean
//	SimpleMappingExceptionResolver exceptionResolver() {
//		SimpleMappingExceptionResolver smer = new SimpleMappingExceptionResolver();
//		Properties props = new Properties();
//		props.put("java.lang.Exception", "error");
//		smer.setExceptionMappings(props);
//		return smer;
//	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
//		registry.addResourceHandler("**/resources/**").addResourceLocations("/resources/"); // An alternative
		registry.addResourceHandler("/scripts/**").addResourceLocations("/resources/scripts/");
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);		
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
		mailSender.setUsername(map.get("username"));
		mailSender.setPassword(map.get("passwordMac")); 		
		
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
		
		return mailSender;
	}
	
	@Bean
	public SimpleMailMessage templateSimpleMessage() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText("This is the test email template for your email:\n%s\n");
		return message;
	}
	
}
