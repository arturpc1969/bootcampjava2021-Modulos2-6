package br.com.alura.livraria.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.alura.livraria.dto.LoginFormDto;
import br.com.alura.livraria.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public String autenticar(LoginFormDto dto) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getSenha());
		authentication = authenticationManager.authenticate(authentication);
		return tokenService.gerarToken(authentication);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioRepository
				.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException(""));
			
	}

}
