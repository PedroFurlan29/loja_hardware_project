package com.lojahardware.unicep.vendas.controller;

import com.lojahardware.unicep.vendas.model.StatusVenda;
import com.lojahardware.unicep.vendas.model.VendaDTO;
import com.lojahardware.unicep.vendas.model.VendaRequestDTO;
import com.lojahardware.unicep.vendas.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
@Slf4j
@Tag(name = "Vendas", description = "Endpoints de vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    @Operation(summary = "Registrar venda", description = "Cria uma nova venda com itens")
    public ResponseEntity<VendaDTO> registrarVenda(@Valid @RequestBody VendaRequestDTO vendaRequest) {
        var venda = vendaService.registrarVenda(vendaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(VendaDTO.fromEntity(venda));
    }

    @GetMapping
    @Operation(summary = "Listar vendas", description = "Lista todas as vendas")
    public ResponseEntity<List<VendaDTO>> listarTodas() {
        var vendas = vendaService.listarTodas();
        return ResponseEntity.ok(vendas.stream().map(VendaDTO::fromEntity).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar venda", description = "Busca uma venda por ID")
    public ResponseEntity<VendaDTO> buscarPorId(@PathVariable Long id) {
        var venda = vendaService.buscarPorId(id);
        return ResponseEntity.ok(VendaDTO.fromEntity(venda));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar vendas por status", description = "Lista vendas filtrando por status")
    public ResponseEntity<List<VendaDTO>> listarPorStatus(@PathVariable StatusVenda status) {
        var vendas = vendaService.listarPorStatus(status);
        return ResponseEntity.ok(vendas.stream().map(VendaDTO::fromEntity).toList());
    }

    @DeleteMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar venda", description = "Cancela uma venda e restitui o estoque")
    public ResponseEntity<Void> cancelarVenda(
            @PathVariable Long id,
            @RequestParam(required = false) String motivo) {
        vendaService.cancelarVenda(id, motivo != null ? motivo : "Cancelamento sem motivo");
        return ResponseEntity.noContent().build();
    }
}
