package br.com.alura.livraria.infra;

public interface EnviadorDeEmail {

	void enviaEmail(String destinatário, String assunto, String mensagem);

}