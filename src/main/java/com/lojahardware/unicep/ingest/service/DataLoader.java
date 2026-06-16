package com.lojahardware.unicep.ingest.service;

import com.lojahardware.unicep.produtos.model.*;
import com.lojahardware.unicep.produtos.repository.ProdutoRepository;
import com.lojahardware.unicep.estoque.service.EstoqueService;
import com.lojahardware.unicep.usuarios.model.PerfilUsuario;
import com.lojahardware.unicep.usuarios.model.Usuario;
import com.lojahardware.unicep.usuarios.repository.UsuarioRepository;
import com.lojahardware.unicep.usuarios.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

@Component @Slf4j @Profile("!test")
public class DataLoader implements CommandLineRunner {
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepo;
    private final EstoqueService estoqueService;
    private final Random rand = new Random(42);

    public DataLoader(UsuarioService u, UsuarioRepository ur, ProdutoRepository p, EstoqueService e) {
        usuarioService = u; usuarioRepository = ur; produtoRepo = p; estoqueService = e;
    }

    @Override
    public void run(String... args) {
        log.info("=== DataLoader: seed idempotente ===");
        criarUsuarioSeNaoExiste("Admin",       "admin@loja.com",       "admin123",       PerfilUsuario.ADMIN);
        criarUsuarioSeNaoExiste("Vendedor",    "vendedor@loja.com",    "vendedor123",    PerfilUsuario.VENDEDOR);
        criarUsuarioSeNaoExiste("Estoquista",  "estoquista@loja.com",  "estoquista123",  PerfilUsuario.ESTOQUISTA);

        // ── CPUs ──────────────────────────────────────────────────────────────────
        salvar(cpu("CPU-001","Intel Core i9-14900K 24-Core 3.2GHz LGA1700",5999.99,4200,8,3,"https://m.media-amazon.com/images/I/61E8jdBSBKL._AC_SL1500_.jpg","Intel","i9-14900K",24,3.2,"Raptor Lake Refresh","LGA1700"));
        salvar(cpu("CPU-002","AMD Ryzen 9 7950X 16-Core 4.5GHz AM5",4799.99,3400,10,3,"https://m.media-amazon.com/images/I/61c2BqmJsGL._AC_SL1500_.jpg","AMD","Ryzen 9 7950X",16,4.5,"Zen 4","AM5"));
        salvar(cpu("CPU-003","Intel Core i5-13600K 14-Core 3.5GHz LGA1700",2199.99,1600,18,5,"https://m.media-amazon.com/images/I/41BC2D+PXAL._AC_SL1000_.jpg","Intel","i5-13600K",14,3.5,"Raptor Lake","LGA1700"));
        salvar(cpu("CPU-004","AMD Ryzen 5 7600X 6-Core 4.7GHz AM5",1599.99,1100,22,5,"https://m.media-amazon.com/images/I/515GjJ3UZML._AC_SL1500_.jpg","AMD","Ryzen 5 7600X",6,4.7,"Zen 4","AM5"));
        salvar(cpu("CPU-005","Intel Core i7-14700K 20-Core 3.4GHz LGA1700",3499.99,2500,12,3,"https://m.media-amazon.com/images/I/61E8jdBSBKL._AC_SL1500_.jpg","Intel","i7-14700K",20,3.4,"Raptor Lake Refresh","LGA1700"));
        salvar(cpu("CPU-006","AMD Ryzen 7 7700X 8-Core 4.5GHz AM5",2299.99,1650,15,5,"https://m.media-amazon.com/images/I/61c2BqmJsGL._AC_SL1500_.jpg","AMD","Ryzen 7 7700X",8,4.5,"Zen 4","AM5"));
        salvar(cpu("CPU-007","Intel Core i3-13100F 4-Core 3.4GHz LGA1700",699.99,490,30,8,"https://m.media-amazon.com/images/I/41BC2D+PXAL._AC_SL1000_.jpg","Intel","i3-13100F",4,3.4,"Raptor Lake","LGA1700"));
        salvar(cpu("CPU-008","AMD Ryzen 5 5600X 6-Core 3.7GHz AM4",999.99,710,25,6,"https://m.media-amazon.com/images/I/515GjJ3UZML._AC_SL1500_.jpg","AMD","Ryzen 5 5600X",6,3.7,"Zen 3","AM4"));
        salvar(cpu("CPU-009","Intel Core i9-13900KS 24-Core 3.2GHz LGA1700",6499.99,4600,5,2,"https://m.media-amazon.com/images/I/61E8jdBSBKL._AC_SL1500_.jpg","Intel","i9-13900KS",24,3.2,"Raptor Lake","LGA1700"));
        salvar(cpu("CPU-010","AMD Ryzen 9 5950X 16-Core 3.4GHz AM4",3999.99,2800,8,3,"https://m.media-amazon.com/images/I/61c2BqmJsGL._AC_SL1500_.jpg","AMD","Ryzen 9 5950X",16,3.4,"Zen 3","AM4"));
        salvar(cpu("CPU-011","Intel Core i5-14600K 14-Core 3.5GHz LGA1700",2499.99,1750,14,4,"https://m.media-amazon.com/images/I/41BC2D+PXAL._AC_SL1000_.jpg","Intel","i5-14600K",14,3.5,"Raptor Lake Refresh","LGA1700"));
        salvar(cpu("CPU-012","AMD Ryzen 7 5800X3D 8-Core 3.4GHz AM4",2099.99,1480,10,3,"https://m.media-amazon.com/images/I/515GjJ3UZML._AC_SL1500_.jpg","AMD","Ryzen 7 5800X3D",8,3.4,"Zen 3 3D V-Cache","AM4"));
        salvar(cpu("CPU-013","Intel Core i7-13700K 16-Core 3.4GHz LGA1700",2799.99,1960,13,4,"https://m.media-amazon.com/images/I/61E8jdBSBKL._AC_SL1500_.jpg","Intel","i7-13700K",16,3.4,"Raptor Lake","LGA1700"));
        salvar(cpu("CPU-014","AMD Ryzen 5 7600 6-Core 3.8GHz AM5",1299.99,900,20,5,"https://m.media-amazon.com/images/I/61c2BqmJsGL._AC_SL1500_.jpg","AMD","Ryzen 5 7600",6,3.8,"Zen 4","AM5"));
        salvar(cpu("CPU-015","Intel Pentium Gold G7400 2-Core 3.7GHz LGA1700",449.99,310,35,8,"https://m.media-amazon.com/images/I/41BC2D+PXAL._AC_SL1000_.jpg","Intel","Pentium G7400",2,3.7,"Alder Lake","LGA1700"));
        salvar(cpu("CPU-016","AMD Ryzen 3 4100 4-Core 3.8GHz AM4",499.99,340,28,7,"https://m.media-amazon.com/images/I/515GjJ3UZML._AC_SL1500_.jpg","AMD","Ryzen 3 4100",4,3.8,"Zen 2","AM4"));

        // ── PLACAS DE VÍDEO ──────────────────────────────────────────────────────
        salvar(gpu("GPU-001","ASUS ROG STRIX GeForce RTX 4090 24GB GDDR6X OC",14999.99,11000,4,2,"https://m.media-amazon.com/images/I/81kJxFfWqDL._AC_SL1500_.jpg","ASUS","ROG STRIX RTX 4090 OC",24,"GDDR6X","AD102",384));
        salvar(gpu("GPU-002","MSI GeForce RTX 4080 Super 16GB GDDR6X Gaming X Slim",9499.99,7000,6,2,"https://m.media-amazon.com/images/I/71h6epTaFGL._AC_SL1500_.jpg","MSI","RTX 4080 SUPER GAMING X SLIM",16,"GDDR6X","AD103",256));
        salvar(gpu("GPU-003","ASUS TUF Gaming Radeon RX 7900 XTX 24GB GDDR6",7299.99,5400,5,2,"https://m.media-amazon.com/images/I/81l6ZMHWNBL._AC_SL1500_.jpg","ASUS","TUF RX 7900 XTX OC",24,"GDDR6","Navi 31",384));
        salvar(gpu("GPU-004","Gigabyte GeForce RTX 4070 Ti Super 16GB GDDR6X WindForce",5199.99,3800,8,3,"https://m.media-amazon.com/images/I/51f7a0u-o5L._AC_SL1500_.jpg","Gigabyte","RTX 4070 Ti SUPER WINDFORCE",16,"GDDR6X","AD103",256));
        salvar(gpu("GPU-005","Sapphire Pulse AMD Radeon RX 7600 8GB GDDR6",1899.99,1400,22,5,"https://m.media-amazon.com/images/I/71CfHF0U7AL._AC_SL1500_.jpg","Sapphire","PULSE RX 7600",8,"GDDR6","Navi 33",128));
        salvar(gpu("GPU-006","NVIDIA GeForce RTX 4070 12GB GDDR6X Founders Edition",4299.99,3000,9,3,"https://m.media-amazon.com/images/I/81kJxFfWqDL._AC_SL1500_.jpg","NVIDIA","RTX 4070 FE",12,"GDDR6X","AD104",192));
        salvar(gpu("GPU-007","AMD Radeon RX 7800 XT 16GB GDDR6",3199.99,2240,11,4,"https://m.media-amazon.com/images/I/71CfHF0U7AL._AC_SL1500_.jpg","PowerColor","Red Devil RX 7800 XT",16,"GDDR6","Navi 32",256));
        salvar(gpu("GPU-008","ASUS GeForce RTX 3060 Ti 8GB GDDR6 Dual OC",2499.99,1750,13,4,"https://m.media-amazon.com/images/I/81l6ZMHWNBL._AC_SL1500_.jpg","ASUS","Dual RTX 3060 Ti OC",8,"GDDR6","GA104",256));
        salvar(gpu("GPU-009","MSI Radeon RX 6700 XT 12GB GDDR6 Gaming X",2999.99,2100,10,3,"https://m.media-amazon.com/images/I/71h6epTaFGL._AC_SL1500_.jpg","MSI","Gaming X RX 6700 XT",12,"GDDR6","Navi 22",192));
        salvar(gpu("GPU-010","Gigabyte GeForce RTX 4060 Ti 16GB GDDR6 Eagle OC",3799.99,2660,12,4,"https://m.media-amazon.com/images/I/51f7a0u-o5L._AC_SL1500_.jpg","Gigabyte","Eagle RTX 4060 Ti OC",16,"GDDR6","AD106",128));
        salvar(gpu("GPU-011","XFX Radeon RX 7700 12GB GDDR6 Speedster SWFT",2699.99,1890,14,4,"https://m.media-amazon.com/images/I/71CfHF0U7AL._AC_SL1500_.jpg","XFX","SWFT 309 RX 7700",12,"GDDR6","Navi 32",192));
        salvar(gpu("GPU-012","ASUS GeForce RTX 4060 8GB GDDR6 Dual OC",2099.99,1470,16,5,"https://m.media-amazon.com/images/I/81kJxFfWqDL._AC_SL1500_.jpg","ASUS","Dual RTX 4060 OC",8,"GDDR6","AD107",128));
        salvar(gpu("GPU-013","AMD Radeon RX 6600 8GB GDDR6 Challenger D",1599.99,1120,18,5,"https://m.media-amazon.com/images/I/81l6ZMHWNBL._AC_SL1500_.jpg","PowerColor","Challenger RX 6600",8,"GDDR6","Navi 23",128));
        salvar(gpu("GPU-014","Zotac GeForce GTX 1660 Super 6GB GDDR6 Twin Fan",1299.99,900,20,5,"https://m.media-amazon.com/images/I/71h6epTaFGL._AC_SL1500_.jpg","Zotac","GTX 1660 Super Twin Fan",6,"GDDR6","TU116",192));
        salvar(gpu("GPU-015","MSI GeForce RTX 4090 24GB GDDR6X Suprim X",16499.99,12000,3,1,"https://m.media-amazon.com/images/I/51f7a0u-o5L._AC_SL1500_.jpg","MSI","SUPRIM X RTX 4090",24,"GDDR6X","AD102",384));
        salvar(gpu("GPU-016","Sapphire Nitro+ Radeon RX 7900 GRE 16GB GDDR6",4799.99,3360,7,3,"https://m.media-amazon.com/images/I/71CfHF0U7AL._AC_SL1500_.jpg","Sapphire","NITRO+ RX 7900 GRE",16,"GDDR6","Navi 31",256));
        salvar(gpu("GPU-017","ASUS ProArt GeForce RTX 4080 16GB GDDR6X",9999.99,7000,5,2,"https://m.media-amazon.com/images/I/81kJxFfWqDL._AC_SL1500_.jpg","ASUS","ProArt RTX 4080",16,"GDDR6X","AD103",256));
        salvar(gpu("GPU-018","AMD Radeon RX 6500 XT 4GB GDDR6",849.99,590,25,6,"https://m.media-amazon.com/images/I/81l6ZMHWNBL._AC_SL1500_.jpg","Gigabyte","Eagle RX 6500 XT",4,"GDDR6","Navi 24",64));

        // ── MEMÓRIAS RAM ─────────────────────────────────────────────────────────
        salvar(mem("RAM-001","G.Skill Trident Z5 RGB DDR5 32GB (2x16GB) 6000MHz CL30",999.99,700,28,8,"https://m.media-amazon.com/images/I/817SzXPL7iL._AC_SL1500_.jpg","G.Skill",32,"DDR5",6000,30));
        salvar(mem("RAM-002","Corsair Vengeance DDR5 64GB (2x32GB) 5600MHz CL40",1499.99,1050,18,5,"https://m.media-amazon.com/images/I/61Q3HWLDSBL._AC_SL1500_.jpg","Corsair",64,"DDR5",5600,40));
        salvar(mem("RAM-003","Kingston Fury Beast DDR4 16GB (2x8GB) 3200MHz CL16",349.99,250,45,10,"https://m.media-amazon.com/images/I/71g9oZ9LO9L._AC_SL1500_.jpg","Kingston",16,"DDR4",3200,16));
        salvar(mem("RAM-004","Corsair Dominator Platinum RGB DDR5 32GB 6200MHz CL36",1299.99,910,14,4,"https://m.media-amazon.com/images/I/71G4sRMqvAL._AC_SL1500_.jpg","Corsair",32,"DDR5",6200,36));
        salvar(mem("RAM-005","G.Skill Ripjaws V DDR4 32GB (2x16GB) 3600MHz CL18",499.99,350,35,8,"https://m.media-amazon.com/images/I/817SzXPL7iL._AC_SL1500_.jpg","G.Skill",32,"DDR4",3600,18));
        salvar(mem("RAM-006","Kingston Fury Renegade DDR5 32GB 6400MHz CL32",1099.99,770,16,5,"https://m.media-amazon.com/images/I/71g9oZ9LO9L._AC_SL1500_.jpg","Kingston",32,"DDR5",6400,32));
        salvar(mem("RAM-007","Corsair Vengeance LPX DDR4 16GB 3000MHz CL15",299.99,210,50,10,"https://m.media-amazon.com/images/I/61Q3HWLDSBL._AC_SL1500_.jpg","Corsair",16,"DDR4",3000,15));
        salvar(mem("RAM-008","G.Skill Trident Z5 DDR5 64GB (2x32GB) 5600MHz CL36",1799.99,1260,10,3,"https://m.media-amazon.com/images/I/817SzXPL7iL._AC_SL1500_.jpg","G.Skill",64,"DDR5",5600,36));
        salvar(mem("RAM-009","Kingston ValueRAM DDR4 8GB 2666MHz",179.99,125,60,12,"https://m.media-amazon.com/images/I/71g9oZ9LO9L._AC_SL1500_.jpg","Kingston",8,"DDR4",2666,19));
        salvar(mem("RAM-010","Corsair Vengeance RGB Pro DDR4 32GB 3200MHz CL16",699.99,490,20,5,"https://m.media-amazon.com/images/I/71G4sRMqvAL._AC_SL1500_.jpg","Corsair",32,"DDR4",3200,16));
        salvar(mem("RAM-011","TeamGroup T-Force Vulcan DDR5 32GB 5200MHz CL40",849.99,595,22,6,"https://m.media-amazon.com/images/I/61Q3HWLDSBL._AC_SL1500_.jpg","TeamGroup",32,"DDR5",5200,40));
        salvar(mem("RAM-012","G.Skill Aegis DDR4 16GB 3000MHz CL16",279.99,196,40,8,"https://m.media-amazon.com/images/I/817SzXPL7iL._AC_SL1500_.jpg","G.Skill",16,"DDR4",3000,16));
        salvar(mem("RAM-013","Corsair Dominator DDR5 96GB (2x48GB) 6000MHz CL40",3499.99,2450,6,2,"https://m.media-amazon.com/images/I/71G4sRMqvAL._AC_SL1500_.jpg","Corsair",96,"DDR5",6000,40));
        salvar(mem("RAM-014","Kingston Fury Impact DDR5 32GB SO-DIMM 4800MHz",699.99,490,15,4,"https://m.media-amazon.com/images/I/71g9oZ9LO9L._AC_SL1500_.jpg","Kingston",32,"DDR5",4800,38));
        salvar(mem("RAM-015","G.Skill Trident Z Royal Elite DDR4 32GB 4000MHz CL16",1199.99,840,8,3,"https://m.media-amazon.com/images/I/817SzXPL7iL._AC_SL1500_.jpg","G.Skill",32,"DDR4",4000,16));
        salvar(mem("RAM-016","Corsair Vengeance DDR5 32GB (2x16GB) 5200MHz CL40",799.99,560,24,6,"https://m.media-amazon.com/images/I/61Q3HWLDSBL._AC_SL1500_.jpg","Corsair",32,"DDR5",5200,40));

        // ── ARMAZENAMENTO ─────────────────────────────────────────────────────────
        salvar(ssd("SSD-001","Samsung 990 Pro NVMe M.2 SSD 2TB PCIe 4.0",799.99,560,38,10,"https://m.media-amazon.com/images/I/61B8XJBM+AL._AC_SL1500_.jpg","Samsung","990 Pro",2000,"SSD NVMe","M.2 PCIe 4.0",7450));
        salvar(ssd("SSD-002","WD Black SN850X 1TB NVMe SSD PCIe 4.0 M.2",599.99,420,30,8,"https://m.media-amazon.com/images/I/71t2VZLThUL._AC_SL1500_.jpg","WD","Black SN850X",1000,"SSD NVMe","M.2 PCIe 4.0",7300));
        salvar(ssd("SSD-003","Seagate Barracuda 500GB SATA SSD 2.5\"",249.99,175,55,12,"https://m.media-amazon.com/images/I/81fHQm+YNNL._AC_SL1500_.jpg","Seagate","Barracuda SSD",500,"SSD SATA","SATA III",560));
        salvar(ssd("SSD-004","WD Blue 4TB HDD SATA 5400RPM 3.5\"",399.99,280,24,6,"https://m.media-amazon.com/images/I/81a34RcJAWL._AC_SL1500_.jpg","WD","Blue HDD",4000,"HDD SATA","SATA III",180));
        salvar(ssd("SSD-005","Samsung 870 EVO 1TB SATA SSD 2.5\"",499.99,350,32,8,"https://m.media-amazon.com/images/I/61B8XJBM+AL._AC_SL1500_.jpg","Samsung","870 EVO",1000,"SSD SATA","SATA III",560));
        salvar(ssd("SSD-006","Kingston NV2 2TB NVMe M.2 SSD PCIe 4.0",449.99,315,40,10,"https://m.media-amazon.com/images/I/71t2VZLThUL._AC_SL1500_.jpg","Kingston","NV2",2000,"SSD NVMe","M.2 PCIe 4.0",3500));
        salvar(ssd("SSD-007","Crucial P3 Plus 4TB NVMe M.2 PCIe 4.0",699.99,490,20,5,"https://m.media-amazon.com/images/I/81fHQm+YNNL._AC_SL1500_.jpg","Crucial","P3 Plus",4000,"SSD NVMe","M.2 PCIe 4.0",5000));
        salvar(ssd("SSD-008","Samsung 990 Pro 4TB NVMe M.2 PCIe 4.0",1499.99,1050,15,4,"https://m.media-amazon.com/images/I/61B8XJBM+AL._AC_SL1500_.jpg","Samsung","990 Pro",4000,"SSD NVMe","M.2 PCIe 4.0",7450));
        salvar(ssd("SSD-009","Seagate IronWolf 8TB NAS HDD 7200RPM",899.99,630,12,3,"https://m.media-amazon.com/images/I/81a34RcJAWL._AC_SL1500_.jpg","Seagate","IronWolf NAS",8000,"HDD SATA","SATA III",210));
        salvar(ssd("SSD-010","WD Red Plus 6TB NAS HDD 5400RPM",699.99,490,18,5,"https://m.media-amazon.com/images/I/71t2VZLThUL._AC_SL1500_.jpg","WD","Red Plus NAS",6000,"HDD SATA","SATA III",190));
        salvar(ssd("SSD-011","Crucial MX500 1TB SATA SSD 2.5\"",389.99,273,42,10,"https://m.media-amazon.com/images/I/81fHQm+YNNL._AC_SL1500_.jpg","Crucial","MX500",1000,"SSD SATA","SATA III",560));
        salvar(ssd("SSD-012","Kingston A2000 500GB NVMe M.2 PCIe 3.0",299.99,210,50,12,"https://m.media-amazon.com/images/I/61B8XJBM+AL._AC_SL1500_.jpg","Kingston","A2000",500,"SSD NVMe","M.2 PCIe 3.0",2200));
        salvar(ssd("SSD-013","WD Black SN770 2TB NVMe PCIe 4.0 M.2",649.99,455,26,7,"https://m.media-amazon.com/images/I/71t2VZLThUL._AC_SL1500_.jpg","WD","Black SN770",2000,"SSD NVMe","M.2 PCIe 4.0",5150));
        salvar(ssd("SSD-014","Samsung 980 Pro 1TB NVMe M.2 PCIe 4.0",499.99,350,35,9,"https://m.media-amazon.com/images/I/81fHQm+YNNL._AC_SL1500_.jpg","Samsung","980 Pro",1000,"SSD NVMe","M.2 PCIe 4.0",7000));
        salvar(ssd("SSD-015","Seagate Barracuda 2TB HDD SATA 7200RPM 3.5\"",299.99,210,28,7,"https://m.media-amazon.com/images/I/81a34RcJAWL._AC_SL1500_.jpg","Seagate","Barracuda HDD",2000,"HDD SATA","SATA III",200));
        salvar(ssd("SSD-016","Crucial T700 2TB NVMe M.2 PCIe 5.0",999.99,700,10,3,"https://m.media-amazon.com/images/I/61B8XJBM+AL._AC_SL1500_.jpg","Crucial","T700",2000,"SSD NVMe","M.2 PCIe 5.0",12400));

        // Marcar ~30% dos produtos com ofertas aleatórias
        aplicarOfertas();

        log.info("=== DataLoader concluido. Total de produtos: {} ===", produtoRepo.count());
    }

