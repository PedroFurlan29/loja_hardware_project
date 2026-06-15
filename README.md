# Hardware Store - Sistema de Gestão de Estoque e Vendas

**Disciplina**: Programação Orientada a Objetos  
**Universidade**: Centro Universitário Central Paulista (UNICEP)  
**Trabalho**: T2 - Implementação Web, Qualidade e Implantação  
**Alunos**: Felipe Machado, Lucas Simel Sodatti, Lucas Galhardi Cury, Pedro Scaramel Furlan

---

## Visão Geral

Sistema web completo para gerenciamento de estoque e vendas de uma loja especializada em hardware, implementado com **Spring Boot 3.x**, **PostgreSQL**, **JWT** e **Docker Compose**.

### Funcionalidades Principais
- ✅ Autenticação JWT stateless
- ✅ Gestão de produtos (CPU, GPU, Memória, Armazenamento)
- ✅ Controle de estoque com nível mínimo crítico
- ✅ Registro e cancelamento de vendas com auditoria
- ✅ Ingestão automática de dados (DummyJSON)
- ✅ API REST documentada via Swagger/OpenAPI
- ✅ Testes automatizados (JUnit 5 + TestContainers)
- ✅ CI/CD via GitHub Actions
- ✅ Docker Compose para deploy rápido

---

## Stack Técnico

### Backend
- **Framework**: Spring Boot 3.3.x
- **Build**: Gradle (Kotlin DSL)
- **BD**: PostgreSQL 15
- **ORM**: Spring Data JPA + Hibernate
- **Migrations**: Liquibase
- **Auth**: Spring Security 6.x + JWT (JJWT 0.12.3)
- **Validação**: Jakarta Bean Validation
- **Documentação API**: SpringDoc-OpenAPI 2.x
- **Testes**: JUnit 5 + Mockito + TestContainers

### DevOps
- **Containerização**: Docker
- **Orquestração**: Docker Compose
- **CI/CD**: GitHub Actions
- **Versionamento**: Git/GitHub

---

## Iniciando Localmente

### Pré-requisitos
- Java 17+
- Gradle 8+
- Docker + Docker Compose (opcional, para BD isolado)
- PostgreSQL 15 (se não usar Docker)

### Opção 1: Com Docker Compose (Recomendado)

```bash
git clone https://github.com/seu-usuario/hardware-store-unicep.git
cd hardware-store-unicep

docker-compose up -d

# Acesse:
# - API: http://localhost:8080/api
# - Swagger: http://localhost:8080/api/swagger-ui.html
```

### Opção 2: Desenvolvimento Local

```bash
export SPRING_PROFILES_ACTIVE=dev

createdb hardware_store -U postgres

./gradlew bootRun

# http://localhost:8080/api/swagger-ui.html
```

---

## Exemplo de Uso da API

### 1. Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@example.com","senha":"123456"}'
```

### 2. Listar Produtos

```bash
curl -X GET http://localhost:8080/api/produtos \
  -H "Authorization: Bearer <TOKEN>"
```

### 3. Registrar Venda

```bash
curl -X POST http://localhost:8080/api/vendas \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"itens": [{"produtoId": 1, "quantidade": 2}]}'
```

---

## Testes

```bash
./gradlew test
```

---

## Arquitetura

### Estrutura de Pacotes (By-Feature)
```
com.lojahardware.unicep/
├── config/
├── shared/
│   ├── exception/
│   ├── util/
│   ├── security/
│   └── model/
├── produtos/
│   ├── model/
│   ├── controller/
│   ├── service/
│   └── repository/
├── estoque/
├── vendas/
├── usuarios/
├── fornecedores/
└── ingest/
```

---

## Decisões Arquiteturais

| Decisão | Justificativa |
|---------|---------------|
| Spring Boot 3.x | Framework consolidado, excelente suporte |
| PostgreSQL | Robustez, transações ACID, escalabilidade |
| JWT Stateless | Sem dependência de sessão, escalável |
| Liquibase | Versionamento de schema, rollbacks |
| TestContainers | Testes com BD real, fidedigno |
| Gradle | Configuração como código, performance |
| By-Feature | Escalabilidade, coesão entre domínios |

---

## Licença

MIT © 2026 UNICEP Hardware Store
