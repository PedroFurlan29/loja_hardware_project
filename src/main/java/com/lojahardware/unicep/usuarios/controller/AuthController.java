package com.lojahardware.unicep.usuarios.controller;

import com.lojahardware.unicep.usuarios.model.LoginRequestDTO;
import com.lojahardware.unicep.usuarios.model.UsuarioDTO;
import com.lojahardware.unicep.usuarios.service.AutenticacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
@Tag(name = "Authentication", description = "Endpoints para autenticação")
public class AuthController {

    private final AutenticacaoService autenticacaoService;

    public AuthController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica usuário e retorna JWT")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        String token = autenticacaoService.autenticar(loginRequest);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("type", "Bearer");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/registrar")
    @Operation(summary = "Registrar usuário", description = "Cria um novo usuário no sistema")
    public ResponseEntity<UsuarioDTO> registrar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioCriado = autenticacaoService.registrar(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }
}
