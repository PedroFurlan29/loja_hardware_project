package com.lojahardware.unicep.config;

import com.lojahardware.unicep.shared.security.JwtAuthenticationEntryPoint;
import com.lojahardware.unicep.shared.security.JwtAuthenticationFilter;
import com.lojahardware.unicep.usuarios.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.Arrays;

@Configuration @EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint entryPoint;
    private final UsuarioService usuarioService;
    private final JwtAuthenticationFilter filter;
    public SecurityConfig(JwtAuthenticationEntryPoint e,UsuarioService u,JwtAuthenticationFilter f){ entryPoint=e;usuarioService=u;filter=f; }
    @Bean public PasswordEncoder passwordEncoder(){ return NoOpPasswordEncoder.getInstance(); }
    @Bean public AuthenticationManager authenticationManager(AuthenticationConfiguration c) throws Exception { return c.getAuthenticationManager(); }
    @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(co->co.configurationSource(r->{ var cfg=new CorsConfiguration(); cfg.setAllowedOrigins(Arrays.asList("*")); cfg.setAllowedMethods(Arrays.asList("*")); cfg.setAllowedHeaders(Arrays.asList("*")); return cfg; }))
            .csrf(c->c.disable())
            .exceptionHandling(e->e.authenticationEntryPoint(entryPoint))
            .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(a->a.requestMatchers("/auth/**","/h2-console/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated());
        http.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
