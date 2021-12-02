package br.com.alura.livraria.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioFormDto {
	
	@NotBlank
	@Pattern(regexp = "[a-zA-ZáàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\s]{2,100}", message = "{formato.nome.invalido}")
	private String nome;
	
	@NotBlank
	@Pattern(regexp = "[a-zA-Z]{4,20}", message = "{formato.login.invalido}")
	private String login;
	
	@NotBlank
	@Email
	private String email;
	
	@NotNull
	private Long perfilId;

}
