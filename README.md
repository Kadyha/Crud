## Pruebas automatizadas (JUnit + Postman/Newman)

La rama `main` ejecuta un workflow de GitHub Actions que:
- Construye el proyecto con JDK 17 y Maven (`mvn test`).
- Levanta MySQL 8 como servicio de CI.
- Empaqueta la app y la arranca con el perfil `mysql-local`.
- Ejecuta las colecciones de Postman (REST y SOAP) con Newman y publica reportes HTML como artefactos.

Estado: ![CI](https://github.com/Kadyha/crud/actions/workflows/ci.yml/badge.svg)

Artefactos de CI: `newman/report-rest.html` y `newman/report-soap.html`.

Cómo correr localmente:
- JUnit/MockMvc: `mvn test`
- API con Newman: ver `newman/README.md` (requiere backend arriba en `http://localhost:8080`).
# CRUD App - Spring Boot + Vue.js

Live demo (Vercel): https://crud-cohan.vercel.app

Aplicación CRUD completa para Person, Student, Professor y Address con:
- Backend: Java 17, Spring Boot 3, Spring MVC (REST), Spring WS (SOAP), Spring Security, JPA/Hibernate
- Frontend: Vue 3 + Vite + Bootstrap 5
- DB: MySQL (Docker y Railway). En local puedes usar H2/MySQL
- Despliegues: Docker Compose (local) y Vercel (frontend) + Railway (backend)

Incluye autenticación (form login DEV y OAuth2 GitHub), datos seed (Docker), pruebas, validaciones, estilos y utilidades para diagnóstico.

---

## Colecciones de Postman (para probar servicios)

- Ubicación: `documentation/postman/`
   - CRUD REST: `Crud-App.postman_collection.json`
   - SOAP: `Crud-App-SOAP.postman_collection.json`
   - Environments: `environments/Crud-App-Local.postman_environment.json` y `environments/Crud-App-Cloud.postman_environment.json`
- Configura `baseUrl` según corresponda (localhost:8080 o dominio cloud).
- Para login por formulario utiliza la request “Login (local form)” que publica a `/perform_login`.

---

## Guía rápida para evaluación (COHAN)

Objetivo: probar en minutos la app en producción y, si se desea, correrla en local con Docker.

1) Producción (recomendado)
   - Abrir: https://crud-cohan.vercel.app
    - Login:
       - Opción 1: “Continuar con GitHub” (OAuth2)
       - Opción 2: formulario (se habilitó un usuario de prueba, las credenciales fueron enviadas al correo de)
   - Verificar:
     - Pestañas Persons / Students / Professors (CRUD)
     - Crear/editar un registro y ver la lista actualizada
     - Salud DB: https://crud-cohan.vercel.app/api/health/db → {"status":"UP"}
     - Depuración OAuth: https://crud-cohan.vercel.app/api/auth/debug (muestra baseUrl y redirect)

2) ¿Cómo funciona (1 párrafo)?
   - Frontend (Vercel) llama a “/api” y rutas de auth en el mismo origen; Vercel reescribe esas rutas al backend (Railway). Así las cookies de sesión funcionan en todos los navegadores y no hay CORS. El backend expone REST/SPA/SOAP y persiste en MySQL.

3) Opcional: ejecutar en local con Docker (1 comando)
   - Requisitos: Docker Desktop
   - Levantar todo:
     ```powershell
     docker compose up -d --build
     ```
   - Abrir: http://localhost:5173
   - DB Admin (opcional): http://localhost:8081 (Server=db, User=appuser, Pass=apppass, DB=crudapp)

4) Qué evalúa esta prueba (checklist)
   - CRUD completo (Person, Student, Professor, Address) por REST y SOAP
   - Seguridad: form login + OAuth2 GitHub; logout; redirecciones correctas
   - Persistencia en MySQL (Railway / Docker), esquema con JPA
   - Observabilidad básica: /api/health/db y /api/auth/debug
   - Front responsivo y simple (Vue + Bootstrap)

5) Enlaces útiles
   - REST: https://crud-cohan.vercel.app/api/persons (requiere sesión)
   - SOAP: https://crud-cohan.vercel.app/ws (usar Postman/SoapUI)
   - Diagnóstico DB: https://crud-cohan.vercel.app/api/health/db
   - Diagnóstico OAuth: https://crud-cohan.vercel.app/api/auth/debug

---

## Endpoints principales
- REST: `/api/persons`, `/api/students`, `/api/professors`, `/api/addresses`
- SOAP: `/ws` (contratos bajo el namespace `http://example.com/crudapp/soap`)
- Auth/diagnóstico: `/login`, `/perform_login`, `/logout`, `/oauth2/authorization/github`, `/api/auth/debug`, `/api/health/db`

---

## Cómo ejecutar

### Opción A: Docker Compose (recomendado para demo local y para revisión)
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

