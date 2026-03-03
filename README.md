# Hotels Finder – Backend API

Spring Boot REST API for the Hotels Finder app. Use with the frontend repo.

## Stack

- Java 17, Spring Boot 3, Spring Data JPA
- MySQL (or H2 for local dev)
- WhatsApp Business API (optional) for booking confirmations

## Run locally

**With MySQL** (create DB `reactlogin` first):

```bash
./mvnw.cmd spring-boot:run
```

**Without MySQL** (H2 in-memory):

```bash
./mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=h2"
```

API: http://localhost:8080

## Deploy (Railway)

1. New Project, Deploy from GitHub, select this repo.
2. Add MySQL; set Variables: SPRING_PROFILES_ACTIVE=railway, SPRING_DATASOURCE_URL, USERNAME, PASSWORD, APP_CORS_ALLOWED_ORIGINS (your frontend URL).
3. Generate domain; use that URL as the frontend API_URL.

## Endpoints

- POST /users/save, POST /users/login, GET /users/getAll, PUT /users/{id}, PATCH /users/{id}/role
- GET/POST /hotels/*, GET/POST/PUT/DELETE /bookings/*

First user to register is ADMIN.
