package br.com.gubee.interview.core.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
import java.util.Optional;
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String mensagemUsuario= "Invalid Data";
        String mensagemDesenvolvedor = Optional.ofNullable(ex.getCause()).orElse(ex).toString();

        List<ErroDesenvolvedor> errosLista = Arrays.asList(new ErroDesenvolvedor(mensagemUsuario, mensagemDesenvolvedor));
        return handleExceptionInternal(ex, errosLista, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ErroDesenvolvedor> errosLista = criarListaDeErros(ex.getBindingResult());

        return handleExceptionInternal(ex, errosLista, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({IllegalArgumentException.class,InvalidDataAccessApiUsageException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(Exception ex,WebRequest request) {
        String mensagemUsuario ="Invalid Data";
        String mensagemDesenvolvedor = ex.toString();
        System.out.println(mensagemDesenvolvedor);
        List<ErroDesenvolvedor> errosLista = Arrays.asList(new ErroDesenvolvedor(mensagemUsuario, mensagemDesenvolvedor));
        return handleExceptionInternal(ex, errosLista, new HttpHeaders(), HttpStatus.OK, request);
    }


    private List<ErroDesenvolvedor> criarListaDeErros(BindingResult bindingResult) {
        List<ErroDesenvolvedor> erros = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String error = fieldError.getField();
            String error_description = fieldError.toString();
            erros.add(new ErroDesenvolvedor(error, error_description));
        }

        return erros;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ErroDesenvolvedor{

        private String error;
        private String error_description;
    }

}
