# jogo-service

Catálogo de jogos do Critical Hit: cadastro, busca, atualização, habilitação para venda e exclusão lógica.

Porta **8081** · Banco `critical_hit`, schema `jogo` · Consumido pela `loja-service`.

## Links

- Swagger UI: http://localhost:8081/swagger-ui/index.html
- OpenAPI: http://localhost:8081/v3/api-docs
- Health: http://localhost:8081/actuator/health
- README da lib: [../lib/README.md](../lib/README.md)

## Como executar

1. Instale a lib: na pasta dela, `mvn clean install`.
2. Suba o Postgres: `docker compose up -d postgres`.
3. Defina o banco e rode:

```powershell
$env:DB_USER = "postgres"
$env:DB_PASSWORD = "postgres"
.\mvnw.cmd spring-boot:run
```

Git Bash: `export DB_USER=postgres DB_PASSWORD=postgres` e `./mvnw spring-boot:run`.

## Configuração

| Variável | Padrão |
|---|---|
| `SERVER_PORT` | `8081` |
| `DB_URL` | `jdbc:postgresql://localhost:5435/critical_hit?currentSchema=jogo` |
| `DB_USER` | `jogo_user` |
| `DB_PASSWORD` | sem padrão |

## Endpoints

| Método | Endpoint | Entrada | Saída | Status |
|---|---|---|---|---|
| POST | `/jogos` | `JogoRequest` | `JogoResponse` | 201, 400 |
| GET | `/jogos?nome=&genero=` | - | lista de `JogoResponse` | 200 |
| GET | `/jogos/{id}` | - | `JogoResponse` | 200, 404 |
| PUT | `/jogos/{id}` | `JogoRequest` | `JogoResponse` | 200, 400, 404 |
| DELETE | `/jogos/{id}` | - | - | 204, 404 |
| PATCH | `/jogos/{id}/habilitar` | - | `JogoResponse` | 200, 404 |

- O jogo nasce **indisponível** e só entra no catálogo depois de habilitado.
- `DELETE` é lógico: o jogo some das buscas e fica indisponível.
- Filtros `nome` (contém) e `genero`: opcionais, combináveis, sem diferenciar maiúsculas.

## DTOs (da lib)

| DTO | Campos |
|---|---|
| `JogoRequest` | `nome` (até 100), `genero` (até 50), `descricao` (opcional, até 500), `preco` (positivo, até 2 casas) |
| `JogoResponse` | `id`, `nome`, `genero`, `descricao`, `preco`, `disponivel` |

## Erros

Formato `ErroResponse` (`status`, `mensagem`, `path`, `timestamp`): **400** corpo inválido, **404** jogo inexistente ou excluído, **500** erro inesperado. Requer `@Import(GlobalExceptionHandler.class)` na classe principal.

## Testes

```powershell
.\mvnw.cmd test
```
