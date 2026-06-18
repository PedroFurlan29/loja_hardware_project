package com.lojahardware.unicep.shared.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GlobalExceptionHandler - Testes Unitários")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private MockHttpServletRequest request;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
    }

    @Test
    @DisplayName("handleApiException() deve retornar status HTTP correto para NOT FOUND")
    void handleApiException_DeveMappearStatusNotFound() {
        ApiException ex = ApiException.notFound("Produto não encontrado");

        ResponseEntity<ErrorResponse> response = handler.handleApiException(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(404);
        assertThat(response.getBody().getMessage()).isEqualTo("Produto não encontrado");
        assertThat(response.getBody().getPath()).isEqualTo("/api/test");
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }

    @Test
    @DisplayName("handleApiException() deve retornar status HTTP correto para CONFLICT")
    void handleApiException_DeveMappearStatusConflict() {
        ApiException ex = ApiException.conflict("SKU já cadastrado");

        ResponseEntity<ErrorResponse> response = handler.handleApiException(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().getStatus()).isEqualTo(409);
        assertThat(response.getBody().getMessage()).isEqualTo("SKU já cadastrado");
    }

    @Test
    @DisplayName("handleApiException() deve retornar status HTTP correto para BAD REQUEST")
    void handleApiException_DeveMappearStatusBadRequest() {
        ApiException ex = ApiException.badRequest("Estoque insuficiente");

        ResponseEntity<ErrorResponse> response = handler.handleApiException(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getStatus()).isEqualTo(400);
    }

    @Test
    @DisplayName("handleApiException() deve retornar INTERNAL_SERVER_ERROR para status HTTP desconhecido")
    void handleApiException_DeveUsarInternalServerError_QuandoStatusDesconhecido() {
        ApiException ex = new ApiException("Erro customizado", 999);

        ResponseEntity<ErrorResponse> response = handler.handleApiException(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("handleGlobalException() deve retornar status 500 com mensagem de erro interno")
    void handleGlobalException_DeveRetornarStatus500() {
        Exception ex = new RuntimeException("NullPointerException inesperada");

        ResponseEntity<ErrorResponse> response = handler.handleGlobalException(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(500);
        assertThat(response.getBody().getMessage()).contains("Internal server error");
        assertThat(response.getBody().getPath()).isEqualTo("/api/test");
    }

    @Test
    @DisplayName("handleApiException() deve incluir o path do request na resposta")
    void handleApiException_DeveIncluirPath() {
        request.setRequestURI("/api/produtos/999");
        ApiException ex = ApiException.notFound("Produto 999 não encontrado");

        ResponseEntity<ErrorResponse> response = handler.handleApiException(ex, request);

        assertThat(response.getBody().getPath()).isEqualTo("/api/produtos/999");
    }
}