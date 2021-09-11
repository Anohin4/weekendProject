package com.example.weekendproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMailService {
    final
    JavaMailSender javaMailSender;

    public SendMailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String to, String token) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom("NewsService");
        simpleMailMessage.setSubject("Activation Link");
        simpleMailMessage.setText("Hello \n This is your activation link: \n  http://localhost:8080/activation/" + token);
        javaMailSender.send(simpleMailMessage);
    }
}
