# ===== Stage 1: Build =====
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar pom y descargar dependencias (cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código
COPY src ./src

# Empaquetar el JAR
RUN mvn -DskipTests package

# ===== Stage 2: Runtime =====
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiar solo el JAR final desde el build
COPY --from=build /app/target/*.jar app.jar

# Puerto que expone tu app (si usas Spring Boot es 8080)
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]