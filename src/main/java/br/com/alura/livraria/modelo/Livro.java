package br.com.alura.livraria.modelo;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "livros")
public class Livro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long livroId;
	private String titulo;
	private LocalDate dataDeLancamento;
	private Integer numeroDePaginas;
	
	@ManyToOne
	@JoinColumn(name="autor_id")
	private Autor autor;

	public Livro(String titulo, LocalDate dataDeLancamento, Integer numeroDePaginas, Autor autor) {
		this.titulo = titulo;
		this.dataDeLancamento = dataDeLancamento;
		this.numeroDePaginas = numeroDePaginas;
		this.autor = autor;
	}
	
	

}
