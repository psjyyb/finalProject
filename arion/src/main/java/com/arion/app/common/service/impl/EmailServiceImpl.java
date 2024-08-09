package com.arion.app.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.arion.app.common.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
    private JavaMailSender mailSender;
	
    public void sendSimpleEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("ass6728@naver.com");
        message.setSubject("ARION 정기결제 5일전입니다.");
        message.setText("안녕하세요 고객님! 저희 ARION 을 이용해 주셔서 감사합니다. 정기결제 5일 전이니 계좌를 확인해 주시고 결제가 원할하게 이루어질수있도록 금액을 채워주시기 바랍니다 감사합니다!");
        message.setFrom("psjyyb3418@gmail.com");

        mailSender.send(message);
    }
}
