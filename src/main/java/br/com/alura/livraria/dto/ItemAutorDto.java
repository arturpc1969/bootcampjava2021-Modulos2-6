package br.com.alura.livraria.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonGetter;

import br.com.alura.livraria.modelo.Autor;
import lombok.Getter;

@Getter
public class ItemAutorDto {

	private Autor autor;
	private Long quantidadeLivros;
	private BigDecimal percentual;

	@JsonGetter("autor")
	public String getNomeAutor() {
		return autor.getNome();
	}

	public ItemAutorDto(Autor autor, Long quantidadeLivros, Long quantidadeTotalLivros) {
		this.autor = autor;
		this.quantidadeLivros = quantidadeLivros;
		this.percentual = new BigDecimal(quantidadeLivros)
				.divide(new BigDecimal(quantidadeTotalLivros), 4, RoundingMode.HALF_UP)
				.multiply(new BigDecimal("100"))
				.setScale(2);
	}

}
