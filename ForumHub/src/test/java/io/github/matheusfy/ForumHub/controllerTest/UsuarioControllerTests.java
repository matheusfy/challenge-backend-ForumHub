package io.github.matheusfy.ForumHub.controllerTest;

import io.github.matheusfy.ForumHub.infra.auth.TokenService;
import io.github.matheusfy.ForumHub.infra.exceptions.GlobalExceptionHandler;
import io.github.matheusfy.ForumHub.models.Usuario.Usuario;
import io.github.matheusfy.ForumHub.models.Usuario.dto.CadastraUsuarioDTO;
import io.github.matheusfy.ForumHub.models.Usuario.dto.UserBasicInfoDTO;
import io.github.matheusfy.ForumHub.services.UsuarioService;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
public class UsuarioControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UsuarioService usuarioService;

  @MockBean
  private TokenService tokenService;

  @Autowired
  JacksonTester<CadastraUsuarioDTO> CadastroUsuarioDTOtest;

  @Autowired
  JacksonTester<UserBasicInfoDTO> UserBasicInfoDTOtest;

  @Autowired
  JacksonTester<GlobalExceptionHandler> exceptionHandlerJacksonTester;

  @Test
  void LoadContext() {

  }

  @Test
  @DisplayName("Recebe uma entrada valida para cadastro e retorna status 201")
  @WithMockUser
  void cadastrarUsuarioValidoTest() throws Exception {

    UserBasicInfoDTO usuarioDTOEsperado = new UserBasicInfoDTO(
        new Usuario(1L, "John Doe", "johndoe@example.com", "passwordtest", true, null,
            null));

    when(usuarioService.cadastrarUsuario(any())).thenReturn(usuarioDTOEsperado);

    MockHttpServletResponse response = mockMvc
        .perform(MockMvcRequestBuilders.post("/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(CadastroUsuarioDTOtest.write(
                new CadastraUsuarioDTO("John Doe", "johndoe@example.com", "passwordtest"))
                .getJson()))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    String jsonUsuarioEsperado = UserBasicInfoDTOtest.write(usuarioDTOEsperado).getJson();

    assertThat(response.getContentAsString()).isEqualTo(jsonUsuarioEsperado);
  }

  @Test
  @DisplayName("Recebe uma entrada sem senha")
  @WithMockUser // notação para ignorar autenticação do usuário
  void RecebeParametroInvalidoTest() throws Exception {

    when(usuarioService.cadastrarUsuario(any())).thenReturn(null);

    MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/usuarios"))
        .andReturn()
        .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
  }
}
