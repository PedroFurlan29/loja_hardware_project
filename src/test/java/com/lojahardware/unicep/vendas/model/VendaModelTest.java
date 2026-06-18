package com.lojahardware.unicep.vendas.model;

import com.lojahardware.unicep.produtos.model.CPU;
import com.lojahardware.unicep.produtos.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Venda - Testes Unitários do Modelo")
class VendaModelTest {

    private Venda venda;
    private Produto produto;

    @BeforeEach
    void setUp() {
        venda = new Venda();
        produto = buildCpu("CPU-001", BigDecimal.valueOf(1500));
    }

    private CPU buildCpu(String sku, BigDecimal preco) {
        CPU cpu = new CPU();
        cpu.setSku(sku);
        cpu.setDescricao("Processador Teste");
        cpu.setPrecoVenda(preco);
        cpu.setCustoAquisicao(BigDecimal.valueOf(1000));
        cpu.setQuantidade(10);
        cpu.setEstoque_minimo(2);
        cpu.setMarca("Intel");
        cpu.setModelo("i7-Test");
        cpu.setNucleos(8);
        cpu.setFrequenciaGHz(3.4);
        cpu.setArquitetura("x86-64");
        cpu.setSocket("LGA1700");
        return cpu;
    }

    private ItemVenda buildItem(Produto p, int qty, BigDecimal preco) {
        ItemVenda item = new ItemVenda();
        item.setProduto(p);
        item.setQuantidade(qty);
        item.setPrecoUnitario(preco);
        return item;
    }

    @Nested
    @DisplayName("calcularTotal()")
    class CalcularTotal {

        @Test
        @DisplayName("deve calcular zero quando não há itens")
        void deveRetornarZero_QuandoSemItens() {
            venda.calcularTotal();
            assertThat(venda.getValorTotal()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("deve calcular o subtotal de um único item")
        void deveCalcularUmItem() {
            ItemVenda item = buildItem(produto, 2, BigDecimal.valueOf(1500));
            venda.getItens().add(item);

            venda.calcularTotal();

            assertThat(venda.getValorTotal()).isEqualByComparingTo(BigDecimal.valueOf(3000));
        }

        @Test
        @DisplayName("deve somar os subtotais de múltiplos itens")
        void deveSomarMultiplosItens() {
            Produto outroProduto = buildCpu("CPU-002", BigDecimal.valueOf(800));
            ItemVenda item1 = buildItem(produto, 2, BigDecimal.valueOf(1500));        // 3000
            ItemVenda item2 = buildItem(outroProduto, 3, BigDecimal.valueOf(800));    // 2400

            venda.getItens().add(item1);
            venda.getItens().add(item2);
            venda.calcularTotal();

            assertThat(venda.getValorTotal()).isEqualByComparingTo(BigDecimal.valueOf(5400));
        }

        @Test
        @DisplayName("deve recalcular corretamente após adicionar mais itens")
        void deveRecalcularAposAdicionarItens() {
            ItemVenda item1 = buildItem(produto, 1, BigDecimal.valueOf(1500));
            venda.getItens().add(item1);
            venda.calcularTotal();
            assertThat(venda.getValorTotal()).isEqualByComparingTo(BigDecimal.valueOf(1500));

            Produto outro = buildCpu("CPU-003", BigDecimal.valueOf(500));
            ItemVenda item2 = buildItem(outro, 2, BigDecimal.valueOf(500));
            venda.getItens().add(item2);
            venda.calcularTotal();
            assertThat(venda.getValorTotal()).isEqualByComparingTo(BigDecimal.valueOf(2500));
        }
    }

    @Nested
    @DisplayName("adicionarItem()")
    class AdicionarItem {

        @Test
        @DisplayName("deve adicionar item à lista e setar a referência da venda no item")
        void deveAdicionarItemESetarVenda() {
            ItemVenda item = buildItem(produto, 1, BigDecimal.valueOf(1500));

            venda.adicionarItem(item);

            assertThat(venda.getItens()).hasSize(1);
            assertThat(item.getVenda()).isSameAs(venda);
        }

        @Test
        @DisplayName("deve adicionar múltiplos itens corretamente")
        void deveAdicionarMultiplosItens() {
            ItemVenda item1 = buildItem(produto, 1, BigDecimal.valueOf(1500));
            Produto outro = buildCpu("CPU-002", BigDecimal.valueOf(500));
            ItemVenda item2 = buildItem(outro, 2, BigDecimal.valueOf(500));

            venda.adicionarItem(item1);
            venda.adicionarItem(item2);

            assertThat(venda.getItens()).hasSize(2);
            assertThat(item1.getVenda()).isSameAs(venda);
            assertThat(item2.getVenda()).isSameAs(venda);
        }
    }

    @Nested
    @DisplayName("removerItem()")
    class RemoverItem {

        @Test
        @DisplayName("deve remover item da lista")
        void deveRemoverItem() {
            ItemVenda item = buildItem(produto, 1, BigDecimal.valueOf(1500));
            venda.getItens().add(item);

            venda.removerItem(item);

            assertThat(venda.getItens()).isEmpty();
        }

        @Test
        @DisplayName("deve remover apenas o item correto de múltiplos itens")
        void deveRemoverApenasItemCorreto() {
            ItemVenda item1 = buildItem(produto, 1, BigDecimal.valueOf(1500));
            Produto outro = buildCpu("CPU-002", BigDecimal.valueOf(500));
            ItemVenda item2 = buildItem(outro, 2, BigDecimal.valueOf(500));

            venda.getItens().add(item1);
            venda.getItens().add(item2);
            venda.removerItem(item1);

            assertThat(venda.getItens()).hasSize(1).containsExactly(item2);
        }
    }

    @Nested
    @DisplayName("Status inicial")
    class StatusInicial {

        @Test
        @DisplayName("deve inicializar com status PENDENTE")
        void deveInicializarComStatusPendente() {
            assertThat(venda.getStatus()).isEqualTo(StatusVenda.PENDENTE);
        }

        @Test
        @DisplayName("deve inicializar com valor total zero")
        void deveInicializarComValorTotalZero() {
            assertThat(venda.getValorTotal()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("deve inicializar com lista de itens vazia")
        void deveInicializarComListaVazia() {
            assertThat(venda.getItens()).isNotNull().isEmpty();
        }
    }
}