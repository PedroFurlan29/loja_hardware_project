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
@Table(name = "placas_de_video")
@DiscriminatorValue("PLACA_VIDEO")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlacaDeVideo extends Produto {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String marca;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String modelo;

    @NotNull
    @Positive
    @Column(name = "vram_gb", nullable = false)
    private Integer vramGb;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String tipoMemoria;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String chipset;

    @NotNull
    @Positive
    @Column(name = "largura_banda_bits", nullable = false)
    private Integer larguraBandaBits;

    @Override
    public String getDescricaoTecnica() {
        return String.format("%s %s - %dGB %s, %d-bit - %s",
                this.marca, this.modelo, this.vramGb, this.tipoMemoria,
                this.larguraBandaBits, this.chipset);
    }
}
