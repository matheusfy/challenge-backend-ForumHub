package io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException(String msg) {
        super(msg);
    }
}
