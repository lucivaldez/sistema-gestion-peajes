# sistema-gestion-peajes
Aplicación web desarrollada con Spring Boot que permite gestionar el tránsito vehicular en puestos de peaje, calcular tarifas dinámicas con bonificaciones y notificar eventos relevantes a los usuarios.

---

## 📌 Descripción

El sistema permite registrar vehículos, propietarios y puestos de peaje. A través de la simulación de tránsito, se calculan los costos en base a la categoría del vehículo y se aplican distintas bonificaciones. Además, se actualiza el saldo de los propietarios y se generan notificaciones automáticas.


## 🚀 Funcionalidades

- Registro de vehículos y propietarios
- Gestión de puestos de peaje
- Simulación de tránsito
- Cálculo de tarifas por categoría
- Aplicación de bonificaciones
- Generación de notificaciones automáticas
- Consulta de información de usuarios y vehículos


## 🛠️ Tecnologías

- Java
- Spring Boot
- API REST
- Arquitectura en capas


## 🧠 Arquitectura y Patrones

El sistema está diseñado siguiendo una arquitectura en capas (Controller, Service, Domain) aplicando principios GRASP.

Se implementaron los siguientes patrones de diseño:

- **GRASP Experto:** la lógica se ubica en las clases que poseen la información necesaria.
- **Fachada:** desacopla los controladores de la lógica de negocio.
- **Strategy:** permite aplicar distintos tipos de bonificaciones según el contexto.
- **Observer:** se utiliza para notificar eventos relevantes (por ejemplo, alertas de saldo bajo).
- **State:** gestiona los distintos estados de las entidades del sistema.


## ▶️ Cómo ejecutar el proyecto

Clonar Repositorio:
git clone https://github.com/lucivaldez/sistema-gestion-peajes.git

Ejecutar la aplicación:
mvn spring-boot:run

En el navegador:
http://localhost:8080/index.html
