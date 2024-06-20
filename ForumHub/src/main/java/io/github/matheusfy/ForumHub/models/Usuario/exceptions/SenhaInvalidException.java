package io.github.matheusfy.ForumHub.models.Usuario.exceptions;


public class SenhaInvalidException extends RuntimeException{

    public SenhaInvalidException(){
        super("Senha deve ter pelo menos 6 caracteres");
    }

}
