package org.example.spring_boot_mini_project.service.ServiceImp;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;



@Service

public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    public void sendOtpEmail(String recipientEmail, String otp, String body) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom("kosalsreyka3@gmail.com");
        mimeMessageHelper.setTo(recipientEmail);
        mimeMessageHelper.setSubject(otp);
        Context context = new Context();
        context.setVariable("otp", body);
        String processedString = templateEngine.process("email", context);

        mimeMessageHelper.setText(processedString, true);

        javaMailSender.send(mimeMessage);
    }

}
