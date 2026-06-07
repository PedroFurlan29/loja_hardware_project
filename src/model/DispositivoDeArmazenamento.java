package model;

import enums.TipoArmazenamento;


 //Representa um dispositivo de armazenamento (HD, SSD, NVMe) na loja de hardware.
 //Herda os atributos e comportamentos gerais de Produto.

public class DispositivoDeArmazenamento extends Produto {

    private int capacidadeGB;
    private TipoArmazenamento tipo;

    public DispositivoDeArmazenamento(String sku, String nome, double precoVenda,
                                      int qtdEstoque, int qtdMinima,
                                      int capacidadeGB, TipoArmazenamento tipo) {
        super(sku, nome, precoVenda, qtdEstoque, qtdMinima);
        this.capacidadeGB = capacidadeGB;
        this.tipo = tipo;
    }

    @Override
    public String getDescricaoTecnica() {
        return String.format("Armazenamento | Tipo: %s | Capacidade: %d GB", tipo, capacidadeGB);
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

    public TipoArmazenamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoArmazenamento tipo) {
        this.tipo = tipo;
    }
}
