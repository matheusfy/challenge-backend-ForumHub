package io.github.matheusfy.ForumHub.infra.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class SpringDocsConfiguration {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI().info(new Info()
        .title("ForumHub API")
        .version("v1")
        .description("API para um fórum de discussão de cursos")
        .license(
            new License()
                .name("Apache 2.0"))
        .contact(new Contact()
            .email("Backend-Team@forumhub.com")
            .name("Backend Team")))
        .externalDocs(new ExternalDocumentation()
            .description("Documentação mais detalhada do projeto")
            .url("https://github.com/matheusfy/challenge-backend-ForumHub"))
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
  }
}
