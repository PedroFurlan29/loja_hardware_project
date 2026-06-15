package com.lojahardware.unicep.usuarios.model;

public enum PerfilUsuario {
    ADMIN("Administrador"),
    VENDEDOR("Vendedor"),
    ESTOQUISTA("Estoquista");

    private final String descricao;

    PerfilUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
