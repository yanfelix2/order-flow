# 🍔 Order Flow API

API REST para gerenciamento de fluxo de pedidos em lanchonetes e restaurantes, desenvolvida com **Spring Boot 4** e **MySQL**. Foco em integridade de dados, cálculos automáticos de totais e tratamento de erros padronizado.

## 🚀 Tecnologias utilizadas

* **Java 21** (LTS)
* **Spring Boot 4.0.4**
* **Spring Data JPA / Hibernate**
* **MySQL**
* **Maven**
* **Swagger / OpenAPI 3**

## 🏗️ Estrutura do Projeto

O projeto segue uma arquitetura em camadas para facilitar a manutenção e escalabilidade:

```text
src/main/java/com/projects/order_flow
├── controller     # Endpoints da API
├── service        # Regras de negócio e validações
├── database
│   ├── model      # Entidades JPA
│   ├── repository # Interfaces de acesso ao banco
│   └── enums      # Tipos enumerados (Cargo, Status)
├── dto            # Objetos de transferência (Records)
├── exception      # Exceções personalizadas
└── handler        # Tratamento global de erros
```

**Arquitetura:**
`Controller` → `Service` → `DTO Mapping` → `Repository` → `Database`

## ⚙️ Funcionalidades

### 👤 Usuários
* Cadastro e atualização com validação de **Cargos** (ADMIN, GARCOM, COZINHA).
* Proteção de dados sensíveis utilizando DTOs.

### 📦 Catálogo (Produtos & Categorias)
* Cadastro de produtos vinculados a categorias existentes.
* Atualização de preços e descrições com validação de integridade.

### 📝 Pedidos & Itens
* Abertura de pedidos e adição de itens com **cálculo automático de valor total**.
* **Congelamento de preço:** O item registra o valor do produto no momento da venda.
* Bloqueio de alterações em pedidos com status `PAGO` ou `CANCELADO`.

## 🗄️ Banco de Dados

* **Banco:** MySQL 8
* **Tabelas principais:** `usuarios`, `produtos`, `categorias`, `pedidos`, `itens_pedido`.

**Relacionamentos:**
* `Produto` → `Categoria` (Muitos para Um)
* `ItemPedido` → `Pedido` e `Produto`
* `Pedido` → `Usuario` (Atendente/Entregador)

## 🛠️ Como executar localmente

1. **Clonar o repositório**
   ```bash
   git clone https://github.com/seu-usuario/order-flow.git
   cd order-flow
   ```

2. **Configurar o banco de dados**
   Crie um banco chamado `order_flow` no seu MySQL e ajuste o `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/order_flow
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```

3. **Rodar a aplicação**
   ```bash
   mvn spring-boot:run
   ```

## 📚 Documentação Swagger

Com a aplicação rodando, acesse:
`http://localhost:8080/swagger-ui/index.html`

## 📌 Endpoints principais

### Usuários
* `POST /usuarios` - Cadastrar
* `GET /usuarios` - Listar todos
* `PUT /usuarios/{id}` - Atualizar dados e cargo

### Produtos
* `POST /produtos` - Cadastrar produto vinculado a categoria
* `GET /produtos` - Listar com detalhes da categoria
* `PUT /produtos/{id}` - Atualizar preço/dados

### Itens de Pedido
* `POST /itens-pedido` - Adicionar item e atualizar total do pedido
* `DELETE /itens-pedido/{id}` - Remover item e estornar valor do pedido

---

## 👨‍💻 Autor

**Yan Pimentel** - Desenvolvedor Back-End em formação.
* **GitHub:** [yanfelix2](https://github.com/yanfelix2)
* **LinkedIn:** [Yan Pimentel](https://www.linkedin.com/in/yan-pimentel/)
