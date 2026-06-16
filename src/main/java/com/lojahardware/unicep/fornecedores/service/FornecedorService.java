package com.lojahardware.unicep.fornecedores.service;

import com.lojahardware.unicep.fornecedores.model.Fornecedor;
import com.lojahardware.unicep.fornecedores.repository.FornecedorRepository;
import com.lojahardware.unicep.shared.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FornecedorService {
    private final FornecedorRepository repo;
    public FornecedorService(FornecedorRepository r) { repo = r; }

    @Transactional
    public Fornecedor criar(Fornecedor f) {
        if (repo.findByCnpj(f.getCnpj()).isPresent())
            throw ApiException.conflict("CNPJ já cadastrado");
        return repo.save(f);
    }

    public List<Fornecedor> listar() { return repo.findAll(); }

    public Fornecedor buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> ApiException.notFound("Fornecedor"));
    }

    @Transactional
    public Fornecedor atualizar(Long id, Fornecedor dados) {
        Fornecedor f = buscarPorId(id);
        f.setNome(dados.getNome());
        f.setEmail(dados.getEmail());
        f.setTelefone(dados.getTelefone());
        f.setEndereco(dados.getEndereco());
        return repo.save(f);
    }

    @Transactional
    public void deletar(Long id) {
        buscarPorId(id);
        repo.deleteById(id);
    }
}
