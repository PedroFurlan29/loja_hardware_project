package model;

 // Representa uma placa de vídeo (GPU) na loja de hardware.
 // Herda os atributos e comportamentos gerais de Produto.

public class PlacaDeVideo extends Produto {

    private int memoriaVRAM;
    private String chipset;

    public PlacaDeVideo(String sku, String nome, double precoVenda, int qtdEstoque, int qtdMinima,
                        int memoriaVRAM, String chipset) {
        super(sku, nome, precoVenda, qtdEstoque, qtdMinima);
        this.memoriaVRAM = memoriaVRAM;
        this.chipset = chipset;
    }

    @Override
    public String getDescricaoTecnica() {
        return String.format("GPU | Chipset: %s | VRAM: %d GB", chipset, memoriaVRAM);
    }

    @Override
    public String toString() {
        return super.toString() + " | " + getDescricaoTecnica();
    }

    public int getMemoriaVRAM() {
        return memoriaVRAM;
    }

    public void setMemoriaVRAM(int memoriaVRAM) {
        if (memoriaVRAM <= 0) {
            throw new IllegalArgumentException("Memória VRAM deve ser maior que zero.");
        }
        this.memoriaVRAM = memoriaVRAM;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }
}
