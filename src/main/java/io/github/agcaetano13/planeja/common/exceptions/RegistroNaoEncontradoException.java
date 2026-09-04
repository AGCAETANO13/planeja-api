package io.github.agcaetano13.planeja.common.exceptions;

public class RegistroNaoEncontradoException extends RuntimeException {
    public RegistroNaoEncontradoException() {
        super("Registro não encontrado!");
    }
}
