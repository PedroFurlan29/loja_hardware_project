package model;

import enums.StatusVenda;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

 // Representa uma venda realizada na loja.
 // Gerencia os itens vendidos, o status da transação e a baixa no estoque.

public class Venda {

    private static int contadorId = 1;

    private int id;
    private LocalDateTime dataHora;
    private StatusVenda status;
    private Usuario usuario;
    private List<ItemVenda> itens;

    public Venda(Usuario usuario) {
        this.id = contadorId++;
        this.dataHora = LocalDateTime.now();
        this.status = StatusVenda.ABERTA;
        this.usuario = usuario;
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(ItemVenda item) {
        if (this.status != StatusVenda.ABERTA) {
            throw new IllegalStateException("Não é possível adicionar itens a uma venda " + status + ".");
        }
        Produto produto = item.getProduto();
        if (item.getQuantidade() > produto.getQtdEstoque()) {
            throw new IllegalArgumentException(
                "Estoque insuficiente para '" + produto.getNome() + "'. " +
                "Disponível: " + produto.getQtdEstoque() + ", solicitado: " + item.getQuantidade()
            );
        }
        this.itens.add(item);
    }

    public double getTotalVenda() {
        double total = 0;
        for (ItemVenda item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void finalizarVenda() {
        if (this.status != StatusVenda.ABERTA) {
            throw new IllegalStateException("Só é possível finalizar uma venda com status ABERTA.");
        }
        if (this.itens.isEmpty()) {
            throw new IllegalStateException("Não é possível finalizar uma venda sem itens.");
        }
        // Baixa no estoque de cada item
        for (ItemVenda item : itens) {
            item.getProduto().atualizarEstoque(-item.getQuantidade());
        }
        this.status = StatusVenda.CONCLUIDA;
    }

    public void solicitarCancelamento() {
        if (this.status == StatusVenda.CANCELADA) {
            throw new IllegalStateException("A venda já está cancelada.");
        }
        this.status = StatusVenda.CANCELADA;
    }

    public void aprovarCancelamento(Usuario admin) {
        if (!admin.isAdmin()) {
            throw new IllegalArgumentException("Somente administradores podem aprovar cancelamentos.");
        }
        if (this.status != StatusVenda.CANCELADA) {
            throw new IllegalStateException("A venda precisa estar com status CANCELADA para ser aprovada.");
        }
        // Restituição do estoque
        for (ItemVenda item : itens) {
            item.getProduto().atualizarEstoque(item.getQuantidade());
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== Venda #%d | %s | Status: %s | Usuário: %s ===\n",
                id, dataHora.format(fmt), status, usuario.getLogin()));
        for (ItemVenda item : itens) {
            sb.append(item.toString()).append("\n");
        }
        sb.append(String.format("TOTAL: R$ %.2f", getTotalVenda()));
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public StatusVenda getStatus() {
        return status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<ItemVenda> getItens() {
        return Collections.unmodifiableList(itens);
    }
}
