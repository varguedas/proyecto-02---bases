# Proyectos 02 - Bases

# Sistema de Gestión Legislativa AIR

## Descripción General

Este proyecto corresponde al desarrollo de un sistema para la modernización de la gestión documental y normativa de la Asamblea Institucional Representativa (AIR) del TEC.

El objetivo principal es transformar los procesos manuales actuales en una plataforma estructurada capaz de:
- Gestionar normativa institucional,
- Registrar sesiones y propuestas,
- Controlar trazabilidad legislativa,
- Administrar participación de asambleístas,
- Generar certificaciones oficiales con integridad legal.

El sistema será desarrollado bajo arquitectura MVC y utilizando una base de datos relacional.

---

# Objetivos del Proyecto

- Centralizar la información legislativa institucional.
- Implementar trazabilidad histórica de reglamentos y reformas.
- Automatizar procesos legislativos y administrativos.
- Reducir errores asociados al manejo manual de documentación.
- Garantizar integridad y consistencia normativa.

---

# Tecnologías Utilizadas

| Componente | Tecnología |
|---|---|
| Arquitectura | MVC |
| Base de Datos | PostgreSQL |
| Control de versiones | Git + GitHub |
| Gestión del proyecto | GitHub Projects (Kanban) |

---

## Funcionalidades implementadas en Sprint 3

Durante el Sprint 3 se incorporaron los módulos principales para completar el flujo legislativo del sistema AIR:

- Gestión de sesiones legislativas.
- Registro de asistencia por sesión.
- Registro de votaciones con validación de quórum.
- Generación de atestados con folio único y hash SHA-256.
- Bitácora de auditoría para acciones críticas.
- Integración de accesos desde el menú principal.
- Validación funcional con datos reales en PostgreSQL.

El flujo principal demostrado es:

1. Crear una sesión legislativa.
2. Registrar asistencia de asambleístas.
3. Registrar una votación asociada a una propuesta.
4. Validar el resultado según quórum.
5. Generar un atestado institucional.
6. Consultar la bitácora de auditoría.