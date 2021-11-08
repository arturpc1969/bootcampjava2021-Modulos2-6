package br.com.alura.livraria.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alura.livraria.dto.AtualizacaoUsuarioFormDto;
import br.com.alura.livraria.dto.UsuarioDetalhadoDto;
import br.com.alura.livraria.dto.UsuarioDto;
import br.com.alura.livraria.dto.UsuarioFormDto;
import br.com.alura.livraria.modelo.Perfil;
import br.com.alura.livraria.modelo.Usuario;
import br.com.alura.livraria.repository.PerfilRepository;
import br.com.alura.livraria.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	public Page<UsuarioDto> listar(Pageable paginacao) {
		Page<Usuario> usuarios = usuarioRepository.findAll(paginacao); 
		return usuarios.map(t -> modelMapper.map(t, UsuarioDto.class));
	}

	@Transactional
	public UsuarioDto cadastrar(UsuarioFormDto dto) {
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		usuario.setId(null);
		
		Perfil perfil = perfilRepository.getById(dto.getPerfilId());
		usuario.adicionarPerfil(perfil);
		
		String senha = new SecureRandom().nextInt(999999) + "";
		System.out.println("Senha gerada: " + senha);
		usuario.setSenha(bCryptPasswordEncoder.encode(senha));
		usuarioRepository.save(usuario);
		
		return modelMapper.map(usuario, UsuarioDto.class);
	}

	@Transactional
	public UsuarioDto atualizar(AtualizacaoUsuarioFormDto dto) {
		Usuario usuario = usuarioRepository.getById(dto.getId());
		Perfil perfil = perfilRepository.getById(dto.getPerfilId());
		List<Perfil> perfis = new ArrayList<>();
		perfis.add(perfil);
		
		usuario.atualizarInformacoes(dto, perfis);
		
		System.out.println("Usuario: " + usuario);
		
		return modelMapper.map(usuario, UsuarioDto.class);
	}

	public UsuarioDetalhadoDto detalhar(Long id) {
		Usuario usuario = usuarioRepository.getById(id);		
		return modelMapper.map(usuario, UsuarioDetalhadoDto.class);
	}

	@Transactional
	public void excluir(Long id) {
		usuarioRepository.deleteById(id);
	}

	

}
