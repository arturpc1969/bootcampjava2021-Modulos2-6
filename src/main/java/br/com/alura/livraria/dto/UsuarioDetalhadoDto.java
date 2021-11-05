package br.com.alura.livraria.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.livraria.modelo.Perfil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDetalhadoDto extends UsuarioDto{
	
	private List<Perfil> perfis = new ArrayList<>();

}
