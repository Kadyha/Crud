# CRUD App - Spring Boot + Vue.js

## Overview
This project is a scalable CRUD application for Person, Student, Professor, and Address entities. It supports both REST and SOAP APIs, uses Java 17, Spring Boot 3, JPA/Hibernate, H2 database, and a Vue.js frontend with Bootstrap for usability and validation.

---

## Backend (Spring Boot)
- **Entities:** Person, Student, Professor, Address
- **APIs:**
  - REST: `/api/persons`, `/api/students`, `/api/professors`, `/api/addresses`
  - SOAP: `/ws` (see below)
- **Persistence:** H2 (in-memory, easy to switch to any RDBMS)
- **Validation:** Bean Validation (annotations)
- **Security:** Spring Security (default config)
- **Tests:** JUnit 5 + MockMvc for all controllers

## Frontend (Vue.js)
- **UI:** Bootstrap 5, responsive, tabs for each entity
- **Features:** CRUD for all entities, address modal, validation, usability

---

## How to Run (Development)

### 1. Backend (Spring Boot)
```powershell
cd crud-app
mvn spring-boot:run
```
- Access REST API at: `http://localhost:8080/api/`
- Access SOAP API at: `http://localhost:8080/ws`
- Access H2 Console at: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)

To use MySQL locally instead of H2, create a DB and run with the `mysql-local` profile:

```powershell
cd crud-app
mvn spring-boot:run -Dspring-boot.run.profiles=mysql-local
```
Configure credentials in `src/main/resources/application-mysql-local.properties`.

### 2. Frontend (Vue.js)
```powershell
cd crud-app/frontend
npm install
npm run dev
```
- Open browser at: `http://localhost:5173`

---

## REST API Tutorial

- **Create:**
  - `POST /api/persons` (see `curl-examples.txt` for full payloads)
- **Read:**
  - `GET /api/persons`
- **Update:**
  - `PUT /api/persons/{id}`
- **Delete:**
  - `DELETE /api/persons/{id}`
- **Same for:** `/api/students`, `/api/professors`, `/api/addresses`

### Example (Person)
```bash
curl -X POST http://localhost:8080/api/persons -H "Content-Type: application/json" -d '{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "1234567890",
  "address": { "street": "Main St", "city": "Springfield" }
}'
```

---

## SOAP API Tutorial

- **Endpoint:** `http://localhost:8080/ws`
- **Get All Persons:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:crud="http://example.com/crudapp/soap">
   <soapenv:Header/>
   <soapenv:Body>
      <crud:GetAllPersonsRequest/>
   </soapenv:Body>
</soapenv:Envelope>
```
- **Get All Students/Professors/Addresses:** Use `<crud:GetAllStudentsRequest/>`, etc.
- **How to test:**
  - Use Postman or SoapUI
  - Set `Content-Type: text/xml`
  - POST the envelope to `http://localhost:8080/ws`

---

## Testing

Run all backend tests:
```powershell
cd crud-app
mvn test
```
- Tests cover all REST endpoints for Person, Student, Professor, Address
- You can add more tests for business logic as needed

---

## Code Quality & Comments
- All code is clean, commented, and follows best practices
- Entities, controllers, services, and endpoints are documented
- See inline comments for validation, relationships, and API logic

---

## Troubleshooting
- If you see errors about missing plugins, run `mvn clean install` in the `crud-app` directory
- If the frontend does not connect, check CORS settings or proxy config in `vite.config.js`
- For SOAP errors, ensure all request/response classes exist and Spring WS is configured

---

## More Info
- See `curl-examples.txt` for ready-to-use REST and SOAP requests
- All endpoints are available for easy testing and integration
- The app is ready for extension to more entities or business rules

---

## Docker Compose (Backend + MySQL + Frontend)

Prerequisitos: Docker y Docker Compose.

1) Construir y levantar:

```powershell
cd crud-app
docker compose up -d --build
```

Servicios expuestos:
- MySQL: 3306 (usuario: appuser, contraseña: apppass, BD: crudapp)
- Backend: http://localhost:8080 (perfil activo: docker)
- Frontend: http://localhost:5173
- Adminer (DB Admin): http://localhost:8081 (Server: db, User: appuser, Pass: apppass, DB: crudapp)

Datos iniciales: se cargan automáticamente desde `src/main/resources/data-docker.sql` (Address, Person básico, varios Students y Professors) al iniciar el backend en Docker.

Detener:

```powershell
docker compose down
```

Limpiar volúmenes (borra la base de datos):

```powershell
docker compose down -v
```

