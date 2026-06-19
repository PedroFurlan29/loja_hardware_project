# Hardware Store - Sistema de Gestao de Estoque e Vendas

**Disciplina**: Engenharia de Software | **Universidade**: UNICEP
**Alunos**: Felipe Machado, Lucas Simel Sodatti, Lucas Galhardi Cury, Pedro Scaramel Furlan

---

## Visao Geral

Sistema web para gestao de estoque e vendas de loja de hardware.
Spring Boot 3.3 + H2/PostgreSQL + JWT + Maven.

### Funcionalidades
- Autenticacao JWT stateless (login por email)
- CRUD de produtos (CPU, GPU, Memoria, Armazenamento)
- Controle de estoque com nivel minimo critico
- Registro e cancelamento de vendas
- Seed de dados iniciais

---

## Tecnologias

- Build: Maven 3.9+
- Framework: Spring Boot 3.3.2
- Banco: H2 (dev/test), PostgreSQL (producao)
- Seguranca: Spring Security 6 + JWT (JJWT 0.11.5)
- Java: 17+

---

## Como Rodar

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

## Docker

```
docker-compose up -d
```

Servicos:
- Backend: http://localhost:8080/api
- Frontend: http://localhost:4200

### Build manual

```
mvn clean package -DskipTests
docker build -t hardware-store .
```

---

## Frontend

Angular 22 + Angular Material.

```
cd frontend
npm install
npm start
# Abrir: http://localhost:4200
```

Em producao, o docker-compose sobe o frontend buildado via nginx.

---

## Usuarios de Seed

| Email | Senha | Perfil |
|---|---|---|
| admin@loja.com | admin123 | ADMIN |
| vendedor@loja.com | vendedor123 | VENDEDOR |
| cliente@loja.com | cliente123 | CLIENTE |
| cliente2@loja.com | cliente123 | CLIENTE |

> **Persistência:** O banco PostgreSQL mantém os dados em volume Docker. Usuários criados via seed ou registro persistem mesmo após restart do container. Para resetar os dados, use `docker compose down -v` (isso apaga o volume).

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
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{\"email\":\"admin@loja.com\",\"senha\":\"admin123\"}"
```

### Registrar Venda
```bash
curl -X POST http://localhost:8080/api/vendas -H "Authorization: Bearer <TOKEN>" -H "Content-Type: application/json" -d "{\"itens\":[{\"produtoId\":1,\"quantidade\":2}]}"
```

---

## Licenca

MIT (c) 2026 UNICEP Hardware Store
