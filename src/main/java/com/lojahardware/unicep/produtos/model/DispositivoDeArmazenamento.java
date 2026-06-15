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
@Table(name = "dispositivos_armazenamento")
@DiscriminatorValue("ARMAZENAMENTO")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DispositivoDeArmazenamento extends Produto {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String marca;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String modelo;

    @NotNull
    @Positive
    @Column(name = "capacidade_gb", nullable = false)
    private Integer capacidadeGb;

    @NotBlank
    @Column(name = "tipo_armazenamento", nullable = false, length = 50)
    private String tipoArmazenamento;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String interface_;

    @NotNull
    @Positive
    @Column(name = "velocidade_leitura_mbs", nullable = false)
    private Integer velocidadeLeituraMBs;

    @Override
    public String getDescricaoTecnica() {
        return String.format("%s %s - %dGB %s (%s, %d MB/s)",
                this.marca, this.modelo, this.capacidadeGb,
                this.tipoArmazenamento, this.interface_, this.velocidadeLeituraMBs);
    }
}
