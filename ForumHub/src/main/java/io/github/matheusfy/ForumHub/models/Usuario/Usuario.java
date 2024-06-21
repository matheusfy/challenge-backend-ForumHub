package io.github.matheusfy.ForumHub.models.Usuario;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.github.matheusfy.ForumHub.models.Resposta.Resposta;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private String email;
	private String senha;

	// TODO: Inserir coluna no Banco antes de descomentar
	// private Boolean ativo = true;

	@OneToMany(mappedBy = "usuario")
	private List<Resposta> respostas;

	@OneToMany(mappedBy = "usuario")
	private List<Topico> topicos;

	// TODO: Implemental perfil
	// private Perfil perfil;

	public Usuario(CadastraUsuarioDTO usuario) {
		this.nome = usuario.nome();
		this.email = usuario.email();
		this.senha = new BCryptPasswordEncoder().encode(usuario.senha());
		// this.ative = true
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", id=" + id + ", nome=" + nome + ", senha=" + senha + "]";
	}

}
