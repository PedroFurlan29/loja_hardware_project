package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacaDeVideoTest {

    @Test
    void deveCriarPlacaDeVideoCorretamente() {

        PlacaDeVideo gpu = new PlacaDeVideo(
                "GPU001",
                "RTX 4060",
                2499.90,
                5,
                1,
                8,
                "NVIDIA"
        );

        assertEquals("GPU001", gpu.getSku());
        assertEquals("RTX 4060", gpu.getNome());
        assertEquals(2499.90, gpu.getPrecoVenda());
        assertEquals(5, gpu.getQtdEstoque());
        assertEquals(1, gpu.getQtdMinima());
        assertEquals(8, gpu.getMemoriaVRAM());
        assertEquals("NVIDIA", gpu.getChipset());
    }

    @Test
    void deveAlterarMemoriaVRAM() {

        PlacaDeVideo gpu = new PlacaDeVideo(
                "GPU001",
                "RTX 4060",
                2499.90,
                5,
                1,
                8,
                "NVIDIA"
        );

        gpu.setMemoriaVRAM(12);

        assertEquals(12, gpu.getMemoriaVRAM());
    }

    @Test
    void deveLancarExcecaoAoDefinirMemoriaVRAMInvalida() {

        PlacaDeVideo gpu = new PlacaDeVideo(
                "GPU001",
                "RTX 4060",
                2499.90,
                5,
                1,
                8,
                "NVIDIA"
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gpu.setMemoriaVRAM(0)
        );

        assertEquals(
                "Memória VRAM deve ser maior que zero.",
                exception.getMessage()
        );
    }

    @Test
    void deveAlterarChipset() {

        PlacaDeVideo gpu = new PlacaDeVideo(
                "GPU001",
                "RTX 4060",
                2499.90,
                5,
                1,
                8,
                "NVIDIA"
        );

        gpu.setChipset("AMD");

        assertEquals("AMD", gpu.getChipset());
    }
}