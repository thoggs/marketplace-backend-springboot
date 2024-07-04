# Marketplace

<p align="center" width="100%">
    <img width="22%" src="https://www.vectorlogo.zone/logos/springio/springio-ar21.svg" alt="Spring Boot Logo">
</p>

## Descrição do Projeto

Este é um projeto de backend utilizando Spring Boot e Java 21. Ele inclui a configuração inicial do
projeto, modelos básicos e endpoints para manipulação de dados em um banco de dados PostgreSQL.

## Estrutura do Projeto

```
project-root/
├── app/
│   ├── models/
│   │   ├── ProductModel.java
│   │   └── UserModel.java
│   ├── web/
│   │   └── controllers/
│   │       ├── AuthController.java
│   │       ├── HealthController.java
│   │       ├── ProductController.java
│   │       └── UserController.java
│   ├── config/
│   │   ├── AppConfig.java
│   │   ├── CorsConfig.java
│   │   ├── EnvironConfig.java
│   │   ├── RestTemplateConfig.java
│   │   └── SecurityConfig.java
├── dtos/
│   ├── auth/
│   ├── params/
│   ├── query/
│   ├── record/
│   └── response/
├── exceptions/
│   ├── EmailAlreadyExistsException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── filters/
│   └── JwtAuthenticationFilter.java
├── repository/
│   ├── ProductRepository.java
│   └── UserRepository.java
├── security/
│   └── auth/
│       ├── CustomAccessDeniedHandler.java
│       ├── CustomAuthenticationEntryPoint.java
│       └── CustomUserNotFoundException.java
├── services/
│   ├── jwt/
│   ├── user/
│   └── web/
├── shared/
│   ├── enums/
│   └── parsers/
├── MarketplaceBackendApplication.java
├── application.properties
├── application-docker.properties
├── application-local.properties
└── application-production.properties
└── ...
```

## Pré-requisitos

- Java 21
- Gradle 8.8
- Banco de dados PostgreSQL
- Docker (apenas para inicialização rápida)
- Docker Compose (apenas para inicialização rápida)

## Inicialização Rápida

Subir o projeto com Docker:

### 1. Clone o repositório e entre na pasta do projeto:

```bash
git clone https://github.com/thoggs/marketplace-backend-springboot.git && cd marketplace-backend-springboot && docker-compose up -d
```

- **Observação**: O seeder de dados é executado automaticamente ao iniciar o contêiner. Para desativá-lo, defina a
  variável de ambiente `SEED_DB` como `false`.

### 3. Acesse o Projeto:

> O projeto estará disponível em http://localhost:8080/api/{endpoint}

## Descrição da API

### Endpoints:

### **Auth** (Autenticação)

- **POST /auth/signin**: autentica um usuário e retorna dados do usuário e um token de acesso.
- **POST /auth/signup**: registra um novo usuário e retorna dados do usuário e um token de acesso.
- **POST /github-signin**: autentica um usuário com o GitHub e retorna dados do usuário e um token de acesso.

### **Users** (Usuários)

- **GET /api/users**: retorna uma lista paginada de todos os usuarios registrados. É possível personalizar a
  página e a quantidade de resultados exibidos na lista adicionando os seguintes parâmetros à URL:
    - **page**: número da página a ser exibida.
        - Exemplo: `http://localhost:8080/api/users?page=2` exibe a segunda página de resultados.

    - **pageSize**: quantidade de resultados exibidos por página.
        - Exemplo: `http://localhost:8080/api/users?perPage=5&page=3` exibe a terceira página com até 5
          usuários por página.

    - **searchTerm**: termo de pesquisa para filtrar resultados.
        - Será executado `LIKE` no banco de dados pelo termo informado.
        - Exemplo: `http://localhost:8080/api/users?searchTerm=John` filtra resultados contendo "John".
    - **sorting**: ordena os resultados por uma coluna específica.
        - Exemplo: `http://localhost:8080/api/users?sorting=sorting=[{"id":"firstName","desc":false}]`

- **GET /api/users/{id}**: retorna informações detalhadas sobre um usuário específico.

- **POST /api/users**: cria um novo registro de usuário.

- **PUT /api/users/{id}**: atualiza as informações de um usuário existente.

- **DELETE /api/users/{id}**: exclui um registro de usuário existente.

### **Products** (Produtos)

- **GET /api/products**: retorna uma lista paginada de todos os produtos registrados. É possível personalizar a
  página e a quantidade de resultados exibidos na lista adicionando os seguintes parâmetros à URL:
    - **page**: número da página a ser exibida.
        - Exemplo: `http://localhost:8080/api/products?page=2` exibe a segunda página de resultados.

    - **pageSize**: quantidade de resultados exibidos por página.
        - Exemplo: `http://localhost:8080/api/products?perPage=5&page=3` exibe a terceira página com até 5
          produtos por página.

    - **searchTerm**: termo de pesquisa para filtrar resultados.
        - Será executado um `LIKE` no banco de dados pelo termo informado.
        - Exemplo: `http://localhost:8080/api/products?searchTerm=John` filtra resultados contendo "John".
    - **sorting**: ordena os resultados por uma coluna específica.
        - Exemplo: `http://localhost:8080/api/products?sorting=sorting=[{"id":"name","desc":false}]`

### **Health** (Status)

- **GET /health/check**: retorna o status da aplicação.

## Configuração para Desenvolvimento

### Passo 1: Clonar o Repositório

```bash
git clone https://github.com/thoggs/marketplace-backend-springboot.git && cd marketplace-backend-springboot
```

### Passo 2: Instalar Dependências

```bash
./gradlew build
```

### Passo 3: Configurar Variáveis de Ambiente

Crie ou edite o arquivo `.env` na raiz do projeto e adicione suas configurações do banco de dados:

```env
JWT_SECRET=mysecretkey
JWT_EXPIRATION_TIME=86400000
GITHUB_API_URL=https://api.github.com
SEE_DB=true
```

### Passo 5: Rodar o Projeto

#### Para rodar o projeto em modo de desenvolvimento:

```bash
./gradlew bootRun
```

#### Para rodar o projeto em modo de produção:

```bash
./gradlew build && java -jar build/libs/marketplace-backend-0.0.1-SNAPSHOT.jar
```

## Tecnologias Utilizadas

- **Spring Boot**: Framework para criação de aplicações Java
- **Hibernate JPA**: ORM para mapeamento objeto-relacional
- **PostgreSQL**: Banco de dados relacional
- **Spring Security**: Framework para segurança e autenticação
- **Spring Web**: Framework para desenvolvimento web e APIs REST
- **Spring Data JPA**: Abstração para simplificar o acesso a dados com JPA
- **java-jwt**: Biblioteca para geração e validação de tokens JWT
- **Spring Boot Starter Test**: Dependência para testes unitários e de integração
- **Spring Boot DevTools**: Ferramentas de desenvolvimento para Spring Boot
- **Lombok**: Biblioteca para simplificação de código Java através de anotações
- **Docker**: Plataforma de contêineres
- **Gradle**: Ferramenta de automação de build e gerenciamento de dependências
- **Actuator**: Monitoramento e métricas para aplicações Spring Boot

## License

Project license [Apache-2.0](https://opensource.org/license/apache-2-0)