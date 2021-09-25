package br.com.alura.livraria.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroDto {
	
	private int livroId;
	private String titulo;
	
	@JsonFormat(pattern = "dd/MM/yyyy") 
	private LocalDate dataDeLancamento;
	
	private int numeroDePaginas;
	private AutorDto autor;
//	private int autorId;
//	private String nome;
//	private String email;
//	private LocalDate dataDeNascimento;
	
	@JsonGetter("autor")
	public String getNomeAutorDto() {
		//return autor == null ? null : autor.getNome();
		return autor.getNome();
	}

}
