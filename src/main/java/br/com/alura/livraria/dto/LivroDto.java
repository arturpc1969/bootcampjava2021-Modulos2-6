package br.com.alura.livraria.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroDto {
	
	private Long livroId;
	private String titulo;
	
	@JsonFormat(pattern = "dd/MM/yyyy") 
	private LocalDate dataDeLancamento;
	
	private Integer numeroDePaginas;
	private AutorDto autor;
	
	@JsonGetter("autor")
	public Long getIdAutorDto() {
		return autor.getAutorId();
	}

}
