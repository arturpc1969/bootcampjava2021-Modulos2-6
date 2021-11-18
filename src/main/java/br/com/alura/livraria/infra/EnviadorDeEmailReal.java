package br.com.alura.livraria.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class EnviadorDeEmailReal implements EnviadorDeEmail {
	
	@Autowired
	private JavaMailSender mailSender;

	@Override
	@Async
	public void enviaEmail(String destinatário, String assunto, String mensagem) {
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(destinatário);
		email.setSubject(assunto);
		email.setText(mensagem);
		
		mailSender.send(email);
		
	}
	
	
}