    private void aplicarOfertas() {
        List<Produto> todos = produtoRepo.findAll();
        todos.forEach(p -> {
            if (!p.getEmOferta() && rand.nextInt(10) < 3) { // 30% de chance
                double pct = 0.05 + rand.nextDouble() * 0.30; // 5–35% off
                BigDecimal oferta = p.getPrecoVenda().multiply(BigDecimal.valueOf(1 - pct)).setScale(2, RoundingMode.HALF_UP);
                p.setEmOferta(true);
                p.setPrecoOferta(oferta);
                produtoRepo.save(p);
            }
        });
        long ofertasCount = produtoRepo.findAll().stream().filter(Produto::getEmOferta).count();
        log.info("Ofertas aplicadas: {} produtos em promoção", ofertasCount);
    }

    private void criarUsuarioSeNaoExiste(String nome, String email, String senha, PerfilUsuario perfil) {
        if (usuarioRepository.findByEmail(email).isPresent()) { log.info("Usuário {} já existe.", email); return; }
        try { Usuario u = new Usuario(); u.setNome(nome); u.setEmail(email); u.setSenha(senha); u.setPerfil(perfil); usuarioService.criar(u); log.info("Usuário {} criado.", email); }
        catch (Exception e) { log.warn("Erro ao criar usuário {}: {}", email, e.getMessage()); }
    }

