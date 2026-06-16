package com.lojahardware.unicep.usuarios.controller;

import com.lojahardware.unicep.shared.util.JwtUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/auth") @Slf4j
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    public AuthController(AuthenticationManager a,JwtUtil j){ authManager=a;jwtUtil=j; }
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
    @Data public static class LoginRequest { private String email; private String senha; }
}
