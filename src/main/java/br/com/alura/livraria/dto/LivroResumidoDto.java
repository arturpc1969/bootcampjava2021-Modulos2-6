package br.com.alura.livraria.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LivroResumidoDto {
	
	private Long livroId;
	private String titulo;
	
	@JsonFormat(pattern = "dd/MM/yyyy") 
	private LocalDate dataDeLancamento;
	
	private Integer numeroDePaginas;

}
