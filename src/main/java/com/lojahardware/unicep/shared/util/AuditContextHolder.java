package com.lojahardware.unicep.shared.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuditContextHolder {

    private static final ThreadLocal<String> usuarioId = new ThreadLocal<>();
    private static final ThreadLocal<String> acao = new ThreadLocal<>();
    private static final ThreadLocal<String> recursoId = new ThreadLocal<>();

    public static void registrarAcao(Long usuarioId, String acao, Long recursoId) {
        AuditContextHolder.usuarioId.set(usuarioId.toString());
        AuditContextHolder.acao.set(acao);
        AuditContextHolder.recursoId.set(recursoId.toString());

        log.info("AUDITORIA - Usuário: {}, Ação: {}, Recurso: {}", usuarioId, acao, recursoId);
    }

    public static String getUsuarioId() {
        return usuarioId.get();
    }

    public static String getAcao() {
        return acao.get();
    }

    public static String getRecursoId() {
        return recursoId.get();
    }

    public static void clear() {
        usuarioId.remove();
        acao.remove();
        recursoId.remove();
    }
}
