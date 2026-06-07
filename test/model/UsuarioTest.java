package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import enums.PerfilUsuario;

public class UsuarioTest {

    @Test
    void deveAutenticarUsuarioCorretamente() {

        Usuario usuario = new Usuario(
                "admin",
                "123",
                PerfilUsuario.ADMIN
        );

        assertTrue(usuario.autenticar("admin", "123"));
    }

    @Test
    void deveFalharAutenticacao() {

        Usuario usuario = new Usuario(
                "admin",
                "123",
                PerfilUsuario.ADMIN
        );

        assertFalse(usuario.autenticar("admin", "errado"));
    }

    @Test
    void deveRetornarTrueQuandoForAdmin() {

        Usuario usuario = new Usuario(
                "admin",
                "123",
                PerfilUsuario.ADMIN
        );

        assertTrue(usuario.isAdmin());
    }
}