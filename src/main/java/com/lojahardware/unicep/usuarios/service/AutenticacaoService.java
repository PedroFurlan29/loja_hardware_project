package com.lojahardware.unicep.usuarios.service;

import com.lojahardware.unicep.shared.exception.ApiException;
import com.lojahardware.unicep.shared.util.JwtUtil;
import com.lojahardware.unicep.usuarios.model.LoginRequestDTO;
import com.lojahardware.unicep.usuarios.model.UsuarioDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AutenticacaoService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    public AutenticacaoService(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                              UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    public String autenticar(LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getSenha()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtUtil.generateToken(userDetails);
        } catch (Exception e) {
            log.error("Authentication failed for email: {}", loginRequest.getEmail());
            throw ApiException.unauthorized("Invalid credentials");
        }
    }

    public UsuarioDTO registrar(UsuarioDTO usuarioDTO) {
        var usuario = new com.lojahardware.unicep.usuarios.model.Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setPerfil(usuarioDTO.getPerfil());

        var usuarioCriado = usuarioService.criar(usuario);
        return UsuarioDTO.fromEntity(usuarioCriado);
    }
}
