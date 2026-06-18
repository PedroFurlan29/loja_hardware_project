package com.lojahardware.unicep.produtos.dto;

import com.lojahardware.unicep.produtos.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProdutoMapper - Testes Unitários")
class ProdutoMapperTest {

    private void preencherCamposProduto(Produto p, String sku) {
        p.setSku(sku);
        p.setDescricao("Descrição " + sku);
        p.setPrecoVenda(new BigDecimal("1500.00"));
        p.setPrecoOferta(new BigDecimal("1350.00"));
        p.setCustoAquisicao(new BigDecimal("1000.00"));
        p.setQuantidade(10);
        p.setEstoque_minimo(2);
        p.setImagemUrl("http://img.test/" + sku + ".jpg");
        p.setEmOferta(true);
    }

    @Test
    @DisplayName("toDTO() deve retornar null quando produto é null")
    void toDTO_DeveRetornarNull_QuandoProdutoNulo() {
        assertThat(ProdutoMapper.toDTO(null)).isNull();
    }

    @Nested
    @DisplayName("CPU")
    class CpuMapeamento {

        @Test
        @DisplayName("deve mapear todos os campos gerais do produto")
        void deveMapearCamposGeraisDoProduto() {
            CPU cpu = new CPU();
            preencherCamposProduto(cpu, "CPU-001");
            cpu.setMarca("Intel");
            cpu.setModelo("Core i7-13700K");
            cpu.setNucleos(16);
            cpu.setFrequenciaGHz(3.4);
            cpu.setArquitetura("x86-64");
            cpu.setSocket("LGA1700");

            ProdutoDTO dto = ProdutoMapper.toDTO(cpu);

            assertThat(dto).isNotNull();
            assertThat(dto.getSku()).isEqualTo("CPU-001");
            assertThat(dto.getDescricao()).isEqualTo("Descrição CPU-001");
            assertThat(dto.getPrecoVenda()).isEqualByComparingTo(new BigDecimal("1500.00"));
            assertThat(dto.getPrecoOferta()).isEqualByComparingTo(new BigDecimal("1350.00"));
            assertThat(dto.getCustoAquisicao()).isEqualByComparingTo(new BigDecimal("1000.00"));
            assertThat(dto.getQuantidade()).isEqualTo(10);
            assertThat(dto.getEstoque_minimo()).isEqualTo(2);
            assertThat(dto.getImagemUrl()).isEqualTo("http://img.test/CPU-001.jpg");
            assertThat(dto.getEmOferta()).isTrue();
            assertThat(dto.getEstoqueCritico()).isFalse(); // 10 > 2
        }

        @Test
        @DisplayName("deve mapear campos específicos de CPU e setar tipoProduto como CPU")
        void deveMapearCamposEspecificosDeCpu() {
            CPU cpu = new CPU();
            preencherCamposProduto(cpu, "CPU-001");
            cpu.setMarca("AMD");
            cpu.setModelo("Ryzen 9 7950X");
            cpu.setNucleos(16);
            cpu.setFrequenciaGHz(4.5);
            cpu.setArquitetura("Zen 4");
            cpu.setSocket("AM5");

            ProdutoDTO dto = ProdutoMapper.toDTO(cpu);

            assertThat(dto.getTipoProduto()).isEqualTo("CPU");
            assertThat(dto.getMarca()).isEqualTo("AMD");
            assertThat(dto.getModelo()).isEqualTo("Ryzen 9 7950X");
            assertThat(dto.getNucleos()).isEqualTo(16);
            assertThat(dto.getFrequenciaGHz()).isEqualTo(4.5);
            assertThat(dto.getArquitetura()).isEqualTo("Zen 4");
            assertThat(dto.getSocket()).isEqualTo("AM5");
        }

        @Test
        @DisplayName("deve mapear descricaoTecnica da CPU corretamente")
        void deveMapearDescricaoTecnicaDaCpu() {
            CPU cpu = new CPU();
            preencherCamposProduto(cpu, "CPU-002");
            cpu.setMarca("Intel");
            cpu.setModelo("Core i5");
            cpu.setNucleos(6);
            cpu.setFrequenciaGHz(3.0);
            cpu.setArquitetura("x86-64");
            cpu.setSocket("LGA1200");

            ProdutoDTO dto = ProdutoMapper.toDTO(cpu);

            assertThat(dto.getDescricaoTecnica())
                    .isEqualTo("Intel Core i5 - 6 núcleos @ 3,00 GHz (x86-64, Socket LGA1200)");
        }

        @Test
        @DisplayName("deve indicar estoqueCritico como true quando quantidade <= estoqueMinimo")
        void deveIndicarEstoqueCritico_QuandoEstoqueAbaixoMinimo() {
            CPU cpu = new CPU();
            preencherCamposProduto(cpu, "CPU-003");
            cpu.setQuantidade(2);
            cpu.setEstoque_minimo(2);
            cpu.setMarca("Intel"); cpu.setModelo("i3"); cpu.setNucleos(4);
            cpu.setFrequenciaGHz(2.4); cpu.setArquitetura("x86"); cpu.setSocket("LGA1151");

            ProdutoDTO dto = ProdutoMapper.toDTO(cpu);

            assertThat(dto.getEstoqueCritico()).isTrue();
        }
    }

    @Nested
    @DisplayName("PlacaDeVideo")
    class GpuMapeamento {

