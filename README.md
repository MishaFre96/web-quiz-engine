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
