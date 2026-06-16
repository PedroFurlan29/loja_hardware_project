package com.lojahardware.unicep.produtos.controller;

import com.lojahardware.unicep.produtos.model.Produto;
import com.lojahardware.unicep.produtos.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/produtos") @Slf4j
public class ProdutoController {
    private final ProdutoService service;
    public ProdutoController(ProdutoService s){ this.service=s; }
    @GetMapping public ResponseEntity<Page<Produto>> listar(@RequestParam(defaultValue="0")int p,@RequestParam(defaultValue="10")int s){
        return ResponseEntity.ok(service.listarComPaginacao(PageRequest.of(p,s)));
    }
    @GetMapping("/{id}") public ResponseEntity<Produto> buscar(@PathVariable Long id){ return ResponseEntity.ok(service.buscarPorId(id)); }
    @PostMapping public ResponseEntity<Produto> criar(@Valid @RequestBody Produto pr){ return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(pr)); }
    @PutMapping("/{id}") public ResponseEntity<Produto> atualizar(@PathVariable Long id,@Valid @RequestBody Produto pr){ return ResponseEntity.ok(service.atualizar(id,pr)); }
}
