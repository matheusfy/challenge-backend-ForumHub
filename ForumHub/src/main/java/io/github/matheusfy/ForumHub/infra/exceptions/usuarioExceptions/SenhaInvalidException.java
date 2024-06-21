package io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions;

public class SenhaInvalidException extends RuntimeException {

    public SenhaInvalidException() {
        super("Senha deve ter pelo menos 6 caracteres");
    }

}
