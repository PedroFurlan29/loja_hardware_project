package com.lojahardware.unicep.vendas.repository;

import com.lojahardware.unicep.vendas.model.StatusVenda;
import com.lojahardware.unicep.vendas.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByStatus(StatusVenda status);
}
