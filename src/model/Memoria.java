package model;

import enums.TipoMemoria;

 // Representa um módulo de memória RAM na loja de hardware.
 // Herda os atributos e comportamentos gerais de Produto.

public class Memoria extends Produto {

    private int capacidadeGB;
    private TipoMemoria tipo;

    public Memoria(String sku, String nome, double precoVenda, int qtdEstoque, int qtdMinima,
                   int capacidadeGB, TipoMemoria tipo) {
        super(sku, nome, precoVenda, qtdEstoque, qtdMinima);
        this.capacidadeGB = capacidadeGB;
        this.tipo = tipo;
    }

    @Override
    public String getDescricaoTecnica() {
        return String.format("Memória RAM | Tipo: %s | Capacidade: %d GB", tipo, capacidadeGB);
    }

    @Override
    public String toString() {
        return super.toString() + " | " + getDescricaoTecnica();
    }

    public int getCapacidadeGB() {
        return capacidadeGB;
    }

    public void setCapacidadeGB(int capacidadeGB) {
        if (capacidadeGB <= 0) {
            throw new IllegalArgumentException("Capacidade deve ser maior que zero.");
        }
        this.capacidadeGB = capacidadeGB;
    }

    public TipoMemoria getTipo() {
        return tipo;
    }

    public void setTipo(TipoMemoria tipo) {
        this.tipo = tipo;
    }
}
