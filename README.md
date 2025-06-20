# Movie Awards API

API para análise de intervalos de prêmios entre produtores de filmes vencedores. Desenvolvida com Java e Spring Boot.

---

## Funcionalidades

- Importação de filmes via CSV. (basta alterar o arquivo src/main/resources/movielist.csv)
- Cálculo do produtor com o **menor** e **maior** intervalo entre vitórias. (/movies/listMinAndMax)
- Endpoints REST para consulta desses dados. (get/post/put/delete)
- Testes de integração cobrindo os principais cenários.
---

## Como rodar o projeto

### Pré-requisitos

- Java 17+
- Maven 3.8+ ou Gradle
- IDE (IntelliJ, VS Code, Eclipse etc.)

### 1. Clone o repositório

```bash
git clone https://github.com/luisfernandogdutra/movieapi
cd seu-repo
```
### 2. Execute a aplicação
Via Maven:
```bash
./mvnw spring-boot:run
```
### 3. Executar os testes de integração

O projeto já inclui testes de integração para validar os cálculos de intervalo de prêmios.

Com Maven:
```bash
./mvnw test
```


## Rodando com Docker

### 1. Construa a imagem Docker

No terminal, dentro da pasta do projeto:

```bash
docker build -t movie-awards-api .
```

### 2. Execute o container
```bash
 docker run -p 8080:8080 movie-awards-api
 ```
A API estará acessível em http://localhost:8080.# movieapi
