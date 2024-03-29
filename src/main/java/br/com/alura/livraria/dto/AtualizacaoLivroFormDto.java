package br.com.alura.livraria.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizacaoLivroFormDto extends LivroFormDto{
	
	@NotNull
	private Long livroId;

}
