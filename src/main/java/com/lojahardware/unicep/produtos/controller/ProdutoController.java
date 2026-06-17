package com.lojahardware.unicep.produtos.controller;

import com.lojahardware.unicep.produtos.dto.ProdutoDTO;
import com.lojahardware.unicep.produtos.dto.ProdutoMapper;
import com.lojahardware.unicep.produtos.model.Produto;
import com.lojahardware.unicep.produtos.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
@Slf4j
public class ProdutoController {
    private final ProdutoService service;
    
    public ProdutoController(ProdutoService s) {
        this.service = s;
    }
    
    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> listar(
            @RequestParam(defaultValue = "0") int p,
            @RequestParam(defaultValue = "10") int s) {
        Page<Produto> produtos = service.listarComPaginacao(PageRequest.of(p, s));
        List<ProdutoDTO> dtos = produtos.getContent().stream()
                .map(ProdutoMapper::toDTO)
                .collect(Collectors.toList());
        Page<ProdutoDTO> pageDTO = new PageImpl<>(dtos, produtos.getPageable(), produtos.getTotalElements());
        return ResponseEntity.ok(pageDTO);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(ProdutoMapper.toDTO(service.buscarPorId(id)));
    }
    
    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody Produto pr) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(pr));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody Produto pr) {
        return ResponseEntity.ok(service.atualizar(id, pr));
    }
}