package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import enums.TipoMemoria;

public class MemoriaTest {

    @Test
    void deveCriarMemoriaCorretamente() {

        Memoria memoria = new Memoria(
                "RAM-001",
                "Kingston Fury",
                350,
                10,
                2,
                16,
                TipoMemoria.DDR5
        );

        assertEquals(16, memoria.getCapacidadeGB());
        assertEquals(TipoMemoria.DDR5, memoria.getTipo());
    }

    @Test
    void deveAlterarCapacidadeDaMemoria() {

        Memoria memoria = new Memoria(
                "RAM-001",
                "Kingston Fury",
                350,
                10,
                2,
                16,
                TipoMemoria.DDR5
        );

        memoria.setCapacidadeGB(32);

        assertEquals(32, memoria.getCapacidadeGB());
    }
}