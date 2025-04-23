# 📁 ProjectingAPI

**ProjectingAPI** é uma aplicação RESTful desenvolvida com **Spring Boot**, voltada para o gerenciamento de projetos, tarefas e usuários, com controle de autenticação.
 
## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **MySQL**
- **JWT (JSON Web Token)** para autenticação
- **Swagger UI** para documentação da API

## ⚙️ Configuração do Ambiente

### 2. Configuração do Banco de Dados

Crie um banco de dados no MySQL:

```sql
CREATE DATABASE projecting_api;
```

Configure o `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/projecting_api
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 3. Variável de Ambiente JWT_SECRET

Para que o JWT funcione corretamente, você precisa definir a variável de ambiente `JWT_SECRET`. Exemplo em sistemas Unix/Linux/Mac:

```bash
export JWT_SECRET=sua_chave_secreta_aqui
```

No Windows (CMD):

```cmd
set JWT_SECRET=sua_chave_secreta_aqui
```

Ou você pode definir diretamente no `application.properties` (não recomendado para produção):

```properties
jwt.secret=sua_chave_secreta_aqui
```

## ▶️ Executando o Projeto

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/projecting-api.git
cd projecting-api
```

### 2. Compile e rode

```bash
./mvnw spring-boot:run
```

## 📡 Acessando a API

A API estará disponível em:

```
http://localhost:8080/api
```

A documentação interativa (Swagger UI) estará disponível em:

```
http://localhost:8080/swagger-ui.html
```

## 🧪 Testando a API

Você pode utilizar ferramentas como:

- [Postman](https://www.postman.com/)
- [Insomnia](https://insomnia.rest/)
- Ou diretamente pelo Swagger UI

## 👨‍💻 Contribuição

Sinta-se à vontade para contribuir com sugestões, melhorias e correções. Basta fazer um fork do projeto e abrir um Pull Request. 😉
