# Hardware Store - Sistema de Gestao de Estoque e Vendas

**Disciplina**: Programacao Orientada a Objetos
**Universidade**: UNICEP
**Alunos**: Felipe Machado, Lucas Simel Sodatti, Lucas Galhardi Cury, Pedro Scaramel Furlan
---

## Visao Geral

Sistema web para gerenciamento de estoque e vendas de loja de hardware.
Spring Boot 3.3 + H2/PostgreSQL + JWT + Maven.

### Funcionalidades
- Autenticacao JWT stateless (login por email)
- CRUD de produtos (CPU, GPU, Memoria, Armazenamento)
- Controle de estoque com nivel minimo critico
- Registro e cancelamento de vendas
- Seed de dados iniciais (usuarios + produto CPU)

---

## Tecnologias

- Build: Maven 3.9+
- Framework: Spring Boot 3.3.2
- Banco: H2 (dev/test), PostgreSQL (producao)
- ORM: Spring Data JPA + Hibernate
- Seguranca: Spring Security 6 + JWT (JJWT 0.11.5)
- Validacao: Jakarta Bean Validation
- Java: 17+
---

## Como Rodar

Pre-requisitos: Java 17+, Maven 3.9+

```
git clone <repo>
cd <repo>
mvn spring-boot:run
# API: http://localhost:8080/api
# H2 Console: http://localhost:8080/api/h2-console
```

```
mvn test
```

---

## Usuarios de Seed

| Email | Senha | Perfil |
|---|---|---|
| admin@loja.com | admin123 | ADMIN |
| vendedor@loja.com | vendedor123 | VENDEDOR |
| estoquista@loja.com | estoquista123 | ESTOQUISTA |
---

## Endpoints da API

Base URL: http://localhost:8080/api

### Autenticacao

| Metodo | Rota | Descricao |
|---|---|---|
| POST | /auth/login | Login (email + senha) -> token JWT |

### Produtos

| Metodo | Rota | Descricao |
|---|---|---|
| GET | /produtos?p=0&s=10 | Listar paginado |
| GET | /produtos/{id} | Buscar por ID |
| POST | /produtos | Criar produto |
| PUT | /produtos/{id} | Atualizar produto |

### Estoque

| Metodo | Rota | Descricao |
|---|---|---|
| GET | /estoque/{produtoId} | Consultar estoque |
| POST | /estoque | Criar registro |
| PUT | /estoque/{produtoId}/baixar | Baixar quantidade |
| PUT | /estoque/{produtoId}/repor | Repor quantidade |

### Vendas

| Metodo | Rota | Descricao |
|---|---|---|
| GET | /vendas | Listar vendas |
| GET | /vendas/{id} | Buscar venda |
| POST | /vendas | Registrar venda |
| POST | /vendas/{id}/cancelar | Cancelar venda |
---

## Exemplos de Uso

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{"email":"admin@loja.com","senha":"admin123"}"
```
