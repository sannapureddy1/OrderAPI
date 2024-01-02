package com.orderAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.orderAPI.entities.User;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;
	

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("xampleapi@gmail.com");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
    }
    
    public String accountCreationResponse(User temp) {
    	String welcomeMessage = "Dear "+ temp.getUserName() +" ,\n\n" +
    	        "Exciting news! Your OrderAPI account is now active. Here are your details:\n\n" +
    	        "Username: "+ temp.getUserName() + "\n" +
    	        "Email: "+ temp.getEmail() + "\n\n" +
    	        "Welcome aboard!";
    	return welcomeMessage;
    }
	
}
