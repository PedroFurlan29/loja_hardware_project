package com.lojahardware.unicep.vendas.service;

import com.lojahardware.unicep.estoque.service.EstoqueService;
import com.lojahardware.unicep.produtos.model.Produto;
import com.lojahardware.unicep.produtos.repository.ProdutoRepository;
import com.lojahardware.unicep.usuarios.model.Usuario;
import com.lojahardware.unicep.usuarios.repository.UsuarioRepository;
import com.lojahardware.unicep.vendas.model.*;
import com.lojahardware.unicep.vendas.repository.VendaRepository;
import com.lojahardware.unicep.shared.exception.ApiException;
import com.lojahardware.unicep.vendas.controller.VendaController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @Slf4j
public class VendaService {
    private final VendaRepository repo;
    private final ProdutoRepository produtoRepo;
    private final UsuarioRepository usuarioRepo;
    private final EstoqueService estoqueService;
    public VendaService(VendaRepository r,ProdutoRepository p,UsuarioRepository u,EstoqueService e){ repo=r;produtoRepo=p;usuarioRepo=u;estoqueService=e; }
    @Transactional public Venda registrar(VendaController.VendaRequest req){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario user=usuarioRepo.findByEmail(email).orElseThrow(()->ApiException.notFound("User"));
        Venda v=new Venda(); v.setUsuario(user);
        for(var i:req.getItens()){
            Produto p=produtoRepo.findById(i.getProdutoId()).orElseThrow(()->ApiException.notFound("Produto"));
            estoqueService.baixar(p.getId(),i.getQuantidade());
            ItemVenda iv=new ItemVenda(); iv.setProduto(p); iv.setQuantidade(i.getQuantidade());
            iv.setPrecoUnitario(p.getPrecoVenda()); iv.setVenda(v); v.getItens().add(iv);
        }
        v.calcularTotal(); v.setStatus(StatusVenda.CONCLUIDA); Venda saved=repo.save(v); log.info("Venda {} registrada",saved.getId()); return saved;
    }
    @Transactional public void cancelar(Long vendaId,String motivo){
        Venda v=repo.findById(vendaId).orElseThrow(()->ApiException.notFound("Venda"));
        if(v.getStatus()==StatusVenda.CANCELADA) throw ApiException.badRequest("Already cancelled");
        for(ItemVenda i:v.getItens()) estoqueService.repor(i.getProduto().getId(),i.getQuantidade());
        v.setStatus(StatusVenda.CANCELADA); v.setMotivoCancelamento(motivo); repo.save(v);
    }
    public Venda buscarPorId(Long id){ return repo.findById(id).orElseThrow(()->ApiException.notFound("Venda")); }
    public List<Venda> listar(){ return repo.findAll(); }
}
