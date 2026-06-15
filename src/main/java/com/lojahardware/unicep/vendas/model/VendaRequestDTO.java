package com.lojahardware.unicep.vendas.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaRequestDTO {

    @NotEmpty(message = "Venda deve ter pelo menos um item")
    private List<ItemVendaRequestDTO> itens;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemVendaRequestDTO {

        @NotNull(message = "Produto ID é obrigatório")
        private Long produtoId;

        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser positiva")
        private Integer quantidade;
    }
}
