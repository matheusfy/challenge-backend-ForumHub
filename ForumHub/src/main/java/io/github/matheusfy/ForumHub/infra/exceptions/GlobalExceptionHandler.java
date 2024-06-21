package io.github.matheusfy.ForumHub.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.DuplicateTituloAndMessagemException;
import io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions.DuplicatedEmailException;
import io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions.SenhaInvalidException;

@Component
public class GlobalExceptionHandler {

    @ExceptionHandler(SenhaInvalidException.class)
    public ResponseEntity<String> SenhaInvalidException(SenhaInvalidException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<String> DuplicatedEmailException(DuplicatedEmailException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<String> InvalidUserException(InvalidUserException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateTituloAndMessagemException.class)
    public ResponseEntity<String> DuplicateTituloAndMessagemException(DuplicateTituloAndMessagemException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    public record ErrorMsg(String campo, String msg) {
    }
}
