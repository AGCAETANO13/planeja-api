package io.github.agcaetano13.planeja.dominio.cartao;

import io.github.agcaetano13.planeja.dominio.cartao.dto.CartaoDetalhes;
import io.github.agcaetano13.planeja.dominio.cartao.dto.CartaoForm;
import io.github.agcaetano13.planeja.dominio.cartao.model.CartaoEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
@CrossOrigin("*") /// isso é para permitir uma url no angular (vscode) has been blocked by CORS policy:
public class CartaoController {

    @Autowired
    private CartaoService service;

    @PostMapping
    public ResponseEntity<CartaoDetalhes> criar(@RequestBody @Valid CartaoForm novo) {
        CartaoDetalhes detalhes = service.criar(novo);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalhes);
    }

    @GetMapping("{id}")
    public ResponseEntity<CartaoDetalhes> obterDetalhes(@PathVariable UUID id) {
        var result = service.obterDetalhes(id);
        return ResponseEntity.ok(result);

    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable UUID id, @RequestBody CartaoForm dadosAtualizacao) {
        service.atualizar(id, dadosAtualizacao);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public Page<CartaoDetalhes> listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        var pageRequest = PageRequest.of(page, size);
        return service.listar(pageRequest);
    }

}
