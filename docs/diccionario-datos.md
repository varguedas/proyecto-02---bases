# Diccionario de Datos - Proyecto AIR

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
