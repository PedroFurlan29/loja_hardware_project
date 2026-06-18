package com.lojahardware.unicep;

import com.lojahardware.unicep.estoque.model.Estoque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Estoque - Testes Unitários do Modelo")
class EstoqueTest {

    private Estoque estoque;

    @BeforeEach
    void setUp() {
        estoque = new Estoque();
        estoque.setQuantidade(10);
        estoque.setEstoque_minimo(5);
    }

    @Nested
    @DisplayName("temEstoque()")
    class TemEstoque {

        @Test
        @DisplayName("deve retornar true quando quantidade disponível é maior que a solicitada")
        void deveRetornarTrue_QuandoQuantidadeSuficiente() {
            assertThat(estoque.temEstoque(4)).isTrue();
        }

        @Test
        @DisplayName("deve retornar true quando quantidade disponível é exatamente igual à solicitada")
        void deveRetornarTrue_QuandoQuantidadeExata() {
            assertThat(estoque.temEstoque(10)).isTrue();
        }

        @Test
        @DisplayName("deve retornar false quando quantidade disponível é menor que a solicitada")
        void deveRetornarFalse_QuandoQuantidadeInsuficiente() {
            assertThat(estoque.temEstoque(11)).isFalse();
        }

        @Test
        @DisplayName("deve retornar false quando solicitada mais de zero e estoque é zero")
        void deveRetornarFalse_QuandoEstoqueZerado() {
            estoque.setQuantidade(0);
            assertThat(estoque.temEstoque(1)).isFalse();
        }
    }

    @Nested
    @DisplayName("baixar()")
    class Baixar {

        @Test
        @DisplayName("deve decrementar a quantidade corretamente")
        void deveDiminuirQuantidade() {
            estoque.baixar(3);
            assertThat(estoque.getQuantidade()).isEqualTo(7);
        }

        @Test
        @DisplayName("deve zerar o estoque quando baixado totalmente")
        void deveZerarEstoque_QuandoBaixadoTotal() {
            estoque.baixar(10);
            assertThat(estoque.getQuantidade()).isEqualTo(0);
        }

        @Test
        @DisplayName("deve decrementar em 1 corretamente")
        void deveDecrementarUm() {
            estoque.baixar(1);
            assertThat(estoque.getQuantidade()).isEqualTo(9);
        }
    }

    @Nested
    @DisplayName("repor()")
    class Repor {

        @Test
        @DisplayName("deve incrementar a quantidade corretamente")
        void deveIncrementarQuantidade() {
            estoque.repor(5);
            assertThat(estoque.getQuantidade()).isEqualTo(15);
        }

        @Test
        @DisplayName("deve repor quando estoque está zerado")
        void deveReporEstoqueZerado() {
            estoque.setQuantidade(0);
            estoque.repor(10);
            assertThat(estoque.getQuantidade()).isEqualTo(10);
        }

        @Test
        @DisplayName("deve acumular múltiplas reposições")
        void deveAcumularReposicoes() {
            estoque.repor(5);
            estoque.repor(3);
            assertThat(estoque.getQuantidade()).isEqualTo(18);
        }
    }

    @Nested
    @DisplayName("isCritico()")
    class IsCritico {

        @Test
        @DisplayName("deve retornar true quando quantidade é igual ao mínimo")
        void deveSerCritico_QuandoIgualAoMinimo() {
            estoque.setQuantidade(5);
            assertThat(estoque.isCritico()).isTrue();
        }

        @Test
        @DisplayName("deve retornar true quando quantidade está abaixo do mínimo")
        void deveSerCritico_QuandoAbaixoDoMinimo() {
            estoque.setQuantidade(4);
            assertThat(estoque.isCritico()).isTrue();
        }

        @Test
        @DisplayName("deve retornar false quando quantidade está acima do mínimo")
        void naoDeveSerCritico_QuandoAcimaDoMinimo() {
            estoque.setQuantidade(6);
            assertThat(estoque.isCritico()).isFalse();
        }

        @Test
        @DisplayName("deve retornar true quando estoque está zerado")
        void deveSerCritico_QuandoZerado() {
            estoque.setQuantidade(0);
            assertThat(estoque.isCritico()).isTrue();
        }
    }
}