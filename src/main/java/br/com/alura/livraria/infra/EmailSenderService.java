package br.com.alura.livraria.infra;

import java.io.IOException;

import javax.mail.MessagingException;

import br.com.alura.livraria.modelo.Mail;

public interface EmailSenderService {

	void sendEmail(Mail mail, String template) throws MessagingException, IOException;

}