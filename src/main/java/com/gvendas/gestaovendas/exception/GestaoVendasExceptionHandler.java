package com.gvendas.gestaovendas.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class GestaoVendasExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String NOT_BLANK = "NotBlank";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        List<Error> erros = gerarListaDeErros(ex.getBindingResult());

        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request){
        String msgUsuario = "Recurso não encontrado.";
        String msgDev = ex.toString();

        List<Error> erros = Arrays.asList(new Error(msgUsuario, msgDev));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<Error> gerarListaDeErros(BindingResult bindingResult) {
        List<Error> erros = new ArrayList<Error>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String msgUsuario = tratarMensagemDeErroParaUsuario(fieldError);
            String msgDesenvolvedor = fieldError.toString();

            erros.add(new Error(msgUsuario, msgDesenvolvedor));
        });

        return erros;
    }

    private String tratarMensagemDeErroParaUsuario(FieldError fieldError) {
        if(fieldError.getCode().equals(NOT_BLANK)){
            return fieldError.getDefaultMessage().concat(" é obrigatório.");
        }

        if(fieldError.getCode().equals("Length")){
            return fieldError.getDefaultMessage().concat(String.format(" deve ter no entre %s e %s caracteres.", fieldError.getArguments()[2],  fieldError.getArguments()[1]));
        }

        return fieldError.toString();
    }
}
