package io.github.matheusfy.ForumHub.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.DuplicateTituloAndMessagemException;
import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.InvalidCursoException;
import io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions.DuplicatedEmailException;
import io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions.SenhaInvalidException;
import io.github.matheusfy.ForumHub.services.TopicoNotUpdatedException;
import io.github.matheusfy.ForumHub.services.TopiconNotFoundException;

@RestControllerAdvice
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
    public ResponseEntity<String> DuplicateTituloAndMessagemException(
            DuplicateTituloAndMessagemException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InvalidCursoException.class)
    public ResponseEntity<String> InvalidCursoException(InvalidCursoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(TopiconNotFoundException.class)
    public ResponseEntity<String> TopiconNotFoundException(TopiconNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(TopicoNotUpdatedException.class)
    public ResponseEntity<String> TopicoNotUpdatedException(TopicoNotUpdatedException ex) {
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(ex.getMessage());
    }

    public record ErrorMsg(String campo, String msg) {
    }
}
