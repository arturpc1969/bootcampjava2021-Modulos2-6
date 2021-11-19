package br.com.alura.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;

import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class LivroServiceTest {
	
	@Mock
	private LivroRepository livroRepository;
	
	@Mock
	private AutorRepository autorRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks
	private LivroService service;
	
	private LivroFormDto criaLivroFormDto() {
		LivroFormDto formDto = new LivroFormDto(
				"Título do livro 10",
				LocalDate.now(),
				100,
				1l);
		return formDto;
	}

	@Test
	void deveriaCadastrarLivroComTodosArgumentosValidos() {
		
		LivroFormDto formDto = criaLivroFormDto();
		Autor autor = new Autor(1l, "Nome do Autor", "autor@email.com", LocalDate.parse("1935-10-23"), "Lorem Ipsum");
		Livro livro = new Livro("Título do livro 10", LocalDate.now(), 100,	autor);
		LivroDto livroDto = new LivroDto(livro.getLivroId(), livro.getTitulo(), livro.getDataDeLancamento(), livro.getNumeroDePaginas(), livro.getAutor().getAutorId());
		
		Mockito
		.when(autorRepository.findById(formDto.getAutorId()))
		.thenReturn(Optional.of((autor)));
		
		Mockito.when(modelMapper.map(formDto, Livro.class)).thenReturn(livro);
		
		Mockito.when(modelMapper.map(livro, LivroDto.class)).thenReturn(livroDto);
		
		LivroDto dto = service.cadastrar(formDto);
		
		assertEquals(formDto.getTitulo(), dto.getTitulo());
		assertEquals(formDto.getDataDeLancamento(), dto.getDataDeLancamento());
		assertEquals(formDto.getNumeroDePaginas(), dto.getNumeroDePaginas());
		assertEquals(formDto.getAutorId(), dto.getAutorId());
		
		Mockito.verify(livroRepository).save(Mockito.any());
		
	}
	
	@Test
	void naoDeveriaCadastrarLivroComAutorInexistente() {
		
		LivroFormDto formDto = criaLivroFormDto();		
		Mockito.when(autorRepository.findById(formDto.getAutorId())).thenThrow(EntityNotFoundException.class);
		assertThrows(IllegalArgumentException.class,() -> service.cadastrar(formDto));
		
	}

}