Qué enviar a revisión (local)
- Este mismo repo con `docker-compose.yml` y `frontend/nginx.conf` ya listos.
- Instrucciones rápidas:
   - `docker compose up -d --build`
   - Abrir http://localhost:5173
   - Login por GitHub si configuran el OAuth local (callback a localhost:5173)
   - Adminer: http://localhost:8081

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

### Pruebas con Postman (colecciones incluidas)

- Colección REST: `documentation/postman/Crud-App.postman_collection.json`
- Colección SOAP: `documentation/postman/Crud-App-SOAP.postman_collection.json`
- Environments:
   - Local: `documentation/postman/environments/Crud-App-Local.postman_environment.json`
   - Cloud: `documentation/postman/environments/Crud-App-Cloud.postman_environment.json`

Pasos:
1) Importa la colección y el environment en Postman.
2) Ajusta `baseUrl` si es necesario.
3) Usa la petición “Login (local form)” que POSTea a `/perform_login`. Postman gestionará la cookie de sesión.
4) Ejecuta el folder de endpoints para validar CRUD.

### Opción C: Cloud (Vercel + Railway)
Objetivo: mismo comportamiento que Docker (mismo origen) usando rewrites.

1) Railway (backend)
- Variables:
  - `SPRING_PROFILES_ACTIVE=railway`
   - DB: usar `SPRING_DATASOURCE_URL/USERNAME/PASSWORD` (proxy público) o enlazar el recurso MySQL y dejar que el perfil `railway` lea `MYSQLHOST/MYSQLPORT/...`.
      - Ejemplo proxy público: `SPRING_DATASOURCE_URL=jdbc:mysql://<host>:<port>/<db>?sslMode=REQUIRED&allowPublicKeyRetrieval=true&serverTimezone=UTC`
      - `SPRING_DATASOURCE_USERNAME=root`, `SPRING_DATASOURCE_PASSWORD=<pass>`
  - `APP_FRONTEND_URL=https://crud-cohan.vercel.app`
  - OAuth2 (opcional): `GITHUB_CLIENT_ID` / `GITHUB_CLIENT_SECRET`
- Cookies cross-site y headers forward ya están configurados en el perfil `railway`.
- Salud DB: `https://<tu-backend>.railway.app/api/health/db`

2) Vercel (frontend)
- El proyecto incluye `frontend/vercel.json` con rewrites para `/api`, `/login`, `/perform_login`, `/logout`, `/oauth2/*`, `/login/oauth2/*` hacia Railway.
- Variables en Vercel:
   - `VITE_API_URL=/api` (misma-origin). No pongas secretos de GitHub aquí.
   - (opcional) `VITE_AUTH_PROVIDER=oauth`.
   - Redeploy para aplicar.
- Despliega y abre: `https://crud-cohan.vercel.app`

3) GitHub OAuth en producción (Vercel)
- En tu OAuth App de GitHub (PROD):
  - Homepage URL: `https://crud-cohan.vercel.app`
  - Authorization callback URL: `https://crud-cohan.vercel.app/login/oauth2/code/github`
- Si ves “redirect_uri is not associated…”, revisa que el callback sea EXACTO.
 - En Railway (backend): `GITHUB_CLIENT_ID/SECRET` y (si hiciera falta forzar) `SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GITHUB_REDIRECT_URI=https://crud-cohan.vercel.app/login/oauth2/code/github`.
 - Verifica en `https://crud-cohan.vercel.app/api/auth/debug`:
    - `detectedBaseUrl` debe ser el dominio de Vercel.
    - `resolvedRedirectUri` debe apuntar a `/login/oauth2/code/github` en Vercel.
 - Prueba el botón “GitHub”.

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
- Página de login única en el backend (`/login`), con form login y botón “Continuar con GitHub” (OAuth2).
- El usuario en memoria para DEV está deshabilitado por defecto y no se publica ninguna credencial en este repo. Para habilitarlo en un entorno controlado, usa variables de entorno: `APP_SECURITY_DEV_USER_ENABLED=true`, `APP_SECURITY_USER_NAME`, `APP_SECURITY_USER_PASSWORD`.
- La SPA redirige automáticamente a `/login` si el usuario no está autenticado.
- En Docker y Vercel se usa “mismo origen” (same-origin) para API/Auth, lo que asegura el funcionamiento de cookies de sesión incluso en incógnito.
- Endpoints abiertos para diagnóstico: `/api/auth/debug`, `/api/health/db`

---

## Salud y Diagnóstico
- `GET /api/health/db`: SELECT 1 contra la base (UP/DOWN)
- `GET /api/auth/debug`: muestra configuración activa de OAuth2 (clientId, redirectUriTemplate)
 - `GET /api/debug/db`: muestra el JDBC URL y usuario efectivos (sanitizados) — útil para Railway.

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

