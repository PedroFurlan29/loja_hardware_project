package com.lojahardware.unicep.estoque.controller;

import com.lojahardware.unicep.estoque.model.Estoque;
import com.lojahardware.unicep.estoque.service.EstoqueService;
import lombok.Data;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/estoque")
public class EstoqueController {
    private final EstoqueService service;
    public EstoqueController(EstoqueService s){ service=s; }
    @GetMapping("/criticos") public ResponseEntity<?> criticos(){ return ResponseEntity.ok(service.listarCriticos()); }
    @GetMapping("/{produtoId}") public ResponseEntity<Estoque> buscar(@PathVariable Long produtoId){ return ResponseEntity.ok(service.buscarPorProdutoId(produtoId)); }
    @PostMapping public ResponseEntity<Estoque> criar(@RequestBody CriarEstoqueRequest req){ return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(req.produtoId,req.quantidade,req.estoqueMinimo)); }
    @PutMapping("/{produtoId}/baixar") public ResponseEntity<?> baixar(@PathVariable Long produtoId,@RequestBody BaixarRequest req){ service.baixar(produtoId,req.quantidade); return ResponseEntity.ok().build(); }
    @PutMapping("/{produtoId}/repor") public ResponseEntity<?> repor(@PathVariable Long produtoId,@RequestBody BaixarRequest req){ service.repor(produtoId,req.quantidade); return ResponseEntity.ok().build(); }
    @Data public static class CriarEstoqueRequest{ Long produtoId; Integer quantidade; Integer estoqueMinimo; }
    @Data public static class BaixarRequest{ Integer quantidade; }
}
