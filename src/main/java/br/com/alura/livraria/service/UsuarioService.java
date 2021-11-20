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
import br.com.alura.livraria.dto.UsuarioDetalhadoDto;
import br.com.alura.livraria.dto.UsuarioDto;
import br.com.alura.livraria.dto.UsuarioFormDto;
import br.com.alura.livraria.infra.EmailSenderService;
import br.com.alura.livraria.infra.EnviadorDeEmail;
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
	private EnviadorDeEmail enviadorDeEmail;

	@Autowired
	private EmailSenderService emailService;

	public Page<UsuarioDto> listar(Pageable paginacao) {
		Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
		return usuarios.map(t -> modelMapper.map(t, UsuarioDto.class));
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
		String assunto = "Bem vindo(a) a Livraria";		
		String mensagem = String.format("Olá, %s\n\n"
				+ "Seguem abaixo os seus dados de acesso ao sistema Livraria.\n\n"
				+ "Login: %s\n"
				+ "Senha: %s\n\n", usuario.getNome(), usuario.getLogin(), senha);
		
		enviadorDeEmail.enviaEmail(destinatario, assunto, mensagem);
		
		Mail mail = new Mail();
		//mail.setFrom("${MAIL_USER}");//replace with your desired email
		mail.setFrom("livrariateste79@gmail.com");
		mail.setMailTo(destinatario);//replace with your desired email
		mail.setSubject(assunto);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", usuario.getNome());
		model.put("location", "United States");
		model.put("sign", "Java Developer");
		mail.setProps(model);

		emailService.sendEmail(mail);
		
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
