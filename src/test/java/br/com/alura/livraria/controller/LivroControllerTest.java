package br.com.alura.livraria.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.alura.livraria.infra.security.TokenService;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Perfil;
import br.com.alura.livraria.modelo.Usuario;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.PerfilRepository;
import br.com.alura.livraria.repository.UsuarioRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class LivroControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private TokenService tokenService;
	
	private String token;
	
	private Autor criaAutorParaTeste(String nome) {
		return new Autor(nome, "autor@email.com", LocalDate.parse("1935-10-23"), "Lorem Ipsum");
	}
	
	@BeforeEach
	private void gerarToken() {
		Usuario logado = new Usuario("Artur", "artur", "123456", "email@email.com");
		Perfil admin = perfilRepository.findById(1l).get();
		logado.adicionarPerfil(admin);
		usuarioRepository.save(logado);
		Authentication authentication = new UsernamePasswordAuthenticationToken(logado, logado.getLogin());
		this.token = tokenService.gerarToken(authentication);
	}

	@Test
	void naoDeveriaCadastrarLivroComDadosIncompletos() throws Exception {
		String json = "{}";

		mvc.perform(post("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isBadRequest());
	}

	@Test
	void deveriaCadastrarLivroComDadosValidos() throws Exception {
		Autor autor1 = criaAutorParaTeste("Nome do Autor1");
		autorRepository.save(autor1);

		String json = "{\"titulo\":\"Nome do livro\", \"dataDeLancamento\":\"2010-06-13\", \"numeroDePaginas\":322, \"autorId\":"
				+ autor1.getAutorId() + "}";
		String jsonEsperado = "{\"titulo\":\"Nome do livro\", \"dataDeLancamento\":\"13/06/2010\", \"numeroDePaginas\":322, \"autorId\":"
				+ autor1.getAutorId() + "}";

		mvc.perform(post("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isCreated()).andExpect(header().exists("Location"))
				.andExpect(content().json(jsonEsperado));
	}

	@Test
	void naoDeveriaCadastrarLivroComAutorInexistente() throws Exception {
		String json = "{\"titulo\":\"Nome do livro\", \"dataDeLancamento\":\"2010-06-13\", \"numeroDePaginas\":322, \"autorId\":0}";

		mvc.perform(post("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isInternalServerError());
	}

}