        @Test
        @DisplayName("deve mapear campos de GPU e setar tipoProduto como GPU")
        void deveMapearCamposDeGpu() {
            PlacaDeVideo gpu = new PlacaDeVideo();
            preencherCamposProduto(gpu, "GPU-001");
            gpu.setMarca("NVIDIA");
            gpu.setModelo("RTX 4090");
            gpu.setVramGb(24);
            gpu.setTipoMemoria("GDDR6X");
            gpu.setChipset("AD102");
            gpu.setLarguraBandaBits(384);

            ProdutoDTO dto = ProdutoMapper.toDTO(gpu);

            assertThat(dto.getTipoProduto()).isEqualTo("GPU");
            assertThat(dto.getMarca()).isEqualTo("NVIDIA");
            assertThat(dto.getModelo()).isEqualTo("RTX 4090");
            assertThat(dto.getVramGb()).isEqualTo(24);
            assertThat(dto.getTipoMemoria()).isEqualTo("GDDR6X");
            assertThat(dto.getChipset()).isEqualTo("AD102");
            assertThat(dto.getLarguraBandaBits()).isEqualTo(384);
        }

        @Test
        @DisplayName("deve mapear descricaoTecnica de GPU corretamente")
        void deveMapearDescricaoTecnicaDeGpu() {
            PlacaDeVideo gpu = new PlacaDeVideo();
            preencherCamposProduto(gpu, "GPU-002");
            gpu.setMarca("AMD"); gpu.setModelo("RX 7900 XTX");
            gpu.setVramGb(24); gpu.setTipoMemoria("GDDR6");
            gpu.setChipset("Navi 31"); gpu.setLarguraBandaBits(384);

            ProdutoDTO dto = ProdutoMapper.toDTO(gpu);

            assertThat(dto.getDescricaoTecnica())
                    .isEqualTo("AMD RX 7900 XTX - 24GB GDDR6, 384-bit - Navi 31");
        }
    }

    @Nested
    @DisplayName("Memoria")
    class MemoriaMapeamento {

        @Test
        @DisplayName("deve mapear campos de RAM e setar tipoProduto como RAM")
        void deveMapearCamposDeRam() {
            Memoria ram = new Memoria();
            preencherCamposProduto(ram, "RAM-001");
            ram.setMarca("Corsair");
            ram.setCapacidadeGb(16);
            ram.setTipoMemoria("DDR5");
            ram.setFrequenciaMHz(6000);
            ram.setLatenciaCl(36);

            ProdutoDTO dto = ProdutoMapper.toDTO(ram);

            assertThat(dto.getTipoProduto()).isEqualTo("RAM");
            assertThat(dto.getMarca()).isEqualTo("Corsair");
            assertThat(dto.getCapacidadeGb()).isEqualTo(16);
            assertThat(dto.getTipoMemoria()).isEqualTo("DDR5");
            assertThat(dto.getFrequenciaMHz()).isEqualTo(6000);
            assertThat(dto.getLatenciaCl()).isEqualTo(36);
        }

        @Test
        @DisplayName("deve mapear descricaoTecnica de RAM corretamente")
        void deveMapearDescricaoTecnicaDeRam() {
            Memoria ram = new Memoria();
            preencherCamposProduto(ram, "RAM-002");
            ram.setMarca("Kingston"); ram.setCapacidadeGb(32);
            ram.setTipoMemoria("DDR4"); ram.setFrequenciaMHz(3200); ram.setLatenciaCl(16);

            ProdutoDTO dto = ProdutoMapper.toDTO(ram);

            assertThat(dto.getDescricaoTecnica())
                    .isEqualTo("Kingston 32GB DDR4 - 3200MHz CL16");
        }
    }

    @Nested
    @DisplayName("DispositivoDeArmazenamento")
    class SsdMapeamento {

        @Test
        @DisplayName("deve mapear campos de SSD e setar tipoProduto como SSD")
        void deveMapearCamposDeSsd() {
            DispositivoDeArmazenamento ssd = new DispositivoDeArmazenamento();
            preencherCamposProduto(ssd, "SSD-001");
            ssd.setMarca("Samsung");
            ssd.setModelo("990 Pro");
            ssd.setCapacidadeGb(1000);
            ssd.setTipoArmazenamento("NVMe");
            ssd.setInterface_("PCIe 4.0");
            ssd.setVelocidadeLeituraMBs(7450);

            ProdutoDTO dto = ProdutoMapper.toDTO(ssd);

            assertThat(dto.getTipoProduto()).isEqualTo("SSD");
            assertThat(dto.getMarca()).isEqualTo("Samsung");
            assertThat(dto.getModelo()).isEqualTo("990 Pro");
            assertThat(dto.getCapacidadeGb()).isEqualTo(1000);
            assertThat(dto.getTipoArmazenamento()).isEqualTo("NVMe");
            assertThat(dto.getInterface_()).isEqualTo("PCIe 4.0");
            assertThat(dto.getVelocidadeLeituraMBs()).isEqualTo(7450);
        }

        @Test
        @DisplayName("deve mapear descricaoTecnica de SSD corretamente")
        void deveMapearDescricaoTecnicaDeSsd() {
            DispositivoDeArmazenamento ssd = new DispositivoDeArmazenamento();
            preencherCamposProduto(ssd, "SSD-002");
            ssd.setMarca("WD"); ssd.setModelo("Black SN850X");
            ssd.setCapacidadeGb(2000); ssd.setTipoArmazenamento("NVMe");
            ssd.setInterface_("PCIe 4.0"); ssd.setVelocidadeLeituraMBs(7300);

            ProdutoDTO dto = ProdutoMapper.toDTO(ssd);

            assertThat(dto.getDescricaoTecnica())
                    .isEqualTo("WD Black SN850X - 2000GB NVMe (PCIe 4.0, 7300 MB/s)");
        }
    }
}