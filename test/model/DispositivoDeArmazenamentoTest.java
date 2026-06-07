package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import enums.TipoArmazenamento;

public class DispositivoDeArmazenamentoTest {

    @Test
    void deveCriarDispositivoCorretamente() {

        DispositivoDeArmazenamento armazenamento =
                new DispositivoDeArmazenamento(
                        "SSD001",
                        "SSD Kingston 1TB",
                        499.90,
                        10,
                        2,
                        1000,
                        TipoArmazenamento.SSD
                );

        assertEquals("SSD001", armazenamento.getSku());
        assertEquals("SSD Kingston 1TB", armazenamento.getNome());
        assertEquals(499.90, armazenamento.getPrecoVenda());
        assertEquals(10, armazenamento.getQtdEstoque());
        assertEquals(2, armazenamento.getQtdMinima());
        assertEquals(1000, armazenamento.getCapacidadeGB());
        assertEquals(TipoArmazenamento.SSD, armazenamento.getTipo());
    }

    @Test
    void deveAlterarCapacidadeGB() {

        DispositivoDeArmazenamento armazenamento =
                new DispositivoDeArmazenamento(
                        "SSD001",
                        "SSD Kingston 1TB",
                        499.90,
                        10,
                        2,
                        1000,
                        TipoArmazenamento.SSD
                );

        armazenamento.setCapacidadeGB(2000);

        assertEquals(2000, armazenamento.getCapacidadeGB());
    }

    @Test
    void deveLancarExcecaoAoDefinirCapacidadeInvalida() {

        DispositivoDeArmazenamento armazenamento =
                new DispositivoDeArmazenamento(
                        "SSD001",
                        "SSD Kingston 1TB",
                        499.90,
                        10,
                        2,
                        1000,
                        TipoArmazenamento.SSD
                );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> armazenamento.setCapacidadeGB(0)
        );

        assertEquals(
                "Capacidade deve ser maior que zero.",
                exception.getMessage()
        );
    }

    @Test
    void deveAlterarTipoArmazenamento() {

        DispositivoDeArmazenamento armazenamento =
                new DispositivoDeArmazenamento(
                        "SSD001",
                        "SSD Kingston 1TB",
                        499.90,
                        10,
                        2,
                        1000,
                        TipoArmazenamento.SSD
                );

        armazenamento.setTipo(TipoArmazenamento.NVME);

        assertEquals(TipoArmazenamento.NVME, armazenamento.getTipo());
    }
}