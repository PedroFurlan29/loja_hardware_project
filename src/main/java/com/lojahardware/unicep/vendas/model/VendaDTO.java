package com.lojahardware.unicep.vendas.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendaDTO {

    private Long id;
    private String usuarioEmail;
    private StatusVenda status;
    private BigDecimal valorTotal;
    private List<ItemVendaDTO> itens;
    private LocalDateTime criadoEm;
    private String motivoCancelamento;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ItemVendaDTO {
        private Long id;
        private Long produtoId;
        private String produtoSku;
        private Integer quantidade;
        private BigDecimal precoUnitario;
    }

    public static VendaDTO fromEntity(Venda venda) {
        return VendaDTO.builder()
                .id(venda.getId())
                .usuarioEmail(venda.getUsuario().getEmail())
                .status(venda.getStatus())
                .valorTotal(venda.getValorTotal())
                .criadoEm(venda.getCreatedAt())
                .motivoCancelamento(venda.getMotivoCancelamento())
                .itens(venda.getItens().stream()
                        .map(item -> ItemVendaDTO.builder()
                                .id(item.getId())
                                .produtoId(item.getProduto().getId())
                                .produtoSku(item.getProduto().getSku())
                                .quantidade(item.getQuantidade())
                                .precoUnitario(item.getPrecoUnitario())
                                .build())
                        .toList())
                .build();
    }
}
