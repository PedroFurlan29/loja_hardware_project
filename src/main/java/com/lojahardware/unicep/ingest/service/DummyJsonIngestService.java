package com.lojahardware.unicep.ingest.service;

import com.lojahardware.unicep.ingest.model.DummyJsonProductDTO;
import com.lojahardware.unicep.produtos.model.*;
import com.lojahardware.unicep.produtos.repository.ProdutoRepository;
import com.lojahardware.unicep.estoque.service.EstoqueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Random;

@Service
@Slf4j
public class DummyJsonIngestService {

    private final ProdutoRepository produtoRepository;
    private final EstoqueService estoqueService;
    private final RestTemplate restTemplate;
    private static final String DUMMY_JSON_URL = "https://dummyjson.com/products?limit=30";

    public DummyJsonIngestService(ProdutoRepository produtoRepository, EstoqueService estoqueService,
                                  RestTemplate restTemplate) {
        this.produtoRepository = produtoRepository;
        this.estoqueService = estoqueService;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void ingestirDados() {
        try {
            log.info("Iniciando ingestão de dados do DummyJSON...");

            DummyJsonProductDTO.DummyJsonResponse response = restTemplate.getForObject(
                    DUMMY_JSON_URL,
                    DummyJsonProductDTO.DummyJsonResponse.class
            );

            if (response == null || response.getProducts() == null) {
                log.warn("Nenhum dado retornado do DummyJSON");
                return;
            }

            Random random = new Random();

            for (DummyJsonProductDTO dto : response.getProducts()) {
                if (produtoRepository.findBySku("SKU-" + dto.getId()).isPresent()) {
                    continue;
                }

                Produto produto = mapearProduto(dto, random);
                if (produto != null) {
                    Produto produtoSalvo = produtoRepository.save(produto);

                    Integer quantidade = dto.getStock() > 0 ? dto.getStock() : random.nextInt(50) + 10;
                    estoqueService.criar(produtoSalvo.getId(), quantidade, 5);

                    log.debug("Produto {} ingerido com sucesso", produtoSalvo.getSku());
                }
            }

            log.info("Ingestão de dados concluída com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao ingerir dados do DummyJSON: ", e);
        }
    }

    private Produto mapearProduto(DummyJsonProductDTO dto, Random random) {
        String category = dto.getCategory().toLowerCase();
        String sku = "SKU-" + dto.getId();
        BigDecimal preco = BigDecimal.valueOf(dto.getPrice() != null ? dto.getPrice() : 100);
        BigDecimal custo = preco.multiply(BigDecimal.valueOf(0.6));

        if (category.contains("laptop") || category.contains("phone") || category.contains("computer")) {
            CPU cpu = new CPU();
            cpu.setSku(sku);
            cpu.setDescricao(dto.getTitle());
            cpu.setPrecoVenda(preco);
            cpu.setCustoAquisicao(custo);
            cpu.setQuantidade(dto.getStock() != null ? dto.getStock() : random.nextInt(50));
            cpu.setEstoque_minimo(5);
            cpu.setMarca(dto.getBrand() != null ? dto.getBrand() : "Unknown");
            cpu.setModelo(dto.getTitle().substring(0, Math.min(30, dto.getTitle().length())));
            cpu.setNucleos(random.nextInt(16) + 2);
            cpu.setFrequenciaGHz(2.0 + random.nextDouble() * 4);
            cpu.setArquitetura("x86");
            cpu.setSocket("LGA1700");
            return cpu;
        } else if (category.contains("graphics") || category.contains("gpu")) {
            PlacaDeVideo gpu = new PlacaDeVideo();
            gpu.setSku(sku);
            gpu.setDescricao(dto.getTitle());
            gpu.setPrecoVenda(preco);
            gpu.setCustoAquisicao(custo);
            gpu.setQuantidade(dto.getStock() != null ? dto.getStock() : random.nextInt(50));
            gpu.setEstoque_minimo(5);
            gpu.setMarca(dto.getBrand() != null ? dto.getBrand() : "Unknown");
            gpu.setModelo(dto.getTitle().substring(0, Math.min(30, dto.getTitle().length())));
            gpu.setVramGb(random.nextInt(24) + 2);
            gpu.setTipoMemoria("GDDR6");
            gpu.setChipset("RTX 3050");
            gpu.setLarguraBandaBits(128);
            return gpu;
        } else if (category.contains("memory") || category.contains("ram")) {
            Memoria memoria = new Memoria();
            memoria.setSku(sku);
            memoria.setDescricao(dto.getTitle());
            memoria.setPrecoVenda(preco);
            memoria.setCustoAquisicao(custo);
            memoria.setQuantidade(dto.getStock() != null ? dto.getStock() : random.nextInt(50));
            memoria.setEstoque_minimo(5);
            memoria.setMarca(dto.getBrand() != null ? dto.getBrand() : "Unknown");
            memoria.setCapacidadeGb(random.nextInt(64) + 4);
            memoria.setTipoMemoria("DDR4");
            memoria.setFrequenciaMHz(3200 + random.nextInt(800));
            memoria.setLatenciaCl(random.nextInt(4) + 16);
            return memoria;
        } else {
            DispositivoDeArmazenamento armazenamento = new DispositivoDeArmazenamento();
            armazenamento.setSku(sku);
            armazenamento.setDescricao(dto.getTitle());
            armazenamento.setPrecoVenda(preco);
            armazenamento.setCustoAquisicao(custo);
            armazenamento.setQuantidade(dto.getStock() != null ? dto.getStock() : random.nextInt(50));
            armazenamento.setEstoque_minimo(5);
            armazenamento.setMarca(dto.getBrand() != null ? dto.getBrand() : "Unknown");
            armazenamento.setModelo(dto.getTitle().substring(0, Math.min(30, dto.getTitle().length())));
            armazenamento.setCapacidadeGb(random.nextInt(2000) + 256);
            armazenamento.setTipoArmazenamento("SSD");
            armazenamento.setInterface_("NVMe");
            armazenamento.setVelocidadeLeituraMBs(random.nextInt(3000) + 500);
            return armazenamento;
        }
    }
}
