# Plan Sprint 3 - Semana 1

## 1. Objetivo del Sprint 3

El objetivo del Sprint 3 es ampliar el sistema AIR Gestión Legislativa mediante la incorporación de módulos relacionados con sesiones legislativas, propuestas, votaciones y certificaciones. Durante la primera semana se define la distribución de tareas entre los tres integrantes del equipo y las tablas necesarias para desarrollar las funcionalidades durante la segunda semana del sprint.

---

## 2. Alcance de la primera semana

Para esta primera entrega del Sprint 3 se contempla:

- Definir las tareas principales del sprint.
- Distribuir responsabilidades entre los tres integrantes del equipo.
- Identificar las tablas necesarias para los nuevos módulos.
- Preparar issues en GitHub para dar seguimiento al avance.
- Mantener la rama `develop` como base estable del proyecto.

---

## 3. Distribución de tareas

| Responsable | Tareas principales | Archivos o módulos relacionados | Resultado esperado |
|---|---|---|---|
| Kendall | Módulo de sesiones legislativas y asistencia | `Sesion.java`, `SesionDAO.java`, `SesionView.java`, `AsistenciaSesion.java`, `AsistenciaSesionDAO.java` | Crear, listar y consultar sesiones legislativas. Registrar asistencia de asambleístas en una sesión. |
| Victoria | Módulo de propuestas y votaciones | `Propuesta.java`, `PropuestaDAO.java`, `PropuestaView.java`, `Votacion.java`, `VotacionDAO.java`, `VotacionView.java` | Registrar propuestas asociadas a sesiones y gestionar votaciones con resultado. |
| Jimena | Módulo de certificaciones, documentación e integración | `Certificado.java`, `CertificacionDAO.java`, `CertificacionView.java`, `MenuPrincipalView.java`, `docs/` | Generar certificaciones básicas, actualizar documentación técnica e integrar los módulos al menú principal. |
| Todo el equipo | Pruebas e integración final | `proyecto-air.sql`, GitHub Issues, Neon PostgreSQL, GUI | Validar que los módulos funcionen con datos reales, registrar evidencias y mantener el tablero actualizado. |

---

## 4. Issues propuestas para Sprint 3

| Issue  | Descripción | Responsable |
|---|---|---|
| Diseñar tablas del Sprint 3 | Definir tablas para sesiones, asistencia, propuestas, votaciones y certificaciones | Todo el equipo |
| Implementar módulo de sesiones | Crear modelo, DAO y vista para sesiones legislativas | Integrante 1 |
| Implementar módulo de asistencia a sesiones | Registrar asistencia de asambleístas por sesión | Integrante 1 |
| Implementar módulo de propuestas | Crear modelo, DAO y vista para registrar propuestas | Integrante 2 |
| Implementar módulo de votaciones | Crear modelo, DAO y vista para registrar votaciones y resultados | Integrante 2 |
| Implementar módulo de certificaciones | Crear modelo, DAO y vista para generar certificaciones básicas | Integrante 3 |
| Integrar módulos del Sprint 3 al menú principal | Agregar accesos desde la GUI principal | Integrante 3 |
| Actualizar documentación técnica del Sprint 3 | Actualizar manual técnico, diccionario de datos y README | Integrante 3 |
| Preparar evidencias del Sprint 3 | Capturas, video y validaciones finales | Todo el equipo |

---

## 5. Tablas necesarias

## 5.1 Tabla: sesion_legislativa

| Campo | Tipo | Descripción |
|---|---|---|
| id_sesion | SERIAL PRIMARY KEY | Identificador único de la sesión |
| numero_sesion | VARCHAR(50) | Código o número de sesión |
| fecha_sesion | DATE | Fecha de realización |
| tipo_sesion | VARCHAR(50) | Ordinaria, extraordinaria u otro tipo |
| estado | VARCHAR(50) | Programada, realizada o cancelada |
| quorum_requerido | INT | Cantidad mínima requerida para sesionar |
| descripcion | TEXT | Detalle general de la sesión |

