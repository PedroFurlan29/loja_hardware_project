package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import enums.TipoMemoria;

public class ItemVendaTest {

    @Test
    void deveCalcularSubtotalCorretamente() {

        Memoria memoria = new Memoria(
                "RAM-001",
                "Kingston",
                300,
                10,
                5,
                16,
                TipoMemoria.DDR5
        );

        Fornecedor fornecedor = new Fornecedor(
                "Kabum",
                "11999999999"
        );

        ItemVenda item = new ItemVenda(
                memoria,
                2,
                fornecedor
        );

        assertEquals(600, item.getSubtotal());
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeForZero() {

        Memoria memoria = new Memoria(
                "RAM-001",
                "Kingston",
                300,
                10,
                5,
                16,
                TipoMemoria.DDR5
        );

        Fornecedor fornecedor = new Fornecedor(
                "Kabum",
                "11999999999"
        );

        assertThrows(IllegalArgumentException.class, () -> {

            new ItemVenda(
                    memoria,
                    0,
                    fornecedor
            );
        });
    }
}