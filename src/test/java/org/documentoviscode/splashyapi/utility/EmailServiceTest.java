package org.documentoviscode.splashyapi.utility;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.springframework.mail.MailSendException;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailServiceTest {

    @Autowired
    EmailService emailService;

    @Disabled("Avoid Spam")
    @Test
    void whenCorrectInput_sendSimpleMessage_ShouldSendEmail() {
        assertDoesNotThrow(() -> emailService.sendSimpleMessage("documentovisco@gmail.com", "Test", "Siema Eniu"));
    }

    @Test
    void whenInvalidRecipient_sendSimpleMessage_ShouldThrow() {
        assertThrows(MailSendException.class, () -> emailService.sendSimpleMessage("123", "Test", "Siema Eniu"));
    }

    @Disabled("Avoid Spam")
    @Test
    void whenCorrectInput_sendMessageWithPdf_ShouldSendEmail() {
        assertDoesNotThrow(() -> emailService.sendMessageWithPdf("documentovisco@gmail.com", "Test", "Siema Eniu","src/test/resources/test.pdf"));
    }

    @Test
    void whenInvalidRecipient_sendMessageWithPdf_ShouldThrow() {
        assertThrows(MailSendException.class, () -> emailService.sendMessageWithPdf("123", "Test", "Siema Eniu", "src/test/resources/test.pdf"));
    }

    @Test
    void whenCantFindPdf_sendMessageWithPdf_ShouldThrow() {
        assertThrows(MailSendException.class, () -> emailService.sendMessageWithPdf("documentovisco@gmail.com", "Test", "Siema Eniu", "randompath"));
    }
}