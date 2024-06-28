package io.github.matheusfy.ForumHub.controllerTest;

import io.github.matheusfy.ForumHub.infra.auth.TokenService;
import io.github.matheusfy.ForumHub.models.Curso.Curso;
import io.github.matheusfy.ForumHub.models.Curso.CursoCategorias;
import io.github.matheusfy.ForumHub.models.Topico.CadastroTopicoDTO;
import io.github.matheusfy.ForumHub.models.Topico.StatusTopico;
import io.github.matheusfy.ForumHub.models.Topico.Topico;
import io.github.matheusfy.ForumHub.models.Topico.dto.AtualizacaoTopicoDTO;
import io.github.matheusfy.ForumHub.models.Topico.dto.TopicoDetailsDTO;
import io.github.matheusfy.ForumHub.models.Usuario.Usuario;
import io.github.matheusfy.ForumHub.services.TopicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureJsonTesters
class TopicoControllerTest {

	Usuario user;
	Curso curso;
	Curso curso2;
	CadastroTopicoDTO ct1;
	Topico topico1;
	Topico topico2;

	@MockBean
	TopicoService topicoService;

	@MockBean
	TokenService tokenService;

	@Autowired
	JacksonTester<TopicoDetailsDTO> topicoDetailsDTOJacksonTester;

	@Autowired
	JacksonTester<CadastroTopicoDTO> topicoCadastroJacksonTester;

	@Autowired
	JacksonTester<AtualizacaoTopicoDTO> topicoUpdateJacksonTester;

	@Autowired
	MockMvc mockMvc;

	@BeforeEach
	public void init() {
		user = new Usuario(1L, "John Doe", "johndoe@example.com", "passwordtest", true, null, null);
		curso = new Curso(1L, "Java", CursoCategorias.PROGRAMACAO);
		curso2 = new Curso(2L, "Python", CursoCategorias.PROGRAMACAO);

		CadastroTopicoDTO ct1 = new CadastroTopicoDTO("Programacao com Java", "teste", 1L, "Java");
		CadastroTopicoDTO ct2 = new CadastroTopicoDTO("Aperfeiçoando Java", "teste", 1L, "Java");

		topico1 = new Topico(1L, ct1.titulo(), ct1.mensagem(), StatusTopico.ABERTO,
				LocalDateTime.parse("2021-09-01T10:15:30"),
				LocalDateTime.parse("2021-09-01T10:15:30"), true, curso, user, null);
	}

	@Test
	@WithMockUser
	void buscarTodosTopicos() throws Exception {

	}

	@Test
	@WithMockUser
	void buscarTopicosRecentes() {
		// TODO: Implementar
	}

	@Test
	@WithMockUser
	void buscaTopico() throws Exception {

		when(topicoService.buscaTopico(1L)).thenReturn(new TopicoDetailsDTO(topico1));

		MockHttpServletResponse response = mockMvc.perform(
				MockMvcRequestBuilders.get("/topicos/1")).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		String jsonEsperado = topicoDetailsDTOJacksonTester.write(new TopicoDetailsDTO(topico1)).getJson();

		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
	}

	@Test
	@WithMockUser
	@DisplayName("Recebe uma entrada valida para cadastro e retorna status 201")
	void cadastrarTopicoValido() throws Exception {

		when(topicoService.cadastrarTopico(any())).thenReturn(new TopicoDetailsDTO(topico1));

		MockHttpServletResponse response = mockMvc.perform(
				MockMvcRequestBuilders.post("/topicos")
						.contentType(MediaType.APPLICATION_JSON)
						.content(topicoCadastroJacksonTester.write(
								new CadastroTopicoDTO("Programacao com Java", "teste", 1L, "Java")).getJson()))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

		String jsonEsperado = topicoDetailsDTOJacksonTester
				.write(new TopicoDetailsDTO(topico1)).getJson();

		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

	}

	@Test
	@WithMockUser
	@DisplayName("Recebe uma entrada valida para cadastro e retorna status 400")
	void cadastrarTopicoInvalido() throws Exception {

		when(topicoService.cadastrarTopico(any())).thenReturn(null);

		MockHttpServletResponse response = mockMvc.perform(
				MockMvcRequestBuilders.post("/topicos")
						.contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	@WithMockUser
	@DisplayName("Recebe uma entrada valida e atualiza um topico ja existente")
	void atualizarTopico() throws IOException, Exception {

		AtualizacaoTopicoDTO topicoUpdateDTO = new AtualizacaoTopicoDTO("Programando em Python", "teste", 1L, "Python",
				StatusTopico.ABERTO);

		topico1.updated(topicoUpdateDTO, curso2);

		when(topicoService.atualizarTopico(topicoUpdateDTO, 1L)).thenReturn(new TopicoDetailsDTO(topico1));

		MockHttpServletResponse response = mockMvc.perform(
				MockMvcRequestBuilders.put("/topicos/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(topicoUpdateJacksonTester.write(
								topicoUpdateDTO)
								.getJson()))
				.andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

		String jsonEsperado = topicoDetailsDTOJacksonTester.write(new TopicoDetailsDTO(
				1L, "Programando em Python", "John Doe", "teste", StatusTopico.ABERTO, "2021-09-01 10:15:30", "Python"))
				.getJson();

		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
	}

	@Test
	@WithMockUser
	void deletarTopicoValido() throws Exception {

		when(tokenService.getSubject(any())).thenReturn("johndoe@example.com");
		doNothing().when(topicoService).deletarTopico(1L, "johndoe@example.com");

			MockHttpServletResponse response = mockMvc.perform(
					MockMvcRequestBuilders.delete("/topicos/1")
			).andReturn().getResponse();

			assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}
}