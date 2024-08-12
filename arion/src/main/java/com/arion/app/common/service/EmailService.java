package com.arion.app.common.service;

public interface EmailService {
	public void sendSimpleEmail();
	
	public void sendEmail(String to, String subject, String text);
}
