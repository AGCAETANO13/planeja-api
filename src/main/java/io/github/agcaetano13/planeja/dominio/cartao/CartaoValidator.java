package io.github.agcaetano13.planeja.dominio.cartao;

import io.github.agcaetano13.planeja.common.validation.CampoInvalido;
import io.github.agcaetano13.planeja.common.validation.ValidationResult;
import io.github.agcaetano13.planeja.dominio.cartao.dto.CartaoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CartaoValidator {

    @Autowired
    private CartaoRepository repository;

    public ValidationResult validar(CartaoForm form, UUID id) {
        var result = ValidationResult.novo();

        // 1. Normaliza o nome: remove espaços no início/fim e converte para maiúsculo
        String nomeNormalizado = form.nome() != null ? form.nome().trim().toUpperCase() : "";

        boolean nomeJaExiste;

        if (id == null) {
            // Cenário de CRIAÇÃO: verifica se já existe esse nome
            nomeJaExiste = repository.existsByNomeIgnoreCase(nomeNormalizado);
        } else {
            // Cenário de ATUALIZAÇÃO: verifica se existe esse nome em OUTRO cartão (ID diferente)
            nomeJaExiste = repository.existsByNomeIgnoreCaseAndIdNot(nomeNormalizado, id);
        }

        // 2. Se encontrou, adiciona o erro
        if (nomeJaExiste) {
            result.add(new CampoInvalido("nome", "Já existe um cartão cadastrado com este nome."));
        }

        return result;
    }
}