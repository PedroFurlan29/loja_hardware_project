package model;


 // Representa um fornecedor de produtos da loja de hardware.

public class Fornecedor {

    private String cnpj;
    private String razaoSocial;

    public Fornecedor(String cnpj, String razaoSocial) {
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    @Override
    public String toString() {
        return String.format("Fornecedor: %s | CNPJ: %s", razaoSocial, cnpj);
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
}
