package com.lojahardware.unicep.vendas.controller;

import com.lojahardware.unicep.vendas.dto.VendaDTO;
import com.lojahardware.unicep.vendas.dto.VendaMapper;
import com.lojahardware.unicep.vendas.model.Venda;
import com.lojahardware.unicep.vendas.service.VendaService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import lombok.Data;
import java.util.*;

@RestController
@RequestMapping("/vendas")
public class VendaController {
    private final VendaService service;
    public VendaController(VendaService s){ service=s; }

    @GetMapping
    public ResponseEntity<List<VendaDTO>> listar(){
        return ResponseEntity.ok(VendaMapper.toDTOList(service.listar()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaDTO> buscar(@PathVariable Long id){
        return ResponseEntity.ok(VendaMapper.toDTO(service.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<VendaDTO> registrar(@RequestBody VendaRequest req){
        return ResponseEntity.status(HttpStatus.CREATED).body(VendaMapper.toDTO(service.registrar(req)));
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long id, @RequestBody Map<String,String> body){
        service.cancelar(id, body.get("motivo"));
        return ResponseEntity.ok().build();
    }

    @Data
    public static class VendaRequest {
        private List<ItemRequest> itens;
    }

    @Data
    public static class ItemRequest {
        private Long produtoId;
        private Integer quantidade;
    }
}