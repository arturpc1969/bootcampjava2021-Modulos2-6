package br.com.alura.livraria.modelo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"autor"})
@AllArgsConstructor
@NoArgsConstructor
public class Livro {
	
	private int livroId;
	private String titulo;
	private LocalDate dataDeLancamento;
	private int numeroDePaginas;
	private Autor autor;	

}
