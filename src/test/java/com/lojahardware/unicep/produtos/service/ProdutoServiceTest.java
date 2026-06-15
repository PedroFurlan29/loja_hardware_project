package com.lojahardware.unicep.produtos.service;

import com.lojahardware.unicep.AbstractIntegrationTest;
import com.lojahardware.unicep.produtos.model.PlacaDeVideo;
import com.lojahardware.unicep.produtos.repository.ProdutoRepository;
import com.lojahardware.unicep.shared.exception.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProdutoServiceTest extends AbstractIntegrationTest {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    private PlacaDeVideo gpu;

    @BeforeEach
    public void setup() {
        gpu = new PlacaDeVideo();
        gpu.setSku("GPU-001");
        gpu.setDescricao("Test GPU");
        gpu.setPrecoVenda(BigDecimal.valueOf(1000));
        gpu.setCustoAquisicao(BigDecimal.valueOf(600));
        gpu.setMarca("NVIDIA");
        gpu.setModelo("RTX 4090");
        gpu.setVramGb(24);
        gpu.setTipoMemoria("GDDR6X");
        gpu.setChipset("RTX 4090");
        gpu.setLarguraBandaBits(576);
    }

    @Test
    public void testCriarProduto() {
        var produtoCriado = produtoService.criar(gpu);

        assertNotNull(produtoCriado);
        assertNotNull(produtoCriado.getId());
        assertEquals("GPU-001", produtoCriado.getSku());
    }

    @Test
    public void testCriarProdutoDuplicado() {
        produtoService.criar(gpu);

        assertThrows(ApiException.class, () -> produtoService.criar(gpu));
    }

    @Test
    public void testBuscarPorSku() {
        produtoService.criar(gpu);

        var encontrado = produtoService.buscarPorSku("GPU-001");
        assertNotNull(encontrado);
        assertEquals("GPU-001", encontrado.getSku());
    }
}
