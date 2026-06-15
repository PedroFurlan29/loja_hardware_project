package com.lojahardware.unicep.vendas.service;

import com.lojahardware.unicep.AbstractIntegrationTest;
import com.lojahardware.unicep.estoque.model.Estoque;
import com.lojahardware.unicep.estoque.repository.EstoqueRepository;
import com.lojahardware.unicep.estoque.service.EstoqueService;
import com.lojahardware.unicep.produtos.model.CPU;
import com.lojahardware.unicep.produtos.repository.ProdutoRepository;
import com.lojahardware.unicep.usuarios.model.PerfilUsuario;
import com.lojahardware.unicep.usuarios.model.Usuario;
import com.lojahardware.unicep.usuarios.repository.UsuarioRepository;
import com.lojahardware.unicep.vendas.model.StatusVenda;
import com.lojahardware.unicep.vendas.model.VendaRequestDTO;
import com.lojahardware.unicep.vendas.repository.VendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class VendaServiceTest extends AbstractIntegrationTest {

    @Autowired
    private VendaService vendaService;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private CPU cpu;
    private Estoque estoque;

    @BeforeEach
    public void setup() {
        usuario = new Usuario();
        usuario.setNome("Test User");
        usuario.setEmail("test@example.com");
        usuario.setSenha("encoded_password");
        usuario.setPerfil(PerfilUsuario.VENDEDOR);
        usuarioRepository.save(usuario);

        cpu = new CPU();
        cpu.setSku("CPU-001");
        cpu.setDescricao("Test CPU");
        cpu.setPrecoVenda(BigDecimal.valueOf(300));
        cpu.setCustoAquisicao(BigDecimal.valueOf(150));
        cpu.setMarca("Intel");
        cpu.setModelo("i7-12700K");
        cpu.setNucleos(12);
        cpu.setFrequenciaGHz(3.6);
        cpu.setArquitetura("x86");
        cpu.setSocket("LGA1700");
        produtoRepository.save(cpu);

        estoque = new Estoque();
        estoque.setProduto(cpu);
        estoque.setQuantidade(100);
        estoque.setEstoque_minimo(5);
        estoqueRepository.save(estoque);
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "VENDEDOR")
    public void testRegistrarVendaComSucesso() {
        VendaRequestDTO.ItemVendaRequestDTO item = new VendaRequestDTO.ItemVendaRequestDTO();
        item.setProdutoId(cpu.getId());
        item.setQuantidade(10);

        VendaRequestDTO vendaRequest = new VendaRequestDTO();
        vendaRequest.setItens(java.util.List.of(item));

        var venda = vendaService.registrarVenda(vendaRequest);

        assertNotNull(venda);
        assertEquals(StatusVenda.CONCLUIDA, venda.getStatus());
        assertEquals(BigDecimal.valueOf(3000), venda.getValorTotal());

        var estoqueAtualizado = estoqueRepository.findByProdutoId(cpu.getId()).get();
        assertEquals(90, estoqueAtualizado.getQuantidade());
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "VENDEDOR")
    public void testRegistrarVendaSemEstoque() {
        estoque.setQuantidade(5);
        estoqueRepository.save(estoque);

        VendaRequestDTO.ItemVendaRequestDTO item = new VendaRequestDTO.ItemVendaRequestDTO();
        item.setProdutoId(cpu.getId());
        item.setQuantidade(10);

        VendaRequestDTO vendaRequest = new VendaRequestDTO();
        vendaRequest.setItens(java.util.List.of(item));

        assertThrows(Exception.class, () -> vendaService.registrarVenda(vendaRequest));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = "VENDEDOR")
    public void testCancelarVenda() {
        VendaRequestDTO.ItemVendaRequestDTO item = new VendaRequestDTO.ItemVendaRequestDTO();
        item.setProdutoId(cpu.getId());
        item.setQuantidade(10);

        VendaRequestDTO vendaRequest = new VendaRequestDTO();
        vendaRequest.setItens(java.util.List.of(item));

        var venda = vendaService.registrarVenda(vendaRequest);

        vendaService.cancelarVenda(venda.getId(), "Produto defeituoso");

        var vendaCancelada = vendaRepository.findById(venda.getId()).get();
        assertEquals(StatusVenda.CANCELADA, vendaCancelada.getStatus());

        var estoqueAtualizado = estoqueRepository.findByProdutoId(cpu.getId()).get();
        assertEquals(100, estoqueAtualizado.getQuantidade());
    }
}
