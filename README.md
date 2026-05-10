# Web Quiz Engine

API REST para crear cuestionarios, resolverlos y consultar el historial de aciertos.  
Incluye registro de usuarios, autenticación Basic Auth con contraseñas cifradas y paginación.  
Proyecto desarrollado como parte del track de **Spring Boot** en [Hyperskill](https://hyperskill.org/).

## 🚀 Funcionalidades principales

- ✅ Registro de usuarios con validación manual de email y contraseña  
- 🔐 Autenticación HTTP Basic con contraseñas codificadas (BCrypt)  
- 📝 CRUD completo de cuestionarios  
- 🧠 Resolución de quizzes con feedback inmediato  
- 📊 Historial de quizzes completados por el usuario  
- 📄 Paginación en todos los listados (10 elementos por página)  
- 🛡️ Borrado de quizzes solo por el autor  
- ⚠️ Manejo centralizado de errores con `@ControllerAdvice`

## 🛠️ Tecnologías

- Java 17+
- Spring Boot (Web, Data JPA, Security, Actuator)
- Spring Security (Basic Auth)
- H2 Database (archivo local)
- Gradle
- JUnit 5

## 📦 Cómo ejecutar

1. Clona el repositorio  
   `git clone https://github.com/tu-usuario/web-quiz-engine.git`

2. Importa el proyecto en IntelliJ IDEA (o tu IDE) como proyecto Gradle

3. Ejecuta `WebQuizEngine` o usa la tarea `bootRun`

4. La API estará disponible en `http://localhost:8889`

> Puedes acceder a la consola H2 en `http://localhost:8889/h2-console`  
> URL: `jdbc:h2:file:../quizdb` | User: `sa` | Password: `password`

## 📮 Ejemplos de uso

### Registrar usuario

`POST /api/register`  
```json
{
  "email": "test@mail.org",
  "password": "strongpassword"
}
