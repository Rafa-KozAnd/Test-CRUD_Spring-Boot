# Test-CRUD_Spring-Boot

# CRUD Spring Boot

Este projeto é uma aplicação CRUD (Create, Read, Update, Delete) desenvolvida com Java e Spring Boot. A aplicação permite gerenciar informações de pessoas e seus endereços utilizando um banco de dados H2.

## Como Compilar e Executar

Para compilar e executar o projeto, execute o seguinte comando:

```bash
mvn spring-boot:run
```

O projeto executará à partir da:
```bash
http://localhost:8080
```

Para reinstalar e atualizar/limpar o sistema:
```bash
mvn clean install
```

### Acessando a Consola H2

Após iniciar sua aplicação, você pode acessar a consola H2 para visualizar e manipular os dados. Abra um navegador e vá para:

```bash
http://localhost:8080/h2-console
```

### Teste Unitários do C.R.U.D.

```bash
mvn test
```

### Tecnologias Utilizadas
- Java: Linguagem de programação utilizada.

- Spring Boot: Framework para desenvolvimento de aplicações Java.

- JPA / Hibernate: Ferramentas para acesso a dados e mapeamento objeto-relacional.

- H2 Database: Banco de dados em memória utilizado para armazenar os dados.

- Maven: Ferramenta de gerenciamento de dependências e build.