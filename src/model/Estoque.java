package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

 // Representa o estoque da loja.
 // Centraliza o catálogo de produtos e oferece operações de consulta e reposição.

public class Estoque {

    private LocalDate dataUltimaAtualizacao;
    private List<Produto> produtos;

    public Estoque() {
        this.produtos = new ArrayList<>();
        this.dataUltimaAtualizacao = LocalDate.now();
    }

    public void adicionarProduto(Produto produto) {
        if (buscarPorSku(produto.getSku()) != null) {
            throw new IllegalArgumentException(
                "Já existe um produto cadastrado com o SKU: " + produto.getSku()
            );
        }
        this.produtos.add(produto);
        this.dataUltimaAtualizacao = LocalDate.now();
    }

    public boolean removerProduto(String sku) {
        Produto produto = buscarPorSku(sku);
        if (produto != null) {
            produtos.remove(produto);
            this.dataUltimaAtualizacao = LocalDate.now();
            return true;
        }
        return false;
    }

    public void registrarEntrada(String sku, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade de entrada deve ser maior que zero.");
        }
        Produto produto = buscarPorSku(sku);
        if (produto == null) {
            throw new IllegalArgumentException("Produto com SKU '" + sku + "' não encontrado.");
        }
        produto.atualizarEstoque(quantidade);
        this.dataUltimaAtualizacao = LocalDate.now();
    }

    public Produto buscarPorSku(String sku) {
        for (Produto p : produtos) {
            if (p.getSku().equalsIgnoreCase(sku)) {
                return p;
            }
        }
        return null;
    }

    public List<Produto> listarCriticos() {
        List<Produto> criticos = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.isEstoqueCritico()) {
                criticos.add(p);
            }
        }
        return criticos;
    }

    public String gerarRelatorio() {
        if (produtos.isEmpty()) {
            return "Estoque vazio.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO DE ESTOQUE | Atualizado em: ")
          .append(dataUltimaAtualizacao).append(" ===\n");
        for (Produto p : produtos) {
            sb.append(p.toString()).append("\n");
        }
        List<Produto> criticos = listarCriticos();
        if (!criticos.isEmpty()) {
            sb.append("\n⚠ PRODUTOS COM ESTOQUE CRÍTICO:\n");
            for (Produto p : criticos) {
                sb.append("  - ").append(p.getNome())
                  .append(" | SKU: ").append(p.getSku())
                  .append(" | Qtd: ").append(p.getQtdEstoque()).append("\n");
            }
        }
        return sb.toString();
    }

    public LocalDate getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public List<Produto> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }
}
