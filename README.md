# CRUD App - Spring Boot + Vue.js

Live demo (Vercel): https://crud-cohan.vercel.app

Aplicación CRUD completa para Person, Student, Professor y Address con:
- Backend: Java 17, Spring Boot 3, Spring MVC (REST), Spring WS (SOAP), Spring Security, JPA/Hibernate
- Frontend: Vue 3 + Vite + Bootstrap 5
- DB: MySQL (Docker y Railway). En local puedes usar H2/MySQL
- Despliegues: Docker Compose (local) y Vercel (frontend) + Railway (backend)

Incluye autenticación (form login DEV y OAuth2 GitHub), datos seed (Docker), pruebas, validaciones, estilos y utilidades para diagnóstico.

---

## Endpoints principales
- REST: `/api/persons`, `/api/students`, `/api/professors`, `/api/addresses`
- SOAP: `/ws` (contratos bajo el namespace `http://example.com/crudapp/soap`)
- Auth/diagnóstico: `/login`, `/perform_login`, `/logout`, `/oauth2/authorization/github`, `/api/auth/debug`, `/api/health/db`

---

## Cómo ejecutar

### Opción A: Docker Compose (recomendado para demo local)
Prerequisitos: Docker y Docker Compose.

1) Construir y levantar

```powershell
cd crud-app
docker compose up -d --build
```

Servicios expuestos:
- Frontend (SPA + proxy): http://localhost:5173
  - API/Auth por el mismo origen: http://localhost:5173/api, /login, /oauth2, etc.
- MySQL: 3306 (usuario: appuser, contraseña: apppass, BD: crudapp)
- Adminer (DB Admin): http://localhost:8081 (Server: db, User: appuser, Pass: apppass, DB: crudapp)

Datos iniciales (seed): `src/main/resources/data-docker.sql` se ejecuta automáticamente

Detener y limpiar
```powershell
docker compose down
docker compose down -v   # borra la base (volumen)
```

OAuth2 (GitHub) en Docker — opcional
1) Crea un OAuth App en GitHub (DEV) con callback EXACTO: `http://localhost:5173/login/oauth2/code/github`
2) Copia `.env.example` a `.env` en la raíz y completa:
   - `OAUTH2_ENABLED=true`
   - `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GITHUB_CLIENT_ID=...`
   - `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GITHUB_CLIENT_SECRET=...`
3) Reconstruye/levanta backend
```powershell
docker compose build backend ; docker compose up -d backend
```
4) Verifica en `http://localhost:5173/api/auth/debug` que aparece tu `clientId` y `redirectUriTemplate`

### Opción B: Desarrollo local (sin Docker)
Backend
```powershell
cd crud-app
mvn spring-boot:run
```
- REST: http://localhost:8080/api/
- SOAP: http://localhost:8080/ws
- H2 Console: http://localhost:8080/h2-console (JDBC: jdbc:h2:mem:testdb)

Frontend
```powershell
cd crud-app/frontend
npm install
npm run dev
```
- SPA: http://localhost:5173

Para usar MySQL en local, ejecuta con el perfil `mysql-local` y configura `application-mysql-local.properties`.

### Opción C: Cloud (Vercel + Railway)
Objetivo: mismo comportamiento que Docker (mismo origen) usando rewrites.

1) Railway (backend)
- Variables:
  - `SPRING_PROFILES_ACTIVE=railway`
  - DB: `MYSQL_URL` (o `MYSQLHOST`/`MYSQLPORT`/`MYSQLDATABASE`/`MYSQLUSER`/`MYSQLPASSWORD`)
  - `APP_FRONTEND_URL=https://crud-cohan.vercel.app`
  - OAuth2 (opcional): `GITHUB_CLIENT_ID` / `GITHUB_CLIENT_SECRET`
- Cookies cross-site y headers forward ya están configurados en el perfil `railway`.
- Salud DB: `https://<tu-backend>.railway.app/api/health/db`

2) Vercel (frontend)
- El proyecto incluye `frontend/vercel.json` con rewrites para `/api`, `/login`, `/perform_login`, `/logout`, `/oauth2/*`, `/login/oauth2/*` hacia Railway.
- VITE_API_URL está configurado a `/api` para usar mismo origen (evita bloqueos de cookies en incognito).
- Despliega y abre: `https://crud-cohan.vercel.app`

3) GitHub OAuth en producción (Vercel)
- En tu OAuth App de GitHub (PROD):
  - Homepage URL: `https://crud-cohan.vercel.app`
  - Authorization callback URL: `https://crud-cohan.vercel.app/login/oauth2/code/github`
