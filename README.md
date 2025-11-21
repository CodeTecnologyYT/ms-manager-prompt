# MS Manager Prompt

**MS Manager Prompt** es un microservicio reactivo diseñado para la orquestación, gestión y persistencia de interacciones con modelos de Inteligencia Artificial. Este servicio actúa como un núcleo central para administrar chats, historiales, contextos de prompts y funcionalidades colaborativas como exportación a PDF y envío de correos.

El proyecto está construido siguiendo los principios de la **Arquitectura Hexagonal (Puertos y Adaptadores)**, garantizando un desacoplamiento entre la lógica de negocio, la infraestructura y la interfaz web.

---

## 🚀 Tecnologías Principales

Este proyecto utiliza un stack tecnológico moderno basado en el ecosistema Java y Spring:

*   **Lenguaje:** Java 17 SDK
*   **Framework:** Spring Boot (con Spring WebFlux para programación reactiva)
*   **Base de Datos:** Spring Data MongoDB (Reactive)
*   **Especificaciones:** Jakarta EE
*   **Herramientas de Build:** Gradle
*   **Utilidades:** Lombok
*   **Motores de Plantillas:** FreeMarker (para generación de Markdown/HTML)
*   **Reportes:** JasperReports (para generación de PDFs)
*   **Documentación API:** OpenAPI (Swagger)

---

## 🏛️ Arquitectura

El proyecto sigue una estructura de **Arquitectura Hexagonal**, organizada por dominios funcionales (`auth`, `chat`, `report`, `settings`).

### Estructura del Dominio `chat` (Ejemplo)
La lógica principal reside en `src/main/java/com/kaust/ms/manager/prompt/chat`:

*   **application/usecases**: Contiene la lógica de negocio pura (ej. `GeneratePromptUseCase`, `ProcessChatMessageStreamUseCase`). Implementan interfaces de entrada.
*   **domain**:
    *   **models**: Entidades del dominio agnósticas a la infraestructura.
    *   **ports**: Interfaces que definen contratos para adaptadores de salida (Repositorios, Clientes API, etc.).
*   **infrastructure**: Implementaciones técnicas.
    *   **mongodb**: Adaptadores para la persistencia de datos.
    *   **web**: Controladores REST/Stream (ej. `StreamController`).
    *   **freemarker**: Adaptadores para renderizado de plantillas.
    *   **ia**: Integraciones con proveedores de IA.

---

## ✨ Funcionalidades Clave

### 1. Gestión de Chat y Contexto
*   **Streaming de Respuestas:** Manejo de flujos de datos en tiempo real desde modelos de IA (`StreamController`, `ProcessChatMessageStreamUseCase`).
*   **Historial y Organización:** CRUD completo de Chats y Carpetas (`FolderController`).
*   **Búsqueda:** Capacidades de búsqueda sobre chats y prompts previos.
*   **Generación de Grafos:** Análisis de entidades y relaciones (`GenerateGraphEntitiesUseCase`).

### 2. Integración RAG (Retrieval-Augmented Generation)
*   Soporte para chat con contexto enriquecido mediante recuperación de información (`ReactiveRagChatPort`).
*   Generación dinámica de vistas de contexto en Markdown.

### 3. Colaboración y Exportación
*   **Exportación a PDF:** Generación de reportes de chats completos o mensajes individuales (`GeneratePDFSharingChatUseCase`).
*   **Compartir por Email:** Envío de conversaciones renderizadas vía correo electrónico (`SendMailSharingChatUseCase`), utilizando plantillas HTML.

### 4. Configuración y Ajustes
*   Gestión de modelos de IA y configuraciones del sistema (`SettingsController`).

---

## 🛠️ Configuración del Proyecto

El servicio se configura a través de múltiples archivos de propiedades ubicados en `src/main/resources`:

*   `application.properties`: Configuración general de Spring Boot.
*   `database.properties`: Cadenas de conexión a MongoDB.
*   `ai.properties`: Keys y endpoints para los servicios de IA.
*   `mail.properties`: Configuración SMTP para el envío de correos.
*   `firebase.properties`: Integraciones con Firebase (si aplica para notificaciones/auth).

---

## 📦 Instalación y Ejecución

### Prerrequisitos
* JDK 17 instalado. 
* Instancia de MongoDB corriendo (local o clúster). 
* Variables de entorno configuradas para las claves de API (IA, Base de datos, Correo).
* Google SDK instalado [link de instalacion](https://docs.cloud.google.com/sdk/docs/install?hl=es-419)

### Comandos Gradle
```shell
bash ./gradlew clean build
```
**Ejecutar la aplicación:**
```shell
bash ./gradlew 
```
**Ejecutar tests:**
```shell
bash ./gradlew test
```


---

## 📝 Plantillas y Recursos

El proyecto utiliza recursos externos para dar formato a la salida:

*   **Plantillas de Correo:** `resources/templates/mails/` (HTML).
*   **Plantillas Markdown:** `resources/templates/markdown/context-view.md` (Estructura de contexto para la IA).
*   **Reportes:** `resources/reports` (Definiciones JasperReports).

---

## 🤝 Contribución

1.  Haz un Fork del repositorio.
2.  Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`).
3.  Asegúrate de seguir la estructura de paquetes Hexagonal.
4.  Haz Commit de tus cambios.
5.  Abre un Pull Request.

---

© 2025 Kaust - MS Manager Prompt