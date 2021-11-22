package br.com.alura.livraria.infra;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.alura.livraria.modelo.Mail;

@Service
@Profile({"dev", "test"})
public class EmailSenderServiceFake implements EmailSenderService {

    @Override
    @Async
	public void sendEmail(Mail mail, String template) throws MessagingException, IOException {
    	
    	String destinatario = mail.getMailTo();
    	String assunto = mail.getSubject();
    	
    	Map<String, Object> variaveis = mail.getProps();
    	
    	String login = (String) variaveis.get("login");
    	String senha = (String) variaveis.get("senha");
    	String nome = (String) variaveis.get("nome");
    	String mensagem = (String) variaveis.get("mensagem");
    			
    	System.out.println("Enviando e-mail para o usuário " + nome + "...");
		System.out.println("Para: " + destinatario);
		System.out.println("Assunto: " + assunto);
		System.out.println("Seu cadastro no sistema Livraria OnLine foi realizado com sucesso.");
		System.out.println(mensagem);
		System.out.println("Login: " + login);
		System.out.println("Senha: " + senha);
    	
    }

}