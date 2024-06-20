package io.github.matheusfy.ForumHub.infra.exceptions;

import io.github.matheusfy.ForumHub.models.Usuario.exceptions.DuplicatedEmailException;
import io.github.matheusfy.ForumHub.models.Usuario.exceptions.SenhaInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
public class GlobalExceptionHandler {

    @ExceptionHandler(SenhaInvalidException.class)
    public ResponseEntity<String> SenhaInvalidException(SenhaInvalidException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<String> DuplicatedEmailException(DuplicatedEmailException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }


    public record ErrorMsg(String campo, String msg){
    }
}
