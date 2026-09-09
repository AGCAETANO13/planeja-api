package io.github.agcaetano13.planeja.dominio.cartao;

import io.github.agcaetano13.planeja.common.exceptions.RegistroNaoEncontradoException;
import io.github.agcaetano13.planeja.common.exceptions.ValidationException;
import io.github.agcaetano13.planeja.dominio.cartao.dto.CartaoDetalhes;
import io.github.agcaetano13.planeja.dominio.cartao.dto.CartaoForm;
import io.github.agcaetano13.planeja.dominio.cartao.mapper.CartaoMapper;
import io.github.agcaetano13.planeja.dominio.cartao.model.CartaoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;  // ← ADICIONADO
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CartaoService {

    @Autowired
    private CartaoValidator validator;
    @Autowired
    private CartaoRepository repository;

    @Autowired
    private CartaoMapper mapper;

    public CartaoDetalhes criar(CartaoForm form) {
        var result = validator.validar(form, null);

        if(result.isInValid()){
            throw new ValidationException(result.getCampoInvalidos());
        }
        CartaoEntity entity = mapper.toEntity(form);
        repository.save(entity);
        return mapper.toDetalhes(entity);
    }

    public CartaoDetalhes obterDetalhes(UUID id) {
        return repository
                .findById(id)
                .map(mapper::toDetalhes)
                .orElseThrow(() -> new RegistroNaoEncontradoException());
    }

    @Transactional
    public void atualizar(UUID id, CartaoForm dadosAtualizacao) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException());

        var result = validator.validar(dadosAtualizacao, id);

        if (result.isInValid()) {
            throw new ValidationException(result.getCampoInvalidos());
        }

        mapper.update(entity, dadosAtualizacao);
        repository.save(entity);
    }

    public Page<CartaoDetalhes> listar(PageRequest pageRequest) {
        return repository
                .findAll(pageRequest)
                .map(mapper::toDetalhes);
    }
}