package com.lojahardware.unicep.produtos.model;

import com.lojahardware.unicep.shared.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_produto", discriminatorType = DiscriminatorType.STRING)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public abstract class Produto extends BaseEntity {

    @NotBlank(message = "SKU é obrigatório")
    @Column(unique = true, nullable = false, length = 50)
    private String sku;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false, length = 255)
    private String descricao;

    @NotNull(message = "Preço de venda é obrigatório")
    @Positive(message = "Preço de venda deve ser positivo")
    @Column(name = "preco_venda", nullable = false)
    private BigDecimal precoVenda;

    @NotNull(message = "Custo de aquisição é obrigatório")
    @Positive(message = "Custo deve ser positivo")
    @Column(name = "custo_aquisicao", nullable = false)
    private BigDecimal custoAquisicao;

    @NotNull(message = "Quantidade é obrigatória")
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade = 0;

    @NotNull(message = "Estoque mínimo é obrigatório")
    @Column(name = "estoque_minimo", nullable = false)
    private Integer estoque_minimo = 5;

    @Column(name = "imagem_url")
    private String imagemUrl;

    @Column(name = "preco_oferta")
    private BigDecimal precoOferta;

    @Column(name = "em_oferta", nullable = false)
    private Boolean emOferta = false;

    public abstract String getDescricaoTecnica();

    public boolean isEstoqueCritico() {
        return this.quantidade <= this.estoque_minimo;
    }
}
