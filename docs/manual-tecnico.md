# Manual Técnico - Proyecto AIR

## Descripción General

El Proyecto AIR corresponde a un sistema de gestión legislativa diseñado para administrar la información de la Asamblea Institucional Representativa del Instituto Tecnológico de Costa Rica.

El sistema permite:

- Gestión de asambleístas
- Gestión normativa
- Registro de sesiones
- Control de propuestas
- Certificaciones oficiales
- Control de roles y permisos

---

# Arquitectura del Sistema

El sistema implementa arquitectura MVC (Modelo Vista Controlador).

## Estructura del Proyecto

```text
/src
├── config
├── controllers
├── models
├── services
├── views
```

---
##
# Tecnologías Utilizadas

| Tecnología | Uso |
|---|---|
| Java | Backend principal |
| PostgreSQL | Base de datos relacional |
| Neon PostgreSQL | Base de datos cloud |
| JDBC | Conexión Java ↔ PostgreSQL |
| GitHub | Control de versiones |
| GitFlow | Gestión de ramas |

---

# Configuración de Base de Datos

La conexión a la base de datos se realiza mediante JDBC utilizando la clase:

```text
DatabaseConnection.java
```

La base de datos utilizada corresponde a Neon PostgreSQL.

---

# Compilación del Proyecto

## Compilar

```bash
javac -cp "lib/*" -d out src/config/DatabaseConnection.java src/Main.java
```

## Ejecutar

```bash
java -cp "out;lib/*" Main
```

---

# Gestión Git

El proyecto utiliza:

- main → rama final de producción
- develop → integración del Sprint
- feature/* → ramas individuales de trabajo

Cada funcionalidad se desarrolla mediante Pull Requests.

---

# Módulos Implementados

## AuthController

Responsable de:
- autenticación,
- validación de usuarios,
- control de acceso.

---

## SecretariaController

Responsable de:
- registro de asambleístas,
- consultas administrativas.

---

## LegislativoController

Responsable de:
- gestión normativa,
- carga de reglamentos,
- consultas legislativas.

---

# Seguridad

El sistema implementa:

- control de roles,
- permisos,
- auditoría,
- restricciones SQL,
- llaves foráneas,
- validaciones de integridad.

---

# Estado Actual Sprint 2

Actualmente el sistema posee:

- arquitectura MVC funcional,
- conexión real a PostgreSQL,
- CRUDs básicos,
- gestión de normativa,
- gestión de asambleístas,
- autenticación básica,
- integración GitFlow.
