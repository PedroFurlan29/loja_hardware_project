package com.lojahardware.unicep.usuarios.controller;

import com.lojahardware.unicep.shared.util.JwtUtil;
import com.lojahardware.unicep.usuarios.service.UsuarioService;
import com.lojahardware.unicep.usuarios.model.Usuario;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

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
    @GetMapping("/vendedores")
    public ResponseEntity<List<Map<String,Object>>> vendedores(){
        List<Map<String,Object>> lista = usuarioService.listarVendedores().stream()
            .map(v -> Map.<String,Object>of("id",v.getId(),"nome",v.getNome(),"email",v.getEmail()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody RegisterRequest req) {
        try {
            Usuario u = new Usuario();
            u.setNome(req.getNome());
            u.setEmail(req.getEmail());
            u.setSenha(req.getSenha());
            u.setPerfil(PerfilUsuario.CLIENTE);
            usuarioService.criar(u);
            return ResponseEntity.status(201).body(Map.of("message","Conta criada com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Data public static class LoginRequest { private String email; private String senha; }
    @Data public static class RegisterRequest { private String nome; private String email; private String senha; }
}
