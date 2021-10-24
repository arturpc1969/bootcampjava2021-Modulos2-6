package br.com.alura.livraria.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.alura.livraria.dto.ItemAutorDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class LivroRepositoryTest {

	@Autowired
	private LivroRepository repository;

	@Autowired
	private TestEntityManager em;

	private Autor criaAutorParaTeste(String nome) {
		return new Autor(nome, "autor@email.com", LocalDate.parse("1935-10-23"), "Lorem Ipsum");
	}

	private Livro criaLivroParaTeste(Autor autor) {
		return new Livro("Título", LocalDate.now(), 120, autor);
	}

	@Test
	void deveriaRetornarRelatorioLivrosPorAutor() {

		Autor autor1 = criaAutorParaTeste("Nome do Autor1");
		em.persist(autor1);
		Autor autor2 = criaAutorParaTeste("Nome do Autor2");
		em.persist(autor2);
		Autor autor3 = criaAutorParaTeste("Nome do Autor3");
		em.persist(autor3);

		Livro livro1 = criaLivroParaTeste(autor1);
		em.persist(livro1);
		Livro livro2 = criaLivroParaTeste(autor2);
		em.persist(livro2);
		Livro livro3 = criaLivroParaTeste(autor3);
		em.persist(livro3);
		Livro livro4 = criaLivroParaTeste(autor2);
		em.persist(livro4);
		Livro livro5 = criaLivroParaTeste(autor3);
		em.persist(livro5);
		Livro livro6 = criaLivroParaTeste(autor3);
		em.persist(livro6);
		Livro livro7 = criaLivroParaTeste(autor3);
		em.persist(livro7);
		Livro livro8 = criaLivroParaTeste(autor2);
		em.persist(livro8);
		Livro livro9 = criaLivroParaTeste(autor3);
		em.persist(livro9);
		Livro livro10 = criaLivroParaTeste(autor1);
		em.persist(livro10);

		List<ItemAutorDto> relatorio = repository.relatorioLivrosPorAutor();

		Assertions.assertThat(relatorio).hasSize(3)
				.extracting(ItemAutorDto::getNomeAutor, ItemAutorDto::getQuantidadeLivros, ItemAutorDto::getPercentual)
				.containsExactlyInAnyOrder(Assertions.tuple("Nome do Autor1", 2l, new BigDecimal("20.00")),
						Assertions.tuple("Nome do Autor2", 3l, new BigDecimal("30.00")),
						Assertions.tuple("Nome do Autor3", 5l, new BigDecimal("50.00")));

	}

}
