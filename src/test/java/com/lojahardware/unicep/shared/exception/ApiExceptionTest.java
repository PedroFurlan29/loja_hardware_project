package com.lojahardware.unicep.shared.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ApiException - Testes Unitários")
class ApiExceptionTest {

    @Test
    @DisplayName("badRequest() deve criar exceção com status 400 e a mensagem correta")
    void badRequest_DeveRetornarStatus400() {
        ApiException ex = ApiException.badRequest("Campo inválido");

        assertThat(ex.getStatusCode()).isEqualTo(400);
        assertThat(ex.getMessage()).isEqualTo("Campo inválido");
    }

    @Test
    @DisplayName("unauthorized() deve criar exceção com status 401")
    void unauthorized_DeveRetornarStatus401() {
        ApiException ex = ApiException.unauthorized("Não autorizado");

        assertThat(ex.getStatusCode()).isEqualTo(401);
        assertThat(ex.getMessage()).isEqualTo("Não autorizado");
    }

    @Test
    @DisplayName("forbidden() deve criar exceção com status 403")
    void forbidden_DeveRetornarStatus403() {
        ApiException ex = ApiException.forbidden("Acesso negado");

        assertThat(ex.getStatusCode()).isEqualTo(403);
        assertThat(ex.getMessage()).isEqualTo("Acesso negado");
    }

    @Test
    @DisplayName("notFound() deve criar exceção com status 404")
    void notFound_DeveRetornarStatus404() {
        ApiException ex = ApiException.notFound("Recurso não encontrado");

        assertThat(ex.getStatusCode()).isEqualTo(404);
        assertThat(ex.getMessage()).isEqualTo("Recurso não encontrado");
    }

    @Test
    @DisplayName("conflict() deve criar exceção com status 409")
    void conflict_DeveRetornarStatus409() {
        ApiException ex = ApiException.conflict("Recurso já existe");

        assertThat(ex.getStatusCode()).isEqualTo(409);
        assertThat(ex.getMessage()).isEqualTo("Recurso já existe");
    }

    @Test
    @DisplayName("internalServerError() deve criar exceção com status 500")
    void internalServerError_DeveRetornarStatus500() {
        ApiException ex = ApiException.internalServerError("Erro interno");

        assertThat(ex.getStatusCode()).isEqualTo(500);
        assertThat(ex.getMessage()).isEqualTo("Erro interno");
    }

    @Test
    @DisplayName("construtor com causa deve preservar a causa")
    void construtorComCausa_DevePreservarCausa() {
        RuntimeException cause = new RuntimeException("causa raiz");

        ApiException ex = new ApiException("Mensagem", cause, 500);

        assertThat(ex.getCause()).isSameAs(cause);
        assertThat(ex.getStatusCode()).isEqualTo(500);
        assertThat(ex.getMessage()).isEqualTo("Mensagem");
    }

    @Test
    @DisplayName("ApiException deve ser uma RuntimeException")
    void deveEstenderRuntimeException() {
        ApiException ex = ApiException.notFound("teste");
        assertThat(ex).isInstanceOf(RuntimeException.class);
    }
}