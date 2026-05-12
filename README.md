# Ms_Usuario - Sanos y Salvos

Microservicio encargado de la gestión de perfiles y registro de usuarios para la plataforma **Sanos y Salvos**. Este componente centraliza la información de los ciudadanos e instituciones que utilizan el sistema para reportar mascotas perdidas o encontradas.

---

##  Funcionalidades Principales
*Registro de Usuarios:** Creación de nuevas cuentas en la plataforma con validación de datos.
*Gestión de Perfiles:** Almacenamiento seguro de información personal y de contacto.
*Integración de Autenticación:** Actúa en conjunto con el microservicio de Auth (Ms_Auth) para proveer los datos necesarios durante el inicio de sesión.
*Base de Datos Independiente:** Mantiene la información de los usuarios aislada del resto del sistema, cumpliendo con los principios de diseño de microservicios.

---

# Stack Tecnológico
*Framework:** Spring Boot (Java)
*Gestor de Dependencias:** Maven
*Base de Datos:** MySQL
*Arquitectura:** Diseño basado en Microservicios
*ORM:** Spring Data JPA / Hibernate

---

# Configuración y Ejecución local

# Requisitos previos
* Java Development Kit (JDK) 17 o superior.
* Maven instalado.
* Servidor MySQL corriendo localmente.
