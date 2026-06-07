package model;

 // Classe abstrata que representa um produto genérico da loja de hardware.
 // Todas as categorias de hardware herdam desta classe.

public abstract class Produto {

    private String sku;
    private String nome;
    private double precoVenda;
    private int qtdEstoque;
    private int qtdMinima;

    public Produto(String sku, String nome, double precoVenda, int qtdEstoque, int qtdMinima) {
        this.sku = sku;
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.qtdEstoque = qtdEstoque;
        this.qtdMinima = qtdMinima;
    }

    public boolean isEstoqueCritico() {
        return this.qtdEstoque <= this.qtdMinima;
    }

    public void atualizarEstoque(int qtd) {
        int novaQtd = this.qtdEstoque + qtd;
        if (novaQtd < 0) {
            throw new IllegalArgumentException(
                "Operação inválida: estoque insuficiente para o produto '" + this.nome + "'. " +
                "Disponível: " + this.qtdEstoque + ", solicitado: " + Math.abs(qtd)
            );
        }
        this.qtdEstoque = novaQtd;
    }

    public abstract String getDescricaoTecnica();

    @Override
    public String toString() {
        return String.format("[%s] %s - R$ %.2f | Estoque: %d%s",
                sku, nome, precoVenda, qtdEstoque,
                isEstoqueCritico() ? " !!! ESTOQUE CRÍTICO" : "");
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        if (precoVenda < 0) {
            throw new IllegalArgumentException("Preço de venda não pode ser negativo.");
        }
        this.precoVenda = precoVenda;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public int getQtdMinima() {
        return qtdMinima;
    }

    public void setQtdMinima(int qtdMinima) {
        this.qtdMinima = qtdMinima;
    }
}
