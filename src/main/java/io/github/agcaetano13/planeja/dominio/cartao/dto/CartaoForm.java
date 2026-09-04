package io.github.agcaetano13.planeja.dominio.cartao.dto;

import io.github.agcaetano13.planeja.dominio.cartao.model.BandeiraCartao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CartaoForm(
        @NotBlank(message = ("Campo obrigatório."))
        String nome,
        @NotNull(message = ("Campo obrigatório."))
        BandeiraCartao bandeira) {

}
