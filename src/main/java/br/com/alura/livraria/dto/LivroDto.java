package br.com.alura.livraria.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LivroDto extends LivroResumidoDto{
	
	private Long autorId;
	
	public LivroDto(Long livroId, String titulo, LocalDate dataDeLancamento, Integer numeroDePaginas, Long autorId) {
		this.setLivroId(livroId);
		this.setTitulo(titulo);
		this.setDataDeLancamento(dataDeLancamento);
		this.setNumeroDePaginas(numeroDePaginas);
		this.setAutorId(autorId);
	}

}
