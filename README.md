<h1 align="center">Currency converter</h1>

<p align="center">
 <a href="#-sobre-o-projeto">Sobre</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
 <a href="#-stack">Stack</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
 <a href="#-estrutura-do-projeto">Estrutura</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
 <a href="#-como-executar-o-projeto">Como executar</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
 <a href="#-endoints">Endpoints</a>
</p>

## 💻 Sobre o projeto

Implementação do convesor de moedas visando cumprir os requisitos do desafio técnico proposto.

Nesse desafio me desafiei a seguir uma das stacks proposta na qual eu nunca havia trabalhando antes, não consegui implementar todos os itens desejáveis, mas a API está funcional.

---

### 🛠 Stack

As seguintes ferramentas foram usadas na construção do projeto:

- [Kotlin](https://github.com/JetBrains/kotlin) como linguagem de programação
- [Javalin](https://github.com/tipsy/javalin) como framework web
- [Koin](https://github.com/InsertKoinIO/koin) framework de injeção de dependências
- [Jackson](https://github.com/FasterXML/jackson-module-kotlin) as data bind serialization/deserialization
- [HikariCP](https://github.com/brettwooldridge/HikariCP) as datasource to abstract driver implementation
- [H2](https://github.com/h2database/h2database) como banco de dados
- [Exposed](https://github.com/JetBrains/Exposed) como framework da camada de persistência de dados
- [Retrofit](https://square.github.io/retrofit/) client HTTP para consumo  de API externas
- [Mockk](https://mockk.io/) para criar mocks utilizados nos testes unitários

### Estrutura do projeto
      + config/
          Todas as configurações da aplicativos. Javalin, Koin e Database.
      + domain/
        + repository/
            Camada de persistência e definição de tabelas.
        + service/
            Camada contendo a lógica do negócio.
      + web/
        + controllers
            Classes que representam os Endpoints da aplicação.
        Router Roteador para recursos
      - Main.kt <- Classe main da aplicação.

### 🚀 Como executar o projeto

### Pré-requisitos
-  [Git](https://git-scm.com)
-  [Java(JDK 1.8)](https://openjdk.java.net/install/)
-  [Gradle](https://gradle.org/)


### Inciando a aplicação

#### clone este repositório
```
$ git clone git@github.com:mcruz85/CurrencyConverter.git
```

#### Testes
```
$ gradle test
```

#### Inicie o servidor
```
$ gradle run
```

## Endpoints

Local
> http://localhost:7000

Heroku
> https://jaya-currency-converter.herokuapp.com


- **`POST /transactions`**: O endpoint deve receber `from`, `to`, `amount`  e `userId` dentro do corpo da requisição, sendo que atributos `from` e `to` devem ser as possíveis moedas (`BRL`, `USD`, `EUR`, `JPY`), para realizar a conversão.

Request:
```json
{
  "from": "BRL",
  "to": "USD",
  "amount": 10.0,
  "userId": 1
}
```
Response:
```json
{
  "id": 1,
  "userId": 1,
  "originCurrency": "BRL",
  "originAmount": 10.0,
  "destinationCurrency": "USD",
  "destinationAmount": 1.9617834036651973,
  "exchangeRate": 0.19617834036651974,
  "createdAt": "2021-06-21T02:07:02.602Z"
}
```


- **`GET /transactions?user={id}`**: Esse endpoint retorna todas com transações realizadas por um usuário


Response:
```json
{
  "transactions": [
    {
      "id": 1,
      "userId": 1,
      "originCurrency": "BRL",
      "originAmount": 10.0,
      "destinationCurrency": "USD",
      "destinationAmount": 1.9617840928644228,
      "exchangeRate": 0.19617840928644228,
      "createdAt": "2021-06-21T00:00:00.000Z"
    },
    {
      "id": 2,
      "userId": 1,
      "originCurrency": "BRL",
      "originAmount": 10.0,
      "destinationCurrency": "USD",
      "destinationAmount": 1.9617840928644228,
      "exchangeRate": 0.19617840928644228,
      "createdAt": "2021-06-21T00:00:00.000Z"
    }
  ]
}
```
