**Sistema completo para gerenciamento de um zoológico, incluindo cadastro de animais, tipos de cuidado e cuidados realizados em animais.
O projeto é composto por:**

- Backend: Spring Boot (Java 17)

- Frontend: React + Vite

- Banco de Dados: SQL Server

- Containerização: Docker + Docker Compose

**Como executar o sistema**:

Antes de iniciar, instale:

- Docker Desktop

- Docker Compose (vem junto)

- Opcional: Java 17 e Node 18+ caso queira rodar fora do Docker

**Executando com Docker:**

- Entre na pasta raiz do projeto: cd zoo-management-system

- Suba todos os serviços (backend, frontend e banco): docker compose up --build

**Após os containers inicializarem, acesse:**

- Frontend: http://localhost

- API: http://localhost:8080

- Banco de dados:

   Host: localhost
  
   Porta: 1433
  
   Usuário: sa
  
   Senha: 220571
  
   Banco: ZooDB

**Executando Backend sem Docker:**

  - cd backend
    
  - mvn spring-boot:run

  - A API ficará disponível em: http://localhost:8080
    
**Executando Frontend sem Docker:**

 - cd frontend

 - npm install

 - npm run dev
 
 - Acesse: http://localhost:5173

**Tecnologias Utilizadas**

**Backend:**

- Java 17

- Spring Boot

- Spring Data JPA

- SQL Server

- Maven

**Frontend:**

- React

- Vite

- Axios

- **Infraestrutura**

- Docker

- Docker Compose

- SQL Server Express
