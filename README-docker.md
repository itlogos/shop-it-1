# Dockerizing `shop-it-d`

This package adds production-ready Dockerfiles for each module and a `docker-compose.yml` with a Postgres database.

## Layout

```
back-end/
  Dockerfile           # Builds and runs the back-end service on port 8081
manager/
  Dockerfile           # Builds and runs the manager (UI/API gateway) on port 8080
.dockerignore
.env                   # Centralized variables for Compose
docker-compose.yml
```

## Prerequisites

- Docker and Docker Compose v2+
- Internet access to pull Maven dependencies and Docker images

## Build & Run

1. Place these files at the root of the project (next to `pom.xml`), preserving the paths:
   - `back-end/Dockerfile`
   - `manager/Dockerfile`
   - `.dockerignore`
   - `.env`
   - `docker-compose.yml`

2. Adjust `.env` if you need different DB credentials.

3. Start the stack:
   ```bash
   docker compose up -d --build
   ```

4. Access services:
   - Manager: http://localhost:8080
   - Back-end (catalogue): http://localhost:8081
   - Postgres: localhost:5432 (DB: `catalogue`, user: `postgres`, password: `admin` by default)

## Notes

- The Spring profile `standalone` is enabled by default in both services. We override DB and service endpoints via environment variables.
- If you add Flyway later, simply enable it (e.g., `spring.flyway.enabled=true`) and provide the correct schema migration scripts.
- JDK 21 images are used to match the project's `pom.xml` (`maven.compiler.source/target=21`). If you change Java version, update images accordingly.
- For production, you may wish to:
  - Switch runtime image to a distroless base.
  - Add resource limits in Compose or run under Kubernetes.
  - Add proper health endpoints and health checks.
