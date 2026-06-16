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

@Service @Slf4j
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    public ProdutoService(ProdutoRepository r){ this.produtoRepository=r; }
    @Transactional public Produto criar(Produto p){
        if(produtoRepository.findBySku(p.getSku()).isPresent())
            throw ApiException.conflict("Product with SKU "+p.getSku()+" already exists");
        return produtoRepository.save(p);
    }
    @Transactional(readOnly=true)
    public Produto buscarPorId(Long id){ return produtoRepository.findById(id).orElseThrow(()->ApiException.notFound("Produto not found: "+id)); }
    @Transactional(readOnly=true)
    public Produto buscarPorSku(String sku){ return produtoRepository.findBySku(sku).orElseThrow(()->ApiException.notFound("Produto not found: "+sku)); }
    @Transactional(readOnly=true) public List<Produto> listarTodos(){ return produtoRepository.findAll(); }
    @Transactional(readOnly=true) public Page<Produto> listarComPaginacao(Pageable pageable){ return produtoRepository.findAll(pageable); }
    @Transactional public Produto atualizar(Long id,Produto a){
        Produto p=buscarPorId(id);
        p.setDescricao(a.getDescricao()); p.setPrecoVenda(a.getPrecoVenda()); p.setCustoAquisicao(a.getCustoAquisicao());
        return produtoRepository.save(p);
    }
    @Transactional public void deletar(Long id){
        Produto p=buscarPorId(id);
        produtoRepository.delete(p);
    }
}
