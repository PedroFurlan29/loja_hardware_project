package com.lojahardware.unicep.vendas.service;

import com.lojahardware.unicep.estoque.service.EstoqueService;
import com.lojahardware.unicep.produtos.model.Produto;
import com.lojahardware.unicep.produtos.repository.ProdutoRepository;
import com.lojahardware.unicep.usuarios.model.Usuario;
import com.lojahardware.unicep.usuarios.repository.UsuarioRepository;
import com.lojahardware.unicep.vendas.model.ItemVenda;
import com.lojahardware.unicep.vendas.model.StatusVenda;
import com.lojahardware.unicep.vendas.model.Venda;
import com.lojahardware.unicep.vendas.model.VendaRequestDTO;
import com.lojahardware.unicep.vendas.repository.VendaRepository;
import com.lojahardware.unicep.shared.exception.ApiException;
import com.lojahardware.unicep.shared.util.AuditContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstoqueService estoqueService;

    public VendaService(VendaRepository vendaRepository, ProdutoRepository produtoRepository,
                       UsuarioRepository usuarioRepository, EstoqueService estoqueService) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
        this.estoqueService = estoqueService;
    }

    @Transactional
    public Venda registrarVenda(VendaRequestDTO vendaRequest) {
        String emailDoContexto = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(emailDoContexto)
                .orElseThrow(() -> ApiException.unauthorized("User not found"));

        Venda venda = new Venda();
        venda.setUsuario(usuario);
        venda.setStatus(StatusVenda.PENDENTE);

        for (var itemRequest : vendaRequest.getItens()) {
            Produto produto = produtoRepository.findById(itemRequest.getProdutoId())
                    .orElseThrow(() -> ApiException.notFound("Produto not found"));

            try {
                estoqueService.buscarPorProdutoId(produto.getId());
            } catch (ApiException e) {
                throw ApiException.badRequest("No stock available for product: " + produto.getSku());
            }

            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(itemRequest.getQuantidade());
            item.setPrecoUnitario(produto.getPrecoVenda());

            venda.adicionarItem(item);
        }

        venda.calcularTotal();

        try {
            for (ItemVenda item : venda.getItens()) {
                estoqueService.baixar(item.getProduto().getId(), item.getQuantidade());
            }
        } catch (ApiException e) {
            throw ApiException.badRequest("Insufficient stock: " + e.getMessage());
        }

        venda.setStatus(StatusVenda.CONCLUIDA);
        Venda vendaSalva = vendaRepository.save(venda);

        AuditContextHolder.registrarAcao(usuario.getId(), "Venda registrada", vendaSalva.getId());
        log.info("Venda {} registrada pelo usuário {}", vendaSalva.getId(), usuario.getEmail());

        return vendaSalva;
    }

    @Transactional
    public void cancelarVenda(Long vendaId, String motivo) {
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> ApiException.notFound("Venda not found"));

        if (venda.getStatus() == StatusVenda.CANCELADA) {
            throw ApiException.badRequest("Venda already cancelled");
        }

        for (ItemVenda item : venda.getItens()) {
            estoqueService.repor(item.getProduto().getId(), item.getQuantidade());
        }

        venda.setStatus(StatusVenda.CANCELADA);
        venda.setMotivoCancelamento(motivo);
        vendaRepository.save(venda);

        String emailDoContexto = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario admin = usuarioRepository.findByEmail(emailDoContexto).orElse(null);
        if (admin != null) {
            AuditContextHolder.registrarAcao(admin.getId(), "Venda cancelada", venda.getId());
        }

        log.info("Venda {} cancelada com motivo: {}", vendaId, motivo);
    }

    @Transactional(readOnly = true)
    public Venda buscarPorId(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> ApiException.notFound("Venda not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Venda> listarPorStatus(StatusVenda status) {
        return vendaRepository.findByStatus(status);
    }
}
