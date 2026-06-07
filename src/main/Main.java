package main;

import enums.PerfilUsuario;
import enums.TipoArmazenamento;
import enums.TipoMemoria;
import model.*;


 // Classe principal para demonstração do funcionamento do sistema.
 // Simula um fluxo completo: cadastro de produtos, venda, cancelamento e relatório.


public class Main {

    public static void main(String[] args) {


        // 1. Criação de usuários:

        Usuario vendedor = new Usuario("Joao", "123456", PerfilUsuario.VENDEDOR);
        Usuario admin    = new Usuario("admin", "admin123", PerfilUsuario.ADMIN);

        System.out.println("=== USUÁRIOS CRIADOS ===");
        System.out.println(vendedor);
        System.out.println(admin);
        System.out.println();


        // 2. Autenticação

        System.out.println("=== AUTENTICAÇÃO ===");
        System.out.println("Login 'Joao' / '123456': " + vendedor.autenticar("Joao", "1234"));
        System.out.println("Login 'Joao' / 'errada': " + vendedor.autenticar("Joao", "errada"));
        System.out.println();


        // 3. Criação de fornecedores

        Fornecedor fornecedor1 = new Fornecedor("12.345.678/0001-99", "HardWares Ltda");
        Fornecedor fornecedor2 = new Fornecedor("98.765.432/0001-11", "Logitech S.A.");


        // 4. Cadastro de produtos

        CPU cpu = new CPU(
            "CPU-001", "Intel Core i9-14900K", 5299.99, 5, 2,
            "Intel 9", 24
        );
        PlacaDeVideo gpu = new PlacaDeVideo(
            "GPU-001", "NVIDIA RTX 4080 Super", 7499.00, 9, 1,
            16, "AD103"
        );
        DispositivoDeArmazenamento ssd = new DispositivoDeArmazenamento(
            "SSD-001", "Samsung 990 Pro 1TB", 1699.99, 20, 3,
            1000, TipoArmazenamento.NVME
        );
        Memoria ram = new Memoria(
            "MEM-001", "Corsair Vengeance 32GB", 1200.00, 15, 3,
            32, TipoMemoria.DDR5
        );


        // 5. Estoque

        Estoque estoque = new Estoque();
        estoque.adicionarProduto(cpu);
        estoque.adicionarProduto(gpu);
        estoque.adicionarProduto(ssd);
        estoque.adicionarProduto(ram);

        System.out.println(estoque.gerarRelatorio());


        // 6. Descrições técnicas (polimorfismo)

        System.out.println("=== DESCRIÇÕES TÉCNICAS (Polimorfismo) ===");
        for (Produto p : estoque.getProdutos()) {
            System.out.println(p.getDescricaoTecnica());
        }
        System.out.println();


        // 7. Registrar uma venda

        System.out.println("=== REGISTRANDO VENDA ===");
        Venda venda1 = new Venda(vendedor);

        ItemVenda item1 = new ItemVenda(cpu, 1, fornecedor1);
        ItemVenda item2 = new ItemVenda(ram, 2, fornecedor2);

        venda1.adicionarItem(item1);
        venda1.adicionarItem(item2);
        venda1.finalizarVenda();

        System.out.println(venda1);
        System.out.println();


        // 8. Estoque após venda

        System.out.println("=== ESTOQUE APÓS VENDA ===");
        System.out.println("CPU estoque: " + cpu.getQtdEstoque());
        System.out.println("RAM estoque: " + ram.getQtdEstoque());
        System.out.println();


        // 9. Reposição de estoque

        System.out.println("=== REPOSIÇÃO DE ESTOQUE ===");
        estoque.registrarEntrada("CPU-001", 5);
        System.out.println("CPU estoque após reposição: " + cpu.getQtdEstoque());
        System.out.println();


        // 10. Cancelamento de venda (fluxo completo)

        System.out.println("=== CANCELAMENTO DE VENDA ===");
        venda1.solicitarCancelamento();
        System.out.println("Status após solicitação: " + venda1.getStatus());

        venda1.aprovarCancelamento(admin);
        System.out.println("CPU estoque após cancelamento aprovado: " + cpu.getQtdEstoque());
        System.out.println("RAM estoque após cancelamento aprovado: " + ram.getQtdEstoque());
        System.out.println();


        // 11. Relatório final com produtos críticos
        // Forçar estoque crítico na GPU para demonstração

        gpu.atualizarEstoque(-4);
        System.out.println(estoque.gerarRelatorio());
    }
}
