package io.github.agcaetano13.planeja.dominio.cartao;

import io.github.agcaetano13.planeja.common.validation.CampoInvalido;
import io.github.agcaetano13.planeja.common.validation.ValidationResult;
import io.github.agcaetano13.planeja.dominio.cartao.dto.CartaoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CartaoValidator {

    @Autowired // criamos um metodo que nao pode cadastrar mais de um cartao com mesmo nome
    private CartaoRepository repository;

    public ValidationResult validar(CartaoForm form, UUID id) {
        var result = ValidationResult.novo();

        var isListaNaoVazia = !repository.findByNomeAndNotId(form.nome(), id).isEmpty();
        if (isListaNaoVazia ) {
            result.add(new CampoInvalido("nome", "Já cadastrado."));
        }

        return result;
    }
}
