package br.com.alura.livraria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.livraria.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	@Query("SELECT u from Usuario u JOIN FETCH u.perfis WHERE u.id = :id")
	Optional<Usuario> carregarPorIdComPerfis(Long id);
	
	Optional<Usuario> findByLogin(String nome);

}
