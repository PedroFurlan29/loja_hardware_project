package com.lojahardware.unicep.produtos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "memorias")
@DiscriminatorValue("MEMORIA")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Memoria extends Produto {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String marca;

    @NotNull
    @Positive
    @Column(name = "capacidade_gb", nullable = false)
    private Integer capacidadeGb;

    @NotBlank
    @Column(name = "tipo_memoria", nullable = false, length = 50)
    private String tipoMemoria;

    @NotNull
    @Positive
    @Column(name = "frequencia_mhz", nullable = false)
    private Integer frequenciaMHz;

    @NotNull
    @Positive
    @Column(name = "latencia_cl", nullable = false)
    private Integer latenciaCl;

    @Override
    public String getDescricaoTecnica() {
        return String.format("%s %dGB %s - %dMHz CL%d",
                this.marca, this.capacidadeGb, this.tipoMemoria,
                this.frequenciaMHz, this.latenciaCl);
    }
}
