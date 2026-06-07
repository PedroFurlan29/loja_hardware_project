package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import enums.TipoMemoria;

public class ProdutoTest {

    @Test
    void deveRetornarTrueQuandoEstoqueForCritico() {

        Memoria memoria = new Memoria(
                "RAM-001",
                "Kingston Fury",
                300,
                2,
                5,
                16,
                TipoMemoria.DDR5
        );

        assertTrue(memoria.isEstoqueCritico());
    }

    @Test
    void deveAtualizarEstoqueCorretamente() {

        Memoria memoria = new Memoria(
                "RAM-001",
                "Kingston Fury",
                300,
                10,
                5,
                16,
                TipoMemoria.DDR5
        );

        memoria.atualizarEstoque(-3);

        assertEquals(7, memoria.getQtdEstoque());
    }
}