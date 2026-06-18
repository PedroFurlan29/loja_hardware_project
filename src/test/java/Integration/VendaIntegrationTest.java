package Integration;

import com.lojahardware.unicep.produtos.model.CPU;
import com.lojahardware.unicep.produtos.repository.ProdutoRepository;
import com.lojahardware.unicep.usuarios.model.PerfilUsuario;
import com.lojahardware.unicep.usuarios.model.Usuario;
import com.lojahardware.unicep.usuarios.repository.UsuarioRepository;
import com.lojahardware.unicep.vendas.model.ItemVenda;
import com.lojahardware.unicep.vendas.model.StatusVenda;
import com.lojahardware.unicep.vendas.model.Venda;
import com.lojahardware.unicep.vendas.repository.VendaRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class VendaIntegrationTest {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve persistir uma venda completa com item e calcular valor total")
    void devePersistirVendaCompleta() {

        Usuario vendedor = new Usuario();
        vendedor.setNome("Pedro");
        vendedor.setEmail("pedro@teste.com");
        vendedor.setSenha("123456");
        vendedor.setPerfil(PerfilUsuario.ADMIN);

        vendedor = usuarioRepository.save(vendedor);

        CPU cpu = new CPU();

        cpu.setSku("CPU-001");
        cpu.setDescricao("Ryzen 7 5700X");

        cpu.setPrecoVenda(new BigDecimal("1200.00"));
        cpu.setCustoAquisicao(new BigDecimal("900.00"));

        cpu.setQuantidade(10);
        cpu.setEstoque_minimo(2);

        cpu.setMarca("AMD");
        cpu.setModelo("5700X");
        cpu.setNucleos(8);
        cpu.setFrequenciaGHz(3.4);
        cpu.setArquitetura("Zen 3");
        cpu.setSocket("AM4");

        cpu = produtoRepository.save(cpu);

        Venda venda = new Venda();

        venda.setUsuario(vendedor);
        venda.setStatus(StatusVenda.CONCLUIDA);

        ItemVenda item = new ItemVenda();

        item.setProduto(cpu);
        item.setQuantidade(2);
        item.setPrecoUnitario(cpu.getPrecoVenda());

        venda.adicionarItem(item);

        venda.calcularTotal();

        Venda vendaSalva = vendaRepository.save(venda);

        assertNotNull(vendaSalva.getId());

        assertEquals(
                new BigDecimal("2400.00"),
                vendaSalva.getValorTotal()
        );

        assertEquals(
                1,
                vendaSalva.getItens().size()
        );

        assertEquals(
                StatusVenda.CONCLUIDA,
                vendaSalva.getStatus()
        );

        assertEquals(
                vendedor.getId(),
                vendaSalva.getUsuario().getId()
        );

        assertEquals(
                cpu.getId(),
                vendaSalva.getItens().get(0).getProduto().getId()
        );
    }
}