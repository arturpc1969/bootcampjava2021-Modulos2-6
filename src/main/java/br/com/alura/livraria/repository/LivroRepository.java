package br.com.alura.livraria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.livraria.dto.ItemAutorDto;
import br.com.alura.livraria.modelo.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

	@Query("select new br.com.alura.livraria.dto.ItemAutorDto(liv.autor as autor,"
			+ "	count(liv.livroId) as quantidadeLivros,"
			+ "	(count(liv.livroId)*1.0 / (select count(l.livroId) from Livro l)*1.0)*100.0 as percentual)"
			+ " from Livro liv " + " group by liv.autor")
	List<ItemAutorDto> relatorioLivrosPorAutor();

}
