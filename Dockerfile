# Estágio 1: Compilar o projeto
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /jogo

COPY pom.xml .
COPY src ./src
COPY lib ./lib

RUN mvn clean package -DskipTests


# Estágio 2: Executar a aplicação
FROM eclipse-temurin:21-jre

WORKDIR /app

RUN useradd --system --create-home appuser

COPY --from=build /jogo/target/jogo-0.0.1-SNAPSHOT.war /app/app.war

RUN chown appuser:appuser /app/app.war

USER appuser

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.war"]