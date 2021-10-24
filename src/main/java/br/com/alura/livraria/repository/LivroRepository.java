package br.com.alura.livraria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.livraria.dto.ItemAutorDto;
import br.com.alura.livraria.modelo.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

	@Query("select new br.com.alura.livraria.dto.ItemAutorDto(liv.autor as autor,"
			+ "	count(liv.livroId) as quantidadeLivros,"
			+ "	(select count(l.livroId) from Livro l) as quantidadeTotalLivros) from Livro liv "
			+ " group by liv.autor")
	List<ItemAutorDto> relatorioLivrosPorAutor();

}
