package io.github.agcaetano13.planeja.common.validation;

import io.github.agcaetano13.planeja.dominio.cartao.dto.CartaoForm;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private List<CampoInvalido> campoInvalidos;

    private ValidationResult(List<CampoInvalido> campoInvalidos) {
        this.campoInvalidos = campoInvalidos;
    }

    public static ValidationResult novo () {
        return new ValidationResult(new ArrayList<>());
    }

    public void add(CampoInvalido campoInvalido) {
        this.campoInvalidos.add(campoInvalido);

    }

    public List<CampoInvalido> getCampoInvalidos() {
        return campoInvalidos;
    }

    public boolean isInValid() {
        return !campoInvalidos.isEmpty();
    }


}
