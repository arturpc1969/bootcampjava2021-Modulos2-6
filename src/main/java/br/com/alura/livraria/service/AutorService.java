package br.com.alura.livraria.service;

import java.util.List;

import javax.naming.directory.AttributeInUseException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.alura.livraria.dto.AtualizacaoAutorFormDto;
import br.com.alura.livraria.dto.AutorDto;
import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.dto.ItemAutorDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.LivroRepository;

@Service
public class AutorService {
	
	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Page<AutorDto> listar(Pageable paginacao) {
		Page<Autor> autores = autorRepository.findAll(paginacao);
		return autores
				.map(a -> modelMapper.map(a, AutorDto.class));
	}

	@Transactional
	public AutorDto cadastrar(AutorFormDto dto) {
		Autor autor = modelMapper.map(dto, Autor.class);
		autorRepository.save(autor);
		return modelMapper.map(autor, AutorDto.class);
	}

	@Transactional
	public AutorDto atualizar(AtualizacaoAutorFormDto dto) {
		Autor autor = autorRepository.getById(dto.getAutorId());
		autor.atualizarDados(dto.getNome(), dto.getEmail(), dto.getDataDeNascimento(), dto.getMiniCurriculo());		
		return modelMapper.map(autor, AutorDto.class);
	}

	@Transactional
	public void excluir(Long id) throws AttributeInUseException {	//TODO: refatorar esse método
		Autor autorParaExcluir = autorRepository.getById(id);
		
		int livrosDoAutor = 0;
		
		List<ItemAutorDto> autores = livroRepository.relatorioLivrosPorAutor();
		for (ItemAutorDto itemAutorDto : autores) {
			if(itemAutorDto.getNomeAutor().equals(autorParaExcluir.getNome())) {
				livrosDoAutor ++;
			}
		}
		if(livrosDoAutor == 0) {
			autorRepository.deleteById(id);
		} else {
			throw new AttributeInUseException("O autor possui livros cadastrados!");
		}
			
	}

	public AutorDto detalhar(Long id) {
		Autor autor = autorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		return modelMapper.map(autor, AutorDto.class);
	}

	

}
