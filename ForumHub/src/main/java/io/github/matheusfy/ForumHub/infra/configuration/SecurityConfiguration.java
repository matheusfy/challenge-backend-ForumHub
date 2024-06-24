package io.github.matheusfy.ForumHub.infra.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import io.github.matheusfy.ForumHub.infra.filter.SecurityFilter;

@Configuration
@EnableWebMvc
public class SecurityConfiguration {

  @Autowired
  private SecurityFilter securityFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity.csrf(csrf -> csrf.disable())
        .sessionManagement(
            management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> {
          authorize.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
              .permitAll();
          authorize.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
          authorize.anyRequest().authenticated();
        }).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
    // TODO: Encontrar uma forma para quando o usuário realizar a requisição sem
    // autenticaçãom, retornar o erro de acesso negado
    // junto com a mensagem de "Não autorizado. Por favor, faça login."

  }

  @Bean
  public AuthenticationManager beanManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
