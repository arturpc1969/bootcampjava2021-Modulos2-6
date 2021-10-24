package br.com.alura.livraria.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {
	
	@Mock
	private LivroRepository livroRepository;
	
	@Mock
	private AutorRepository autorRepository;
	
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
		
		Mockito
		.when(autorRepository.findById(formDto.getAutorId()))
		.thenReturn(Optional.of((new Autor(1l, "", "", LocalDate.now(), ""))));
		
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
