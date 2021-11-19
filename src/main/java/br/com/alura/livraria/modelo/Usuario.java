package br.com.alura.livraria.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.alura.livraria.dto.AtualizacaoUsuarioFormDto;
import br.com.alura.livraria.dto.PerfilDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"senha"})
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String login;
	private String senha;
	private String email;
	
	@ManyToMany
	@JoinTable(name = "perfis_usuarios",
		joinColumns = @JoinColumn(name = "usuario_id"),
		inverseJoinColumns = @JoinColumn(name = "perfil_id"))
	private List<Perfil> perfis = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.perfis;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void adicionarPerfil(PerfilDto perfilDto) {
		Perfil perfil = new Perfil(perfilDto.getId(), perfilDto.getNome());
		this.perfis.add(perfil);
	}

	public Usuario(String nome, String login, String senha, String email) {
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.email = email;
	}

	public void atualizarInformacoes(AtualizacaoUsuarioFormDto dto, List<Perfil> perfis) {
		this.nome = dto.getNome();
		this.login = dto.getLogin();
		this.email = dto.getEmail();
		this.perfis = perfis;
	}

}
