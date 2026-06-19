package com.lojahardware.unicep.vendas.controller;

import com.lojahardware.unicep.usuarios.repository.UsuarioRepository;
import com.lojahardware.unicep.vendas.dto.VendaDTO;
import com.lojahardware.unicep.vendas.dto.VendaMapper;
import com.lojahardware.unicep.vendas.model.Venda;
import com.lojahardware.unicep.vendas.service.VendaService;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/vendas")
public class VendaController {
    private final VendaService service;
    private final UsuarioRepository usuarioRepo;

    public VendaController(VendaService s, UsuarioRepository u){ service=s; usuarioRepo=u; }

    private void enrich(List<VendaDTO> list) {
        list.forEach(dto -> VendaMapper.enrich(dto, usuarioRepo));
    }

    private void enrich(VendaDTO dto) {
        VendaMapper.enrich(dto, usuarioRepo);
    }

    @GetMapping
    public ResponseEntity<List<VendaDTO>> listar(){
        List<VendaDTO> list = VendaMapper.toDTOList(service.listar());
        enrich(list);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<VendaDTO>> minhasVendas(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<VendaDTO> list = VendaMapper.toDTOList(service.listarPorUsuario(email));
        enrich(list);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/vendedor/{id}")
    public ResponseEntity<List<VendaDTO>> porVendedor(@PathVariable Long id){
        List<VendaDTO> list = VendaMapper.toDTOList(service.listarPorVendedor(id));
        enrich(list);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaDTO> buscar(@PathVariable Long id){
        VendaDTO dto = VendaMapper.toDTO(service.buscarPorId(id));
        enrich(dto);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<VendaDTO> registrar(@RequestBody VendaRequest req){
        VendaDTO dto = VendaMapper.toDTO(service.registrar(req));
        enrich(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long id, @RequestBody Map<String,String> body){
        service.cancelar(id, body.get("motivo"));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/totais")
    public ResponseEntity<Map<String, Object>> totais() {
        List<Venda> todas = service.listar();
        long totalVendas = todas.stream().filter(v -> v.getStatus().name().equals("CONCLUIDA")).count();
        BigDecimal valorTotal = todas.stream()
            .filter(v -> v.getStatus().name().equals("CONCLUIDA"))
            .map(Venda::getValorTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, Object> res = new HashMap<>();
        res.put("totalVendas", totalVendas);
        res.put("valorTotal", valorTotal);
        return ResponseEntity.ok(res);
    }

    @Data
    public static class VendaRequest {
        private List<ItemRequest> itens;
        private Long vendedorId;
    }

    @Data
    public static class ItemRequest {
        private Long produtoId;
        private Integer quantidade;
    }
}