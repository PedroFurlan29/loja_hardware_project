package com.lojahardware.unicep.usuarios.controller;

import com.lojahardware.unicep.shared.util.JwtUtil;
import com.lojahardware.unicep.usuarios.service.UsuarioService;
import com.lojahardware.unicep.usuarios.model.Usuario;
import com.lojahardware.unicep.usuarios.model.PerfilUsuario;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/auth") @Slf4j
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;
    public AuthController(AuthenticationManager a,JwtUtil j,UsuarioService u){ authManager=a;jwtUtil=j;usuarioService=u; }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req){
        try{
            var auth=authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(),req.getSenha()));
            UserDetails userDetails=(UserDetails)auth.getPrincipal();
            String token=jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(Map.of("token",token,"type","Bearer"));
        }catch(Exception e){
            return ResponseEntity.status(401).body(Map.of("error","Invalid credentials"));
        }
    }
    @GetMapping("/me")
    public ResponseEntity<?> me(){
        try{
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            Usuario u=usuarioService.buscarPorEmail(email);
            return ResponseEntity.ok(Map.of(
                "id",u.getId(),
                "nome",u.getNome(),
                "email",u.getEmail(),
                "perfil",u.getPerfil().name()
            ));
        }catch(Exception e){
            return ResponseEntity.status(401).body(Map.of("error","Not authenticated"));
        }
    }
    @Data public static class LoginRequest { private String email; private String senha; }
}
