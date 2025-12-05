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
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Estos son los paquetes de Ubuntu correspondientes a las librerías que faltan
RUN apt-get update && apt-get install -y \
    libglib2.0-0t64\
    libnspr4 \
    libnss3 \
    libdbus-1-3 \
    libatk1.0-0t64 \
    libatk-bridge2.0-0t64 \
    libatspi2.0-0t64 \
    libx11-6 \
    libxcomposite1 \
    libxdamage1 \
    libxext6 \
    libxfixes3 \
    libxrandr2 \
    libgbm1 \
    libxcb1 \
    libxkbcommon0 \
    libasound2t64 \
    && rm -rf /var/lib/apt/lists/*

# Creamos una carpeta temporal para instalar los navegadores
ENV PLAYWRIGHT_BROWSERS_PATH=/app/pw-browsers
RUN mkdir -p $PLAYWRIGHT_BROWSERS_PATH

# --- Copiar SOLO el JAR ya construido ---
COPY --from=build /app/build/libs/*.jar app.jar

# Puerto expuesto (Spring Boot usual)
EXPOSE 8080

# Variables de entorno para Java y Playwright
ENV JAVA_TOOL_OPTIONS="--add-opens=java.base/java.io=ALL-UNNAMED"

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]