- Si ves “redirect_uri is not associated…”, revisa que el callback sea EXACTO.

---

## Cómo probar la API

### REST (ejemplos)
Person (crear)
```bash
curl -X POST http://localhost:8080/api/persons -H "Content-Type: application/json" -d '{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "1234567890",
  "address": { "street": "Main St", "city": "Springfield" }
}'
```
Listar
```bash
curl http://localhost:8080/api/persons
```
En Vercel (mismo origen): `https://crud-cohan.vercel.app/api/persons`

### SOAP
- Endpoint: `/ws`
- Ejemplo: GetAllPersons
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:crud="http://example.com/crudapp/soap">
  <soapenv:Header/>
  <soapenv:Body>
    <crud:GetAllPersonsRequest/>
  </soapenv:Body>
</soapenv:Envelope>
```
- Cómo testear: Postman o SoapUI → POST a `/ws` con `Content-Type: text/xml`.

---

## Autenticación y Seguridad
- Página de login única en el backend (`/login`), con:
  - Form login DEV: usuario `dev` / password `dev123` (configurable, desactivable en PROD)
  - Botón “Continuar con GitHub” (OAuth2)
- La SPA redirige automáticamente a `/login` si el usuario no está autenticado.
- En Docker y Vercel se usa “mismo origen” (same-origin) para API/Auth, lo que asegura el funcionamiento de cookies de sesión incluso en incógnito.
- Endpoints abiertos para diagnóstico: `/api/auth/debug`, `/api/health/db`

---

## Salud y Diagnóstico
- `GET /api/health/db`: SELECT 1 contra la base (UP/DOWN)
- `GET /api/auth/debug`: muestra configuración activa de OAuth2 (clientId, redirectUriTemplate)

---

## Requisitos – Cómo se cumplen
Requisitos indicados:
1) No es necesario implementar los métodos descritos en el diagrama
   - Se implementó CRUD sobre Person y entidades relacionadas con enfoque práctico.
2) El CRUD se debe realizar sobre la entidad Person
   - Implementado: Person con herencia (Student, Professor) y relación con Address.
3) El CRUD debe quedar expuesto como servicio web SOAP, REST o ambos
   - Implementado ambos: REST bajo `/api/*` y SOAP bajo `/ws`.
4) Preferiblemente implementar la solución en JAVA
   - Implementado en Java 17 con Spring Boot 3.
5) Preferiblemente usar Hibernate o JPA como ORM
   - Implementado con Spring Data JPA (Hibernate).
6) Se puede usar cualquier framework en el back end
   - Se usa Spring Boot (MVC, WS, Security).
7) Se puede usar cualquier motor de base de datos relacional
   - MySQL en Docker y Railway; soporte H2 en local.
8) Se puede usar cualquier librería para la visualización en HTML
   - Vue 3 + Bootstrap 5.
9) Se puede usar cualquier servidor de aplicaciones
   - Empaquetado como Spring Boot (embedded Tomcat), desplegable en contenedores.

Opcionales (resueltos):
- Estilos: interfaz con Bootstrap y componentes limpios.
- Usabilidad: SPA por tabs, modal de dirección, validaciones.
- Validaciones: Bean Validation en entidades.
- Logging: logs estándar de Spring + perfiles.
- Testing: JUnit 5 + MockMvc para controladores (ver `surefire-reports`).
- Seguridad: Spring Security (form login + OAuth2 GitHub), Basic para Postman, CORS y cookies configuradas por entorno.
- CRUD para todas las entidades: Person/Student/Professor/Address expuestos por REST; consultas SOAP generales.

---

## Troubleshooting
- Docker: si el backend no arranca por DB, revisa que `crudapp-mysql` esté healthy.
- Vercel: si `/login` devuelve 404, redeploy para aplicar rewrites; asegúrate que Railway está arriba.
- GitHub OAuth: error “redirect_uri is not associated…” indica callback mal configurado (ver sección Cloud).
- Incógnito: si la sesión no persiste, verifica que la SPA use mismo origen (`/api`), ya configurado en `frontend/vercel.json`.

---

## Testing (backend)
```powershell
cd crud-app
mvn test
```
Los tests cubren los controladores REST; puedes extenderlos a servicios/reglas de negocio.

---

## Notas finales
- La demo pública está en: https://crud-cohan.vercel.app
- El backend en Railway utiliza el perfil `railway` con cookies seguras y forward headers para redirecciones correctas.
- En Docker, Nginx del frontend proxyea API/Auth para mantener same-origin y simplificar la seguridad.

