package com.lojahardware.unicep.vendas.controller;

import com.lojahardware.unicep.vendas.model.Venda;
import com.lojahardware.unicep.vendas.service.VendaService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import lombok.Data;
import java.util.*;

@RestController @RequestMapping("/vendas")
public class VendaController {
    private final VendaService service;
    public VendaController(VendaService s){ service=s; }
    @GetMapping public ResponseEntity<List<Venda>> listar(){ return ResponseEntity.ok(service.listar()); }
    @GetMapping("/{id}") public ResponseEntity<Venda> buscar(@PathVariable Long id){ return ResponseEntity.ok(service.buscarPorId(id)); }
    @PostMapping public ResponseEntity<Venda> registrar(@RequestBody VendaRequest req){ return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(req)); }
    @PostMapping("/{id}/cancelar") public ResponseEntity<?> cancelar(@PathVariable Long id,@RequestBody Map<String,String> body){ service.cancelar(id,body.get("motivo")); return ResponseEntity.ok().build(); }
    @Data public static class VendaRequest { private List<ItemRequest> itens; }
    @Data public static class ItemRequest { private Long produtoId; private Integer quantidade; }
}
