# MCP Server de Sistema de Vendas

Este repositório contém o código-fonte de um sistema de vendas (Produtos, Clientes, Vendas, Itens de Venda) desenvolvido com Java, Spring Boot, AI Server, MCP Tools e PostgreSQL.



## Tecnologias Utilizadas

Este projeto foi construído utilizando as seguintes tecnologias:

*   **Java 21:** Linguagem de programação.
*   **Spring Boot 3.4.7:** Framework para desenvolvimento de aplicações Java.
*   **Spring AI:** Para funcionalidades de inteligência artificial (AI Server).
*   **MCP Tools:** Ferramentas específicas do projeto.
*   **PostgreSQL:** Banco de dados relacional.
*   **Maven:** Ferramenta de automação de build e gerenciamento de dependências.
*   **Spring Data JPA:** Para persistência de dados com o PostgreSQL.
*   **Spring Boot Starter Validation:** Para validação de dados.
*   **Lombok:** Para reduzir o código boilerplate.




## Estrutura do Projeto

A estrutura do projeto segue o padrão de aplicações Spring Boot, com pacotes bem definidos para cada camada da aplicação:

```
src
├── main
│   ├── java
│   │   └── br
│   │       └── com
│   │           └── wm
│   │               └── springmcp
│   │                   ├── SpringMcpApplication.java
│   │                   ├── config
│   │                   │   └── Config.java
│   │                   ├── controller
│   │                   │   ├── ClienteController.java
│   │                   │   ├── ProdutoController.java
│   │                   │   └── VendaController.java
│   │                   ├── exception
│   │                   │   └── GlobalExceptionHandler.java
│   │                   ├── model
│   │                   │   ├── Cliente.java
│   │                   │   ├── ItensVenda.java
│   │                   │   ├── Produto.java
│   │                   │   └── Venda.java
│   │                   ├── repository
│   │                   │   ├── ClienteRepository.java
│   │                   │   ├── ItensVendaRepository.java
│   │                   │   ├── ProdutoRepository.java
│   │                   │   └── VendaRepository.java
│   │                   ├── service
│   │                   │   ├── ClienteService.java
│   │                   │   ├── ProdutoService.java
│   │                   │   ├── VendaService.java
│   │                   │   └── VendasAnalysisService.java
│   │                   └── tools
│   │                       └── VendasMcpTools.java
│   └── resources
│       └── application.properties
└── test
    └── java
        └── br
            └── com
                └── wm
                    └── springmcp
                        └── SpringMcpApplicationTests.java
```




## Pré-requisitos

Para executar este projeto, você precisará ter o seguinte software instalado em sua máquina:

*   **Java Development Kit (JDK) 21** ou superior.
*   **Apache Maven**.
*   **PostgreSQL**.





## Configuração do Banco de Dados

1.  Certifique-se de ter uma instância do PostgreSQL em execução.
2.  Crie um novo banco de dados para o projeto (ex: `spring_mcp_db`).
3.  Atualize as configurações de conexão com o banco de dados no arquivo `src/main/resources/application.properties`:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/spring_mcp_db
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    ```

    Substitua `seu_usuario` e `sua_senha` pelas suas credenciais do PostgreSQL.




## Como Executar a Aplicação

1.  Clone o repositório:

    ```bash
    git clone https://github.com/williammian/spring-mcp.git
    cd spring-mcp
    ```

2.  Compile o projeto usando Maven:

    ```bash
    mvn clean install
    ```

3.  Execute a aplicação Spring Boot:

    ```bash
    mvn spring-boot:run
    ```

    A aplicação estará disponível em `http://localhost:8080`.




## Endpoints da API

O sistema expõe os seguintes endpoints RESTful:

### Clientes
*   `GET /clientes`: Lista todos os clientes.
*   `GET /clientes/{id}`: Retorna um cliente pelo ID.
*   `POST /clientes`: Cria um novo cliente.
*   `PUT /clientes/{id}`: Atualiza um cliente existente.
*   `DELETE /clientes/{id}`: Exclui um cliente.

### Produtos
*   `GET /produtos`: Lista todos os produtos.
*   `GET /produtos/{id}`: Retorna um produto pelo ID.
*   `POST /produtos`: Cria um novo produto.
*   `PUT /produtos/{id}`: Atualiza um produto existente.
*   `DELETE /produtos/{id}`: Exclui um produto.

### Vendas
*   `GET /vendas`: Lista todas as vendas.
*   `GET /vendas/{id}`: Retorna uma venda pelo ID.
*   `POST /vendas`: Cria uma nova venda.
*   `PUT /vendas/{id}`: Atualiza uma venda existente.
*   `DELETE /vendas/{id}`: Exclui uma venda.




## Configuração do Claude Desktop

Para integrar este projeto com o Claude Desktop, você precisará configurar o arquivo `claude_desktop_config.json` da seguinte forma:

1.  Localize o arquivo `claude_desktop_config.json` em sua instalação do Claude Desktop.
2.  Adicione a seguinte configuração dentro do objeto `mcpServers`:

    ```json
    {
        "mcpServers": {
            "vendas-analysis-server": {
                "command": "java",
                "args": [
                    "-jar",
                    "C:\\Fontes\\FontesSTS\\spring-mcp\\target\\spring-mcp-0.0.1-SNAPSHOT.jar"
                ]
            }
        }
    }
    ```

    **Observação:** Certifique-se de que o caminho para o arquivo `.jar` (`C:\\Fontes\\FontesSTS\\spring-mcp\\target\\spring-mcp-0.0.1-SNAPSHOT.jar`) esteja correto e aponte para o local onde o JAR do seu projeto foi compilado.
