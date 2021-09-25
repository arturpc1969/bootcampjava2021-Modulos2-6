package br.com.alura.livraria.modelo;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Autor {
	
	private int autorId;
	private String nome;
	private String email;
	private LocalDate dataDeNascimento;
	private String miniCurriculo;
	
}


