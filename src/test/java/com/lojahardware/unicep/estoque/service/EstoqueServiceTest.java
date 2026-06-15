package com.lojahardware.unicep.estoque.service;

import com.lojahardware.unicep.AbstractIntegrationTest;
import com.lojahardware.unicep.estoque.model.Estoque;
import com.lojahardware.unicep.estoque.repository.EstoqueRepository;
import com.lojahardware.unicep.produtos.model.Memoria;
import com.lojahardware.unicep.produtos.repository.ProdutoRepository;
import com.lojahardware.unicep.shared.exception.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EstoqueServiceTest extends AbstractIntegrationTest {

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private Memoria memoria;

    @BeforeEach
    public void setup() {
        memoria = new Memoria();
        memoria.setSku("RAM-001");
        memoria.setDescricao("Test Memory");
        memoria.setPrecoVenda(BigDecimal.valueOf(100));
        memoria.setCustoAquisicao(BigDecimal.valueOf(50));
        memoria.setMarca("Corsair");
        memoria.setCapacidadeGb(16);
        memoria.setTipoMemoria("DDR4");
        memoria.setFrequenciaMHz(3200);
        memoria.setLatenciaCl(16);
        produtoRepository.save(memoria);
    }

    @Test
    public void testCriarEstoque() {
        var estoque = estoqueService.criar(memoria.getId(), 50, 5);

        assertNotNull(estoque);
        assertEquals(50, estoque.getQuantidade());
        assertEquals(5, estoque.getEstoque_minimo());
    }

    @Test
    public void testBaixarEstoque() {
        estoqueService.criar(memoria.getId(), 50, 5);
        estoqueService.baixar(memoria.getId(), 10);

        var estoque = estoqueService.buscarPorProdutoId(memoria.getId());
        assertEquals(40, estoque.getQuantidade());
    }

    @Test
    public void testBaixarEstoqueSemQtd() {
        estoqueService.criar(memoria.getId(), 5, 5);

        assertThrows(ApiException.class, () -> estoqueService.baixar(memoria.getId(), 10));
    }

    @Test
    public void testListarCriticos() {
        estoqueService.criar(memoria.getId(), 3, 5);

        List<Estoque> criticos = estoqueService.listarCriticos();

        assertEquals(1, criticos.size());
    }
}
