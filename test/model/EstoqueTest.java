package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import enums.TipoArmazenamento;

public class EstoqueTest {

    @Test
    void deveAdicionarProdutoAoEstoque() {

        Estoque estoque = new Estoque();

        Produto produto = new DispositivoDeArmazenamento(
                "SSD001",
                "SSD Kingston 1TB",
                499.90,
                10,
                2,
                1000,
                TipoArmazenamento.SSD
        );

        estoque.adicionarProduto(produto);

        assertEquals(1, estoque.getProdutos().size());
        assertEquals(produto, estoque.buscarPorSku("SSD001"));
    }

    @Test
    void naoDevePermitirSkuDuplicado() {

        Estoque estoque = new Estoque();

        Produto produto1 = new CPU(
                "CPU001",
                "Ryzen 5",
                899.90,
                5,
                1,
                "Zen 3",
                6
        );

        Produto produto2 = new CPU(
                "CPU001",
                "Ryzen 7",
                1499.90,
                3,
                1,
                "Zen 4",
                8
        );

        estoque.adicionarProduto(produto1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> estoque.adicionarProduto(produto2)
        );

        assertEquals(
                "Já existe um produto cadastrado com o SKU: CPU001",
                exception.getMessage()
        );
    }

    @Test
    void deveRemoverProdutoDoEstoque() {

        Estoque estoque = new Estoque();

        Produto produto = new CPU(
                "CPU001",
                "Ryzen 5",
                899.90,
                5,
                1,
                "Zen 3",
                6
        );

        estoque.adicionarProduto(produto);

        boolean removido = estoque.removerProduto("CPU001");

        assertTrue(removido);
        assertNull(estoque.buscarPorSku("CPU001"));
    }

    @Test
    void deveRegistrarEntradaDeEstoque() {

        Estoque estoque = new Estoque();

        Produto produto = new CPU(
                "CPU001",
                "Ryzen 5",
                899.90,
                5,
                1,
                "Zen 3",
                6
        );

        estoque.adicionarProduto(produto);

        estoque.registrarEntrada("CPU001", 10);

        assertEquals(15, produto.getQtdEstoque());
    }

    @Test
    void deveLancarExcecaoAoRegistrarEntradaInvalida() {

        Estoque estoque = new Estoque();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> estoque.registrarEntrada("CPU001", 0)
        );

        assertEquals(
                "A quantidade de entrada deve ser maior que zero.",
                exception.getMessage()
        );
    }

    @Test
    void deveLancarExcecaoAoBuscarProdutoInexistenteParaEntrada() {

        Estoque estoque = new Estoque();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> estoque.registrarEntrada("ABC123", 5)
        );

        assertEquals(
                "Produto com SKU 'ABC123' não encontrado.",
                exception.getMessage()
        );
    }

    @Test
    void deveListarProdutosCriticos() {

        Estoque estoque = new Estoque();

        Produto produto = new CPU(
                "CPU001",
                "Ryzen 5",
                899.90,
                1,
                2,
                "Zen 3",
                6
        );

        estoque.adicionarProduto(produto);

        List<Produto> criticos = estoque.listarCriticos();

        assertEquals(1, criticos.size());
        assertEquals(produto, criticos.get(0));
    }

    @Test
    void deveGerarRelatorioDeEstoque() {

        Estoque estoque = new Estoque();

        Produto produto = new CPU(
                "CPU001",
                "Ryzen 5",
                899.90,
                5,
                1,
                "Zen 3",
                6
        );

        estoque.adicionarProduto(produto);

        String relatorio = estoque.gerarRelatorio();

        assertTrue(relatorio.contains("RELATÓRIO DE ESTOQUE"));
        assertTrue(relatorio.contains("Ryzen 5"));
        assertTrue(relatorio.contains("CPU001"));
    }

    @Test
    void deveRetornarMensagemQuandoEstoqueEstiverVazio() {

        Estoque estoque = new Estoque();

        String relatorio = estoque.gerarRelatorio();

        assertEquals("Estoque vazio.", relatorio);
    }
}