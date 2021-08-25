package com.hyunryungkim.whatgoeswhere.services.impl;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hyunryungkim.whatgoeswhere.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	private JavaMailSender emailSender;
	private SimpleMailMessage template;
	
	@Autowired
	public EmailServiceImpl(JavaMailSender emailSender, SimpleMailMessage template) {
		this.emailSender = emailSender;
		this.template = template;
	}
	
	@Override
	public void sendSimpleMessage(String from, String to, String subject, String text) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
		
	}

	@Override
	public void sendSimpleMessageUsingTemplate(String from, String to, String subject, String... templateArgs) {
		String text = String.format(template.getText(), templateArgs);
		sendSimpleMessage(from, to,subject,text);
		
	}

	@Override
	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
		// TODO Auto-generated method stub
//		MimeMessage message = emailSender.createMimeMessage();
//	     
//	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
//	    
//	    helper.setFrom("noreply@baeldung.com");
//	    helper.setTo(to);
//	    helper.setSubject(subject);
//	    helper.setText(text);
//	        
//	    FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
//	    helper.addAttachment("Invoice", file);
//
//	    emailSender.send(message);
		
	}

}
