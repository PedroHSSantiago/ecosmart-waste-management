# =============================================================================
# Stage 1: Build & Execução dos Testes Automatizados (Maven + JDK 17)
# =============================================================================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o arquivo de configuração de dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte da aplicação e da suíte de testes (Cucumber + RestAssured + JSON Schema)
COPY src ./src

# Executa todos os testes automatizados BDD/API/Contrato e gera o pacote JAR
RUN mvn clean package -B

# =============================================================================
# Stage 2: Runtime enxuto e eficiente energeticamente (Pilar Ambiental ESG)
# =============================================================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Cria usuário não-root por boas práticas de governança e segurança
RUN addgroup -S ecosmart && adduser -S ecosmart -G ecosmart
USER ecosmart:ecosmart

COPY --from=build /app/target/ecosmart-waste-management-1.0.0.jar app.jar

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=5s --start-period=15s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
