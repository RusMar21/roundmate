FROM eclipse-temurin:25-jdk-noble AS builder

WORKDIR /workspace

COPY . .

RUN chmod +x gradlew

RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:25-jdk-noble AS runtime

WORKDIR /app

COPY --from=builder /workspace/build/libs/*.jar app.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
