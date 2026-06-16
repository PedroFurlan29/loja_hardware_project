package com.lojahardware.unicep.usuarios.service;

import com.lojahardware.unicep.usuarios.model.Usuario;
import com.lojahardware.unicep.usuarios.repository.UsuarioRepository;
import com.lojahardware.unicep.shared.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @Slf4j
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    public UsuarioService(UsuarioRepository r,@Lazy PasswordEncoder p){ usuarioRepository=r;passwordEncoder=p; }
    @Override @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario u=usuarioRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Usuario not found: "+email));
        return User.builder().username(u.getEmail()).password(u.getSenha())
            .authorities(List.of(new SimpleGrantedAuthority(u.getRole())))
            .accountExpired(false).accountLocked(false).credentialsExpired(false).disabled(false).build();
    }
    @Transactional public Usuario criar(Usuario u){
        if(usuarioRepository.findByEmail(u.getEmail()).isPresent()) throw ApiException.conflict("Email already registered");
        u.setSenha(passwordEncoder.encode(u.getSenha()));
        return usuarioRepository.save(u);
    }
    @Transactional(readOnly=true)
    public Usuario buscarPorId(Long id){ return usuarioRepository.findById(id).orElseThrow(()->ApiException.notFound("Usuario not found: "+id)); }
    @Transactional(readOnly=true)
    public Usuario buscarPorEmail(String email){ return usuarioRepository.findByEmail(email).orElseThrow(()->ApiException.notFound("Usuario not found: "+email)); }
}
