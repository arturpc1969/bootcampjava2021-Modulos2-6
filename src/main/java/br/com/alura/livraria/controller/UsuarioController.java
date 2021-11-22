package br.com.alura.livraria.controller;

import java.io.IOException;
import java.net.URI;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import br.com.alura.livraria.dto.AtualizacaoUsuarioFormDto;
import br.com.alura.livraria.dto.UsuarioComIdDto;
import br.com.alura.livraria.dto.UsuarioDetalhadoDto;
import br.com.alura.livraria.dto.UsuarioDto;
import br.com.alura.livraria.dto.UsuarioFormDto;
import br.com.alura.livraria.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/usuarios")
@Api(tags = "Usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping
	@ApiOperation("Listar usuários")
	public Page<UsuarioComIdDto> listar(@PageableDefault(size = 15) Pageable paginacao){
		return service.listar(paginacao);
	}
	
	@PostMapping
	@ApiOperation("Cadastrar novo usuário")
	public ResponseEntity<UsuarioDto> cadastrar(@Valid @RequestBody UsuarioFormDto dto, UriComponentsBuilder uriBuilder) throws MessagingException, IOException {
		UsuarioDto usuarioDto = service.cadastrar(dto);
		URI uri = uriBuilder
				.path("/usuarios/{id}")
				.buildAndExpand(usuarioDto.getNome())
				.toUri();
		return ResponseEntity.created(uri).body(usuarioDto); 
	}
	
	@PutMapping
	@ApiOperation("Atualizar usuário")
	public ResponseEntity<UsuarioDto> atualizar(@Valid @RequestBody AtualizacaoUsuarioFormDto dto) {
		UsuarioDto usuarioAtualizado = service.atualizar(dto);
		return ResponseEntity.ok(usuarioAtualizado); 
	}
	
	@GetMapping("/{id}")
	@ApiOperation("Detalhar um usuário")
	public ResponseEntity<UsuarioDetalhadoDto> detalhar(@NotNull @PathVariable Long id) {
		UsuarioDetalhadoDto dto = service.detalhar(id);
		return ResponseEntity.ok(dto); 
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation("Excluir um usuário")
	public ResponseEntity<UsuarioDto> remover(@NotNull @PathVariable Long id) {
		service.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
}
