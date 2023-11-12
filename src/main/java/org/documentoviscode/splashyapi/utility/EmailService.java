package org.documentoviscode.splashyapi.utility;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

@Component
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    public void sendSimpleMessage(
            String to,
            String subject,
            String text
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ssplashytv@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendHTMLMessage(String to, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setFrom("ssplashytv@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        Context context = new Context();
        context.setVariable("message", text);

        String htmlContent = templateEngine.process("email", context);
        helper.setText(htmlContent, true);

        emailSender.send(mimeMessage);
    }

    public void sendShortenedFacture(String to, String username, String packageName, String packagePrice) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom("ssplashytv@gmail.com");
        helper.setTo(to);
        helper.setSubject("Podsumowanie Twojego zamówienia");

        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("package", packageName);
        context.setVariable("price", packagePrice);
        context.setVariable("sexEnd", username.charAt(username.length() - 1) != 'a' ? 'e' : 'a');

        String htmlContent = templateEngine.process("email", context);
        helper.setText(htmlContent, true);

        FileSystemResource file = new FileSystemResource(new File("Facture.pdf"));
        helper.addAttachment("Facture.pdf", file);

        emailSender.send(mimeMessage);
    }

    public void sendMessageWithPdf(
            String to,
            String subject,
            String text,
            String pathToAttachment
    ) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("ssplashytv@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("Invoice.pdf", file);

        emailSender.send(message);
    }
}