---

## 5.2 Tabla: asistencia_sesion

| Campo | Tipo | Descripción |
|---|---|---|
| id_asistencia | SERIAL PRIMARY KEY | Identificador del registro |
| id_sesion | INT | Sesión asociada |
| id_asambleista | INT | Asambleísta asociado |
| estado_asistencia | VARCHAR(50) | Presente, ausente o justificado |
| fecha_registro | TIMESTAMP | Fecha y hora del registro |

Relación:

- Una sesión puede tener muchos registros de asistencia.
- Un asambleísta puede aparecer en muchas sesiones.

---

## 5.3 Tabla: propuesta

| Campo | Tipo | Descripción |
|---|---|---|
| id_propuesta | SERIAL PRIMARY KEY | Identificador de la propuesta |
| id_sesion | INT | Sesión donde se presenta |
| titulo | VARCHAR(200) | Título de la propuesta |
| descripcion | TEXT | Contenido o resumen de la propuesta |
| estado | VARCHAR(50) | Pendiente, aprobada o rechazada |
| fecha_presentacion | DATE | Fecha de presentación |

Relación:

- Una sesión puede tener muchas propuestas.

---

## 5.4 Tabla: votacion

| Campo | Tipo | Descripción |
|---|---|---|
| id_votacion | SERIAL PRIMARY KEY | Identificador de la votación |
| id_propuesta | INT | Propuesta asociada |
| fecha_votacion | TIMESTAMP | Fecha y hora de votación |
| tipo_votacion | VARCHAR(50) | Simple, nominal o calificada |
| resultado | VARCHAR(50) | Aprobada o rechazada |

Relación:

- Una propuesta puede tener una votación asociada.

---

## 5.5 Tabla: voto_asambleista

| Campo | Tipo | Descripción |
|---|---|---|
| id_voto | SERIAL PRIMARY KEY | Identificador del voto |
| id_votacion | INT | Votación asociada |
| id_asambleista | INT | Asambleísta que emite el voto |
| voto | VARCHAR(50) | A favor, en contra o abstención |
| observacion | TEXT | Observación opcional |

Relación:

- Una votación puede tener muchos votos individuales.

---

## 5.6 Tabla: certificacion_emitida

| Campo | Tipo | Descripción |
|---|---|---|
| id_certificacion | SERIAL PRIMARY KEY | Identificador de la certificación |
| tipo_certificacion | VARCHAR(80) | Sesión, propuesta, votación o nombramiento |
| referencia_id | INT | ID del registro certificado |
| contenido | TEXT | Texto de la certificación |
| hash_seguridad | TEXT | Código hash de integridad |
| fecha_emision | TIMESTAMP | Fecha de emisión |

Relación:

- Una certificación puede estar asociada a una sesión, propuesta, votación o nombramiento.

---

## 6. Relaciones principales del Sprint 3

- Una sesión legislativa puede tener muchas asistencias.
- Una sesión legislativa puede tener muchas propuestas.
- Una propuesta puede tener una votación.
- Una votación puede tener muchos votos individuales.
- Una certificación puede emitirse sobre sesiones, propuestas, votaciones o nombramientos.

---

## 7. Criterios de aceptación

| Módulo | Criterio de aceptación |
|---|---|
| Sesiones | El sistema permite crear y listar sesiones legislativas |
| Asistencia | El sistema permite registrar asistencia por asambleísta en una sesión |
| Propuestas | El sistema permite registrar propuestas asociadas a sesiones |
| Votaciones | El sistema permite registrar votos y calcular el resultado |
| Certificaciones | El sistema permite generar una certificación básica |
| Integración | Los módulos nuevos se acceden desde el menú principal |
| Documentación | El diccionario de datos y manual técnico quedan actualizados |

---

## 8. Entregables de la primera semana

- Documento de planificación del Sprint 3.
- Lista de tareas distribuidas entre los tres integrantes.
- Tablas necesarias definidas.
- Issues creadas en GitHub.
- Rama de planificación integrada a `develop`.