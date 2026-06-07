package model;

 // Representa um processador (CPU) na loja de hardware.
 // Herda os atributos e comportamentos gerais de Produto.

public class CPU extends Produto {

    private String arquitetura;
    private int nucleos;

    public CPU(String sku, String nome, double precoVenda, int qtdEstoque, int qtdMinima,
               String arquitetura, int nucleos) {
        super(sku, nome, precoVenda, qtdEstoque, qtdMinima);
        this.arquitetura = arquitetura;
        this.nucleos = nucleos;
    }

    @Override
    public String getDescricaoTecnica() {
        return String.format("CPU | Arquitetura: %s | Núcleos: %d", arquitetura, nucleos);
    }

    @Override
    public String toString() {
        return super.toString() + " | " + getDescricaoTecnica();
    }


    public String getArquitetura() {
        return arquitetura;
    }

    public void setArquitetura(String arquitetura) {
        this.arquitetura = arquitetura;
    }

    public int getNucleos() {
        return nucleos;
    }

    public void setNucleos(int nucleos) {
        if (nucleos <= 0) {
            throw new IllegalArgumentException("Número de núcleos deve ser maior que zero.");
        }
        this.nucleos = nucleos;
    }
}
