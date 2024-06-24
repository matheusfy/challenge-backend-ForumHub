package io.github.matheusfy.ForumHub.infra.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.matheusfy.ForumHub.infra.auth.TokenExpiradoException;
import io.github.matheusfy.ForumHub.infra.exceptions.cursoExceptions.CursoInvalidoException;
import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.DuplicateTituloAndMessagemException;
import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.TopicoDeletedException;
import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.TopicoNotUpdatedException;
import io.github.matheusfy.ForumHub.infra.exceptions.topicoExceptions.TopiconNotFoundException;
import io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions.DuplicatedEmailException;
import io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions.InvalidUserException;
import io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions.SenhaInvalidException;
import io.github.matheusfy.ForumHub.infra.exceptions.usuarioExceptions.UserUnauthorizedException;

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
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(DuplicateTituloAndMessagemException.class)
    public ResponseEntity<String> DuplicateTituloAndMessagemException(
            DuplicateTituloAndMessagemException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CursoInvalidoException.class)
    public ResponseEntity<String> InvalidCursoException(CursoInvalidoException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(TopiconNotFoundException.class)
    public ResponseEntity<String> TopiconNotFoundException(TopiconNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(TopicoNotUpdatedException.class)
    public ResponseEntity<String> TopicoNotUpdatedException(TopicoNotUpdatedException ex) {
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(ex.getMessage());
    }

    @ExceptionHandler(TopicoDeletedException.class)
    public ResponseEntity<String> TopicoDeletedException(TopicoDeletedException ex) {
        return ResponseEntity.status(HttpStatus.GONE).body(ex.getMessage());
    }

    @ExceptionHandler(TokenExpiradoException.class)
    public ResponseEntity<String> TokenExpiradoException(String message, Instant expiredOn) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message + " " + expiredOn);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<String> JWTVerificationException(JWTVerificationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<String> UserUnauthorizedException(UserUnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
