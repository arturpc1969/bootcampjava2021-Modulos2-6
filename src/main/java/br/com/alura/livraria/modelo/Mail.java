package br.com.alura.livraria.modelo;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {

    private String from;
    private String mailTo;
    private String subject;
    private List<Object> attachments;
    private Map<String, Object> props;

    public Mail() {}

}