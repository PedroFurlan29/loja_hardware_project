# Hardware Store - Sistema de Gestão de Estoque e Vendas

**Disciplina**: Programação Orientada a Objetos  
**Universidade**: Centro Universitário Central Paulista (UNICEP)  
**Trabalho**: T2 - Implementação Web, Qualidade e Implantação  
**Alunos**: Felipe Machado, Lucas Simel Sodatti, Lucas Galhardi Cury, Pedro Scaramel Furlan

---

## Visão Geral

Sistema web completo para gerenciamento de estoque e vendas de uma loja especializada em hardware, implementado com **Spring Boot 3.x**, **PostgreSQL**, **JWT** e **Docker Compose**.

### Funcionalidades Principais
- Autenticação JWT stateless
- Gestão de produtos (CPU, GPU, Memória, Armazenamento)
- Controle de estoque com nível mínimo crítico
- Registro e cancelamento de vendas com auditoria
- Ingestão automática de dados (DummyJSON)
- API REST documentada via Swagger/OpenAPI
- Testes automatizados (JUnit 5 + TestContainers)
- CI/CD via GitHub Actions
- Docker Compose para deploy rápido

---

## Tecnologias

### Backend
- **Framework**: Spring Boot 3.3.x
- **Build**: Gradle (Kotlin DSL)
- **Banco de Dados**: PostgreSQL 15
- **ORM**: Spring Data JPA + Hibernate
- **Migrações**: Liquibase
- **Autenticação**: Spring Security 6.x + JWT (JJWT 0.12.3)
- **Validação**: Jakarta Bean Validation
- **Documentação da API**: SpringDoc-OpenAPI 2.x
- **Testes**: JUnit 5 + Mockito + TestContainers

### DevOps
- **Contêineres**: Docker
- **Orquestração**: Docker Compose
- **CI/CD**: GitHub Actions
- **Versionamento**: Git/GitHub

---

## Como Rodar Localmente

### Pré-requisitos
- Java 17+
- Gradle 8+
- Docker + Docker Compose (opcional, para BD isolado)
- PostgreSQL 15 (se não usar Docker)

### Opção 1: Com Docker Compose (Recomendado)

```bash
git clone https://github.com/PedroFurlan29/loja_hardware_project.git
cd loja_hardware_project

docker-compose up -d

# Acesse:
# - API: http://localhost:8080/api
# - Swagger: http://localhost:8080/api/swagger-ui.html
```

### Opção 2: Desenvolvimento Local

```bash
set SPRING_PROFILES_ACTIVE=dev

createdb hardware_store -U postgres

./gradlew bootRun

# http://localhost:8080/api/swagger-ui.html
```

---

## Exemplos de Uso da API

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

### 3. Registrar uma Venda

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
| Spring Boot 3.x | Framework consolidado, excelente suporte da comunidade |
| PostgreSQL | Robustez, transações ACID, escalabilidade |
| JWT Stateless | Sem dependência de sessão no servidor, escalável |
| Liquibase | Versionamento de schema, rollbacks, rastreabilidade |
| TestContainers | Testes com banco real, não em memória, mais fidedigno |
| Gradle | Configuração como código, cache de build, performance |
| By-Feature | Coesão por domínio, facilita adicionar novas funcionalidades |

---

## Licença

MIT © 2026 UNICEP Hardware Store
