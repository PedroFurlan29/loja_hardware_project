package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FornecedorTest {

    @Test
    void deveCriarFornecedorCorretamente() {

        Fornecedor fornecedor = new Fornecedor(
                "12.345.678/0001-99",
                "Kabum"
        );

        assertEquals("12.345.678/0001-99", fornecedor.getCnpj());
        assertEquals("Kabum", fornecedor.getRazaoSocial());
    }

    @Test
    void deveAlterarRazaoSocialFornecedor() {

        Fornecedor fornecedor = new Fornecedor(
                "12.345.678/0001-99",
                "Kabum"
        );

        fornecedor.setRazaoSocial("Pichau");

        assertEquals("Pichau", fornecedor.getRazaoSocial());
    }

    @Test
    void deveAlterarCnpjFornecedor() {

        Fornecedor fornecedor = new Fornecedor(
                "12.345.678/0001-99",
                "Kabum"
        );

        fornecedor.setCnpj("98.765.432/0001-11");

        assertEquals("98.765.432/0001-11", fornecedor.getCnpj());
    }
}