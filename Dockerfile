FROM eclipse-temurin:21-jre
WORKDIR /app

RUN useradd --system --create-home appuser

COPY --from=build /app/jogo/target/jogo-0.0.1-SNAPSHOT.war /app/app.war

RUN chown appuser:appuser /app/app.war

USER appuser

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.war"]