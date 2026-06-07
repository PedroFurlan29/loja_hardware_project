package model;

 // Representa um item (linha) dentro de uma venda.
 // Associa um produto e seu fornecedor a uma quantidade e preço unitário no momento da venda.

public class ItemVenda {

    private int quantidade;
    private double precoUnitario;
    private Produto produto;
    private Fornecedor fornecedor;

    public ItemVenda(Produto produto, int quantidade, Fornecedor fornecedor) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade do item deve ser maior que zero.");
        }
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPrecoVenda(); // captura o preço no momento da venda
        this.fornecedor = fornecedor;
    }

    public double getSubtotal() {
        return quantidade * precoUnitario;
    }

    @Override
    public String toString() {
        return String.format("  - %s | Qtd: %d | Preço Unit.: R$ %.2f | Subtotal: R$ %.2f",
                produto.getNome(), quantidade, precoUnitario, getSubtotal());
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public Produto getProduto() {
        return produto;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
}
