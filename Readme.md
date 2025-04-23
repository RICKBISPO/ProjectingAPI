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
