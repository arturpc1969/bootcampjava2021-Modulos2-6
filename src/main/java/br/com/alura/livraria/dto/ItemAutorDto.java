package br.com.alura.livraria.dto;

import com.fasterxml.jackson.annotation.JsonGetter;

import br.com.alura.livraria.modelo.Autor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemAutorDto {
	
	private Autor autor;
	private Long quantidadeLivros;
	private Double percentual;
	
	@JsonGetter("autor")
	public String getNomeAutor() {
		return autor.getNome();
	}
	
}
