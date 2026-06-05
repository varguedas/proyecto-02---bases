# Diccionario de Datos - Proyecto AIR

## Tabla: elemento_normativo

Descripción:
Almacena la estructura jerárquica de la normativa institucional. Permite representar títulos, capítulos, artículos, incisos y sub-incisos mediante una relación recursiva.

| Campo | Tipo | Restricciones | Descripción |
|---|---|---|---|
| id_elemento | SERIAL | PK | Identificador único del elemento normativo |
| id_reglamento | INT | FK NOT NULL | Reglamento al que pertenece |
| id_elemento_padre | INT | FK NULL | Referencia al elemento padre para recursividad |
| id_nivel_reglamento | INT | FK NOT NULL | Nivel jerárquico del elemento |
| numero_etiqueta | VARCHAR(50) | NULL | Etiqueta visible del elemento |
| contenido_texto | TEXT | NULL | Contenido textual |
| orden | INT | CHECK > 0 | Orden de aparición |
| fecha_inicio_vigencia | DATE | NOT NULL | Inicio de vigencia |
| fecha_fin_vigencia | DATE | NULL | Fin de vigencia |
| id_estado_vigencia | INT | FK NOT NULL | Estado de vigencia |



## Tabla: nombramiento

Descripción:
Registra los nombramientos históricos de los asambleístas, permitiendo mantener trazabilidad temporal de sus cargos y sectores.

| Campo | Tipo | Restricciones | Descripción |
|---|---|---|---|
| id_nombramiento | SERIAL | PK | Identificador único |
| asambleista_id | INT | FK NOT NULL | Asambleísta asociado |
| sector_id | INT | FK NOT NULL | Sector representado |
| resolucion_id | INT | FK NULL | Resolución asociada |
| fecha_inicio | DATE | NOT NULL | Inicio del nombramiento |
| fecha_fin | DATE | NULL | Fin del nombramiento |
| estado | VARCHAR(40) | DEFAULT 'VIGENTE' | Estado actual |
| id_puesto | INT | FK NULL | Puesto desempeñado |
| id_usuario_registro | INT | FK NULL | Usuario que realizó el registro |
| fecha_registro | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Fecha de registro |

### Restricciones importantes

- Se implementa una restricción `EXCLUDE USING gist` para evitar traslapes de fechas para un mismo asambleísta.
- La función `sp_registrar_nombramiento` valida automáticamente conflictos temporales.
- Se conserva historial completo de participación institucional.

## Tabla: sys_log_auditoria

Descripción:
Registra automáticamente operaciones relevantes del sistema mediante triggers de auditoría.

| Campo | Tipo | Restricciones | Descripción |
|---|---|---|---|
| id_log | SERIAL | PK | Identificador del registro |
| id_usuario | INT | FK NULL | Usuario relacionado |
| accion | VARCHAR(100) | NOT NULL | Acción realizada |
| tabla_afectada | VARCHAR(100) | NOT NULL | Tabla modificada |
| detalle | TEXT | NULL | Descripción del cambio |
| registro_id | INT | NULL | Registro afectado |
| fecha_hora | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Fecha y hora |

### Restricciones importantes

- La función `fn_auditoria_basica()` registra automáticamente operaciones INSERT, UPDATE y DELETE.
- El trigger `tg_auditoria_asambleista` demuestra auditoría automática sobre la tabla `asambleista`.




### Restricciones importantes

- La tabla utiliza recursividad mediante `id_elemento_padre`.
- Se implementan índices únicos para evitar duplicidad de orden jerárquico.
- Se valida que `fecha_fin_vigencia` no sea menor que `fecha_inicio_vigencia`.
- La vista `vw_arbol_normativo` reconstruye el árbol normativo usando `WITH RECURSIVE`.











## Tabla: sys_usuario

| Campo | Tipo | Descripción |
|---|---|---|
| id_usuario | SERIAL | Identificador único del usuario |
| username | VARCHAR(80) | Nombre de usuario |
| passw | TEXT | Contraseña cifrada |
| email | VARCHAR(150) | Correo institucional |
| estado | BOOLEAN | Estado activo/inactivo |

---

## Tabla: asambleista

| Campo | Tipo | Descripción |
|---|---|---|
| asambleista_id | SERIAL | Identificador del asambleísta |
| cedula | VARCHAR(30) | Cédula oficial |
| nombre | VARCHAR(150) | Nombre completo |
| correo_institucional | VARCHAR(150) | Correo institucional |

---

## Tabla: reglamento

| Campo | Tipo | Descripción |
|---|---|---|
| id_reglamento | SERIAL | Identificador del reglamento |
| nombre_normativa | VARCHAR(200) | Nombre del reglamento |
| sigla | VARCHAR(50) | Sigla institucional |

---

## Tabla: elemento_normativo

| Campo | Tipo | Descripción |
|---|---|---|
| id_elemento | SERIAL | Identificador del elemento |
| id_elemento_padre | INT | Relación recursiva padre-hijo |
| contenido_texto | TEXT | Texto normativo |
| fecha_inicio_vigencia | DATE | Inicio de vigencia |
| fecha_fin_vigencia | DATE | Fin de vigencia |

---

## Tabla: sesiones

| Campo | Tipo | Descripción |
|---|---|---|
| id_sesion | SERIAL | Identificador de sesión |
| numero_sesion | VARCHAR(80) | Código de sesión |
| fecha | DATE | Fecha de sesión |
| quorum_requerido | INT | Quórum legal |

---

## Tabla: propuesta

| Campo | Tipo | Descripción |
|---|---|---|
| id_propuesta | SERIAL | Identificador de propuesta |
| titulo | VARCHAR(250) | Título de propuesta |
| texto_sustitutivo | TEXT | Texto legal |
| codigo_air | VARCHAR(80) | Código AIR |

---

## Tabla: certificacion_emitida

| Campo | Tipo | Descripción |
|---|---|---|
| id_certificacion | SERIAL | Identificador |
| folio_unico | VARCHAR(80) | Folio institucional |
| hash_seguridad | TEXT | Hash SHA-256 |
| fecha_emision | TIMESTAMP | Fecha de emisión |
##