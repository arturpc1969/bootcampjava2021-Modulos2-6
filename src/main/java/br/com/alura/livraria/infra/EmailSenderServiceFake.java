package br.com.alura.livraria.infra;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import br.com.alura.livraria.modelo.Mail;

@Service
@Profile({"dev", "test"})
public class EmailSenderServiceFake implements EmailSenderService {

    @Override
	public void sendEmail(Mail mail) throws MessagingException, IOException {
    	
    }

}