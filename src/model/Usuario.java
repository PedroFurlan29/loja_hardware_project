package model;

import enums.PerfilUsuario;

 // Representa um usuário do sistema (Vendedor ou Administrador).
 // Responsável pela autenticação e controle de perfil de acesso.

public class Usuario {

    private String login;
    private String senha;
    private PerfilUsuario perfil;

    public Usuario(String login, String senha, PerfilUsuario perfil) {
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    public boolean autenticar(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }

    public boolean isAdmin() {
        return this.perfil == PerfilUsuario.ADMIN;
    }

    @Override
    public String toString() {
        return String.format("Usuário: %s | Perfil: %s", login, perfil);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUsuario perfil) {
        this.perfil = perfil;
    }
}
