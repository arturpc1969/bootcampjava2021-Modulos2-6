package br.com.alura.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.livraria.dto.AutorDto;
import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.repository.AutorRepository;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

	@Mock
	private AutorRepository autorRepository;

	@InjectMocks
	private AutorService service;

	private AutorFormDto criaAutorFormDto() {
		AutorFormDto formDto = new AutorFormDto("Nome do Autor", "autor@email.com", LocalDate.parse("1935-10-23"),
				"Lorem Ipsum");
		return formDto;
	}

	@Test
	void deveriaCadastrarAutorComDadosValidos() {
		AutorFormDto formDto = criaAutorFormDto();

		AutorDto dto = service.cadastrar(formDto);

		assertEquals(formDto.getNome(), dto.getNome());
		assertEquals(formDto.getEmail(), dto.getEmail());
		assertEquals(formDto.getDataDeNascimento(), dto.getDataDeNascimento());
		assertEquals(formDto.getMiniCurriculo(), dto.getMiniCurriculo());

		Mockito.verify(autorRepository).save(Mockito.any());

	}

}
