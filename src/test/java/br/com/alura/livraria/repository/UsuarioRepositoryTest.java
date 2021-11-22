package br.com.alura.livraria.repository;

import java.util.List;
import java.util.NoSuchElementException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.alura.livraria.dto.PerfilDto;
import br.com.alura.livraria.modelo.Usuario;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private TestEntityManager em;

	private Usuario criaUsuarioParaTeste(String nome, String login, String senha, String email) {
		return new Usuario(nome, login, senha, email);
	}
	
	@BeforeEach
	void preparaUsuariosDeTeste() {
		PerfilDto perfilDto = new PerfilDto(2l, "ROLE_COMUM");

		Usuario usuario1 = criaUsuarioParaTeste("Nome1", "login1", "senha1", "login1@email.com");
		usuario1.adicionarPerfil(perfilDto);
		em.persist(usuario1);
		Usuario usuario2 = criaUsuarioParaTeste("Nome2", "login2", "senha2", "login2@email.com");
		usuario2.adicionarPerfil(perfilDto);
		em.persist(usuario2);
		Usuario usuario3 = criaUsuarioParaTeste("Nome3", "login3", "senha3", "login3@email.com");
		usuario3.adicionarPerfil(perfilDto);
		em.persist(usuario3);
	}

	@Test
	void deveriaRetornarUsuarioSolicitadoConformeId() {
		
		Long maiorId = 1l;
		List<Usuario> usuarios = repository.findAll();
		
		for (Usuario usuario : usuarios) {
			if (usuario.getId() > maiorId) {
				maiorId = usuario.getId();
			}
		}
		
		Usuario usuarioChamado = repository.carregarPorIdComPerfis(maiorId).get();
		
		Assertions.assertThat(usuarioChamado).hasFieldOrPropertyWithValue("nome", "Nome3");
		Assertions.assertThat(usuarioChamado.getPerfis()).hasToString("[Perfil(id=2, nome=ROLE_COMUM)]");
				
	}
	
	@Test
	void naoDeveriaRetornarUsuarioSolicitadoSeIdForInvalido() {
		
		Assertions.assertThatExceptionOfType(NoSuchElementException.class)
			.isThrownBy(() -> {
				Long maiorId = 1l;
				List<Usuario> usuarios = repository.findAll();
				
				for (Usuario usuario : usuarios) {
					if (usuario.getId() > maiorId) {
						maiorId = usuario.getId();
					}
				}
				
				Long idForaDoRange = maiorId +1l;
				
				Usuario usuarioChamado = repository.carregarPorIdComPerfis(idForaDoRange).get();
				
			});
		
	}
	
	@Test
	void deveriaRetornarUsuarioSolicitadoConformeLogin() {
		
		Usuario usuarioChamado = repository.findByLogin("login2").get();

		Assertions.assertThat(usuarioChamado).hasFieldOrPropertyWithValue("nome", "Nome2");
	}
	
	@Test
	void naoDeveriaRetornarUsuarioSolicitadoSeLoginForInvalido() {

		Assertions.assertThatExceptionOfType(NoSuchElementException.class)
			.isThrownBy(() -> {
				Usuario usuarioChamado = repository.findByLogin("login").get();
			});
		
	}
	
}
