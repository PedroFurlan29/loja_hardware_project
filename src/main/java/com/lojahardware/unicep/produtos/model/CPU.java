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
@Table(name = "cpus")
@DiscriminatorValue("CPU")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CPU extends Produto {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String marca;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String modelo;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer nucleos;

    @NotNull
    @Positive
    @Column(name = "frequencia_ghz", nullable = false)
    private Double frequenciaGHz;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String arquitetura;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String socket;

    @Override
    public String getDescricaoTecnica() {
        return String.format("%s %s - %d núcleos @ %.2f GHz (%s, Socket %s)",
                this.marca, this.modelo, this.nucleos, this.frequenciaGHz,
                this.arquitetura, this.socket);
    }
}
