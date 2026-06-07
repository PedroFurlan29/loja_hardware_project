package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CPUTest {

    @Test
    void deveCriarCPUCorretamente() {

        CPU cpu = new CPU(
                "CPU001",
                "Ryzen 7 5700X",
                1299.90,
                8,
                2,
                "Zen 3",
                8
        );

        assertEquals("CPU001", cpu.getSku());
        assertEquals("Ryzen 7 5700X", cpu.getNome());
        assertEquals(1299.90, cpu.getPrecoVenda());
        assertEquals(8, cpu.getQtdEstoque());
        assertEquals(2, cpu.getQtdMinima());
        assertEquals("Zen 3", cpu.getArquitetura());
        assertEquals(8, cpu.getNucleos());
    }

    @Test
    void deveAlterarArquitetura() {

        CPU cpu = new CPU(
                "CPU001",
                "Ryzen 7 5700X",
                1299.90,
                8,
                2,
                "Zen 3",
                8
        );

        cpu.setArquitetura("Zen 4");

        assertEquals("Zen 4", cpu.getArquitetura());
    }

    @Test
    void deveAlterarNumeroDeNucleos() {

        CPU cpu = new CPU(
                "CPU001",
                "Ryzen 7 5700X",
                1299.90,
                8,
                2,
                "Zen 3",
                8
        );

        cpu.setNucleos(12);

        assertEquals(12, cpu.getNucleos());
    }

    @Test
    void deveLancarExcecaoAoDefinirNumeroDeNucleosInvalido() {

        CPU cpu = new CPU(
                "CPU001",
                "Ryzen 7 5700X",
                1299.90,
                8,
                2,
                "Zen 3",
                8
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cpu.setNucleos(0)
        );

        assertEquals(
                "Número de núcleos deve ser maior que zero.",
                exception.getMessage()
        );
    }
}