    private void salvar(Produto produto) {
        if (produtoRepo.findBySku(produto.getSku()).isPresent()) { log.info("SKU={} já existe.", produto.getSku()); return; }
        try {
            Produto saved = produtoRepo.save(produto);
            try { estoqueService.criar(saved.getId(), produto.getQuantidade(), produto.getEstoque_minimo()); }
            catch (Exception e) { log.warn("Estoque SKU={} já existe: {}", produto.getSku(), e.getMessage()); }
            log.info("Produto criado: {} | {}", saved.getSku(), saved.getDescricao());
        } catch (Exception e) { log.error("Erro SKU={}: {}", produto.getSku(), e.getMessage()); }
    }

    private CPU cpu(String sku,String desc,double preco,double custo,int qty,int min,String img,String marca,String modelo,int nucleos,double freq,String arq,String socket){
        CPU c=new CPU(); c.setSku(sku); c.setDescricao(desc); c.setPrecoVenda(bd(preco)); c.setCustoAquisicao(bd(custo)); c.setQuantidade(qty); c.setEstoque_minimo(min); c.setImagemUrl(img); c.setMarca(marca); c.setModelo(modelo); c.setNucleos(nucleos); c.setFrequenciaGHz(freq); c.setArquitetura(arq); c.setSocket(socket); return c;
    }
    private PlacaDeVideo gpu(String sku,String desc,double preco,double custo,int qty,int min,String img,String marca,String modelo,int vram,String tipoMem,String chipset,int largura){
        PlacaDeVideo g=new PlacaDeVideo(); g.setSku(sku); g.setDescricao(desc); g.setPrecoVenda(bd(preco)); g.setCustoAquisicao(bd(custo)); g.setQuantidade(qty); g.setEstoque_minimo(min); g.setImagemUrl(img); g.setMarca(marca); g.setModelo(modelo); g.setVramGb(vram); g.setTipoMemoria(tipoMem); g.setChipset(chipset); g.setLarguraBandaBits(largura); return g;
    }
    private Memoria mem(String sku,String desc,double preco,double custo,int qty,int min,String img,String marca,int cap,String tipo,int freq,int lat){
        Memoria m=new Memoria(); m.setSku(sku); m.setDescricao(desc); m.setPrecoVenda(bd(preco)); m.setCustoAquisicao(bd(custo)); m.setQuantidade(qty); m.setEstoque_minimo(min); m.setImagemUrl(img); m.setMarca(marca); m.setCapacidadeGb(cap); m.setTipoMemoria(tipo); m.setFrequenciaMHz(freq); m.setLatenciaCl(lat); return m;
    }
    private DispositivoDeArmazenamento ssd(String sku,String desc,double preco,double custo,int qty,int min,String img,String marca,String modelo,int cap,String tipo,String iface,int vel){
        DispositivoDeArmazenamento d=new DispositivoDeArmazenamento(); d.setSku(sku); d.setDescricao(desc); d.setPrecoVenda(bd(preco)); d.setCustoAquisicao(bd(custo)); d.setQuantidade(qty); d.setEstoque_minimo(min); d.setImagemUrl(img); d.setMarca(marca); d.setModelo(modelo); d.setCapacidadeGb(cap); d.setTipoArmazenamento(tipo); d.setInterface_(iface); d.setVelocidadeLeituraMBs(vel); return d;
    }
    private BigDecimal bd(double v){ return BigDecimal.valueOf(v); }
}
