# 🏢 HomeRentSolution - Microservicio de Propiedades (ms-propiedades)

Este microservicio se encarga de gestionar de forma aislada toda la lógica de negocio, persistencia y validaciones relacionadas con las propiedades de alquiler dentro del ecosistema de HomeRentSolution.

---

## 🛠️ Tecnologías y Requisitos

* **Java:** 25
* **Framework:** Spring Boot 4.0.6
* **Base de Datos:** MySQL 8.x (vía Spring Data JPA)
* **Comunicación:** Spring Cloud OpenFeign (Valida la existencia del anfitrión llamando a `ms-anfitriones`)
* **Documentación:** Springdoc OpenAPI v2 (Swagger)

---

## ⚙️ Configuración del Entorno (`application.yml`)

El servicio cuenta con una arquitectura de perfiles dinámicos:
* **Perfil `dev` (Desarrollo):** Conectado a la base de datos `db_propiedades_dev` y documentación activa.
* **Perfil `test` (Pruebas):** Conectado a `db_propiedades_test` con limpieza absoluta (`create-drop`) y Swagger desactivado.

### Puerto de Escucha:
* **Local:** `http://localhost:8081`

---

## 🚀 Instalación y Despliegue Local

1. Asegúrate de tener creada la base de datos en tu MySQL local: `db_propiedades_dev`.
