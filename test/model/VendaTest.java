package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import enums.PerfilUsuario;
import enums.TipoMemoria;

public class VendaTest {

    @Test
    void deveAdicionarItemNaVenda() {

        Usuario vendedor = new Usuario(
                "vendedor",
                "123",
                PerfilUsuario.VENDEDOR
        );

        Venda venda = new Venda(vendedor);

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

        venda.adicionarItem(item);

        assertEquals(1, venda.getItens().size());
    }

    @Test
    void deveFinalizarVendaEReduzirEstoque() {

        Usuario vendedor = new Usuario(
                "vendedor",
                "123",
                PerfilUsuario.VENDEDOR
        );

        Venda venda = new Venda(vendedor);

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

        venda.adicionarItem(item);

        venda.finalizarVenda();

        assertEquals(8, memoria.getQtdEstoque());
    }

    @Test
    void naoDeveFinalizarVendaSemItens() {

        Usuario vendedor = new Usuario(
                "vendedor",
                "123",
                PerfilUsuario.VENDEDOR
        );

        Venda venda = new Venda(vendedor);

        assertThrows(IllegalStateException.class, () -> {
            venda.finalizarVenda();
        });
    }

    @Test
    void deveRestaurarEstoqueAoCancelarVenda() {

        Usuario vendedor = new Usuario(
                "vendedor",
                "123",
                PerfilUsuario.VENDEDOR
        );

        Usuario admin = new Usuario(
                "admin",
                "123",
                PerfilUsuario.ADMIN
        );

        Venda venda = new Venda(vendedor);

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

        venda.adicionarItem(item);

        venda.finalizarVenda();

        venda.solicitarCancelamento();

        venda.aprovarCancelamento(admin);

        assertEquals(10, memoria.getQtdEstoque());
    }
}