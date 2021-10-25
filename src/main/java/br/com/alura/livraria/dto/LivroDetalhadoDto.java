package br.com.alura.livraria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LivroDetalhadoDto extends LivroResumidoDto{
	
	private AutorDto autor;

}
