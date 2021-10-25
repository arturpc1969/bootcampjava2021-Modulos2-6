package br.com.alura.livraria.controller;

import java.net.URI;

import javax.naming.directory.AttributeInUseException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.livraria.dto.AtualizacaoAutorFormDto;
import br.com.alura.livraria.dto.AutorDto;
import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.service.AutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/autores")
@Api(tags = "Autor")
public class AutorController {
	
	@Autowired
	private AutorService service;
	
	@GetMapping
	@ApiOperation("Listar autores")
	public Page<AutorDto> listar(Pageable paginacao) {
		return service.listar(paginacao);
	}
	
	@PostMapping
	@ApiOperation("Cadastrar novo autor")
	public ResponseEntity<AutorDto> cadastrar(@RequestBody @Valid AutorFormDto dto, UriComponentsBuilder uriBuilder) {
		AutorDto autorDto = service.cadastrar(dto);
		URI uri = uriBuilder
				.path("/autores/{autorId}")
				.buildAndExpand(autorDto.getAutorId())
				.toUri();
		return ResponseEntity.created(uri).body(autorDto);
	}
	
	@PutMapping
	@ApiOperation("Atualizar um autor")
	public ResponseEntity<AutorDto> atualizar(@RequestBody @Valid AtualizacaoAutorFormDto dto) {
		AutorDto autorAtualizado = service.atualizar(dto);		
		return ResponseEntity.ok(autorAtualizado);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation("Excluir um autor")
	public ResponseEntity<AutorDto> excluir(@PathVariable @NotNull Long id) throws AttributeInUseException {
		service.excluir(id);		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	@ApiOperation("Detalhar um autor")
	public ResponseEntity<AutorDto> detalhar(@PathVariable @NotNull Long id) {
		AutorDto autorDetalhado = service.detalhar(id);		
		return ResponseEntity.ok(autorDetalhado);
	}

}





