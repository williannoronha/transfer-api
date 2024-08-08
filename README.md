# Transfer API

## Descrição
A Transfer API é uma aplicação RESTful desenvolvida para gerenciar transferências bancárias entre contas. Esta API permite criar, visualizar e listar transferências, oferecendo uma solução robusta e segura para operações financeiras.

![Jornada Transfer API](../transfer-api/doc/jornada-transfer-api.png)

Esse projeto foi pensado apenas para fins de teste de criação de projeto Java.

## Tecnologias Utilizadas
Java 21
Spring Boot 3.3.2
Spring Data JPA
H2 Database
Lombok
Springdoc OpenAPI 2.0.2

## Pré-requisitos
Java JDK 21
Maven 3.9.8

## Configuração do Ambiente

1. Clone o repositório:

```sh
    git clone https://github.com/williannoronha/transfer-api.git
    cd transfer-api
```
2. Instale as dependências:

```sh
    mvn clean install
```

3. Executar a aplicação:

```shelshl
mvn spring-boot:run
```
## Endpoints da API

A documentação completa da API pode ser acessada via Swagger UI após iniciar a aplicação:

```bash
    http://localhost:8080/swagger-ui/index.html
```

### Exemplos de Endpoints

- Cadastrar Cliente
    - POST /api/v1/clientes
    ```json
    {
        "nome": "Maria das Dores", 
        "numeroConta": "171171", 
        "saldo": 400000.00
    }
    ```

- Consulta Clientes
    - GET /api/v1/clientes

- Busca Cliente através do número da conta
    - GET /api/v1/clientes/{numeroConta}

- Transferência entre contas
    - POST /api/v1/transferencias
    ```json
        {
            "id":null,
            "contaOrigem":"171171",
            "contaDestino":"752615-3",
            "valor":11000.00,
            "data":null
        }
    ```

- Histórico das transferências
    - GET /api/v1/transferencias/numeroConta


## Configuração do Swagger
A documentação da API é gerada automaticamente pelo Springdoc OpenAPI. A configuração está localizada na classe SwaggerConfig:

## Erros Comuns e Soluções
- Erro: java.lang.TypeNotPresentException: Type javax.servlet.http.HttpServletRequest not present

    - Solução: Certifique-se de que todas as dependências estão corretamente configuradas no pom.xml.

- Erro: Whitelabel Error Page

    - Solução: Verifique se o Swagger está corretamente configurado e a URL correta está sendo acessada (http://localhost:8080/swagger-ui/index.html).


## Contribuição
1. Faça um fork do projeto.
2. Crie uma nova branch: git checkout b minha-feature.
3.  Faça suas alterações e commit: git  commit -m 'Minha nova feature'.
4. Envie para o branch original: git push origin minha-feature.
5. Crie um pull request.


