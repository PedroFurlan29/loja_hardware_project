package com.lojahardware.unicep.produtos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Long id;
    private String sku;
    private String descricao;
    private BigDecimal precoVenda;
    private BigDecimal precoOferta;
    private BigDecimal custoAquisicao;
    private Integer quantidade;
    private Integer estoque_minimo;
    private String imagemUrl;
    private Boolean emOferta;
    private Boolean estoqueCritico;
    private String descricaoTecnica;
    private String tipoProduto;
    
    private String marca;
    private String modelo;
    private Integer nucleos;
    private Double frequenciaGHz;
    private String arquitetura;
    private String socket;
    private Integer vramGb;
    private String tipoMemoria;
    private String chipset;
    private Integer larguraBandaBits;
    private Integer capacidadeGb;
    private Integer frequenciaMHz;
    private Integer latenciaCl;
    private String tipoArmazenamento;
    private String interface_;
    private Integer velocidadeLeituraMBs;
}