package com.lojahardware.unicep.vendas.dto;

import com.lojahardware.unicep.produtos.model.CPU;
import com.lojahardware.unicep.usuarios.model.PerfilUsuario;
import com.lojahardware.unicep.usuarios.model.Usuario;
import com.lojahardware.unicep.vendas.model.ItemVenda;
import com.lojahardware.unicep.vendas.model.StatusVenda;
import com.lojahardware.unicep.vendas.model.Venda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("VendaMapper - Testes Unitários")
class VendaMapperTest {

    private Usuario usuario;
    private CPU produto;
    private Venda venda;
    private ItemVenda item;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setNome("João Vendedor");
        usuario.setEmail("joao@loja.com");
        usuario.setSenha("senha");
        usuario.setPerfil(PerfilUsuario.VENDEDOR);

        produto = new CPU();
        produto.setSku("CPU-TEST");
        produto.setDescricao("Processador Teste");
        produto.setPrecoVenda(BigDecimal.valueOf(1500));
        produto.setCustoAquisicao(BigDecimal.valueOf(1000));
        produto.setQuantidade(10);
        produto.setEstoque_minimo(2);
        produto.setMarca("Intel"); produto.setModelo("i7");
        produto.setNucleos(8); produto.setFrequenciaGHz(3.4);
        produto.setArquitetura("x86-64"); produto.setSocket("LGA1700");

        item = new ItemVenda();
        item.setId(1L);
        item.setProduto(produto);
        item.setQuantidade(2);
        item.setPrecoUnitario(BigDecimal.valueOf(1500));

