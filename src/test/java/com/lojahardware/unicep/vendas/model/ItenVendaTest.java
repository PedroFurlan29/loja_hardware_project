package com.lojahardware.unicep.vendas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ItemVenda - Testes Unitários do Modelo")
class ItemVendaTest {

    @Test
    @DisplayName("getSubtotal() deve retornar precoUnitario × quantidade")
    void deveCalcularSubtotalCorretamente() {
        ItemVenda item = new ItemVenda();
        item.setPrecoUnitario(BigDecimal.valueOf(1500));
        item.setQuantidade(3);

        assertThat(item.getSubtotal()).isEqualByComparingTo(BigDecimal.valueOf(4500));
    }

    @Test
    @DisplayName("getSubtotal() deve retornar zero quando preço é zero")
    void deveRetornarZero_QuandoPrecoZero() {
        ItemVenda item = new ItemVenda();
        item.setPrecoUnitario(BigDecimal.ZERO);
        item.setQuantidade(5);

        assertThat(item.getSubtotal()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("getSubtotal() deve funcionar com quantidade unitária")
    void deveCalcularSubtotal_QuantidadeUm() {
        ItemVenda item = new ItemVenda();
        item.setPrecoUnitario(BigDecimal.valueOf(999.99));
        item.setQuantidade(1);

        assertThat(item.getSubtotal()).isEqualByComparingTo(BigDecimal.valueOf(999.99));
    }

    @Test
    @DisplayName("getSubtotal() deve lidar com valores decimais")
    void deveCalcularSubtotal_ComDecimais() {
        ItemVenda item = new ItemVenda();
        item.setPrecoUnitario(new BigDecimal("299.90"));
        item.setQuantidade(4);

        assertThat(item.getSubtotal()).isEqualByComparingTo(new BigDecimal("1199.60"));
    }

    // --- StatusVenda enum ---

    @Test
    @DisplayName("StatusVenda deve ter 4 valores definidos")
    void statusVenda_DeveConterQuatroValores() {
        assertThat(StatusVenda.values()).hasSize(4);
    }

    @Test
    @DisplayName("StatusVenda.PENDENTE deve ter a descrição correta")
    void statusPendente_DeveConterDescricaoCorreta() {
        assertThat(StatusVenda.PENDENTE.getDescricao()).isEqualTo("Pendente");
    }

    @Test
    @DisplayName("StatusVenda.CONCLUIDA deve ter a descrição correta")
    void statusConcluida_DeveConterDescricaoCorreta() {
        assertThat(StatusVenda.CONCLUIDA.getDescricao()).isEqualTo("Concluída");
    }

    @Test
    @DisplayName("StatusVenda.CANCELADA deve ter a descrição correta")
    void statusCancelada_DeveConterDescricaoCorreta() {
        assertThat(StatusVenda.CANCELADA.getDescricao()).isEqualTo("Cancelada");
    }

    @Test
    @DisplayName("StatusVenda.SOLICITACAO_CANCELAMENTO deve ter a descrição correta")
    void statusSolicitacaoCancelamento_DeveConterDescricaoCorreta() {
        assertThat(StatusVenda.SOLICITACAO_CANCELAMENTO.getDescricao())
                .isEqualTo("Solicitação de cancelamento");
    }
}