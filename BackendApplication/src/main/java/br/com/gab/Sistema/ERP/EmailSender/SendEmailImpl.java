package br.com.gab.Sistema.ERP.EmailSender;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@ConditionalOnProperty(value = "dev.test", havingValue = "false")
public class SendEmailImpl implements SendEmail {

    @Override
    public void sendEmail(String recipient, String conteudo) throws MessagingException {

        if(recipient == null || conteudo == null){
            return;
        }

        String email = "gabriwlmarcelino165@gmail.com";
        String senha = "bcbdpowvdbqxrigk";

        Properties prop = new Properties();

        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.starttls.enable", "true");

        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, senha);
            }
        });

        Message message = prepareMessage(session, email, recipient, conteudo);
        System.out.println("INICIO");
        Transport.send(message);
        System.out.println("TERMINOU");

    }

    public static Message prepareMessage(Session session, String email, String recipient, String conteudo) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(" -> NOVA SENHA <-");
            message.setText(conteudo);
            return message;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        SendEmailImpl sendEmail = new SendEmailImpl();
        try {
            sendEmail.sendEmail("gabrielmarcelino165@gmail.com", "TESTE");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}