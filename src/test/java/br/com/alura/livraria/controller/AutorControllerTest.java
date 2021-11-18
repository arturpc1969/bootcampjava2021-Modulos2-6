package br.com.alura.livraria.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import br.com.alura.livraria.modelo.Perfil;
import br.com.alura.livraria.modelo.Usuario;
import br.com.alura.livraria.repository.PerfilRepository;
import br.com.alura.livraria.repository.UsuarioRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AutorControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	private String token;
	
	@BeforeEach
	public void gerarToken() {
		Usuario logado = new Usuario("Artur", "artur", "123456", "email@email.com");
		Perfil admin = perfilRepository.findById(1l).get();
		logado.adicionarPerfil(admin);
		usuarioRepository.save(logado);
		Authentication authentication = new UsernamePasswordAuthenticationToken(logado, logado.getLogin());
		this.token = tokenService.gerarToken(authentication);		
	}

	@Test
	void naoDeveriaCadastrarAutorComDadosIncompletos() throws Exception {

		String json = "{}";

		mvc.perform(post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isBadRequest());
	}

	@Test
	void deveriaCadastrarAutorComDadosValidos() throws Exception {

		String json = "{\"nome\":\"Nome do Autor\", \"email\":\"autor@email.com\", \"dataDeNascimento\":\"1935-10-23\", \"miniCurriculo\":\"Lorem ipsum\"}";
		String jsonEsperado = "{\"nome\":\"Nome do Autor\", \"email\":\"autor@email.com\", \"dataDeNascimento\":\"23/10/1935\", \"miniCurriculo\":\"Lorem ipsum\"}";

		mvc.perform(post("/autores")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isCreated()).andExpect(header().exists("Location"))
				.andExpect(content().json(jsonEsperado));
	}

}
