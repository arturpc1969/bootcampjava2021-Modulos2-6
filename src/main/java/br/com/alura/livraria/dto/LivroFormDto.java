package br.com.alura.livraria.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LivroFormDto {
	
	@NotBlank
	@Size(min = 10, message = "{tamanho.titulo.invalido}")
	private String titulo;
	
	@PastOrPresent
	private LocalDate dataDeLancamento;
	
	@Min(100)
	private Integer numeroDePaginas;
	
	@NotNull
	private Long autorId;

}
