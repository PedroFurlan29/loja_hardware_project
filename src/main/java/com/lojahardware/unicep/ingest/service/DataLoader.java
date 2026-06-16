package com.lojahardware.unicep.ingest.service;

import com.lojahardware.unicep.produtos.model.CPU;
import com.lojahardware.unicep.produtos.repository.ProdutoRepository;
import com.lojahardware.unicep.estoque.service.EstoqueService;
import com.lojahardware.unicep.usuarios.model.PerfilUsuario;
import com.lojahardware.unicep.usuarios.model.Usuario;
import com.lojahardware.unicep.usuarios.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component @Slf4j @Profile("!test")
public class DataLoader implements CommandLineRunner {
    private final UsuarioService usuarioService;
    private final ProdutoRepository produtoRepo;
    private final EstoqueService estoqueService;
    public DataLoader(UsuarioService u,ProdutoRepository p,EstoqueService e){ usuarioService=u;produtoRepo=p;estoqueService=e; }
    @Override public void run(String... args){
        log.info("Seed inicial...");
        try{
            usuarioService.criar(createUser("Admin","admin@loja.com","admin123",PerfilUsuario.ADMIN));
            usuarioService.criar(createUser("Vendedor","vendedor@loja.com","vendedor123",PerfilUsuario.VENDEDOR));
            usuarioService.criar(createUser("Estoquista","estoquista@loja.com","estoquista123",PerfilUsuario.ESTOQUISTA));
            CPU cpu=new CPU(); cpu.setSku("CPU-001"); cpu.setDescricao("Intel Core i7-12700K");
            cpu.setPrecoVenda(BigDecimal.valueOf(500)); cpu.setCustoAquisicao(BigDecimal.valueOf(300));
            cpu.setQuantidade(20); cpu.setEstoque_minimo(5);
            cpu.setMarca("Intel"); cpu.setModelo("i7-12700K");
            cpu.setNucleos(12); cpu.setFrequenciaGHz(3.6); cpu.setArquitetura("x86"); cpu.setSocket("LGA1700");
            var saved=produtoRepo.save(cpu);
            estoqueService.criar(saved.getId(),20,5);
            log.info("Seed concluida");
        }catch(Exception e){ log.warn("Seed ja existe: {}",e.getMessage()); }
    }
    private Usuario createUser(String nome,String email,String senha,PerfilUsuario perfil){
        Usuario u=new Usuario(); u.setNome(nome); u.setEmail(email); u.setSenha(senha); u.setPerfil(perfil); return u;
    }
}
