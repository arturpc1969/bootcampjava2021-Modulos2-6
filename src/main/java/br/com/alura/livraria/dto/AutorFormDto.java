package br.com.alura.livraria.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AutorFormDto {

	@NotBlank
	private String nome;
	@NotBlank
	private String email;
	@Past
	private LocalDate dataDeNascimento;
	@NotBlank
	private String miniCurriculo;
	
}


