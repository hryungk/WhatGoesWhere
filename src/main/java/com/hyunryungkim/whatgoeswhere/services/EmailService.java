package com.hyunryungkim.whatgoeswhere.services;

public interface EmailService {
	void sendSimpleMessage(String from, String to, String subject, String text);
	void sendSimpleMessageUsingTemplate(String from, String to, String subject, 
			String ...templateModel);
	void sendMessageWithAttachment(String to, String subject, String text, 
			String pathToAttachment);

}
