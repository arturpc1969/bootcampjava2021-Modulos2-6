package br.com.alura.livraria.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroFormDto {
	
	@NotBlank
	@Size(min = 10)
	private String titulo;
	
	@PastOrPresent
	private LocalDate dataDeLancamento;
	
	@Min(100)
	private int numeroDePaginas;
	
	private AutorFormDto autor;

}
