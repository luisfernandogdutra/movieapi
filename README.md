# Movie Awards API

API para an�lise de intervalos de pr�mios entre produtores de filmes vencedores. Desenvolvida com Java e Spring Boot.

---

## Funcionalidades

- Importa��o de filmes via CSV. (basta alterarmo arquivo src/main/resources/movielist.csv)
- C�lculo do produtor com o **menor** e **maior** intervalo entre vit�rias. (/movies/listMinAndMax)
- Endpoints REST para consulta desses dados. (get/post/put/delete)
- Testes de integra��o cobrindo os principais cen�rios.
---

## Como rodar o projeto

### Pr�-requisitos

- Java 17+
- Maven 3.8+ ou Gradle
- IDE (IntelliJ, VS Code, Eclipse etc.)

### 1. Clone o reposit�rio

```bash
git clone https://github.com/luisfernandogdutra/movieapi
cd seu-repo
```
### 2. Execute a aplica��o
Via Maven:
```bash
./mvnw spring-boot:run
```
### 3. Executar os testes de integra��o

O projeto j� inclui testes de integra��o para validar os c�lculos de intervalo de pr�mios.

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
A API estar� acess�vel em http://localhost:8080.# movieapi
