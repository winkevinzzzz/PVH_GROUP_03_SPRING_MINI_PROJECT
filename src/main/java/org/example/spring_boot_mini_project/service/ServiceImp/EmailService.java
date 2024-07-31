package org.example.spring_boot_mini_project.service.ServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
//    private final JavaMailSender mailSender;
//
//    public EmailService(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }

//    public void sendNewMail(String to, String subject, String body) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("retsokhim2001@gmail.com");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//        mailSender.send(message);
//    }
@Autowired
private JavaMailSender javaMailSender;

    public void sendOtpEmail(String recipientEmail, String otp,String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("kosalsreyka3@gmail.com"); // Replace with your sender email
        msg.setTo(recipientEmail);
        msg.setSubject("Verify your email with opt code");
        msg.setText("Your OTP is: " + otp);
        msg.setText(body);
        javaMailSender.send(msg);
    }

}
