package br.com.gab.Sistema.ERP.EmailSender;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@ConditionalOnProperty(value = "dev.test", havingValue = "true")
public class MockSendEmailImpl implements SendEmail {

    @Override
    public void sendEmail(String recipient, String conteudo) throws MessagingException {
        return;
    }
}
