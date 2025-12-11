# ================================
# ===== Stage 1: BUILD APP =======
# ================================
FROM eclipse-temurin:17-jdk AS build

# Directorio donde se trabajará
WORKDIR /app

# --- Copiar solo los archivos de Gradle ---
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .

# --- Permisos para el wrapper ---
RUN chmod +x gradlew

# --- Descargar dependencias (CACHE) ---
RUN ./gradlew dependencies --no-daemon

# --- Copiar el código fuente por separado ---
COPY src ./src

# --- Compilar el proyecto y generar el JAR ---
RUN ./gradlew bootJar --no-daemon


# ================================
# ===== Stage 2: RUNTIME =========
# ================================
FROM mcr.microsoft.com/playwright:v1.57.0-noble

# Instalar OpenJDK 17 (JRE) necesario para correr Spring Boot
RUN apt-get update && \
    apt-get install -y openjdk-17-jre-headless && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# --- Copiar SOLO el JAR ya construido ---
COPY --from=build /app/build/libs/*.jar app.jar

# Puerto expuesto (Spring Boot usual)
EXPOSE 8080

# Variables de entorno para Java y Playwright
ENV JAVA_TOOL_OPTIONS="--add-opens=java.base/java.io=ALL-UNNAMED"

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]