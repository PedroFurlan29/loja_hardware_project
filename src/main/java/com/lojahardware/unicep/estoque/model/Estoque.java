package com.lojahardware.unicep.estoque.model;

import com.lojahardware.unicep.produtos.model.Produto;
import com.lojahardware.unicep.shared.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estoque")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Estoque extends BaseEntity {

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false, unique = true)
    private Produto produto;

    @NotNull
    @Column(nullable = false)
    private Integer quantidade;

    @NotNull
    @Column(name = "estoque_minimo", nullable = false)
    private Integer estoque_minimo;

    public boolean temEstoque(Integer quantidade) {
        return this.quantidade >= quantidade;
    }

    public void baixar(Integer quantidade) {
        this.quantidade -= quantidade;
    }

    public void repor(Integer quantidade) {
        this.quantidade += quantidade;
    }

    public boolean isCritico() {
        return this.quantidade <= this.estoque_minimo;
    }
}
