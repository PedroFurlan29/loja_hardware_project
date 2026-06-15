package com.lojahardware.unicep.estoque.controller;

import com.lojahardware.unicep.estoque.model.Estoque;
import com.lojahardware.unicep.estoque.service.EstoqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
@Slf4j
@Tag(name = "Estoque", description = "Endpoints de gerenciamento de estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping
    @Operation(summary = "Listar estoque", description = "Lista todo o estoque")
    public ResponseEntity<List<Estoque>> listar() {
        var estoque = estoqueService.listarTodos();
        return ResponseEntity.ok(estoque);
    }

    @GetMapping("/criticos")
    @Operation(summary = "Listar críticos", description = "Lista produtos em nível crítico de estoque")
    public ResponseEntity<List<Estoque>> listarCriticos() {
        var criticos = estoqueService.listarCriticos();
        return ResponseEntity.ok(criticos);
    }

    @GetMapping("/produto/{produtoId}")
    @Operation(summary = "Buscar por produto", description = "Busca estoque de um produto")
    public ResponseEntity<Estoque> buscarPorProduto(@PathVariable Long produtoId) {
        var estoque = estoqueService.buscarPorProdutoId(produtoId);
        return ResponseEntity.ok(estoque);
    }

    @PostMapping
    @Operation(summary = "Criar estoque", description = "Cria um novo registro de estoque")
    public ResponseEntity<Estoque> criar(
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade,
            @RequestParam Integer estoque_minimo) {
        var estoque = estoqueService.criar(produtoId, quantidade, estoque_minimo);
        return ResponseEntity.status(HttpStatus.CREATED).body(estoque);
    }

    @PostMapping("/{produtoId}/repor")
    @Operation(summary = "Repor estoque", description = "Adiciona quantidade ao estoque")
    public ResponseEntity<Void> repor(
            @PathVariable Long produtoId,
            @RequestParam Integer quantidade) {
        estoqueService.repor(produtoId, quantidade);
        return ResponseEntity.ok().build();
    }
}
