package com.lojahardware.unicep.produtos.service;

import com.lojahardware.unicep.produtos.model.Produto;
import com.lojahardware.unicep.produtos.repository.ProdutoRepository;
import com.lojahardware.unicep.shared.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Produto criar(Produto produto) {
        if (produtoRepository.findBySku(produto.getSku()).isPresent()) {
            throw ApiException.conflict("Product with SKU " + produto.getSku() + " already exists");
        }

        return produtoRepository.save(produto);
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> ApiException.notFound("Produto not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Produto buscarPorSku(String sku) {
        return produtoRepository.findBySku(sku)
                .orElseThrow(() -> ApiException.notFound("Produto not found with SKU: " + sku));
    }

    @Transactional(readOnly = true)
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Produto> listarComPaginacao(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    @Transactional
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produto = buscarPorId(id);

        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPrecoVenda(produtoAtualizado.getPrecoVenda());
        produto.setCustoAquisicao(produtoAtualizado.getCustoAquisicao());

        return produtoRepository.save(produto);
    }

    @Transactional
    public void deletar(Long id) {
        Produto produto = buscarPorId(id);
        produto.delete();
        produtoRepository.save(produto);
    }
}
