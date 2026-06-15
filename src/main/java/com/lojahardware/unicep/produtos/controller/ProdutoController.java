package com.lojahardware.unicep.produtos.controller;

import com.lojahardware.unicep.produtos.model.Produto;
import com.lojahardware.unicep.produtos.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@Slf4j
@Tag(name = "Produtos", description = "Endpoints de produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    @Operation(summary = "Listar produtos", description = "Lista todos os produtos")
    public ResponseEntity<List<Produto>> listarTodos() {
        var produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto", description = "Busca um produto por ID")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        var produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Buscar por SKU", description = "Busca um produto por SKU")
    public ResponseEntity<Produto> buscarPorSku(@PathVariable String sku) {
        var produto = produtoService.buscarPorSku(sku);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    @Operation(summary = "Criar produto", description = "Cria um novo produto")
    public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto) {
        var produtoCriado = produtoService.criar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza um produto existente")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        var produtoAtualizado = produtoService.atualizar(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar produto", description = "Soft delete de um produto")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
