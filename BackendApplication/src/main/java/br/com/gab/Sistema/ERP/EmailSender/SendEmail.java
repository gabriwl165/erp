package br.com.gab.Sistema.ERP.EmailSender;

import javax.mail.MessagingException;

public interface SendEmail {
    void sendEmail(String recipient, String conteudo) throws MessagingException;
}
