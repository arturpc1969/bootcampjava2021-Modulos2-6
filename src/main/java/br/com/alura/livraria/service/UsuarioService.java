package br.com.alura.livraria.service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alura.livraria.dto.AtualizacaoUsuarioFormDto;
import br.com.alura.livraria.dto.PerfilDto;
import br.com.alura.livraria.dto.UsuarioComIdDto;
import br.com.alura.livraria.dto.UsuarioDetalhadoDto;
import br.com.alura.livraria.dto.UsuarioDto;
import br.com.alura.livraria.dto.UsuarioFormDto;
import br.com.alura.livraria.infra.EmailSenderService;
import br.com.alura.livraria.modelo.Mail;
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

	@Autowired
	private EmailSenderService emailService;

	public Page<UsuarioComIdDto> listar(Pageable paginacao) {
		Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
		return usuarios.map(t -> modelMapper.map(t, UsuarioComIdDto.class));
	}

	@Transactional
	public UsuarioDto cadastrar(UsuarioFormDto dto) throws MessagingException, IOException {
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		usuario.setId(null);
		
		Perfil perfil = perfilRepository.getById(dto.getPerfilId());
		
		PerfilDto perfilDto = modelMapper.map(perfil, PerfilDto.class);
		
		usuario.adicionarPerfil(perfilDto);
		
		String senha = new SecureRandom().nextInt(999999) + "";
		usuario.setSenha(bCryptPasswordEncoder.encode(senha));
		usuarioRepository.save(usuario);
		
		String destinatario = usuario.getEmail();
		String assunto = "Bem vindo(a) a Livraria OnLine";		
		String mensagem = "Seguem abaixo suas informações de acesso:";
		String template = "email-template";
		
		Mail mail = new Mail();
		mail.setFrom("${MAIL_USER}");
		mail.setMailTo(destinatario);
		mail.setSubject(assunto);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nome", usuario.getNome());
		model.put("login", usuario.getLogin());
		model.put("senha", senha);
		model.put("mensagem", mensagem);
		mail.setProps(model);
		
		
		emailService.sendEmail(mail, template);
		
		return modelMapper.map(usuario, UsuarioDto.class);
	}

	@Transactional
	public UsuarioDto atualizar(AtualizacaoUsuarioFormDto dto) {
		Usuario usuario = usuarioRepository.getById(dto.getId());
		Perfil perfil = perfilRepository.getById(dto.getPerfilId());
		List<Perfil> perfis = new ArrayList<>();
		perfis.add(perfil);

		usuario.atualizarInformacoes(dto, perfis);

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
