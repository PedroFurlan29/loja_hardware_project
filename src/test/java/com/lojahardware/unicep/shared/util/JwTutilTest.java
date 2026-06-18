package com.lojahardware.unicep.shared.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JwtUtil - Testes Unitários")
class JwtUtilTest {

    // Mínimo 32 bytes para HS256
    private static final String VALID_SECRET = "test-secret-key-for-ci-unit-tests-minimum-32-bytes";
    private static final long EXPIRATION_MS = 86_400_000L; // 24h

    private JwtUtil jwtUtil;
    private UserDetails adminDetails;
    private UserDetails vendedorDetails;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", VALID_SECRET);
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", EXPIRATION_MS);

        adminDetails = User.builder()
                .username("admin@loja.com")
                .password("encoded_password")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .build();

        vendedorDetails = User.builder()
                .username("vendedor@loja.com")
                .password("encoded_pass")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_VENDEDOR")))
                .build();
    }

    @Test
    @DisplayName("generateToken() deve gerar um token não nulo e não vazio")
    void generateToken_DeveRetornarTokenNaoNulo() {
        String token = jwtUtil.generateToken(adminDetails);

        assertThat(token).isNotNull().isNotBlank();
    }

    @Test
    @DisplayName("generateToken() deve gerar tokens distintos para usuários diferentes")
    void generateToken_DeveGerarTokenDistintosParaUsuariosDiferentes() {
        String tokenAdmin = jwtUtil.generateToken(adminDetails);
        String tokenVendedor = jwtUtil.generateToken(vendedorDetails);

        assertThat(tokenAdmin).isNotEqualTo(tokenVendedor);
    }

    @Test
    @DisplayName("getEmailFromToken() deve extrair o email do subject do token")
    void getEmailFromToken_DeveRetornarEmailCorreto() {
        String token = jwtUtil.generateToken(adminDetails);

        String email = jwtUtil.getEmailFromToken(token);

        assertThat(email).isEqualTo("admin@loja.com");
    }

    @Test
    @DisplayName("getEmailFromToken() deve extrair email do vendedor corretamente")
    void getEmailFromToken_DeveRetornarEmailDoVendedor() {
        String token = jwtUtil.generateToken(vendedorDetails);

        assertThat(jwtUtil.getEmailFromToken(token)).isEqualTo("vendedor@loja.com");
    }

    @Test
    @DisplayName("getRolesFromToken() deve retornar as roles do usuário")
    void getRolesFromToken_DeveRetornarRolesCorretas() {
        String token = jwtUtil.generateToken(adminDetails);

        List<String> roles = jwtUtil.getRolesFromToken(token);

        assertThat(roles).containsExactly("ROLE_ADMIN");
    }

    @Test
    @DisplayName("getRolesFromToken() deve retornar role de vendedor corretamente")
    void getRolesFromToken_DeveRetornarRoleVendedor() {
        String token = jwtUtil.generateToken(vendedorDetails);

        List<String> roles = jwtUtil.getRolesFromToken(token);

        assertThat(roles).containsExactly("ROLE_VENDEDOR");
    }

    @Test
    @DisplayName("validateToken() deve retornar true para token válido")
    void validateToken_DeveRetornarTrue_TokenValido() {
        String token = jwtUtil.generateToken(adminDetails);

        assertThat(jwtUtil.validateToken(token)).isTrue();
    }

    @Test
    @DisplayName("validateToken() deve retornar false para token malformado")
    void validateToken_DeveRetornarFalse_TokenMalformado() {
        assertThat(jwtUtil.validateToken("token.invalido.aqui")).isFalse();
    }

    @Test
    @DisplayName("validateToken() deve retornar false para token vazio")
    void validateToken_DeveRetornarFalse_TokenVazio() {
        assertThat(jwtUtil.validateToken("")).isFalse();
    }

    @Test
    @DisplayName("validateToken() deve retornar false para string aleatória")
    void validateToken_DeveRetornarFalse_StringAleatoria() {
        assertThat(jwtUtil.validateToken("abc123xyz")).isFalse();
    }

    @Test
    @DisplayName("isTokenExpired() deve retornar false para token recém gerado")
    void isTokenExpired_DeveRetornarFalse_TokenRecente() {
        String token = jwtUtil.generateToken(adminDetails);

        assertThat(jwtUtil.isTokenExpired(token)).isFalse();
    }

    @Test
    @DisplayName("isTokenExpired() deve retornar true para token expirado")
    void isTokenExpired_DeveRetornarTrue_TokenExpirado() throws InterruptedException {
        JwtUtil shortLivedUtil = new JwtUtil();
        ReflectionTestUtils.setField(shortLivedUtil, "jwtSecret", VALID_SECRET);
        ReflectionTestUtils.setField(shortLivedUtil, "jwtExpirationMs", 1L); // expira em 1ms

        String token = shortLivedUtil.generateToken(adminDetails);
        Thread.sleep(50);

        assertThat(shortLivedUtil.isTokenExpired(token)).isTrue();
    }

    @Test
    @DisplayName("isTokenExpired() deve retornar true para token inválido")
    void isTokenExpired_DeveRetornarTrue_TokenInvalido() {
        assertThat(jwtUtil.isTokenExpired("token.invalido")).isTrue();
    }

    @Test
    @DisplayName("token gerado deve ter formato JWT com 3 partes separadas por ponto")
    void tokenGerado_DeveTerFormatoJwt() {
        String token = jwtUtil.generateToken(adminDetails);
        String[] parts = token.split("\\.");

        assertThat(parts).hasSize(3);
    }
}