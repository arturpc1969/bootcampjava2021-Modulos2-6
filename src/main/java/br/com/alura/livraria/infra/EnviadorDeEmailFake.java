package br.com.alura.livraria.infra;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Profile({"dev", "test"})
public class EnviadorDeEmailFake implements EnviadorDeEmail {
	
	@Override
	@Async
	public void enviaEmail(String destinatário, String assunto, String mensagem) {
		
		System.out.println("Enviando e-mail...");
		System.out.println("Para: " + destinatário);
		System.out.println("Assunto: " + assunto);
		System.out.println("Mensagem: " + mensagem);
		
	}
	
	
}
