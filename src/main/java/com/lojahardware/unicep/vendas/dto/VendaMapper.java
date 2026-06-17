package com.lojahardware.unicep.vendas.dto;

import com.lojahardware.unicep.vendas.model.ItemVenda;
import com.lojahardware.unicep.vendas.model.Venda;

import java.util.List;
import java.util.stream.Collectors;

public class VendaMapper {

    public static VendaDTO toDTO(Venda venda) {
        if (venda == null) return null;

        VendaDTO dto = new VendaDTO();
        dto.setId(venda.getId());
        dto.setUsuarioNome(venda.getUsuario() != null ? venda.getUsuario().getNome() : null);
        dto.setStatus(venda.getStatus() != null ? venda.getStatus().name() : null);
        dto.setValorTotal(venda.getValorTotal());
        dto.setMotivoCancelamento(venda.getMotivoCancelamento());

        if (venda.getItens() != null) {
            dto.setItens(venda.getItens().stream()
                    .map(VendaMapper::toItemDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static ItemVendaDTO toItemDTO(ItemVenda item) {
        if (item == null) return null;

        ItemVendaDTO dto = new ItemVendaDTO();
        dto.setId(item.getId());
        dto.setProdutoNome(item.getProduto() != null ? item.getProduto().getDescricao() : null);
        dto.setProdutoSku(item.getProduto() != null ? item.getProduto().getSku() : null);
        dto.setQuantidade(item.getQuantidade());
        dto.setPrecoUnitario(item.getPrecoUnitario());
        dto.setSubtotal(item.getSubtotal());

        return dto;
    }

    public static List<VendaDTO> toDTOList(List<Venda> vendas) {
        return vendas.stream()
                .map(VendaMapper::toDTO)
                .collect(Collectors.toList());
    }
}