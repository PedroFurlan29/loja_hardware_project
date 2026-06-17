package com.lojahardware.unicep.produtos.dto;

import com.lojahardware.unicep.produtos.model.*;

public class ProdutoMapper {
    
    public static ProdutoDTO toDTO(Produto produto) {
        if (produto == null) return null;
        
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setSku(produto.getSku());
        dto.setDescricao(produto.getDescricao());
        dto.setPrecoVenda(produto.getPrecoVenda());
        dto.setPrecoOferta(produto.getPrecoOferta());
        dto.setCustoAquisicao(produto.getCustoAquisicao());
        dto.setQuantidade(produto.getQuantidade());
        dto.setEstoque_minimo(produto.getEstoque_minimo());
        dto.setImagemUrl(produto.getImagemUrl());
        dto.setEmOferta(produto.getEmOferta());
        dto.setEstoqueCritico(produto.isEstoqueCritico());
        dto.setDescricaoTecnica(produto.getDescricaoTecnica());
        
        if (produto instanceof CPU) {
            CPU cpu = (CPU) produto;
            dto.setTipoProduto("CPU");
            dto.setMarca(cpu.getMarca());
            dto.setModelo(cpu.getModelo());
            dto.setNucleos(cpu.getNucleos());
            dto.setFrequenciaGHz(cpu.getFrequenciaGHz());
            dto.setArquitetura(cpu.getArquitetura());
            dto.setSocket(cpu.getSocket());
        } else if (produto instanceof PlacaDeVideo) {
            PlacaDeVideo gpu = (PlacaDeVideo) produto;
            dto.setTipoProduto("GPU");
            dto.setMarca(gpu.getMarca());
            dto.setModelo(gpu.getModelo());
            dto.setVramGb(gpu.getVramGb());
            dto.setTipoMemoria(gpu.getTipoMemoria());
            dto.setChipset(gpu.getChipset());
            dto.setLarguraBandaBits(gpu.getLarguraBandaBits());
        } else if (produto instanceof Memoria) {
            Memoria ram = (Memoria) produto;
            dto.setTipoProduto("RAM");
            dto.setMarca(ram.getMarca());
            dto.setCapacidadeGb(ram.getCapacidadeGb());
            dto.setTipoMemoria(ram.getTipoMemoria());
            dto.setFrequenciaMHz(ram.getFrequenciaMHz());
            dto.setLatenciaCl(ram.getLatenciaCl());
        } else if (produto instanceof DispositivoDeArmazenamento) {
            DispositivoDeArmazenamento ssd = (DispositivoDeArmazenamento) produto;
            dto.setTipoProduto("SSD");
            dto.setMarca(ssd.getMarca());
            dto.setModelo(ssd.getModelo());
            dto.setCapacidadeGb(ssd.getCapacidadeGb());
            dto.setTipoArmazenamento(ssd.getTipoArmazenamento());
            dto.setInterface_(ssd.getInterface_());
            dto.setVelocidadeLeituraMBs(ssd.getVelocidadeLeituraMBs());
        }
        
        return dto;
    }
}