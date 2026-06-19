package com.lojahardware.unicep.vendas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {
    private Long id;
    private String usuarioNome;
    private String status;
    private BigDecimal valorTotal;
    private String motivoCancelamento;
    private List<ItemVendaDTO> itens;
    private Long vendedorReferenciaId;
    private String vendedorReferenciaNome;
    private BigDecimal comissao;
}