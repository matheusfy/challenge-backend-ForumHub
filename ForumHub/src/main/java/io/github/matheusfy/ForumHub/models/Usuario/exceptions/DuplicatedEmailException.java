package io.github.matheusfy.ForumHub.models.Usuario.exceptions;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException(String msg) {
        super(msg);
    }
}
