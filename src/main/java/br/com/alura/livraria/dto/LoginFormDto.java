package br.com.alura.livraria.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginFormDto {
	
	@NotBlank
	private String login;
	
	@NotBlank
	private String senha;
}
