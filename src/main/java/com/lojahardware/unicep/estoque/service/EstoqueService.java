package com.lojahardware.unicep.estoque.service;

import com.lojahardware.unicep.estoque.model.Estoque;
import com.lojahardware.unicep.estoque.repository.EstoqueRepository;
import com.lojahardware.unicep.produtos.model.Produto;
import com.lojahardware.unicep.produtos.repository.ProdutoRepository;
import com.lojahardware.unicep.shared.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;

    public EstoqueService(EstoqueRepository estoqueRepository, ProdutoRepository produtoRepository) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Estoque criar(Long produtoId, Integer quantidade, Integer estoque_minimo) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> ApiException.notFound("Produto not found"));

        if (estoqueRepository.findByProdutoId(produtoId).isPresent()) {
            throw ApiException.conflict("Estoque already exists for this product");
        }

        Estoque estoque = new Estoque();
        estoque.setProduto(produto);
        estoque.setQuantidade(quantidade);
        estoque.setEstoque_minimo(estoque_minimo);

        return estoqueRepository.save(estoque);
    }

    @Transactional(readOnly = true)
    public Estoque buscarPorProdutoId(Long produtoId) {
        return estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> ApiException.notFound("Estoque not found for this product"));
    }

    @Transactional(readOnly = true)
    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Estoque> listarCriticos() {
        return estoqueRepository.findAll().stream()
                .filter(Estoque::isCritico)
                .toList();
    }

    @Transactional
    public void baixar(Long produtoId, Integer quantidade) {
        Estoque estoque = buscarPorProdutoId(produtoId);
        if (!estoque.temEstoque(quantidade)) {
            throw ApiException.badRequest("Insufficient stock. Available: " + estoque.getQuantidade());
        }
        estoque.baixar(quantidade);
        estoqueRepository.save(estoque);

        // Sincroniza quantidade no Produto
        Produto p = estoque.getProduto();
        p.setQuantidade(estoque.getQuantidade());
        produtoRepository.save(p);

        log.info("Stock decreased for product {} by {}", produtoId, quantidade);
    }

    @Transactional
    public void repor(Long produtoId, Integer quantidade) {
        Estoque estoque = buscarPorProdutoId(produtoId);
        estoque.repor(quantidade);
        estoqueRepository.save(estoque);

        // Sincroniza quantidade no Produto
        Produto p = estoque.getProduto();
        p.setQuantidade(estoque.getQuantidade());
        produtoRepository.save(p);

        log.info("Stock replenished for product {} by {}", produtoId, quantidade);
    }
}
