package io.github.agcaetano13.planeja.infra.handlers;

import io.github.agcaetano13.planeja.common.exceptions.RegistroNaoEncontradoException;
import io.github.agcaetano13.planeja.common.exceptions.ValidationException;
import io.github.agcaetano13.planeja.common.validation.CampoInvalido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        var body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", e.getMessage(),
                "camposInvalidos", e.getCamposInvalidos()  // ← MUDANÇA: camposInvalidos (camelCase)

        );

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {

        var camposInvalidos = e.getFieldErrors()
                .stream()
                .map(fe -> new CampoInvalido(fe.getField(), fe.getDefaultMessage()))
                .toList();

        var status = HttpStatus.UNPROCESSABLE_ENTITY;

        var body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", e.getMessage(),
                "camposInvalidos", camposInvalidos  // ← MUDANÇA: camposInvalidos (camelCase)

        );

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    public ResponseEntity<?> handleRegistroNaoEncontradoException(
            RegistroNaoEncontradoException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", HttpStatus.NOT_FOUND.value(),
                        "error", e.getMessage(),
                        "message", e.getMessage()

                ));

    }
}