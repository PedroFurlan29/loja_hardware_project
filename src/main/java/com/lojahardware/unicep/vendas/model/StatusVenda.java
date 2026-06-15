package com.lojahardware.unicep.vendas.model;

public enum StatusVenda {
    PENDENTE("Pendente"),
    CONCLUIDA("Concluída"),
    CANCELADA("Cancelada"),
    SOLICITACAO_CANCELAMENTO("Solicitação de cancelamento");

    private final String descricao;

    StatusVenda(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
