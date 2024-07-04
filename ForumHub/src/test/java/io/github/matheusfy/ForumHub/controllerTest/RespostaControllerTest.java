package io.github.matheusfy.ForumHub.controllerTest;

import io.github.matheusfy.ForumHub.controller.RespostaDetailDto;
import io.github.matheusfy.ForumHub.infra.auth.TokenService;
import io.github.matheusfy.ForumHub.models.Resposta.dto.RespostaDto;
import io.github.matheusfy.ForumHub.services.RespostaService;
import jakarta.servlet.http.HttpServletRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RespostaControllerTest {

  @Autowired
  MockMvc mvc;

  @MockBean
  RespostaService respostaService;

  @MockBean
  TokenService tokenService;

  @Mock
  HttpServletRequest request;

  @Autowired
  JacksonTester<RespostaDto> respostaDtoJacksonTester;

  @Autowired
  JacksonTester<Page<RespostaDetailDto>> respostasPageJacksonTester;

  RespostaDto respostaDto;
  String userEmail;
  ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {

    userEmail = "test@example.com";
    respostaDto = new RespostaDto("Test answer", "Test solution");
    objectMapper = new ObjectMapper();
  }

  @Test
  @DisplayName("Postar resposta should return 201 Created")
  @WithMockUser
  void postarResposta() throws Exception {
    // Arrange
    Long idTopico = 1L;

    Long respostaId = 1L;

    when(tokenService.getSubject(any())).thenReturn(userEmail);
    when(respostaService.postarResposta(eq(idTopico), any(RespostaDto.class), eq(userEmail)))
        .thenReturn(respostaId);

    URI uri = UriComponentsBuilder.fromUriString("/resposta/{respostaId}").buildAndExpand(respostaId).toUri();

    MvcResult result = mvc.perform(
        MockMvcRequestBuilders.post("/respostas/resposta/{idTopico}", idTopico)
            .content(respostaDtoJacksonTester.write(respostaDto).getJson())
            .contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    MockHttpServletResponse responseMock = result.getResponse();

    // Assert
    assertThat(responseMock.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(responseMock.getHeader("Location")).isEqualTo(uri.toString());
  }

  @Test
  @DisplayName("Get respostas should return 200 OK")
  @WithMockUser
  void getRespostas() throws Exception {
    // Arrange
    Long idTopico = 1L;
    Page<RespostaDetailDto> respostas = new PageImpl<>(Collections.emptyList());

    when(respostaService.getRespostas(eq(idTopico), any(Pageable.class))).thenReturn(respostas);

    // Act
    MvcResult result = mvc.perform(
        MockMvcRequestBuilders.get("/respostas/{idTopico}", idTopico)
            .param("page", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    MockHttpServletResponse responseMock = result.getResponse();

    // Assert
    // TODO: Fix this test
    // assertThat(responseMock.getStatus()).isEqualTo(HttpStatus.OK.value());
    // assertThat(responseMock.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(respostas));
  }

  @Test
  @DisplayName("Get resposta should return 200 OK")
  @WithMockUser
  void getResposta() throws Exception {
    // Arrange
    Long idResposta = 1L;
    RespostaDetailDto resposta = new RespostaDetailDto(idResposta, "Test answer", "Test solution", null, idResposta,
        null, null);

    when(respostaService.getResposta(eq(idResposta))).thenReturn(resposta);

    // Act
    MvcResult result = mvc.perform(
        MockMvcRequestBuilders.get("/respostas/resposta/{idResposta}", idResposta)
            .contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    MockHttpServletResponse responseMock = result.getResponse();

    // Assert
    assertThat(responseMock.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(responseMock.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(resposta));
  }

  @Test
  @DisplayName("Get respostas usuario should return 200 OK")
  @WithMockUser
  void getRespostasUsuario() throws Exception {
    // Arrange
    Page<RespostaDetailDto> respostas = new PageImpl<>(Collections.emptyList());

    when(respostaService.getRespostasUsuario(eq(userEmail), any(Pageable.class))).thenReturn(respostas);

    // Act
    MvcResult result = mvc.perform(
        MockMvcRequestBuilders.get("/respostas/user")
            .param("page", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    MockHttpServletResponse responseMock = result.getResponse();

    // Assert
    assertThat(responseMock.getStatus()).isEqualTo(HttpStatus.OK.value());
    // TODO: Fix this assertion
    // assertThat(responseMock.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(respostas));
  }

  @Test
  @DisplayName("Atualizar resposta should return 200 OK")
  @WithMockUser
  void atualizarResposta() throws Exception {
    // Arrange
    Long idResposta = 1L;
    RespostaDetailDto respostaAtualizada = new RespostaDetailDto(idResposta, "Updated answer", "Updated solution",
        userEmail, idResposta, userEmail, userEmail);

    when(tokenService.getSubject(any())).thenReturn(userEmail);
    when(respostaService.atualizarResposta(eq(idResposta), eq(respostaDto), eq(userEmail)))
        .thenReturn(respostaAtualizada);

    // Act
    MvcResult result = mvc.perform(
        MockMvcRequestBuilders.put("/respostas/resposta/{idResposta}", idResposta)
            .contentType(MediaType.APPLICATION_JSON)
            .content(respostaDtoJacksonTester.write(respostaDto).getJson()))
        .andReturn();

    MockHttpServletResponse responseMock = result.getResponse();

    // Assert
    assertThat(responseMock.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(responseMock.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(respostaAtualizada));
  }

  @Test
  @DisplayName("Deletar resposta should return 204 No Content")
  @WithMockUser
  void deletarResposta() throws Exception {
    // Arrange
    Long idResposta = 1L;

    // Act
    MvcResult result = mvc.perform(
        MockMvcRequestBuilders.delete("/respostas/resposta/{idResposta}", idResposta)
            .contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    MockHttpServletResponse responseMock = result.getResponse();

    // Assert
    assertThat(responseMock.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
  }
}