        venda = new Venda();
        venda.setId(10L);
        venda.setUsuario(usuario);
        venda.setStatus(StatusVenda.CONCLUIDA);
        venda.setValorTotal(BigDecimal.valueOf(3000));
        venda.getItens().add(item);
        item.setVenda(venda);
    }

    @Nested
    @DisplayName("toDTO(Venda)")
    class ToDTOVenda {

        @Test
        @DisplayName("deve retornar null quando venda é null")
        void deveRetornarNull_QuandoVendaNula() {
            assertThat(VendaMapper.toDTO(null)).isNull();
        }

        @Test
        @DisplayName("deve mapear o id da venda")
        void deveMapearId() {
            VendaDTO dto = VendaMapper.toDTO(venda);
            assertThat(dto.getId()).isEqualTo(10L);
        }

        @Test
        @DisplayName("deve mapear o nome do usuário")
        void deveMapearNomeDoUsuario() {
            VendaDTO dto = VendaMapper.toDTO(venda);
            assertThat(dto.getUsuarioNome()).isEqualTo("João Vendedor");
        }

        @Test
        @DisplayName("deve mapear o status como String")
        void deveMapearStatus() {
            VendaDTO dto = VendaMapper.toDTO(venda);
            assertThat(dto.getStatus()).isEqualTo("CONCLUIDA");
        }

        @Test
        @DisplayName("deve mapear o valor total")
        void deveMapearValorTotal() {
            VendaDTO dto = VendaMapper.toDTO(venda);
            assertThat(dto.getValorTotal()).isEqualByComparingTo(BigDecimal.valueOf(3000));
        }

        @Test
        @DisplayName("deve mapear o motivo de cancelamento quando presente")
        void deveMapearMotivoCancelamento() {
            venda.setMotivoCancelamento("Produto defeituoso");
            VendaDTO dto = VendaMapper.toDTO(venda);
            assertThat(dto.getMotivoCancelamento()).isEqualTo("Produto defeituoso");
        }

        @Test
        @DisplayName("deve mapear os itens da venda")
        void deveMapearItens() {
            VendaDTO dto = VendaMapper.toDTO(venda);
            assertThat(dto.getItens()).hasSize(1);
        }

        @Test
        @DisplayName("deve retornar usuarioNome null quando usuario é null")
        void deveRetornarNomeNulo_QuandoUsuarioNulo() {
            venda.setUsuario(null);
            VendaDTO dto = VendaMapper.toDTO(venda);
            assertThat(dto.getUsuarioNome()).isNull();
        }

        @Test
        @DisplayName("deve retornar status null quando status é null")
        void deveRetornarStatusNulo_QuandoStatusNulo() {
            venda.setStatus(null);
            VendaDTO dto = VendaMapper.toDTO(venda);
            assertThat(dto.getStatus()).isNull();
        }
    }

    @Nested
    @DisplayName("toItemDTO(ItemVenda)")
    class ToItemDTO {

        @Test
        @DisplayName("deve retornar null quando item é null")
        void deveRetornarNull_QuandoItemNulo() {
            assertThat(VendaMapper.toItemDTO(null)).isNull();
        }

        @Test
        @DisplayName("deve mapear o id do item")
        void deveMapearId() {
            ItemVendaDTO dto = VendaMapper.toItemDTO(item);
            assertThat(dto.getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("deve mapear a descrição do produto como produtoNome")
        void deveMapearDescricaoDoProduto() {
            ItemVendaDTO dto = VendaMapper.toItemDTO(item);
            assertThat(dto.getProdutoNome()).isEqualTo("Processador Teste");
        }

        @Test
        @DisplayName("deve mapear o SKU do produto")
        void deveMapearSkuDoProduto() {
            ItemVendaDTO dto = VendaMapper.toItemDTO(item);
            assertThat(dto.getProdutoSku()).isEqualTo("CPU-TEST");
        }

        @Test
        @DisplayName("deve mapear a quantidade do item")
        void deveMapearQuantidade() {
            ItemVendaDTO dto = VendaMapper.toItemDTO(item);
            assertThat(dto.getQuantidade()).isEqualTo(2);
        }

        @Test
        @DisplayName("deve mapear o preço unitário")
        void deveMapearPrecoUnitario() {
            ItemVendaDTO dto = VendaMapper.toItemDTO(item);
            assertThat(dto.getPrecoUnitario()).isEqualByComparingTo(BigDecimal.valueOf(1500));
        }

        @Test
        @DisplayName("deve calcular e mapear o subtotal corretamente")
        void deveCalcularEMapearSubtotal() {
            ItemVendaDTO dto = VendaMapper.toItemDTO(item);
            assertThat(dto.getSubtotal()).isEqualByComparingTo(BigDecimal.valueOf(3000)); // 1500 * 2
        }

        @Test
        @DisplayName("deve retornar produtoNome null quando produto é null")
        void deveRetornarNomeProdutoNulo_QuandoProdutoNulo() {
            item.setProduto(null);
            ItemVendaDTO dto = VendaMapper.toItemDTO(item);
            assertThat(dto.getProdutoNome()).isNull();
            assertThat(dto.getProdutoSku()).isNull();
        }
    }

    @Nested
    @DisplayName("toDTOList(List<Venda>)")
    class ToDTOList {

        @Test
        @DisplayName("deve converter lista de vendas para lista de DTOs")
        void deveConverterListaDeVendas() {
            Venda venda2 = new Venda();
            venda2.setId(20L);
            venda2.setUsuario(usuario);
            venda2.setStatus(StatusVenda.CANCELADA);
            venda2.setValorTotal(BigDecimal.ZERO);

            List<VendaDTO> dtos = VendaMapper.toDTOList(List.of(venda, venda2));

            assertThat(dtos).hasSize(2);
            assertThat(dtos.get(0).getId()).isEqualTo(10L);
            assertThat(dtos.get(1).getId()).isEqualTo(20L);
        }

        @Test
        @DisplayName("deve retornar lista vazia para lista vazia de entrada")
        void deveRetornarListaVazia_QuandoEntradaVazia() {
            List<VendaDTO> dtos = VendaMapper.toDTOList(List.of());
            assertThat(dtos).isEmpty();
        }
    }
}