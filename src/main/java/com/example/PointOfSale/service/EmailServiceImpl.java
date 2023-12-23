package com.example.PointOfSale.service;

import com.example.PointOfSale.model.Account;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl{
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("admin@gmail.com");
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setText(body);

        javaMailSender.send(simpleMailMessage);
    }

    public void sendMimeEmail(Account account, String subject, String url) throws MessagingException {
        Context context = new Context();
        context.setVariable("username", account.getUsername());
        context.setVariable("password", account.getUsername());
        context.setVariable("url", url);

        String text = templateEngine.process("emailTemplate", context);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setPriority(1);
        helper.setSubject(subject);
        helper.setFrom("admin@gmail.com");
        helper.setTo(account.getEmail());
        helper.setText(text, true);

        javaMailSender.send(message);
    }
}
