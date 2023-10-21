package com.contoh.cv.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

  private JavaMailSender javaMailSender;

  public void sendSimpleEmail(String to, String subject, String text){
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);

    javaMailSender.send(message);
  }
}
