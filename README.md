# RoundMate

Краткое описание проекта.

## Technology stack

- Kotlin
- Java 25
- Spring Boot 4.1.1
- Spring Modulith
- gRPC
- PostgreSQL 18
- Liquibase
- Gradle
- Docker Compose
- Testcontainers

## Requirements

- Java 25
- Docker Desktop
- Git

## Run with Docker Compose
```bash
docker compose up -d --build
```

## Check container status
```bash
docker compose ps
```

## View application logs
```bash
docker compose logs -f roundmate
```

## Stop the application
```bash
docker compose down
```

## Run application locally

Сначала поднять PostgreSQL:
```bash
docker compose up -d postgres
```

Загрузить переменные окружения:
```bash
set -a
source .env
set +a
```

Запустить приложение:
```bash
./gradlew bootRun
```
## Run checks
```bash
./gradlew check
```